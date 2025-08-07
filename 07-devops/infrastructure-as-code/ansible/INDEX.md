# Ansible

Ten katalog jest częścią sekcji infrastructure-as-code.

## Zawartość katalogu

- [Ansible Basics](Ansible_Basics.md) - Podstawy Ansible
- [Ansible Playbooks](Ansible_Playbooks.md) - Tworzenie i używanie playbooków
- [Ansible Inventory](Ansible_Inventory.md) - Zarządzanie inventory
- [Ansible Modules](Ansible_Modules.md) - Używanie modułów
- [Ansible Roles](Ansible_Roles.md) - Tworzenie i używanie ról
- [Ansible Best Practices](Ansible_Best_Practices.md) - Najlepsze praktyki
- [Ansible with Docker](Ansible_Docker.md) - Integracja z Docker
- [Ansible with Kubernetes](Ansible_Kubernetes.md) - Integracja z K8s

## Kluczowe tematy

### ⚙️ **Ansible**

#### **Playbook** – zbiór zadań automatyzujących konfigurację
- **YAML format** - human-readable configuration
- **Task-based execution** - sequential task execution
- **Conditional execution** - when statements
- **Error handling** - ignore_errors, failed_when
- **Parallel execution** - async tasks, polling

#### **Inventory** – lista hostów, do których Ansible się łączy
- **Static inventory** - INI or YAML files
- **Dynamic inventory** - scripts, plugins, cloud providers
- **Host groups** - logical grouping of hosts
- **Host variables** - per-host configuration
- **Group variables** - shared configuration

#### **Modules** – gotowe akcje (np. `docker_container`, `apt`)
- **Built-in modules** - file, copy, template, service
- **Community modules** - docker, kubernetes, cloud providers
- **Custom modules** - Python-based custom actions
- **Module parameters** - configuration options
- **Module return values** - structured output

#### **Roles** – organizacja kodu (komponenty jak usługi)
- **Role structure** - tasks, handlers, vars, defaults
- **Role dependencies** - meta/main.yml
- **Role composition** - combining multiple roles
- **Role testing** - molecule, testinfra
- **Role sharing** - Ansible Galaxy

#### **Idempotencja** – wielokrotne uruchomienie = ten sam efekt
- **State checking** - checking current state before changes
- **Conditional execution** - only run when needed
- **Safe operations** - no side effects on repeated runs
- **Rollback capability** - reversible operations
- **Consistent results** - predictable outcomes

#### **Connection: local** – tryb do automatyzacji Dockera lokalnie
- **Local execution** - running on control node
- **Docker connection** - connecting to Docker daemon
- **Container management** - creating, starting, stopping containers
- **Image management** - building, pulling, pushing images
- **Network management** - Docker network operations

### 🔧 **Ansible Architecture**

#### **Control Node** - maszyna zarządzająca
- **Ansible installation** - Python-based automation engine
- **Inventory management** - host and group definitions
- **Playbook execution** - orchestration engine
- **Configuration management** - centralized configuration
- **Secret management** - Ansible Vault integration

#### **Managed Nodes** - maszyny docelowe
- **SSH connectivity** - secure remote execution
- **Python runtime** - required for module execution
- **No agent required** - agentless architecture
- **Minimal footprint** - lightweight requirements
- **Cross-platform** - Linux, Windows, macOS support

#### **Connection Methods**
- **SSH** - Secure Shell (default)
- **WinRM** - Windows Remote Management
- **Docker** - container connections
- **Kubernetes** - pod connections
- **Local** - same machine execution

### 📋 **Playbook Structure**

#### **Basic Playbook**
```yaml
---
- name: Configure web server
  hosts: webservers
  become: yes
  tasks:
    - name: Install nginx
      apt:
        name: nginx
        state: present
        update_cache: yes
```

#### **Advanced Playbook**
```yaml
---
- name: Deploy application
  hosts: app_servers
  vars:
    app_version: "1.0.0"
    app_port: 3000
  pre_tasks:
    - name: Update package cache
      apt: update_cache=yes
  tasks:
    - name: Install dependencies
      apt:
        name: "{{ item }}"
        state: present
      loop:
        - nodejs
        - npm
    - name: Deploy application
      copy:
        src: "app-{{ app_version }}.tar.gz"
        dest: "/opt/app/"
  handlers:
    - name: restart application
      service:
        name: myapp
        state: restarted
  post_tasks:
    - name: Verify deployment
      uri:
        url: "http://localhost:{{ app_port }}/health"
        status_code: 200
```

