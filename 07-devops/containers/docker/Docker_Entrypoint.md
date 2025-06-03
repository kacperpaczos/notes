# Docker ENTRYPOINT

## Czym jest ENTRYPOINT?

ENTRYPOINT to instrukcja Dockerfile, która definiuje główną komendę (polecenie), która będzie uruchamiana za każdym razem, gdy kontener jest uruchamiany. W przeciwieństwie do CMD, ENTRYPOINT nie może być nadpisany przez argumenty podane w `docker run`.

## Składnia

```dockerfile
ENTRYPOINT ["executable", "param1", "param2"]  # forma exec (zalecana)
ENTRYPOINT command param1 param2              # forma shell
```

## Jak działa ENTRYPOINT?

### Forma exec (zalecana)
```dockerfile
ENTRYPOINT ["python", "app.py"]
```

### Forma shell
```dockerfile
ENTRYPOINT python app.py
```

## Różnice między ENTRYPOINT a CMD

| ENTRYPOINT | CMD |
|------------|-----|
| Nie może być nadpisany | Może być nadpisany |
| Zawsze wykonywany | Może być pominięty |
| Definiuje główną funkcję kontenera | Definiuje domyślne argumenty |

## Przykłady użycia

### Przykład 1: Aplikacja Python
```dockerfile
FROM python:3.9-slim

WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

ENTRYPOINT ["python", "app.py"]
```

### Przykład 2: Skrypt startowy
```dockerfile
FROM ubuntu:20.04

COPY start-script.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start-script.sh

ENTRYPOINT ["/usr/local/bin/start-script.sh"]
CMD ["--help"]
```

### Przykład 3: Kombinacja ENTRYPOINT + CMD
```dockerfile
FROM node:16-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .

ENTRYPOINT ["node", "server.js"]
CMD ["--port", "3000"]
```

## Najlepsze praktyki

1. **Używaj formy exec**: `ENTRYPOINT ["cmd", "param"]` zamiast `ENTRYPOINT cmd param`
2. **Kombinuj z CMD**: ENTRYPOINT definiuje komendę, CMD definiuje domyślne argumenty
3. **Twórz skrypty startowe**: Dla złożonych operacji uruchomieniowych
4. **Obsługuj sygnały**: Upewnij się, że proces główny poprawnie obsługuje SIGTERM

## Przykłady skryptów startowych

### start-script.sh
```bash
#!/bin/bash
set -e

# Inicjalizacja bazy danych
if [ "$DATABASE_INIT" = "true" ]; then
    echo "Inicjalizuję bazę danych..."
    python manage.py migrate
fi

# Uruchomienie aplikacji
exec "$@"
```

### Dockerfile z skryptem
```dockerfile
FROM python:3.9-slim

WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY start-script.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start-script.sh

COPY . .

ENTRYPOINT ["/usr/local/bin/start-script.sh"]
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
```

## Debugowanie ENTRYPOINT

### Nadpisanie ENTRYPOINT podczas testowania
```bash
# Uruchomienie z interaktywną sesją bash
docker run -it --entrypoint bash my-app:latest

# Uruchomienie z inną komendą
docker run -it --entrypoint sh my-app:latest
```

### Sprawdzenie konfiguracji obrazu
```bash
docker inspect my-app:latest | jq '.[0].Config'
```

## Częste problemy i rozwiązania

### Problem: Kontener kończy się natychmiast
**Rozwiązanie**: Upewnij się, że proces główny nie kończy się

### Problem: Brak obsługi sygnałów
**Rozwiązanie**: Użyj `exec` w skrypcie startowym lub zastosuj init system

### Problem: Zmienne środowiskowe nie są dostępne
**Rozwiązanie**: Użyj formy exec i upewnij się o przekazaniu środowiska 