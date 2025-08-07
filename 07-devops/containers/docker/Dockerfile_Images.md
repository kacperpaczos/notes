# Dockerfile & Images

## 🐳 **Dockerfile** – plik definiujący obraz aplikacji

### Podstawowa struktura Dockerfile

```dockerfile
# Base image
FROM node:18-alpine

# Metadata
LABEL maintainer="dev@example.com"
LABEL version="1.0"

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --only=production

# Copy application code
COPY . .

# Expose port
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:3000/health || exit 1

# Default command
CMD ["npm", "start"]
```

### Kluczowe instrukcje Dockerfile

#### **FROM** - Obraz bazowy
```dockerfile
FROM ubuntu:22.04
FROM node:18-alpine
FROM python:3.11-slim
```

#### **COPY vs ADD**
```dockerfile
# COPY - podstawowe kopiowanie plików
COPY src/ /app/src/

# ADD - z dodatkowymi funkcjami (rozpakowywanie, URL)
ADD archive.tar.gz /app/
ADD https://example.com/file.txt /app/
```

#### **RUN** - Wykonywanie komend
```dockerfile
# Łączenie komend (mniej warstw)
RUN apt-get update && \
    apt-get install -y package1 package2 && \
    rm -rf /var/lib/apt/lists/*

# Używanie cache dla npm
COPY package*.json ./
RUN npm ci --only=production
COPY . .
```

#### **ENV** - Zmienne środowiskowe
```dockerfile
ENV NODE_ENV=production
ENV PORT=3000
ENV APP_VERSION=1.0.0
```

#### **ARG** - Argumenty build-time
```dockerfile
ARG VERSION=latest
ARG BUILD_DATE
FROM node:${VERSION}
```

### 📦 **Docker Image** – statyczny snapshot aplikacji + środowisko

#### Warstwy obrazu (Layers)
- **Union File System** - każda instrukcja tworzy nową warstwę
- **Copy-on-Write** - współdzielenie warstw między obrazami
- **Immutable** - obrazy są niezmienne po utworzeniu

#### Tagowanie obrazów
```bash
# Podstawowe tagowanie
docker build -t myapp:1.0 .

# Tagowanie z registry
docker build -t registry.example.com/myapp:1.0 .

# Tagowanie z datą
docker build -t myapp:$(date +%Y%m%d) .

# Tagowanie jako latest
docker tag myapp:1.0 myapp:latest
```

### 🔧 **Multi-stage builds** – optymalizacja rozmiaru obrazów

```dockerfile
# Build stage
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Production stage
FROM node:18-alpine AS production
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY --from=builder /app/dist ./dist
EXPOSE 3000
CMD ["npm", "start"]
```

### ⚡ **Layer caching** – strategie optymalizacji buildów

#### Najlepsze praktyki
1. **Kopiuj zależności przed kodem**
```dockerfile
COPY package*.json ./
RUN npm install
COPY . .
```

2. **Używaj .dockerignore**
```dockerignore
node_modules
npm-debug.log
.git
.gitignore
README.md
.env
```

3. **Łącz komendy RUN**
```dockerfile
# Źle - 3 warstwy
RUN apt-get update
RUN apt-get install -y package1
RUN apt-get install -y package2

# Dobrze - 1 warstwa
RUN apt-get update && \
    apt-get install -y package1 package2 && \
    rm -rf /var/lib/apt/lists/*
```

### 🏗️ **Base images** – wybór odpowiednich obrazów bazowych

#### Typy obrazów bazowych
- **Alpine** - minimalny rozmiar (~5MB)
- **Slim** - zredukowany rozmiar
- **Full** - kompletne środowisko
- **Distroless** - tylko runtime, brak shell

#### Przykłady
```dockerfile
# Alpine - minimalny
FROM node:18-alpine

# Slim - zredukowany
FROM python:3.11-slim

# Distroless - tylko runtime
FROM gcr.io/distroless/nodejs18-debian11
```

### 🔍 **Inspekcja obrazów**

```bash
# Historia obrazu
docker history myapp:latest

# Inspekcja szczegółów
docker inspect myapp:latest

# Rozmiar obrazów
docker images

# Usuwanie nieużywanych obrazów
docker image prune
```

### 🚀 **Build context i optymalizacja**

```bash
# Build z kontekstem
docker build -t myapp:latest .

# Build z argumentami
docker build --build-arg VERSION=1.0 -t myapp:1.0 .

# Build z cache
docker build --no-cache -t myapp:latest .

# Build z target stage
docker build --target builder -t myapp:builder .
```

### 📊 **Metryki obrazów**

#### Rozmiar obrazu
```bash
# Sprawdzenie rozmiaru
docker images --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}"

# Analiza warstw
docker history --no-trunc myapp:latest
```

#### Bezpieczeństwo
```bash
# Skanowanie podatności
docker scan myapp:latest

# Sprawdzenie podpisów
docker trust inspect myapp:latest
```