### 🗂️ **Inventory Management**

#### **Static Inventory (INI)**
```ini
[webservers]
web1.example.com
web2.example.com

[dbservers]
db1.example.com
db2.example.com

[webservers:vars]
http_port=80
max_clients=200

[dbservers:vars]
db_port=5432
```

#### **Static Inventory (YAML)**
```yaml
all:
  children:
    webservers:
      hosts:
        web1.example.com:
          http_port: 80
        web2.example.com:
          http_port: 8080
      vars:
        max_clients: 200
    dbservers:
      hosts:
        db1.example.com:
          db_port: 5432
        db2.example.com:
          db_port: 5433
```

#### **Dynamic Inventory**
```bash
#!/bin/bash
# dynamic_inventory.sh
echo '{
  "webservers": {
    "hosts": ["web1.example.com", "web2.example.com"],
    "vars": {
      "http_port": 80
    }
  }
}'
```

### 🔌 **Module Categories**

#### **System Modules**
- **Package management** - apt, yum, dnf, pip
- **Service management** - service, systemd
- **User management** - user, group
- **File operations** - file, copy, template
- **Process management** - command, shell, raw

#### **Cloud Modules**
- **AWS** - ec2, s3, rds, elb
- **Azure** - azure_rm_virtualmachine, azure_rm_storageaccount
- **GCP** - gcp_compute_instance, gcp_storage_bucket
- **Docker** - docker_container, docker_image
- **Kubernetes** - k8s, kubernetes.core

#### **Network Modules**
- **Network devices** - ios_config, nxos_config
- **Load balancers** - haproxy, nginx
- **DNS** - route53, cloudflare
- **Firewall** - ufw, iptables
- **VPN** - openvpn, wireguard

### 🎭 **Role Development**

#### **Role Structure**
```
roles/
└── webserver/
    ├── defaults/
    │   └── main.yml
    ├── files/
    ├── handlers/
    │   └── main.yml
    ├── meta/
    │   └── main.yml
    ├── tasks/
    │   └── main.yml
    ├── templates/
    ├── tests/
    └── vars/
        └── main.yml
```

#### **Role Dependencies**
```yaml
# roles/webserver/meta/main.yml
---
dependencies:
  - role: common
  - role: nginx
    version: "1.0.0"
  - role: ssl
    when: ssl_enabled | default(false)
```

### 🔐 **Security Features**

#### **Ansible Vault**
```bash
# Encrypt sensitive data
ansible-vault encrypt secrets.yml

# Run playbook with vault
ansible-playbook playbook.yml --ask-vault-pass

# Create encrypted variable
ansible-vault encrypt_string 'secret_password'
```

#### **SSH Key Management**
```bash
# Generate SSH key
ssh-keygen -t rsa -b 4096

# Copy key to managed nodes
ssh-copy-id user@host

# Use specific key
ansible-playbook playbook.yml --private-key=~/.ssh/custom_key
```

### 🚀 **Performance Optimization**

#### **Parallel Execution**
```yaml
# Limit concurrent hosts
ansible-playbook playbook.yml --forks=10

# Async tasks
- name: Long running task
  command: /usr/bin/long_script
  async: 300
  poll: 10
```

#### **Fact Caching**
```ini
# ansible.cfg
[defaults]
fact_caching = jsonfile
fact_caching_connection = /tmp/ansible_facts
fact_caching_timeout = 86400
```

### 🔄 **Integration Patterns**

#### **CI/CD Integration**
```yaml
# .github/workflows/deploy.yml
- name: Deploy with Ansible
  run: |
    ansible-playbook deploy.yml \
      --inventory inventory/production \
      --extra-vars "version=${{ github.sha }}"
```

#### **Docker Integration**
```yaml
- name: Build Docker image
  docker_image:
    name: myapp
    tag: "{{ app_version }}"
    build:
      path: "{{ playbook_dir }}/app"
    source: build
```

#### **Kubernetes Integration**
```yaml
- name: Deploy to Kubernetes
  kubernetes.core.k8s:
    state: present
    definition:
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: myapp
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
              image: myapp:{{ app_version }}
```

