Programowanie obiektowe:

Abstrakcja (zasada abstrakcji, Arystoteles) to wyodrębnienie z rzeczywistości oraz uogólnienie (uproszczenie) najważniejszych cech problemu
i wyrażenie rozwiązania w obrębie tych cech. Na przykład, człowiek składa się z wielu miliardów komórek połączonych ze sobą,
tworzących wiele autonomicznych organów współpracujących ze sobą. Natomiast w celu uproszczenia możemy najważniejsze cechy człowieka zapisać w taki sposób:
* rasa
* wzrost
* waga
* wiek
* imię
* nazwisko
* wykształcenie

### Klasa
Klasa jest to zestaw cech oraz możliwych akcji, klasa to nowy własny typ danych zaprojektowany przez programistę umożliwiający dostęp do własności (cech)
poprzez funkcje składowe klasy (metody).
Obiekt to wycinek rzeczywistości posiadający własne cechy (właściwości) oraz mogący wykonywać pewne akcje (metody).

```cpp
class Pracownik {
public:
    std::string imie;
    std::string nazwisko;
    int wiek;
    double pensja;

    Pracownik(std::string imie, std::string nazwisko, int wiek, double pensja)
        : imie(imie), nazwisko(nazwisko), wiek(wiek), pensja(pensja) {}
};
```

### Hermetyzacja
Hermetyzacja polega na ukrywaniu pewnych danych składowych (cech obiektu) lub metod (funkcji składowych) obiektów danej klasy tak, aby były one dostępne tylko metodom składowym danej klasy lub funkcjom zaprzyjaźnionym.

### Polimorfizm
Polimorfizm - wielopostaciowość. Mechanizmy pozwalające programiście używać wartości, zmiennych i podprogramów na kilka różnych sposobów. Inaczej mówiąc, jest to możliwość wyabstrahowania wyrażeń od konkretnych typów.

### Dziedziczenie
Dziedziczenie - to inaczej powtórne wykorzystanie cech oraz działań klasy podstawowej przez klasę pochodną oraz rozszerzenie klasy podstawowej o nowe metody.

### Konstruktor
Konstruktor - metoda składowa klasy, której nazwa jest identyczna co nazwa klasy. Podczas tworzenia obiektu jest wywoływany konstruktor, którego zadaniem jest prawidłowe przypisanie wartości początkowych do obiektu. W klasie może posiadać więcej konstruktorów. W takiej sytuacji mamy do czynienia z przeciążeniem nazwy konstruktora.

### Destruktor
Destruktor - jest to metoda składowa klasy uruchamiana po usunięciu obiektu klasy. Destruktor służy do "czyszczenia" po obiekcie. Podczas tworzenia destruktora nie zwraca on żadnego typu. Destruktor w przeciwieństwie do konstruktora może być tylko jeden. Nie można przeciążać nazwy destruktora. Dodatkowo do destruktora nie przekazujemy żadnych argumentów.
