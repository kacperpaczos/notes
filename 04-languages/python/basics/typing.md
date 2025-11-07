# 🐍 Typowanie w Pythonie — Notatki

---

## 🔹 1. Python to język **dynamicznie typowany**

* Typ zmiennej jest ustalany **automatycznie w czasie działania programu**.

* Nie trzeba deklarować typów przy tworzeniu zmiennej:

  ```python
  x = 5        # int
  x = "hello"  # teraz str – i to jest OK
  ```

---

## 🔹 2. Typy podstawowe

| Typ     | Przykład     | Opis                      |
| ------- | ------------ | ------------------------- |
| `int`   | `x = 10`     | liczby całkowite          |
| `float` | `x = 3.14`   | liczby zmiennoprzecinkowe |
| `str`   | `x = "text"` | łańcuch znaków            |
| `bool`  | `x = True`   | wartość logiczna          |
| `list`  | `[1, 2, 3]`  | lista (mutowalna)         |
| `tuple` | `(1, 2, 3)`  | krotka (niemutowalna)     |
| `dict`  | `{"a": 1}`   | słownik                   |
| `set`   | `{1, 2, 3}`  | zbiór                     |

---

## 🔹 3. Adnotacje typów (Type Hints)

Od Pythona 3.5 można używać **adnotacji typów**, by ułatwić czytanie kodu i analizę przez narzędzia typu `mypy`.

```python
def greet(name: str) -> str:
    return f"Hello, {name}"

age: int = 25
```

➡️ Nie wpływa to na działanie programu — to **tylko informacja** dla człowieka i analizatorów statycznych.

---

## 🔹 4. Typowanie kolekcji

Nowoczesny zapis (Python 3.9+):

```python
numbers: list[int] = [1, 2, 3]
user: dict[str, int] = {"age": 30}
```

Starszy zapis (dla kompatybilności z 3.7–3.8):

```python
from typing import List, Dict
numbers: List[int] = [1, 2, 3]
user: Dict[str, int] = {"age": 30}
```

---

## 🔹 5. Typy złożone i generyczne

```python
from typing import Optional, Union, Any, Tuple

# Optional = może być None
name: Optional[str] = None  # == Union[str, None]

# Union = jedno z kilku typów
value: Union[int, float] = 3.14

# Tuple = krotka o znanym składzie
point: Tuple[int, int] = (3, 7)

# Any = dowolny typ (wyłącza sprawdzanie)
data: Any = "coś"
```

---

## 🔹 6. Typowanie funkcji

```python
def add(a: int, b: int) -> int:
    return a + b
```

Dla funkcji, które **nie zwracają wartości**:

```python
def log(msg: str) -> None:
    print(msg)
```

---

## 🔹 7. Typowanie klas i metod

```python
class User:
    def __init__(self, name: str, age: int):
        self.name = name
        self.age = age

    def greet(self) -> str:
        return f"Hi, I'm {self.name}"
```

---

## 🔹 8. Typy z `typing`

| Typ                   | Opis                                         |
| --------------------- | -------------------------------------------- |
| `Any`                 | dowolny typ                                  |
| `Union[A, B]`         | jeden z dwóch                                |
| `Optional[A]`         | `A` lub `None`                               |
| `Callable[[A, B], R]` | funkcja o argumentach i typie zwrotu         |
| `TypeVar`             | zmienna typowa (np. dla generyków)           |
| `Literal`             | dosłowna wartość                             |
| `cast()`              | zmienia typ *dla analizatora*, nie w runtime |

---

## 🔹 9. Typowanie funkcji anonimowych i callbacków

```python
from typing import Callable

# Funkcja przyjmująca callback
def process(func: Callable[[int, int], int]) -> int:
    return func(2, 3)
```

---

## 🔹 10. `cast()` — rzutowanie typów logicznych (statyczne)

```python
from typing import cast

value: object = "text"
s = cast(str, value)  # dla mypy — traktuj to jako string
print(s.upper())
```

---

## 🔹 11. Typowanie asynchroniczne

```python
async def fetch_data(url: str) -> dict[str, Any]:
    ...
```

---

## 🔹 12. Sprawdzanie typów (narzędzia)

| Narzędzie   | Opis                                     |
| ----------- | ---------------------------------------- |
| **mypy**    | statyczny analizator typów               |
| **pyright** | szybki checker od Microsoftu             |
| **pylance** | rozszerzenie VS Code (oparte na pyright) |
| **ruff**    | linter + checker typów                   |

Uruchom:

```bash
mypy plik.py
```

---

## 🔹 13. Typowanie w `dataclasses` i `pydantic`

### `dataclass`

```python
from dataclasses import dataclass

@dataclass
class User:
    name: str
    age: int
```

### `pydantic`

```python
from pydantic import BaseModel

class User(BaseModel):
    name: str
    age: int
```

Pydantic **waliduje typy w runtime**, w przeciwieństwie do zwykłych adnotacji.

---

## 🔹 14. Typowanie nowoczesne (Python 3.12+)

* Można używać operatora `|` zamiast `Union`:

  ```python
  def parse(data: int | str) -> str:
      ...
  ```

* `Self` dla metod klas:

  ```python
  from typing import Self

  class Builder:
      def set_name(self, name: str) -> Self:
          self.name = name
          return self
  ```

---

## 🔹 15. Dobra praktyka

✅ Używaj typów dla publicznego API i kluczowych funkcji.

✅ Unikaj `Any`, jeśli możesz określić konkretny typ.

✅ Sprawdzaj kod `mypy` lub `ruff check --select=ANN`.

✅ Typowanie pomaga IDE w podpowiedziach i unikaniu błędów.

---

## Powiązane tematy

- [typing-module](../advanced/typing-module.md) - zaawansowane użycie modułu typing

