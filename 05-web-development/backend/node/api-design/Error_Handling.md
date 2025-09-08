# Error Handling w API - RFC 7807 i Najlepsze Praktyki

## Cel
Przewodnik po obsłudze błędów w API zgodny z RFC 7807 (Problem Details for HTTP APIs) i najlepszymi praktykami branżowymi.

## Problem
Potrzeba standaryzacji obsługi błędów, która będzie:
- Spójna w całym API
- Informacyjna dla deweloperów
- Zgodna ze standardami branżowymi
- Łatwa w debugowaniu i monitorowaniu

## Pojęcia Kluczowe

### RFC 7807 - Problem Details for HTTP APIs
- **Problem Details** - standardowy format błędów HTTP
- **Content-Type** - `application/problem+json`
- **Pola obowiązkowe** - `type`, `title`, `status`
- **Pola opcjonalne** - `detail`, `instance`
- **Rozszerzenia** - dodatkowe pola specyficzne dla domeny

### Typy Błędów
- **Validation Errors** - błędy walidacji danych
- **Business Logic Errors** - błędy logiki biznesowej
- **System Errors** - błędy systemowe
- **Authentication Errors** - błędy uwierzytelniania
- **Authorization Errors** - błędy autoryzacji

## Struktura / Diagram

```
Client Request
    ↓
API Gateway
    ↓
Validation Layer
    ↓
Business Logic Layer
    ↓
Data Access Layer
    ↓
Error Handler
    ↓
Standardized Error Response
```

## Przepływ Działania

### 1. Wykrycie Błędu
```javascript
// Walidacja danych wejściowych
if (!email || !isValidEmail(email)) {
  throw new ValidationError('Email jest wymagany i musi być prawidłowy');
}

// Logika biznesowa
if (user.isBlocked) {
  throw new BusinessError('Użytkownik jest zablokowany');
}

// Dostęp do danych
if (!user) {
  throw new NotFoundError('Użytkownik nie znaleziony');
}
```

### 2. Przetworzenie Błędu
```javascript
// Error Handler Middleware
app.use((error, req, res, next) => {
  const problemDetails = {
    type: 'https://api.example.com/problems/validation-error',
    title: 'Validation Error',
    status: 400,
    detail: error.message,
    instance: req.originalUrl,
    timestamp: new Date().toISOString(),
    requestId: req.id
  };
  
  res.status(400).json(problemDetails);
});
```

### 3. Odpowiedź do Klienta
```json
{
  "type": "https://api.example.com/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Email jest wymagany i musi być prawidłowy",
  "instance": "/api/v1/users",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-123"
}
```

## Przykłady Użycia

### Błąd Walidacji (400 Bad Request)
```json
{
  "type": "https://api.example.com/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Dane wejściowe są nieprawidłowe",
  "instance": "/api/v1/users",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-123",
  "errors": [
    {
      "field": "email",
      "message": "Email jest wymagany",
      "code": "REQUIRED"
    },
    {
      "field": "age",
      "message": "Wiek musi być liczbą",
      "code": "INVALID_TYPE"
    }
  ]
}
```

### Błąd Autoryzacji (401 Unauthorized)
```json
{
  "type": "https://api.example.com/problems/unauthorized",
  "title": "Unauthorized",
  "status": 401,
  "detail": "Token autoryzacyjny jest wymagany",
  "instance": "/api/v1/users/123",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-124"
}
```

### Błąd Uprawnień (403 Forbidden)
```json
{
  "type": "https://api.example.com/problems/forbidden",
  "title": "Forbidden",
  "status": 403,
  "detail": "Brak uprawnień do tego zasobu",
  "instance": "/api/v1/users/123",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-125"
}
```

### Zasób Nie Znaleziony (404 Not Found)
```json
{
  "type": "https://api.example.com/problems/not-found",
  "title": "Not Found",
  "status": 404,
  "detail": "Użytkownik o ID 123 nie został znaleziony",
  "instance": "/api/v1/users/123",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-126"
}
```

### Konflikt (409 Conflict)
```json
{
  "type": "https://api.example.com/problems/conflict",
  "title": "Conflict",
  "status": 409,
  "detail": "Użytkownik z tym emailem już istnieje",
  "instance": "/api/v1/users",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-127"
}
```

### Błąd Serwera (500 Internal Server Error)
```json
{
  "type": "https://api.example.com/problems/internal-error",
  "title": "Internal Server Error",
  "status": 500,
  "detail": "Wystąpił nieoczekiwany błąd serwera",
  "instance": "/api/v1/users",
  "timestamp": "2023-01-01T00:00:00Z",
  "requestId": "req-128"
}
```

## Implementacja (Fragmenty Kodu)

### Klasa Bazowa dla Błędów
```javascript
class ApiError extends Error {
  constructor(message, statusCode, type, details = null) {
    super(message);
    this.name = this.constructor.name;
    this.statusCode = statusCode;
    this.type = type;
    this.details = details;
    this.timestamp = new Date().toISOString();
  }
}

class ValidationError extends ApiError {
  constructor(message, details = null) {
    super(message, 400, 'validation-error', details);
  }
}

class NotFoundError extends ApiError {
  constructor(message) {
    super(message, 404, 'not-found');
  }
}

class UnauthorizedError extends ApiError {
  constructor(message = 'Unauthorized') {
    super(message, 401, 'unauthorized');
  }
}

class ForbiddenError extends ApiError {
  constructor(message = 'Forbidden') {
    super(message, 403, 'forbidden');
  }
}

class ConflictError extends ApiError {
  constructor(message) {
    super(message, 409, 'conflict');
  }
}
```

