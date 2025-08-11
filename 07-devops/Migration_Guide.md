# Przewodnik migracji do konteneryzacji i orkiestracji

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


## 🔄 **Strategia migracji do Kubernetes (etapy)**

### Etap 1: **Dockerize** aplikację (Dockerfile, Compose)

#### Analiza aplikacji
```bash
# Sprawdzenie zależności aplikacji
ldd /usr/bin/myapp
dpkg -l | grep -E "(python|node|java|nginx)"

# Analiza konfiguracji
find /etc -name "*myapp*" -type f
find /var/log -name "*myapp*" -type f

# Sprawdzenie portów
netstat -tlnp | grep myapp
ss -tlnp | grep myapp
```

#### Tworzenie Dockerfile
```dockerfile
# Multi-stage build dla optymalizacji
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

FROM node:18-alpine AS production
WORKDIR /app
COPY --from=builder /app/node_modules ./node_modules
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

#### Docker Compose dla development
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=development
      - DATABASE_URL=postgresql://user:pass@db:5432/myapp
    depends_on:
      - db
    volumes:
      - .:/app
      - /app/node_modules

  db:
    image: postgres:13
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

### Etap 2: **Zautomatyzuj provisioning** (Ansible / Terraform)

#### Ansible playbook dla infrastruktury
```yaml
---
- name: Provision Kubernetes cluster
  hosts: k8s_nodes
  become: yes
  tasks:
    - name: Update package cache
      apt:
        update_cache: yes
        cache_valid_time: 3600

    - name: Install Docker
      apt:
        name:
          - docker.io
          - docker-compose
        state: present

    - name: Install Kubernetes components
      apt:
        name:
          - kubelet
          - kubeadm
          - kubectl
        state: present

    - name: Configure Docker daemon
      copy:
        content: |
          {
            "exec-opts": ["native.cgroupdriver=systemd"],
            "log-driver": "json-file",
            "log-opts": {
              "max-size": "100m"
            },
            "storage-driver": "overlay2"
          }
        dest: /etc/docker/daemon.json
      notify: restart docker

    - name: Enable and start kubelet
      systemd:
        name: kubelet
        enabled: yes
        state: started
```

#### Terraform dla infrastruktury chmurowej
```hcl
# main.tf
provider "aws" {
  region = "us-west-2"
}

resource "aws_eks_cluster" "main" {
  name     = "myapp-cluster"
  role_arn = aws_iam_role.eks_cluster.arn
  version  = "1.27"

  vpc_config {
    subnet_ids = aws_subnet.eks[*].id
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_cluster_policy
  ]
}

resource "aws_eks_node_group" "main" {
  cluster_name    = aws_eks_cluster.main.name
  node_group_name = "myapp-nodes"
  node_role_arn   = aws_iam_role.eks_nodes.arn
  subnet_ids      = aws_subnet.eks[*].id

  scaling_config {
    desired_size = 3
    max_size     = 5
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_policy
  ]
}
```

### Etap 3: **Deploy do klastra K8s** (ręcznie / Helm / Kustomize)

#### Podstawowe manifesty Kubernetes
```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
  labels:
    app: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: myapp:latest
        ports:
        - containerPort: 3000
        env:
        - name: DATABASE_URL
          valueFrom:
            secretKeyRef:
              name: myapp-secrets
              key: database-url
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
```

#### Helm chart
```yaml
# values.yaml
replicaCount: 3
image:
  repository: myapp
  tag: "1.0.0"
  pullPolicy: IfNotPresent

service:
  type: ClusterIP
  port: 80
  targetPort: 3000

ingress:
  enabled: true
  className: nginx
  hosts:
    - host: myapp.example.com
      paths:
        - path: /
          pathType: Prefix

resources:
  limits:
    cpu: 500m
    memory: 512Mi
  requests:
    cpu: 250m
    memory: 256Mi
```

#### Kustomize overlays
```yaml
# base/kustomization.yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

resources:
- deployment.yaml
- service.yaml
- configmap.yaml

commonLabels:
  app: myapp
```

```yaml
# overlays/production/kustomization.yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

