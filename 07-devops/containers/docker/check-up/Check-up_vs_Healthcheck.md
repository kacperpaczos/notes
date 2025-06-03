# Check-up vs Healthcheck - Różnice i zastosowania

## Healthcheck vs Check-up - Definicje

### Docker HEALTHCHECK
- **Wbudowana funkcja Docker'a** do monitorowania zdrowia kontenerów
- **Automatyczne sprawdzenie** czy kontener działa poprawnie
- **Integracja z Docker'em** - rezultaty widoczne w `docker ps`
- **Automatyczne akcje** - Docker może restartować niezdrowe kontenery

### Check-up (External Health Monitoring)
- **Zewnętrzne narzędzia monitorowania** aplikacji i infrastruktury
- **Kompleksowe sprawdzenie** całej aplikacji i jej zależności
- **Raportowanie** i **alerting** do zespołów DevOps
- **Integracja z systemami monitorowania** (Prometheus, Grafana, ELK Stack)

## Kluczowe różnice

| Aspekt | Docker HEALTHCHECK | External Check-up |
|--------|-------------------|-------------------|
| **Zasięg** | Pojedynczy kontener | Cała aplikacja/infrastruktura |
| **Częstotliwość** | Sekundy/minuty | Minuty/godziny |
| **Złożoność** | Proste testy | Kompleksowe scenariusze |
| **Automatyzacja** | Restart kontenera | Alerty, eskaliacja |
| **Kontekst** | Stan kontenera | Stan biznesowy aplikacji |
| **Narzędzia** | Docker built-in | Nagios, Prometheus, DataDog |

## Przykłady zastosowań

### Docker HEALTHCHECK - Przykłady

#### 1. Podstawowy test HTTP
```dockerfile
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
```

#### 2. Test bazy danych
```dockerfile
HEALTHCHECK --interval=15s --timeout=5s --retries=5 \
  CMD pg_isready -h localhost -p 5432 -U postgres || exit 1
```

#### 3. Test aplikacji z dependency
```dockerfile
HEALTHCHECK --interval=30s --timeout=15s --retries=3 \
  CMD python /app/health_check.py || exit 1
```

### External Check-up - Przykłady

#### 1. Prometheus + Grafana monitoring
```yaml
# prometheus.yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'webapp'
    static_configs:
      - targets: ['webapp:8080']
    metrics_path: /metrics
    scrape_interval: 5s

  - job_name: 'database'
    static_configs:
      - targets: ['postgres_exporter:9187']
```

#### 2. Nagios service check
```bash
# check_webapp.sh
#!/bin/bash

# Sprawdź dostępność aplikacji
response=$(curl -s -o /dev/null -w "%{http_code}" http://webapp:8080/health)

if [ "$response" = "200" ]; then
    echo "OK - Application is healthy"
    exit 0
else
    echo "CRITICAL - Application returned $response"
    exit 2
fi
```

#### 3. Kubernetes Liveness i Readiness Probes
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp
spec:
  replicas: 3
  template:
    spec:
      containers:
      - name: webapp
        image: myapp:latest
        ports:
        - containerPort: 8080
        livenessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /ready
            port: 8080
          initialDelaySeconds: 5
          periodSeconds: 5
```

## Kiedy używać którego?

### Używaj Docker HEALTHCHECK gdy:
- ✅ Chcesz automatycznego restartu kontenerów
- ✅ Monitorujesz pojedyncze kontenery
- ✅ Potrzebujesz szybkiej odpowiedzi na awarie
- ✅ Aplikacja ma prostą architekturę

### Używaj External Check-up gdy:
- ✅ Monitorujesz złożone systemy rozproszone
- ✅ Potrzebujesz raportowania i alertów
- ✅ Chcesz śledzić metryki biznesowe
- ✅ Integrujesz z systemami monitorowania

## Implementacja hybrydowa - Best Practice

### Docker Compose z HEALTHCHECK
```yaml
version: '3.8'

