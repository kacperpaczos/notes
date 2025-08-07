# Docker Registry

## 🏪 **Docker Registry** – repozytorium obrazów (np. Docker Hub)

### **Docker Hub** – publiczne repozytorium

#### Podstawowe operacje
```bash
# Logowanie do Docker Hub
docker login

# Logowanie z określonym użytkownikiem
docker login -u username

# Wylogowanie
docker logout

# Sprawdzenie statusu logowania
docker info | grep Username
```

#### Pushing i pulling obrazów
```bash
# Tagowanie obrazu dla Docker Hub
docker tag myapp:latest username/myapp:latest

# Push obrazu do Docker Hub
docker push username/myapp:latest

# Pull obrazu z Docker Hub
docker pull username/myapp:latest

# Pull konkretnej wersji
docker pull username/myapp:v1.0.0
```

#### Organizacje i zespoły
```bash
# Tagowanie dla organizacji
docker tag myapp:latest myorg/myapp:latest

# Push do organizacji
docker push myorg/myapp:latest

# Pull z organizacji
docker pull myorg/myapp:latest
```

### **Private registries** – własne repozytoria

#### Uruchomienie prywatnego registry
```bash
# Uruchomienie podstawowego registry
docker run -d \
  -p 5000:5000 \
  --name registry \
  registry:2

# Uruchomienie z persistent storage
docker run -d \
  -p 5000:5000 \
  -v registry_data:/var/lib/registry \
  --name registry \
  registry:2

# Uruchomienie z TLS
docker run -d \
  -p 5000:5000 \
  -v registry_data:/var/lib/registry \
  -v /path/to/certs:/certs \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  --name registry \
  registry:2
```

#### Konfiguracja w docker-compose.yml
```yaml
version: '3.8'
services:
  registry:
    image: registry:2
    ports:
      - "5000:5000"
    environment:
      REGISTRY_STORAGE_DELETE_ENABLED: "true"
      REGISTRY_STORAGE_FILESYSTEM_MAXTHREADS: "100"
    volumes:
      - registry_data:/var/lib/registry
      - ./config.yml:/etc/docker/registry/config.yml
    restart: unless-stopped

volumes:
  registry_data:
```

#### Konfiguracja registry (config.yml)
```yaml
version: 0.1
log:
  level: debug
storage:
  delete:
    enabled: true
  cache:
    blobdescriptor: inmemory
  filesystem:
    rootdirectory: /var/lib/registry
http:
  addr: :5000
  headers:
    X-Content-Type-Options: [nosniff]
health:
  storagedriver:
    enabled: true
    interval: 10s
    threshold: 3
```

### **Image tagging** – wersjonowanie obrazów

#### Strategie tagowania
```bash
# Tagowanie z wersją
docker tag myapp:latest myapp:v1.0.0

# Tagowanie z datą
docker tag myapp:latest myapp:$(date +%Y%m%d)

# Tagowanie z commit hash
docker tag myapp:latest myapp:$(git rev-parse --short HEAD)

# Tagowanie z branch
docker tag myapp:latest myapp:$(git branch --show-current)

# Tagowanie z environment
docker tag myapp:latest myapp:production
docker tag myapp:latest myapp:staging
```

#### Multi-arch tagging
```bash
# Tagowanie dla różnych architektur
docker buildx build --platform linux/amd64,linux/arm64 \
  -t myapp:latest \
  --push .

# Sprawdzenie architektur obrazu
docker manifest inspect myapp:latest
```

#### Tagging best practices
```bash
# Semantic versioning
docker tag myapp:latest myapp:1.2.3
docker tag myapp:latest myapp:1.2
docker tag myapp:latest myapp:1

# Environment tags
docker tag myapp:latest myapp:dev
docker tag myapp:latest myapp:test
docker tag myapp:latest myapp:prod

# Feature tags
docker tag myapp:latest myapp:feature-auth
docker tag myapp:latest myapp:bugfix-login
```

### **Registry security** – uwierzytelnianie i autoryzacja

#### Basic authentication
```bash
# Tworzenie pliku htpasswd
htpasswd -Bbn username password > htpasswd

# Uruchomienie registry z auth
docker run -d \
  -p 5000:5000 \
  -v registry_data:/var/lib/registry \
  -v $(pwd)/htpasswd:/htpasswd \
  -e REGISTRY_AUTH=htpasswd \
  -e REGISTRY_AUTH_HTPASSWD_REALM="Registry Realm" \
  -e REGISTRY_AUTH_HTPASSWD_PATH=/htpasswd \
  --name registry \
  registry:2
```

#### TLS/SSL configuration
```bash
# Generowanie certyfikatów
openssl req -newkey rsa:4096 -nodes -sha256 \
  -keyout domain.key -x509 -days 365 \
  -out domain.crt

# Uruchomienie z TLS
docker run -d \
  -p 5000:5000 \
  -v registry_data:/var/lib/registry \
  -v $(pwd):/certs \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  --name registry \
  registry:2
```

