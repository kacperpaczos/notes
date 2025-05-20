# Wzorzec Observer

## Cel
Definiuje zależność jeden-do-wielu między obiektami, tak że gdy jeden obiekt zmienia stan, wszystkie zależne od niego obiekty są powiadamiane i automatycznie aktualizowane.

## Problem
Masz system, w którym:
- Jeden obiekt (subject) zmienia stan
- Inne obiekty (observers) muszą być powiadamiane o tej zmianie
- Nie chcesz ścisłego powiązania między subject a observers
- Chcesz dynamicznie dodawać i usuwać observers

## Rozwiązanie
1. Zdefiniuj interfejs Subject z metodami do zarządzania observers
2. Zdefiniuj interfejs Observer z metodą update
3. Zaimplementuj konkretne klasy Subject i Observer
4. Subject powiadamia wszystkich observers o zmianach

## Struktura
```
Subject
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

## Przykład użycia
- Systemy powiadomień
- Modele MVC
- Systemy event-driven
- Systemy subskrypcji
- Systemy monitorowania

## Zalety
- Luźne powiązanie między subject a observers
- Wsparcie dla broadcast communication
- Dynamiczne dodawanie i usuwanie observers
- Automatyczna aktualizacja observers

## Wady
- Nieoczekiwane aktualizacje
- Może prowadzić do problemów z wydajnością
- Trudny do debugowania
- Może prowadzić do memory leaks

## Przykład implementacji
```java
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

public interface Observer {
    void update(String message);
}

public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state;

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
            observer.update(state);
        }
    }

    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }
}
```

## Powiązane wzorce
- Mediator Pattern - może być używany do koordynacji komunikacji
- Singleton Pattern - subject może być singletonem
- Command Pattern - może być używany do implementacji operacji update
