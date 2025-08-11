# Wzorzec Template Method

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


Wzorzec Template Method definiuje szkielet algorytmu w metodzie, odkładając niektóre kroki na podklasy. Template Method pozwala podklasom redefiniować określone kroki algorytmu bez zmiany jego struktury.

## Zawartość katalogu

### Pliki

- [template_method_pattern.md](template_method_pattern.md) - Szczegółowy opis wzorca Template Method
- [template_method_implementation.java](template_method_implementation.java) - Implementacja wzorca Template Method w Javie

## Przykład użycia

Wzorzec Template Method jest szczególnie przydatny w następujących sytuacjach:
- Gdy mamy algorytm składający się z kilku kroków
- Gdy niektóre kroki algorytmu są wspólne dla wszystkich implementacji
- Gdy chcemy zapewnić spójną strukturę algorytmu
- Gdy chcemy uniknąć duplikacji kodu

## Diagram

```
AbstractClass
    |
    |-- templateMethod()
    |-- primitiveOperation1()
    |-- primitiveOperation2()
    |
ConcreteClass
    |
    |-- primitiveOperation1()
    |-- primitiveOperation2()
```

