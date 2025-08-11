# Abstrakcje i Interfejsy

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


## Abstrakcja
- **Definicja**: Ukrywanie szczegółów implementacyjnych, eksponowanie tylko kluczowej funkcjonalności
- **Klasa abstrakcyjna**: Może zawierać implementację metod oraz pola, ale nie można tworzyć jej instancji
- **Zastosowanie**: Definiowanie wspólnych zachowań dla klas pochodnych
- **Zalety**: Redukcja duplikacji kodu, zapewnienie spójnego interfejsu

## Interfejs
- **Definicja**: Kontrakt określający jakie metody musi implementować klasa
- **Różnica od abstrakcji**: Tylko deklaracje metod, bez implementacji, możliwe implementowanie wielu interfejsów
- **Zastosowanie**: Określanie funkcjonalności niezależnej od hierarchii dziedziczenia
- **Zalety**: Umożliwia polimorfizm, luźne powiązanie między klasami

## Wystawianie jako Usługa
- **Definicja**: Udostępnianie funkcjonalności jako niezależnego serwisu
- **Zastosowanie**: Architektura mikrousług, system wtyczek
- **Zalety**: Modularna architektura, możliwość niezależnego skalowania
- **Techniki**: Dependency Injection, Service Locator

## Dispose Pattern
- **Definicja**: Wzorzec do kontrolowanego zwalniania zasobów niezarządzanych
- **IDisposable**: Interfejs w .NET wymuszający implementację metody Dispose()
- **Zastosowanie**: Zarządzanie zasobami takimi jak pliki, połączenia DB, uchwyty systemowe
- **Implementacja**:
  - Jawne wywołanie Dispose()
  - Użycie bloku using dla automatycznego wywołania Dispose()

## Relacje Między Obiektami

### Agregacja
- **Definicja**: Relacja, w której jeden obiekt zawiera inne obiekty, które mogą istnieć niezależnie
- **Przykład**: Klasa `Uniwersytet` zawiera listę `Studentów`, ale studenci mogą istnieć poza uniwersytetem
- **Zastosowanie**: Modelowanie luźnych powiązań między obiektami
- **Implementacja**: Referencje do obiektów, które mogą być współdzielone

### Kompozycja
- **Definicja**: Silniejsza forma agregacji, w której obiekty są ściśle powiązane i nie mogą istnieć niezależnie
- **Przykład**: Klasa `Samochód` zawiera `Silnik`, który nie może istnieć poza samochodem
- **Zastosowanie**: Modelowanie silnych powiązań, gdzie obiekty są częścią całości
- **Implementacja**: Obiekty tworzone i zarządzane przez obiekt zawierający

### Asocjacja
- **Definicja**: Ogólna relacja między obiektami, która może być jedno- lub dwukierunkowa
- **Przykład**: Relacja między `Nauczycielem` a `Studentem` (nauczyciel uczy studenta, student jest uczony przez nauczyciela)
- **Zastosowanie**: Modelowanie różnych typów powiązań między obiektami
- **Implementacja**: Referencje między obiektami, które mogą być opcjonalne lub wymagane 