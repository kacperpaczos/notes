# Wzorzec Observer

## Cel

## Problem

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Zalety

## Wady

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


## 📋 Przegląd

Wzorzec Observer definiuje zależność jeden-do-wielu między obiektami, tak że gdy jeden obiekt (Subject) zmienia stan, wszystkie zależne od niego obiekty (Observers) są powiadamiane i automatycznie aktualizowane.

## 📖 Notatki

- **[Wzorzec Observer](observer_pattern.md)** - Kompletny przewodnik z przykładami w Java, Python, JavaScript
- **[Implementacja w Java](observer_implementation.java)** - Szczegółowa implementacja wzorca

## 🔄 Przepływ działania

1. **Rejestracja** - Observer rejestruje się u Subject
2. **Zmiana stanu** - Subject zmienia swój stan
3. **Powiadomienie** - Subject wywołuje notifyObservers()
4. **Aktualizacja** - Wszyscy zarejestrowani Observers otrzymują update()

## 🏗️ Struktura

```
Subject (Observable)
    |
    |-- registerObserver()
    |-- removeObserver()
    |-- notifyObservers()
    |
Observer
    |
    |-- update()
    |
ConcreteSubject
    |
    |-- getState()
    |-- setState()
    |
ConcreteObserver
    |
    |-- update()
```

## 📚 Przykłady zastosowań

### Frontend Development
- **React** — stan i efekty (reaktywność; nie klasyczny Observer)
- **Vue.js** — reactive data, watchers
- **Angular** — RxJS Observable, EventEmitter
- **DOM Events** — addEventListener/removeEventListener

### Backend Development
- **Spring Events** (Java) - ApplicationEventPublisher
- **Node.js** - EventEmitter
- **C#** - Events and Delegates
- **Python** - asyncio, callbacks

### Systemy
- **MVC Pattern** — Model powiadamia Widok o zmianach
- **Systemy powiadomień** - email, SMS, push notifications
- **Systemy monitorowania** - alerty, metryki
- **Systemy subskrypcji** - newsletter, RSS feeds

## 🔗 Warianty wzorca

- **Push vs Pull Model** - różne sposoby przekazywania danych
- **Event-Driven Observer** - reakcja na konkretne zdarzenia
- **Reactive Observer** - asynchroniczne powiadomienia

## 🎯 Kiedy używać

### ✅ Użyj gdy:
- Masz zależność jeden-do-wielu między obiektami
- Potrzebujesz luźnego powiązania między komponentami
- Chcesz dynamicznie zarządzać subskrypcjami
- Potrzebujesz automatycznej aktualizacji wielu obiektów

### ❌ Nie używaj gdy:
- Masz tylko jednego Observera
- Subject i Observer są ściśle powiązane
- Potrzebujesz gwarancji kolejności powiadomień
- Masz problemy z wydajnością przy dużej liczbie Observerów

