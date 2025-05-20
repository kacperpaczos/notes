# Wzorzec Mediator

## Cel
Definiuje obiekt, który enkapsuluje sposób, w jaki zbiór obiektów wchodzi w interakcje. Mediator promuje luźne powiązanie, utrzymując obiekty od bezpośredniego odwoływania się do siebie, i pozwala na niezależną zmianę ich interakcji.

## Problem
Masz system, w którym:
- Obiekty komunikują się ze sobą bezpośrednio
- Powstaje sieć zależności między obiektami
- Trudno jest zmienić zachowanie systemu
- Trudno jest dodać nowe obiekty

## Rozwiązanie
1. Zdefiniuj interfejs Mediator
2. Zaimplementuj konkretnego mediatora
3. Przenieś logikę komunikacji do mediatora
4. Obiekty komunikują się tylko przez mediatora

## Struktura
```
Mediator
    |
    |-- notify()
    |
ConcreteMediator
    |
    |-- notify()
    |
Colleague
    |
    |-- setMediator()
    |
ConcreteColleague
    |
    |-- notify()
```

## Przykład użycia
- Systemy czatów
- Systemy rezerwacji
- Systemy kontroli lotów
- Systemy zarządzania dokumentami
- Systemy workflow

## Zalety
- Zmniejsza powiązania między obiektami
- Centralizuje logikę komunikacji
- Ułatwia dodawanie nowych obiektów
- Ułatwia testowanie
- Ułatwia utrzymanie kodu

## Wady
- Może prowadzić do złożoności mediatora
- Może być wąskim gardłem wydajności
- Trudny do debugowania
- Może być nadmiarowy dla prostych przypadków

## Przykład implementacji
```java
public interface Mediator {
    void notify(Colleague sender, String event);
}

public abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}

public class ConcreteMediator implements Mediator {
    private List<Colleague> colleagues = new ArrayList<>();

    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }

    @Override
    public void notify(Colleague sender, String event) {
        for (Colleague colleague : colleagues) {
            if (colleague != sender) {
                // Przekaż informację do innych obiektów
            }
        }
    }
}
```

## Powiązane wzorce
- Observer Pattern - może być używany do implementacji powiadomień
- Facade Pattern - podobna struktura, ale inny cel
- Command Pattern - może być używany do implementacji operacji
