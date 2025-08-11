# Docker Volumes

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


## 💾 **Docker Volumes & Storage** – trwałość danych i zarządzanie storage

### **Volumes** – zarządzane przez Docker storage

#### Tworzenie i zarządzanie wolumenami
```bash
# Tworzenie wolumenu
docker volume create my_data

# Lista wolumenów
docker volume ls

# Inspekcja wolumenu
docker volume inspect my_data

# Usunięcie wolumenu
docker volume rm my_data

# Usunięcie nieużywanych wolumenów
docker volume prune
```

#### Używanie wolumenów w kontenerach
```bash
# Uruchomienie z wolumenem
docker run -d -v my_data:/app/data myapp:latest

# Uruchomienie z określonymi opcjami
docker run -d \
  -v my_data:/app/data:ro \
  -v cache_data:/app/cache \
  myapp:latest
```

#### Konfiguracja wolumenów w docker-compose.yml
```yaml
version: '3.8'
services:
  web:
    image: myapp:latest
    volumes:
      - app_data:/app/data
      - cache_data:/app/cache
      - logs_data:/app/logs

volumes:
  app_data:
    driver: local
  cache_data:
    driver: local
    driver_opts:
      type: tmpfs
      device: tmpfs
  logs_data:
    driver: local
```

### **Bind mounts** – bezpośrednie mapowanie katalogów

#### Podstawowe bind mounts
```bash
# Mapowanie katalogu hosta do kontenera
docker run -d -v /host/path:/container/path myapp:latest

# Mapowanie z określonymi opcjami
docker run -d \
  -v /host/path:/container/path:ro \
  -v /host/config:/container/config:rw \
  myapp:latest
```

#### Bind mounts w docker-compose.yml
```yaml
version: '3.8'
services:
  web:
    image: myapp:latest
    volumes:
      # Pełna ścieżka
      - /host/data:/app/data
      
      # Względna ścieżka
      - ./config:/app/config
      
      # Read-only
      - ./logs:/app/logs:ro
      
      # Z określonymi opcjami
      - type: bind
        source: ./src
        target: /app/src
        read_only: true
```

#### Przykłady użycia bind mounts
```yaml
# Development environment
services:
  web:
    build: .
    volumes:
      - .:/app  # Kod źródłowy
      - /app/node_modules  # Anonymous volume dla node_modules
    environment:
      - NODE_ENV=development

# Production environment
services:
  web:
    image: myapp:latest
    volumes:
      - /var/log/myapp:/app/logs  # Logi na hoście
      - /etc/myapp:/app/config:ro  # Konfiguracja read-only
```

### **tmpfs mounts** – tymczasowe storage w pamięci

#### Używanie tmpfs
```bash
# Tymczasowy wolumen w pamięci
docker run -d --tmpfs /app/temp myapp:latest

# Z określonymi opcjami
docker run -d \
  --tmpfs /app/cache:noexec,nosuid,size=100m \
  myapp:latest
```

#### tmpfs w docker-compose.yml
```yaml
version: '3.8'
services:
  web:
    image: myapp:latest
    tmpfs:
      - /app/temp
      - /app/cache:noexec,nosuid,size=100m
      - /tmp:noexec,nosuid,size=50m
```

#### Przykłady użycia tmpfs
```yaml
# Cache aplikacji
services:
  web:
    image: myapp:latest
    tmpfs:
      - /app/cache:size=200m
      - /app/sessions:size=50m

# Temporary files
services:
  processor:
    image: processor:latest
    tmpfs:
      - /tmp:size=1g
      - /var/tmp:size=500m
```

### **Volume drivers** – rozszerzenia storage

#### Lokalny driver (domyślny)
```yaml
volumes:
  local_data:
    driver: local
    driver_opts:
      type: none
      o: bind
      device: /path/on/host
```

#### NFS driver
```yaml
volumes:
  nfs_data:
    driver: local
    driver_opts:
      type: nfs
      o: addr=192.168.1.100,rw
      device: ":/path/to/dir"
```

#### AWS EBS driver
```yaml
volumes:
  ebs_data:
    driver: rexray/ebs
    driver_opts:
      size: 20
      type: gp2
      zone: us-west-2a
```

#### Custom driver
```yaml
volumes:
  custom_data:
    driver: custom-driver
    driver_opts:
      key: value
      option: setting
```

### **Data persistence** – strategie trwałości danych

