# OpenAPI Specification - Szczegółowy Przewodnik

## Cel
Kompleksowy przewodnik po specyfikacji OpenAPI 3.x z praktycznymi przykładami implementacji.

## Problem
Potrzeba standaryzacji dokumentacji API, która będzie:
- Czytelna dla deweloperów
- Maszynowo przetwarzalna
- Zgodna z najlepszymi praktykami
- Umożliwiająca generowanie kodu i testów

## Pojęcia Kluczowe

### OpenAPI 3.x
- **Specyfikacja** - formalny opis API
- **Swagger** - narzędzia do pracy z OpenAPI
- **Components** - wielokrotnego użytku definicje
- **Paths** - ścieżki endpointów
- **Operations** - operacje HTTP (GET, POST, etc.)
- **Schemas** - modele danych
- **Responses** - definicje odpowiedzi
- **Parameters** - parametry zapytań/ścieżek
- **Security** - mechanizmy bezpieczeństwa

## Struktura Dokumentu OpenAPI

```yaml
openapi: 3.0.3
info:
  title: API Title
  version: 1.0.0
  description: API Description
servers:
  - url: https://api.example.com/v1
paths:
  /users:
    get:
      summary: List users
      responses:
        '200':
          description: Success
components:
  schemas:
    User:
      type: object
      properties:
        id:
          type: integer
        name:
          type: string
```

## Przepływ Działania

### 1. Definicja Informacji Podstawowych
```yaml
info:
  title: "User Management API"
  description: "API do zarządzania użytkownikami"
  version: "1.0.0"
  contact:
    name: "API Support"
    email: "support@example.com"
  license:
    name: "MIT"
    url: "https://opensource.org/licenses/MIT"
```

### 2. Konfiguracja Serwerów
```yaml
servers:
  - url: "https://api.example.com/v1"
    description: "Production server"
  - url: "https://staging-api.example.com/v1"
    description: "Staging server"
  - url: "http://localhost:3000/v1"
    description: "Development server"
```

### 3. Definicja Ścieżek
```yaml
paths:
  /users:
    get:
      summary: "Lista użytkowników"
      description: "Pobiera listę wszystkich użytkowników"
      tags:
        - "Users"
      parameters:
        - name: "page"
          in: "query"
          schema:
            type: "integer"
            default: 1
        - name: "limit"
          in: "query"
          schema:
            type: "integer"
            default: 20
      responses:
        "200":
          description: "Sukces"
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/UsersResponse"
```

## Przykłady Użycia

### Podstawowy Endpoint GET
```yaml
/users/{id}:
  get:
    summary: "Pobierz użytkownika"
    parameters:
      - name: "id"
        in: "path"
        required: true
        schema:
          type: "integer"
        example: 123
    responses:
      "200":
        description: "Użytkownik znaleziony"
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/User"
            example:
              id: 123
              name: "Jan Kowalski"
              email: "jan@example.com"
      "404":
        description: "Użytkownik nie znaleziony"
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Error"
```

### Endpoint POST z Walidacją
```yaml
/users:
  post:
    summary: "Utwórz użytkownika"
    requestBody:
      required: true
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/CreateUserRequest"
          example:
            name: "Jan Kowalski"
            email: "jan@example.com"
            age: 30
    responses:
      "201":
        description: "Użytkownik utworzony"
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/User"
      "400":
        description: "Nieprawidłowe dane"
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/ValidationError"
```

### Endpoint z Uwierzytelnianiem
```yaml
/users/{id}:
  put:
    summary: "Aktualizuj użytkownika"
    security:
      - bearerAuth: []
    parameters:
      - name: "id"
        in: "path"
        required: true
        schema:
          type: "integer"
    requestBody:
      required: true
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/UpdateUserRequest"
    responses:
      "200":
        description: "Użytkownik zaktualizowany"
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/User"
      "401":
        $ref: "#/components/responses/Unauthorized"
      "403":
        $ref: "#/components/responses/Forbidden"
```

## Implementacja (Fragmenty Kodu)