### Middleware do Obsługi Błędów
```javascript
const errorHandler = (error, req, res, next) => {
  // Logowanie błędu
  console.error('API Error:', {
    message: error.message,
    stack: error.stack,
    requestId: req.id,
    url: req.originalUrl,
    method: req.method
  });

  // Jeśli błąd nie jest ApiError, konwertuj na Internal Server Error
  if (!(error instanceof ApiError)) {
    error = new ApiError('Internal Server Error', 500, 'internal-error');
  }

  // Tworzenie Problem Details
  const problemDetails = {
    type: `https://api.example.com/problems/${error.type}`,
    title: getTitleForType(error.type),
    status: error.statusCode,
    detail: error.message,
    instance: req.originalUrl,
    timestamp: error.timestamp,
    requestId: req.id
  };

  // Dodanie szczegółów walidacji jeśli istnieją
  if (error.details) {
    problemDetails.errors = error.details;
  }

  res.status(error.statusCode).json(problemDetails);
};

const getTitleForType = (type) => {
  const titles = {
    'validation-error': 'Validation Error',
    'unauthorized': 'Unauthorized',
    'forbidden': 'Forbidden',
    'not-found': 'Not Found',
    'conflict': 'Conflict',
    'internal-error': 'Internal Server Error'
  };
  return titles[type] || 'Error';
};
```

### Walidacja z Detalami
```javascript
const validateUser = (userData) => {
  const errors = [];

  if (!userData.email) {
    errors.push({
      field: 'email',
      message: 'Email jest wymagany',
      code: 'REQUIRED'
    });
  } else if (!isValidEmail(userData.email)) {
    errors.push({
      field: 'email',
      message: 'Email ma nieprawidłowy format',
      code: 'INVALID_FORMAT'
    });
  }

  if (!userData.name) {
    errors.push({
      field: 'name',
      message: 'Nazwa jest wymagana',
      code: 'REQUIRED'
    });
  } else if (userData.name.length < 2) {
    errors.push({
      field: 'name',
      message: 'Nazwa musi mieć co najmniej 2 znaki',
      code: 'MIN_LENGTH'
    });
  }

  if (errors.length > 0) {
    throw new ValidationError('Dane wejściowe są nieprawidłowe', errors);
  }
};
```

### Mapowanie Statusów HTTP
```javascript
const statusCodeMap = {
  'validation-error': 400,
  'unauthorized': 401,
  'forbidden': 403,
  'not-found': 404,
  'conflict': 409,
  'unprocessable-entity': 422,
  'too-many-requests': 429,
  'internal-error': 500,
  'service-unavailable': 503
};
```

## Zalety

### Dla Deweloperów
- **Spójność** - jednolity format błędów
- **Informacyjność** - szczegółowe informacje o błędach
- **Debugowanie** - łatwiejsze lokalizowanie problemów
- **Standaryzacja** - zgodność z RFC 7807

### Dla Systemu
- **Monitorowanie** - lepsze logowanie błędów
- **Analiza** - możliwość analizy wzorców błędów
- **Automatyzacja** - automatyczne przetwarzanie błędów
- **Skalowalność** - spójne zachowanie w całym systemie

### Dla Użytkowników
- **Przejrzystość** - jasne komunikaty błędów
- **Pomoc** - wskazówki jak naprawić błędy
- **UX** - lepsze doświadczenie użytkownika

## Wady

### Złożoność
- **Implementacja** - wymaga więcej kodu
- **Konserwacja** - potrzeba utrzymania standardów
- **Nauka** - zespół musi poznać RFC 7807

### Ograniczenia
- **Rozmiar** - większe odpowiedzi błędów
- **Wydajność** - dodatkowe przetwarzanie
- **Kompatybilność** - starsze klienty mogą nie obsługiwać

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne
- Wymagana jest szczegółowa obsługa błędów
- Zespół ma doświadczenie z RFC 7807
- Potrzebne jest monitorowanie błędów
- API jest złożone i ma wiele typów błędów

### Nie używać gdy:
- API jest bardzo proste
- Zespół nie ma czasu na implementację
- Wymagana jest maksymalna wydajność
- Klienci nie obsługują Problem Details

## Powiązane Tematy/Wzorce

- [API Standards Compliance](./API_Standards_Compliance.md)
- [REST API Design](./REST_API_Design.md)
- [OpenAPI Specification](./OpenAPI_Specification.md)
- [API Security](./API_Security.md)
- [Logging and Monitoring](./Logging_Monitoring.md)

## Źródła / Dalsza Lektura

- [RFC 7807 - Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807)
- [HTTP Status Codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [Error Handling Best Practices](https://blog.restcase.com/rest-api-error-handling-best-practices/)
- [API Error Handling Patterns](https://www.oreilly.com/library/view/api-design-patterns/9781617295850/)
- [Problem Details in OpenAPI](https://swagger.io/docs/specification/describing-responses/)
