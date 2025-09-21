# Strategie Cachowania - Kompleksowy Przewodnik

## Cel
Kompleksowy przewodnik po różnych strategiach cachowania w aplikacjach webowych, od cache przeglądarki po cache aplikacji.

## Problem
Potrzeba optymalizacji wydajności aplikacji poprzez:
- Zmniejszenie czasu ładowania stron
- Redukcję obciążenia serwerów
- Poprawę doświadczenia użytkownika
- Optymalizację wykorzystania zasobów

## Pojęcia Kluczowe

### Typy Cache
- **Browser Cache** - cache przeglądarki (HTTP cache)
- **CDN Cache** - cache sieci dystrybucji treści
- **Application Cache** - cache na poziomie aplikacji
- **Database Cache** - cache zapytań do bazy danych
- **Memory Cache** - cache w pamięci (Redis, Memcached)
- **Distributed Cache** - cache rozproszony

### Strategie Cache
- **Cache-Aside** - aplikacja zarządza cache
- **Write-Through** - zapis do cache i bazy jednocześnie
- **Write-Behind** - zapis do cache, później do bazy
- **Refresh-Ahead** - odświeżanie cache przed wygaśnięciem

### Polityki Wygaśnięcia
- **TTL (Time To Live)** - czas życia cache
- **LRU (Least Recently Used)** - usuwanie najrzadziej używanych
- **LFU (Least Frequently Used)** - usuwanie najrzadziej używanych
- **FIFO (First In, First Out)** - usuwanie najstarszych

## Struktura / Diagram

```
User Request
    ↓
Browser Cache
    ↓ (cache miss)
CDN Cache
    ↓ (cache miss)
Load Balancer
    ↓
Application Server
    ↓
Application Cache (Redis/Memcached)
    ↓ (cache miss)
Database
```

## Przepływ Działania

### 1. Browser Cache (HTTP Cache)
```http
# Pierwsze żądanie
GET /api/users HTTP/1.1
Host: api.example.com

# Odpowiedź z nagłówkami cache
HTTP/1.1 200 OK
Cache-Control: public, max-age=3600
ETag: "abc123"
Last-Modified: Wed, 21 Oct 2023 07:28:00 GMT

# Kolejne żądanie (z If-None-Match)
GET /api/users HTTP/1.1
Host: api.example.com
If-None-Match: "abc123"

# Odpowiedź 304 Not Modified
HTTP/1.1 304 Not Modified
Cache-Control: public, max-age=3600
ETag: "abc123"
```

### 2. Application Cache
```javascript
// Cache-Aside Pattern
class UserService {
  constructor(cache, userRepository) {
    this.cache = cache;
    this.userRepository = userRepository;
  }

  async getUser(id) {
    // 1. Sprawdź cache
    const cacheKey = `user:${id}`;
    let user = await this.cache.get(cacheKey);
    
    if (user) {
      return user; // Cache hit
    }
    
    // 2. Cache miss - pobierz z bazy
    user = await this.userRepository.findById(id);
    
    if (user) {
      // 3. Zapisz w cache
      await this.cache.set(cacheKey, user, 3600); // TTL: 1 godzina
    }
    
    return user;
  }
}
```

### 3. Write-Through Cache
```javascript
class UserService {
  async updateUser(id, userData) {
    // 1. Aktualizuj bazę danych
    const updatedUser = await this.userRepository.update(id, userData);
    
    // 2. Aktualizuj cache
    const cacheKey = `user:${id}`;
    await this.cache.set(cacheKey, updatedUser, 3600);
    
    return updatedUser;
  }
}
```

## Przykłady Użycia

### HTTP Cache Headers
```javascript
// Express.js - ustawianie nagłówków cache
app.get('/api/users', (req, res) => {
  const users = getUsersFromDatabase();
  
  // Cache na 1 godzinę
  res.set({
    'Cache-Control': 'public, max-age=3600',
    'ETag': generateETag(users),
    'Last-Modified': new Date().toUTCString()
  });
  
  res.json(users);
});

// Sprawdzanie If-None-Match
app.get('/api/users', (req, res) => {
  const users = getUsersFromDatabase();
  const currentETag = generateETag(users);
  const clientETag = req.headers['if-none-match'];
  
  if (clientETag === currentETag) {
    return res.status(304).end(); // Not Modified
  }
  
  res.set('ETag', currentETag);
  res.json(users);
});
```

### Redis Cache Implementation
```javascript
const redis = require('redis');
const client = redis.createClient();

class CacheService {
  async get(key) {
    try {
      const value = await client.get(key);
      return value ? JSON.parse(value) : null;
    } catch (error) {
      console.error('Cache get error:', error);
      return null;
    }
  }
  
  async set(key, value, ttl = 3600) {
    try {
      await client.setex(key, ttl, JSON.stringify(value));
    } catch (error) {
      console.error('Cache set error:', error);
    }
  }
  
  async del(key) {
    try {
      await client.del(key);
    } catch (error) {
      console.error('Cache delete error:', error);
    }
  }
  
  async invalidatePattern(pattern) {
    try {
      const keys = await client.keys(pattern);
      if (keys.length > 0) {
        await client.del(keys);
      }
    } catch (error) {
      console.error('Cache invalidation error:', error);
    }
  }
}
```

