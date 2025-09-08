# Bezpieczeństwo API - OWASP API Security Top 10

## Cel
Kompleksowy przewodnik po bezpieczeństwie API zgodny z OWASP API Security Top 10 i najlepszymi praktykami branżowymi.

## Problem
Potrzeba zabezpieczenia API przed:
- Atakami na autoryzację i uwierzytelnianie
- Wyciekami danych
- Atakami na infrastrukturę
- Błędami konfiguracji
- Iniekcjami i innymi atakami

## Pojęcia Kluczowe

### OWASP API Security Top 10
1. **Broken Object Level Authorization (BOLA)** - złamana autoryzacja na poziomie obiektów
2. **Broken User Authentication** - złamane uwierzytelnianie użytkowników
3. **Excessive Data Exposure** - nadmierne ujawnianie danych
4. **Lack of Resources & Rate Limiting** - brak ograniczeń zasobów i rate limiting
5. **Broken Function Level Authorization** - złamana autoryzacja na poziomie funkcji
6. **Mass Assignment** - masowe przypisanie
7. **Security Misconfiguration** - błędna konfiguracja bezpieczeństwa
8. **Injection** - iniekcje
9. **Improper Assets Management** - nieprawidłowe zarządzanie zasobami
10. **Insufficient Logging & Monitoring** - niewystarczające logowanie i monitorowanie

### Mechanizmy Bezpieczeństwa
- **Authentication** - uwierzytelnianie (kim jesteś)
- **Authorization** - autoryzacja (co możesz robić)
- **Input Validation** - walidacja danych wejściowych
- **Output Sanitization** - sanityzacja danych wyjściowych
- **Rate Limiting** - ograniczanie zapytań
- **Encryption** - szyfrowanie danych

## Struktura Bezpieczeństwa

```
Client
    ↓ HTTPS/TLS
API Gateway
    ↓ Authentication
Rate Limiter
    ↓ Authorization
API Server
    ↓ Input Validation
Business Logic
    ↓ Output Sanitization
Database
```

## Przepływ Bezpieczeństwa

### 1. Uwierzytelnianie (Authentication)
```javascript
// JWT Token Validation
const authenticateToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];
  
  if (!token) {
    return res.status(401).json({
      error: 'Unauthorized',
      message: 'Access token is required'
    });
  }
  
  jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
    if (err) {
      return res.status(403).json({
        error: 'Forbidden',
        message: 'Invalid or expired token'
      });
    }
    
    req.user = user;
    next();
  });
};
```

### 2. Autoryzacja (Authorization)
```javascript
// Role-based Authorization
const authorize = (roles) => {
  return (req, res, next) => {
    if (!req.user) {
      return res.status(401).json({
        error: 'Unauthorized',
        message: 'User not authenticated'
      });
    }
    
    if (!roles.includes(req.user.role)) {
      return res.status(403).json({
        error: 'Forbidden',
        message: 'Insufficient permissions'
      });
    }
    
    next();
  };
};

// Resource-level Authorization
const authorizeResource = (resourceType) => {
  return async (req, res, next) => {
    const resourceId = req.params.id;
    const userId = req.user.id;
    
    try {
      const resource = await getResource(resourceType, resourceId);
      
      if (!resource) {
        return res.status(404).json({
          error: 'Not Found',
          message: 'Resource not found'
        });
      }
      
      // Sprawdź czy użytkownik ma dostęp do zasobu
      if (resource.userId !== userId && req.user.role !== 'admin') {
        return res.status(403).json({
          error: 'Forbidden',
          message: 'Access denied to this resource'
        });
      }
      
      req.resource = resource;
      next();
    } catch (error) {
      next(error);
    }
  };
};
```

