# Materiały edukacyjne o zasadach projektowania

## Zasady SOLID i pokrewne

### Liskov Substitution Principle
**Opis**: Zasada podstawienia Liskov mówi, że obiekty klas pochodnych powinny być w stanie zastąpić obiekty klas bazowych bez wpływu na poprawność programu. Innymi słowy, jeśli S jest podtypem T, to obiekty typu T mogą być zastąpione obiektami typu S bez zmiany pożądanych właściwości programu.

**Przykład w Javie**:
```java
// Niepoprawne zastosowanie LSP
class Ptak {
    public void lataj() {
        System.out.println("Ptak lata");
    }
}

class Pingwin extends Ptak {
    @Override
    public void lataj() {
        throw new UnsupportedOperationException("Pingwiny nie latają!");
    }
}

// Poprawne zastosowanie LSP
interface Latajacy {
    void lataj();
}

class Ptak {
    // Wspólne cechy wszystkich ptaków
}

class WrobelLata extends Ptak implements Latajacy {
    @Override
    public void lataj() {
        System.out.println("Wróbel lata");
    }
}

class Pingwin extends Ptak {
    // Pingwin nie implementuje interfejsu Latajacy
}
```

**Przykład w Pythonie**:
```python
# Niepoprawne zastosowanie LSP
class Ptak:
    def lataj(self):
        print("Ptak lata")

class Pingwin(Ptak):
    def lataj(self):
        raise NotImplementedError("Pingwiny nie latają!")

# Poprawne zastosowanie LSP
class Ptak:
    # Wspólne cechy wszystkich ptaków
    pass

class Latajacy:
    def lataj(self):
        pass

class Wrobel(Ptak, Latajacy):
    def lataj(self):
        print("Wróbel lata")

class Pingwin(Ptak):
    # Pingwin nie dziedziczy po interfejsie Latajacy
    pass
```

### Open/Closed Principle
**Opis**: Zasada otwarte-zamknięte mówi, że klasy, moduły, funkcje itp. powinny być otwarte na rozszerzanie, ale zamknięte na modyfikacje. Oznacza to, że powinieneś być w stanie dodać nową funkcjonalność bez zmiany istniejącego kodu.

**Przykład w Javie**:
```java
// Przed zastosowaniem OCP
class Kalkulator {
    public double oblicz(double a, double b, String operacja) {
        if (operacja.equals("dodaj")) {
            return a + b;
        } else if (operacja.equals("odejmij")) {
            return a - b;
        }
        return 0;
    }
}

// Po zastosowaniu OCP
interface Operacja {
    double wykonaj(double a, double b);
}

class Dodawanie implements Operacja {
    @Override
    public double wykonaj(double a, double b) {
        return a + b;
    }
}

class Odejmowanie implements Operacja {
    @Override
    public double wykonaj(double a, double b) {
        return a - b;
    }
}

class Mnozenie implements Operacja {
    @Override
    public double wykonaj(double a, double b) {
        return a * b;
    }
}

class Kalkulator {
    public double oblicz(double a, double b, Operacja operacja) {
        return operacja.wykonaj(a, b);
    }
}
```

**Przykład w Pythonie**:
```python
# Przed zastosowaniem OCP
class Kalkulator:
    def oblicz(self, a, b, operacja):
        if operacja == "dodaj":
            return a + b
        elif operacja == "odejmij":
            return a - b
        return 0

# Po zastosowaniu OCP
from abc import ABC, abstractmethod

class Operacja(ABC):
    @abstractmethod
    def wykonaj(self, a, b):
        pass

class Dodawanie(Operacja):
    def wykonaj(self, a, b):
        return a + b

class Odejmowanie(Operacja):
    def wykonaj(self, a, b):
        return a - b

class Mnozenie(Operacja):
    def wykonaj(self, a, b):
        return a * b

class Kalkulator:
    def oblicz(self, a, b, operacja):
        return operacja.wykonaj(a, b)
```

### Dependency Inversion Principle
**Opis**: Zasada odwrócenia zależności mówi, że moduły wysokiego poziomu nie powinny zależeć od modułów niskiego poziomu - oba powinny zależeć od abstrakcji. Abstrakcje nie powinny zależeć od szczegółów; to szczegóły powinny zależeć od abstrakcji.

