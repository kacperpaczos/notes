useCallback służy do przechowywania referencji funkcji, jej kopia nie tworzy się między ponownymi renderowaniami komponentu.

// Dodatkowe wyjaśnienie:

// useCallback zwraca memoizowaną wersję funkcji callback, która zmienia się tylko wtedy,
// gdy jedna z zależności się zmieni. Jest to użyteczne przy przekazywaniu callbacks do
// zoptymalizowanych komponentów potomnych, które polegają na równości referencji,
// aby zapobiec niepotrzebnym renderowaniom.

Używaj gdy:
1. Przekazujesz funkcje do zoptymalizowanych komponentów potomnych.

2. Chcesz uniknąć niepotrzebnych renderowań spowodowanych zmianą referencji funkcji.

3. Funkcja jest używana jako zależność w innych hookach, takich jak useEffect.

-----

W tym przypadku użycie useCallback jest uzasadnione, ponieważ:
1. Przekazujemy funkcję do zoptymalizowanego komponentu (React.memo).

2. Chcemy uniknąć niepotrzebnych renderowań ExpensiveComponent.
```
import React, { useState, useCallback } from 'react';

const ExpensiveComponent = React.memo(({ onClick }) => {
  console.log("ExpensiveComponent renderuje się");
  return <button onClick={onClick}>Kliknij mnie</button>;
});

function App() {
  const [count, setCount] = useState(0);

  // Użycie useCallback jest tutaj uzasadnione
  const handleClick = useCallback(() => {
    setCount(prevCount => prevCount + 1);
  }, []); // Pusta tablica zależności, ponieważ funkcja nie zależy od żadnych zmiennych

  return (
    <div>
      <p>Licznik: {count}</p>
      <ExpensiveComponent onClick={handleClick} />
    </div>
  );
}
```
-----
```
import React, { useState, useCallback } from 'react';

function App() {
  const [count, setCount] = useState(0);

  // Użycie useCallback tutaj jest zbędne
  const handleClick = useCallback(() => {
    setCount(prevCount => prevCount + 1);
  }, []);

  return (
    <div>
      <p>Licznik: {count}</p>
      <button onClick={handleClick}>Zwiększ</button>
    </div>
  );
}
```
W tym przypadku użycie useCallback jest zbędne, ponieważ:

1. Nie przekazujemy funkcji do żadnego zoptymalizowanego komponentu potomnego.

2. Prosta funkcja inline nie wpłynie znacząco na wydajność.