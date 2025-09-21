# Wzorce Optymalizacji Wydajności - Kompleksowy Przewodnik

## Cel
Kompleksowy przewodnik po wzorcach i technikach optymalizacji wydajności aplikacji webowych, od frontendu po backend.

## Problem
Potrzeba systematycznego podejścia do optymalizacji wydajności:
- Identyfikacja wąskich gardeł
- Wybór odpowiednich technik optymalizacji
- Implementacja sprawdzonych wzorców
- Monitorowanie i pomiar efektów
- Utrzymanie optymalizacji w czasie

## Pojęcia Kluczowe

### Poziomy Optymalizacji
- **Frontend Performance** - optymalizacja interfejsu użytkownika
- **Backend Performance** - optymalizacja serwera i API
- **Database Performance** - optymalizacja zapytań i bazy danych
- **Network Performance** - optymalizacja transferu danych
- **Infrastructure Performance** - optymalizacja infrastruktury

### Wzorce Optymalizacji
- **Lazy Loading** - opóźnione ładowanie zasobów
- **Code Splitting** - podział kodu na mniejsze części
- **Resource Optimization** - optymalizacja zasobów
- **Caching Strategies** - strategie cachowania
- **Compression** - kompresja danych

### Metryki Wydajności
- **Core Web Vitals** - kluczowe metryki webowe
- **First Contentful Paint (FCP)** - pierwsza treść
- **Largest Contentful Paint (LCP)** - największa treść
- **Cumulative Layout Shift (CLS)** - przesunięcia układu
- **First Input Delay (FID)** - opóźnienie pierwszego wejścia

## Struktura / Diagram

```
Performance Optimization
├── Frontend
│   ├── Lazy Loading
│   ├── Code Splitting
│   ├── Resource Optimization
│   └── Caching
├── Backend
│   ├── Database Optimization
│   ├── API Optimization
│   ├── Caching
│   └── Batching
├── Network
│   ├── CDN
│   ├── Compression
│   ├── HTTP/2
│   └── Preloading
└── Infrastructure
    ├── Load Balancing
    ├── Auto Scaling
    ├── Monitoring
    └── Profiling
```

## Przepływ Działania

### 1. Analiza Wydajności
```javascript
// Performance Monitoring
class PerformanceMonitor {
  constructor() {
    this.metrics = {};
    this.observers = [];
  }
  
  // Core Web Vitals
  measureWebVitals() {
    // LCP - Largest Contentful Paint
    new PerformanceObserver((entryList) => {
      const entries = entryList.getEntries();
      const lastEntry = entries[entries.length - 1];
      this.metrics.lcp = lastEntry.startTime;
    }).observe({ entryTypes: ['largest-contentful-paint'] });
    
    // FID - First Input Delay
    new PerformanceObserver((entryList) => {
      const entries = entryList.getEntries();
      entries.forEach((entry) => {
        this.metrics.fid = entry.processingStart - entry.startTime;
      });
    }).observe({ entryTypes: ['first-input'] });
    
    // CLS - Cumulative Layout Shift
    let clsValue = 0;
    new PerformanceObserver((entryList) => {
      for (const entry of entryList.getEntries()) {
        if (!entry.hadRecentInput) {
          clsValue += entry.value;
        }
      }
      this.metrics.cls = clsValue;
    }).observe({ entryTypes: ['layout-shift'] });
  }
  
  // Custom Metrics
  measureCustomMetric(name, startTime) {
    const endTime = performance.now();
    this.metrics[name] = endTime - startTime;
  }
}
```

