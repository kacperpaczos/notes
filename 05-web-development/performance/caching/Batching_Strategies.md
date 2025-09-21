# Strategie Batching - Optymalizacja Grupowania Operacji

## Cel
Kompleksowy przewodnik po technikach batching - grupowania operacji w celu optymalizacji wydajności i zmniejszenia obciążenia systemu.

## Problem
Potrzeba optymalizacji wydajności poprzez:
- Grupowanie wielu małych operacji w większe
- Zmniejszenie liczby round-trip'ów do bazy danych
- Redukcję obciążenia API
- Optymalizację transferu danych
- Poprawę throughput'u systemu

## Pojęcia Kluczowe

### Typy Batching
- **Database Batching** - grupowanie zapytań do bazy danych
- **API Batching** - grupowanie wywołań API
- **Request Batching** - grupowanie żądań HTTP
- **Message Batching** - grupowanie wiadomości w kolejkach
- **File Batching** - grupowanie operacji na plikach

### Strategie Batching
- **Time-based Batching** - grupowanie według czasu
- **Size-based Batching** - grupowanie według rozmiaru
- **Count-based Batching** - grupowanie według liczby elementów
- **Priority-based Batching** - grupowanie według priorytetu
- **Conditional Batching** - grupowanie warunkowe

### Wzorce Implementacji
- **Batch Processor** - procesor grupujący operacje
- **Batch Queue** - kolejka do grupowania
- **Batch Scheduler** - harmonogram grupujący
- **Batch Aggregator** - agregator operacji

## Struktura / Diagram

```
Individual Operations
    ↓
Batch Queue/Collector
    ↓
Batch Processor
    ↓
Optimized Execution
    ↓
Results Distribution
```

## Przepływ Działania

### 1. Database Batching
```javascript
class DatabaseBatchProcessor {
  constructor(db, batchSize = 100, flushInterval = 1000) {
    this.db = db;
    this.batchSize = batchSize;
    this.flushInterval = flushInterval;
    this.batch = [];
    this.timer = null;
  }
  
  async insert(record) {
    this.batch.push(record);
    
    if (this.batch.length >= this.batchSize) {
      await this.flush();
    } else if (!this.timer) {
      this.scheduleFlush();
    }
  }
  
  scheduleFlush() {
    this.timer = setTimeout(() => {
      this.flush();
    }, this.flushInterval);
  }
  
  async flush() {
    if (this.batch.length === 0) return;
    
    const batchToProcess = [...this.batch];
    this.batch = [];
    
    if (this.timer) {
      clearTimeout(this.timer);
      this.timer = null;
    }
    
    try {
      await this.db.batchInsert(batchToProcess);
      console.log(`Processed batch of ${batchToProcess.length} records`);
    } catch (error) {
      console.error('Batch insert failed:', error);
      // Retry logic or error handling
    }
  }
}
```

### 2. API Request Batching
```javascript
class APIRequestBatcher {
  constructor(apiClient, batchSize = 10, delay = 100) {
    this.apiClient = apiClient;
    this.batchSize = batchSize;
    this.delay = delay;
    this.pendingRequests = new Map();
    this.batchTimer = null;
  }
  
  async request(endpoint, data) {
    return new Promise((resolve, reject) => {
      const requestId = this.generateRequestId();
      
      this.pendingRequests.set(requestId, {
        endpoint,
        data,
        resolve,
        reject,
        timestamp: Date.now()
      });
      
      this.scheduleBatch();
    });
  }
  
  scheduleBatch() {
    if (this.batchTimer) return;
    
    this.batchTimer = setTimeout(() => {
      this.processBatch();
    }, this.delay);
  }
  
  async processBatch() {
    if (this.pendingRequests.size === 0) return;
    
    const requests = Array.from(this.pendingRequests.values());
    this.pendingRequests.clear();
    this.batchTimer = null;
    
    try {
      const results = await this.apiClient.batchRequest(requests);
      this.distributeResults(requests, results);
    } catch (error) {
      this.handleBatchError(requests, error);
    }
  }
  
  distributeResults(requests, results) {
    requests.forEach((request, index) => {
      if (results[index]) {
        request.resolve(results[index]);
      } else {
        request.reject(new Error('No result for request'));
      }
    });
  }
}
```

