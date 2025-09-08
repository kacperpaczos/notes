# Przewodnik Implementacji API - Praktyczne Rekomendacje

## Cel
Praktyczny przewodnik implementacji API z konkretnymi przykładami kodu i rekomendacjami na podstawie analizy zgodności ze standardami.

## Problem
Potrzeba praktycznych wskazówek implementacji, które:
- Poprawią zgodność ze standardami
- Zwiększą jakość API
- Ułatwią utrzymanie i rozwój
- Zapewnią lepsze doświadczenie deweloperów

## Pojęcia Kluczowe

### Implementacja Standardów
- **Envelope Pattern** - spójny format odpowiedzi
- **Pagination** - stronicowanie wyników
- **Rate Limiting** - ograniczanie zapytań
- **Idempotency** - zapewnienie idempotencji
- **Caching** - optymalizacja wydajności
- **Validation** - walidacja danych

### Metryki Jakości
- **Spójność** - jednolity format odpowiedzi
- **Kompletność** - wszystkie wymagane pola
- **Dokumentacja** - przykłady i opisy
- **Bezpieczeństwo** - ochrona przed atakami
- **Wydajność** - optymalizacja transferu danych

## Struktura Implementacji

```
API Layer
├── Request Validation
├── Authentication/Authorization
├── Business Logic
├── Data Access
├── Response Formatting
├── Error Handling
├── Logging/Monitoring
└── Rate Limiting
```

## Przepływ Implementacji

### 1. Ujednolicenie Odpowiedzi (Envelope Pattern)
```javascript
// Struktura odpowiedzi
const createResponse = (data, meta = null) => ({
  data,
  meta: meta ? { ...meta } : undefined
});

// Przykład użycia
app.get('/api/v1/users', async (req, res) => {
  try {
    const users = await userService.getUsers(req.query);
    const pagination = await userService.getPagination(req.query);
    
    res.json(createResponse(users, { pagination }));
  } catch (error) {
    next(error);
  }
});
```

### 2. Implementacja Paginacji
```javascript
// Model paginacji
const PaginationMeta = {
  page: { type: 'integer', minimum: 1, default: 1 },
  limit: { type: 'integer', minimum: 1, maximum: 100, default: 20 },
  total: { type: 'integer', minimum: 0 },
  totalPages: { type: 'integer', minimum: 0 }
};

// Implementacja w serwisie
class UserService {
  async getUsers(query) {
    const { page = 1, limit = 20 } = query;
    const offset = (page - 1) * limit;
    
    const [users, total] = await Promise.all([
      User.find().skip(offset).limit(limit),
      User.countDocuments()
    ]);
    
    return {
      data: users,
      meta: {
        pagination: {
          page: parseInt(page),
          limit: parseInt(limit),
          total,
          totalPages: Math.ceil(total / limit)
        }
      }
    };
  }
}
```

### 3. Rate Limiting
```javascript
// Middleware rate limiting
const rateLimit = require('express-rate-limit');

const createRateLimit = (windowMs, max, message) => 
  rateLimit({
    windowMs,
    max,
    message: {
      error: 'Too Many Requests',
      message,
      retryAfter: Math.ceil(windowMs / 1000)
    },
    standardHeaders: true,
    legacyHeaders: false,
    handler: (req, res) => {
      res.status(429).json({
        error: 'Too Many Requests',
        message,
        retryAfter: Math.ceil(windowMs / 1000)
      });
    }
  });

// Zastosowanie
app.use('/api/v1/auth', createRateLimit(15 * 60 * 1000, 5, 'Zbyt wiele prób logowania'));
app.use('/api/v1/', createRateLimit(15 * 60 * 1000, 100, 'Zbyt wiele zapytań'));
```

### 4. Idempotencja
```javascript
// Middleware idempotencji
const idempotencyMiddleware = (req, res, next) => {
  const idempotencyKey = req.headers['idempotency-key'];
  
  if (req.method === 'POST' && idempotencyKey) {
    // Sprawdź czy operacja już została wykonana
    return checkIdempotency(idempotencyKey)
      .then(result => {
        if (result) {
          return res.status(200).json(result);
        }
        next();
      })
      .catch(next);
  }
  
  next();
};

// Implementacja w kontrolerze
app.post('/api/v1/users', idempotencyMiddleware, async (req, res, next) => {
  try {
    const user = await userService.createUser(req.body);
    
    // Zapisz wynik dla idempotencji
    if (req.headers['idempotency-key']) {
      await saveIdempotencyResult(req.headers['idempotency-key'], user);
    }
    
    res.status(201).json(createResponse(user));
  } catch (error) {
    next(error);
  }
});
```

