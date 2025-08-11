# Referential Equality

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


#### Referential Equality

Jest to algorytm w react, który sprawdza czy dane obiekty zadeklarowane w pamięci, ś atym samym.
Jeśli nie są, a są pokazywane, należy wymusić mechanizm re-renderingu.

Kilka zasad:

The `Object.is()` [algorithm](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/is#description) determines whether two values are the same if:

1. Both values are `undefined` or `null`.
2. Both values are either `true` or `false`.
3. Both values are `Strings` having the same characters, length, and order.
4. Both values are `Numbers` with the same value or `NaN`.
5. Both values are `Objects` that point to one memory location.