### 2. Frontend Optimization
```javascript
// Lazy Loading Implementation
class LazyLoader {
  constructor(options = {}) {
    this.rootMargin = options.rootMargin || '50px';
    this.threshold = options.threshold || 0.1;
    this.observer = null;
    this.elements = new Set();
  }
  
  init() {
    this.observer = new IntersectionObserver(
      this.handleIntersection.bind(this),
      {
        rootMargin: this.rootMargin,
        threshold: this.threshold
      }
    );
  }
  
  observe(element) {
    this.elements.add(element);
    this.observer.observe(element);
  }
  
  handleIntersection(entries) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        this.loadElement(entry.target);
        this.observer.unobserve(entry.target);
        this.elements.delete(entry.target);
      }
    });
  }
  
  loadElement(element) {
    const src = element.dataset.src;
    if (src) {
      element.src = src;
      element.classList.add('loaded');
    }
  }
}

// Code Splitting
const LazyComponent = React.lazy(() => import('./HeavyComponent'));

function App() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <LazyComponent />
    </Suspense>
  );
}

// Resource Optimization
class ResourceOptimizer {
  static optimizeImages() {
    // WebP support detection
    const supportsWebP = document.createElement('canvas')
      .toDataURL('image/webp')
      .indexOf('data:image/webp') === 0;
    
    if (supportsWebP) {
      document.querySelectorAll('img[data-src]').forEach(img => {
        const webpSrc = img.dataset.src.replace(/\.(jpg|jpeg|png)$/, '.webp');
        img.src = webpSrc;
      });
    }
  }
  
  static preloadCriticalResources() {
    const criticalResources = [
      '/fonts/main.woff2',
      '/css/critical.css',
      '/js/main.js'
    ];
    
    criticalResources.forEach(resource => {
      const link = document.createElement('link');
      link.rel = 'preload';
      link.href = resource;
      link.as = resource.endsWith('.css') ? 'style' : 'script';
      document.head.appendChild(link);
    });
  }
}
```

### 3. Backend Optimization
```javascript
// Database Query Optimization
class OptimizedUserService {
  constructor(db, cache) {
    this.db = db;
    this.cache = cache;
  }
  
  // N+1 Problem Solution
  async getUsersWithPosts() {
    // Zamiast N+1 queries, użyj JOIN
    const query = `
      SELECT 
        u.id, u.name, u.email,
        p.id as post_id, p.title, p.content
      FROM users u
      LEFT JOIN posts p ON u.id = p.user_id
      ORDER BY u.id, p.created_at DESC
    `;
    
    const results = await this.db.query(query);
    
    // Grupuj wyniki
    return this.groupUsersWithPosts(results);
  }
  
  groupUsersWithPosts(results) {
    const usersMap = new Map();
    
    results.forEach(row => {
      if (!usersMap.has(row.id)) {
        usersMap.set(row.id, {
          id: row.id,
          name: row.name,
          email: row.email,
          posts: []
        });
      }
      
      if (row.post_id) {
        usersMap.get(row.id).posts.push({
          id: row.post_id,
          title: row.title,
          content: row.content
        });
      }
    });
    
    return Array.from(usersMap.values());
  }
  
  // Connection Pooling
  async getUsersWithConnectionPool() {
    const pool = await this.db.getPool();
    const connection = await pool.getConnection();
    
    try {
      const users = await connection.query('SELECT * FROM users');
      return users;
    } finally {
      connection.release();
    }
  }
}
```

## Przykłady Użycia

### Frontend Performance Patterns
```javascript
// 1. Virtual Scrolling
class VirtualScrollList {
  constructor(container, itemHeight, items) {
    this.container = container;
    this.itemHeight = itemHeight;
    this.items = items;
    this.visibleCount = Math.ceil(container.clientHeight / itemHeight);
    this.scrollTop = 0;
    
    this.init();
  }
  
  init() {
    this.container.addEventListener('scroll', this.handleScroll.bind(this));
    this.render();
  }
  
  handleScroll() {
    this.scrollTop = this.container.scrollTop;
    this.render();
  }
  
  render() {
    const startIndex = Math.floor(this.scrollTop / this.itemHeight);
    const endIndex = Math.min(startIndex + this.visibleCount, this.items.length);
    
    const visibleItems = this.items.slice(startIndex, endIndex);
    const offsetY = startIndex * this.itemHeight;
    
    this.container.innerHTML = `
      <div style="height: ${this.items.length * this.itemHeight}px; position: relative;">
        <div style="transform: translateY(${offsetY}px);">
          ${visibleItems.map(item => this.renderItem(item)).join('')}
        </div>
      </div>
    `;
  }
}

// 2. Debounced Search
class DebouncedSearch {
  constructor(input, callback, delay = 300) {
    this.input = input;
    this.callback = callback;
    this.delay = delay;
    this.timeout = null;
    
    this.input.addEventListener('input', this.handleInput.bind(this));
  }
  
  handleInput() {
    clearTimeout(this.timeout);
    this.timeout = setTimeout(() => {
      this.callback(this.input.value);
    }, this.delay);
  }
}

// 3. Image Lazy Loading with Intersection Observer
class ImageLazyLoader {
  constructor() {
    this.observer = new IntersectionObserver(
      this.handleIntersection.bind(this),
      { rootMargin: '50px' }
    );
  }
  
  observe(img) {
    this.observer.observe(img);
  }
  
  handleIntersection(entries) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const img = entry.target;
        img.src = img.dataset.src;
        img.classList.add('loaded');
        this.observer.unobserve(img);
      }
    });
  }
}
```

