# Docker ENTRYPOINT

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


## Czym jest ENTRYPOINT?

ENTRYPOINT definiuje główną komendę uruchamianą w kontenerze. W przeciwieństwie do CMD, nie może być nadpisany przez `docker run`.

## Składnia

```dockerfile
ENTRYPOINT ["executable", "param1", "param2"]  # forma exec (zalecana)
ENTRYPOINT command param1 param2              # forma shell
```

## Różnice ENTRYPOINT vs CMD

| ENTRYPOINT | CMD |
|------------|-----|
| Nie może być nadpisany | Może być nadpisany |
| Zawsze wykonywany | Może być pominięty |
| Definiuje główną funkcję | Definiuje domyślne argumenty |

## Przykłady

### Python
```dockerfile
FROM python:3.9-slim
WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .
ENTRYPOINT ["python", "app.py"]
```

### PHP
```dockerfile
FROM php:8.1-apache
COPY . /var/www/html/
ENTRYPOINT ["apache2-foreground"]
```

### Ze skryptem startowym
```dockerfile
FROM python:3.9-slim
COPY start.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start.sh
ENTRYPOINT ["/usr/local/bin/start.sh"]
CMD ["python", "app.py"]
```

```bash
#!/bin/bash
# start.sh
set -e
echo "Starting application..."
exec "$@"
```

## Debugowanie

```bash
# Nadpisanie ENTRYPOINT
docker run -it --entrypoint bash my-app:latest
``` 

## PID 1 i minimalny init (`--init` / `init: true`)

- Aby uniknąć problemów z obsługą sygnałów i reapingiem procesów potomnych przez PID 1, można:
  - dodać minimalny init w obrazie (np. `tini`), lub
  - włączyć init w czasie uruchomienia kontenera bez modyfikacji obrazu.

### Włączenie init podczas uruchomienia

```bash
# docker run
docker run --init -d --name app myapp:latest

# Docker Compose (w pliku compose)
services:
  app:
    image: myapp:latest
    init: true
```

Źródła:
- `[Docker Compose: init](https://github.com/docker/docs/blob/main/content/reference/compose-file/services.md#_snippet_45)`
- `[Docker: multi-service containers i init](https://github.com/docker/docs/blob/main/content/manuals/engine/containers/multi-service_container.md#_qa_1)`