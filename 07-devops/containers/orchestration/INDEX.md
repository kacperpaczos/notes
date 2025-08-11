# orchestration

## Cel

## Problem

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Zalety

## Wady

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


Ten katalog jest częścią sekcji containers.

## Zawartość katalogu

- [docker-swarm/](docker-swarm/) — Docker Swarm orchestration
  - [Docker Swarm Basics](docker-swarm/Docker_Swarm_Basics.md) — Podstawy Docker Swarm
  - [Docker Swarm Services](docker-swarm/Docker_Swarm_Services.md) — Zarządzanie usługami

## Kluczowe tematy

### ⚓ Docker Swarm
- **Swarm Mode** — tryb klastra w Dockerze (wbudowana orkiestracja)
- **Service** — skalowalne usługi (create/scale/update/rollback)
- **Stack** — grupa usług z `docker-compose.yml`
- **Overlay Network** — komunikacja między usługami
- **Leader Election** — manager/worker, automatyczny failover
- **Secrets/Configs** — bezpieczne dane i konfiguracje

### Inne orkiestratory i wzorce
- **HashiCorp Nomad** — alternatywa do orkiestracji
- **GitOps** — Argo CD/Flux do deklaratywnych wdrożeń
- **Blue/Green, Canary** — strategie wdrożeń