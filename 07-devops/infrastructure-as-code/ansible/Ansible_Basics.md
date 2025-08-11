# Ansible Basics

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


## ⚙️ **Ansible** – automatyzacja konfiguracji i deploymentu

### **Playbook** – zbiór zadań automatyzujących konfigurację

#### Podstawowa struktura playbook
```yaml
---
- name: Configure web server
  hosts: webservers
  become: yes
  vars:
    http_port: 80
    max_clients: 200
  tasks:
    - name: Update package cache
      apt:
        update_cache: yes
        cache_valid_time: 3600

    - name: Install nginx
      apt:
        name: nginx
        state: present

    - name: Configure nginx
      template:
        src: nginx.conf.j2
        dest: /etc/nginx/nginx.conf
        validate: 'nginx -t -c %s'
      notify: restart nginx

  handlers:
    - name: restart nginx
      service:
        name: nginx
        state: restarted
```

#### YAML format – human-readable configuration
```yaml
# Przykład zagnieżdżonych struktur
webservers:
  - name: web1
    ip: 192.168.1.10
    port: 80
  - name: web2
    ip: 192.168.1.11
    port: 8080

database:
  host: db.example.com
  port: 5432
  name: myapp
  user: admin
```

### **Inventory** – lista hostów, do których Ansible się łączy

#### Static inventory (INI)
```ini
# inventory.ini
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
db_user=postgres

[all:vars]
ansible_user=ubuntu
ansible_ssh_private_key_file=~/.ssh/id_rsa
```

#### Static inventory (YAML)
```yaml
# inventory.yml
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
      vars:
        db_user: postgres
    all:
      vars:
        ansible_user: ubuntu
        ansible_ssh_private_key_file: ~/.ssh/id_rsa
```

#### Dynamic inventory
```bash
#!/bin/bash
# dynamic_inventory.sh
echo '{
  "webservers": {
    "hosts": ["web1.example.com", "web2.example.com"],
    "vars": {
      "http_port": 80,
      "max_clients": 200
    }
  },
  "dbservers": {
    "hosts": ["db1.example.com"],
    "vars": {
      "db_port": 5432
    }
  }
}'
```

### **Modules** – gotowe akcje (np. `docker_container`, `apt`)

#### System modules
```yaml
# Package management
- name: Install packages
  apt:
    name:
      - nginx
      - python3
      - git
    state: present
    update_cache: yes

# Service management
- name: Start and enable nginx
  systemd:
    name: nginx
    state: started
    enabled: yes

# File operations
- name: Copy configuration file
  copy:
    src: config.conf
    dest: /etc/myapp/config.conf
    owner: root
    group: root
    mode: '0644'

# Template rendering
- name: Configure application
  template:
    src: app.conf.j2
    dest: /etc/myapp/app.conf
    validate: 'myapp -t -c %s'
```

#### Cloud modules
```yaml
# AWS EC2
- name: Create EC2 instance
  amazon.aws.ec2_instance:
    name: web-server
    instance_type: t3.micro
    image_id: ami-12345678
    region: us-west-2
    key_name: my-key
    vpc_subnet_id: subnet-12345678
    security_group: sg-12345678

# Docker
- name: Run Docker container
  community.docker.docker_container:
    name: myapp
    image: myapp:latest
    state: started
    ports:
      - "8080:3000"
    env:
      NODE_ENV: production
    volumes:
      - /host/path:/container/path

# Kubernetes
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
              image: myapp:latest
```

### **Roles** – organizacja kodu (komponenty jak usługi)

#### Struktura roli
```
roles/
└── webserver/
    ├── defaults/
    │   └── main.yml          # Domyślne zmienne
    ├── files/                # Pliki do kopiowania
    │   ├── nginx.conf
    │   └── ssl/
    ├── handlers/
    │   └── main.yml          # Handlers
    ├── meta/
    │   └── main.yml          # Metadane i zależności
    ├── tasks/
    │   └── main.yml          # Główne zadania
    ├── templates/            # Szablony Jinja2
    │   ├── nginx.conf.j2
    │   └── vhost.conf.j2
    ├── tests/                # Testy
    │   ├── inventory
    │   └── test.yml
    └── vars/
        └── main.yml          # Zmienne roli
```

#### Przykład roli webserver
```yaml
# roles/webserver/tasks/main.yml
---
- name: Install nginx
  apt:
    name: nginx
    state: present
    update_cache: yes

- name: Configure nginx
  template:
    src: nginx.conf.j2
    dest: /etc/nginx/nginx.conf
    validate: 'nginx -t -c %s'
  notify: restart nginx

- name: Configure virtual hosts
  template:
    src: vhost.conf.j2
    dest: /etc/nginx/sites-available/{{ item.name }}
    validate: 'nginx -t -c %s'
  loop: "{{ virtual_hosts }}"
  notify: restart nginx

- name: Enable sites
  file:
    src: /etc/nginx/sites-available/{{ item.name }}
    dest: /etc/nginx/sites-enabled/{{ item.name }}
    state: link
  loop: "{{ virtual_hosts }}"
  notify: restart nginx
```

