# Wzorzec Event-Driven

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


## 📝 Cel
Wzorzec Event-Driven definiuje sposób komunikacji między komponentami systemu poprzez zdarzenia, gdzie komponenty reagują na zdarzenia zamiast aktywnie kontrolować przepływ wykonania.

## 🎯 Problem
Masz system, w którym:
- Komponenty muszą komunikować się ze sobą
- Nie chcesz ścisłego powiązania między komponentami
- Potrzebujesz asynchronicznej komunikacji
- Chcesz dynamicznie dodawać i usuwać reakcje na zdarzenia

## 🔧 Rozwiązanie
1. Zdefiniuj strukturę zdarzeń (Event)
2. Stwórz mechanizm dystrybucji zdarzeń (Event Bus/Dispatcher)
3. Zaimplementuj event handlers (obserwatorów)
4. Komponenty publikują zdarzenia zamiast bezpośrednio wywoływać inne komponenty

## 🏗️ Struktura
```
Event Source → Event → Event Bus → Event Handlers
     ↓           ↓         ↓           ↓
  Generuje   Struktura  Dystrybucja  Reakcja
  zdarzenie   danych     i routing    na zdarzenie
```

## 📋 Kluczowe komponenty

| Komponent | Opis |
|-----------|------|
| **Event** | Wystąpienie określonego faktu w systemie |
| **Event Source** | Komponent generujący zdarzenia |
| **Event Bus** | Mechanizm dystrybucji zdarzeń |
| **Event Handler** | Funkcja reagująca na zdarzenie |
| **Observer** | Obiekt subskrybujący zdarzenia |

## 🔄 Przepływ
1. Event Source generuje zdarzenie
2. Event Bus odbiera zdarzenie
3. Event Bus dystrybuuje zdarzenie do wszystkich zarejestrowanych handlerów
4. Event Handlers reagują na zdarzenie

## ✅ Zalety
- **Luźne powiązanie** - komponenty nie znają się bezpośrednio
- **Asynchroniczność** - nieblokująca komunikacja
- **Dynamiczność** - łatwe dodawanie/usuwanie reakcji
- **Skalowalność** - wiele handlerów dla jednego zdarzenia
- **Testowalność** - łatwe mockowanie zdarzeń

## ⚠️ Wady
- **Złożoność** - trudniejsze debugowanie przepływu
- **Wydajność** - overhead dystrybucji zdarzeń
- **Spójność** - problemy z kolejnością zdarzeń
- **Memory leaks** - nieprawidłowe zarządzanie subskrypcjami

## 🔗 Powiązane wzorce

### Observer vs Publish/Subscribe

| Aspekt | Observer Pattern | Publish/Subscribe |
|--------|------------------|-------------------|
| **Komunikacja** | Bezpośrednia | Przez pośrednika (broker) |
| **Zakres** | W obrębie jednego procesu | Między procesami/systemami |
| **Złożoność** | Prosty | Bardziej złożony |
| **Wydajność** | Szybki | Wolniejszy (overhead brokera) |
| **Niezawodność** | Brak gwarancji | Gwarancje dostarczania |
| **Skalowalność** | Ograniczona | Wysoka |

### Inne wzorce
- **Mediator Pattern** - koordynacja komunikacji między komponentami
- **Command Pattern** - enkapsulacja żądań jako obiektów
- **Event Sourcing** - przechowywanie stanu jako sekwencji zdarzeń

## 📚 Przykłady zastosowania
- Systemy GUI (React, Vue, Angular)
- Mikrousługi komunikujące się przez zdarzenia
- Systemy gier (Unity, Unreal Engine)
- IoT - czujniki i akcje
- Systemy monitorowania i alertów