### Database Query Caching
```javascript
class DatabaseService {
  constructor(cache) {
    this.cache = cache;
  }
  
  async getUsersByRole(role) {
    const cacheKey = `users:role:${role}`;
    
    // Sprawdź cache
    let users = await this.cache.get(cacheKey);
    if (users) {
      return users;
    }
    
    // Pobierz z bazy danych
    users = await this.database.query(
      'SELECT * FROM users WHERE role = ?',
      [role]
    );
    
    // Zapisz w cache na 30 minut
    await this.cache.set(cacheKey, users, 1800);
    
    return users;
  }
}
```

## Implementacja (Fragmenty Kodu)

### Cache Middleware dla Express.js
```javascript
const cacheMiddleware = (ttl = 300) => {
  return async (req, res, next) => {
    const cacheKey = `cache:${req.method}:${req.originalUrl}`;
    
    try {
      // Sprawdź cache
      const cached = await cache.get(cacheKey);
      if (cached) {
        return res.json(cached);
      }
      
      // Przechwyć odpowiedź
      const originalSend = res.json;
      res.json = function(data) {
        // Zapisz w cache
        cache.set(cacheKey, data, ttl);
        return originalSend.call(this, data);
      };
      
      next();
    } catch (error) {
      console.error('Cache middleware error:', error);
      next();
    }
  };
};

// Użycie
app.get('/api/users', cacheMiddleware(600), getUsers);
```

### Cache Invalidation Strategies
```javascript
class CacheInvalidationService {
  constructor(cache) {
    this.cache = cache;
  }
  
  // Inwalidacja po aktualizacji użytkownika
  async invalidateUserCache(userId) {
    const patterns = [
      `user:${userId}`,
      `users:*`,
      `users:role:*`
    ];
    
    for (const pattern of patterns) {
      await this.cache.invalidatePattern(pattern);
    }
  }
  
  // Inwalidacja po zmianie roli
  async invalidateRoleCache(role) {
    await this.cache.invalidatePattern(`users:role:${role}`);
    await this.cache.invalidatePattern('users:*');
  }
  
  // Inwalidacja całego cache
  async invalidateAll() {
    await this.cache.flushAll();
  }
}
```

### Cache Warming (Preloading)
```javascript
class CacheWarmingService {
  constructor(cache, dataService) {
    this.cache = cache;
    this.dataService = dataService;
  }
  
  async warmUpCache() {
    console.log('Starting cache warming...');
    
    // Najczęściej używane dane
    const popularUsers = await this.dataService.getPopularUsers();
    for (const user of popularUsers) {
      await this.cache.set(`user:${user.id}`, user, 3600);
    }
    
    // Dane według ról
    const roles = ['admin', 'user', 'moderator'];
    for (const role of roles) {
      const users = await this.dataService.getUsersByRole(role);
      await this.cache.set(`users:role:${role}`, users, 1800);
    }
    
    console.log('Cache warming completed');
  }
}
```

## Zalety

### Wydajność
- **Szybkość** - szybsze odpowiedzi z cache
- **Skalowalność** - mniejsze obciążenie serwerów
- **Przepustowość** - obsługa większej liczby użytkowników
- **Oszczędność zasobów** - mniej zapytań do bazy danych

### Doświadczenie Użytkownika
- **Czas ładowania** - szybsze ładowanie stron
- **Responsywność** - natychmiastowe odpowiedzi
- **Niezawodność** - mniej błędów z powodu przeciążenia

### Koszty
- **Infrastruktura** - mniejsze obciążenie serwerów
- **Bandwidth** - mniej transferu danych
- **Energia** - mniejsze zużycie zasobów

## Wady

### Złożoność
- **Implementacja** - dodatkowy kod do zarządzania
- **Debugowanie** - trudniejsze śledzenie problemów
- **Synchronizacja** - problemy z spójnością danych

### Problemy
- **Stale Data** - przestarzałe dane w cache
- **Memory Usage** - zużycie pamięci
- **Cache Invalidation** - trudności z unieważnianiem

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Często odczytywane dane
- Kosztowne operacje (zapytania do bazy, API)
- Dane, które rzadko się zmieniają
- Wysokie obciążenie aplikacji

### Nie używać gdy:
- Dane często się zmieniają
- Bardzo małe aplikacje
- Krytyczne dane wymagające spójności
- Ograniczona pamięć

## Powiązane Tematy/Wzorce

- [Memoization](./Memoization_Patterns.md)
- [Batching Strategies](./Batching_Strategies.md)
- [Performance Optimization](./Performance_Patterns.md)
- [Database Optimization](../database-integration/INDEX.md)
- [CDN Configuration](../cdn/INDEX.md)

## Źródła / Dalsza Lektura

- [HTTP Caching](https://developer.mozilla.org/en-US/docs/Web/HTTP/Caching)
- [Redis Documentation](https://redis.io/documentation)
- [Cache Patterns](https://docs.aws.amazon.com/AmazonElastiCache/latest/mem-ug/Strategies.html)
- [Web Performance Best Practices](https://web.dev/performance/)
- [Caching Strategies](https://www.oreilly.com/library/view/designing-data-intensive-applications/9781491903063/)
