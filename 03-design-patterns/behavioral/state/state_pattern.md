# Wzorzec State

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


## Cel
Pozwala obiektowi zmieniać swoje zachowanie gdy zmienia się jego stan wewnętrzny. Wygląda to tak, jakby obiekt zmieniał swoją klasę.

## Problem
Masz obiekt, którego zachowanie zależy od jego stanu, a zmiany stanu są reprezentowane przez zmiany wartości zmiennych. W rezultacie masz wiele warunków (if-else lub switch) w metodach obiektu, które sprawdzają stan i wykonują odpowiednie działania.

## Rozwiązanie
1. Zdefiniuj interfejs dla stanów
2. Zaimplementuj konkretne stany jako klasy
3. Przenieś logikę zależną od stanu do odpowiednich klas stanów
4. Deleguj operacje do aktualnego stanu

## Struktura
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

## Przykład użycia
- Automaty stanów
- Systemy workflow
- Gry komputerowe
- Systemy zarządzania dokumentami
- Systemy płatności

## Zalety
- Enkapsuluje zachowanie specyficzne dla stanu
- Ułatwia dodawanie nowych stanów
- Eliminuje warunki if-else
- Zwiększa czytelność kodu
- Ułatwia testowanie

## Wady
- Może prowadzić do dużej liczby klas
- Zwiększa złożoność systemu
- Może być nadmiarowy dla prostych przypadków
- Trudny do debugowania

## Przykład implementacji
```java
public interface State {
    void handle(Context context);
}

public class Context {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handle(this);
    }
}

public class ConcreteState implements State {
    @Override
    public void handle(Context context) {
        // Implementacja zachowania dla konkretnego stanu
    }
}
```

## Powiązane wzorce
- Strategy Pattern - podobna struktura, ale inny cel
- Singleton Pattern - często używany do implementacji stanów
- Flyweight Pattern - może być używany do współdzielenia stanów
