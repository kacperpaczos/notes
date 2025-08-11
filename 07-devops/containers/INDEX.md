# containers

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


Ten katalog jest częścią sekcji 07-devops.

## Zawartość katalogu

### Podkatalogi

- [docker/](docker/) — Docker, Dockerfile, Images, Containers, Volumes, Registry
- [docker-compose/](docker-compose/) — Docker Compose: definicje usług, profile, sieci
- [kubernetes/](kubernetes/) — Kubernetes, Helm, Kustomize
- [orchestration/](orchestration/) — Orkiestracja (Docker Swarm, Nomad, wzorce)

## Kluczowe tematy

### 🐳 **Docker & Konteneryzacja**
- **Dockerfile** — przepis na budowę obrazu
- **Image** — niemutowalny artefakt z warstw (layers), wersjonowany tagami
- **Container** — uruchomiona instancja obrazu (izolacja procesu)
- **Docker Compose** — deklaratywne definiowanie usług (dev/test)
- **Volumes** — trwałość danych zarządzana przez Dockera
- **Bind mounts** — mapowanie katalogów/plików z hosta (kod, konfiguracje)
- **Registry** — rejestr obrazów (np. Docker Hub, GHCR)

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
- **Control Plane / Node (worker)** – architektura klastra

### 📦 **Helm (Kubernetes package manager)**
- **Chart** – paczka K8s (templatki + wartości)
- **Values.yaml** – konfiguracja użytkownika
- **Templates** — pliki YAML z Go templates do dynamicznych zasobów
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
4. **Observability i CI/CD** (Prometheus, Grafana, Argo CD)

### 🔍 **DevOps & praktyki**
- **CI/CD** — GitHub Actions/GitLab CI, Argo CD (GitOps)
- **IaC** — Terraform, Ansible
- **Blue/Green**, **Canary**, **Zero-downtime**
- **RBAC** i polityki (OPA Gatekeeper / Kyverno)
- **Monitoring & Logging** — Prometheus, Loki, Grafana, ELK
- **Security** — Trivy (skan), Cosign (podpisy), SBOM, Pod Security Admission

