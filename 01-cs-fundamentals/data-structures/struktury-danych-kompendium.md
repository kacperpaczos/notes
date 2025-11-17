# Kompendium Struktur Danych i Ich Cech

Kompleksowy przewodnik po fundamentalnych pojęciach struktur danych, ich cechach, relacjach oraz związkach z notacją matematyczną. Każda sekcja zawiera definicję, cechy, przykłady w Pythonie i skojarzenia matematyczne (np. relacje, złożoności, funkcje).

---

## 1. Wprowadzenie

### 1.1 Cel dokumentu
- Zbudować wspólne słownictwo algorytmiczne wokół rekordów, danych satelitarnych i kluczowych struktur danych.
- Powiązać intuicje programistyczne z formalnym aparatem matematycznym (notacja asymptotyczna, relacje, funkcje).
- Zapewnić praktyczne przykłady w Pythonie, które można szybko wykorzystać w projektach lub rozmowach technicznych.

### 1.2 Zakres tematyczny
- Pojęcia mikro (klucz, rekord, dane satelitarne, pole, payload).
- Pojęcia makro (ADT, mutowalność, złożoności, porządki, iteratory).
- Tabele porównawcze, diagramy relacji i przykłady kodu.
- Słownik terminów z definicjami w języku polskim.

### 1.3 Konwencje i notacja matematyczna
- **Złożoności asymptotyczne:** Big-O `O(f(n))`, Theta `Θ(f(n))`, Omega `Ω(f(n))`.
- **Zbiory i relacje:** `K` – zbiór kluczy, `V` – zbiór wartości (danych satelitarnych), relacja `f: K → V`.
- **Operacje CRUD:** `Create`, `Read`, `Update`, `Delete`.
- **Symbolika Python:** `dict`, `dataclass`, `NamedTuple`, `typing.Protocol`.

---

## 2. Podstawowe pojęcia związane z rekordami i danymi

### 2.1 Klucz (key)
- **Definicja:** Unikalny identyfikator rekordu w zbiorze `K`, często element porządkujący.
- **Cechy:** Unikatowość, porównywalność (`<`, `>`), podatność na haszowanie.
- **Skojarzenia matematyczne:** Funkcja injektywna `id: K → ℕ`, relacje porządku totalnego.
- **Python:**
```python
from dataclasses import dataclass

@dataclass(frozen=True)
class KlientID:
    numer: int

klucz = KlientID(42)
```

### 2.2 Dane satelitarne (satellite data)
- **Definicja:** Informacje `V` przechowywane przy kluczu, nie uczestniczą w wyszukiwaniu.
- **Cechy:** Mogą być strukturami złożonymi; często zmieniają się częściej niż klucze.
- **Skojarzenia matematyczne:** Mapowanie `assoc: K → V`, gdzie `V` może być wielowymiarowe.
- **Python:**
```python
dane = {"email": "ala@example.com", "saldo": 120.50}
```

### 2.3 Rekord (record)
- **Definicja:** Uporządkowany zbiór pól `⟨k, v₁, v₂, …⟩`, często `k` pełni rolę klucza.
- **Cechy:** Spójność danych, możliwość walidacji, często reprezentowany jako `dict`, `dataclass`.
- **Skojarzenia matematyczne:** Kartezjański produkt `K × V₁ × V₂ × …`.
- **Python:**
```python
from dataclasses import dataclass

@dataclass
class Klient:
    id: int
    imie: str
    saldo: float
```

### 2.4 Pole (field)
- **Definicja:** Najmniejsza nazwana jednostka danych w rekordzie.
- **Cechy:** Typ statyczny/dynamiczny, może mieć ograniczenia (`wiek ∈ ℕ`).
- **Skojarzenia matematyczne:** Funkcja projekcji `π_i`.
- **Python:**
```python
saldo = klient.saldo  # pole typu float
```

### 2.5 Krotka (tuple)
- **Definicja:** Niezmienny, indeksowany zbiór elementów, opcjonalnie nazwanych.
- **Cechy:** Stała długość, szybkie porównania leksykograficzne.
- **Skojarzenia matematyczne:** Produkt kartezjański, klasy ekwiwalencji.
- **Python:**
```python
rekord = (42, "Ala", 120.50)
```