### 3. Message Queue Batching
```javascript
class MessageBatcher {
  constructor(producer, batchSize = 50, flushInterval = 5000) {
    this.producer = producer;
    this.batchSize = batchSize;
    this.flushInterval = flushInterval;
    this.messages = [];
    this.timer = null;
  }
  
  async sendMessage(topic, message) {
    this.messages.push({ topic, message, timestamp: Date.now() });
    
    if (this.messages.length >= this.batchSize) {
      await this.flush();
    } else if (!this.timer) {
      this.scheduleFlush();
    }
  }
  
  scheduleFlush() {
    this.timer = setTimeout(async () => {
      await this.flush();
    }, this.flushInterval);
  }
  
  async flush() {
    if (this.messages.length === 0) return;
    
    const batch = [...this.messages];
    this.messages = [];
    
    if (this.timer) {
      clearTimeout(this.timer);
      this.timer = null;
    }
    
    try {
      // Grupuj wiadomości według topic
      const groupedMessages = this.groupByTopic(batch);
      
      for (const [topic, messages] of groupedMessages) {
        await this.producer.sendBatch(topic, messages);
      }
      
      console.log(`Sent batch of ${batch.length} messages`);
    } catch (error) {
      console.error('Batch send failed:', error);
      // Retry or dead letter queue
    }
  }
  
  groupByTopic(messages) {
    return messages.reduce((groups, msg) => {
      if (!groups[msg.topic]) {
        groups[msg.topic] = [];
      }
      groups[msg.topic].push(msg);
      return groups;
    }, {});
  }
}
```

## Przykłady Użycia

### Database Batch Operations
```javascript
// 1. Batch Insert
class UserService {
  constructor(db) {
    this.db = db;
    this.batchProcessor = new DatabaseBatchProcessor(db);
  }
  
  async createUser(userData) {
    return this.batchProcessor.insert({
      type: 'INSERT',
      table: 'users',
      data: userData
    });
  }
  
  async createUsers(usersData) {
    // Bulk insert dla wielu użytkowników
    const query = `
      INSERT INTO users (name, email, created_at) 
      VALUES ${usersData.map(() => '(?, ?, ?)').join(', ')}
    `;
    
    const values = usersData.flatMap(user => [
      user.name, 
      user.email, 
      new Date()
    ]);
    
    return this.db.query(query, values);
  }
}

// 2. Batch Update
async function batchUpdateUsers(updates) {
  const cases = updates.map(update => 
    `WHEN id = ${update.id} THEN '${update.status}'`
  ).join(' ');
  
  const ids = updates.map(u => u.id).join(',');
  
  const query = `
    UPDATE users 
    SET status = CASE ${cases} END
    WHERE id IN (${ids})
  `;
  
  return db.query(query);
}
```

### API Response Batching
```javascript
// GraphQL DataLoader Pattern
class DataLoader {
  constructor(batchFn, options = {}) {
    this.batchFn = batchFn;
    this.batchSize = options.batchSize || 100;
    this.cache = options.cache !== false;
    this.cacheMap = new Map();
    this.batch = null;
  }
  
  load(key) {
    if (this.cache && this.cacheMap.has(key)) {
      return Promise.resolve(this.cacheMap.get(key));
    }
    
    if (!this.batch) {
      this.batch = new Batch(this);
    }
    
    return this.batch.add(key);
  }
  
  async dispatch() {
    if (!this.batch) return;
    
    const batch = this.batch;
    this.batch = null;
    
    const keys = batch.keys;
    const results = await this.batchFn(keys);
    
    keys.forEach((key, index) => {
      const result = results[index];
      if (this.cache) {
        this.cacheMap.set(key, result);
      }
      batch.promises[index].resolve(result);
    });
  }
}

// Użycie w GraphQL resolver
const userLoader = new DataLoader(async (userIds) => {
  const users = await db.query(
    'SELECT * FROM users WHERE id IN (?)',
    [userIds]
  );
  
  // Zwróć w tej samej kolejności co userIds
  return userIds.map(id => 
    users.find(user => user.id === id)
  );
});

// W resolver
const user = await userLoader.load(userId);
```