#### Registry z reverse proxy (nginx)
```nginx
# nginx.conf
upstream docker-registry {
  server registry:5000;
}

server {
  listen 443 ssl;
  server_name registry.example.com;

  ssl_certificate /etc/nginx/ssl/domain.crt;
  ssl_certificate_key /etc/nginx/ssl/domain.key;

  client_max_body_size 0;
  chunked_transfer_encoding on;

  location / {
    auth_basic "Registry realm";
    auth_basic_user_file /etc/nginx/htpasswd;
    proxy_pass http://docker-registry;
    proxy_set_header Host $http_host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_read_timeout 900;
    proxy_connect_timeout 900;
  }
}
```

### **Image scanning** – bezpieczeństwo obrazów

#### Skanowanie podatności
```bash
# Skanowanie obrazu
docker scan myapp:latest

# Skanowanie z określonymi opcjami
docker scan --severity high myapp:latest

# Skanowanie z wyjściem JSON
docker scan --json myapp:latest

# Skanowanie z ignorowaniem CVEs
docker scan --severity high --exclude-base myapp:latest
```

#### Integracja z CI/CD
```yaml
# .github/workflows/security-scan.yml
name: Security Scan
on: [push, pull_request]
jobs:
  scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Build image
        run: docker build -t myapp .
      - name: Scan image
        run: docker scan --severity high myapp
```

#### Trivy scanner
```bash
# Instalacja Trivy
wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | sudo apt-key add -
echo deb https://aquasecurity.github.io/trivy-repo/deb bionic main | sudo tee -a /etc/apt/sources.list.d/trivy.list
sudo apt-get update
sudo apt-get install trivy

# Skanowanie obrazu
trivy image myapp:latest

# Skanowanie z określonymi opcjami
trivy image --severity HIGH,CRITICAL myapp:latest

# Skanowanie z wyjściem JSON
trivy image -f json myapp:latest
```

### **Registry management** – zarządzanie repozytorium

#### Registry API
```bash
# Lista repozytoriów
curl -X GET http://localhost:5000/v2/_catalog

# Lista tagów dla repozytorium
curl -X GET http://localhost:5000/v2/myapp/tags/list

# Manifest dla tagu
curl -X GET http://localhost:5000/v2/myapp/manifests/latest

# Usunięcie manifestu
curl -X DELETE http://localhost:5000/v2/myapp/manifests/sha256:abc123
```

#### Garbage collection
```bash
# Uruchomienie garbage collection
docker exec registry registry garbage-collect /etc/docker/registry/config.yml

# Garbage collection z dry-run
docker exec registry registry garbage-collect --dry-run /etc/docker/registry/config.yml

# Garbage collection z określonymi opcjami
docker exec registry registry garbage-collect \
  --delete-untagged \
  /etc/docker/registry/config.yml
```

#### Registry monitoring
```bash
# Sprawdzenie statusu registry
curl -X GET http://localhost:5000/v2/

# Sprawdzenie health endpoint
curl -X GET http://localhost:5000/v2/_catalog

# Sprawdzenie metryk (jeśli włączone)
curl -X GET http://localhost:5000/metrics
```

### **Cloud registries** – rejestry w chmurze

#### AWS ECR
```bash
# Logowanie do ECR
aws ecr get-login-password --region us-west-2 | \
  docker login --username AWS --password-stdin \
  123456789012.dkr.ecr.us-west-2.amazonaws.com

# Tworzenie repozytorium
aws ecr create-repository --repository-name myapp

# Tagowanie dla ECR
docker tag myapp:latest \
  123456789012.dkr.ecr.us-west-2.amazonaws.com/myapp:latest

# Push do ECR
docker push \
  123456789012.dkr.ecr.us-west-2.amazonaws.com/myapp:latest
```

#### Google Container Registry (GCR)
```bash
# Logowanie do GCR
gcloud auth configure-docker

# Tagowanie dla GCR
docker tag myapp:latest gcr.io/my-project/myapp:latest

# Push do GCR
docker push gcr.io/my-project/myapp:latest
```

#### Azure Container Registry (ACR)
```bash
# Logowanie do ACR
az acr login --name myregistry

# Tagowanie dla ACR
docker tag myapp:latest myregistry.azurecr.io/myapp:latest

# Push do ACR
docker push myregistry.azurecr.io/myapp:latest
```

### **Registry best practices**

#### Organizacja obrazów
```bash
# Struktura nazewnictwa
myorg/app-name:version
myorg/app-name:environment
myorg/app-name:feature

# Przykłady
mycompany/webapp:v1.2.3
mycompany/webapp:production
mycompany/webapp:feature-auth
```

#### Lifecycle management
```bash
# Automatyczne usuwanie starych tagów
docker run --rm \
  -v /var/run/docker.sock:/var/run/docker.sock \
  registry-cleaner \
  --registry-url http://localhost:5000 \
  --repository myapp \
  --keep-tags 10 \
  --older-than 30d
```

#### Backup strategies
```bash
# Backup registry data
docker run --rm \
  -v registry_data:/registry \
  -v $(pwd):/backup \
  alpine tar czf /backup/registry_backup.tar.gz -C /registry .

# Restore registry data
docker run --rm \
  -v registry_data:/registry \
  -v $(pwd):/backup \
  alpine tar xzf /backup/registry_backup.tar.gz -C /registry
```