### Backend Performance Patterns
```javascript
// 1. Database Connection Pooling
class DatabasePool {
  constructor(config) {
    this.config = config;
    this.pool = null;
    this.maxConnections = config.maxConnections || 10;
    this.minConnections = config.minConnections || 2;
  }
  
  async initialize() {
    this.pool = await mysql.createPool({
      ...this.config,
      connectionLimit: this.maxConnections,
      acquireTimeout: 60000,
      timeout: 60000,
      reconnect: true
    });
  }
  
  async getConnection() {
    return new Promise((resolve, reject) => {
      this.pool.getConnection((err, connection) => {
        if (err) {
          reject(err);
        } else {
          resolve(connection);
        }
      });
    });
  }
  
  async query(sql, params) {
    const connection = await this.getConnection();
    try {
      const result = await connection.query(sql, params);
      return result;
    } finally {
      connection.release();
    }
  }
}

// 2. API Rate Limiting
class RateLimiter {
  constructor(options = {}) {
    this.windowMs = options.windowMs || 60000; // 1 minute
    this.maxRequests = options.maxRequests || 100;
    this.requests = new Map();
  }
  
  isAllowed(identifier) {
    const now = Date.now();
    const windowStart = now - this.windowMs;
    
    if (!this.requests.has(identifier)) {
      this.requests.set(identifier, []);
    }
    
    const userRequests = this.requests.get(identifier);
    
    // Remove old requests
    const validRequests = userRequests.filter(time => time > windowStart);
    
    if (validRequests.length >= this.maxRequests) {
      return false;
    }
    
    validRequests.push(now);
    this.requests.set(identifier, validRequests);
    
    return true;
  }
}

// 3. Caching with TTL
class TTLCache {
  constructor(defaultTTL = 3600000) { // 1 hour
    this.cache = new Map();
    this.defaultTTL = defaultTTL;
  }
  
  set(key, value, ttl = this.defaultTTL) {
    const expiresAt = Date.now() + ttl;
    this.cache.set(key, { value, expiresAt });
  }
  
  get(key) {
    const item = this.cache.get(key);
    
    if (!item) {
      return null;
    }
    
    if (Date.now() > item.expiresAt) {
      this.cache.delete(key);
      return null;
    }
    
    return item.value;
  }
  
  cleanup() {
    const now = Date.now();
    for (const [key, item] of this.cache) {
      if (now > item.expiresAt) {
        this.cache.delete(key);
      }
    }
  }
}
```

## Implementacja (Fragmenty Kodu)

### Performance Monitoring
```javascript
// Web Vitals Measurement
class WebVitalsMonitor {
  constructor() {
    this.metrics = {};
    this.init();
  }
  
  init() {
    this.measureLCP();
    this.measureFID();
    this.measureCLS();
    this.measureFCP();
  }
  
  measureLCP() {
    new PerformanceObserver((entryList) => {
      const entries = entryList.getEntries();
      const lastEntry = entries[entries.length - 1];
      this.metrics.lcp = lastEntry.startTime;
      this.reportMetric('LCP', lastEntry.startTime);
    }).observe({ entryTypes: ['largest-contentful-paint'] });
  }
  
  measureFID() {
    new PerformanceObserver((entryList) => {
      const entries = entryList.getEntries();
      entries.forEach((entry) => {
        const fid = entry.processingStart - entry.startTime;
        this.metrics.fid = fid;
        this.reportMetric('FID', fid);
      });
    }).observe({ entryTypes: ['first-input'] });
  }
  
  measureCLS() {
    let clsValue = 0;
    new PerformanceObserver((entryList) => {
      for (const entry of entryList.getEntries()) {
        if (!entry.hadRecentInput) {
          clsValue += entry.value;
        }
      }
      this.metrics.cls = clsValue;
      this.reportMetric('CLS', clsValue);
    }).observe({ entryTypes: ['layout-shift'] });
  }
  
  measureFCP() {
    new PerformanceObserver((entryList) => {
      const entries = entryList.getEntries();
      entries.forEach((entry) => {
        if (entry.name === 'first-contentful-paint') {
          this.metrics.fcp = entry.startTime;
          this.reportMetric('FCP', entry.startTime);
        }
      });
    }).observe({ entryTypes: ['paint'] });
  }
  
  reportMetric(name, value) {
    // Send to analytics
    if (typeof gtag !== 'undefined') {
      gtag('event', name, {
        event_category: 'Web Vitals',
        value: Math.round(value),
        non_interaction: true
      });
    }
  }
}
```

