# Wzorce Projektowe - Przegląd

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


## Definicja Wzorców Projektowych
- Wzorce projektowe to sprawdzone, praktyczne rozwiązania dla powtarzających się problemów projektowych w inżynierii oprogramowania.

## Rola Obiektów
- Obiekty są instancjami klas, które przechowują dane i metody.
- Umożliwiają **encapsulację**, **reużywalność** i definiują **interakcje** między obiektami.

## Korzyści z Używania Wzorców Projektowych
- Umożliwiają programistom korzystanie z wcześniej opracowanych rozwiązań, co przyspiesza proces tworzenia oprogramowania.
- Pomagają w tworzeniu wspólnego słownictwa, co ułatwia komunikację między programistami.
- Zmniejszają ryzyko błędów projektowych, co prowadzi do lepszej jakości kodu.

## Przykłady Wzorców Projektowych

### 1. Singleton
- Zapewnia, że klasa ma tylko jedną instancję i dostarcza globalny punkt dostępu do tej instancji.

### 2. Fabryka (Factory Pattern)
- Umożliwia tworzenie obiektów bez konieczności określania konkretnej klasy obiektu, co zwiększa elastyczność.

### 3. Wzorzec Obserwatora (Observer Pattern)
- Umożliwia obiektom (obserwatorom) subskrybowanie i otrzymywanie powiadomień o zmianach w innym obiekcie (subjekcie).

## Zastosowanie Wzorców Projektowych
- Wzorce projektowe są bardziej koncepcyjne niż konkretne fragmenty kodu, co pozwala na elastyczność i ponowne wykorzystanie w projektowaniu oprogramowania.
- Umożliwiają programistom unikanie typowych błędów projektowych.

## Podsumowanie
- Wzorce projektowe są kluczowymi technikami w inżynierii oprogramowania, które pomagają w tworzeniu elastycznych, łatwych do zarządzania i skalowalnych aplikacji.

## Fiszki - Kluczowe Pojęcia

### Podstawowe Koncepcje
1. **Wzorce projektowe**
   - Sprawdzone rozwiązania dla typowych problemów w projektowaniu oprogramowania.

2. **Gang of Four**
   - Autorzy książki "Design Patterns: Elements of Reusable Object-Oriented Software".

3. **Katalog wzorców**
   - Zbiór wzorców projektowych zorganizowanych według ich celów.

4. **Język wzorców**
   - Zbiór wzorców związanych z określoną przestrzenią problemową.

### Kategorie Wzorców
5. **Wzorce kreacyjne**
   - Wzorce dotyczące tworzenia obiektów.

6. **Wzorce strukturalne**
   - Wzorce opisujące relacje między obiektami.

7. **Wzorce behawioralne**
   - Wzorce dotyczące interakcji i współpracy obiektów.

### Koncepcje Programowania Obiektowego
8. **Klasy**
   - Szablony do tworzenia obiektów w programowaniu obiektowym.

9. **Prototypy**
   - Obiekty, które mogą być klonowane i rozszerzane w JavaScript.

10. **UML (Unified Modeling Language)**
    - Język do modelowania systemów obiektowych.

11. **Dezorganizacja**
    - Zasady projektowe, takie jak zasada otwarte-zamknięte czy zasada inwersji zależności.

### Relacje Między Obiektami
12. **Agregacja**
    - Relacja, w której jeden obiekt zawiera inne obiekty, które mogą istnieć niezależnie.

13. **Kompozycja**
    - Silniejsza forma agregacji, w której obiekty są ściśle powiązane i nie mogą istnieć niezależnie.

14. **Dziedziczenie**
    - Mechanizm, w którym jedna klasa dziedziczy właściwości i metody innej klasy.

15. **Interfejs**
    - Zbiór metod, które klasa musi zaimplementować, ale nie zawiera ich definicji.

16. **Asocjacja**
    - Ogólna relacja między obiektami, która może być jedno- lub dwukierunkowa.

## Wzorce Projektowe Specyficzne dla Języków Programowania

### Python

#### Strategy Pattern
- **Definicja**: Hermetyzacja algorytmów w osobnych klasach z wspólnym interfejsem
- **Zastosowanie**: Gdy masz wiele algorytmów rozwiązujących ten sam problem
- **Przykład**:
```python
from abc import ABC, abstractmethod

class PaymentStrategy(ABC):
    @abstractmethod
    def pay(self, amount: float) -> None: ...

class CreditCardPayment(PaymentStrategy):
    def pay(self, amount: float) -> None:
        print(f"Płacę {amount} kartą kredytową")

class PayPalPayment(PaymentStrategy):
    def pay(self, amount: float) -> None:
        print(f"Płacę {amount} przez PayPal")

class PaymentProcessor:
    def __init__(self, strategy: PaymentStrategy):
        self.strategy = strategy

    def process_payment(self, amount: float):
        self.strategy.pay(amount)
```

#### Template Method Pattern
- **Definicja**: Szkielet algorytmu w klasie bazowej z możliwością podmiany kroków
- **Zastosowanie**: Procesy z niezmiennymi i zmiennymi krokami
- **Przykład**:
```python
from abc import ABC, abstractmethod

class DataProcessor(ABC):
    def process(self, data):
        self.validate(data)
        cleaned_data = self.clean(data)
        result = self.transform(cleaned_data)
        self.save(result)

    @abstractmethod
    def validate(self, data): ...

    @abstractmethod
    def clean(self, data): ...

    @abstractmethod
    def transform(self, data): ...

    def save(self, data):  # krok domyślny
        print(f"Zapisywanie: {data}")

class JSONProcessor(DataProcessor):
    def validate(self, data):
        if not isinstance(data, dict):
            raise ValueError("Nieprawidłowe dane JSON")

    def clean(self, data):
        return {k: v for k, v in data.items() if v is not None}

    def transform(self, data):
        import json
        return json.dumps(data)
```

#### Guard Clause Pattern
- **Definicja**: Wczesne zwracanie w przypadku błędnych warunków
- **Zaleta**: Eliminacja zagnieżdżonych warunków, czytelniejszy kod

### Java

#### Builder Pattern
- **Definicja**: Budowanie złożonych obiektów krok po kroku
- **Zastosowanie**: Gdy konstruktor ma zbyt wiele parametrów opcjonalnych
- **Przykład**:
```java
public class Computer {
    private String cpu;
    private String ram;
    private String storage;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;

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
    .build();
```

#### Factory Method Pattern
- **Definicja**: Tworzenie obiektów bez specyfikowania konkretnych klas
- **Zastosowanie**: Gdy podklasy decydują o typie tworzonego obiektu

### C++

#### RAII Pattern
- **Definicja**: Zasoby zarządzane przez czas życia obiektów
- **Zastosowanie**: Automatyczne zwalnianie zasobów
- **Przykład**:
```cpp
class MutexLock {
    std::mutex& mutex;

public:
    explicit MutexLock(std::mutex& m) : mutex(m) {
        mutex.lock();
    }

    ~MutexLock() {
        mutex.unlock();
    }
};

std::mutex globalMutex;
void threadSafeFunction() {
    MutexLock lock(globalMutex);  // automatyczne lock/unlock
    // kod chroniony
}
```

#### Namespace Pattern
- **Definicja**: Grupowanie funkcjonalności w przestrzenie nazw
- **Zastosowanie**: Unikanie konfliktów nazw, organizacja kodu

#### Iterator Pattern
- **Definicja**: Dostęp do elementów kolekcji bez eksponowania struktury
- **Zastosowanie**: Jednolity interfejs dla różnych typów kolekcji 