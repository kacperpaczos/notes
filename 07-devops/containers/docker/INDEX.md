# Docker

Ten katalog jest częścią sekcji containers.

## Zawartość katalogu

- [Docker Entrypoint](Docker_Entrypoint.md) - Szczegółowe informacje o ENTRYPOINT w Docker
- [healthcheck/](healthcheck/) - Informacje o Docker HEALTHCHECK
  - [Docker Healthcheck](healthcheck/Docker_Healthcheck.md) - Kompletny przewodnik po HEALTHCHECK
- [check-up/](check-up/) - Zewnętrzne systemy monitorowania
  - [Check-up vs Healthcheck](check-up/Check-up_vs_Healthcheck.md) - Porównanie różnych podejść do monitorowania

## Kluczowe tematy

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

