# Docker Compose

## 🎼 **Docker Compose** – orkiestracja lokalna kontenerów (dev/test)

### Podstawowa struktura docker-compose.yml

```yaml
version: '3.8'

services:
  web:
    image: nginx:alpine
    ports:
      - "8080:80"
    volumes:
      - ./html:/usr/share/nginx/html
    environment:
      - NGINX_HOST=localhost
    depends_on:
      - db
    networks:
      - app-network

  db:
    image: postgres:13
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app-network

  redis:
    image: redis:alpine
    ports:
      - "6379:6379"
    networks:
      - app-network

volumes:
  postgres_data:

networks:
  app-network:
    driver: bridge
```

### 🔧 **Service definitions** – definiowanie usług

#### Podstawowe opcje serwisu
```yaml
services:
  myapp:
    # Obraz do użycia
    image: myapp:latest
    
    # Build context (zamiast image)
    build:
      context: ./app
      dockerfile: Dockerfile
      args:
        VERSION: 1.0.0
    
    # Nazwa kontenera
    container_name: myapp-container
    
    # Restart policy
    restart: unless-stopped
    
    # Health check
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:3000/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
```

#### Konfiguracja zasobów
```yaml
services:
  myapp:
    # Limity zasobów
    deploy:
      resources:
        limits:
          cpus: '0.50'
          memory: 512M
        reservations:
          cpus: '0.25'
          memory: 256M
    
    # Ulimits
    ulimits:
      nofile:
        soft: 20000
        hard: 40000
```

### 🌍 **Environment variables** – konfiguracja środowiskowa

#### Zmienne środowiskowe
```yaml
services:
  web:
    # Bezpośrednie zmienne
    environment:
      - NODE_ENV=production
      - PORT=3000
      - DATABASE_URL=postgresql://user:pass@db:5432/myapp
    
    # Z pliku .env
    env_file:
      - .env
      - .env.local
    
    # Z określonego pliku
    env_file:
      - ./config/production.env
```

#### Plik .env
```bash
# .env
NODE_ENV=production
PORT=3000
DATABASE_URL=postgresql://user:pass@db:5432/myapp
REDIS_URL=redis://redis:6379
API_KEY=your-secret-key
```

### 🌐 **Networking** – komunikacja między kontenerami

#### Typy sieci
```yaml
services:
  web:
    # Domyślna sieć bridge
    networks:
      - default
    
    # Własna sieć
    networks:
      - frontend
      - backend

networks:
  # Sieć bridge (domyślna)
  default:
  
  # Sieć frontend
  frontend:
    driver: bridge
    ipam:
      config:
        - subnet: 172.20.0.0/16
  
  # Sieć backend
  backend:
    driver: bridge
    internal: true  # Brak dostępu do internetu
  
  # Sieć host
  host_network:
    driver: host
```

#### Konfiguracja sieci
```yaml
services:
  web:
    # Określony IP
    networks:
      frontend:
        ipv4_address: 172.20.0.10
    
    # Alias sieciowy
    networks:
      backend:
        aliases:
          - web-service
          - api

networks:
  frontend:
    driver: bridge
    ipam:
      config:
        - subnet: 172.20.0.0/16
          gateway: 172.20.0.1
```

### 💾 **Volume mounts** – współdzielenie danych

#### Typy wolumenów
```yaml
services:
  web:
    volumes:
      # Bind mount
      - ./app:/app
      
      # Named volume
      - app_data:/app/data
      
      # Anonymous volume
      - /app/temp
      
      # Read-only volume
      - ./config:/app/config:ro
      
      # Tmpfs volume
      - /app/cache:tmpfs

volumes:
  app_data:
    driver: local
    driver_opts:
      type: none
      o: bind
      device: /path/on/host
```

#### Konfiguracja wolumenów
```yaml
volumes:
  # Lokalny wolumen
  local_data:
    driver: local
  
  # NFS wolumen
  nfs_data:
    driver: local
    driver_opts:
      type: nfs
      o: addr=192.168.1.100,rw
      device: ":/path/to/dir"
  
  # Custom driver
  custom_data:
    driver: custom-driver
    driver_opts:
      key: value
```