#### Strategie backup
```bash
# Backup wolumenu
docker run --rm -v my_data:/data -v $(pwd):/backup \
  alpine tar czf /backup/my_data_backup.tar.gz -C /data .

# Restore wolumenu
docker run --rm -v my_data:/data -v $(pwd):/backup \
  alpine tar xzf /backup/my_data_backup.tar.gz -C /data
```

#### Backup w docker-compose.yml
```yaml
version: '3.8'
services:
  backup:
    image: alpine
    volumes:
      - db_data:/data
      - ./backups:/backup
    command: |
      sh -c "
        tar czf /backup/db_backup_$(date +%Y%m%d_%H%M%S).tar.gz -C /data .
        find /backup -name 'db_backup_*.tar.gz' -mtime +7 -delete
      "
    profiles:
      - backup
```

#### Data migration
```bash
# Migracja danych między wolumenami
docker run --rm \
  -v old_data:/old \
  -v new_data:/new \
  alpine sh -c "cp -r /old/* /new/"

# Migracja z bind mount do wolumenu
docker run --rm \
  -v /host/path:/old \
  -v new_data:/new \
  alpine sh -c "cp -r /old/* /new/"
```

### **Volume management** – zaawansowane operacje

#### Volume inspection
```bash
# Szczegółowe informacje o wolumenie
docker volume inspect my_data

# Lokalizacja wolumenu na hoście
docker volume inspect my_data | grep Mountpoint

# Rozmiar wolumenu
docker run --rm -v my_data:/data alpine du -sh /data
```

#### Volume cleanup
```bash
# Usunięcie nieużywanych wolumenów
docker volume prune

# Usunięcie wszystkich wolumenów
docker volume prune -a

# Usunięcie wolumenów z określonymi filtrami
docker volume prune --filter "label=project=myapp"
```

#### Volume labeling
```yaml
volumes:
  app_data:
    driver: local
    labels:
      project: myapp
      environment: production
      backup: daily
```

### **Performance optimization**

#### Volume performance tips
```yaml
# Używanie named volumes zamiast bind mounts
services:
  web:
    volumes:
      - app_data:/app/data  # Lepsze niż bind mount
      - ./config:/app/config:ro  # Bind mount dla konfiguracji

# Optymalizacja dla dużych plików
volumes:
  large_data:
    driver: local
    driver_opts:
      type: none
      o: bind
      device: /fast/ssd/path
```

#### Caching strategies
```yaml
services:
  web:
    volumes:
      # Cache w pamięci
      - /app/cache:tmpfs
      
      # Cache na dysku
      - cache_data:/app/cache
      
      # Shared cache między kontenerami
      - shared_cache:/shared/cache

volumes:
  cache_data:
    driver: local
  shared_cache:
    driver: local
```

### **Security considerations**

#### Volume permissions
```bash
# Uruchomienie z określonymi uprawnieniami
docker run -d \
  -v my_data:/app/data \
  --user 1000:1000 \
  myapp:latest

# Ustawienie uprawnień w kontenerze
docker run --rm -v my_data:/data alpine \
  chown -R 1000:1000 /data
```

#### Read-only volumes
```yaml
services:
  web:
    volumes:
      # Konfiguracja read-only
      - ./config:/app/config:ro
      
      # Logi read-only
      - logs_data:/app/logs:ro
      
      # Tymczasowe dane
      - /app/temp:tmpfs
```

#### Volume encryption
```yaml
volumes:
  encrypted_data:
    driver: local
    driver_opts:
      type: crypt
      device: /dev/mapper/encrypted
      keyfile: /path/to/keyfile
```

### **Monitoring i troubleshooting**

#### Volume monitoring
```bash
# Sprawdzenie użycia wolumenów
docker system df -v

# Sprawdzenie wolumenów używanych przez kontener
docker inspect mycontainer | grep -A 10 "Mounts"

# Sprawdzenie wolumenów w docker-compose
docker-compose ps
docker-compose exec web df -h
```

#### Volume troubleshooting
```bash
# Sprawdzenie uprawnień
docker run --rm -v my_data:/data alpine ls -la /data

# Sprawdzenie zawartości
docker run --rm -v my_data:/data alpine find /data -type f

# Sprawdzenie logów systemu plików
docker run --rm -v my_data:/data alpine dmesg | grep -i error
```

#### Volume health checks
```yaml
services:
  web:
    image: myapp:latest
    volumes:
      - app_data:/app/data
    healthcheck:
      test: ["CMD", "test", "-f", "/app/data/health.txt"]
      interval: 30s
      timeout: 10s
      retries: 3
```