### 3. Walidacja Danych Wejściowych
```javascript
// Input Validation Middleware
const validateInput = (schema) => {
  return (req, res, next) => {
    const { error, value } = schema.validate(req.body, {
      abortEarly: false,
      stripUnknown: true
    });
    
    if (error) {
      const errors = error.details.map(detail => ({
        field: detail.path.join('.'),
        message: detail.message,
        code: detail.type
      }));
      
      return res.status(400).json({
        error: 'Validation Error',
        message: 'Invalid input data',
        errors
      });
    }
    
    req.body = value;
    next();
  };
};

// Przykład schematu walidacji
const userSchema = Joi.object({
  name: Joi.string().min(2).max(100).required(),
  email: Joi.string().email().required(),
  age: Joi.number().integer().min(0).max(150),
  password: Joi.string().min(8).pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/)
});
```

## Przykłady Użycia

### 1. Broken Object Level Authorization (BOLA)
```javascript
// ❌ ZŁE - Brak sprawdzania uprawnień
app.get('/api/v1/users/:id', async (req, res) => {
  const user = await User.findById(req.params.id);
  res.json(user);
});

// ✅ DOBRE - Sprawdzanie uprawnień
app.get('/api/v1/users/:id', 
  authenticateToken,
  authorizeResource('user'),
  async (req, res) => {
    res.json(req.resource);
  }
);
```

### 2. Broken User Authentication
```javascript
// ❌ ZŁE - Słabe hasła
const createUser = async (userData) => {
  const user = new User({
    ...userData,
    password: userData.password // Bez hashowania
  });
  return await user.save();
};

// ✅ DOBRE - Silne hasła i hashowanie
const bcrypt = require('bcrypt');

const createUser = async (userData) => {
  const saltRounds = 12;
  const hashedPassword = await bcrypt.hash(userData.password, saltRounds);
  
  const user = new User({
    ...userData,
    password: hashedPassword
  });
  
  return await user.save();
};
```

### 3. Excessive Data Exposure
```javascript
// ❌ ZŁE - Zwracanie wszystkich danych
app.get('/api/v1/users/:id', async (req, res) => {
  const user = await User.findById(req.params.id);
  res.json(user); // Zwraca hasło, tokeny, itp.
});

// ✅ DOBRE - Filtrowanie danych
app.get('/api/v1/users/:id', async (req, res) => {
  const user = await User.findById(req.params.id)
    .select('-password -tokens -internalNotes');
  res.json(user);
});
```

### 4. Lack of Resources & Rate Limiting
```javascript
// ❌ ZŁE - Brak rate limiting
app.post('/api/v1/auth/login', async (req, res) => {
  // Możliwość brute force
});

// ✅ DOBRE - Rate limiting
const rateLimit = require('express-rate-limit');

const loginLimiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minut
  max: 5, // 5 prób na IP
  message: {
    error: 'Too Many Requests',
    message: 'Too many login attempts, try again later'
  },
  standardHeaders: true,
  legacyHeaders: false
});

app.post('/api/v1/auth/login', loginLimiter, async (req, res) => {
  // Logika logowania
});
```

### 5. Mass Assignment
```javascript
// ❌ ZŁE - Masowe przypisanie
app.put('/api/v1/users/:id', async (req, res) => {
  const user = await User.findByIdAndUpdate(
    req.params.id,
    req.body, // Może zawierać role, isAdmin, itp.
    { new: true }
  );
  res.json(user);
});

// ✅ DOBRE - Kontrolowane przypisanie
app.put('/api/v1/users/:id', 
  validateInput(updateUserSchema),
  async (req, res) => {
    const allowedFields = ['name', 'email', 'age'];
    const updateData = {};
    
    allowedFields.forEach(field => {
      if (req.body[field] !== undefined) {
        updateData[field] = req.body[field];
      }
    });
    
    const user = await User.findByIdAndUpdate(
      req.params.id,
      updateData,
      { new: true }
    );
    res.json(user);
  }
);
```

