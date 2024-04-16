Entropia jest miarą niepewności lub nieprzewidywalności informacji. W teorii informacji, entropia jest używana do określenia ilości informacji, która jest zawarta w wiadomości. 

**Im większa entropia, tym więcej informacji można przekazać.**

$$H(X) := -\sum_{x\in \mathcal{X}} p(x) \log p(x),$$

Entropia \(H(X)\) zbioru danych \(X\) jest równa sumie iloczynów prawdopodobieństwa wystąpienia elementu \(x\) w zbiorze \(X\), oznaczonego jako \(p(x)\), i logarytmu tego prawdopodobieństwa, wziętych z przeciwnym znakiem.

Przeciwny znak w formule na entropię jest używany, ponieważ prawdopodobieństwa \(p(x)\) są zawsze dodatnie, a logarytm prawdopodobieństwa jest ujemny, gdy \(p(x) < 1\), co ma miejsce w większości przypadków. Użycie przeciwnego znaku sprawia, że wynik jest dodatni, co jest bardziej intuicyjne przy interpretacji entropii jako miary niepewności - większa wartość oznacza większą niepewność (więcej informacji jest potrzebnych do oposania stanu, wiadomość jest mniej przewidywalna).

Sumowanie odbywa się po wszystkich możliwych wartościach \(x\) w zbiorze \(X\).

Przykłady trzech zbiorów danych o różnych entropiach:

1. Zbiór danych o entropii 0 (wszystkie elementy są takie same, co oznacza brak niepewności):
   - Zbiór: {A, A, A, A}
   - Entropia: 0, ponieważ nie ma niepewności co do wyniku.

2. Zbiór danych o entropii 1 (elementy są równomiernie rozłożone, co oznacza maksymalną niepewność):
   - Zbiór: {A, B}
   - Entropia: 1, ponieważ prawdopodobieństwo każdego zdarzenia (A lub B) wynosi 1/2, co daje maksymalną niepewność.

3. Zbiór danych o entropii 0,5 (elementy mają różne prawdopodobieństwa wystąpienia, ale nie ma pełnej niepewności):
   - Zbiór: {A, A, A, B}
   - Entropia: 0,5, ponieważ większość elementów to A, ale jest też B, co wprowadza pewien poziom niepewności.