**Przykład w Javie**:
```java
// Przed zastosowaniem DIP
class EmailSender {
    public void wyslijEmail(String odbiorca, String temat, String tresc) {
        // Konkretna implementacja wysyłania maili przez SendGrid
        System.out.println("Wysyłanie maila przez SendGrid");
    }
}

class PowiadomienieService {
    private EmailSender emailSender = new EmailSender();
    
    public void powiadomUzytkownika(String uzytkownikEmail, String wiadomosc) {
        emailSender.wyslijEmail(uzytkownikEmail, "Powiadomienie", wiadomosc);
    }
}

// Po zastosowaniu DIP
interface DostawcaWiadomosci {
    void wyslij(String odbiorca, String temat, String tresc);
}

class SendGridEmailSender implements DostawcaWiadomosci {
    @Override
    public void wyslij(String odbiorca, String temat, String tresc) {
        System.out.println("Wysyłanie maila przez SendGrid");
    }
}

class SMSSender implements DostawcaWiadomosci {
    @Override
    public void wyslij(String odbiorca, String temat, String tresc) {
        System.out.println("Wysyłanie SMS");
    }
}

class PowiadomienieService {
    private final DostawcaWiadomosci dostawcaWiadomosci;
    
    public PowiadomienieService(DostawcaWiadomosci dostawcaWiadomosci) {
        this.dostawcaWiadomosci = dostawcaWiadomosci;
    }
    
    public void powiadomUzytkownika(String uzytkownikKontakt, String wiadomosc) {
        dostawcaWiadomosci.wyslij(uzytkownikKontakt, "Powiadomienie", wiadomosc);
    }
}
```

**Przykład w Pythonie**:
```python
# Przed zastosowaniem DIP
class EmailSender:
    def wyslij_email(self, odbiorca, temat, tresc):
        # Konkretna implementacja wysyłania maili przez SendGrid
        print("Wysyłanie maila przez SendGrid")

class PowiadomienieService:
    def __init__(self):
        self.email_sender = EmailSender()
    
    def powiadom_uzytkownika(self, uzytkownik_email, wiadomosc):
        self.email_sender.wyslij_email(uzytkownik_email, "Powiadomienie", wiadomosc)

# Po zastosowaniu DIP
from abc import ABC, abstractmethod

class DostawcaWiadomosci(ABC):
    @abstractmethod
    def wyslij(self, odbiorca, temat, tresc):
        pass

class SendGridEmailSender(DostawcaWiadomosci):
    def wyslij(self, odbiorca, temat, tresc):
        print("Wysyłanie maila przez SendGrid")

class SMSSender(DostawcaWiadomosci):
    def wyslij(self, odbiorca, temat, tresc):
        print("Wysyłanie SMS")

class PowiadomienieService:
    def __init__(self, dostawca_wiadomosci):
        self.dostawca_wiadomosci = dostawca_wiadomosci
    
    def powiadom_uzytkownika(self, uzytkownik_kontakt, wiadomosc):
        self.dostawca_wiadomosci.wyslij(uzytkownik_kontakt, "Powiadomienie", wiadomosc)
```

### Composing Objects Principle
**Opis**: Zasada kompozycji obiektów (znana również jako "Preferuj kompozycję nad dziedziczeniem") sugeruje, że lepiej jest budować złożone obiekty przez łączenie mniejszych, niezależnych komponentów niż przez dziedziczenie z klasy bazowej.

