# Memoization - Wzorce i Implementacje

## Cel
Kompleksowy przewodnik po memoization - technice optymalizacji polegającej na cachowaniu wyników funkcji dla danych argumentów.

## Problem
Potrzeba optymalizacji wydajności funkcji, które:
- Wykonują kosztowne obliczenia
- Są wywoływane wielokrotnie z tymi samymi argumentami
- Mają deterministyczne wyniki (pure functions)
- Mogą być zoptymalizowane przez cachowanie

## Pojęcia Kluczowe

### Memoization
- **Definicja** - technika cachowania wyników funkcji
- **Pure Functions** - funkcje bez efektów ubocznych
- **Cache Key** - unikalny identyfikator dla argumentów
- **Cache Hit** - wynik znaleziony w cache
- **Cache Miss** - wynik nieznany, trzeba obliczyć

### Wzorce Memoization
- **Simple Memoization** - podstawowe cachowanie
- **LRU Memoization** - z usuwaniem najrzadziej używanych
- **TTL Memoization** - z czasem wygaśnięcia
- **WeakMap Memoization** - z automatycznym garbage collection
- **Async Memoization** - dla funkcji asynchronicznych

### Zastosowania
- **Fibonacci** - klasyczny przykład
- **API Calls** - cachowanie odpowiedzi
- **Database Queries** - cachowanie zapytań
- **Expensive Calculations** - kosztowne obliczenia
- **Recursive Functions** - funkcje rekurencyjne

## Struktura / Diagram

```
Function Call
    ↓
Check Cache
    ↓ (cache miss)
Execute Function
    ↓
Store Result in Cache
    ↓
Return Result
```

## Przepływ Działania

### 1. Podstawowa Memoization
```javascript
// Prosta implementacja memoization
function memoize(fn) {
  const cache = new Map();
  
  return function(...args) {
    const key = JSON.stringify(args);
    
    if (cache.has(key)) {
      console.log('Cache hit');
      return cache.get(key);
    }
    
    console.log('Cache miss');
    const result = fn.apply(this, args);
    cache.set(key, result);
    return result;
  };
}

// Przykład użycia - Fibonacci
const fibonacci = memoize(function(n) {
  if (n < 2) return n;
  return fibonacci(n - 1) + fibonacci(n - 2);
});

console.log(fibonacci(10)); // Cache miss, oblicza
console.log(fibonacci(10)); // Cache hit, zwraca z cache
```

### 2. LRU Memoization
```javascript
class LRUMemoize {
  constructor(fn, maxSize = 100) {
    this.fn = fn;
    this.maxSize = maxSize;
    this.cache = new Map();
  }
  
  call(...args) {
    const key = JSON.stringify(args);
    
    if (this.cache.has(key)) {
      // Przenieś na koniec (najnowszy)
      const value = this.cache.get(key);
      this.cache.delete(key);
      this.cache.set(key, value);
      return value;
    }
    
    const result = this.fn.apply(this, args);
    
    // Usuń najstarszy element jeśli przekroczono limit
    if (this.cache.size >= this.maxSize) {
      const firstKey = this.cache.keys().next().value;
      this.cache.delete(firstKey);
    }
    
    this.cache.set(key, result);
    return result;
  }
}

// Użycie
const memoizedFib = new LRUMemoize(fibonacci, 50);
```

### 3. TTL Memoization
```javascript
class TTLMemoize {
  constructor(fn, ttl = 60000) { // 60 sekund domyślnie
    this.fn = fn;
    this.ttl = ttl;
    this.cache = new Map();
  }
  
  call(...args) {
    const key = JSON.stringify(args);
    const now = Date.now();
    
    if (this.cache.has(key)) {
      const { value, timestamp } = this.cache.get(key);
      
      if (now - timestamp < this.ttl) {
        return value; // Cache hit
      } else {
        this.cache.delete(key); // Wygaśnięty
      }
    }
    
    const result = this.fn.apply(this, args);
    this.cache.set(key, { value: result, timestamp: now });
    return result;
  }
}
```

## Przykłady Użycia