## Przykłady Użycia

### Kompletny Endpoint z Wszystkimi Standardami
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

// Walidacja zapytań
const validateQuery = (req, res, next) => {
  const { page, limit, sort, order } = req.query;
  
  // Walidacja paginacji
  if (page && (isNaN(page) || page < 1)) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Page must be a positive integer'
    });
  }
  
  if (limit && (isNaN(limit) || limit < 1 || limit > 100)) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Limit must be between 1 and 100'
    });
  }
  
  // Walidacja sortowania
  if (sort && !['name', 'email', 'createdAt'].includes(sort)) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Invalid sort field'
    });
  }
  
  if (order && !['asc', 'desc'].includes(order)) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Order must be asc or desc'
    });
  }
  
  next();
};
```

### POST z Idempotencją i Walidacją
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

// Walidacja danych użytkownika
const validateUserData = (req, res, next) => {
  const { name, email, age } = req.body;
  const errors = [];
  
  if (!name || name.trim().length < 2) {
    errors.push({
      field: 'name',
      message: 'Name must be at least 2 characters',
      code: 'MIN_LENGTH'
    });
  }
  
  if (!email || !isValidEmail(email)) {
    errors.push({
      field: 'email',
      message: 'Valid email is required',
      code: 'INVALID_EMAIL'
    });
  }
  
  if (age && (isNaN(age) || age < 0 || age > 150)) {
    errors.push({
      field: 'age',
      message: 'Age must be between 0 and 150',
      code: 'INVALID_AGE'
    });
  }
  
  if (errors.length > 0) {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Invalid input data',
      errors
    });
  }
  
  next();
};
```

### PUT z Cachowaniem
```javascript
// PUT /api/v1/users/:id
app.put('/api/v1/users/:id',
  authenticateToken,
  validateUserData,
  async (req, res, next) => {
    try {
      const user = await userService.updateUser(req.params.id, req.body);
      
      // Ustaw nagłówki cache
      res.set({
        'ETag': `"${user.updatedAt.getTime()}"`,
        'Cache-Control': 'private, max-age=3600'
      });
      
      res.json(createResponse(user));
    } catch (error) {
      next(error);
    }
  }
);

// GET z obsługą ETag
app.get('/api/v1/users/:id',
  authenticateToken,
  async (req, res, next) => {
    try {
      const user = await userService.getUser(req.params.id);
      const etag = `"${user.updatedAt.getTime()}"`;
      
      // Sprawdź If-None-Match
      if (req.headers['if-none-match'] === etag) {
        return res.status(304).end();
      }
      
      res.set({
        'ETag': etag,
        'Cache-Control': 'private, max-age=3600'
      });
      
      res.json(createResponse(user));
    } catch (error) {
      next(error);
    }
  }
);
```

## Implementacja (Fragmenty Kodu)

### Kompletny Serwis z Paginacją
```javascript
class UserService {
  async getUsers(query) {
    const {
      page = 1,
      limit = 20,
      sort = 'createdAt',
      order = 'desc',
      status,
      role
    } = query;
    
    const offset = (page - 1) * limit;
    const sortOrder = order === 'desc' ? -1 : 1;
    
    // Budowanie filtra
    const filter = {};
    if (status) filter.status = status;
    if (role) filter.role = role;
    
    // Zapytanie do bazy danych
    const [users, total] = await Promise.all([
      User.find(filter)
        .sort({ [sort]: sortOrder })
        .skip(offset)
        .limit(limit)
        .select('-password'),
      User.countDocuments(filter)
    ]);
    
    return {
      data: users,
      meta: {
        pagination: {
          page: parseInt(page),
          limit: parseInt(limit),
          total,
          totalPages: Math.ceil(total / limit)
        }
      }
    };
  }
  
  async createUser(userData) {
    // Sprawdź duplikaty
    const existingUser = await User.findOne({ email: userData.email });
    if (existingUser) {
      throw new ConflictError('User with this email already exists');
    }
    
    const user = new User(userData);
    await user.save();
    
    return user.toObject({ transform: (doc, ret) => {
      delete ret.password;
      return ret;
    }});
  }
}
```