**Przykład w Javie**:
```java
// Używanie dziedziczenia
class Pojazd {
    protected String marka;
    protected String model;
    protected int rokProdukcji;
    
    public void jedz() {
        System.out.println("Pojazd jedzie");
    }
}

class Samochod extends Pojazd {
    private int liczbaDrzwi;
    
    @Override
    public void jedz() {
        System.out.println("Samochód jedzie po drodze");
    }
}

class SamochodSportowy extends Samochod {
    private int maksymalnaPredkosc;
    
    @Override
    public void jedz() {
        System.out.println("Samochód sportowy pędzi!");
    }
}

// Używanie kompozycji
class Silnik {
    private int moc;
    
    public void uruchom() {
        System.out.println("Silnik uruchomiony");
    }
}

class Nadwozie {
    private String typ;
    private int liczbaDrzwi;
}

class Zawieszenie {
    private String typ;
    
    public void amortyzuj() {
        System.out.println("Zawieszenie amortyzuje");
    }
}

class Samochod {
    private String marka;
    private String model;
    private int rokProdukcji;
    private Silnik silnik;
    private Nadwozie nadwozie;
    private Zawieszenie zawieszenie;
    
    public Samochod(String marka, String model, Silnik silnik, Nadwozie nadwozie, Zawieszenie zawieszenie) {
        this.marka = marka;
        this.model = model;
        this.silnik = silnik;
        this.nadwozie = nadwozie;
        this.zawieszenie = zawieszenie;
    }
    
    public void jedz() {
        silnik.uruchom();
        zawieszenie.amortyzuj();
        System.out.println("Samochód jedzie");
    }
}
```

**Przykład w Pythonie**:
```python
# Używanie dziedziczenia
class Pojazd:
    def __init__(self):
        self.marka = None
        self.model = None
        self.rok_produkcji = None
    
    def jedz(self):
        print("Pojazd jedzie")

class Samochod(Pojazd):
    def __init__(self):
        super().__init__()
        self.liczba_drzwi = None
    
    def jedz(self):
        print("Samochód jedzie po drodze")

class SamochodSportowy(Samochod):
    def __init__(self):
        super().__init__()
        self.maksymalna_predkosc = None
    
    def jedz(self):
        print("Samochód sportowy pędzi!")

# Używanie kompozycji
class Silnik:
    def __init__(self, moc):
        self.moc = moc
    
    def uruchom(self):
        print("Silnik uruchomiony")

class Nadwozie:
    def __init__(self, typ, liczba_drzwi):
        self.typ = typ
        self.liczba_drzwi = liczba_drzwi

class Zawieszenie:
    def __init__(self, typ):
        self.typ = typ
    
    def amortyzuj(self):
        print("Zawieszenie amortyzuje")

class Samochod:
    def __init__(self, marka, model, silnik, nadwozie, zawieszenie):
        self.marka = marka
        self.model = model
        self.silnik = silnik
        self.nadwozie = nadwozie
        self.zawieszenie = zawieszenie
    
    def jedz(self):
        self.silnik.uruchom()
        self.zawieszenie.amortyzuj()
        print("Samochód jedzie")
```

### Interface Segregation Principle
**Opis**: Zasada segregacji interfejsów mówi, że klient nie powinien być zmuszany do zależności od metod, których nie używa. Lepiej jest mieć wiele małych, specyficznych interfejsów niż jeden duży, ogólny interfejs.

**Przykład w Javie**:
```java
// Przed zastosowaniem ISP
interface Pracownik {
    void pracuj();
    void jedz();
    void odbierzWyplate();
    void kierujZespolem();
}

class PracownikPelnyEtat implements Pracownik {
    @Override
    public void pracuj() {
        System.out.println("Pracuję pełny etat");
    }
    
    @Override
    public void jedz() {
        System.out.println("Jem obiad w firmowej stołówce");
    }
    
    @Override
    public void odbierzWyplate() {
        System.out.println("Odbieram wypłatę");
    }
    
    @Override
    public void kierujZespolem() {
        System.out.println("Kieruję zespołem");
    }
}

class Stażysta implements Pracownik {
    @Override
    public void pracuj() {
        System.out.println("Pracuję jako stażysta");
    }
    
    @Override
    public void jedz() {
        System.out.println("Jem obiad w firmowej stołówce");
    }
    
    @Override
    public void odbierzWyplate() {
        System.out.println("Odbieram stypendium");
    }
    
    @Override
    public void kierujZespolem() {
        // Stażysta nie kieruje zespołem, ale musi implementować tę metodę
        throw new UnsupportedOperationException("Stażysta nie może kierować zespołem");
    }
}

// Po zastosowaniu ISP
interface Pracujacy {
    void pracuj();
}

interface Jedzacy {
    void jedz();
}

interface OdbierającyWyplate {
    void odbierzWyplate();
}

interface KierujacyZespolem {
    void kierujZespolem();
}

class PracownikPelnyEtat implements Pracujacy, Jedzacy, OdbierającyWyplate, KierujacyZespolem {
    @Override
    public void pracuj() {
        System.out.println("Pracuję pełny etat");
    }
    
    @Override
    public void jedz() {
        System.out.println("Jem obiad w firmowej stołówce");
    }
    
    @Override
    public void odbierzWyplate() {
        System.out.println("Odbieram wypłatę");
    }
    
    @Override
    public void kierujZespolem() {
        System.out.println("Kieruję zespołem");
    }
}

class Stażysta implements Pracujacy, Jedzacy, OdbierającyWyplate {
    @Override
    public void pracuj() {
        System.out.println("Pracuję jako stażysta");
    }
    
    @Override
    public void jedz() {
        System.out.println("Jem obiad w firmowej stołówce");
    }
    
    @Override
    public void odbierzWyplate() {
        System.out.println("Odbieram stypendium");
    }
    // Stażysta nie implementuje interfejsu KierujacyZespolem
}
```

