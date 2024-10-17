React zarządza VirtualDOM wewnętrznie, aby optymalizować proces renderowania komponentów. React porównuje VirtualDOM z rzeczywistym stanem komponentów i na tej podstawie decyduje, które elementy DOM należy zmienić.
ReactDOM jest odpowiedzialny za przekształcenie tych zoptymalizowanych operacji na rzeczywistym DOM przeglądarki. To ReactDOM faktycznie manipuluje DOM na podstawie wyników z VirtualDOM.
Podsumowując:

VirtualDOM to mechanizm optymalizacji renderowania w React <==> React.
ReactDOM wykonuje rzeczywiste zmiany w DOM przeglądarki <==> ReactDOM.

Algorytm VirtualDOM w **React** działa według kilku kroków, które pozwalają efektywnie zarządzać aktualizacjami interfejsu. Cały proces jest zoptymalizowany pod kątem minimalizacji operacji na rzeczywistym DOM, co przekłada się na lepszą wydajność. Oto, jak działa:

### 1. **Tworzenie VirtualDOM**
- Gdy wywołujesz funkcję `render()` dla komponentu Reacta, React tworzy drzewo VirtualDOM. To uproszczona, wirtualna reprezentacja struktury rzeczywistego DOM, która przechowuje aktualny stan UI.

### 2. **Zmiana stanu (state) lub właściwości (props)**
- Gdy zmienia się stan (state) lub właściwości (props) komponentu, React nie od razu aktualizuje rzeczywisty DOM. Zamiast tego tworzy nową wersję VirtualDOM, która odzwierciedla te zmiany.

### 3. **Diffing – Porównywanie VirtualDOM (algorytm różnicowania)**
- React porównuje nową wersję VirtualDOM z poprzednią wersją. Ten proces nazywany jest **diffingiem**.
- **Diffing** polega na przechodzeniu drzewa VirtualDOM i porównywaniu poszczególnych węzłów (komponentów). React sprawdza, które węzły (elementy) są inne, które zostały zmienione, dodane lub usunięte.

### 4. **Minimalne zmiany (reconciliacja)**
- Po wykryciu różnic, React optymalizuje zmiany i generuje listę operacji, które trzeba wykonać na rzeczywistym DOM, aby zaktualizować interfejs.
- Dzięki temu React modyfikuje tylko te elementy, które uległy zmianie, zamiast przerysowywać całą stronę.

### 5. **Batching (grupowanie zmian)**
- React często grupuje operacje aktualizacji, aby uniknąć nadmiernych manipulacji DOM. Zmiany są stosowane w jednym cyklu renderowania, co dodatkowo poprawia wydajność.

### Kluczowe optymalizacje algorytmu:
- **Stały klucz dla dzieci komponentów:** React wykorzystuje klucze (`key`), aby szybko porównać elementy w liście. Klucze pomagają mu rozpoznać, które elementy są takie same, a które zmieniły pozycję lub zostały usunięte/dodane.
- **Uniknięcie pełnego porównania:** Zamiast porównywać całe drzewa DOM, algorytm VirtualDOM wykonuje optymalizacje, takie jak uproszczone porównania, co przyspiesza proces różnicowania.

