# containers

Ten katalog jest częścią sekcji 07-devops.

## Zawartość katalogu

### Podkatalogi

- [kubernetes/](kubernetes/) - Kubernetes, Helm, Kustomize, Docker Swarm
- [docker/](docker/) - Docker, Dockerfile, Images, Containers, Compose, Volumes, Registry
- [orchestration/](orchestration/) - Orchestration tools and patterns
- [docker-compose/](docker-compose/) - Docker Compose examples and patterns

## Kluczowe tematy

### 🐳 **Docker & Konteneryzacja**
- **Dockerfile** – plik definiujący obraz aplikacji
- **Docker Image** – statyczny snapshot aplikacji + środowisko
- **Docker Container** – uruchomiona instancja obrazu
- **Docker Compose** – orkiestracja lokalna kontenerów (dev/test)
- **Volumes / Bind mounts** – mechanizmy trwałości danych
- **Docker Registry** – repozytorium obrazów (np. Docker Hub)

### ⚓ **Docker Swarm**
- **Swarm Mode** – tryb klastra w Dockerze (wbudowana orkiestracja)
- **Service** – sposób na uruchamianie skalowalnych kontenerów
- **Stack** – grupa usług definiowana przez `docker-compose.yml`
- **Overlay Network** – komunikacja między kontenerami w klastrze
- **Leader Election** – jeden node zarządza planowaniem zadań
- **Secrets / Configs** – zarządzanie poufnymi danymi

### ☸️ **Kubernetes (K8s)**
- **Pod** – najmniejsza jednostka w K8s (zwykle 1 kontener)
- **Deployment** – replikacja i rolling updates
- **Service** – load balancer wewnętrzny/zewnętrzny
- **Ingress** – reverse proxy + routing do usług
- **ConfigMap / Secret** – konfiguracja i hasła
- **Namespace** – izolacja środowisk
- **PersistentVolume / Claim** – trwałość danych
- **Node / Cluster / Master / Worker** – architektura klastra

### 📦 **Helm (Kubernetes package manager)**
- **Chart** – paczka K8s (templatki + wartości)
- **Values.yaml** – konfiguracja użytkownika
- **Templates** – pliki YAML z Go-template do dynamicznych zasobów
- **Releases** – instancje zainstalowanych chartów
- **helm install / upgrade / rollback / uninstall**
- **OCI registry / Chart repository**

### 🔧 **Kustomize (K8s customization tool)**
- **Base** – bazowe zasoby YAML
- **Overlay** – środowiskowe nadpiski (dev/prod/staging)
- **kustomization.yaml** – manifest konfiguracji
- **Patching (JSON6902, strategic merge)**
- **Transformacje (prefix, namespace, labels)**
- **Built-in plugins / generators**

### 🔄 **Strategia migracji do Kubernetes (etapy)**
1. **Dockerize** aplikację (Dockerfile, Compose)
2. **Zautomatyzuj provisioning** (Ansible / Terraform)
3. **Deploy do klastra K8s** (ręcznie / Helm / Kustomize)
4. **Observability i CI/CD** (Prometheus, Grafana, ArgoCD)

### 🔍 **DevOps & praktyki**
- **CI/CD pipelines** – np. GitHub Actions, GitLab CI, Argo
- **IaC (Infrastructure as Code)** – Ansible, Terraform
- **Blue/Green Deployment**, **Canary Releases**
- **Zero downtime deployments**
- **RBAC (Role Based Access Control)**
- **Monitoring & Logging** – Prometheus, Loki, Grafana, ELK
- **Security best practices** – least privilege, secret rotation

