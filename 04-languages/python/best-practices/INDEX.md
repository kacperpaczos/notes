# best-practices

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


Ten katalog jest częścią sekcji python.

## Zawartość katalogu

### Wzorce Projektowe w Python

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
        return json.dumps(data)
```

#### Guard Clause Pattern
- **Definicja**: Wczesne zwracanie w przypadku błędnych warunków
- **Zaleta**: Eliminacja zagnieżdżonych warunków, czytelniejszy kod
- **Przykład**:
```python
# Bez Guard Clause - zagnieżdżone warunki
def process_data(data):
    if data is not None:
        if isinstance(data, list):
            if len(data) > 0:
                # przetwarzanie
                pass
            else:
                return "Pusta lista"
        else:
            return "Nieprawidłowy typ"
    else:
        return "Brak danych"

# Z Guard Clause - wczesne zwracanie
def process_data(data):
    if data is None:
        return "Brak danych"
    if not isinstance(data, list):
        return "Nieprawidłowy typ"
    if len(data) == 0:
        return "Pusta lista"
    # przetwarzanie
    pass
```