resources:
- ../../base

namespace: production

patches:
- target:
    kind: Deployment
    name: myapp
  patch: |-
    - op: replace
      path: /spec/replicas
      value: 5

configMapGenerator:
- name: myapp-config
  literals:
  - environment=production
  - log_level=info
```

### Etap 4: **Observability i CI/CD** (Prometheus, Grafana, Argo CD)

#### Monitoring z Prometheus
```yaml
# prometheus-config.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: prometheus-config
data:
  prometheus.yml: |
    global:
      scrape_interval: 15s
    scrape_configs:
    - job_name: 'kubernetes-pods'
      kubernetes_sd_configs:
      - role: pod
      relabel_configs:
      - source_labels: [__meta_kubernetes_pod_annotation_prometheus_io_scrape]
        action: keep
        regex: true
      - source_labels: [__meta_kubernetes_pod_annotation_prometheus_io_path]
        action: replace
        target_label: __metrics_path__
        regex: (.+)
      - source_labels: [__address__, __meta_kubernetes_pod_annotation_prometheus_io_port]
        action: replace
        regex: ([^:]+)(?::\d+)?;(\d+)
        replacement: $1:$2
        target_label: __address__
```

#### CI/CD z GitHub Actions
```yaml
# .github/workflows/deploy.yml
name: Deploy to Kubernetes
on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3

    - name: Build Docker image
      run: |
        docker build -t myapp:${{ github.sha }} .
        docker tag myapp:${{ github.sha }} myapp:latest

    - name: Push to registry
      run: |
        echo ${{ secrets.REGISTRY_PASSWORD }} | docker login -u ${{ secrets.REGISTRY_USERNAME }} --password-stdin
        docker push myapp:${{ github.sha }}
        docker push myapp:latest

    - name: Deploy to Kubernetes
      run: |
        echo "${{ secrets.KUBECONFIG }}" | base64 -d > kubeconfig
        export KUBECONFIG=kubeconfig
        kubectl set image deployment/myapp myapp=myapp:${{ github.sha }}
```

#### GitOps z Argo CD
```yaml
# argocd-app.yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: myapp
  namespace: argocd
spec:
  project: default
  source:
    repoURL: https://github.com/myorg/myapp-k8s
    targetRevision: HEAD
    path: k8s
  destination:
    server: https://kubernetes.default.svc
    namespace: production
  syncPolicy:
    automated:
      prune: true
      selfHeal: true
    syncOptions:
    - CreateNamespace=true
```

## 🚀 **Strategie migracji**

### Migracja stopniowa (Blue-Green)

#### Etap 1: Przygotowanie
```bash
# Backup danych
pg_dump myapp > backup.sql
tar czf config_backup.tar.gz /etc/myapp/

# Przygotowanie nowego środowiska
kubectl create namespace myapp-new
kubectl apply -f k8s/ -n myapp-new
```

#### Etap 2: Testowanie
```bash
# Sprawdzenie nowego środowiska
kubectl get pods -n myapp-new
kubectl logs -f deployment/myapp -n myapp-new

# Test funkcjonalności
curl -H "Host: myapp-new.example.com" http://localhost
```

#### Etap 3: Przełączenie
```bash
# Zmiana DNS
kubectl patch ingress myapp -p '{"spec":{"rules":[{"host":"myapp.example.com"}]}}'

# Monitorowanie
kubectl get pods -n myapp-new -w
```

### Migracja z zachowaniem stanu

#### Migracja bazy danych
```yaml
# database-migration-job.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: db-migration
spec:
  template:
    spec:
      containers:
      - name: migration
        image: postgres:13
        command:
        - /bin/sh
        - -c
        - |
          pg_dump -h old-db-host -U user -d myapp | \
          psql -h new-db-host -U user -d myapp
        env:
        - name: PGPASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secrets
              key: password
      restartPolicy: Never
  backoffLimit: 3
