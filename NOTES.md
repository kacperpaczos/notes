# 📝 NOTES.md - Kompendium Zaawansowanych Technik Programowania

Ten dokument zawiera skondensowaną wiedzę na temat zaawansowanych technik programowania w trzech językach: Python, Java i C++. Obejmuje wzorce projektowe, optymalizacje wydajności, techniki testowania oraz kluczowe koncepcje algorytmiczne.

## Spis Treści

1. [Python Implementation](#python-implementation)
2. [Java Implementation](#java-implementation)
3. [C++ Implementation](#cpp-implementation)
4. [Optymalizacje Wydajnościowe](#optymalizacje-wydajnościowe)
5. [Zakresy Testów](#zakresy-testów-38-total)
6. [Wzorce Projektowe](#wzorce-projektowe)
7. [Koncepcje Algorytmiczne](#koncepcje-algorytmiczne)
8. [Kluczowe Słowa](#kluczowe-słowa-do-twoich-notatek)

---

## Python Implementation

### Techniki Implementacyjne

#### **Modular Design**
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

#### **Type Hints**
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

#### **List Comprehension**
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

#### **Funkcje reversed() i map()**
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

#### **Exception Hierarchy**
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

#### **Exit Codes**
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

#### **Docstrings**
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

#### **SRP (Single Responsibility Principle)**
- **Definicja**: Każda klasa powinna mieć tylko jeden powód do zmiany
- **Zastosowanie**: Separacja logiki biznesowej od infrastruktury

#### **Separation of Concerns**
- **Definicja**: Podział aplikacji na warstwy o różnych odpowiedzialnościach
- **Warstwy**: Prezentacja, Logika biznesowa, Dostęp do danych

#### **Defensive Programming**
- **Definicja**: Programowanie z założeniem, że wszystko może się zepsuć
- **Techniki**: Walidacja danych, obsługa wyjątków, sanity checks

#### **DRY (Don't Repeat Yourself)**
- **Definicja**: Eliminacja duplikacji kodu poprzez abstrakcję
- **Techniki**: Funkcje, klasy, konfiguracja

#### **KISS (Keep It Simple, Stupid)**
- **Definicja**: Prostota jest kluczem do niezawodności
- **Zasada**: Wybieraj najprostsze rozwiązanie spełniające wymagania

---

## Java Implementation

### Techniki Implementacyjne

#### **Utility Class Pattern**
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

#### **BufferedReader i Stream API**
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

#### **Try-with-resources**
- **Definicja**: Automatyczne zarządzanie zasobami
- **Wymagania**: Zasób musi implementować AutoCloseable
- **Przykład**:
```java
try (FileInputStream fis = new FileInputStream("file.txt");
     BufferedInputStream bis = new BufferedInputStream(fis)) {
    // praca z zasobami
} // automatyczne zamknięcie
```

#### **Method References**
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

#### **Collectors**
- **Definicja**: Klasa narzędziowa dla operacji terminalnych na strumieniach
- **Popularne**: `toList()`, `toSet()`, `toMap()`, `joining()`, `groupingBy()`
- **Przykład**:
```java
Map<String, List<Person>> byCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity));
```

#### **Javadoc**
- **Standard**: Dokumentacja w formacie HTML
- **Tagi**: `@param`, `@return`, `@throws`, `@author`, `@version`
- **Generowanie**: `javadoc` command lub IDE integration

### Koncepcje Projektowe

#### **Immutability**
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
}
```

#### **Encapsulation**
- **Definicja**: Ukrywanie implementacji przed użytkownikiem klasy
- **Poziomy**: Public, protected, package-private, private

#### **Functional Programming**
- **Cechy**: Funkcje pierwszej klasy, niemutowalność, funkcje wyższego rzędu
- **Stream API**: Główny przedstawiciel w Javie

#### **Lazy Evaluation**
- **Definicja**: Obliczanie wartości tylko gdy jest potrzebne
- **Implementacja**: Strumienie są leniwe aż do operacji terminalnej

#### **RAII-like (Resource Acquisition Is Initialization)**
- **Adaptacja**: Try-with-resources jako forma RAII w Javie
- **Porównanie**: W C++ RAII używa konstruktorów/destruktorów

---

## C++ Implementation

### Techniki Implementacyjne

#### **Anonymous Namespace**
- **Definicja**: Namespace bez nazwy dla ukrywania implementacji
- **Zastosowanie**: Funkcje pomocnicze widoczne tylko w bieżącym pliku
- **Przykład**:
```cpp
// plik.cpp
namespace {
    void helperFunction() { /* implementacja */ }
}

void publicFunction() {
    helperFunction(); // dostęp tylko w tym pliku
}
```

#### **Fast I/O**
- **Optymalizacje**: `ios::sync_with_stdio(false)`, `cin.tie(NULL)`
- **Korzyści**: Przyspieszenie I/O nawet 10x
- **Przykład**:
```cpp
#include <iostream>

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(NULL);

    int n;
    std::cin >> n;
    // szybkie I/O
}
```

#### **Reserve() i Reverse Iterators**
- **reserve()**: Rezerwacja pamięci dla wektorów
- **Reverse iterators**: Iteracja wsteczna
- **Przykład**:
```cpp
std::vector<int> vec;
vec.reserve(1000); // uniknięcie realokacji

// Iteracja wsteczna
for (auto it = vec.rbegin(); it != vec.rend(); ++it) {
    std::cout << *it << ' ';
}
```

#### **RAII (Resource Acquisition Is Initialization)**
- **Definicja**: Zarządzanie zasobami poprzez czas życia obiektów
- **Zasada**: Zasób tworzony w konstruktorze, zwalniany w destruktorze
- **Przykład**:
```cpp
class FileHandler {
private:
    std::ifstream file;

public:
    FileHandler(const std::string& filename)
        : file(filename) {}

    ~FileHandler() {
        if (file.is_open()) {
            file.close();
        }
    }
};
```

#### **Const Correctness**
- **Definicja**: Określanie, które operacje nie modyfikują obiektu
- **Słowa kluczowe**: `const` dla metod i parametrów
- **Przykład**:
```cpp
class MyClass {
public:
    int getValue() const { return value; } // nie modyfikuje obiektu
    void setValue(int v) { value = v; }    // modyfikuje obiekt
private:
    int value;
};
```

#### **Doxygen**
- **Standard**: Dokumentacja podobna do Javadoc
- **Komentarze**: `/** opis */`, `@param`, `@return`
- **Generowanie**: Narzędzie doxygen

### Koncepcje Projektowe

#### **Zero-overhead Abstraction**
- **Definicja**: Abstrakcje nie wprowadzają overhead'u w runtime
- **Przykład**: Inline functions, templates

#### **Exception Safety**
- **Poziomy**: No-throw, Strong, Basic guarantee
- **Techniki**: RAII, copy-and-swap idiom

#### **Memory Locality**
- **Definicja**: Umieszczanie powiązanych danych blisko siebie w pamięci
- **Korzyści**: Lepsze wykorzystanie cache

#### **Move Semantics**
- **Definicja**: Przenoszenie zasobów zamiast kopiowania
- **Słowa kluczowe**: `&&` (rvalue reference), `std::move()`
- **Przykład**:
```cpp
std::vector<int> createVector() {
    std::vector<int> temp = {1, 2, 3, 4, 5};
    return temp; // move semantics
}
```

---

## Optymalizacje Wydajnościowe

### Python
- **Lazy Evaluation**: Generatory, itertools
- **str.join()**: Efektywne łączenie stringów
- **Przykład**:
```python
# Zły przykład
result = ""
for item in items:
    result += str(item)  # O(n²) - tworzenie nowych stringów

# Dobry przykład
result = "".join(str(item) for item in items)  # O(n)
```

### Java
- **BufferedReader**: Buforowana lektura plików
- **StringBuilder**: Modyfikowalny string dla konkatenacji
- **Stream Pipeline**: Leniwe przetwarzanie
- **Przykład**:
```java
String result = list.stream()
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase)
    .collect(Collectors.joining(", "));
```

### C++
- **Fast I/O**: Wyłączenie synchronizacji z C
- **reserve()**: Pre-alokacja pamięci
- **Zero-copy Iterators**: Bezpośredni dostęp do danych
- **Przykład**:
```cpp
std::vector<int> vec;
vec.reserve(large_size); // uniknięcie realokacji

// Zero-copy
for (const auto& item : vec) { // referencja zamiast kopii
    process(item);
}
```

---

## Zakresy Testów (38 total)

### **1. Basic (5)** - Happy Path
1. **Standard Input**: Podstawowe przypadki testowe
2. **Single Value**: Pojedyncze wartości brzegowe
3. **Small Arrays**: Małe kolekcje danych
4. **Basic Operations**: Podstawowe operacje I/O
5. **Default Parameters**: Domyślne wartości parametrów

### **2. Edge Cases (8)** - Warunki Brzegowe
6. **Empty Input**: Puste dane wejściowe
7. **Whitespace Only**: Tylko spacje i tabulatory
8. **Boundary Values**: Wartości na granicach zakresów
9. **Special Characters**: Znaki specjalne i Unicode
10. **Null/None Values**: Wartości nullowe w różnych kontekstach
11. **Single Character**: Pojedyncze znaki
12. **Maximum Length**: Maksymalna długość danych
13. **Zero Values**: Wartości zerowe i minimalne

### **3. Performance (7)** - Skalowalność
14. **Large Datasets**: 10^5 - 10^6 elementów
15. **INT_MAX/MIN**: Wartości maksymalne/minimalne
16. **Time Complexity**: Testowanie O(n) zachowań
17. **Memory Usage**: Monitorowanie zużycia pamięci
18. **Concurrent Access**: Wielowątkowość
19. **I/O Bottlenecks**: Testowanie ograniczeń I/O
20. **Cache Effects**: Wpływ cache na wydajność

### **4. Error Handling (7)** - Walidacja
21. **Invalid Input Types**: Nieprawidłowe typy danych
22. **Malformed Data**: Niepoprawnie sformatowane dane
23. **File Permissions**: Brak uprawnień do plików
24. **Network Errors**: Błędy sieciowe
25. **Out of Memory**: Przekroczenie limitów pamięci
26. **Stack Overflow**: Rekurencja bezwarunkowa
27. **Exit Codes**: Sprawdzenie kodów zakończenia

### **5. Advanced (11)** - Zaawansowane przypadki
28. **50k Stress Test**: Testy z 50 tysiącami elementów
29. **Mathematical Sequences**: Ciągi matematyczne (Fibonacci, etc.)
30. **Different Separators**: Różne separatory danych
31. **Encoding Variations**: Różne kodowania znaków
32. **Endianness**: Różne porządki bajtów
33. **Floating Point Precision**: Precyzja liczb zmiennoprzecinkowych
34. **Race Conditions**: Warunki wyścigu w kodzie współbieżnym
35. **Memory Leaks**: Wycieki pamięci (głównie C++)
36. **Resource Exhaustion**: Wyczerpanie zasobów systemowych
37. **Integration Tests**: Testy integracyjne z innymi systemami
38. **Regression Tests**: Testy regresji po zmianach

---

## Wzorce Projektowe

### Python
- **Strategy**: Hermetyzacja algorytmów w osobnych klasach
- **Template Method**: Szkielet algorytmu z możliwością podmiany kroków
- **Guard Clause**: Wczesne zwracanie w przypadku błędnych warunków

### Java
- **Utility Class**: Klasy narzędziowe z metodami statycznymi
- **Builder**: Budowanie złożonych obiektów krok po kroku
- **Factory Method**: Tworzenie obiektów bez specyfikowania konkretnych klas

### C++
- **RAII**: Zarządzanie zasobami poprzez czas życia obiektów
- **Namespace**: Grupowanie funkcjonalności w przestrzenie nazw
- **Iterator**: Dostęp do elementów kolekcji bez eksponowania struktury

---

## Koncepcje Algorytmiczne

### **Złożoność Obliczeniowa**
- **O(n)**: Złożoność liniowa - proporcjonalna do rozmiaru danych
- **Space Complexity**: Zużycie pamięci w zależności od danych
- **Time Complexity**: Czas wykonania w zależności od danych

### **Optymalizacje**
- **Single Pass**: Jedno przejście przez dane
- **Lazy Evaluation**: Obliczanie tylko potrzebnych wartości
- **Memory Reservation**: Pre-alokacja pamięci dla znanych rozmiarów

---

## Kluczowe Słowa (do Twoich notatek)

### Python
`modular design`, `type hints`, `list comprehension`, `reversed()`, `map()`, `exception hierarchy`, `exit codes`, `docstrings`, `SRP`, `Separation of Concerns`, `defensive programming`, `DRY`, `KISS`, `lazy evaluation`, `str.join()`

### Java
`utility class pattern`, `BufferedReader`, `Stream API`, `try-with-resources`, `method references`, `Collectors`, `Javadoc`, `immutability`, `encapsulation`, `functional programming`, `lazy evaluation`, `RAII-like`, `StringBuilder`

### C++
`anonymous namespace`, `fast I/O`, `sync_with_stdio`, `cin.tie`, `reserve()`, `reverse iterators`, `RAII`, `const correctness`, `Doxygen`, `zero-overhead abstraction`, `exception safety`, `memory locality`, `move semantics`

### Ogólne
`design patterns`, `algorithmic complexity`, `performance optimization`, `test coverage`, `edge cases`, `defensive programming`, `SOLID principles`, `clean code`

---

*Ten dokument został stworzony jako kompendium wiedzy do szybkiego odniesienia podczas nauki i pracy z zaawansowanymi technikami programowania.*
