# API Design - Projektowanie i Implementacja API

## Cel
Kompleksowy przewodnik po projektowaniu, implementacji i zabezpieczaniu API zgodnego ze standardami branżowymi.

## Problem
Potrzeba stworzenia API, które będzie:
- Zgodne ze standardami (OpenAPI, REST, RFC 7807)
- Bezpieczne (OWASP API Security Top 10)
- Skalowalne i wydajne
- Łatwe w utrzymaniu i rozwijaniu
- Przyjazne dla deweloperów

## Pojęcia Kluczowe

### Standardy API
- **OpenAPI 3.x** - specyfikacja dokumentacji API
- **REST** - architektura (zasoby, metody HTTP, statusy)
- **RFC 7807** - Problem Details for HTTP APIs
- **JSON:API** - standard formatowania odpowiedzi
- **HATEOAS** - Hypermedia as the Engine of Application State

### Bezpieczeństwo
- **OWASP API Security Top 10** - standardy bezpieczeństwa
- **Authentication** - uwierzytelnianie (JWT, OAuth2)
- **Authorization** - autoryzacja (RBAC, ABAC)
- **Rate Limiting** - ograniczanie zapytań
- **Input Validation** - walidacja danych wejściowych

### Wzorce Projektowe
- **Envelope Pattern** - spójny format odpowiedzi
- **Pagination** - stronicowanie wyników
- **Idempotency** - zapewnienie idempotencji
- **Caching** - optymalizacja wydajności
- **Error Handling** - obsługa błędów

## Struktura / Diagram

```
Client Application
    ↓ HTTPS/TLS
API Gateway
    ↓ Authentication
Rate Limiter
    ↓ Authorization
API Server
    ↓ Input Validation
Business Logic Layer
    ↓ Output Sanitization
Data Access Layer
    ↓
Database
```

## Przepływ Działania

### 1. Projektowanie API
- Identyfikacja zasobów i operacji
- Definicja ścieżek i metod HTTP
- Projektowanie schematów danych
- Planowanie wersjonowania

### 2. Implementacja Standardów
- Konfiguracja OpenAPI
- Implementacja REST principles
- Obsługa błędów (RFC 7807)
- Paginacja i filtrowanie

### 3. Zabezpieczanie API
- Uwierzytelnianie i autoryzacja
- Walidacja danych wejściowych
- Rate limiting i monitoring
- Logowanie i audyt

### 4. Dokumentacja i Testowanie
- Generowanie dokumentacji
- Testy kontraktowe
- Testy bezpieczeństwa
- Monitorowanie wydajności

## Przykłady Użycia

### Podstawowy Endpoint
```javascript
// GET /api/v1/users
app.get('/api/v1/users', 
  authenticateToken,
  validateQuery,
  async (req, res, next) => {
    try {
      const result = await userService.getUsers(req.query);
      res.json(createResponse(result.data, result.meta));
    } catch (error) {
      next(error);
    }
  }
);
```

### Tworzenie Zasobu z Idempotencją
```javascript
// POST /api/v1/users
app.post('/api/v1/users',
  idempotencyMiddleware,
  validateUserData,
  async (req, res, next) => {
    try {
      const user = await userService.createUser(req.body);
      res.status(201).json(createResponse(user));
    } catch (error) {
      next(error);
    }
  }
);
```

### Obsługa Błędów
```javascript
// Struktura błędu zgodna z RFC 7807
{
  "type": "https://api.example.com/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Dane wejściowe są nieprawidłowe",
  "instance": "/api/v1/users",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-123"
}
```

## Implementacja (Fragmenty Kodu)

### Middleware Bezpieczeństwa
```javascript
const securityMiddleware = {
  authenticate: (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) return res.status(401).json({ error: 'Unauthorized' });
    
    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
      if (err) return res.status(403).json({ error: 'Forbidden' });
      req.user = user;
      next();
    });
  },
  
  authorize: (roles) => (req, res, next) => {
    if (!roles.includes(req.user.role)) {
      return res.status(403).json({ error: 'Insufficient permissions' });
    }
    next();
  }
};
```

