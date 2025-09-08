# REST API Design - Zasady i Najlepsze Praktyki

## Cel
Przewodnik po projektowaniu REST API zgodnego z zasadami architektury REST i najlepszymi praktykami branżowymi.

## Problem
Potrzeba stworzenia API, które będzie:
- Intuicyjne w użyciu
- Skalowalne i wydajne
- Zgodne ze standardami REST
- Łatwe w utrzymaniu i rozwijaniu

## Pojęcia Kluczowe

### REST (Representational State Transfer)
- **Zasoby** - obiekty, które można identyfikować przez URI
- **Reprezentacje** - formaty danych (JSON, XML, HTML)
- **Metody HTTP** - operacje na zasobach (GET, POST, PUT, DELETE)
- **Statusy HTTP** - kody odpowiedzi informujące o rezultacie
- **Stateless** - brak przechowywania stanu między żądaniami
- **Cacheable** - możliwość cachowania odpowiedzi

### Zasady REST
1. **Uniform Interface** - jednolity interfejs
2. **Stateless** - bezstanowość
3. **Cacheable** - możliwość cachowania
4. **Client-Server** - separacja klienta i serwera
5. **Layered System** - system warstwowy
6. **Code on Demand** - opcjonalne wykonywanie kodu

## Struktura / Diagram

```
Client (Browser/App)
    ↓ HTTP Request
API Gateway/Load Balancer
    ↓
REST API Server
    ↓
Business Logic Layer
    ↓
Data Access Layer
    ↓
Database
```

## Przepływ Działania

### 1. Identyfikacja Zasobów
```
Zasób: Użytkownicy
URI: /api/v1/users
Metody: GET, POST

Zasób: Pojedynczy użytkownik
URI: /api/v1/users/{id}
Metody: GET, PUT, DELETE

Zasób: Posty użytkownika
URI: /api/v1/users/{id}/posts
Metody: GET, POST
```

### 2. Definicja Metod HTTP
```http
GET    /api/v1/users           # Lista użytkowników
POST   /api/v1/users           # Utwórz użytkownika
GET    /api/v1/users/123       # Pobierz użytkownika
PUT    /api/v1/users/123       # Zaktualizuj użytkownika
DELETE /api/v1/users/123       # Usuń użytkownika
```

### 3. Statusy HTTP
```http
200 OK          # Sukces
201 Created     # Zasób utworzony
204 No Content  # Sukces bez treści
400 Bad Request # Błędne żądanie
401 Unauthorized # Brak autoryzacji
403 Forbidden   # Brak uprawnień
404 Not Found   # Zasób nie znaleziony
409 Conflict    # Konflikt
422 Unprocessable Entity # Błąd walidacji
500 Internal Server Error # Błąd serwera
```

## Przykłady Użycia

### Pobieranie Listy Zasobów
```http
GET /api/v1/users?page=1&limit=20&sort=name&order=asc
Accept: application/json

Response:
{
  "data": [
    {
      "id": 1,
      "name": "Jan Kowalski",
      "email": "jan@example.com"
    }
  ],
  "meta": {
    "pagination": {
      "page": 1,
      "limit": 20,
      "total": 100,
      "totalPages": 5
    }
  }
}
```

### Tworzenie Zasobu
```http
POST /api/v1/users
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Jan Kowalski",
  "email": "jan@example.com",
  "age": 30
}

Response (201 Created):
{
  "data": {
    "id": 123,
    "name": "Jan Kowalski",
    "email": "jan@example.com",
    "age": 30,
    "createdAt": "2023-01-01T00:00:00Z"
  }
}
```

### Aktualizacja Zasobu
```http
PUT /api/v1/users/123
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Jan Nowak",
  "email": "jan.nowak@example.com",
  "age": 31
}

Response (200 OK):
{
  "data": {
    "id": 123,
    "name": "Jan Nowak",
    "email": "jan.nowak@example.com",
    "age": 31,
    "updatedAt": "2023-01-02T00:00:00Z"
  }
}
```

### Usuwanie Zasobu
```http
DELETE /api/v1/users/123
Authorization: Bearer <token>

Response (204 No Content):
```

## Implementacja (Fragmenty Kodu)

