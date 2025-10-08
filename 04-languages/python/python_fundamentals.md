# Python Fundamentals

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


## Overview

## Basic Syntax

## Data Types

## Control Structures

## Functions

## Error Handling

## Standard Library

## Package Management

## Development Environment Setup

## Best Practices

## Resources

## Zaawansowane Techniki Programowania

### Modular Design
- **Definicja**: Projektowanie kodu w sposób modularny, dzieląc funkcjonalności na niezależne, wymienne moduły
- **Zastosowanie**: Każda klasa/funkcja powinna mieć pojedynczą odpowiedzialność
- **Przykład**:
```python
# Zły przykład - jedna klasa robi wszystko
class BadProcessor:
    def __init__(self, data):
        self.data = data
        self.process_data()
        self.save_to_file()
        self.send_notification()

# Dobry przykład - separacja odpowiedzialności
class DataProcessor:
    def process(self, data): ...

class FileSaver:
    def save(self, data, filename): ...

class NotificationService:
    def send(self, message): ...
```

### Type Hints
- **Definicja**: Adnotacje typów zwiększające czytelność i umożliwiające statyczną analizę
- **Składnia**: `def function(param: Type) -> ReturnType:`
- **Korzyści**: Lepsze IDE support, wykrywanie błędów w czasie programowania, dokumentacja
- **Przykład**:
```python
from typing import List, Optional, Dict, Union

def process_data(data: List[Dict[str, Union[int, str]]]) -> Optional[str]:
    if not data:
        return None
    # przetwarzanie...
    return result
```

### List Comprehension
- **Definicja**: Zwięzła składnia tworzenia list w jednej linii
- **Składnia**: `[expression for item in iterable if condition]`
- **Zalety**: Czytelność, wydajność (bez overhead'u funkcji map)
- **Przykład**:
```python
# Tradycyjne podejście
squares = []
for x in range(10):
    if x % 2 == 0:
        squares.append(x**2)

# List comprehension
squares = [x**2 for x in range(10) if x % 2 == 0]
```

### Funkcje reversed() i map()
- **reversed()**: Iterator wsteczny bez kopiowania kolekcji
- **map()**: Transformacja elementów bez tworzenia pośredniej listy (Python 3)
- **Przykład**:
```python
# reversed() - oszczędność pamięci
for item in reversed(large_list):  # O(1) pamięci
    process(item)

# map() - leniwa ewaluacja
result = map(lambda x: x*2, large_dataset)  # Nie tworzy listy w pamięci
```

### Exception Hierarchy
- **Definicja**: Hierarchia wyjątków od najbardziej specyficznych do ogólnych
- **Zasada**: Złap specyficzne wyjątki przed ogólniejszymi
- **Przykład**:
```python
try:
    # operacje plikowe
    pass
except FileNotFoundError:  # Specyficzny
    # obsługa braku pliku
    pass
except IOError:  # Ogólniejszy
    # obsługa innych błędów I/O
    pass
except Exception:  # Najogólniejszy
    # obsługa wszystkich innych wyjątków
    pass
```

### Exit Codes
- **Standardowe kody**: 0 (sukces), 1 (błąd ogólny), 2 (błąd składni)
- **sys.exit(code)**: Zamyka program z podanym kodem
- **Przykład**:
```python
import sys

def main():
    if not validate_input():
        print("Błąd walidacji", file=sys.stderr)
        sys.exit(1)  # Kod błędu
    # przetwarzanie...
    sys.exit(0)  # Sukces
```

### Docstrings
- **Standardy**: Google, NumPy, Sphinx
- **Zawartość**: Opis, parametry (Args), zwracana wartość (Returns), wyjątki (Raises)
- **Przykład**:
```python
def calculate_fibonacci(n: int) -> List[int]:
    """
    Oblicza ciąg Fibonacciego do n-tego elementu.

    Args:
        n: Liczba naturalna określająca długość ciągu

    Returns:
        Lista zawierająca ciąg Fibonacciego

    Raises:
        ValueError: Gdy n jest mniejsze od 0
    """
```

### Koncepcje Projektowe

#### SRP (Single Responsibility Principle)
- **Definicja**: Każda klasa powinna mieć tylko jeden powód do zmiany
- **Zastosowanie**: Separacja logiki biznesowej od infrastruktury

#### Separation of Concerns
- **Definicja**: Podział aplikacji na warstwy o różnych odpowiedzialnościach
- **Warstwy**: Prezentacja, Logika biznesowa, Dostęp do danych

#### Defensive Programming
- **Definicja**: Programowanie z założeniem, że wszystko może się zepsuć
- **Techniki**: Walidacja danych, obsługa wyjątków, sanity checks

#### DRY (Don't Repeat Yourself)
- **Definicja**: Eliminacja duplikacji kodu poprzez abstrakcję
- **Techniki**: Funkcje, klasy, konfiguracja

#### KISS (Keep It Simple, Stupid)
- **Definicja**: Prostota jest kluczem do niezawodności
- **Zasada**: Wybieraj najprostsze rozwiązanie spełniające wymagania

### Optymalizacje Wydajnościowe w Python

#### Lazy Evaluation
- **Definicja**: Obliczanie wartości tylko gdy jest potrzebne
- **Implementacja**: Generatory, itertools
- **Przykład**:
```python
# Generator - leniwa ewaluacja
def fibonacci_generator():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a + b

# Użycie
fib_gen = fibonacci_generator()
for i, num in enumerate(fib_gen):
    if i >= 10:
        break
    print(num)
```

#### str.join() dla łączenia stringów
- **Zasada**: Użyj `str.join()` zamiast konkatenacji `+`
- **Przykład**:
```python
# Zły przykład - O(n²)
result = ""
for item in items:
    result += str(item)

# Dobry przykład - O(n)
result = "".join(str(item) for item in items)
```

#### List vs Generator Expressions
- **Generatory**: Oszczędność pamięci dla dużych kolekcji
- **Listy**: Gdy potrzebujesz wielokrotnego dostępu
- **Przykład**:
```python
# Generator expression - oszczędność pamięci
large_gen = (x**2 for x in range(1000000))

# List comprehension - pełna alokacja pamięci
large_list = [x**2 for x in range(1000000)]
```
