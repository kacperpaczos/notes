# Kubernetes (K8s)

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


Ten katalog jest częścią sekcji containers.

## Zawartość katalogu

- [Kubernetes Core](Kubernetes_Core.md) - Podstawowe koncepcje K8s
- [Kubernetes Objects](Kubernetes_Objects.md) - Obiekty K8s (Pods, Deployments, Services)
- [Kubernetes Networking](Kubernetes_Networking.md) - Sieci w K8s
- [Kubernetes Storage](Kubernetes_Storage.md) - Zarządzanie storage w K8s
- [Kubernetes Security](Kubernetes_Security.md) - Bezpieczeństwo w K8s
- [helm/](helm/) - Kubernetes package manager
  - [Helm Basics](helm/Helm_Basics.md) - Podstawy Helm
  - [Helm Charts](helm/Helm_Charts.md) - Tworzenie i używanie chartów
- [kustomize/](kustomize/) - K8s customization tool
  - [Kustomize Basics](kustomize/Kustomize_Basics.md) - Podstawy Kustomize
  - [Kustomize Overlays](kustomize/Kustomize_Overlays.md) - Overlay patterns
  

## Kluczowe tematy

### ☸️ **Kubernetes (K8s)**

#### **Pod** – najmniejsza jednostka w K8s (zwykle 1 kontener)
- **Pod lifecycle** - Pending, Running, Succeeded, Failed
- **Pod scheduling** - node affinity, taints, tolerations
- **Pod security** - security contexts, pod security standards
- **Multi-container pods** - sidecar, ambassador, adapter patterns

#### **Deployment** – replikacja i rolling updates
- **Replica management** - desired state vs actual state
- **Rolling updates** - zero-downtime deployments
- **Rollback strategies** - quick rollback to previous version
- **Update strategies** - RollingUpdate vs Recreate

#### **Service** – load balancer wewnętrzny/zewnętrzny
- **Service types** - ClusterIP, NodePort, LoadBalancer, ExternalName
- **Service discovery** - DNS-based service discovery
- **Load balancing** - round-robin, session affinity
- **Service mesh** - Istio, Linkerd integration

#### **Ingress** – reverse proxy + routing do usług
- **Ingress controllers** - nginx, traefik, haproxy
- **TLS termination** - SSL/TLS certificate management
- **Path-based routing** - URL path routing rules
- **Host-based routing** - virtual host routing

#### **ConfigMap / Secret** – konfiguracja i hasła
- **ConfigMap** - non-sensitive configuration data
- **Secret** - sensitive data (passwords, tokens, keys)
- **Secret types** - Opaque, kubernetes.io/service-account-token, etc.
- **Secret management** - external secret operators

#### **Namespace** – izolacja środowisk
- **Resource quotas** - CPU, memory, storage limits
- **Network policies** - pod-to-pod communication rules
- **RBAC** - role-based access control per namespace
- **Default namespaces** - default, kube-system, kube-public

#### **PersistentVolume / Claim** – trwałość danych
- **Storage classes** - dynamic provisioning
- **Volume modes** - Filesystem vs Block
- **Access modes** - ReadWriteOnce, ReadOnlyMany, ReadWriteMany
- **Volume snapshots** - backup and restore capabilities

#### **Control Plane / Node (worker)** – architektura klastra
- **Control plane** - API Server, Scheduler, Controller Manager, etcd
- **Worker nodes** - kubelet, kube-proxy, container runtime
- **Node management** - node registration, taints, cordoning
- **Cluster scaling** - horizontal and vertical scaling

#### **Kubelet / API Server / Scheduler / Controller Manager**
- **Kubelet** - primary node agent
- **API Server** - cluster's front-end
- **Scheduler** - pod placement decisions
- **Controller Manager** - reconciliation loops

### 📦 **Helm (Kubernetes package manager)**

#### **Chart** – paczka K8s (templatki + wartości)
- **Chart structure** - Chart.yaml, values.yaml, templates/
- **Chart dependencies** - requirements.yaml
- **Chart metadata** - version, description, maintainers
- **Chart repositories** - Helm Hub, custom repos

#### **Values.yaml** – konfiguracja użytkownika
- **Default values** - chart default configuration
- **Value overrides** - --set, --values, -f flags
- **Value types** - strings, numbers, booleans, arrays, objects
- **Value validation** - schema validation

#### **Templates** – pliki YAML z Go templates do dynamicznych zasobów
- **Template functions** - built-in and custom functions
- **Template pipelines** - chaining functions
- **Template conditionals** - if/else statements
- **Template loops** - range loops