### File Processing Batching
```javascript
class FileBatchProcessor {
  constructor(processor, batchSize = 100) {
    this.processor = processor;
    this.batchSize = batchSize;
    this.queue = [];
    this.processing = false;
  }
  
  async addFile(filePath) {
    this.queue.push(filePath);
    
    if (this.queue.length >= this.batchSize) {
      await this.processBatch();
    }
  }
  
  async processBatch() {
    if (this.processing || this.queue.length === 0) return;
    
    this.processing = true;
    const batch = this.queue.splice(0, this.batchSize);
    
    try {
      await this.processor.processBatch(batch);
      console.log(`Processed ${batch.length} files`);
    } catch (error) {
      console.error('Batch processing failed:', error);
      // Retry or error handling
    } finally {
      this.processing = false;
      
      // Process remaining files
      if (this.queue.length > 0) {
        setImmediate(() => this.processBatch());
      }
    }
  }
}
```

## Implementacja (Fragmenty Kodu)

### Redis Pipeline Batching
```javascript
class RedisBatchProcessor {
  constructor(redisClient) {
    this.redis = redisClient;
    this.pipeline = null;
    this.operations = [];
  }
  
  async set(key, value) {
    this.operations.push({ type: 'SET', key, value });
    return this.flushIfNeeded();
  }
  
  async get(key) {
    this.operations.push({ type: 'GET', key });
    return this.flushIfNeeded();
  }
  
  async flushIfNeeded() {
    if (this.operations.length >= 100) {
      return this.flush();
    }
  }
  
  async flush() {
    if (this.operations.length === 0) return;
    
    const pipeline = this.redis.pipeline();
    const results = [];
    
    for (const op of this.operations) {
      switch (op.type) {
        case 'SET':
          pipeline.set(op.key, op.value);
          break;
        case 'GET':
          pipeline.get(op.key);
          break;
      }
    }
    
    const pipelineResults = await pipeline.exec();
    
    // Mapuj wyniki do operacji
    this.operations.forEach((op, index) => {
      results.push(pipelineResults[index]);
    });
    
    this.operations = [];
    return results;
  }
}
```

### HTTP Request Batching
```javascript
class HTTPRequestBatcher {
  constructor(baseURL, options = {}) {
    this.baseURL = baseURL;
    this.batchSize = options.batchSize || 20;
    this.delay = options.delay || 100;
    this.pendingRequests = new Map();
    this.timer = null;
  }
  
  async get(url, options = {}) {
    return this.request('GET', url, null, options);
  }
  
  async post(url, data, options = {}) {
    return this.request('POST', url, data, options);
  }
  
  async request(method, url, data, options) {
    return new Promise((resolve, reject) => {
      const requestId = this.generateId();
      
      this.pendingRequests.set(requestId, {
        method,
        url,
        data,
        options,
        resolve,
        reject
      });
      
      this.scheduleBatch();
    });
  }
  
  scheduleBatch() {
    if (this.timer) return;
    
    this.timer = setTimeout(() => {
      this.processBatch();
    }, this.delay);
  }
  
  async processBatch() {
    if (this.pendingRequests.size === 0) return;
    
    const requests = Array.from(this.pendingRequests.values());
    this.pendingRequests.clear();
    this.timer = null;
    
    try {
      // Grupuj według metody i endpoint
      const grouped = this.groupRequests(requests);
      
      for (const [key, group] of grouped) {
        await this.processGroup(key, group);
      }
    } catch (error) {
      this.handleBatchError(requests, error);
    }
  }
  
  groupRequests(requests) {
    return requests.reduce((groups, req) => {
      const key = `${req.method}:${req.url}`;
      if (!groups[key]) groups[key] = [];
      groups[key].push(req);
      return groups;
    }, {});
  }
}
```