### 6. Security Misconfiguration
```javascript
// ❌ ZŁE - Błędna konfiguracja
app.use(cors({
  origin: '*', // Pozwala na wszystkie domeny
  credentials: true
}));

// ✅ DOBRE - Bezpieczna konfiguracja
app.use(cors({
  origin: process.env.ALLOWED_ORIGINS.split(','),
  credentials: true,
  optionsSuccessStatus: 200
}));

// Helmet.js dla nagłówków bezpieczeństwa
const helmet = require('helmet');
app.use(helmet({
  contentSecurityPolicy: {
    directives: {
      defaultSrc: ["'self'"],
      styleSrc: ["'self'", "'unsafe-inline'"],
      scriptSrc: ["'self'"],
      imgSrc: ["'self'", "data:", "https:"]
    }
  }
}));
```

### 7. Injection
```javascript
// ❌ ZŁE - SQL Injection
app.get('/api/v1/users', async (req, res) => {
  const query = `SELECT * FROM users WHERE name = '${req.query.name}'`;
  const users = await db.query(query);
  res.json(users);
});

// ✅ DOBRE - Parametryzowane zapytania
app.get('/api/v1/users', async (req, res) => {
  const users = await User.find({ name: req.query.name });
  res.json(users);
});

// Walidacja NoSQL Injection
const mongoSanitize = require('express-mongo-sanitize');
app.use(mongoSanitize());
```

### 8. Improper Assets Management
```javascript
// ❌ ZŁE - Brak wersjonowania API
app.get('/api/users', (req, res) => {
  // Stara wersja bez zabezpieczeń
});

// ✅ DOBRE - Wersjonowanie i deprecation
app.get('/api/v1/users', (req, res) => {
  // Aktualna wersja
});

app.get('/api/v2/users', (req, res) => {
  // Nowa wersja z lepszymi zabezpieczeniami
});
```

### 9. Insufficient Logging & Monitoring
```javascript
// ❌ ZŁE - Brak logowania
app.post('/api/v1/auth/login', async (req, res) => {
  // Brak logów
});

// ✅ DOBRE - Kompletne logowanie
const winston = require('winston');

const logger = winston.createLogger({
  level: 'info',
  format: winston.format.combine(
    winston.format.timestamp(),
    winston.format.json()
  ),
  transports: [
    new winston.transports.File({ filename: 'error.log', level: 'error' }),
    new winston.transports.File({ filename: 'combined.log' })
  ]
});

app.post('/api/v1/auth/login', async (req, res) => {
  const { email, password } = req.body;
  
  try {
    const user = await authenticateUser(email, password);
    
    logger.info('Successful login', {
      userId: user.id,
      email: user.email,
      ip: req.ip,
      userAgent: req.get('User-Agent'),
      timestamp: new Date().toISOString()
    });
    
    res.json({ token: generateToken(user) });
  } catch (error) {
    logger.warn('Failed login attempt', {
      email,
      ip: req.ip,
      userAgent: req.get('User-Agent'),
      error: error.message,
      timestamp: new Date().toISOString()
    });
    
    res.status(401).json({
      error: 'Unauthorized',
      message: 'Invalid credentials'
    });
  }
});
```

## Implementacja (Fragmenty Kodu)

### Kompletny Middleware Bezpieczeństwa
```javascript
const securityMiddleware = {
  // CORS Configuration
  cors: (req, res, next) => {
    const allowedOrigins = process.env.ALLOWED_ORIGINS.split(',');
    const origin = req.headers.origin;
    
    if (allowedOrigins.includes(origin)) {
      res.setHeader('Access-Control-Allow-Origin', origin);
    }
    
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    res.setHeader('Access-Control-Allow-Credentials', 'true');
    
    if (req.method === 'OPTIONS') {
      res.sendStatus(200);
    } else {
      next();
    }
  },
  
  // Rate Limiting
  rateLimit: (windowMs, max, message) => {
    const attempts = new Map();
    
    return (req, res, next) => {
      const key = req.ip;
      const now = Date.now();
      const windowStart = now - windowMs;
      
      // Wyczyść stare próby
      if (attempts.has(key)) {
        const userAttempts = attempts.get(key).filter(time => time > windowStart);
        attempts.set(key, userAttempts);
      } else {
        attempts.set(key, []);
      }
      
      const userAttempts = attempts.get(key);
      
      if (userAttempts.length >= max) {
        return res.status(429).json({
          error: 'Too Many Requests',
          message,
          retryAfter: Math.ceil(windowMs / 1000)
        });
      }
      
      userAttempts.push(now);
      next();
    };
  },
  
  // Input Sanitization
  sanitize: (req, res, next) => {
    // Usuń potencjalnie niebezpieczne znaki
    const sanitizeObject = (obj) => {
      for (const key in obj) {
        if (typeof obj[key] === 'string') {
          obj[key] = obj[key]
            .replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, '')
            .replace(/javascript:/gi, '')
            .replace(/on\w+\s*=/gi, '');
        } else if (typeof obj[key] === 'object' && obj[key] !== null) {
          sanitizeObject(obj[key]);
        }
      }
    };
    
    sanitizeObject(req.body);
    sanitizeObject(req.query);
    sanitizeObject(req.params);
    
    next();
  }
};
```

