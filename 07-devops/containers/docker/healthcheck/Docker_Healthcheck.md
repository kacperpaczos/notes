# Docker HEALTHCHECK

## Czym jest HEALTHCHECK?

HEALTHCHECK to instrukcja Dockerfile, która pozwala Docker'owi okresowo sprawdzać, czy kontener jest zdrowy (działa poprawnie). Docker może automatycznie restartować kontenery, które nie przejdą testu zdrowia.

## Składnia

```dockerfile
HEALTHCHECK [OPTIONS] CMD command
HEALTHCHECK NONE  # wyłącza healthcheck
```

### Opcje HEALTHCHECK

- `--interval=DURATION` (domyślnie: 30s) - jak często uruchamiać test
- `--timeout=DURATION` (domyślnie: 30s) - ile czekać na odpowiedź
- `--start-period=DURATION` (domyślnie: 0s) - czas inicjalizacji kontenera
- `--retries=N` (domyślnie: 3) - ile nieudanych prób przed uznaniem za niezdrowy

## Przykłady HEALTHCHECK

### Przykład 1: Aplikacja Web
```dockerfile
FROM nginx:alpine

COPY index.html /usr/share/nginx/html/

HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost/ || exit 1
```

### Przykład 2: Aplikacja Python z Flask
```dockerfile
FROM python:3.9-slim

WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 5000

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:5000/health || exit 1

CMD ["python", "app.py"]
```

### Przykład 3: Baza danych PostgreSQL
```dockerfile
FROM postgres:13

HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=5 \
  CMD pg_isready -U ${POSTGRES_USER:-postgres} -d ${POSTGRES_DB:-postgres} || exit 1
```

### Przykład 4: Aplikacja Node.js
```dockerfile
FROM node:16-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD node healthcheck.js || exit 1

CMD ["npm", "start"]
```

## Tworzenie endpoint'a zdrowia

### Flask (Python)
```python
from flask import Flask, jsonify
import psutil
import datetime

app = Flask(__name__)

@app.route('/health')
def health_check():
    try:
        # Sprawdź połączenie z bazą danych
        # Sprawdź zużycie pamięci
        memory_usage = psutil.virtual_memory().percent
        
        if memory_usage > 90:
            return jsonify({
                'status': 'unhealthy',
                'timestamp': datetime.datetime.now().isoformat(),
                'memory_usage': f"{memory_usage}%"
            }), 503
            
        return jsonify({
            'status': 'healthy',
            'timestamp': datetime.datetime.now().isoformat(),
            'memory_usage': f"{memory_usage}%"
        }), 200
    except Exception as e:
        return jsonify({
            'status': 'unhealthy',
            'error': str(e),
            'timestamp': datetime.datetime.now().isoformat()
        }), 503

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
```

### Node.js
```javascript
// healthcheck.js
const http = require('http');

const options = {
  hostname: 'localhost',
  port: 3000,
  path: '/health',
  method: 'GET',
  timeout: 2000
};

const req = http.request(options, (res) => {
  if (res.statusCode === 200) {
    process.exit(0);
  } else {
    process.exit(1);
  }
});

req.on('error', () => {
  process.exit(1);
});

req.on('timeout', () => {
  req.destroy();
  process.exit(1);
});

req.end();
```

### Express.js endpoint
```javascript
// server.js
const express = require('express');
const app = express();

app.get('/health', (req, res) => {
  // Sprawdź połączenia z bazą danych, zewnętrznymi serwisami itp.
  const healthStatus = {
    uptime: process.uptime(),
    message: 'OK',
    timestamp: new Date().toISOString(),
    memory: process.memoryUsage()
  };
  
  try {
    // Dodatkowe sprawdzenia...
    res.status(200).json(healthStatus);
  } catch (error) {
    healthStatus.message = error;
    res.status(503).json(healthStatus);
  }
});

app.listen(3000, () => {
  console.log('Server running on port 3000');
});
```

## Sprawdzanie statusu zdrowia

### Komenda docker ps
```bash
# Sprawdź status wszystkich kontenerów
docker ps

# Wyświetl kolumnę STATUS z informacją o zdrowiu
docker ps --format "table {{.Names}}\t{{.Status}}"
```

### Komenda docker inspect
```bash
# Sprawdź szczegółowe informacje o zdrowiu
docker inspect --format='{{json .State.Health}}' container-name | jq
```

### Przykładowe statusy
- `starting` - kontener się uruchamia, healthcheck jeszcze nie działał
- `healthy` - ostatni healthcheck zakończył się sukcesem
- `unhealthy` - określona liczba healthcheck'ów zakończyła się niepowodzeniem

## Najlepsze praktyki

1. **Utrzymuj prostotę**: Healthcheck powinien być szybki i prosty
2. **Sprawdzaj rzeczywiste zdrowie**: Nie tylko czy proces działa, ale czy aplikacja odpowiada
3. **Uwzględnij czas uruchamiania**: Użyj `--start-period` dla aplikacji wymagających czasu na inicjalizację
4. **Monitoruj zależności**: Sprawdzaj połączenia z bazami danych, zewnętrznymi API
5. **Loguj problemy**: Zapisuj informacje o problemach dla debugowania

## Integracja z Docker Compose

```yaml
version: '3.8'

services:
  web:
    build: .
    ports:
      - "80:80"
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
    
  db:
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
```

## Problemy i rozwiązania

### Problem: Zbyt częste restarty
**Rozwiązanie**: Zwiększ `--retries` i `--interval`

### Problem: Healthcheck nie działa
**Rozwiązanie**: Sprawdź czy narzędzia (curl, wget) są zainstalowane w kontenerze

### Problem: Aplikacja wolno się uruchamia
**Rozwiązanie**: Zwiększ `--start-period` 