#### **Releases** – instancje zainstalowanych chartów
- **Release lifecycle** - install, upgrade, rollback, uninstall
- **Release history** - tracking changes
- **Release hooks** - pre/post install/upgrade/delete
- **Release testing** - test hooks and validation

#### **helm install / upgrade / rollback / uninstall**
- **Install** - deploying charts to cluster
- **Upgrade** - updating existing releases
- **Rollback** - reverting to previous versions
- **Uninstall** - removing releases from cluster

#### **OCI registry / Chart repository**
- **OCI compatibility** - Open Container Initiative
- **Chart repositories** - HTTP/HTTPS repositories
- **Chart signing** - cryptographic verification
- **Chart provenance** - origin and integrity

### 🔧 **Kustomize (K8s customization tool)**

#### **Base** – bazowe zasoby YAML
- **Base structure** - kustomization.yaml, resources
- **Base composition** - multiple resource files
- **Base validation** - schema validation
- **Base testing** - kustomize build validation

#### **Overlay** – środowiskowe nadpiski (dev/prod/staging)
- **Environment overlays** - dev, staging, prod
- **Feature overlays** - feature flags, configurations
- **Regional overlays** - multi-region deployments
- **Overlay composition** - multiple overlays

#### **kustomization.yaml** – manifest konfiguracji
- **Resources** - list of resource files
- **Patches** - strategic merge patches
- **Transformers** - name prefix, namespace, labels
- **Generators** - configMapGenerator, secretGenerator

#### **Patching (JSON6902, strategic merge)**
- **Strategic merge patches** - intelligent merging
- **JSON patch** - RFC 6902 JSON Patch
- **Patch targets** - specific resources and fields
- **Patch validation** - schema validation

#### **Transformacje (prefix, namespace, labels)**
- **Name prefixing** - adding prefixes to resource names
- **Namespace transformation** - changing namespaces
- **Label transformation** - adding/modifying labels
- **Annotation transformation** - adding/modifying annotations

#### **Built-in plugins / generators**
- **ConfigMap generator** - generating ConfigMaps from files
- **Secret generator** - generating Secrets from files
- **Image transformer** - updating image references
- **Replica count transformer** - updating replica counts

### ⚓ **Docker Swarm**

#### **Swarm Mode** – tryb klastra w Dockerze (wbudowana orkiestracja)
- **Swarm initialization** - docker swarm init
- **Swarm join** - adding nodes to swarm
- **Swarm leave** - removing nodes from swarm
- **Swarm update** - updating swarm configuration

#### **Service** – sposób na uruchamianie skalowalnych kontenerów
- **Service creation** - docker service create
- **Service scaling** - docker service scale
- **Service update** - docker service update
- **Service rollback** - docker service rollback

#### **Stack** – grupa usług definiowana przez `docker-compose.yml`
- **Stack deployment** - docker stack deploy
- **Stack management** - docker stack ls, ps, rm
- **Stack services** - managing multiple services
- **Stack configuration** - environment-specific configs

#### **Overlay Network** – komunikacja między kontenerami w klastrze
- **Network creation** - docker network create --driver overlay
- **Network discovery** - automatic service discovery
- **Network security** - encrypted communication
- **Network isolation** - service-to-service communication

#### **Leader Election** – jeden node zarządza planowaniem zadań
- **Manager nodes** - control plane nodes
- **Worker nodes** - compute nodes
- **Leader election** - automatic failover
- **Node failure** - automatic recovery

#### **Secrets / Configs** – zarządzanie poufnymi danymi
- **Secret management** - docker secret create
- **Config management** - docker config create
- **Secret rotation** - updating secrets
- **Secret access** - service access to secrets

### 🔄 **Strategia migracji do Kubernetes (etapy)**

#### 1. **Dockerize** aplikację (Dockerfile, Compose)
- **Containerization** - creating Docker images
- **Multi-stage builds** - optimizing image size
- **Docker Compose** - local development setup
- **Health checks** - application health monitoring

#### 2. **Zautomatyzuj provisioning** (Ansible / Terraform)
- **Infrastructure as Code** - automated provisioning
- **Configuration management** - consistent environments
- **Environment parity** - dev/staging/prod consistency
- **Automated testing** - CI/CD integration

#### 3. **Deploy do klastra K8s** (ręcznie / Helm / Kustomize)
- **Manual deployment** - kubectl apply
- **Helm charts** - templated deployments
- **Kustomize overlays** - environment-specific configs
- **GitOps** - declarative deployments

#### 4. **Observability i CI/CD** (Prometheus, Grafana, Argo CD)
- **Monitoring** - Prometheus, Grafana
- **Logging** - ELK stack, Loki
- **Tracing** - Jaeger, Zipkin
- **CI/CD** - Argo CD, Flux, Tekton

