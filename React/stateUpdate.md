Funkcyjna forma aktualizacji stanu w React to sposób aktualizacji stanu komponentu, który wykorzystuje funkcję zamiast bezpośredniej wartości. Jest to szczególnie przydatne, gdy nowy stan zależy od poprzedniego stanu.

Przykład:
```
import React, { useState } from 'react';

function RozneFormy() {
  const [liczba, setLiczba] = useState(0);
  const [tekst, setTekst] = useState('');
  const [obiekt, setObiekt] = useState({ klucz: 'wartość' });
  const [tablica, setTablica] = useState([1, 2, 3]);

  // 1. Bezpośrednia aktualizacja
  const aktualizujBezpośrednio = () => {
    setLiczba(5);
  };

  // 2. Funkcyjna aktualizacja
  const aktualizujFunkcyjnie = () => {
    setLiczba(prev => prev + 1);
  };

  // 3. Aktualizacja tekstu
  const aktualizujTekst = (nowyTekst) => {
    setTekst(nowyTekst);
  };

  // 4. Aktualizacja obiektu
  const aktualizujObiekt = () => {
    setObiekt(prev => ({ ...prev, nowyKlucz: 'nowaWartość' }));
  };

  // 5. Aktualizacja tablicy - dodawanie elementu
  const dodajDoTablicy = () => {
    setTablica(prev => [...prev, 4]);
  };

  // 6. Aktualizacja tablicy - usuwanie elementu
  const usunZTablicy = () => {
    setTablica(prev => prev.filter(item => item !== 2));
  };

  // 7. Złożona aktualizacja zależna od poprzedniego stanu
  const zlozonaAktualizacja = () => {
    setLiczba(prev => {
      if (prev < 10) {
        return prev + 1;
      } else {
        return 0;
      }
    });
  };

  return (
    <div>
      <p>Liczba: {liczba}</p>
      <p>Tekst: {tekst}</p>
      <p>Obiekt: {JSON.stringify(obiekt)}</p>
      <p>Tablica: {tablica.join(', ')}</p>
      {/* Przyciski do testowania różnych form aktualizacji */}
    </div>
  );
}
```
