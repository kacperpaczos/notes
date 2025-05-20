# Wzorzec Observer

Wzorzec Observer definiuje zależność jeden-do-wielu między obiektami, tak że gdy jeden obiekt zmienia stan, wszystkie zależne od niego obiekty są powiadamiane i automatycznie aktualizowane.

## Zawartość katalogu

### Pliki

- [observer_pattern.md](observer_pattern.md) - Szczegółowy opis wzorca Observer
- [observer_implementation.java](observer_implementation.java) - Implementacja wzorca Observer w Javie

## Przykład użycia

Wzorzec Observer jest szczególnie przydatny w następujących sytuacjach:
- Gdy jeden obiekt zmienia stan i inne obiekty muszą być powiadamiane
- Gdy nie chcemy ścisłego powiązania między obiektami
- Gdy chcemy dynamicznie dodawać i usuwać obserwatorów
- Gdy chcemy automatycznie aktualizować obserwatorów

## Diagram

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

## Przykłady zastosowań

- Systemy powiadomień
- Modele MVC
- Systemy event-driven
- Systemy subskrypcji
- Systemy monitorowania

