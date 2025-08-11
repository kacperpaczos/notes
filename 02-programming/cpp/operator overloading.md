# Przeciążanie operatorów w C++

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


Przeciążanie operatorów to technika w C++, która pozwala na nadanie nowego znaczenia standardowym operatorom dla typów zdefiniowanych przez użytkownika. Dzięki temu możemy używać operatorów w sposób intuicyjny z naszymi własnymi klasami.

## Podstawowe zasady:
1. Większość operatorów może być przeciążona
2. Nie można zmienić priorytetu operatorów
3. Nie można tworzyć nowych operatorów
4. Niektóre operatory (np. ::, ., ?:) nie mogą być przeciążone

## Przykład: Przeciążanie operatora dodawania dla klasy Wektor

#include <iostream>

class Wektor {
private:
    double x, y;

public:
    Wektor(double x = 0, double y = 0) : x(x), y(y) {}

    Wektor operator+(const Wektor& inny) const {
        return Wektor(x + inny.x, y + inny.y);
    }

    void wyswietl() const {
        std::cout << "(" << x << ", " << y << ")" << std::endl;
    }
};

int main() {
    Wektor v1(1, 2);
    Wektor v2(3, 4);
    Wektor v3 = v1 + v2;

    std::cout << "v1 = ";
    v1.wyswietl();
    std::cout << "v2 = ";
    v2.wyswietl();
    std::cout << "v1 + v2 = ";
    v3.wyswietl();

    return 0;
}