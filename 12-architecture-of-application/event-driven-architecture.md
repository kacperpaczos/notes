# Architektura Event-Driven (Event-Driven Architecture - EDA)

## 📋 Przegląd

Event-Driven Architecture (EDA) to paradygmat architektoniczny, w którym przepływ danych i kontroli jest napędzany przez zdarzenia. Systemy event-driven są reaktywne, asynchroniczne i luźno powiązane.

## 🏗️ Komponenty architektury

### 1. Event Producers (Producenci zdarzeń)
- Komponenty generujące zdarzenia
- Mogą być użytkownicy, systemy zewnętrzne, czujniki, inne mikrousługi
- Nie znają bezpośrednio konsumentów zdarzeń

### 2. Event Consumers (Konsumenci zdarzeń)
- Komponenty przetwarzające zdarzenia
- Reagują na zdarzenia i wykonują odpowiednie akcje
- Mogą być wieloma dla jednego zdarzenia

### 3. Event Broker/Message Queue
- Pośredniczy w komunikacji między producentami a konsumentami
- Zapewnia niezawodność dostarczania
- Przykłady: Apache Kafka, RabbitMQ, AWS SQS

### 4. Event Store
- Przechowuje historię zdarzeń (w Event Sourcing)
- Umożliwia odtwarzanie stanu systemu
- Przykłady: EventStoreDB, Apache Kafka

### 5. Event Schema Registry
- Definiuje strukturę zdarzeń
- Zapewnia kompatybilność wersji
- Przykłady: Apache Avro Schema Registry, Confluent Schema Registry

## 🔄 Przepływ w architekturze event-driven

```
[Event Producer] → [Event] → [Event Broker] → [Event Consumers]
       ↓              ↓            ↓              ↓
   Generuje      Struktura    Dystrybucja    Przetwarzanie
   zdarzenie     danych       i routing      i reakcja
```

## 🎯 Wzorce architektoniczne

### Event Sourcing
- Stan aplikacji jako sekwencja zdarzeń
- Możliwość odtwarzania stanu w dowolnym momencie
- Audit trail i historia zmian

### CQRS (Command Query Responsibility Segregation)
- Oddzielenie operacji zapisu (Commands) od odczytu (Queries)
- Optymalizacja modeli dla różnych operacji
- Skalowanie niezależne dla zapisu i odczytu

### Saga Pattern
- Zarządzanie transakcjami rozproszonymi
- Kompensacja błędów w systemach rozproszonych
- Koordynacja długotrwałych procesów biznesowych

### Outbox Pattern
- Zapewnienie niezawodności dostarczania zdarzeń
- Atomowość operacji biznesowych i publikacji zdarzeń
- Eliminacja problemów z transakcjami rozproszonymi

## ✅ Zalety architektury event-driven

1. **Luźne powiązanie** - komponenty nie znają się bezpośrednio
2. **Skalowalność** - łatwe dodawanie nowych konsumentów
3. **Elastyczność** - dynamiczne reagowanie na zmiany
4. **Niezawodność** - możliwość ponownego przetwarzania zdarzeń
5. **Audyt** - pełna historia zdarzeń w systemie
6. **Asynchroniczność** - nieblokujące operacje

## ⚠️ Wyzwania i strategie rozwiązywania

### 1. **Kolejność zdarzeń (Event Ordering)**
**Problem:** W systemach rozproszonych zdarzenia mogą docierać w innej kolejności niż zostały wygenerowane.

**Strategie:**
- **Sequential IDs** - każdemu zdarzeniu przypisz sekwencyjny identyfikator
- **Vector Clocks** - śledzenie zależności między zdarzeniami
- **Event Sourcing** - przechowywanie zdarzeń w kolejności chronologicznej
- **Partitioning** - podział na partycje z gwarancją kolejności w ramach partycji

### 2. **Spójność danych (Data Consistency)**
**Problem:** Transakcje rozproszone i spójność między mikrousługami.

**Strategie:**
- **Saga Pattern** - koordynacja transakcji rozproszonych
- **Outbox Pattern** - atomowość operacji biznesowych i publikacji zdarzeń
- **Eventual Consistency** - akceptacja tymczasowej niespójności
- **CQRS** - oddzielenie modeli zapisu i odczytu

### 3. **Wydajność i skalowalność**
**Problem:** Overhead dystrybucji zdarzeń i bottlenecky.

**Strategies:**
- **Partitioning** - podział zdarzeń na partycje
- **Batching** - grupowanie zdarzeń w batchy
- **Async Processing** - asynchroniczne przetwarzanie
- **Caching** - cache'owanie często używanych danych

### 4. **Debugowanie i monitoring**
**Problem:** Trudne śledzenie przepływu zdarzeń w systemach rozproszonych.

**Strategies:**
- **Correlation IDs** - śledzenie przepływu przez system
- **Distributed Tracing** - narzędzia jak Jaeger, Zipkin
- **Event Logging** - szczegółowe logowanie zdarzeń
- **Event Replay** - możliwość odtwarzania zdarzeń

### 5. **Testowanie**
**Problem:** Trudne testowanie asynchronicznych przepływów.

**Strategies:**
- **Event Sourcing** - możliwość odtwarzania stanu
- **Integration Tests** - testy z rzeczywistymi event brokerami
- **Event Mocks** - mockowanie zdarzeń w testach
- **Contract Testing** - testowanie kontraktów zdarzeń

## 🔧 Narzędzia i technologie

### Message Brokers
- **Apache Kafka** - wysoko wydajny, rozproszony system kolejek
- **RabbitMQ** - elastyczny message broker z wieloma wzorcami routingu
- **Apache Pulsar** - cloud-native messaging platform
- **AWS SQS/SNS** - zarządzane usługi kolejkowania w chmurze

### Event Stores
- **EventStoreDB** - dedykowany event store
- **Apache Kafka** - może służyć jako event store
- **Axon Server** - event store dla frameworka Axon

### Frameworki i biblioteki
- **Spring Events** (Java) - framework do event-driven development
- **Node.js EventEmitter** - wbudowany system zdarzeń
- **Python asyncio** - asynchroniczne programowanie
- **.NET Event Grid** - event routing w Azure
- **Axon Framework** (Java) - CQRS i Event Sourcing

## 📊 Monitoring i observability

### Metryki do śledzenia
- Liczba zdarzeń na sekundę
- Czas przetwarzania zdarzeń
- Opóźnienia w dostarczaniu
- Liczba błędów przetwarzania
- Rozmiar kolejek

### Narzędzia
- **Prometheus + Grafana** - monitoring metryk
- **Jaeger/Zipkin** - distributed tracing
- **ELK Stack** - logowanie i analiza
- **Kafka Manager** - zarządzanie Kafka

## 🎯 Przykłady zastosowań

### E-commerce
- Zamówienia → płatności → wysyłka → powiadomienia
- Każdy krok jako osobne zdarzenie

### Mikrousługi
- Komunikacja między usługami przez zdarzenia
- Decoupling i niezależne skalowanie

### IoT
- Czujniki → przetwarzanie → akcje
- Reaktywne systemy automatyzacji

### Gaming
- Akcje gracza → aktualizacja stanu → powiadomienia
- Real-time multiplayer games 