services:
  webapp:
    build: .
    ports:
      - "8080:8080"
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s
    depends_on:
      database:
        condition: service_healthy

  database:
    image: postgres:13
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U user -d myapp"]
      interval: 10s
      timeout: 5s
      retries: 5

  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
```

### Endpoint zdrowia z metrykami
```python
from flask import Flask, jsonify
import psutil
import time
import psycopg2
from prometheus_client import Counter, Histogram, generate_latest

app = Flask(__name__)

# Metryki Prometheus
health_checks = Counter('health_checks_total', 'Total health checks')
health_check_duration = Histogram('health_check_duration_seconds', 'Health check duration')

@app.route('/health')
@health_check_duration.time()
def health_check():
    health_checks.inc()
    
    try:
        # Sprawdź bazę danych
        conn = psycopg2.connect(
            host="database",
            database="myapp",
            user="user",
            password="password"
        )
        conn.close()
        
        # Sprawdź zużycie zasobów
        memory_usage = psutil.virtual_memory().percent
        disk_usage = psutil.disk_usage('/').percent
        
        status = {
            'status': 'healthy',
            'timestamp': time.time(),
            'checks': {
                'database': 'ok',
                'memory_usage': f"{memory_usage}%",
                'disk_usage': f"{disk_usage}%"
            }
        }
        
        if memory_usage > 90 or disk_usage > 90:
            status['status'] = 'warning'
            
        return jsonify(status), 200
        
    except Exception as e:
        return jsonify({
            'status': 'unhealthy',
            'error': str(e),
            'timestamp': time.time()
        }), 503

@app.route('/metrics')
def metrics():
    return generate_latest()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
```

## Monitoring stack - kompletny przykład

### 1. Application Health Endpoint
```python
# health.py
import requests
import json
import sys

def check_dependencies():
    checks = {}
    
    # Database check
    try:
        # Sprawdź połączenie z bazą
        checks['database'] = 'healthy'
    except:
        checks['database'] = 'unhealthy'
    
    # External API check
    try:
        response = requests.get('https://api.external-service.com/health', timeout=5)
        checks['external_api'] = 'healthy' if response.status_code == 200 else 'unhealthy'
    except:
        checks['external_api'] = 'unhealthy'
    
    # Redis check
    try:
        # Sprawdź Redis
        checks['redis'] = 'healthy'
    except:
        checks['redis'] = 'unhealthy'
    
    return checks

if __name__ == '__main__':
    checks = check_dependencies()
    unhealthy = [k for k, v in checks.items() if v == 'unhealthy']
    
    if unhealthy:
        print(f"Unhealthy dependencies: {unhealthy}")
        sys.exit(1)
    else:
        print("All dependencies healthy")
        sys.exit(0)
```

### 2. Dockerfile z HEALTHCHECK
```dockerfile
FROM python:3.9-slim

WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=15s --start-period=30s --retries=3 \
  CMD python health.py

CMD ["python", "app.py"]
```

### 3. Monitoring z Prometheus Alert Rules
```yaml
# alert_rules.yml
groups:
- name: application_health
  rules:
  - alert: ContainerUnhealthy
    expr: up == 0
    for: 1m
    labels:
      severity: critical
    annotations:
      summary: "Container {{ $labels.instance }} is down"
      
  - alert: HighMemoryUsage
    expr: container_memory_usage_bytes / container_spec_memory_limit_bytes > 0.9
    for: 5m
    labels:
      severity: warning
    annotations:
      summary: "High memory usage on {{ $labels.instance }}"
```

## Podsumowanie

Najlepszym podejściem jest **połączenie obydwu strategii**:

1. **Docker HEALTHCHECK** - dla szybkiego reagowania na awarie kontenerów
2. **External Check-up** - dla kompleksowego monitorowania aplikacji

Takie podejście zapewnia:
- ⚡ Szybkie reagowanie na awarie (HEALTHCHECK)
- 📊 Pełny wgląd w stan systemu (External monitoring)
- 🔄 Automatyczne samoleczenie (restart kontenerów)
- 📈 Długoterminowe trendy i metryki
- 🚨 Zaawansowane alertowanie i eskaliacja 