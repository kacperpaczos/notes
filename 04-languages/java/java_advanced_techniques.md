# Zaawansowane Techniki Programowania w Java

## Techniki Implementacyjne

### Utility Class Pattern
- **Definicja**: Klasa zawierająca tylko metody statyczne
- **Konstruktor**: Prywatny, aby zapobiec instancjonowaniu
- **Przykład**:
```java
public final class StringUtils {
    private StringUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
```

### BufferedReader i Stream API
- **BufferedReader**: Buforowana lektura dla wydajności
- **Stream API**: Programowanie funkcyjne na kolekcjach
- **Przykład**:
```java
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    List<String> lines = reader.lines()
        .filter(line -> !line.trim().isEmpty())
        .map(String::toUpperCase)
        .collect(Collectors.toList());
}
```

### Try-with-resources
- **Definicja**: Automatyczne zarządzanie zasobami
- **Wymagania**: Zasób musi implementować AutoCloseable
- **Przykład**:
```java
try (FileInputStream fis = new FileInputStream("file.txt");
     BufferedInputStream bis = new BufferedInputStream(fis)) {
    // praca z zasobami
} // automatyczne zamknięcie
```

### Method References
- **Składnia**: `Object::method` lub `Class::staticMethod`
- **Zastosowanie**: Zwięzła forma lambd
- **Przykład**:
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Lambda
names.forEach(name -> System.out.println(name));

// Method reference
names.forEach(System.out::println);
```

### Collectors
- **Definicja**: Klasa narzędziowa dla operacji terminalnych na strumieniach
- **Popularne**: `toList()`, `toSet()`, `toMap()`, `joining()`, `groupingBy()`
- **Przykład**:
```java
Map<String, List<Person>> byCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity));
```

### Javadoc
- **Standard**: Dokumentacja w formacie HTML
- **Tagi**: `@param`, `@return`, `@throws`, `@author`, `@version`
- **Generowanie**: `javadoc` command lub IDE integration

## Koncepcje Projektowe

### Immutability
- **Definicja**: Obiekty, których stanu nie można zmienić po utworzeniu
- **Korzyści**: Bezpieczeństwo wątkowe, caching, łatwiejsze testowanie
- **Przykład**:
```java
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Tylko gettery, brak setterów
    public String getName() { return name; }
    public int getAge() { return age; }
}
```

### Encapsulation
- **Definicja**: Ukrywanie implementacji przed użytkownikiem klasy
- **Poziomy**: Public, protected, package-private, private
- **Przykład**:
```java
public class BankAccount {
    private double balance;  // prywatne pole

    public void deposit(double amount) {  // publiczny setter
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {  // publiczny getter
        return balance;
    }
}
```

### Functional Programming
- **Cechy**: Funkcje pierwszej klasy, niemutowalność, funkcje wyższego rzędu
- **Stream API**: Główny przedstawiciel w Javie
- **Przykład**:
```java
List<String> result = numbers.stream()
    .filter(n -> n % 2 == 0)           // funkcja wyższego rzędu
    .map(n -> n * n)                    // transformacja
    .collect(Collectors.toList());      // kolektor
```

### Lazy Evaluation
- **Definicja**: Obliczanie wartości tylko gdy jest potrzebne
- **Implementacja**: Strumienie są leniwe aż do operacji terminalnej
- **Przykład**:
```java
Stream<Integer> stream = numbers.stream()
    .filter(n -> n > 100)     // nie wykonuje się jeszcze
    .map(n -> n * 2);         // nie wykonuje się jeszcze

List<Integer> result = stream.collect(Collectors.toList()); // wykonanie
```

### RAII-like (Resource Acquisition Is Initialization)
- **Adaptacja**: Try-with-resources jako forma RAII w Javie
- **Porównanie**: W C++ RAII używa konstruktorów/destruktorów
- **Przykład**:
```java
// RAII-like w Java
public class ResourceManager implements AutoCloseable {
    private Connection connection;

    public ResourceManager(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public void doWork() {
        // praca z zasobem
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

// Użycie
try (ResourceManager rm = new ResourceManager("jdbc:sqlite:test.db")) {
    rm.doWork();
} // automatyczne wywołanie close()
```

## Wzorce Projektowe w Java

### Builder Pattern
- **Definicja**: Budowanie złożonych obiektów krok po kroku
- **Zastosowanie**: Gdy konstruktor ma zbyt wiele parametrów opcjonalnych
- **Przykład**:
```java
public class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String graphics;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphics = builder.graphics;
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String graphics;

        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

// Użycie
Computer gamingPC = new Computer.Builder()
    .cpu("Intel i7")
    .ram("32GB")
    .storage("1TB SSD")
    .graphics("RTX 3080")
    .build();
```

### Factory Method Pattern
- **Definicja**: Tworzenie obiektów bez specyfikowania konkretnych klas
- **Zastosowanie**: Gdy podklasy decydują o typie tworzonego obiektu
- **Przykład**:
```java
public abstract class Logistics {
    public void planDelivery() {
        Transport transport = createTransport();
        transport.deliver();
    }

    protected abstract Transport createTransport();
}

public class RoadLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Truck();
    }
}

public class SeaLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Ship();
    }
}
```

## Optymalizacje Wydajnościowe w Java

### BufferedReader dla I/O
- **Zasada**: Buforowanie zmniejsza liczbę operacji systemowych
- **Przykład**:
```java
// Wolne - bez buforowania
FileReader fr = new FileReader("large-file.txt");
int ch;
while ((ch = fr.read()) != -1) {
    // przetwarzanie
}

// Szybkie - z buforowaniem
BufferedReader br = new BufferedReader(new FileReader("large-file.txt"));
String line;
while ((line = br.readLine()) != null) {
    // przetwarzanie
}
```

### StringBuilder dla konkatenacji
- **Zasada**: String jest niemutowalny, użyj StringBuilder dla modyfikacji
- **Przykład**:
```java
// Wolne - tworzenie nowych obiektów
String result = "";
for (String item : items) {
    result += item;  // O(n²)
}

// Szybkie - modyfikowalny buffer
StringBuilder sb = new StringBuilder();
for (String item : items) {
    sb.append(item);  // O(n)
}
String result = sb.toString();
```

### Stream Pipeline Optimization
- **Zasada**: Unikaj niepotrzebnych operacji, używaj parallel gdy odpowiednie
- **Przykład**:
```java
// Nieoptymalne - wielokrotne przejścia
List<String> result = data.stream()
    .filter(s -> s.length() > 3)
    .collect(Collectors.toList());

result.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);

// Optymalne - jeden pipeline
data.stream()
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase)
    .forEach(System.out::println);
```
