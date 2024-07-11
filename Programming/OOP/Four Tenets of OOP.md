1. Abstrakcja
   - Ukrywanie złożoności i przedstawianie tylko niezbędnych cech obiektu
   - Pozwala skupić się na tym, co obiekt robi, a nie jak to robi

2. Enkapsulacja
   - Ukrywanie wewnętrznych szczegółów implementacji obiektu
   - Kontrolowanie dostępu do danych poprzez metody publiczne

3. Dziedziczenie
   - Tworzenie nowych klas na podstawie istniejących
   - Umożliwia ponowne wykorzystanie kodu i tworzenie hierarchii klas

4. Polimorfizm
   - Zdolność obiektów różnych klas do reagowania na te same metody w różny sposób
   - Umożliwia pisanie bardziej elastycznego i rozszerzalnego kodu

Przykład w C++:
```cpp
#include <iostream>
#include <string>

class Zwierze {
protected:
    std::string nazwa;
public:
    Zwierze(const std::string& n) : nazwa(n) {}
    virtual void wydajDzwiek() = 0;
};

class Pies : public Zwierze {
public:
    Pies(const std::string& n) : Zwierze(n) {}
    void wydajDzwiek() override {
        std::cout << nazwa << " szczeka: Hau hau!" << std::endl;
    }
};

class Kot : public Zwierze {
public:
    Kot(const std::string& n) : Zwierze(n) {}
    void wydajDzwiek() override {
        std::cout << nazwa << " miauczy: Miau miau!" << std::endl;
    }
};

int main() {
    Pies pies("Burek");
    Kot kot("Mruczek");
    
    pies.wydajDzwiek();
    kot.wydajDzwiek();
    
    return 0;
}
```