### 🔄 **Dependency management**

#### depends_on
```yaml
services:
  web:
    depends_on:
      - db
      - redis
  
  api:
    depends_on:
      db:
        condition: service_healthy
      redis:
        condition: service_started
```

#### Service health checks
```yaml
services:
  db:
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
  
  web:
    depends_on:
      db:
        condition: service_healthy
```

### 🚀 **Scaling i deployment**

#### Scaling services
```bash
# Skalowanie serwisu
docker-compose up --scale web=3

# Skalowanie w docker-compose.yml
services:
  web:
    deploy:
      replicas: 3
```

#### Update strategies
```yaml
services:
  web:
    deploy:
      update_config:
        parallelism: 1
        delay: 10s
        order: start-first
        failure_action: rollback
        monitor: 60s
        max_failure_ratio: 0.3
```

### 🔧 **Zaawansowane funkcje**

#### Override files
```yaml
# docker-compose.override.yml
version: '3.8'
services:
  web:
    volumes:
      - ./src:/app/src  # Development mount
    environment:
      - DEBUG=true
    ports:
      - "3000:3000"  # Development port
```

#### Multiple compose files
```bash
# Użycie wielu plików
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up

# Lub z override
docker-compose -f docker-compose.yml -f docker-compose.override.yml up
```

#### Profiles
```yaml
services:
  web:
    profiles:
      - frontend
      - full-stack
  
  db:
    profiles:
      - backend
      - full-stack
  
  redis:
    profiles:
      - cache
      - full-stack
```

```bash
# Uruchomienie z profilem
docker-compose --profile full-stack up
```

### 📊 **Monitoring i debugging**

#### Logs
```bash
# Wyświetlenie logów
docker-compose logs

# Logi konkretnego serwisu
docker-compose logs web

# Logi w czasie rzeczywistym
docker-compose logs -f

# Logi z timestamp
docker-compose logs -t
```

#### Exec i shell
```bash
# Wykonanie komendy w kontenerze
docker-compose exec web npm install

# Shell w kontenerze
docker-compose exec web /bin/bash

# Wykonanie jako root
docker-compose exec -u root web whoami
```

#### Inspect
```bash
# Informacje o serwisach
docker-compose ps

# Szczegółowe informacje
docker-compose config

# Sprawdzenie konfiguracji
docker-compose config --quiet
```

### 🔐 **Secrets i configs**

#### Secrets
```yaml
services:
  web:
    secrets:
      - db_password
      - api_key

secrets:
  db_password:
    file: ./secrets/db_password.txt
  api_key:
    external: true
```

#### Configs
```yaml
services:
  web:
    configs:
      - source: app_config
        target: /app/config/app.yml
      - source: nginx_config
        target: /etc/nginx/nginx.conf

configs:
  app_config:
    file: ./config/app.yml
  nginx_config:
    external: true
```

### 🛠️ **Development workflow**

#### Development setup
```yaml
version: '3.8'
services:
  web:
    build:
      context: .
      target: development
    volumes:
      - .:/app
      - /app/node_modules
    environment:
      - NODE_ENV=development
      - DEBUG=true
    ports:
      - "3000:3000"
    command: npm run dev
```

#### Production setup
```yaml
version: '3.8'
services:
  web:
    build:
      context: .
      target: production
    environment:
      - NODE_ENV=production
    ports:
      - "80:3000"
    restart: unless-stopped
    deploy:
      replicas: 3
```

### 📝 **Przykłady użycia**

#### Full-stack application
```yaml
version: '3.8'
services:
  frontend:
    build: ./frontend
    ports:
      - "3000:3000"
    environment:
      - REACT_APP_API_URL=http://backend:8000
    depends_on:
      - backend

  backend:
    build: ./backend
    ports:
      - "8000:8000"
    environment:
      - DATABASE_URL=postgresql://user:pass@db:5432/myapp
      - REDIS_URL=redis://redis:6379
    depends_on:
      - db
      - redis

  db:
    image: postgres:13
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:alpine
    volumes:
      - redis_data:/data

volumes:
  postgres_data:
  redis_data:
```