### JavaScript - Różne Implementacje
```javascript
// 1. Podstawowa memoization z WeakMap
function memoizeWeakMap(fn) {
  const cache = new WeakMap();
  
  return function(obj, ...args) {
    if (!cache.has(obj)) {
      cache.set(obj, new Map());
    }
    
    const objCache = cache.get(obj);
    const key = JSON.stringify(args);
    
    if (objCache.has(key)) {
      return objCache.get(key);
    }
    
    const result = fn.call(obj, ...args);
    objCache.set(key, result);
    return result;
  };
}

// 2. Memoization z custom key generator
function memoizeWithKey(fn, keyGenerator) {
  const cache = new Map();
  
  return function(...args) {
    const key = keyGenerator ? keyGenerator(...args) : JSON.stringify(args);
    
    if (cache.has(key)) {
      return cache.get(key);
    }
    
    const result = fn.apply(this, args);
    cache.set(key, result);
    return result;
  };
}

// 3. Async memoization
function memoizeAsync(fn) {
  const cache = new Map();
  const pending = new Map();
  
  return async function(...args) {
    const key = JSON.stringify(args);
    
    if (cache.has(key)) {
      return cache.get(key);
    }
    
    if (pending.has(key)) {
      return pending.get(key);
    }
    
    const promise = fn.apply(this, args);
    pending.set(key, promise);
    
    try {
      const result = await promise;
      cache.set(key, result);
      pending.delete(key);
      return result;
    } catch (error) {
      pending.delete(key);
      throw error;
    }
  };
}
```

### Python - Implementacje
```python
from functools import lru_cache, wraps
import time
from typing import Any, Callable, Dict, Tuple

# 1. Podstawowa memoization
def memoize(func: Callable) -> Callable:
    cache: Dict[Tuple, Any] = {}
    
    @wraps(func)
    def wrapper(*args, **kwargs):
        key = (args, tuple(sorted(kwargs.items())))
        
        if key in cache:
            return cache[key]
        
        result = func(*args, **kwargs)
        cache[key] = result
        return result
    
    return wrapper

# 2. LRU Cache z functools
@lru_cache(maxsize=128)
def fibonacci(n: int) -> int:
    if n < 2:
        return n
    return fibonacci(n - 1) + fibonacci(n - 2)

# 3. TTL Memoization
def memoize_ttl(ttl_seconds: int = 60):
    def decorator(func: Callable) -> Callable:
        cache: Dict[Tuple, Tuple[Any, float]] = {}
        
        @wraps(func)
        def wrapper(*args, **kwargs):
            key = (args, tuple(sorted(kwargs.items())))
            now = time.time()
            
            if key in cache:
                result, timestamp = cache[key]
                if now - timestamp < ttl_seconds:
                    return result
                else:
                    del cache[key]
            
            result = func(*args, **kwargs)
            cache[key] = (result, now)
            return result
        
        return wrapper
    return decorator

# Użycie
@memoize_ttl(ttl_seconds=300)  # 5 minut
def expensive_calculation(x: int, y: int) -> int:
    time.sleep(1)  # Symulacja kosztownej operacji
    return x * y * 1000
```

### Java - Implementacje
```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memoization {
    
    // 1. Podstawowa memoization
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        return new Function<T, R>() {
            private final Map<T, R> cache = new ConcurrentHashMap<>();
            
            @Override
            public R apply(T input) {
                return cache.computeIfAbsent(input, function);
            }
        };
    }
    
    // 2. Memoization z TTL
    public static <T, R> Function<T, R> memoizeWithTTL(
            Function<T, R> function, 
            long ttlMillis) {
        return new Function<T, R>() {
            private final Map<T, CacheEntry<R>> cache = new ConcurrentHashMap<>();
            
            @Override
            public R apply(T input) {
                CacheEntry<R> entry = cache.get(input);
                long now = System.currentTimeMillis();
                
                if (entry != null && (now - entry.timestamp) < ttlMillis) {
                    return entry.value;
                }
                
                R result = function.apply(input);
                cache.put(input, new CacheEntry<>(result, now));
                return result;
            }
        };
    }
    
    private static class CacheEntry<R> {
        final R value;
        final long timestamp;
        
        CacheEntry(R value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}

// Użycie
Function<Integer, Integer> memoizedFib = Memoization.memoize(n -> {
    if (n < 2) return n;
    return memoizedFib.apply(n - 1) + memoizedFib.apply(n - 2);
});
```

## Implementacja (Fragmenty Kodu)

### React - useMemo i useCallback
```javascript
import React, { useMemo, useCallback, useState } from 'react';

function ExpensiveComponent({ data, filter }) {
  // Memoizacja kosztownych obliczeń
  const filteredData = useMemo(() => {
    console.log('Computing filtered data...');
    return data.filter(item => item.category === filter);
  }, [data, filter]);
  
  // Memoizacja funkcji
  const handleClick = useCallback((id) => {
    console.log('Clicked item:', id);
  }, []);
  
  return (
    <div>
      {filteredData.map(item => (
        <div key={item.id} onClick={() => handleClick(item.id)}>
          {item.name}
        </div>
      ))}
    </div>
  );
}

// Custom hook do memoization
function useMemoizedValue(fn, deps) {
  return useMemo(fn, deps);
}

// Przykład użycia
function MyComponent({ userId }) {
  const userData = useMemoizedValue(
    () => fetchUserData(userId),
    [userId]
  );
  
  return <div>{userData?.name}</div>;
}
```

