### Paradygmat
Paradygmat skupia się na tym, jak coś zrobić, a nie na tym, co zrobić. Jest to sposób, w jaki postrzegamy i interpretujemy określony fragment rzeczywistości. Paradygmat programowania definiuje podejście do rozwiązywania problemów i strukturyzacji kodu w konkretny sposób.
### Imperatywny

Paradygmat programowania, w którym programista opisuje, jak program ma wykonać zadanie, używając instrukcji zmieniających stan programu.

#### Elementy programowania imperatywnego:
1. **Instrukcje** - polecenia, które zmieniają stan programu.
2. **Zmienne** - przechowują stan programu.
3. **Pętle** - umożliwiają wielokrotne wykonanie zestawu instrukcji.
4. **Warunki** - pozwalają na wykonanie różnych instrukcji w zależności od stanu programu.
5. **Funkcje** - grupują instrukcje w celu ich wielokrotnego użycia.

```cpp
#include <iostream>

int main() {
    int sum = 0;
    for (int i = 1; i <= 10; ++i) {
        sum += i;
    }
    std::cout << "Suma od 1 do 10 to: " << sum << std::endl;
    return 0;
}

```

### Proceduralny

Paradygmat programowania, który opiera się na procedurach, czyli podprogramach, które wykonują określone zadania. Jest to podzbiór programowania imperatywnego.

#### Elementy programowania proceduralnego:
1. **Procedury** - podprogramy, które wykonują określone zadania.
2. **Instrukcje** - polecenia, które zmieniają stan programu.
3. **Zmienne** - przechowują stan programu.
4. **Pętle** - umożliwiają wielokrotne wykonanie zestawu instrukcji.
5. **Warunki** - pozwalają na wykonanie różnych instrukcji w zależności od stanu programu.
6. **Funkcje** - grupują instrukcje w celu ich wielokrotnego użycia.

Paradygmat procesduralny jest podtypem paradygmatu imperatywnego.

**Tu po prostu jest funkcja, która coś robi** Impreatywne są python i bash (linia po linii), a procecduralne, kiedy pojawiają sie funkcje.
```cpp
#include <iostream>
#include <vector>

// Funkcja proceduralna do obliczania średniej
double obliczSrednia(const std::vector<double>& liczby) {
    double suma = 0.0;
    for (const auto& liczba : liczby) {
        suma += liczba;
    }
    return suma / liczby.size();
}

int main() {
    std::vector<double> oceny = {4.5, 3.5, 5.0, 4.0, 3.0};
    
    // Imperatywne obliczanie sumy
    double suma = 0.0;
    for (const auto& ocena : oceny) {
        suma += ocena;
    }
    
    // Wywołanie funkcji proceduralnej
    double srednia = obliczSrednia(oceny);
    
    std::cout << "Suma ocen: " << suma << std::endl;
    std::cout << "Średnia ocen: " << srednia << std::endl;
    
    return 0;
}
```

### OOP (Programowanie Obiektowe)

Paradygmat programowania, który opiera się na obiektach, które są instancjami klas. Klasy definiują właściwości i metody, które obiekty mogą posiadać i wykonywać.

