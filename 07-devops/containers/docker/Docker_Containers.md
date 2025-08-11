# Docker Containers

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


## 📦 **Docker Container** – uruchomiona instancja obrazu

### Podstawowe operacje na kontenerach

#### Tworzenie i uruchamianie
```bash
# Uruchomienie kontenera w trybie interaktywnym
docker run -it ubuntu:22.04 /bin/bash

# Uruchomienie w tle
docker run -d --name myapp myapp:latest

# Uruchomienie z portami
docker run -d -p 8080:3000 --name webapp myapp:latest

# Uruchomienie z zmiennymi środowiskowymi
docker run -d -e NODE_ENV=production -e PORT=3000 myapp:latest
```

#### Zarządzanie kontenerami
```bash
# Lista uruchomionych kontenerów
docker ps

# Lista wszystkich kontenerów
docker ps -a

# Zatrzymanie kontenera
docker stop myapp

# Uruchomienie zatrzymanego kontenera
docker start myapp

# Restart kontenera
docker restart myapp

# Usunięcie kontenera
docker rm myapp

# Usunięcie wszystkich zatrzymanych kontenerów
docker container prune
```

### 🔄 **Container lifecycle** – create, start, stop, remove

#### Etapy życia kontenera
1. **Created** - kontener utworzony, ale nie uruchomiony
2. **Running** - kontener aktywny i wykonujący procesy
3. **Paused** - kontener wstrzymany (zachowuje stan)
4. **Stopped** - kontener zatrzymany
5. **Removed** - kontener usunięty

#### Komendy lifecycle
```bash
# Tworzenie bez uruchamiania
docker create --name myapp myapp:latest

# Uruchomienie utworzonego kontenera
docker start myapp

# Wstrzymanie kontenera
docker pause myapp

# Wznowienie wstrzymanego kontenera
docker unpause myapp

# Zatrzymanie z timeout
docker stop --time=30 myapp

# Wymuszenie zatrzymania
docker kill myapp
```

### ⚡ **Resource limits** – CPU, memory, I/O constraints

#### Limity CPU
```bash
# Limit CPU (1.0 = 100% jednego CPU)
docker run -d --cpus=0.5 myapp:latest

# Limit CPU z określeniem okresu
docker run -d --cpus=0.5 --cpu-period=100000 --cpu-quota=50000 myapp:latest

# Przypisanie do konkretnych CPU
docker run -d --cpuset-cpus="0,1" myapp:latest
```

#### Limity pamięci
```bash
# Limit pamięci
docker run -d --memory=512m myapp:latest

# Limit pamięci + swap
docker run -d --memory=512m --memory-swap=1g myapp:latest

# Limit pamięci z rezerwacją
docker run -d --memory=512m --memory-reservation=256m myapp:latest
```

#### Limity I/O
```bash
# Limit operacji I/O
docker run -d --device-read-bps=/dev/sda:1mb myapp:latest

# Limit operacji zapisu
docker run -d --device-write-bps=/dev/sda:1mb myapp:latest

# Limit liczby operacji I/O
docker run -d --device-read-iops=/dev/sda:100 myapp:latest
```

### 🌐 **Container networking** – bridge, host, none networks

#### Typy sieci
```bash
# Bridge network (domyślna)
docker run -d --network bridge myapp:latest

# Host network (bezpośredni dostęp do sieci hosta)
docker run -d --network host myapp:latest

# None network (brak dostępu do sieci)
docker run -d --network none myapp:latest

# Custom network
docker network create mynetwork
docker run -d --network mynetwork myapp:latest
```

#### Konfiguracja sieci
```bash
# Uruchomienie z określonym IP
docker run -d --network mynetwork --ip=172.18.0.10 myapp:latest

# Uruchomienie z aliasem
docker run -d --network mynetwork --network-alias=web myapp:latest

# Port forwarding
docker run -d -p 8080:3000 -p 8443:443 myapp:latest

# Port range
docker run -d -p 8080-8090:3000-3010 myapp:latest
```

#### Zarządzanie sieciami
```bash
# Lista sieci
docker network ls

# Inspekcja sieci
docker network inspect bridge

# Usunięcie sieci
docker network rm mynetwork

# Usunięcie nieużywanych sieci
docker network prune
```

### 🔍 **Container inspection** – logs, exec, inspect

#### Logi kontenera
```bash
# Wyświetlenie logów
docker logs myapp

# Logi z timestamp
docker logs -t myapp

# Logi z tail
docker logs --tail=100 myapp

# Logi w czasie rzeczywistym
docker logs -f myapp

# Logi z określonego czasu
docker logs --since="2023-01-01T00:00:00" myapp
```

#### Wykonywanie komend w kontenerze
```bash
# Wykonanie komendy w uruchomionym kontenerze
docker exec -it myapp /bin/bash

# Wykonanie komendy jako root
docker exec -u root myapp whoami

# Wykonanie komendy w określonym katalogu
docker exec -w /app myapp npm install

# Wykonanie komendy z zmiennymi środowiskowymi
docker exec -e DEBUG=1 myapp npm test
```

#### Inspekcja kontenera
```bash
# Szczegółowe informacje o kontenerze
docker inspect myapp

# Konkretne informacje (format JSON)
docker inspect -f '{{.State.Status}}' myapp

# Informacje o sieci
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' myapp

# Informacje o portach
docker inspect -f '{{range $p, $conf := .NetworkSettings.Ports}}{{$p}} -> {{(index $conf 0).HostPort}}{{"\n"}}{{end}}' myapp
```

### 📊 **Metryki i monitoring**

#### Statystyki kontenera
```bash
# Statystyki w czasie rzeczywistym
docker stats

# Statystyki konkretnego kontenera
docker stats myapp

# Statystyki bez stream
docker stats --no-stream

# Statystyki z formatowaniem
docker stats --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}"
```

#### Informacje o procesach
```bash
# Procesy w kontenerze
docker top myapp

# Procesy z dodatkowymi informacjami
docker top myapp -eo pid,ppid,cmd
```

### 🔧 **Zaawansowane operacje**

#### Commit kontenera do obrazu
```bash
# Utworzenie obrazu ze stanu kontenera
docker commit myapp myapp:snapshot

# Commit z metadanymi
docker commit -m "Added new feature" -a "Developer" myapp myapp:v1.1
```

#### Export/Import kontenera
```bash
# Export kontenera do tar
docker export myapp > myapp.tar

# Import z tar do obrazu
docker import myapp.tar myapp:imported
```

#### Copy files to/from container
```bash
# Kopiowanie z hosta do kontenera
docker cp local_file.txt myapp:/app/

# Kopiowanie z kontenera do hosta
docker cp myapp:/app/logs.txt ./local_logs.txt
```

### 🛡️ **Bezpieczeństwo kontenerów**

#### Uruchomienie z ograniczeniami
```bash
# Uruchomienie jako non-root user
docker run -d --user=1000:1000 myapp:latest

# Uruchomienie w read-only mode
docker run -d --read-only myapp:latest

# Uruchomienie z określonymi capabilities
docker run -d --cap-drop=ALL --cap-add=NET_BIND_SERVICE myapp:latest

# Uruchomienie z security options
docker run -d --security-opt=no-new-privileges myapp:latest
```

#### Resource isolation
```bash
# Uruchomienie z określonymi ulimit
docker run -d --ulimit nofile=1024:1024 myapp:latest

# Uruchomienie z określonymi sysctls
docker run -d --sysctl net.core.somaxconn=1024 myapp:latest
```
