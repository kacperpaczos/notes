# Standardy API - Ocena Zgodności i Rekomendacje

## Cel
Kompleksowa analiza zgodności API ze standardami branżowymi oraz praktyczne rekomendacje implementacji.

## Standardy Referencyjne

### Podstawowe Standardy
- **OpenAPI 3.x** - specyfikacja dokumentacji API
- **REST** - architektura (nazywanie zasobów, metody HTTP, statusy)
- **RFC 7807** - Problem Details for HTTP APIs (format błędów)
- **JSON:API** - standard formatowania odpowiedzi JSON
- **OWASP API Security Top 10** - standardy bezpieczeństwa

### Dodatkowe Standardy
- Wersjonowanie API
- Paginacja, filtrowanie, sortowanie
- Limitowanie zapytań (Rate Limiting)
- Uwierzytelnianie i autoryzacja (JWT/OAuth2)
- Kontrakt odpowiedzi (enveloping, metadane)
- Idempotencja
- HATEOAS
- Content negotiation i media types
- Caching (ETag, Cache-Control)
- Deprecation i wersjonowanie pól

## Ocena Zgodności

### ✅ Spełniane Standardy

#### OpenAPI 3.0.3
- Dobrze zorganizowane `components`
- Poprawne `responses` i `schemas`
- Zdefiniowane `servers`
- Struktura dokumentacji zgodna ze specyfikacją

#### Wersjonowanie API
- `/api/v1` w base path
- Spójne wersjonowanie w całym API

#### Uwierzytelnianie
- JWT Bearer Token
- Poprawna implementacja autoryzacji

#### Healthcheck
- `/system/health` z sensownym schematem
- Standardowy endpoint monitorowania

### ⚠️ Częściowo Spełniane Standardy

#### Metody i Ścieżki HTTP
**Spełniane:**
- Zasoby głównie rzeczownikowe
- Podstawowe metody HTTP (GET, POST, PUT, DELETE)

**Brakujące:**
- Akcje w ścieżkach (`/sms/send`, `/accesses/{id}/toggle`, `/settings/preset/...`)
- Mniej REST-owe podejście (akceptowalne, ale nie idealne)

#### Statusy HTTP
**Spełniane:**
- 200, 201, 400, 401

**Brakujące:**
- Konsekwentne 403/404 (zdefiniowane, ale rzadko używane)
- 204 dla operacji bez treści (np. reset ustawień)

#### Format Błędów
**Spełniane:**
- Spójny `ApiError`

**Brakujące:**
- Nie RFC 7807 (Problem Details)
- Brak `application/problem+json`

#### Paginacja
**Spełniane:**
- Parametry `page`/`limit`
- Definicja `PaginationMeta`

**Brakujące:**
- Odpowiedzi listujące nie zwracają `meta.pagination`

#### Kontrakt Odpowiedzi (Envelope)
**Spełniane:**
- Większość zwraca `ApiResponse`

**Brakujące:**
- `LoginResponse` nie jest "owinięty"
- Brak spójności w formatowaniu

### ❌ Brakujące Standardy

#### Rate Limiting
- Brak odpowiedzi 429
- Brak nagłówków `X-RateLimit-*`
- Brak `Retry-After` (poza polem w `SendSmsResponse`)

#### Idempotencja
- Akcyjne POST-y nie wspominają o `Idempotency-Key`
- Brak mechanizmu zapobiegania duplikatom

#### HATEOAS/JSON:API
- Nie stosowane (OK, jeśli nie celujesz w ten styl)

#### Content Negotiation
- Brak `application/problem+json` dla błędów

#### Caching
- Brak `ETag`/`If-None-Match`
- Brak `Cache-Control`
- Brak wariantów 304

#### Dokumentacja
- Brak przykładów odpowiedzi sukcesu
- Przykłady głównie dla błędów

#### Deprecation
- Brak oznaczeń `deprecated: true`

#### Filtrowanie i Sortowanie
- Filtrowanie: częściowo (type, isActive, dateFrom/dateTo)
- Sortowanie: brak

## Rekomendacje Implementacji

### 🎯 Najważniejsze (Priorytet 1)

