Służy do przekazania danych w dół, bez potrzeby przekazania ich przez każdy komponent po dordze poprzez propsy.

```
import { createContext, use } from 'react';

const ThemeContext = createContext('light'); // Domyślny motyw 'light'

function ThemeProvider({ children }) {
  const [theme, setTheme] = useState('light'); // Zarządzanie stanem motywu

  return (
    <ThemeContext value={{ theme, setTheme }}> 
      {children}
    </ThemeContext>
  );
}

function Button() {
  const { theme, setTheme } = use(ThemeContext); // Użycie hooka 'use'

  return (
    <button onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>
      Przełącz motyw ({theme})
    </button>
  );
}

function App() {
  return (
    <ThemeProvider>
      <Button />
    </ThemeProvider>
  );
}

```

Context API działa na zasadzie "wielokrotnego renderowania", więc jeśli dane kontekstowe się zmieniają, każdy komponent, który z nich korzysta, będzie renderowany ponownie. W dużych aplikacjach może to prowadzić do problemów z wydajnością.