### Rate Limiting
```javascript
const rateLimit = require('express-rate-limit');

const createRateLimit = (windowMs, max, message) => 
  rateLimit({
    windowMs,
    max,
    message: { error: 'Too Many Requests', message },
    standardHeaders: true,
    legacyHeaders: false
  });
```

### Walidacja Danych
```javascript
const validateInput = (schema) => (req, res, next) => {
  const { error, value } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Invalid input data',
      errors: error.details
    });
  }
  req.body = value;
  next();
};
```

## Zalety

### Dla Deweloperów
- **Spójność** - jednolity format odpowiedzi
- **Dokumentacja** - automatyczna dokumentacja API
- **Narzędzia** - bogate wsparcie narzędziowe
- **Debugowanie** - lepsze logowanie błędów

### Dla Systemu
- **Skalowalność** - łatwe dodawanie nowych endpointów
- **Bezpieczeństwo** - ochrona przed typowymi atakami
- **Wydajność** - optymalizacja transferu danych
- **Monitorowanie** - szczegółowe metryki i logi

### Dla Organizacji
- **Jakość** - lepsze API dzięki standaryzacji
- **Czas** - szybszy rozwój dzięki narzędziom
- **Koszty** - mniej błędów i problemów integracyjnych
- **Compliance** - zgodność z regulacjami

## Wady

### Złożoność
- **Krzywa uczenia** - wymaga znajomości standardów
- **Implementacja** - więcej kodu do napisania
- **Konserwacja** - potrzeba utrzymania standardów

### Ograniczenia
- **Wydajność** - dodatkowe przetwarzanie
- **Sztywność** - trudności z niestandardowymi operacjami
- **Rozmiar** - większe odpowiedzi API

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne lub używane przez wiele zespołów
- Wymagana jest wysoka jakość i bezpieczeństwo
- Planowane jest długoterminowe utrzymanie
- Zespół ma doświadczenie z standardami API

### Nie używać gdy:
- API jest prototypowe lub tymczasowe
- Zespół nie ma czasu na naukę standardów
- API jest bardzo proste (1-2 endpointy)
- Wymagana jest maksymalna wydajność

## Powiązane Tematy/Wzorce

- [REST API Design](./REST_API_Design.md)
- [OpenAPI Specification](./OpenAPI_Specification.md)
- [Error Handling](./Error_Handling.md)
- [API Security](./API_Security.md)
- [API Implementation Guide](./API_Implementation_Guide.md)
- [Authentication & Authorization](../authentication/INDEX.md)
- [Database Integration](../database-integration/INDEX.md)

## Źródła / Dalsza Lektura

- [OpenAPI Specification](https://swagger.io/specification/)
- [RFC 7807 - Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807)
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [REST API Tutorial](https://restfulapi.net/)
- [API Design Patterns](https://www.oreilly.com/library/view/api-design-patterns/9781617295850/)

---

Ten katalog jest częścią sekcji backend/node.

## Zawartość Katalogu

### Główne Artykuły
- **[API Standards Compliance](./API_Standards_Compliance.md)** - Ocena zgodności ze standardami branżowymi
- **[API Implementation Guide](./API_Implementation_Guide.md)** - Praktyczny przewodnik implementacji

### Standardy i Specyfikacje
- **[OpenAPI Specification](./OpenAPI_Specification.md)** - Szczegółowy przewodnik po OpenAPI 3.x
- **[REST API Design](./REST_API_Design.md)** - Zasady i najlepsze praktyki REST
- **[Error Handling](./Error_Handling.md)** - RFC 7807 i obsługa błędów

### Bezpieczeństwo
- **[API Security](./API_Security.md)** - OWASP API Security Top 10 i najlepsze praktyki

### Powiązane Katalogi
- **[Authentication](../authentication/)** - Uwierzytelnianie i autoryzacja
- **[Database Integration](../database-integration/)** - Integracja z bazami danych
- **[Middleware](../middleware/)** - Middleware i interceptory