### Middleware do Obsługi Błędów
```javascript
const errorHandler = (error, req, res, next) => {
  // Logowanie błędu
  logger.error('API Error', {
    message: error.message,
    stack: error.stack,
    requestId: req.id,
    url: req.originalUrl,
    method: req.method,
    userAgent: req.get('User-Agent')
  });
  
  // Mapowanie błędów
  if (error.name === 'ValidationError') {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Invalid input data',
      errors: Object.values(error.errors).map(err => ({
        field: err.path,
        message: err.message,
        code: err.kind
      }))
    });
  }
  
  if (error.name === 'CastError') {
    return res.status(400).json({
      error: 'Validation Error',
      message: 'Invalid ID format'
    });
  }
  
  if (error.name === 'MongoError' && error.code === 11000) {
    return res.status(409).json({
      error: 'Conflict',
      message: 'Resource already exists'
    });
  }
  
  // Domyślny błąd serwera
  res.status(500).json({
    error: 'Internal Server Error',
    message: 'An unexpected error occurred'
  });
};
```

### Konfiguracja OpenAPI
```yaml
# openapi.yaml - przykład kompletnej konfiguracji
openapi: 3.0.3
info:
  title: User Management API
  version: 1.0.0
  description: API do zarządzania użytkownikami

servers:
  - url: https://api.example.com/v1
    description: Production server

paths:
  /users:
    get:
      summary: List users
      parameters:
        - name: page
          in: query
          schema:
            type: integer
            minimum: 1
            default: 1
        - name: limit
          in: query
          schema:
            type: integer
            minimum: 1
            maximum: 100
            default: 20
        - name: sort
          in: query
          schema:
            type: string
            enum: [name, email, createdAt]
            default: createdAt
        - name: order
          in: query
          schema:
            type: string
            enum: [asc, desc]
            default: desc
      responses:
        '200':
          description: Success
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/UsersResponse'
        '400':
          $ref: '#/components/responses/BadRequest'
        '401':
          $ref: '#/components/responses/Unauthorized'
        '429':
          $ref: '#/components/responses/TooManyRequests'

components:
  schemas:
    User:
      type: object
      required: [id, name, email]
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
        email:
          type: string
          format: email
        createdAt:
          type: string
          format: date-time
    
    UsersResponse:
      type: object
      properties:
        data:
          type: array
          items:
            $ref: '#/components/schemas/User'
        meta:
          type: object
          properties:
            pagination:
              $ref: '#/components/schemas/PaginationMeta'
    
    PaginationMeta:
      type: object
      properties:
        page:
          type: integer
        limit:
          type: integer
        total:
          type: integer
        totalPages:
          type: integer
  
  responses:
    BadRequest:
      description: Bad Request
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/Error'
    
    Unauthorized:
      description: Unauthorized
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/Error'
    
    TooManyRequests:
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
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/Error'
```

## Zalety

### Dla Deweloperów
- **Spójność** - jednolity format odpowiedzi
- **Przewidywalność** - standardowe zachowanie
- **Dokumentacja** - kompletne przykłady
- **Debugowanie** - lepsze logowanie błędów

### Dla Systemu
- **Wydajność** - optymalizacja zapytań
- **Skalowalność** - paginacja i cache
- **Bezpieczeństwo** - rate limiting i walidacja
- **Monitorowanie** - szczegółowe logi

### Dla Użytkowników
- **Szybkość** - cache i optymalizacje
- **Niezawodność** - obsługa błędów
- **Przejrzystość** - jasne komunikaty

## Wady

### Złożoność
- **Implementacja** - więcej kodu do napisania
- **Testowanie** - więcej przypadków testowych
- **Konserwacja** - potrzeba utrzymania standardów

### Wydajność
- **Overhead** - dodatkowe przetwarzanie
- **Rozmiar** - większe odpowiedzi
- **Złożoność** - więcej warstw abstrakcji

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API ma być publiczne
- Wymagana jest wysoka jakość
- Zespół ma doświadczenie
- Planowane jest długoterminowe utrzymanie

### Nie używać gdy:
- API jest prototypowe
- Zespół jest mały i niedoświadczony
- Czas implementacji jest krytyczny
- API ma krótki cykl życia

## Powiązane Tematy/Wzorce

- [API Standards Compliance](./API_Standards_Compliance.md)
- [OpenAPI Specification](./OpenAPI_Specification.md)
- [REST API Design](./REST_API_Design.md)
- [Error Handling](./Error_Handling.md)
- [API Security](./API_Security.md)

## Źródła / Dalsza Lektura

- [API Design Patterns](https://www.oreilly.com/library/view/api-design-patterns/9781617295850/)
- [REST API Best Practices](https://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/)
- [Express.js Documentation](https://expressjs.com/)
- [Node.js Best Practices](https://github.com/goldbergyoni/nodebestpractices)
- [API Security Best Practices](https://owasp.org/www-project-api-security/)