### 2.6 Klasa / struktura (class / struct)
- **Definicja:** Konstrukcja opisująca rekord wraz z metodami.
- **Cechy:** Hermetyzacja, dziedziczenie, kontrakty typów.
- **Skojarzenia matematyczne:** Typ algebraiczny, obiekt jako para `(stan, operacje)`.
- **Python:**
```python
class Konto:
    def __init__(self, id, saldo):
        self.id = id
        self.saldo = saldo
```

### 2.7 Tablica asocjacyjna / mapa (dictionary)
- **Definicja:** Struktura odwzorowująca `K → V`.
- **Cechy:** O(1) średni odczyt/zapis dzięki tablicy haszującej.
- **Skojarzenia matematyczne:** Funkcja częściowa, graf dwudzielny `K ↔ V`.
- **Python:**
```python
klienci = {
    42: {"imie": "Ala", "saldo": 120.50}
}
```

### 2.8 Rekord złożony (composite record)
- **Definicja:** Rekord, którego klucz składa się z kilku pól `k = (k₁, k₂, …)`.
- **Cechy:** Wymaga porządku leksykograficznego i złożonych indeksów.
- **Skojarzenia matematyczne:** Produkt `K₁ × K₂`, klucze złożone w bazach danych.
- **Python:**
```python
klucz = ("PL", "00-123")
indeks[klucz] = {"miasto": "Warszawa"}
```

### 2.9 Indeks (index)
- **Definicja:** Pomocnicza struktura `I` mapująca klucze do lokalizacji rekordów.
- **Cechy:** Przyspiesza `Read`, kosztuje dodatkową pamięć i aktualizacje.
- **Skojarzenia matematyczne:** Funkcja `idx: K → ℕ`, drzewa B, hasze.
- **Python (prosty słownik offsetów):**
```python
indeks = {klient["id"]: pozycja_w_pliku}
```

### 2.10 Payload
- **Definicja:** „Ładunek” – właściwe dane niebędące kluczem.
- **Cechy:** Może być dużą strukturą (JSON, binaria).
- **Skojarzenia matematyczne:** Wektor atrybutów `v ∈ ℝⁿ`.
- **Python:**
```python
payload = {"transakcje": [100, -20, 50]}
```

### 2.11 Primary key / Foreign key
- **Primary key:** Minimalny zbiór pól gwarantujący unikalność rekordu.
- **Foreign key:** Pole wskazujące na klucz główny innej tabeli (relacja `K_a → K_b`).
- **Skojarzenia matematyczne:** Relacje referencyjne, funkcje zależności, ograniczenia integralności.
- **Python (symulacja):**
```python
klient = {"id": 42}
zamowienie = {"id": 7, "klient_id": klient["id"]}
```

### 2.12 Hash / Hash table
- **Hash:** Funkcja `h: K → [0, m-1]`.
- **Hash table:** Tablica kubełków, gdzie przechowywane są `(klucz, dane satelitarne)`.
- **Cechy:** Średnio `O(1)` odczyt, `O(n)` w najgorszym przypadku kolizji.
- **Skojarzenia matematyczne:** Funkcje mieszające, probabilistyka kolizji, teoria liczb (modulo, liczby pierwsze).
- **Python:**
```python
hash(klucz) % m
```

### 2.13 Tablica (array)
- **Definicja:** Kontyguacyjna sekwencja elementów tego samego typu, indeksowana liczbami całkowitymi `A[0..n-1]`.
- **Cechy:** Dostęp losowy `O(1)`, stały rozmiar (statyczna) lub dynamiczny, efektywna pamięć.
- **Skojarzenia matematyczne:** Wektor `v ∈ Vⁿ`, funkcja `f: ℕ → T`, ciąg `(a₀, a₁, …, aₙ₋₁)`.
- **Złożoność:** Odczyt `O(1)`, wstawienie `O(n)` (przesunięcia), usunięcie `O(n)`.
- **Python:**
```python
# W Pythonie list jest dynamiczną tablicą
tablica = [1, 2, 3, 4, 5]
tablica[2] = 10  # O(1) dostęp
tablica.insert(1, 99)  # O(n) wstawienie
```