### Memory Optimization
```javascript
// Memory Leak Prevention
class MemoryOptimizer {
  static cleanupEventListeners() {
    // Remove event listeners when components unmount
    const cleanup = () => {
      document.removeEventListener('scroll', this.handleScroll);
      window.removeEventListener('resize', this.handleResize);
    };
    
    return cleanup;
  }
  
  static useWeakMap() {
    // Use WeakMap for object references
    const cache = new WeakMap();
    
    return {
      set: (obj, value) => cache.set(obj, value),
      get: (obj) => cache.get(obj)
    };
  }
  
  static debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
      const later = () => {
        clearTimeout(timeout);
        func(...args);
      };
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
    };
  }
  
  static throttle(func, limit) {
    let inThrottle;
    return function() {
      const args = arguments;
      const context = this;
      if (!inThrottle) {
        func.apply(context, args);
        inThrottle = true;
        setTimeout(() => inThrottle = false, limit);
      }
    };
  }
}
```

### Network Optimization
```javascript
// HTTP/2 Server Push
class HTTP2Optimizer {
  static enableServerPush(res, resources) {
    resources.forEach(resource => {
      res.push(resource.path, resource.options);
    });
  }
  
  static preloadCriticalResources() {
    const criticalResources = [
      { href: '/css/critical.css', as: 'style' },
      { href: '/js/main.js', as: 'script' },
      { href: '/fonts/main.woff2', as: 'font', type: 'font/woff2' }
    ];
    
    criticalResources.forEach(resource => {
      const link = document.createElement('link');
      link.rel = 'preload';
      link.href = resource.href;
      link.as = resource.as;
      if (resource.type) link.type = resource.type;
      document.head.appendChild(link);
    });
  }
  
  static optimizeImages() {
    // Responsive images with srcset
    const images = document.querySelectorAll('img[data-srcset]');
    images.forEach(img => {
      img.srcset = img.dataset.srcset;
      img.sizes = img.dataset.sizes || '100vw';
    });
  }
}
```

## Zalety

### Wydajność
- **Szybkość** - szybsze ładowanie i działanie
- **Responsywność** - lepsze doświadczenie użytkownika
- **Skalowalność** - obsługa większej liczby użytkowników
- **Efektywność** - lepsze wykorzystanie zasobów

### Biznesowe
- **SEO** - lepsze pozycje w wyszukiwarkach
- **Konwersja** - wyższe wskaźniki konwersji
- **Zadowolenie** - lepsze doświadczenie użytkownika
- **Koszty** - niższe koszty infrastruktury

## Wady

### Złożoność
- **Implementacja** - więcej kodu do napisania
- **Debugging** - trudniejsze debugowanie
- **Maintenance** - więcej kodu do utrzymania
- **Testing** - więcej przypadków testowych

### Problemy
- **Over-optimization** - zbyt agresywne optymalizacje
- **Compatibility** - problemy z kompatybilnością
- **Complexity** - zwiększona złożoność systemu

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Aplikacja ma problemy z wydajnością
- Wysokie obciążenie użytkowników
- Krytyczne metryki wydajności
- Konkurencyjne środowisko
- Długoterminowy projekt

### Nie używać gdy:
- Prototyp lub MVP
- Bardzo mała aplikacja
- Ograniczone zasoby na optymalizację
- Krótki cykl życia projektu
- Brak problemów z wydajnością

## Powiązane Tematy/Wzorce

- [Caching Strategies](./Caching_Strategies.md)
- [Memoization Patterns](./Memoization_Patterns.md)
- [Batching Strategies](./Batching_Strategies.md)
- [Code Splitting](../code-splitting/INDEX.md)
- [Lazy Loading](../lazy-loading/INDEX.md)
- [Compression](../compression/INDEX.md)

## Źródła / Dalsza Lektura

- [Web Performance Best Practices](https://web.dev/performance/)
- [Core Web Vitals](https://web.dev/vitals/)
- [High Performance JavaScript](https://www.oreilly.com/library/view/high-performance-javascript/9780596802806/)
- [Designing Data-Intensive Applications](https://www.oreilly.com/library/view/designing-data-intensive-applications/9781491903063/)
- [Performance Optimization Patterns](https://www.oreilly.com/library/view/patterns-of-enterprise-application-architecture/0321127420/)
