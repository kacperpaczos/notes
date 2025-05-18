# Kategorie wyrażeń w C++

W C++ istnieje kilka kategorii wyrażeń, które są klasyfikowane na podstawie ich wartości:

## 1. lvalue (locator value)
- Wyrażenia, które odnoszą się do lokalizacji w pamięci, której można przypisać wartość
- Przykłady:
  - zmienne
  - referencje
  - elementy tablicy

## 2. prvalue (pure rvalue)
- Wyrażenia, które nie mają stałej lokalizacji w pamięci
- Tymczasowe wartości, które nie są związane z żadnym obiektem
- Przykłady:
  - literały (np. 42, "hello")
  - wyniki operacji arytmetycznych
  - wywołania funkcji zwracające wartości

## 3. xvalue (expiring value)
- Wyrażenia, które reprezentują obiekty na końcu swojego życia i mogą być "przeniesione"
- Przykłady:
  - wynik funkcji zwracającej przez std::move
  - tymczasowy obiekt zwracany przez funkcję

## 4. glvalue (generalized lvalue)
- Kategoria obejmująca zarówno lvalue jak i xvalue
- Wyrażenia, które mają tożsamość:
  - związane z konkretną lokalizacją w pamięci
  - obiekty, które mogą być przeniesione

## 5. rvalue
- Kategoria obejmująca zarówno prvalue jak i xvalue
- Wyrażenia, które nie mają stałej lokalizacji w pamięci
- Kandydaci do optymalizacji przenoszenia

Te kategorie są używane do określenia, jak wyrażenia mogą być używane w różnych kontekstach, takich jak:
- przypisania
- przekazywanie argumentów do funkcji
- zwracanie wartości z funkcji