### 2.14 Zmienna (variable)
- **Definicja:** Nazwane miejsce w pamięci przechowujące wartość, które może być modyfikowane.
- **Cechy:** Ma typ (statyczny/dynamiczny), zakres (scope), czas życia (lifetime).
- **Skojarzenia matematyczne:** Funkcja przypisania `x := v`, zmienne w rachunku lambda `λx.`, wiązanie wartości.
- **Python:**
```python
x = 42          # zmienna całkowita
y = "tekst"     # zmienna string
z = [1, 2, 3]   # zmienna lista (referencja)
```

### 2.15 String (ciąg znaków)
- **Definicja:** Sekwencja znaków `s = c₀c₁…cₙ₋₁`, gdzie `cᵢ ∈ Σ` (alfabet).
- **Cechy:** Niemutowalny w Pythonie (tworzy nowe obiekty), może być indeksowany, wspiera konkatenację.
- **Skojarzenia matematyczne:** Słowo nad alfabetem `Σ*`, język formalny, automaty skończone, wyrażenia regularne.
- **Złożoność:** Długość `|s| = n`, konkatenacja `O(n+m)`, wyszukiwanie wzorca `O(n·m)` (naiwne) lub `O(n+m)` (KMP).
- **Python:**
```python
s = "Hello"
print(s[0])     # O(1) dostęp - 'H'
s + " World"    # O(n+m) konkatenacja (tworzy nowy string)
len(s)          # O(1) długość
```

---

## 3. Ogólne pojęcia struktur danych

### 3.1 Abstrakcyjny Typ Danych (ADT)
- **Definicja:** Specyfikacja operacji i zachowania bez szczegółów implementacyjnych.
- **Przykład:** „Stos” jako ADT z operacjami `push`, `pop`.
- **Matematycznie:** Zbiór operacji i aksjomatów (algebra abstrakcyjna).

### 3.2 Implementacja vs interfejs
- **Interfejs:** Zbiór metod publicznych.
- **Implementacja:** Konkretna struktura i algorytmy.
- **Python (Protocol + klasa):**
```python
from typing import Protocol, Any

class Stos(Protocol):
    def push(self, value: Any) -> None: ...
    def pop(self) -> Any: ...
```

### 3.3 Mutowalność vs niemutowalność
- **Mutowalne:** Zmieniają stan w miejscu (lista).
- **Niemutowalne:** Tworzą nowe wersje (tuple, `frozenset`).
- **Matematyka:** Funkcje destruktywne vs funkcje czyste.

### 3.4 Złożoność czasowa
- **Definicje:** `T(n)`, `O`, `Θ`, `Ω`.
- **Przykłady:** Wyszukiwanie liniowe `O(n)`, binarne `O(log n)`.
- **Skojarzenia matematyczne:** Analiza funkcji rekurencyjnych, rachunek różniczkowy/logarytmy.

### 3.5 Złożoność przestrzenna
- **Definicje:** Ilość pamięci pomocniczej `S(n)`.
- **Przykład:** Tablica `n` elementów → `Θ(n)`.
- **Skojarzenia matematyczne:** Funkcje wzrostu, ograniczenia pamięci, analiza amortyzowana.

