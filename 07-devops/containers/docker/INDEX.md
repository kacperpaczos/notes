# Docker

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

- [Docker Introduction](Docker_Introduction.md) - Core Docker concepts, benefits and challenges
- [Docker Entrypoint](Docker_Entrypoint.md) - Szczegółowe informacje o ENTRYPOINT w Docker
- [Dockerfile & Images](Dockerfile_Images.md) - Tworzenie obrazów i Dockerfile
- [Docker Containers](Docker_Containers.md) - Zarządzanie kontenerami
- [Docker a systemd](Docker_Systemd.md) - Dlaczego kontenery zwykle nie używają systemd i kiedy warto
- [Docker Compose](Docker_Compose.md) - Orkiestracja lokalna kontenerów
- [Docker Volumes](Docker_Volumes.md) - Trwałość danych i zarządzanie storage
- [Docker Registry](Docker_Registry.md) - Repozytoria obrazów
- [healthcheck/](healthcheck/) - Informacje o Docker HEALTHCHECK
  - [Docker Healthcheck](healthcheck/Docker_Healthcheck.md) - Kompletny przewodnik po HEALTHCHECK
- [check-up/](check-up/) - Zewnętrzne systemy monitorowania
  - [Check-up vs Healthcheck](check-up/Check-up_vs_Healthcheck.md) - Porównanie różnych podejść do monitorowania

## Kluczowe tematy

### 🐳 **Dockerfile & Images**
- **Dockerfile** – plik definiujący obraz aplikacji
- **Docker Image** – niemutowalny artefakt (aplikacja + środowisko)
- **Multi-stage builds** – optymalizacja rozmiaru obrazów
- **Layer caching** – strategie optymalizacji buildów
- **Base images** – wybór odpowiednich obrazów bazowych

### 📦 **Docker Containers**
- **Docker Container** – uruchomiona instancja obrazu
- **Container lifecycle** – create, start, stop, remove
- **Resource limits** – CPU, memory, I/O constraints
- **Container networking** – bridge, host, none networks
- **Container inspection** – logs, exec, inspect

### 🎼 **Docker Compose**
- **Docker Compose** – deklaratywne definiowanie usług (dev/test)
- **Service definitions** – definiowanie usług
- **Environment variables** – konfiguracja środowiskowa
- **Networking** – komunikacja między kontenerami
- **Volume mounts** – współdzielenie danych

### 💾 **Docker Volumes & Storage**
- **Volumes** – zarządzane przez Docker storage
- **Bind mounts** – bezpośrednie mapowanie katalogów
- **tmpfs mounts** – tymczasowe storage w pamięci
- **Volume drivers** – rozszerzenia storage
- **Data persistence** – strategie trwałości danych

### 🏪 **Docker Registry**
- **Docker Registry** – repozytorium obrazów (np. Docker Hub)
- **Private registries** – własne repozytoria
- **Image tagging** – wersjonowanie obrazów
- **Registry security** – uwierzytelnianie i autoryzacja
- **Image scanning** – bezpieczeństwo obrazów

### ENTRYPOINT
- Jak działa ENTRYPOINT w Docker
- Różnice między ENTRYPOINT a CMD  
- Najlepsze praktyki i przykłady implementacji
- Tworzenie skryptów startowych

### HEALTHCHECK
- Wbudowane mechanizmy monitorowania Docker
- Konfiguracja health checks
- Integracja z Docker Compose i orchestratorami

### External Monitoring (Check-up)
- Zewnętrzne systemy monitorowania
- Różnice między HEALTHCHECK a check-up
- Implementacja hybrydowa
- Integracja z Prometheus, Grafana, Nagios