```

#### Migracja plików
```yaml
# file-migration-job.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: file-migration
spec:
  template:
    spec:
      containers:
      - name: migration
        image: alpine
        command:
        - /bin/sh
        - -c
        - |
          rsync -av /mnt/old-files/ /mnt/new-files/
        volumeMounts:
        - name: old-files
          mountPath: /mnt/old-files
        - name: new-files
          mountPath: /mnt/new-files
      volumes:
      - name: old-files
        hostPath:
          path: /var/lib/myapp/files
      - name: new-files
        persistentVolumeClaim:
          claimName: myapp-files-pvc
      restartPolicy: Never
```

## 🔧 **Narzędzia i automatyzacja**

### Skrypty migracji
```bash
#!/bin/bash
# migrate.sh

set -e

echo "Starting migration..."

# Backup
echo "Creating backup..."
pg_dump myapp > backup_$(date +%Y%m%d_%H%M%S).sql

# Build new image
echo "Building Docker image..."
docker build -t myapp:new .

# Deploy to staging
echo "Deploying to staging..."
kubectl apply -f k8s/staging/

# Wait for deployment
echo "Waiting for deployment..."
kubectl rollout status deployment/myapp -n staging

# Run tests
echo "Running tests..."
kubectl run test --image=busybox --rm -it --restart=Never -- \
  wget -O- http://myapp-staging:3000/health

# Deploy to production
echo "Deploying to production..."
kubectl apply -f k8s/production/

# Cleanup
echo "Cleaning up..."
kubectl delete -f k8s/staging/

echo "Migration completed!"
```

### Monitoring migracji
```yaml
# migration-monitoring.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: migration-dashboard
data:
  dashboard.json: |
    {
      "dashboard": {
        "title": "Migration Dashboard",
        "panels": [
          {
            "title": "Old vs New Response Time",
            "type": "graph",
            "targets": [
              {
                "expr": "rate(http_request_duration_seconds_sum[5m])",
                "legendFormat": "{{pod}}"
              }
            ]
          },
          {
            "title": "Error Rate",
            "type": "graph",
            "targets": [
              {
                "expr": "rate(http_requests_total{status=~\"5..\"}[5m])",
                "legendFormat": "{{pod}}"
              }
            ]
          }
        ]
      }
    }
```

## 📊 **Metryki sukcesu**

### Kluczowe wskaźniki (KPIs)
- **Czas migracji**: < 4 godziny
- **Downtime**: < 5 minut
- **Błędy**: 0 krytycznych
- **Wydajność**: Brak degradacji > 5%
- **Koszt**: Redukcja o 30-50%

### Monitoring post-migracji
```yaml
# post-migration-checks.yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: migration-health-check
spec:
  schedule: "*/5 * * * *"
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: health-check
            image: curlimages/curl
            command:
            - /bin/sh
            - -c
            - |
              # Check application health
              curl -f http://myapp:3000/health || exit 1
              
              # Check database connectivity
              curl -f http://myapp:3000/db-health || exit 1
              
              # Check external dependencies
              curl -f http://myapp:3000/deps-health || exit 1
          restartPolicy: OnFailure
```

## 🛡️ **Bezpieczeństwo i compliance**

### Audyt migracji
```yaml
# audit-policy.yaml
apiVersion: audit.k8s.io/v1
kind: Policy
rules:
- level: RequestResponse
  resources:
  - group: ""
    resources: ["pods", "services", "configmaps", "secrets"]
- level: Metadata
  resources:
  - group: ""
    resources: ["namespaces", "nodes"]
```

### Backup i disaster recovery
```bash
#!/bin/bash
# backup-strategy.sh

# Backup Kubernetes resources
kubectl get all -o yaml > k8s-backup-$(date +%Y%m%d).yaml

# Backup persistent volumes
kubectl get pvc -o yaml > pvc-backup-$(date +%Y%m%d).yaml

# Backup secrets
kubectl get secrets -o yaml > secrets-backup-$(date +%Y%m%d).yaml

# Backup databases
kubectl exec deployment/postgres -- pg_dump myapp > db-backup-$(date +%Y%m%d).sql

# Upload to secure storage
aws s3 cp *.yaml s3://myapp-backups/
aws s3 cp *.sql s3://myapp-backups/
```