#### Elementy i zasady w języku C++23:
1. **Klasy** - definiują właściwości (pola) i metody (funkcje składowe).
2. **Obiekty** - instancje klas, które mogą posiadać własne stany i zachowania.
3. **Enkapsulacja** - ukrywanie danych i implementacji wewnątrz klasy, udostępniając tylko niezbędny interfejs.
4. **Hermetyzacja** - kontrolowanie dostępu do danych składowych poprzez modyfikatory dostępu (`private`, `protected`, `public`).
5. **Dziedziczenie** - umożliwia tworzenie nowych klas na podstawie istniejących (`class Derived : public Base`).
6. **Kompozycja** - alternatywa dla dziedziczenia, polegająca na tworzeniu obiektów złożonych z innych obiektów.
7. **Polimorfizm** - pozwala na używanie wskaźników lub referencji do klas bazowych w celu wywoływania metod klas pochodnych (`virtual` i `override`).
8. **Abstrakcja** - definiowanie interfejsów i klas abstrakcyjnych (`abstract class`).
9. **Interfejsy** - w C++ realizowane jako czysto wirtualne klasy abstrakcyjne.
10. **Konstruktor** - specjalna metoda wywoływana podczas tworzenia obiektu (`ClassName(parameters)`).
11. **Destruktor** - specjalna metoda wywoływana podczas niszczenia obiektu (`~ClassName()`).
12. **Szablony klas** - umożliwiają tworzenie klas generycznych (`template <typename T> class ClassName`).
13. **Moduły** - nowa funkcjonalność w C++23, która pozwala na lepsze zarządzanie kodem (`export module ModuleName;`).
14. **Koncepty** - definiowanie wymagań dla typów używanych w szablonach (`concept ConceptName = requires(T t) { ... };`).
```cpp
#include <iostream>
#include <string>
#include <vector>

class Przedmiot {
protected:
    std::string nazwa;
    double ocena;

public:
    Przedmiot(const std::string& n, double o) : nazwa(n), ocena(o) {}
    virtual void wyswietl() const {
        std::cout << nazwa << ": " << ocena << std::endl;
    }
    virtual double getOcena() const { return ocena; }
};

class PrzedmiotZaawansowany : public Przedmiot {
private:
    int poziomTrudnosci;

public:
    PrzedmiotZaawansowany(const std::string& n, double o, int pt)
        : Przedmiot(n, o), poziomTrudnosci(pt) {}
    void wyswietl() const override {
        Przedmiot::wyswietl();
        std::cout << "Poziom trudności: " << poziomTrudnosci << std::endl;
    }
};

class Student {
private:
    std::string imie;
    std::vector<Przedmiot*> przedmioty;

public:
    Student(const std::string& i) : imie(i) {}
    void dodajPrzedmiot(Przedmiot* p) { przedmioty.push_back(p); }
    void wyswietlOceny() const {
        std::cout << "Oceny studenta " << imie << ":" << std::endl;
        for (const auto& p : przedmioty) {
            p->wyswietl();
        }
    }
    double obliczSrednia() const {
        double suma = 0.0;
        for (const auto& p : przedmioty) {
            suma += p->getOcena();
        }
        return suma / przedmioty.size();
    }
};

int main() {
    Student jan("Jan Kowalski");
    jan.dodajPrzedmiot(new Przedmiot("Matematyka", 4.5));
    jan.dodajPrzedmiot(new PrzedmiotZaawansowany("Fizyka kwantowa", 5.0, 3));
    
    jan.wyswietlOceny();
    std::cout << "Średnia ocen: " << jan.obliczSrednia() << std::endl;
    
    return 0;
}

```
### Generyczny (Programowanie Generyczne)

Paradygmat programowania, który pozwala na pisanie kodu, który może działać z różnymi typami danych. W C++ jest to realizowane za pomocą szablonów (templates).

#### Elementy i zasady w języku C++23:
1. **Szablony funkcji** - umożliwiają tworzenie funkcji, które mogą działać z różnymi typami danych (`template <typename T> T functionName(T param)`).
2. **Szablony klas** - umożliwiają tworzenie klas generycznych (`template <typename T> class ClassName`).
3. **Szablony zmiennych** - pozwalają na definiowanie zmiennych generycznych (`template <typename T> T variableName`).
4. **Szablony aliasów** - umożliwiają tworzenie aliasów typów generycznych (`template <typename T> using AliasName = ExistingType<T>`).
5. **Szablony funkcji członkowskich** - pozwalają na definiowanie funkcji członkowskich w klasach generycznych (`template <typename T> void ClassName<T>::functionName()`).
6. **Szablony zmiennych członkowskich** - umożliwiają definiowanie zmiennych członkowskich w klasach generycznych (`template <typename T> T ClassName<T>::variableName`).
7. **Szablony przyjaciół** - pozwalają na definiowanie funkcji lub klas przyjaciół w szablonach (`template <typename T> friend class FriendClass`).
8. **Szablony specjalizowane** - umożliwiają tworzenie specjalnych wersji szablonów dla określonych typów (`template <> class ClassName<SpecificType>`).
9. **Koncepty** - definiowanie wymagań dla typów używanych w szablonach (`concept ConceptName = requires(T t) { ... };`).
10. **Metaprogramowanie szablonowe** - technika programowania, która wykorzystuje szablony do generowania kodu w czasie kompilacji, umożliwiając wykonywanie obliczeń i manipulacji typami na etapie kompilacji.
11. **SFINAE (Substitution Failure Is Not An Error)** - technika wykorzystywana w metaprogramowaniu szablonowym, która pozwala na wybór odpowiedniej specjalizacji szablonu na podstawie właściwości typów, bez generowania błędów kompilacji.
```cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <type_traits>

// Szablon funkcji do znajdowania maksymalnej wartości
template <typename T>
T znajdzMaks(const std::vector<T>& dane) {
    return *std::max_element(dane.begin(), dane.end());
}

// Szablon klasy do przechowywania i przetwarzania danych
template <typename T>
class Kontener {
private:
    std::vector<T> dane;

public:
    void dodaj(const T& element) {
        dane.push_back(element);
    }

    T znajdzMaks() const {
        return ::znajdzMaks(dane);
    }

    // Metaprogramowanie szablonowe
    template <typename U = T>
    typename std::enable_if<std::is_arithmetic<U>::value, double>::type
    obliczSrednia() const {
        double suma = 0.0;
        for (const auto& element : dane) {
            suma += element;
        }
        return suma / dane.size();
    }
};

int main() {
    Kontener<int> liczby;
    liczby.dodaj(5);
    liczby.dodaj(2);
    liczby.dodaj(8);
    liczby.dodaj(1);

    std::cout << "Maksymalna liczba: " << liczby.znajdzMaks() << std::endl;
    std::cout << "Średnia: " << liczby.obliczSrednia() << std::endl;

    Kontener<std::string> slowa;
    slowa.dodaj("jabłko");
    slowa.dodaj("banan");
    slowa.dodaj("cytryna");

    std::cout << "Najdłuższe słowo: " << slowa.znajdzMaks() << std::endl;
    // slowa.obliczSrednia(); // To by nie skompilowało się ze względu na SFINAE
    
    return 0;
}
```
Funkcjonalny - paradygmat programowania, który traktuje obliczenia jako ewaluację funkcji matematycznych i unika zmiennych stanów oraz efektów ubocznych. Kładzie nacisk na niemutowalność danych, co oznacza, że raz utworzone struktury danych nie powinny być modyfikowane.

