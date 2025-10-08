# Algorytmy i Optymalizacja

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


## Algorytmy Sortujące
- **Bubble Sort**:
  - **Złożoność**: O(n²) dla przypadku przeciętnego i najgorszego
  - **Działanie**: Porównuje sąsiednie elementy i zamienia je, jeśli są w złej kolejności
  - **Zalety**: Prosta implementacja
  - **Wady**: Nieefektywny dla dużych zbiorów danych

- **Selection Sort**:
  - **Złożoność**: O(n²) dla wszystkich przypadków
  - **Działanie**: Znajduje najmniejszy element i umieszcza go na początku
  - **Zalety**: Prosta implementacja, minimalna liczba zamian
  - **Wady**: Nieefektywny dla dużych zbiorów danych

- **Insertion Sort**:
  - **Złożoność**: O(n²) dla przypadku przeciętnego i najgorszego, O(n) dla najlepszego
  - **Działanie**: Buduje posortowaną tablicę element po elemencie
  - **Zalety**: Efektywny dla małych i prawie posortowanych danych
  - **Zastosowanie**: Często używany jako część innych algorytmów

- **Merge Sort**:
  - **Złożoność**: O(n log n) dla wszystkich przypadków
  - **Działanie**: Dziel i zwyciężaj - dzieli tablicę na połowy, sortuje i łączy
  - **Zalety**: Stabilny, przewidywalna wydajność
  - **Wady**: Wymaga dodatkowej pamięci

- **Quick Sort**:
  - **Złożoność**: O(n log n) dla przypadku przeciętnego, O(n²) dla najgorszego
  - **Działanie**: Wybiera element pivot, dzieli tablicę na elementy mniejsze i większe
  - **Zalety**: Bardzo wydajny w praktyce, często używany
  - **Wady**: Niestabilny, zła wydajność dla niektórych danych

- **Heap Sort**:
  - **Złożoność**: O(n log n) dla wszystkich przypadków
  - **Działanie**: Buduje kopiec, zamienia największy element z ostatnim i naprawia kopiec
  - **Zalety**: Wydajny, nie wymaga dodatkowej pamięci
  - **Wady**: Niestabilny, wolniejszy od zoptymalizowanego Quick Sort

## Lokalizowanie Wąskich Gardeł (Bottlenecks)
- **Profilowanie kodu**: Identyfikacja fragmentów kodu zużywających najwięcej zasobów
- **Analiza złożoności algorytmów**: Ocena teoretycznej wydajności
- **Monitorowanie zasobów**: CPU, pamięć, I/O, sieć
- **Metryki wydajności**: Czas odpowiedzi, przepustowość, wykorzystanie zasobów
- **Narzędzia**: Profiler, analizatory kodu, narzędzia monitorujące

## Techniki Optymalizacji
- **Optymalizacja algorytmów**: Wybór odpowiedniego algorytmu dla danego problemu
- **Cachowanie**: Przechowywanie wyników kosztownych obliczeń
- **Indeksowanie baz danych**: Przyspieszenie zapytań
- **Równoległe przetwarzanie**: Wykorzystanie wielu rdzeni/wątków
- **Optymalizacja zapytań**: Refaktoryzacja zapytań SQL
- **Buforowanie**: Zmniejszenie liczby kosztownych operacji I/O
- **Lazy loading**: Ładowanie danych tylko gdy są potrzebne

## Optymalizacje Wydajnościowe Specyficzne dla Języków

### Python

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

# Użycie - wartości obliczane tylko gdy potrzebne
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

### Java

#### BufferedReader dla I/O
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

#### StringBuilder dla konkatenacji
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

#### Stream Pipeline Optimization
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

### C++

#### Fast I/O Operations
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

#### Memory Reservation
- **Zasada**: Rezerwuj pamięć z wyprzedzeniem dla znanych rozmiarów
- **Przykład**:
```cpp
std::vector<int> numbers;
numbers.reserve(1000000);  // alokacja z wyprzedzeniem

for (int i = 0; i < 1000000; ++i) {
    numbers.push_back(i);  // bez realokacji
}
```

#### Zero-copy Iterators
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

#### Template Metaprogramming dla Wydajności
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

#### Cache-friendly Data Structures
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