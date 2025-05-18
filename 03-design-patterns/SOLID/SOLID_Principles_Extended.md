# Zasady SOLID - Rozszerzenie

## S - Single Responsibility Principle
- **Zasada**: Klasa powinna mieć tylko jeden powód do zmiany
- **Praktycznie**: Każda klasa odpowiada za jedną i tylko jedną funkcjonalność
- **Przykład**: Klasa `OrderProcessor` odpowiada tylko za przetwarzanie zamówień, a nie za ich walidację, płatności, itp.

## O - Open/Closed Principle
- **Zasada**: Klasy powinny być otwarte na rozszerzenie, ale zamknięte na modyfikację
- **Praktycznie**: Dodawanie nowych funkcji bez zmiany istniejącego kodu
- **Techniki**: Dziedziczenie, kompozycja, implementacja interfejsów
- **Przykład**: System filtrów, gdzie nowe filtry można dodać bez zmiany istniejącego kodu filtrującego

## L - Liskov Substitution Principle
- **Zasada**: Obiekty klas bazowych powinny być zastępowalne przez obiekty klas pochodnych
- **Praktycznie**: Klasa pochodna nie powinna zmieniać zachowania klasy bazowej
- **Przykład**: Jeśli `Square` dziedziczy po `Rectangle`, to musi zachowywać się jak prostokąt we wszystkich kontekstach

## I - Interface Segregation Principle
- **Zasada**: Wiele specyficznych interfejsów jest lepsze niż jeden ogólny
- **Praktycznie**: Klasa nie powinna być zmuszana do implementacji metod, których nie używa
- **Przykład**: Zamiast jednego interfejsu `Worker` z metodami `work()` i `eat()`, lepiej stworzyć dwa: `Workable` i `Eatable`

## D - Dependency Inversion Principle
- **Zasada**: Moduły wysokopoziomowe nie powinny zależeć od modułów niskopoziomowych. Oba powinny zależeć od abstrakcji
- **Praktycznie**: Programuj do interfejsu, nie do implementacji
- **Techniki**: Dependency Injection, IoC kontenery
- **Przykład**: Klasa `NotificationService` zależna od interfejsu `IMessageSender`, a nie konkretnej implementacji

## Praktyczne zastosowanie SOLID
- Zasady SOLID najlepiej stosować w złożonych systemach
- W małych projektach lub prostych funkcjonalnościach mogą prowadzić do nadmiernej złożoności
- Należy zachować pragmatyczne podejście i stosować zasady tam, gdzie przynoszą korzyść
- Główne cele: utrzymywalność kodu, możliwość rozszerzania, testowalność 