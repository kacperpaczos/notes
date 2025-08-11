# Wzorzec Singleton

## Problem

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Wady

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


## Definicja
Singleton to wzorzec kreacyjny, który zapewnia istnienie tylko jednej instancji danej klasy oraz dostarcza globalny punkt dostępu do tej instancji.

## Cel
- Zagwarantowanie, że klasa ma tylko jedną instancję
- Zapewnienie globalnego dostępu do tej instancji z dowolnego miejsca w programie
- Kontrola nad tworzeniem instancji

## Przykłady zastosowań
- Preferencje aplikacji (np. ustawienia gry)
- Kolejka wydruku
- Sterowniki urządzeń
- Połączenia z bazą danych
- Cache aplikacji

## Problem, który rozwiązuje
Wyobraźmy sobie aplikację mobilną z grą w pokera, która ma klasę preferencji przechowującą ustawienia użytkownika:
- Wizualne elementy (kolor stołu do gry, wzór na rewersie kart)
- Ustawienia dźwięku
- Inne preferencje

Istnienie wielu instancji klasy preferencji mogłoby prowadzić do:
- Niejasności, która instancja przechowuje aktualne wybory użytkownika
- Niespójności w wyświetlaniu gry
- Konfliktów w ustawieniach

## Implementacja

### Podstawowa struktura
```java
public class Singleton {
    // Prywatna statyczna zmienna przechowująca jedyną instancję
    private static Singleton uniqueInstance;
    
    // Prywatny konstruktor - uniemożliwia tworzenie instancji z zewnątrz
    private Singleton() {
        // inicjalizacja
    }
    
    // Publiczna metoda dostępowa (zamiast konstruktora)
    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
    
    // Metody biznesowe klasy
    public void doSomething() {
        // logika
    }
}
```

### Kluczowe elementy implementacji
1. **Prywatny konstruktor** - uniemożliwia tworzenie instancji z zewnątrz klasy
2. **Prywatna statyczna zmienna** (`uniqueInstance`) - przechowuje jedyną instancję klasy
3. **Publiczna statyczna metoda** (`getInstance()`) - sprawdza, czy instancja już istnieje:
   - Jeśli nie istnieje (`null`) - tworzy nową instancję
   - Jeśli już istnieje - zwraca istniejącą instancję

### Koncepcja "leniwego tworzenia" (lazy creation)
- Instancja nie jest tworzona od razu, tylko wtedy, gdy jest potrzebna
- Oszczędność zasobów, szczególnie gdy obiekt jest duży lub kosztowny w tworzeniu
- Tworzenie następuje dopiero przy pierwszym wywołaniu `getInstance()`

## Zalety
- Gwarancja jednej instancji - zapobiega konfliktom i niespójnościom
- Globalny dostęp - łatwy dostęp z dowolnego miejsca w programie
- Oszczędność zasobów - kontrola nad tworzeniem i cyklem życia obiektu
- Leniwe inicjowanie - obiekt tworzony tylko gdy jest potrzebny

## Wady i zagrożenia
- Problemy w środowisku wielowątkowym - potrzeba dodatkowej synchronizacji
- Potencjalne utrudnienie testowania - globalne stany trudniej testować
- Łamanie zasady pojedynczej odpowiedzialności - klasa zajmuje się swoją logiką biznesową oraz zarządzaniem własną instancją

## Warianty implementacji
### Dla środowisk wielowątkowych
```java
// Wersja z synchronizacją
public static synchronized Singleton getInstance() {
    if (uniqueInstance == null) {
        uniqueInstance = new Singleton();
    }
    return uniqueInstance;
}

// Wersja z wczesną inicjalizacją
private static Singleton uniqueInstance = new Singleton();
public static Singleton getInstance() {
    return uniqueInstance;
}

// Wersja z podwójnym sprawdzeniem blokady (double-checked locking)
private volatile static Singleton uniqueInstance;
public static Singleton getInstance() {
    if (uniqueInstance == null) {
        synchronized (Singleton.class) {
            if (uniqueInstance == null) {
                uniqueInstance = new Singleton();
            }
        }
    }
    return uniqueInstance;
}
```

## Podsumowanie
Wzorzec Singleton jest jednym z najprostszych, ale bardzo przydatnych wzorców projektowych. Zapewnia, że klasa ma tylko jedną instancję, co jest kluczowe w przypadkach, gdzie wielokrotne instancje mogłyby powodować problemy lub niespójności. Singleton osiąga ten cel poprzez ukrycie standardowego konstruktora i wymuszenie używania metody statycznej do uzyskania dostępu do jedynej instancji klasy. 