#### Dependencies między rolami
```yaml
# roles/webserver/meta/main.yml
---
dependencies:
  - role: common
  - role: ssl
    when: ssl_enabled | default(false)
  - role: firewall
    when: firewall_enabled | default(true)
```

### **Idempotencja** – wielokrotne uruchomienie = ten sam efekt

#### Przykłady idempotentnych zadań
```yaml
# Sprawdzanie stanu przed zmianą
- name: Check if user exists
  getent:
    database: passwd
    key: myuser
  register: user_check

- name: Create user if not exists
  user:
    name: myuser
    shell: /bin/bash
    home: /home/myuser
  when: user_check.ansible_facts.getent_passwd is not defined

# Używanie modułów, które sprawdzają stan
- name: Ensure package is installed
  apt:
    name: nginx
    state: present  # present, absent, latest

- name: Ensure service is running
  systemd:
    name: nginx
    state: started  # started, stopped, restarted, reloaded

- name: Ensure file exists with content
  copy:
    content: "Hello World"
    dest: /tmp/hello.txt
    force: no  # Nie nadpisuj jeśli plik istnieje
```

#### State checking
```yaml
# Sprawdzanie aktualnego stanu
- name: Get current configuration
  command: cat /etc/nginx/nginx.conf
  register: current_config
  changed_when: false

- name: Update configuration only if needed
  template:
    src: nginx.conf.j2
    dest: /etc/nginx/nginx.conf
    validate: 'nginx -t -c %s'
  when: current_config.stdout != expected_config
  notify: restart nginx
```

### **Connection: local** – tryb do automatyzacji Dockera lokalnie

#### Lokalne wykonanie
```yaml
---
- name: Local Docker operations
  hosts: localhost
  connection: local
  tasks:
    - name: Build Docker image
      community.docker.docker_image:
        name: myapp
        tag: latest
        source: build
        build:
          path: ./app
          dockerfile: Dockerfile

    - name: Run Docker container
      community.docker.docker_container:
        name: myapp
        image: myapp:latest
        state: started
        ports:
          - "8080:3000"
        env:
          NODE_ENV: production
```

#### Docker Compose z Ansible
```yaml
---
- name: Deploy with Docker Compose
  hosts: localhost
  connection: local
  tasks:
    - name: Create docker-compose.yml
      template:
        src: docker-compose.yml.j2
        dest: ./docker-compose.yml

    - name: Start services
      community.docker.docker_compose:
        project_src: .
        state: present

    - name: Scale service
      community.docker.docker_compose:
        project_src: .
        services:
          - web
        scale:
          web: 3
```

### **Ansible Architecture**

#### Control Node
```bash
# Instalacja Ansible
sudo apt update
sudo apt install ansible

# Sprawdzenie wersji
ansible --version

# Konfiguracja
cat > ansible.cfg << EOF
[defaults]
inventory = ./inventory
remote_user = ubuntu
private_key_file = ~/.ssh/id_rsa
host_key_checking = False
timeout = 30
gathering = smart
fact_caching = jsonfile
fact_caching_connection = /tmp/ansible_facts
fact_caching_timeout = 86400
EOF
```

#### Managed Nodes
```bash
# Wymagania na node'ach
# - SSH dostęp
# - Python 2.7+ lub Python 3.5+
# - sudo uprawnienia (jeśli używamy become)

# Sprawdzenie połączenia
ansible all -m ping

# Sprawdzenie faktów
ansible all -m setup

# Test połączenia z określonymi hostami
ansible webservers -m ping
```

### **Connection Methods**

#### SSH (domyślny)
```yaml
# inventory.yml
all:
  hosts:
    web1.example.com:
      ansible_user: ubuntu
      ansible_ssh_private_key_file: ~/.ssh/id_rsa
      ansible_ssh_common_args: '-o StrictHostKeyChecking=no'
```

#### WinRM (Windows)
```yaml
# inventory.yml
all:
  hosts:
    win-server.example.com:
      ansible_connection: winrm
      ansible_user: Administrator
      ansible_password: "{{ vault_password }}"
      ansible_winrm_server_cert_validation: ignore
```

#### Docker
```yaml
# inventory.yml
all:
  hosts:
    container1:
      ansible_connection: docker
      ansible_docker_image: ubuntu:20.04
```

### **Security Features**

#### Ansible Vault
```bash
# Szyfrowanie pliku
ansible-vault encrypt secrets.yml

# Szyfrowanie zmiennej
ansible-vault encrypt_string 'secret_password' --name 'db_password'

# Uruchomienie z hasłem
ansible-playbook playbook.yml --ask-vault-pass

# Uruchomienie z plikiem hasła
ansible-playbook playbook.yml --vault-password-file ~/.vault_pass
```

#### SSH Key Management
```bash
# Generowanie klucza SSH
ssh-keygen -t rsa -b 4096 -f ~/.ssh/ansible_key

# Kopiowanie klucza na hosty
ssh-copy-id -i ~/.ssh/ansible_key.pub ubuntu@host.example.com

# Użycie określonego klucza
ansible-playbook playbook.yml --private-key=~/.ssh/ansible_key
```
