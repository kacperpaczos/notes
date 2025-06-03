# Docker HEALTHCHECK

## Czym jest HEALTHCHECK?

HEALTHCHECK pozwala Docker'owi okresowo sprawdzać, czy kontener działa poprawnie. Docker może automatycznie restartować niezdrowe kontenery.

## Składnia

```dockerfile
HEALTHCHECK [OPTIONS] CMD command
HEALTHCHECK NONE  # wyłącza healthcheck
```

### Opcje
- `--interval=30s` - jak często uruchamiać test
- `--timeout=30s` - ile czekać na odpowiedź  
- `--start-period=0s` - czas inicjalizacji
- `--retries=3` - ile prób przed uznaniem za niezdrowy

## Przykłady

### Python Flask
```dockerfile
FROM python:3.9-slim
WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:5000/health || exit 1

CMD ["python", "app.py"]
```

```python
# app.py
from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/health')
def health():
    return jsonify({'status': 'healthy'}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
```

### PHP
```dockerfile
FROM php:8.1-apache
COPY . /var/www/html/

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost/health.php || exit 1
```

```php
<?php
// health.php
http_response_code(200);
echo json_encode(['status' => 'healthy']);
?>
```

## Sprawdzanie statusu

```bash
# Status kontenerów
docker ps

# Szczegóły zdrowia
docker inspect --format='{{json .State.Health}}' container-name
```

### Statusy
- `starting` - uruchamianie
- `healthy` - zdrowy  
- `unhealthy` - niezdrowy

## Docker Compose

```yaml
version: '3.8'
services:
  web:
    build: .
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost/health.php"]
      interval: 30s
      timeout: 10s
      retries: 3
``` 