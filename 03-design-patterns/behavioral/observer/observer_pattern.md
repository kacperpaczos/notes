# Wzorzec Observer

## 📋 Cel
Definiuje zależność jeden-do-wielu między obiektami, tak że gdy jeden obiekt (Subject) zmienia stan, wszystkie zależne od niego obiekty (Observers) są powiadamiane i automatycznie aktualizowane.

## 🎯 Problem
Masz system, w którym:
- Jeden obiekt (Subject) zmienia stan
- Inne obiekty (Observers) muszą być powiadamiane o tej zmianie
- Nie chcesz ścisłego powiązania między Subject a Observers
- Chcesz dynamicznie dodawać i usuwać Observers
- Potrzebujesz mechanizmu powiadamiania wielu obiektów o zmianach

## 🔧 Rozwiązanie
1. Zdefiniuj interfejs Subject z metodami do zarządzania observers
2. Zdefiniuj interfejs Observer z metodą update
3. Zaimplementuj konkretne klasy Subject i Observer
4. Subject powiadamia wszystkich observers o zmianach

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

## 🔄 Przepływ działania

1. **Rejestracja** - Observer rejestruje się u Subject
2. **Zmiana stanu** - Subject zmienia swój stan
3. **Powiadomienie** - Subject wywołuje notifyObservers()
4. **Aktualizacja** - Wszyscy zarejestrowani Observers otrzymują update()

## 📚 Przykłady użycia

### Frontend Development
- **React** - useState, useEffect hooks
- **Vue.js** - reactive data, watchers
- **Angular** - Observables, EventEmitter
- **DOM Events** - addEventListener, removeEventListener

### Backend Development
- **Spring Events** (Java) - ApplicationEventPublisher
- **Node.js** - EventEmitter
- **C#** - Events and Delegates
- **Python** - asyncio, callbacks

### Systemy
- **MVC Pattern** - Model notyfikuje View o zmianach
- **Systemy powiadomień** - email, SMS, push notifications
- **Systemy monitorowania** - alerty, metryki
- **Systemy subskrypcji** - newsletter, RSS feeds

## ✅ Zalety

- **Luźne powiązanie** - Subject nie zna szczegółów implementacji Observers
- **Wsparcie dla broadcast communication** - jeden Subject, wielu Observers
- **Dynamiczne zarządzanie** - łatwe dodawanie i usuwanie Observers
- **Automatyczna aktualizacja** - Observers aktualizują się automatycznie
- **Testowalność** - łatwe mockowanie Subject/Observer
- **Reużywalność** - Subject może być używany z różnymi Observerami

## ⚠️ Wady

- **Nieoczekiwane aktualizacje** - trudne do przewidzenia kolejności aktualizacji
- **Problemy z wydajnością** - przy dużej liczbie Observers
- **Trudne debugowanie** - złożone przepływy powiadomień
- **Memory leaks** - nieprawidłowe usuwanie Observers
- **Cykl zależności** - Subject może być Observerem innego Subjecta

## 🔧 Implementacje w różnych językach

### Java
```java
import java.util.ArrayList;
import java.util.List;

// Interfejsy
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

interface Observer {
    void update(String message);
}

// Konkretne implementacje
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}

class NewsChannel implements Observer {
    private String name;

    public NewsChannel(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " otrzymał wiadomość: " + news);
    }
}

// Użycie
public class Main {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();
        
        NewsChannel channel1 = new NewsChannel("TVN");
        NewsChannel channel2 = new NewsChannel("Polsat");
        
        agency.registerObserver(channel1);
        agency.registerObserver(channel2);
        
        agency.setNews("Ważne wydarzenie!");
    }
}
```

### Python
```python
from abc import ABC, abstractmethod
from typing import List

# Interfejsy
class Subject(ABC):
    @abstractmethod
    def register_observer(self, observer):
        pass
    
    @abstractmethod
    def remove_observer(self, observer):
        pass
    
    @abstractmethod
    def notify_observers(self):
        pass

class Observer(ABC):
    @abstractmethod
    def update(self, message):
        pass

# Konkretne implementacje
class WeatherStation(Subject):
    def __init__(self):
        self._observers: List[Observer] = []
        self._temperature = 0
    
    def register_observer(self, observer):
        self._observers.append(observer)
    
    def remove_observer(self, observer):
        self._observers.remove(observer)
    
    def notify_observers(self):
        for observer in self._observers:
            observer.update(self._temperature)
    
    def set_temperature(self, temperature):
        self._temperature = temperature
        self.notify_observers()

class WeatherDisplay(Observer):
    def __init__(self, name):
        self.name = name
    
    def update(self, temperature):
        print(f"{self.name}: Temperatura wynosi {temperature}°C")

# Użycie
if __name__ == "__main__":
    station = WeatherStation()
    
    display1 = WeatherDisplay("Termometr w salonie")
    display2 = WeatherDisplay("Termometr w kuchni")
    
    station.register_observer(display1)
    station.register_observer(display2)
    
    station.set_temperature(25)
```

### JavaScript
```javascript
class Subject {
    constructor() {
        this.observers = [];
    }
    
    registerObserver(observer) {
        this.observers.push(observer);
    }
    
    removeObserver(observer) {
        const index = this.observers.indexOf(observer);
        if (index > -1) {
            this.observers.splice(index, 1);
        }
    }
    
    notifyObservers(data) {
        this.observers.forEach(observer => observer.update(data));
    }
}

class Observer {
    constructor(name) {
        this.name = name;
    }
    
    update(data) {
        console.log(`${this.name} otrzymał: ${data}`);
    }
}

// Użycie
const subject = new Subject();
const observer1 = new Observer("Observer 1");
const observer2 = new Observer("Observer 2");

subject.registerObserver(observer1);
subject.registerObserver(observer2);

subject.notifyObservers("Ważna wiadomość!");
```

## 🔗 Warianty wzorca

### 1. **Push vs Pull Model**
- **Push** - Subject wysyła dane do Observerów
- **Pull** - Observerzy pobierają dane z Subjecta

### 2. **Event-Driven Observer**
- Używa konkretnych typów zdarzeń
- Observerzy reagują na określone zdarzenia

### 3. **Reactive Observer**
- Asynchroniczne powiadomienia
- Backpressure handling

## 🔗 Powiązane wzorce

- **Mediator Pattern** - koordynacja komunikacji między komponentami
- **Singleton Pattern** - Subject może być singletonem
- **Command Pattern** - enkapsulacja żądań jako obiektów
- **Event-Driven Architecture** - Observer jako podstawa EDA
- **Publish/Subscribe** - rozszerzenie Observer o broker

## 🎯 Kiedy używać

### ✅ Użyj gdy:
- Masz zależność jeden-do-wielu między obiektami
- Potrzebujesz luźnego powiązania między komponentami
- Chcesz dynamicznie zarządzać subskrypcjami
- Potrzebujesz automatycznej aktualizacji wielu obiektów

### ❌ Nie używaj gdy:
- Masz tylko jednego Observera (użyj bezpośredniego wywołania)
- Subject i Observer są ściśle powiązane
- Potrzebujesz gwarancji kolejności powiadomień
- Masz problemy z wydajnością przy dużej liczbie Observerów