#### Elementy i zasady w języku C++23:
1. **Funkcje wyższego rzędu** - funkcje, które mogą przyjmować inne funkcje jako argumenty lub zwracać funkcje jako wyniki (`std::function`). Umożliwiają one implementację technik takich jak currying, czyli częściowe aplikowanie funkcji.
2. **Funkcje anonimowe (lambda)** - funkcje definiowane w miejscu, gdzie są używane, bez konieczności nadawania im nazwy (`[] (int x) { return x * x; }`).
3. **Czyste funkcje** - funkcje, które dla tych samych argumentów zawsze zwracają te same wyniki i nie mają efektów ubocznych.
4. **Niezmienność** - unikanie zmiennych stanów, preferowanie stałych i niezmiennych struktur danych (`const`). Niemutowalność danych jest kluczowa w programowaniu funkcyjnym.
5. **Rekurencja** - technika, w której funkcja wywołuje samą siebie, aby rozwiązać problem.
6. **Funkcje jako obiekty pierwszoklasowe** - funkcje mogą być przekazywane jako argumenty, zwracane z innych funkcji i przypisywane do zmiennych.
7. **Funkcje czystego wyjścia** - funkcje, które nie mają efektów ubocznych i nie zmieniają stanu programu poza zwracaniem wartości.
8. **Funkcje kompozycyjne** - łączenie prostych funkcji w bardziej złożone operacje (`std::bind`, `std::compose`).
9. **Monady** - struktury danych, które reprezentują obliczenia jako sekwencje kroków (`std::optional`, `std::future`).

Przykład wykorzystania std::function i std::bind:
```cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <functional>

// Funkcja wyższego rzędu
template <typename T, typename Func>
std::vector<T> mapuj(const std::vector<T>& dane, Func f) {
    std::vector<T> wynik;
    std::transform(dane.begin(), dane.end(), std::back_inserter(wynik), f);
    return wynik;
}

// Funkcja curried
auto dodaj = [](int a) {
    return [a](int b) {
        return a + b;
    };
};

int main() {
    std::vector<int> liczby = {1, 2, 3, 4, 5};
    
    // Użycie funkcji wyższego rzędu z lambdą
    auto kwadraty = mapuj(liczby, [](int x) { return x * x; });
    
    std::cout << "Kwadraty: ";
    for (const auto& k : kwadraty) {
        std::cout << k << " ";
    }
    std::cout << std::endl;
    
    // Użycie std::function i std::bind
    std::function<int(int)> dodajPiec = std::bind(dodaj, 5);
    std::cout << "5 + 3 = " << dodajPiec(3) << std::endl;
    
    // Kompozycja funkcji
    auto podwoj = [](int x) { return x * 2; };
    auto zwieksz = [](int x) { return x + 1; };
    auto podwojIZwieksz = [&](int x) { return zwieksz(podwoj(x)); };
    
    std::cout << "Podwój i zwiększ 3: " << podwojIZwieksz(3) << std::endl;
    
    return 0;
}
```
