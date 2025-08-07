# Helm Basics

## 📦 **Helm (Kubernetes package manager)** – zarządzanie aplikacjami w K8s

### **Chart** – paczka K8s (templatki + wartości)

#### Struktura chartu
```
myapp/
├── Chart.yaml          # Metadane chartu
├── values.yaml         # Domyślne wartości
├── charts/             # Zależności
├── templates/          # Szablony YAML
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── _helpers.tpl
├── .helmignore         # Pliki do ignorowania
└── README.md           # Dokumentacja
```

#### Chart.yaml
```yaml
apiVersion: v2
name: myapp
description: A Helm chart for My Application
type: application
version: 0.1.0
appVersion: "1.0.0"
keywords:
  - web
  - api
home: https://github.com/myorg/myapp
sources:
  - https://github.com/myorg/myapp
maintainers:
  - name: Developer
    email: dev@example.com
dependencies:
  - name: postgresql
    version: 12.x.x
    repository: https://charts.bitnami.com/bitnami
```

### **Values.yaml** – konfiguracja użytkownika

#### Domyślne wartości
```yaml
# Globalne ustawienia
global:
  environment: production
  domain: example.com

# Konfiguracja aplikacji
image:
  repository: myapp
  tag: "1.0.0"
  pullPolicy: IfNotPresent

# Konfiguracja deploymentu
replicaCount: 3

# Konfiguracja serwisu
service:
  type: ClusterIP
  port: 80
  targetPort: 3000

# Konfiguracja ingress
ingress:
  enabled: true
  className: nginx
  hosts:
    - host: myapp.example.com
      paths:
        - path: /
          pathType: Prefix
  tls:
    - secretName: myapp-tls
      hosts:
        - myapp.example.com

# Konfiguracja zasobów
resources:
  limits:
    cpu: 500m
    memory: 512Mi
  requests:
    cpu: 250m
    memory: 256Mi

# Konfiguracja persistent volume
persistence:
  enabled: true
  size: 10Gi
  storageClass: ""

# Konfiguracja secrets
secrets:
  database:
    url: ""
    username: ""
    password: ""
```

#### Typy wartości
```yaml
# Stringi
appName: "myapp"
version: "1.0.0"

# Liczby
replicas: 3
port: 8080

# Boolean
enabled: true
debug: false

# Tablice
ports:
  - 80
  - 443
  - 8080

# Obiekty
database:
  host: "db.example.com"
  port: 5432
  name: "myapp"

# Listy obiektów
servers:
  - name: "web1"
    port: 80
  - name: "web2"
    port: 8080
```

### **Templates** – pliki YAML z Go-template do dynamicznych zasobów

#### Podstawowe szablony
```yaml
# templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include "myapp.fullname" . }}
  labels:
    {{- include "myapp.labels" . | nindent 4 }}
spec:
  replicas: {{ .Values.replicaCount }}
  selector:
    matchLabels:
      {{- include "myapp.selectorLabels" . | nindent 6 }}
  template:
    metadata:
      labels:
        {{- include "myapp.selectorLabels" . | nindent 8 }}
    spec:
      containers:
        - name: {{ .Chart.Name }}
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
          imagePullPolicy: {{ .Values.image.pullPolicy }}
          ports:
            - name: http
              containerPort: {{ .Values.service.targetPort }}
              protocol: TCP
          resources:
            {{- toYaml .Values.resources | nindent 12 }}
```

#### Template functions
```yaml
# Funkcje string
{{ .Values.appName | quote }}
{{ .Values.version | upper }}
{{ .Values.description | trunc 50 }}

# Funkcje matematyczne
{{ add .Values.replicas 1 }}
{{ mul .Values.cpu 2 }}
{{ div .Values.memory 1024 }}

# Funkcje logiczne
{{ if .Values.enabled }}
enabled: true
{{ end }}

{{ if eq .Values.environment "production" }}
replicas: 3
{{ else }}
replicas: 1
{{ end }}

# Funkcje list
{{ range .Values.ports }}
- port: {{ . }}
{{ end }}

{{ range $key, $value := .Values.config }}
{{ $key }}: {{ $value }}
{{ end }}
```

#### Template pipelines
```yaml
# Łączenie funkcji
{{ .Values.name | default "myapp" | quote }}

# Warunkowe renderowanie
{{- if .Values.ingress.enabled }}
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: {{ include "myapp.fullname" . }}
{{- end }}

# Include innych szablonów
{{- include "myapp.labels" . | nindent 4 }}

# Definicje helperów
{{- define "myapp.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}
```

### **Releases** – instancje zainstalowanych chartów