**Przykład w Pythonie**:
```python
# Przed zastosowaniem ISP
from abc import ABC, abstractmethod

class Pracownik(ABC):
    @abstractmethod
    def pracuj(self):
        pass
    
    @abstractmethod
    def jedz(self):
        pass
    
    @abstractmethod
    def odbierz_wyplate(self):
        pass
    
    @abstractmethod
    def kieruj_zespolem(self):
        pass

class PracownikPelnyEtat(Pracownik):
    def pracuj(self):
        print("Pracuję pełny etat")
    
    def jedz(self):
        print("Jem obiad w firmowej stołówce")
    
    def odbierz_wyplate(self):
        print("Odbieram wypłatę")
    
    def kieruj_zespolem(self):
        print("Kieruję zespołem")

class Stazysta(Pracownik):
    def pracuj(self):
        print("Pracuję jako stażysta")
    
    def jedz(self):
        print("Jem obiad w firmowej stołówce")
    
    def odbierz_wyplate(self):
        print("Odbieram stypendium")
    
    def kieruj_zespolem(self):
        # Stażysta nie kieruje zespołem, ale musi implementować tę metodę
        raise NotImplementedError("Stażysta nie może kierować zespołem")

# Po zastosowaniu ISP
class Pracujacy(ABC):
    @abstractmethod
    def pracuj(self):
        pass

class Jedzacy(ABC):
    @abstractmethod
    def jedz(self):
        pass

class OdbierajacyWyplate(ABC):
    @abstractmethod
    def odbierz_wyplate(self):
        pass

class KierujacyZespolem(ABC):
    @abstractmethod
    def kieruj_zespolem(self):
        pass

class PracownikPelnyEtat(Pracujacy, Jedzacy, OdbierajacyWyplate, KierujacyZespolem):
    def pracuj(self):
        print("Pracuję pełny etat")
    
    def jedz(self):
        print("Jem obiad w firmowej stołówce")
    
    def odbierz_wyplate(self):
        print("Odbieram wypłatę")
    
    def kieruj_zespolem(self):
        print("Kieruję zespołem")

class Stazysta(Pracujacy, Jedzacy, OdbierajacyWyplate):
    def pracuj(self):
        print("Pracuję jako stażysta")
    
    def jedz(self):
        print("Jem obiad w firmowej stołówce")
    
    def odbierz_wyplate(self):
        print("Odbieram stypendium")
    # Stażysta nie implementuje interfejsu KierujacyZespolem
```

### Principle of Least Knowledge
**Opis**: Zasada najmniejszej wiedzy (znana również jako Prawo Demeter) mówi, że obiekt powinien mieć ograniczoną wiedzę o innych obiektach i powinien komunikować się tylko z bezpośrednio powiązanymi obiektami. Mówiąc prościej: "Rozmawiaj tylko z najbliższymi przyjaciółmi".