### Struktura URL-i
```javascript
// ✅ DOBRE - Rzeczownikowe zasoby
GET    /api/v1/users
POST   /api/v1/users
GET    /api/v1/users/123
PUT    /api/v1/users/123
DELETE /api/v1/users/123

// ❌ ZŁE - Akcje w URL
GET    /api/v1/getUser/123
POST   /api/v1/createUser
POST   /api/v1/updateUser/123
POST   /api/v1/deleteUser/123
```

### Wersjonowanie API
```javascript
// Wersjonowanie w URL (zalecane)
/api/v1/users
/api/v2/users

// Wersjonowanie w nagłówku
Accept: application/vnd.api.v1+json
Accept: application/vnd.api.v2+json

// Wersjonowanie w parametrze (niezalecane)
/api/users?version=1
```

### Filtrowanie i Sortowanie
```javascript
// Filtrowanie
GET /api/v1/users?status=active&role=admin
GET /api/v1/users?createdAfter=2023-01-01&createdBefore=2023-12-31

// Sortowanie
GET /api/v1/users?sort=name&order=asc
GET /api/v1/users?sort=-createdAt  // minus = desc

// Paginacja
GET /api/v1/users?page=1&limit=20
GET /api/v1/users?offset=0&limit=20
```

### Obsługa Błędów
```javascript
// Struktura błędu
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Dane są nieprawidłowe",
    "details": [
      {
        "field": "email",
        "message": "Email jest wymagany"
      }
    ],
    "timestamp": "2023-01-01T00:00:00Z",
    "requestId": "req-123"
  }
}

// Mapowanie statusów
400 Bad Request     → Błąd walidacji
401 Unauthorized    → Brak tokenu
403 Forbidden       → Brak uprawnień
404 Not Found       → Zasób nie istnieje
409 Conflict        → Konflikt (np. duplikat)
422 Unprocessable   → Błąd walidacji danych
429 Too Many Requests → Przekroczenie limitu
500 Internal Error  → Błąd serwera
```

## Zalety

### Dla Deweloperów
- **Intuicyjność** - łatwe do zrozumienia
- **Standaryzacja** - spójne wzorce
- **Narzędzia** - bogate wsparcie narzędziowe
- **Dokumentacja** - łatwa do wygenerowania

### Dla Systemu
- **Skalowalność** - łatwe dodawanie nowych endpointów
- **Cacheowanie** - możliwość cachowania odpowiedzi
- **Stateless** - łatwiejsze skalowanie poziome
- **Interoperacyjność** - standardowe protokoły

### Dla Użytkowników
- **Przewidywalność** - spójne zachowanie
- **Elastyczność** - różne formaty danych
- **Wydajność** - optymalizacja transferu danych

## Wady

### Ograniczenia REST
- **Over-fetching** - pobieranie niepotrzebnych danych
- **Under-fetching** - potrzeba wielu żądań
- **Brak real-time** - trudności z aktualizacjami na żywo
- **Sztywność** - trudności z niestandardowymi operacjami

### Problemy Implementacyjne
- **Złożoność** - może być przytłaczające dla prostych operacji
- **Wydajność** - wiele żądań HTTP
- **Cacheowanie** - trudności z cache'owaniem złożonych operacji

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne
- Wymagana jest interoperacyjność
- Dane mają strukturę zasobów
- Potrzebne jest cacheowanie
- Zespół ma doświadczenie z REST

### Nie używać gdy:
- Potrzebne są operacje real-time
- API ma złożone operacje biznesowe
- Wymagana jest wysoka wydajność
- Dane nie mają struktury zasobów
- Zespół preferuje GraphQL

## Powiązane Tematy/Wzorce

- [OpenAPI Specification](./OpenAPI_Specification.md)
- [API Standards Compliance](./API_Standards_Compliance.md)
- [Error Handling](./Error_Handling.md)
- [API Security](./API_Security.md)
- [GraphQL vs REST](./GraphQL_vs_REST.md)

## Źródła / Dalsza Lektura

- [REST API Tutorial](https://restfulapi.net/)
- [HTTP Status Codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [RESTful Web Services](https://www.oreilly.com/library/view/restful-web-services/9780596529260/)
- [API Design Patterns](https://www.oreilly.com/library/view/api-design-patterns/9781617295850/)
- [REST API Best Practices](https://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/)
