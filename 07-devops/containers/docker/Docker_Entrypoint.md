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