#### Zarządzanie release'ami
```bash
# Instalacja chartu
helm install myapp ./myapp

# Instalacja z wartościami
helm install myapp ./myapp --values values-prod.yaml

# Instalacja z określonymi wartościami
helm install myapp ./myapp --set replicaCount=5 --set image.tag=1.1.0

# Upgrade release'u
helm upgrade myapp ./myapp

# Rollback do poprzedniej wersji
helm rollback myapp 1

# Usunięcie release'u
helm uninstall myapp

# Lista release'ów
helm list

# Historia release'u
helm history myapp
```

#### Status release'u
```bash
# Sprawdzenie statusu
helm status myapp

# Test release'u
helm test myapp

# Sprawdzenie wartości
helm get values myapp

# Sprawdzenie manifestu
helm get manifest myapp

# Sprawdzenie notes
helm get notes myapp
```

### **helm install / upgrade / rollback / uninstall**

#### Instalacja
```bash
# Podstawowa instalacja
helm install myapp ./myapp

# Instalacja z namespace
helm install myapp ./myapp --namespace production --create-namespace

# Instalacja z timeout
helm install myapp ./myapp --timeout 5m

# Instalacja z atomic (rollback przy błędzie)
helm install myapp ./myapp --atomic

# Instalacja z wait
helm install myapp ./myapp --wait --timeout 5m

# Instalacja z dry-run
helm install myapp ./myapp --dry-run
```

#### Upgrade
```bash
# Podstawowy upgrade
helm upgrade myapp ./myapp

# Upgrade z wartościami
helm upgrade myapp ./myapp --values values-prod.yaml

# Upgrade z resetem wartości
helm upgrade myapp ./myapp --reset-values

# Upgrade z reinstall
helm upgrade myapp ./myapp --reinstall

# Upgrade z force
helm upgrade myapp ./myapp --force
```

#### Rollback
```bash
# Rollback do poprzedniej wersji
helm rollback myapp

# Rollback do konkretnej wersji
helm rollback myapp 2

# Rollback z timeout
helm rollback myapp --timeout 5m

# Rollback z wait
helm rollback myapp --wait
```

#### Uninstall
```bash
# Usunięcie release'u
helm uninstall myapp

# Usunięcie z namespace
helm uninstall myapp --namespace production

# Usunięcie z timeout
helm uninstall myapp --timeout 5m

# Usunięcie z keep history
helm uninstall myapp --keep-history
```

### **OCI registry / Chart repository**

#### OCI compatibility
```bash
# Logowanie do OCI registry
helm registry login registry.example.com

# Push chartu do OCI registry
helm push myapp-0.1.0.tgz oci://registry.example.com/charts

# Pull chartu z OCI registry
helm pull oci://registry.example.com/charts/myapp --version 0.1.0

# Instalacja z OCI registry
helm install myapp oci://registry.example.com/charts/myapp --version 0.1.0
```

#### Chart repositories
```bash
# Dodanie repozytorium
helm repo add bitnami https://charts.bitnami.com/bitnami

# Lista repozytoriów
helm repo list

# Update repozytoriów
helm repo update

# Wyszukiwanie chartów
helm search repo nginx

# Instalacja z repozytorium
helm install nginx bitnami/nginx
```

#### Własne repozytorium
```bash
# Tworzenie repozytorium
helm repo create myrepo

# Package chartu
helm package ./myapp

# Index repozytorium
helm repo index .

# Serwowanie repozytorium
python3 -m http.server 8080

# Dodanie własnego repozytorium
helm repo add myrepo http://localhost:8080
```

### **Zaawansowane funkcje**

#### Hooks
```yaml
# templates/pre-install-job.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: {{ include "myapp.fullname" . }}-pre-install
  annotations:
    "helm.sh/hook": pre-install
    "helm.sh/hook-weight": "-5"
    "helm.sh/hook-delete-policy": hook-succeeded
spec:
  template:
    spec:
      containers:
      - name: pre-install
        image: busybox
        command: ['sh', '-c', 'echo "Pre-install hook"']
      restartPolicy: Never
  backoffLimit: 3
```

#### Tests
```yaml
# templates/test-connection.yaml
apiVersion: v1
kind: Pod
metadata:
  name: "{{ include "myapp.fullname" . }}-test-connection"
  annotations:
    "helm.sh/hook": test
spec:
  containers:
    - name: test-connection
      image: busybox
      command:
        - /bin/sh
        - -c
        - |
          wget --timeout=5 --tries=1 http://{{ include "myapp.fullname" . }}:{{ .Values.service.port }}
  restartPolicy: Never
```

#### Dependencies
```yaml
# Chart.yaml
dependencies:
  - name: postgresql
    version: 12.x.x
    repository: https://charts.bitnami.com/bitnami
    condition: postgresql.enabled
  - name: redis
    version: 16.x.x
    repository: https://charts.bitnami.com/bitnami
    condition: redis.enabled
```

```bash
# Update dependencies
helm dependency update ./myapp

# Build dependencies
helm dependency build ./myapp

# List dependencies
helm dependency list ./myapp
```
