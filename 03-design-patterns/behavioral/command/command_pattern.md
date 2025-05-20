# Wzorzec Command

## Cel
Enkapsuluje żądanie jako obiekt, umożliwiając parametryzację klientów z różnymi żądaniami, kolejkowanie żądań, logowanie żądań oraz obsługę operacji cofania.

## Problem
Masz system, w którym chcesz:
- Parametryzować obiekty operacjami
- Kolejkować operacje
- Wspierać operacje cofania
- Logować operacje
- Wspierać transakcje

## Rozwiązanie
1. Zdefiniuj interfejs Command z metodą execute()
2. Zaimplementuj konkretne komendy
3. Stwórz klasę Invoker, która będzie wykonywać komendy
4. Stwórz klasę Receiver, która będzie wykonywać faktyczne operacje

## Struktura
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

## Przykład użycia
- Systemy menu
- Kolejkowanie zadań
- Operacje cofania
- Logowanie operacji
- Transakcje
- Makra

## Zalety
- Enkapsuluje żądanie jako obiekt
- Umożliwia parametryzację klientów
- Wspiera operacje cofania
- Umożliwia kolejkowanie żądań
- Ułatwia logowanie operacji

## Wady
- Zwiększa liczbę klas
- Może prowadzić do złożoności
- Trudny do debugowania
- Może być nadmiarowy dla prostych przypadków

## Przykład implementacji
```java
public interface Command {
    void execute();
    void undo();
}

public class ConcreteCommand implements Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.action();
    }

    @Override
    public void undo() {
        receiver.undoAction();
    }
}

public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
```

## Powiązane wzorce
- Memento Pattern - może być używany do implementacji operacji cofania
- Composite Pattern - może być używany do implementacji makr
- Chain of Responsibility - podobna struktura, ale inny cel