**Przykład w Javie**:
```java
// Naruszenie zasady Least Knowledge
class HistoriaTransakcji {
    public List<Transakcja> getTransakcje() {
        return Arrays.asList(new Transakcja("Zakup", 100), new Transakcja("Wpłata", 200));
    }
}

class KontoBankowe {
    private HistoriaTransakcji historiaTransakcji = new HistoriaTransakcji();
    
    public HistoriaTransakcji getHistoriaTransakcji() {
        return historiaTransakcji;
    }
}

class Klient {
    private KontoBankowe konto = new KontoBankowe();
    
    public void wydrukujTransakcje() {
        // Naruszenie zasady - klient zna szczegóły implementacji HistoriaTransakcji
        List<Transakcja> transakcje = konto.getHistoriaTransakcji().getTransakcje();
        for (Transakcja transakcja : transakcje) {
            System.out.println(transakcja);
        }
    }
}

// Zgodnie z zasadą Least Knowledge
class HistoriaTransakcji {
    private List<Transakcja> transakcje = Arrays.asList(new Transakcja("Zakup", 100), new Transakcja("Wpłata", 200));
    
    public List<Transakcja> getTransakcje() {
        return transakcje;
    }
    
    public void wydrukujTransakcje() {
        for (Transakcja transakcja : transakcje) {
            System.out.println(transakcja);
        }
    }
}

class KontoBankowe {
    private HistoriaTransakcji historiaTransakcji = new HistoriaTransakcji();
    
    public List<Transakcja> getTransakcje() {
        return historiaTransakcji.getTransakcje();
    }
    
    public void wydrukujTransakcje() {
        historiaTransakcji.wydrukujTransakcje();
    }
}

class Klient {
    private KontoBankowe konto = new KontoBankowe();
    
    public void wydrukujTransakcje() {
        // Zgodnie z zasadą - klient wywołuje tylko metodę na swoim bezpośrednim obiekcie
        konto.wydrukujTransakcje();
    }
}
```

**Przykład w Pythonie**:
```python
# Naruszenie zasady Least Knowledge
class HistoriaTransakcji:
    def get_transakcje(self):
        return [{"typ": "Zakup", "kwota": 100}, {"typ": "Wpłata", "kwota": 200}]

class KontoBankowe:
    def __init__(self):
        self.historia_transakcji = HistoriaTransakcji()
    
    def get_historia_transakcji(self):
        return self.historia_transakcji

class Klient:
    def __init__(self):
        self.konto = KontoBankowe()
    
    def wydrukuj_transakcje(self):
        # Naruszenie zasady - klient zna szczegóły implementacji HistoriaTransakcji
        transakcje = self.konto.get_historia_transakcji().get_transakcje()
        for transakcja in transakcje:
            print(f"Typ: {transakcja['typ']}, Kwota: {transakcja['kwota']}")

# Zgodnie z zasadą Least Knowledge
class HistoriaTransakcji:
    def __init__(self):
        self.transakcje = [{"typ": "Zakup", "kwota": 100}, {"typ": "Wpłata", "kwota": 200}]
    
    def get_transakcje(self):
        return self.transakcje
    
    def wydrukuj_transakcje(self):
        for transakcja in self.transakcje:
            print(f"Typ: {transakcja['typ']}, Kwota: {transakcja['kwota']}")

class KontoBankowe:
    def __init__(self):
        self.historia_transakcji = HistoriaTransakcji()
    
    def get_transakcje(self):
        return self.historia_transakcji.get_transakcje()
    
    def wydrukuj_transakcje(self):
        self.historia_transakcji.wydrukuj_transakcje()

class Klient:
    def __init__(self):
        self.konto = KontoBankowe()
    
    def wydrukuj_transakcje(self):
        # Zgodnie z zasadą - klient wywołuje tylko metodę na swoim bezpośrednim obiekcie
        self.konto.wydrukuj_transakcje()
```

## Jak stosować te zasady

1. Staraj się identyfikować problemy w istniejącym kodzie, które naruszają te zasady
2. Refaktoryzuj kod, aby zastosować odpowiednie zasady projektowania
3. Stosuj te zasady od początku projektu, aby uniknąć problemów w przyszłości
4. Pamiętaj, że zasady są przewodnikami, a nie sztywnymi regułami - używaj ich z rozsądkiem 