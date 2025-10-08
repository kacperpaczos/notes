# Zaawansowane Techniki Programowania w C++

## Techniki Implementacyjne

### Anonymous Namespace
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

### Fast I/O
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

### Reserve() i Reverse Iterators
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

### RAII (Resource Acquisition Is Initialization)
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

    std::ifstream& getFile() { return file; }
};
```

### Const Correctness
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

### Doxygen
- **Standard**: Dokumentacja podobna do Javadoc
- **Komentarze**: `/** opis */`, `@param`, `@return`
- **Generowanie**: Narzędzie doxygen

## Koncepcje Projektowe

### Zero-overhead Abstraction
- **Definicja**: Abstrakcje nie wprowadzają overhead'u w runtime
- **Przykład**: Inline functions, templates
- **Przykład**:
```cpp
template<typename T>
inline T max(T a, T b) {
    return a > b ? a : b;  // inline - zero overhead
}

int main() {
    int x = max(5, 10);  // kompiluje się do porównania
}
```

### Exception Safety
- **Poziomy**: No-throw, Strong, Basic guarantee
- **Techniki**: RAII, copy-and-swap idiom
- **Przykład**:
```cpp
class SafeVector {
    std::vector<int> data;

public:
    void push_back(int value) {
        data.push_back(value);  // strong guarantee
    }

    size_t size() const noexcept {  // no-throw
        return data.size();
    }
};
```

### Memory Locality
- **Definicja**: Umieszczanie powiązanych danych blisko siebie w pamięci
- **Korzyści**: Lepsze wykorzystanie cache
- **Przykład**:
```cpp
struct Point {
    double x, y, z;  // dane blisko siebie w pamięci
};

std::vector<Point> points;
// Dane są ułożone sekwencyjnie - dobra lokalność
```

### Move Semantics
- **Definicja**: Przenoszenie zasobów zamiast kopiowania
- **Słowa kluczowe**: `&&` (rvalue reference), `std::move()`
- **Przykład**:
```cpp
class ResourceOwner {
    std::vector<int> data;

public:
    ResourceOwner() = default;

    // Move constructor
    ResourceOwner(ResourceOwner&& other) noexcept
        : data(std::move(other.data)) {}

    // Move assignment
    ResourceOwner& operator=(ResourceOwner&& other) noexcept {
        data = std::move(other.data);
        return *this;
    }
};

std::vector<int> createLargeVector() {
    std::vector<int> temp(1000000, 42);
    return temp;  // move semantics - nie kopiuje danych
}
```

## Wzorce Projektowe w C++

### RAII Pattern
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

### Namespace Pattern
- **Definicja**: Grupowanie funkcjonalności w przestrzenie nazw
- **Zastosowanie**: Unikanie konfliktów nazw, organizacja kodu
- **Przykład**:
```cpp
namespace MathUtils {
    namespace Geometry {
        double calculateArea(double radius) {
            return 3.14159 * radius * radius;
        }
    }

    namespace Algebra {
        int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }
    }
}

// Użycie
double area = MathUtils::Geometry::calculateArea(5.0);
int greatestCD = MathUtils::Algebra::gcd(48, 18);
```

### Iterator Pattern
- **Definicja**: Dostęp do elementów kolekcji bez eksponowania struktury
- **Zastosowanie**: Jednolity interfejs dla różnych typów kolekcji
- **Przykład**:
```cpp
template<typename Container>
void printContainer(const Container& container) {
    for (auto it = container.begin(); it != container.end(); ++it) {
        std::cout << *it << " ";
    }
    std::cout << std::endl;
}

std::vector<int> vec = {1, 2, 3, 4, 5};
std::list<std::string> list = {"hello", "world"};

printContainer(vec);   // działa dla vector
printContainer(list);  // działa dla list
```

## Optymalizacje Wydajnościowe w C++

### Fast I/O Operations
- **Optymalizacja**: Wyłączenie synchronizacji z C stdio
- **Przykład**:
```cpp
#include <iostream>
#include <fstream>

int main() {
    // Fast input
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    // Fast output
    std::cout.tie(nullptr);

    // Użycie
    int n;
    std::cin >> n;

    std::ofstream out("output.txt");
    out << "Wynik: " << n << std::endl;
}
```

### Memory Reservation
- **Zasada**: Rezerwuj pamięć z wyprzedzeniem dla znanych rozmiarów
- **Przykład**:
```cpp
std::vector<int> numbers;
numbers.reserve(1000000);  // alokacja z wyprzedzeniem

for (int i = 0; i < 1000000; ++i) {
    numbers.push_back(i);  // bez realokacji
}
```

### Zero-copy Iterators
- **Zasada**: Bezpośredni dostęp do danych bez kopiowania
- **Przykład**:
```cpp
std::vector<std::string> words = {"hello", "world", "cpp"};

// Zero-copy - referencje zamiast kopii
for (const auto& word : words) {  // & - referencja
    std::cout << word << std::endl;
}

// Pessimization - niepotrzebne kopie
for (auto word : words) {  // kopia całego stringa
    std::cout << word << std::endl;
}
```

### Template Metaprogramming dla Wydajności
- **Zasada**: Obliczenia w czasie kompilacji
- **Przykład**:
```cpp
template<int N>
struct Factorial {
    static constexpr int value = N * Factorial<N-1>::value;
};

template<>
struct Factorial<0> {
    static constexpr int value = 1;
};

int main() {
    constexpr int fact5 = Factorial<5>::value;  // obliczone w czasie kompilacji
    std::cout << fact5 << std::endl;  // 120
}
```

### Cache-friendly Data Structures
- **Zasada**: Organizuj dane dla lepszego wykorzystania cache
- **Przykład**:
```cpp
// Cache-friendly - dane blisko siebie
struct Particle {
    float x, y, z;
    float vx, vy, vz;
};

// Cache-unfriendly - dane rozrzucone
struct BadParticle {
    float x;
    float vx;
    // ... inne pola rozrzucone w pamięci
};

// Użycie
std::vector<Particle> particles(10000);
// Wszystkie dane blisko siebie w pamięci
for (auto& p : particles) {
    p.x += p.vx * dt;
    p.y += p.vy * dt;
    p.z += p.vz * dt;
}
```