### Database Connection Pooling z Batching
```javascript
class DatabaseConnectionPool {
  constructor(config) {
    this.config = config;
    this.pool = null;
    this.batchQueue = [];
    this.batchSize = 100;
    this.flushInterval = 1000;
  }
  
  async initialize() {
    this.pool = await mysql.createPool(this.config);
    
    // Flush batch co sekundę
    setInterval(() => {
      this.flushBatch();
    }, this.flushInterval);
  }
  
  async query(sql, params) {
    // Dodaj do batch queue
    this.batchQueue.push({ sql, params, timestamp: Date.now() });
    
    if (this.batchQueue.length >= this.batchSize) {
      await this.flushBatch();
    }
  }
  
  async flushBatch() {
    if (this.batchQueue.length === 0) return;
    
    const batch = [...this.batchQueue];
    this.batchQueue = [];
    
    const connection = await this.pool.getConnection();
    
    try {
      await connection.beginTransaction();
      
      for (const { sql, params } of batch) {
        await connection.query(sql, params);
      }
      
      await connection.commit();
      console.log(`Executed batch of ${batch.length} queries`);
    } catch (error) {
      await connection.rollback();
      console.error('Batch execution failed:', error);
    } finally {
      connection.release();
    }
  }
}
```

## Zalety

### Wydajność
- **Throughput** - większa przepustowość systemu
- **Latency** - mniejsze opóźnienia dzięki grupowaniu
- **Resource Usage** - lepsze wykorzystanie zasobów
- **Network Efficiency** - mniej round-trip'ów

### Skalowalność
- **Load Reduction** - mniejsze obciążenie serwerów
- **Connection Pooling** - lepsze wykorzystanie połączeń
- **Memory Efficiency** - optymalizacja pamięci
- **CPU Optimization** - mniej przełączeń kontekstu

## Wady

### Złożoność
- **Implementation** - bardziej skomplikowany kod
- **Error Handling** - trudniejsze obsługiwanie błędów
- **Debugging** - trudniejsze debugowanie
- **Testing** - więcej przypadków testowych

### Problemy
- **Memory Usage** - zużycie pamięci na kolejki
- **Latency** - potencjalne opóźnienia
- **Data Loss** - ryzyko utraty danych przy błędach
- **Complexity** - zwiększona złożoność systemu

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Duża liczba małych operacji
- Wysokie obciążenie systemu
- Kosztowne round-trip'y
- Operacje, które można grupować
- Systemy z wysokim throughput'em

### Nie używać gdy:
- Operacje wymagają natychmiastowej odpowiedzi
- Krytyczne operacje wymagające ACID
- Bardzo małe systemy
- Operacje z różnymi priorytetami
- Ograniczona pamięć

## Powiązane Tematy/Wzorce

- [Caching Strategies](./Caching_Strategies.md)
- [Memoization Patterns](./Memoization_Patterns.md)
- [Performance Patterns](./Performance_Patterns.md)
- [Database Optimization](../06-databases/INDEX.md)
- [Message Queues](../12-architecture-of-application/INDEX.md)

## Źródła / Dalsza Lektura

- [Database Batching Best Practices](https://docs.microsoft.com/en-us/azure/cosmos-db/bulk-executor-overview)
- [GraphQL DataLoader](https://github.com/graphql/dataloader)
- [Redis Pipeline](https://redis.io/docs/manual/pipelining/)
- [Batch Processing Patterns](https://www.oreilly.com/library/view/designing-data-intensive-applications/9781491903063/)
- [High Performance JavaScript](https://www.oreilly.com/library/view/high-performance-javascript/9780596802806/)
