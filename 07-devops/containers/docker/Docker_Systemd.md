# Docker a systemd

## Dlaczego kontenery zwykle nie używają systemd

- Standardowy kontener uruchamia jeden główny proces jako PID 1 i nie posiada pełnego menedżera init (np. systemd). To celowe: kontenery mają być lekkie i skupione na jednej usłudze.
- Docker na hoście bywa zarządzany przez systemd (jako usługa hosta), ale to nie dotyczy procesów wewnątrz kontenera.

## Jak działa kontener bez systemd

- PID 1 powinien poprawnie obsługiwać sygnały i „sprzątać” procesy potomne (reaping). W praktyce często dodaje się minimalny init, np. `tini` lub `dumb-init`.
- Wiele usług uruchamiamy jako wiele kontenerów (po jednym procesie) i koordynujemy przez sieć/Compose/Kubernetes zamiast „wszystko w jednym” jak na klasycznym serwerze z systemd.
- Jeśli w jednym kontenerze naprawdę potrzebnych jest kilka procesów, stosuje się lekkie supervisory (np. `s6`, `runit`, `supervisord`) – nadal bez pełnego systemd.

### Minimalny init jako PID 1 (przykład)

```dockerfile
FROM debian:stable-slim
RUN apt-get update && apt-get install -y --no-install-recommends tini \
    && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY . /app
ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["./twoja-aplikacja"]
```

## Gdy systemd w kontenerze jest jednak potrzebny

- Uzasadnione przypadki: testy ról Ansible, symulacja „prawdziwego” serwera, specyficzne środowiska CI.
- W takich sytuacjach można użyć obrazu z działającym systemd, np. `[jrei/systemd-debian (Docker Hub)](https://hub.docker.com/r/jrei/systemd-debian)`.
- Zwykle wymaga to uruchomienia w trybie uprzywilejowanym i montowania cgroups, co zwiększa narzut i złożoność – używaj tylko, gdy to konieczne.

### Uruchomienie kontenera z systemd (przykład)

```bash
docker run -d \
  --name debian-systemd \
  --privileged \
  -v /sys/fs/cgroup:/sys/fs/cgroup:ro \
  jrei/systemd-debian:latest
```

### Wymagane mounty i uprawnienia (systemd w kontenerze)

- Pliki systemowe: `/proc` i `/sys` muszą być zamontowane; zalecane, aby `/sys` było read‑only.
- Cgroups: zamontuj `cgroup2` (typowo pod `/sys/fs/cgroup`), często potrzeba trybu `--privileged`.
- `/proc/sys` powinno być read‑only (wyjątki dla namespace’ów sieciowych); rozważ ograniczenia zgodnie z wytycznymi systemd.
- `/dev`: osobny tmpfs z podstawowymi urządzeniami; zapewnij poprawne `tty` podłączone do `/dev/console`.
- Integracja na hoście: przy głębszej integracji z cgroups rozważ `Delegate=yes` po stronie hosta (jednostki systemd delegujące zarządzanie poddrzewami cgroups dla menedżera kontenerów).

Źródła (szczegóły):
- [systemd: CONTAINER_INTERFACE – filesystem i sygnały](https://github.com/systemd/systemd/blob/main/docs/CONTAINER_INTERFACE.md#_snippet_0)
- [systemd: CGROUP_DELEGATION – `Delegate=yes`](https://github.com/systemd/systemd/blob/main/docs/CGROUP_DELEGATION.md#_snippet_4)

## Rekomendacje

- Trzymaj się zasady „jeden proces na kontener”.
- Dodawaj `tini`/`dumb-init`, aby PID 1 prawidłowo obsługiwał sygnały i reaping.
- Po systemd sięgaj wyłącznie w specyficznych przypadkach (np. zgodność/testy).

## Źródła / dalsza lektura

- `[jrei/systemd-debian (Docker Hub)](https://hub.docker.com/r/jrei/systemd-debian)`
- `[systemd: CONTAINER_INTERFACE.md](https://github.com/systemd/systemd/blob/main/docs/CONTAINER_INTERFACE.md)`
- `[systemd: CGROUP_DELEGATION.md](https://github.com/systemd/systemd/blob/main/docs/CGROUP_DELEGATION.md)`