### 3.6 Operacje CRUD
- **Create:** wstawienie nowego rekordu.
- **Read:** odczyt danych satelitarnych po kluczu.
- **Update:** modyfikacja pól.
- **Delete:** usunięcie rekordu.
- **Skojarzenia matematyczne:** Operatory na zbiorach (`∪`, `∩`, `\`), stany automatu.

### 3.7 Traversal (przechodzenie)
- **Definicja:** Systematyczne odwiedzanie elementów (np. in-order w drzewach).
- **Matematyka:** Permutacja elementów, grafowe DFS/BFS.

### 3.8 Iterator
- **Definicja:** Obiekt dostarczający metod `__iter__` / `__next__`.
- **Python:**
```python
class Rekordy:
    def __iter__(self):
        for rekord in self._dane:
            yield rekord
```

### 3.9 Porządek (ordering)
- **Totalny:** Każde dwa elementy porównywalne.
- **Częściowy:** Nie wszystkie elementy można porównać.
- **Zastosowanie:** Sortowanie, drzewa BST.
- **Skojarzenia matematyczne:** Relacje porządku, kraty (lattices).

### 3.10 Stabilność (stability)
- **Definicja:** Zachowanie pierwotnej kolejności elementów równych.
- **Przykład:** Merge sort stabilny, quicksort domyślnie nie.
- **Skojarzenia matematyczne:** Funkcje bijekcyjne na klasach równoważności.

### 3.11 Kolejność (ordering policy)
- **FIFO:** Kolejka.
- **LIFO:** Stos.
- **Priority:** Kolejka priorytetowa.
- **Skojarzenia matematyczne:** Kolejki M/M/1 (teoria kolejek), priorytetyzacja wag.

### 3.12 Dynamiczna vs statyczna alokacja
- **Statyczna:** Rozmiar znany w czasie kompilacji (`array[100]`).
- **Dynamiczna:** `list`, `vector`, `new`.
- **Matematyka:** Funkcja zasobów `S(n)` zależna od czasu.

### 3.13 Kontener vs kolekcja
- **Kontener:** Przechowuje elementy (np. lista, mapa).
- **Kolekcja:** Często wyższy poziom abstrakcji (np. `Collection` w Javie).
- **Skojarzenia matematyczne:** Typy funktorowe, zbiory i multizbiory.

---

## 4. Tabele porównawcze

### 4.1 Klucz vs dane satelitarne
| Aspekt             | Klucz (Key)                     | Dane satelitarne (Value)             |
|--------------------|---------------------------------|--------------------------------------|
| Funkcja            | Identyfikacja, porządkowanie    | Przenoszenie znaczenia domenowego   |
| Wymóg unikalności  | Tak (często)                    | Nie                                 |
| Operacje główne    | Porównanie, haszowanie          | Odczyt, aktualizacja                |
| Złożoność pamięci  | `Θ(|K|)`                        | `Θ(|V|)`                             |
| Skojarzenia        | Relacje porządku, funkcje `f`   | Wektory cech, rekordy               |

### 4.2 Złożoność operacji w wybranych strukturach
| Struktura         | Wstaw | Odczyt | Usunięcie | Uwagi                                     |
|-------------------|-------|--------|-----------|-------------------------------------------|
| Tablica (array)   | `O(n)` | `O(1)` | `O(n)`     | Przesunięcia przy środku                 |
| Tablica dynamiczna| `Amort. O(1)` | `O(1)`    | `O(n)`     | Przesunięcia przy środku                 |
| String            | `O(n+m)` | `O(1)` | `O(n)`     | Konkatenacja tworzy nowy string          |
| Lista jednokier.  | `O(1)` (na początku) | `O(n)` | `O(1)` (na początku) | Brak szybkiego dostępu losowego |
| Hash table        | `O(1)`* | `O(1)`* | `O(1)`* | `*` – średnio, `O(n)` w najgorszym        |
| Drzewo BST        | `O(log n)`* | `O(log n)`* | `O(log n)`* | `*` – przy balansie, inaczej `O(n)`   |
| Kolejka priorytet.| `O(log n)` | `O(1)` lub `O(log n)` | `O(log n)` | Implementacja kopca                     |

### 4.3 Właściwości struktur
| Struktura | Mutowalność | Zachowany porządek | Dostęp losowy | Typowe zastosowania            |
|-----------|-------------|--------------------|---------------|--------------------------------|
| `array`   | Tak         | Tak                | O(1)          | Sekwencje elementów, buforowanie |
| `tuple`   | Nie         | Tak (stała kolejność) | O(1)        | Rekordy stałe, klucze złożone   |
| `list`    | Tak         | Tak                | O(1) indeks   | Bufory, kolejki, zbiory dynamiczne |
| `str`     | Nie         | Tak                | O(1)          | Teksty, dane tekstowe           |
| `dict`    | Tak         | Iteracja od Py3.7  | Brak          | Mapy klucz → wartość            |
| `set`     | Tak         | Brak               | Brak          | Zbiory unikalnych elementów     |

---

## 5. Wizualizacje i diagramy (ASCII)

### 5.1 Struktura rekordu
```
┌─────────────── Rekord ───────────────┐
│ Klucz: ID = 42                       │
│ Dane satelitarne:                    │
│   • imię = "Ala"                     │
│   • saldo = 120.50                   │
└──────────────────────────────────────┘
```

### 5.2 Relacja klucz → rekord → dane
```
K (klucze) ──f──▶ Rekordy ──projekcje──▶ Pola / Dane satelitarne
```

### 5.3 Hierarchia abstrakcji
```
Abstrakcyjny Typ Danych (np. Stos)
         │
         ├─ Implementacja tablicowa
         └─ Implementacja listowa
```

---

## 6. Przykłady praktyczne w Pythonie

### 6.1 Rekord z kluczem i danymi satelitarnymi
```python
from dataclasses import dataclass

@dataclass
class RekordKlienta:
    id: int             # klucz
    email: str          # dane satelitarne
    saldo: float        # dane satelitarne
```

### 6.2 Prosty indeks (mapowanie ID → offset)
```python
import json

indeks = {}
with open("klienci.log", "r", encoding="utf8") as f:
    pos = 0
    for linia in f:
        rekord = json.loads(linia)
        indeks[rekord["id"]] = pos
        pos = f.tell()
```

### 6.3 Hash table z danymi satelitarnymi
```python
class ProstaTabelaHaszujaca:
    def __init__(self, rozmiar=8):
        self.rozmiar = rozmiar
        self.kubelki = [[] for _ in range(rozmiar)]

    def _indeks(self, klucz):
        return hash(klucz) % self.rozmiar

    def wstaw(self, klucz, wartosc):
        bucket = self.kubelki[self._indeks(klucz)]
        for i, (k, _) in enumerate(bucket):
            if k == klucz:
                bucket[i] = (klucz, wartosc)
                return
        bucket.append((klucz, wartosc))
```

### 6.4 Rekord złożony
```python
from collections import namedtuple

AdresID = namedtuple("AdresID", ["kod", "miasto"])
indeks_adresow = {
    AdresID("00-123", "Warszawa"): {"ulica": "Kwiatowa 1"}
}
```

### 6.5 Mutowalne vs niemutowalne struktury
```python
lista = [1, 2, 3]
lista.append(4)          # mutacja

krotka = (1, 2, 3)
nowa = krotka + (4,)     # tworzy nową krotkę
```

### 6.6 Tablica - operacje podstawowe
```python
# Tworzenie i dostęp
tablica = [10, 20, 30, 40, 50]
print(tablica[2])        # O(1) - odczyt: 30
tablica[2] = 35          # O(1) - modyfikacja

# Wstawienie (kosztowne)
tablica.insert(1, 15)    # O(n) - przesuwa elementy
# [10, 15, 20, 30, 40, 50]

# Usunięcie
tablica.pop(2)           # O(n) - przesuwa elementy
del tablica[0]           # O(n)
```

### 6.7 Zmienne - zakres i wiązanie
```python
# Zmienne globalne i lokalne
x = 42  # globalna

def funkcja():
    y = 10      # lokalna
    global x
    x = 100     # modyfikacja globalnej

# Wiązanie referencji
a = [1, 2, 3]
b = a           # b wskazuje na ten sam obiekt
b.append(4)     # modyfikuje również a
print(a)        # [1, 2, 3, 4]
```

### 6.8 String - operacje i właściwości
```python
# Tworzenie i dostęp
s = "Hello"
print(s[0])              # O(1) - 'H'
print(s[-1])             # O(1) - 'o'

# Konkatenacja (tworzy nowy string)
s2 = s + " World"        # O(n+m) - "Hello World"
s3 = "".join([s, " World"])  # efektywniejsze dla wielu stringów

# Wyszukiwanie
"lo" in s                # O(n) - True
s.find("ll")             # O(n) - 2 (indeks)

# Metody niemutujące
s.upper()                # "HELLO" (nowy string)
s.replace("l", "L")      # "HeLLo" (nowy string)
```

---

## 7. Notacja matematyczna w praktyce

### 7.1 Big O (górne ograniczenie)
- Definicja: `T(n) ∈ O(f(n))` ⇔ istnieją `c, n₀`, że `T(n) ≤ c·f(n)` dla `n ≥ n₀`.
- Przykład: Wstawianie w tablicy dynamicznej `O(n)` (przesunięcia) lub `Amort. O(1)`.

### 7.2 Theta (ściśle asymptotyczne)
- `T(n) ∈ Θ(f(n))` ⇔ `T(n)` ograniczone z góry i dołu przez `f(n)`.
- Przykład: Merge sort `Θ(n log n)`.

### 7.3 Omega (dolne ograniczenie)
- `T(n) ∈ Ω(f(n))` ⇒ `T(n)` nie może być mniejsze niż `f(n)` poza stałym czynnikiem.
- Przykład: Każdy algorytm porównawczy sortowania ma `Ω(n log n)`.

### 7.4 Złożoność amortyzowana
- Średni koszt pojedynczej operacji w sekwencji.
- Przykład: powiększenie tablicy dynamicznej co potęgę dwójki.
- Dowód galonowy: suma kosztów rozszerzeń `≈ 2n`, więc średnio `O(1)`.

### 7.5 Przykładowe obliczenia
- **Przechodzenie listy:** `T(n) = n * c`, więc `O(n)`.
- **Wyszukiwanie binarne:** `T(n) = T(n/2) + c → O(log n)`.

---

## 8. Słownik terminów (alfabetycznie)
- **ADT:** Abstrakcyjny typ danych; opis operacji i kontraktów bez implementacji.
- **Array (tablica):** Kontyguacyjna sekwencja elementów tego samego typu, indeksowana liczbami całkowitymi.
- **Composite record (rekord złożony):** Rekord z kluczem wielopolowym.
- **CRUD:** Create, Read, Update, Delete – cztery podstawowe operacje na danych.
- **Field (pole):** Najmniejszy składnik rekordu o określonym typie.
- **Foreign key:** Pole wskazujące na klucz główny innej tabeli.
- **Hash:** Funkcja przypisująca kluczom liczby z ograniczonego zakresu.
- **Hash table:** Struktura mapująca klucze na wartości poprzez funkcję haszującą.
- **Indeks:** Struktura przyspieszająca wyszukiwanie rekordów.
- **Iterator:** Obiekt dostarczający kolejne elementy kolekcji.
- **Key (klucz):** Identyfikator rekordu, często unikalny.
- **Mutowalność:** Zdolność struktury do zmiany stanu w miejscu.
- **Order (porządek):** Reguła określająca relacje `<`, `>` między elementami.
- **Payload:** Dane towarzyszące kluczowi, nienależące do identyfikatora.
- **Primary key:** Minimalny zbiór pól zapewniający unikalność rekordu.
- **Queue (kolejka FIFO):** Struktura o polityce „pierwszy na wejściu – pierwszy na wyjściu".
- **Record (rekord):** Zbiór powiązanych pól reprezentujących encję.
- **Satellite data:** Dane przechowywane z kluczem, ale niewykorzystywane do wyszukiwania.
- **Stack (stos LIFO):** Struktura o polityce „ostatni na wejściu – pierwszy na wyjściu".
- **Stability:** Własność algorytmu zachowująca kolejność elementów równych.
- **String (ciąg znaków):** Sekwencja znaków z alfabetu, reprezentująca tekst.
- **Traversal:** Strategia odwiedzania elementów struktury.
- **Tuple (krotka):** Niezmienna sekwencja wartości.
- **Variable (zmienna):** Nazwane miejsce w pamięci przechowujące wartość, które może być modyfikowane.
- **Złożoność czasowa:** Funkcja opisująca koszt operacji względem rozmiaru danych.
- **Złożoność przestrzenna:** Funkcja opisująca zużycie pamięci względem rozmiaru danych.

---

## Dalsza lektura
- Thomas H. Cormen i in., *Introduction to Algorithms*.
- Donald Knuth, *The Art of Computer Programming*, Vol. 1–3.
- Dokumentacja Python: `dataclasses`, `collections`, `typing`.


