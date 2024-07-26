useMemo to funkcja zapamiętująca stan komponentu, służąca do zoptymalizowania wydajności React poprzez memoizację wyników kosztownych obliczeń.

Główne cechy i zastosowania useMemo:

1. Optymalizacja wydajności: Zapobiega niepotrzebnym przeliczeniom wartości przy każdym renderowaniu komponentu.

2. Memoizacja: Przechowuje wynik funkcji i zwraca go z pamięci podręcznej, gdy zależności nie uległy zmianie.

3. Składnia: 
   ```jsx
   const memoizedValue = useMemo(() => computeExpensiveValue(a, b), [a, b]);
   ```

4. Zależności: Drugi argument to tablica zależności, określająca, kiedy wartość powinna być przeliczona.

5. Zastosowania:
   - Obliczenia kosztowne czasowo
   - Optymalizacja renderowania komponentów potomnych
   - Unikanie niepotrzebnych re-renderowań