### Konfiguracja Bezpieczeństwa
```javascript
// app.js - Konfiguracja bezpieczeństwa
const express = require('express');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const mongoSanitize = require('express-mongo-sanitize');

const app = express();

// Helmet.js - nagłówki bezpieczeństwa
app.use(helmet({
  contentSecurityPolicy: {
    directives: {
      defaultSrc: ["'self'"],
      styleSrc: ["'self'", "'unsafe-inline'"],
      scriptSrc: ["'self'"],
      imgSrc: ["'self'", "data:", "https:"]
    }
  },
  hsts: {
    maxAge: 31536000,
    includeSubDomains: true,
    preload: true
  }
}));

// Rate limiting
app.use(rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minut
  max: 100, // 100 zapytań na IP
  message: {
    error: 'Too Many Requests',
    message: 'Too many requests from this IP'
  }
}));

// Sanityzacja MongoDB
app.use(mongoSanitize());

// Parsowanie JSON z limitem
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true, limit: '10mb' }));

// CORS
app.use(securityMiddleware.cors);

// Sanityzacja danych
app.use(securityMiddleware.sanitize);
```

## Zalety

### Bezpieczeństwo
- **Ochrona** - przed typowymi atakami
- **Zgodność** - z standardami OWASP
- **Audyt** - możliwość audytu bezpieczeństwa
- **Monitorowanie** - wykrywanie ataków

### Jakość
- **Niezawodność** - mniej błędów bezpieczeństwa
- **Zaufanie** - użytkownicy ufają API
- **Compliance** - zgodność z regulacjami
- **Reputacja** - lepsza reputacja produktu

## Wady

### Złożoność
- **Implementacja** - więcej kodu do napisania
- **Testowanie** - więcej przypadków testowych
- **Konserwacja** - potrzeba aktualizacji zabezpieczeń

### Wydajność
- **Overhead** - dodatkowe przetwarzanie
- **Latencja** - większe opóźnienia
- **Zasoby** - więcej zużycia CPU/memory

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- API obsługuje wrażliwe dane
- API jest publiczne
- Wymagana jest zgodność z regulacjami
- Planowane jest długoterminowe utrzymanie

### Nie używać gdy:
- API jest wewnętrzne i proste
- Zespół nie ma doświadczenia
- Czas implementacji jest krytyczny
- API ma krótki cykl życia

## Powiązane Tematy/Wzorce

- [API Standards Compliance](./API_Standards_Compliance.md)
- [Error Handling](./Error_Handling.md)
- [API Implementation Guide](./API_Implementation_Guide.md)
- [Authentication & Authorization](./Authentication_Authorization.md)
- [Logging and Monitoring](./Logging_Monitoring.md)

## Źródła / Dalsza Lektura

- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [OWASP Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [API Security Best Practices](https://owasp.org/www-project-api-security/)
- [JWT Security Best Practices](https://tools.ietf.org/html/rfc8725)
- [Node.js Security Best Practices](https://nodejs.org/en/docs/guides/security/)
