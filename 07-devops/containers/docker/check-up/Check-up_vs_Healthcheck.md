# Check-up vs Healthcheck

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


## Różnice

| Aspekt | Docker HEALTHCHECK | External Check-up |
|--------|-------------------|-------------------|
| **Zasięg** | Pojedynczy kontener | Cała aplikacja |
| **Częstotliwość** | Sekundy/minuty | Minuty/godziny |
| **Złożoność** | Proste testy | Kompleksowe scenariusze |
| **Automatyzacja** | Restart kontenera | Alerty, eskaliacja |
| **Narzędzia** | Docker built-in | Prometheus, Nagios |

## Docker HEALTHCHECK - Przykłady

### Python
```dockerfile
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:5000/health || exit 1
```

### PHP  
```dockerfile
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost/health.php || exit 1
```

## External Check-up - Przykłady

### Prometheus monitoring
```yaml
# prometheus.yml
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'webapp'
    static_configs:
      - targets: ['webapp:8080']
```

### Nagios check
```bash
#!/bin/bash
response=$(curl -s -o /dev/null -w "%{http_code}" http://webapp:8080/health)
if [ "$response" = "200" ]; then
    echo "OK"
    exit 0
else
    echo "CRITICAL"
    exit 2
fi
```

## Kiedy używać?

### HEALTHCHECK
- ✅ Automatyczny restart kontenerów
- ✅ Proste aplikacje
- ✅ Szybka reakcja na awarie

### External Check-up  
- ✅ Złożone systemy
- ✅ Raportowanie i alerty
- ✅ Metryki biznesowe

## Implementacja hybrydowa

### Python endpoint z metrykami
```python
from flask import Flask, jsonify
from prometheus_client import Counter, generate_latest

app = Flask(__name__)
health_checks = Counter('health_checks_total')

@app.route('/health')
def health():
    health_checks.inc()
    return jsonify({'status': 'healthy'})

@app.route('/metrics')
def metrics():
    return generate_latest()
```

### PHP endpoint  
```php
<?php
// health.php
$status = [
    'status' => 'healthy',
    'timestamp' => time(),
    'memory' => memory_get_usage()
];
echo json_encode($status);
?>
```

## Docker Compose z monitoringiem

```yaml
version: '3.8'
services:
  webapp:
    build: .
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost/health.php"]
      interval: 30s
      timeout: 10s
      retries: 3

  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
```

## Podsumowanie

Najlepsze podejście: **połącz oba rozwiązania**
- HEALTHCHECK - szybka reakcja
- External monitoring - pełny obraz systemu 