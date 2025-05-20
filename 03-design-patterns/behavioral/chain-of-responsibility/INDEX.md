# Wzorzec Chain of Responsibility

Wzorzec Chain of Responsibility pozwala uniknąć ścisłego powiązania nadawcy żądania z jego odbiorcą, dając szansę obsłużenia żądania wielu obiektom. Łańcuch obiektów odbiorczych jest tworzony przez przekazywanie żądania od jednego obiektu do następnego w łańcuchu.

## Zawartość katalogu

### Pliki

- [chain_of_responsibility_pattern.md](chain_of_responsibility_pattern.md) - Szczegółowy opis wzorca Chain of Responsibility
- [chain_of_responsibility_implementation.java](chain_of_responsibility_implementation.java) - Implementacja wzorca Chain of Responsibility w Javie

## Przykład użycia

Wzorzec Chain of Responsibility jest szczególnie przydatny w następujących sytuacjach:
- Gdy mamy wiele obiektów, które mogą obsłużyć żądanie
- Gdy nie wiemy z góry, który obiekt powinien obsłużyć żądanie
- Gdy chcemy uniknąć ścisłego powiązania między nadawcą a odbiorcą
- Gdy chcemy dynamicznie zmieniać łańcuch obsługi żądań

## Diagram

```
Handler
    |
    |-- handleRequest()
    |-- setNext()
    |
ConcreteHandler
    |
    |-- handleRequest()
```

## Przykłady zastosowań

- Systemy logowania
- Obsługa wyjątków
- Filtry HTTP
- Systemy autoryzacji
- Pipeline przetwarzania danych

