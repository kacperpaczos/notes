# Wzorzec State

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


Wzorzec State pozwala obiektowi zmieniać swoje zachowanie gdy zmienia się jego stan wewnętrzny. Wygląda to tak, jakby obiekt zmieniał swoją klasę.

## Zawartość katalogu

### Pliki

- [state_pattern.md](state_pattern.md) - Szczegółowy opis wzorca State
- [state_implementation.java](state_implementation.java) - Implementacja wzorca State w Javie

## Przykład użycia

Wzorzec State jest szczególnie przydatny w następujących sytuacjach:
- Gdy obiekt zmienia swoje zachowanie w zależności od stanu
- Gdy mamy wiele warunków if-else lub switch w kodzie
- Gdy chcemy enkapsulować zachowanie specyficzne dla stanu
- Gdy chcemy ułatwić dodawanie nowych stanów

## Diagram

```
Context
    |
    |-- request()
    |-- state
    |
State
    |
    |-- handle()
    |
ConcreteState
    |
    |-- handle()
```

## Przykłady zastosowań

- Automaty stanów
- Systemy workflow
- Gry komputerowe
- Systemy zarządzania dokumentami
- Systemy płatności

