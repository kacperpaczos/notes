# Wzorzec Chain of Responsibility

## Cel
Unika ścisłego powiązania nadawcy żądania z jego odbiorcą, dając szansę obsłużenia żądania wielu obiektom. Łańcuch obiektów odbiorczych jest tworzony przez przekazywanie żądania od jednego obiektu do następnego w łańcuchu.

## Problem
Masz system, w którym żądania mogą być obsługiwane przez różne obiekty, ale nie wiesz z góry, który obiekt powinien obsłużyć konkretne żądanie. Chcesz, aby obiekty same decydowały, czy mogą obsłużyć żądanie, czy powinny przekazać je dalej.

## Rozwiązanie
1. Zdefiniuj interfejs dla obsługi żądań
2. Zaimplementuj łańcuch obiektów obsługujących
3. Każdy obiekt w łańcuchu decyduje, czy obsłużyć żądanie, czy przekazać je dalej

## Struktura
```
Handler
    |
    |-- handleRequest()
    |-- setNext()
    |
ConcreteHandler
    |
    |-- handleRequest()
```

## Przykład użycia
- Systemy logowania
- Obsługa wyjątków
- Filtry HTTP
- Systemy autoryzacji
- Pipeline przetwarzania danych

## Zalety
- Zmniejsza sprzężenie między nadawcą a odbiorcą
- Zwiększa elastyczność w przypisywaniu odpowiedzialności
- Pozwala na dynamiczną zmianę łańcucha
- Ułatwia dodawanie nowych obsługiwaczy

## Wady
- Nie ma gwarancji, że żądanie zostanie obsłużone
- Może prowadzić do nieoczekiwanego zachowania
- Trudny do debugowania
- Może wpływać na wydajność

## Przykład implementacji
```java
public abstract class Handler {
    protected Handler nextHandler;

    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    public abstract void handleRequest(Request request);
}

public class ConcreteHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        if (canHandle(request)) {
            // Obsługa żądania
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    private boolean canHandle(Request request) {
        // Logika decydująca czy handler może obsłużyć żądanie
        return false;
    }
}
```

## Powiązane wzorce
- Composite Pattern - często używany do implementacji łańcucha
- Command Pattern - żądania mogą być reprezentowane jako obiekty Command
- Decorator Pattern - podobna struktura, ale inny cel 