# Wzorzec Command

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


Wzorzec Command enkapsuluje żądanie jako obiekt, umożliwiając parametryzację klientów z różnymi żądaniami, kolejkowanie żądań, logowanie żądań oraz obsługę operacji cofania.

## Zawartość katalogu

### Pliki

- [command_pattern.md](command_pattern.md) - Szczegółowy opis wzorca Command
- [command_implementation.java](command_implementation.java) - Implementacja wzorca Command w Javie

## Przykład użycia

Wzorzec Command jest szczególnie przydatny w następujących sytuacjach:
- Gdy chcemy parametryzować obiekty operacjami
- Gdy chcemy kolejkować operacje
- Gdy chcemy wspierać operacje cofania
- Gdy chcemy logować operacje
- Gdy chcemy wspierać transakcje

## Diagram

```
Command
    |
    |-- execute()
    |
ConcreteCommand
    |
    |-- execute()
    |
Invoker
    |
    |-- setCommand()
    |-- executeCommand()
    |
Receiver
    |
    |-- action()
```

## Przykłady zastosowań

- Systemy menu
- Kolejkowanie zadań
- Operacje cofania
- Logowanie operacji
- Transakcje
- Makra

