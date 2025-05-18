# Różnica między `extends` a `implements` w Javie

## Kluczowe różnice

| Cecha | `extends` | `implements` |
|-------|-----------|--------------|
| Zastosowanie | Używane do dziedziczenia klas | Używane do implementacji interfejsów |
| Dziedziczenie | Dziedziczy zarówno metody, jak i pola klasy bazowej | Dziedziczy tylko deklaracje metod (kontrakty) |
| Liczba elementów | Klasa może rozszerzać tylko jedną klasę (dziedziczenie pojedyncze) | Klasa może implementować wiele interfejsów |
| Inicjalizacja | Wymaga zainicjowania klasy bazowej | Nie wymaga inicjalizacji (interfejsy nie mają stanu) |

## Przykład użycia `extends`

```java
// Klasa bazowa
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " je.");
    }
    
    public void sleep() {
        System.out.println(name + " śpi.");
    }
}

// Klasa pochodna rozszerzająca klasę bazową
public class Dog extends Animal {
    public Dog(String name) {
        super(name); // Wywołanie konstruktora klasy bazowej
    }
    
    public void bark() {
        System.out.println(name + " szczeka.");
    }
}
```

W tym przykładzie:
- `Dog` dziedziczy pola i metody z klasy `Animal`
- `Dog` musi wywołać konstruktor klasy bazowej używając `super()`
- `Dog` może dodawać własne metody i pola

## Przykład użycia `implements`

```java
// Interfejs
public interface Swimmable {
    void swim(); // Metoda bez implementacji (abstrakcyjna)
    
    // Java 8+ może zawierać metody domyślne
    default void float() {
        System.out.println("Unoszenie się na wodzie");
    }
}

// Klasa implementująca interfejs
public class Fish implements Swimmable {
    private String name;
    
    public Fish(String name) {
        this.name = name;
    }
    
    @Override
    public void swim() {
        System.out.println(name + " pływa.");
    }
}
```

W tym przykładzie:
- `Fish` musi zaimplementować wszystkie abstrakcyjne metody z interfejsu `Swimmable`
- Interfejs nie ma stanu (pól z danymi), więc nie wywołujemy żadnego konstruktora
- Klasa może implementować wiele interfejsów poprzez odddzielenie ich przecinkami

## Łączenie `extends` i `implements`

```java
public class Dolphin extends Mammal implements Swimmable, Playful {
    // Rozszerza jedną klasę i implementuje dwa interfejsy
    
    public Dolphin(String name) {
        super(name);
    }
    
    @Override
    public void swim() {
        System.out.println(name + " pływa szybko i zgrabnie.");
    }
    
    @Override
    public void play() {
        System.out.println(name + " bawi się piłką.");
    }
}
```

## Podsumowanie

- `extends` używamy, gdy chcemy dziedziczyć funkcjonalność z klasy bazowej
- `implements` używamy, gdy chcemy zadeklarować, że klasa spełnia kontrakt interfejsu
- W Javie klasa może dziedziczyć tylko po jednej klasie, ale może implementować wiele interfejsów
- Interfejsy pozwalają na osiągnięcie polimorfizmu bez ścisłej hierarchii dziedziczenia 