#### 1. Ujednolicenie Odpowiedzi
```yaml
# Zawsze ApiResponse z data/meta
responses:
  200:
    description: Success
    content:
      application/json:
        schema:
          $ref: '#/components/schemas/ApiResponse'
        example:
          data: {}
          meta:
            pagination:
              page: 1
              limit: 20
              total: 100
```

#### 2. Dodanie Paginacji do Listowań
```yaml
components:
  schemas:
    PaginatedResponse:
      type: object
      properties:
        data:
          type: array
        meta:
          type: object
          properties:
            pagination:
              $ref: '#/components/schemas/PaginationMeta'
```

#### 3. Uzupełnienie Statusów HTTP
```yaml
responses:
  401:
    $ref: '#/components/responses/Unauthorized'
  403:
    $ref: '#/components/responses/Forbidden'
  404:
    $ref: '#/components/responses/NotFound'
  204:
    description: No Content
```

#### 4. Rate Limiting
```yaml
responses:
  429:
    description: Too Many Requests
    headers:
      X-RateLimit-Limit:
        schema:
          type: integer
      X-RateLimit-Remaining:
        schema:
          type: integer
      X-RateLimit-Reset:
        schema:
          type: integer
      Retry-After:
        schema:
          type: integer
```

### 🔧 Średni Priorytet

#### 5. Idempotencja dla Akcyjnych POST-ów
```yaml
parameters:
  - name: Idempotency-Key
    in: header
    required: false
    schema:
      type: string
    description: Klucz zapewniający idempotencję operacji
```

#### 6. RFC 7807 Problem Details
```yaml
responses:
  400:
    description: Bad Request
    content:
      application/problem+json:
        schema:
          type: object
          properties:
            type:
              type: string
            title:
              type: string
            status:
              type: integer
            detail:
              type: string
            instance:
              type: string
```

#### 7. Caching
```yaml
responses:
  200:
    headers:
      ETag:
        schema:
          type: string
      Cache-Control:
        schema:
          type: string
          example: "public, max-age=3600"
  304:
    description: Not Modified
```

#### 8. Sortowanie
```yaml
parameters:
  - name: sort
    in: query
    schema:
      type: string
      example: "created_at"
  - name: order
    in: query
    schema:
      type: string
      enum: [asc, desc]
      default: asc
```

### 📚 Dodatkowe Usprawnienia

#### 9. Przykłady Odpowiedzi
```yaml
responses:
  200:
    content:
      application/json:
        examples:
          success:
            summary: Successful response
            value:
              data:
                id: 1
                name: "Example"
              meta:
                pagination:
                  page: 1
                  limit: 20
                  total: 100
```

#### 10. Deprecation
```yaml
paths:
  /old-endpoint:
    get:
      deprecated: true
      summary: "⚠️ DEPRECATED: Use /new-endpoint instead"
```

## Bezpieczeństwo API (OWASP API Security Top 10)

### Kluczowe Obszary
1. **Broken Object Level Authorization (BOLA)**
2. **Broken User Authentication**
3. **Excessive Data Exposure**
4. **Lack of Resources & Rate Limiting**
5. **Broken Function Level Authorization**
6. **Mass Assignment**
7. **Security Misconfiguration**
8. **Injection**
9. **Improper Assets Management**
10. **Insufficient Logging & Monitoring**

### Rekomendacje Bezpieczeństwa
- Walidacja wszystkich danych wejściowych
- Implementacja rate limiting
- Logowanie i monitorowanie
- Walidacja uprawnień na poziomie zasobów
- Sanityzacja danych wyjściowych

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne
- Wymagana jest interoperacyjność
- Zespół jest duży i rozproszony
- Planowane jest długoterminowe utrzymanie

### Nie używać gdy:
- API jest wewnętrzne i proste
- Zespół jest mały i spójny
- Czas implementacji jest krytyczny
- API ma krótki cykl życia

## Powiązane Tematy

- [REST API Design](./REST_API_Design.md)
- [OpenAPI Specification](./OpenAPI_Specification.md)
- [API Security](./API_Security.md)
- [Error Handling](./Error_Handling.md)
- [Rate Limiting](./Rate_Limiting.md)

## Źródła / Dalsza Lektura

- [OpenAPI Specification](https://swagger.io/specification/)
- [RFC 7807 - Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807)
- [JSON:API](https://jsonapi.org/)
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [REST API Design Best Practices](https://restfulapi.net/)
- [HTTP Status Codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