### Node.js - API Response Memoization
```javascript
const axios = require('axios');
const NodeCache = require('node-cache');

class APIService {
  constructor() {
    this.cache = new NodeCache({ stdTTL: 600 }); // 10 minut
  }
  
  async getUser(id) {
    const cacheKey = `user:${id}`;
    
    // Sprawdź cache
    let user = this.cache.get(cacheKey);
    if (user) {
      return user;
    }
    
    // Pobierz z API
    try {
      const response = await axios.get(`/api/users/${id}`);
      user = response.data;
      
      // Zapisz w cache
      this.cache.set(cacheKey, user);
      
      return user;
    } catch (error) {
      throw error;
    }
  }
  
  // Memoization dla funkcji asynchronicznych
  async memoizedApiCall(endpoint, params = {}) {
    const cacheKey = `${endpoint}:${JSON.stringify(params)}`;
    
    let result = this.cache.get(cacheKey);
    if (result) {
      return result;
    }
    
    result = await this.apiCall(endpoint, params);
    this.cache.set(cacheKey, result);
    
    return result;
  }
}
```

### Database Query Memoization
```javascript
class DatabaseService {
  constructor(db, cache) {
    this.db = db;
    this.cache = cache;
  }
  
  async getUserWithPosts(userId) {
    const cacheKey = `user_posts:${userId}`;
    
    // Sprawdź cache
    let result = await this.cache.get(cacheKey);
    if (result) {
      return result;
    }
    
    // Złożone zapytanie z JOIN
    const query = `
      SELECT u.*, p.title, p.content, p.created_at
      FROM users u
      LEFT JOIN posts p ON u.id = p.user_id
      WHERE u.id = ?
      ORDER BY p.created_at DESC
    `;
    
    result = await this.db.query(query, [userId]);
    
    // Zapisz w cache na 30 minut
    await this.cache.set(cacheKey, result, 1800);
    
    return result;
  }
}
```

## Zalety

### Wydajność
- **Szybkość** - szybsze wykonanie funkcji
- **Oszczędność zasobów** - mniej obliczeń
- **Skalowalność** - lepsze wykorzystanie CPU
- **Responsywność** - natychmiastowe odpowiedzi

### Optymalizacja
- **Algorytmy rekurencyjne** - eliminacja powtarzających się obliczeń
- **API calls** - redukcja liczby zapytań
- **Database queries** - cachowanie wyników zapytań
- **Expensive operations** - optymalizacja kosztownych operacji

## Wady

### Pamięć
- **Memory usage** - zużycie pamięci na cache
- **Memory leaks** - potencjalne wycieki pamięci
- **Cache size** - niekontrolowany rozmiar cache

### Złożoność
- **Debugging** - trudniejsze debugowanie
- **Side effects** - problemy z funkcjami z efektami ubocznymi
- **Cache invalidation** - trudności z unieważnianiem

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Funkcje są pure (bez efektów ubocznych)
- Kosztowne obliczenia
- Funkcje wywoływane wielokrotnie z tymi samymi argumentami
- Algorytmy rekurencyjne
- API calls i database queries

### Nie używać gdy:
- Funkcje z efektami ubocznymi
- Funkcje rzadko wywoływane
- Bardzo proste funkcje
- Funkcje z losowymi wynikami
- Ograniczona pamięć

## Powiązane Tematy/Wzorce

- [Caching Strategies](./Caching_Strategies.md)
- [Batching Strategies](./Batching_Strategies.md)
- [Performance Patterns](./Performance_Patterns.md)
- [Functional Programming](../02-programming/Functional%20Programing/)
- [Algorithm Optimization](../01-cs-fundamentals/algorithms/Algorithms_And_Optimization.md)

## Źródła / Dalsza Lektura

- [Memoization in JavaScript](https://www.freecodecamp.org/news/memoization-in-javascript/)
- [Python functools.lru_cache](https://docs.python.org/3/library/functools.html#functools.lru_cache)
- [React useMemo and useCallback](https://reactjs.org/docs/hooks-reference.html#usememo)
- [Functional Programming Patterns](https://www.oreilly.com/library/view/functional-programming-patterns/9781680503595/)
- [Algorithm Design Manual](https://www.algorist.com/)