### Komponenty - Schematy
```yaml
components:
  schemas:
    User:
      type: object
      required:
        - id
        - name
        - email
      properties:
        id:
          type: integer
          format: int64
          example: 123
        name:
          type: string
          example: "Jan Kowalski"
        email:
          type: string
          format: email
          example: "jan@example.com"
        createdAt:
          type: string
          format: date-time
          example: "2023-01-01T00:00:00Z"
    
    CreateUserRequest:
      type: object
      required:
        - name
        - email
      properties:
        name:
          type: string
          minLength: 2
          maxLength: 100
        email:
          type: string
          format: email
        age:
          type: integer
          minimum: 0
          maximum: 150
    
    ApiResponse:
      type: object
      properties:
        data:
          type: object
        meta:
          type: object
          properties:
            pagination:
              $ref: "#/components/schemas/PaginationMeta"
    
    PaginationMeta:
      type: object
      properties:
        page:
          type: integer
          example: 1
        limit:
          type: integer
          example: 20
        total:
          type: integer
          example: 100
        totalPages:
          type: integer
          example: 5
```

### Komponenty - Odpowiedzi
```yaml
components:
  responses:
    Unauthorized:
      description: "Brak autoryzacji"
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/Error"
          example:
            error: "Unauthorized"
            message: "Token jest wymagany"
            code: 401
    
    Forbidden:
      description: "Brak uprawnień"
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/Error"
          example:
            error: "Forbidden"
            message: "Brak uprawnień do tego zasobu"
            code: 403
    
    NotFound:
      description: "Zasób nie znaleziony"
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/Error"
          example:
            error: "Not Found"
            message: "Zasób nie został znaleziony"
            code: 404
```

### Komponenty - Bezpieczeństwo
```yaml
components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
      description: "JWT Token w nagłówku Authorization"
    
    apiKey:
      type: apiKey
      in: header
      name: X-API-Key
      description: "Klucz API"
```

## Zalety

### Dla Deweloperów
- **Czytelność** - jasna dokumentacja API
- **Interaktywność** - możliwość testowania w Swagger UI
- **Generowanie kodu** - automatyczne tworzenie klientów
- **Walidacja** - sprawdzanie zgodności z specyfikacją

### Dla Zespołu
- **Standaryzacja** - jednolity format dokumentacji
- **Współpraca** - łatwiejsza komunikacja między zespołami
- **Testowanie** - automatyczne generowanie testów
- **Wersjonowanie** - kontrola zmian w API

### Dla Organizacji
- **Jakość** - lepsze API dzięki standaryzacji
- **Czas** - szybszy rozwój dzięki narzędziom
- **Koszty** - mniej błędów i problemów integracyjnych

## Wady

### Złożoność
- **Krzywa uczenia** - wymaga znajomości YAML/JSON
- **Szczegółowość** - może być przytłaczająca dla prostych API
- **Czas** - początkowo więcej czasu na dokumentację

### Ograniczenia
- **Statyczność** - dokumentacja może nie odzwierciedlać rzeczywistości
- **Wersjonowanie** - trudności z zarządzaniem wersjami
- **Narzędzia** - zależność od jakości narzędzi

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne lub używane przez wiele zespołów
- Wymagana jest szczegółowa dokumentacja
- Planowane jest generowanie kodu klienta
- Zespół ma doświadczenie z OpenAPI
- API jest złożone i ma wiele endpointów

### Nie używać gdy:
- API jest bardzo proste (1-2 endpointy)
- Zespół nie ma czasu na naukę
- API jest wewnętrzne i tymczasowe
- Brak narzędzi do pracy z OpenAPI

## Powiązane Tematy/Wzorce

- [REST API Design](./REST_API_Design.md)
- [API Standards Compliance](./API_Standards_Compliance.md)
- [Error Handling](./Error_Handling.md)
- [API Security](./API_Security.md)
- [Swagger Tools](./Swagger_Tools.md)

## Źródła / Dalsza Lektura

- [OpenAPI Specification](https://swagger.io/specification/)
- [OpenAPI Tutorial](https://swagger.io/docs/specification/basic-structure/)
- [Swagger Editor](https://editor.swagger.io/)
- [OpenAPI Generator](https://openapi-generator.tech/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [Postman OpenAPI](https://learning.postman.com/docs/integrations/available-integrations/swagger/)
