# Zasada Podstawienia Liskova (Liskov Substitution Principle)

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


## Wprowadzenie
Zasada podstawienia Liskova (LSP) jest jedną z fundamentalnych zasad SOLID w programowaniu obiektowym. Została sformułowana przez Barbarę Liskov w 1987 roku.

## Definicja
Jeśli klasa S jest podtypem klasy B, to obiekty klasy B mogą być zastąpione obiektami klasy S bez zmiany zachowania programu.

## Kluczowe Aspekty

### Dziedziczenie
- Dziedziczenie pozwala klasie na przejęcie cech i zachowań innej klasy
- Klasa bazowa (B) jest bardziej ogólna
- Klasa pochodna (S) jest bardziej wyspecjalizowana
- Klasa pochodna może dodawać własne cechy i zachowania

### Zasady Podstawienia
1. **Warunki wstępne (Preconditions)**
   - Klasa pochodna nie może wzmacniać warunków wstępnych metody
   - Nie może dodawać dodatkowych warunków do wywołania metody

2. **Warunki końcowe (Postconditions)**
   - Klasa pochodna nie może osłabiać warunków końcowych
   - Może je wzmacniać (np. dodając większą precyzję)

3. **Niezmienniki (Invariants)**
   - Niezmienniki klasy bazowej muszą być zachowane w klasie pochodnej
   - Klasa pochodna nie może zmieniać niezmiennych cech klasy bazowej

## Przykład
```java
// Klasa bazowa
class Sklep {
    public Produkt znajdzProdukt(String nazwa) {
        // Naiwne przeszukiwanie całej listy
        for (Produkt p : produkty) {
            if (p.getNazwa().equals(nazwa)) {
                return p;
            }
        }
        return null;
    }
}

// Klasa pochodna
class SklepZoptymalizowany extends Sklep {
    @Override
    public Produkt znajdzProdukt(String nazwa) {
        // Zoptymalizowane przeszukiwanie (np. przez mapę)
        return mapaProduktow.get(nazwa);
    }
}
```

## Korzyści
- Zapewnia spójność zachowania w hierarchii klas
- Ułatwia rozszerzanie systemu
- Poprawia reużywalność kodu
- Ułatwia testowanie

## Uwagi
- Zasada nie jest wymuszana przez języki programowania
- Nadpisywanie metod może być korzystne dla optymalizacji
- Ważne jest zachowanie semantyki metod, a nie tylko ich sygnatury 