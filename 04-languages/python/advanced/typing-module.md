# typing Module - Type Hints w Pythonie

## Cel

Moduł **typing** ze standardowej biblioteki Pythona dostarcza mechanizmy do adnotacji typów (type hints), które poprawiają czytelność kodu, umożliwiają statyczną analizę i wykrywanie błędów przed uruchomieniem programu.

## Kluczowe Cechy

### 1. Type Hints są opcjonalne
- **Nie wpływają na wykonanie** - Python ignoruje adnotacje typów w runtime
- **Tylko dla deweloperów** - służą do komunikacji intent i narzędziom (mypy, pyright)

### 2. Podstawowe typy
```python
from typing import List, Dict, Tuple, Optional, Union

def process_numbers(numbers: List[int]) -> int:
    return sum(numbers)

def get_user(id: int) -> Optional[Dict[str, str]]:
    if id > 0:
        return {"name": "John", "email": "john@example.com"}
    return None
```

### 3. Złożone typy (Generic Types)

#### List
```python
from typing import List

items: List[str] = ["a", "b", "c"]
matrix: List[List[int]] = [[1, 2], [3, 4]]
```

#### Dict
```python
from typing import Dict

user_data: Dict[str, Union[str, int]] = {
    "name": "John",
    "age": 30
}
```

#### Tuple
```python
from typing import Tuple

point: Tuple[int, int] = (10, 20)
rgb: Tuple[int, int, int] = (255, 128, 0)
variable_length: Tuple[int, ...] = (1, 2, 3, 4)
```

### 4. Union i Optional

#### Union (Python < 3.10)
```python
from typing import Union

def process(value: Union[str, int]) -> str:
    return str(value)
```

#### Optional (skrót dla Union[T, None])
```python
from typing import Optional

def find_item(key: str) -> Optional[str]:
    # Może zwrócić str lub None
    if key in cache:
        return cache[key]
    return None
```

#### Python 3.10+ - `|` operator
```python
def process(value: str | int) -> str:  # Zamiast Union[str, int]
    return str(value)

def find_item(key: str) -> str | None:  # Zamiast Optional[str]
    ...
```

### 5. Type Aliases
```python
from typing import Dict, List

UserId = int
UserData = Dict[str, Union[str, int, List[str]]]

def get_user(id: UserId) -> Optional[UserData]:
    ...
```

### 6. Callable
```python
from typing import Callable

def apply(func: Callable[[int, int], int], a: int, b: int) -> int:
    return func(a, b)

# Skrót dla funkcji z konkretnymi typami
Adder = Callable[[int, int], int]
```

### 7. Generics i Type Variables
```python
from typing import TypeVar, Generic, List

T = TypeVar('T')

class Stack(Generic[T]):
    def __init__(self) -> None:
        self.items: List[T] = []
    
    def push(self, item: T) -> None:
        self.items.append(item)
    
    def pop(self) -> T:
        return self.items.pop()
```

### 8. Literal Types
```python
from typing import Literal

Mode = Literal["read", "write", "append"]

def open_file(mode: Mode) -> None:
    ...
```

### 9. Protocol (Structural Typing)
```python
from typing import Protocol

class Drawable(Protocol):
    def draw(self) -> None: ...

def render(obj: Drawable) -> None:
    obj.draw()  # Każdy obiekt z metodą draw() jest akceptowany
```

## Unikalne Zalety Type Hints

### 1. Statyczna analiza kodu
- **mypy**: Najpopularniejszy type checker
- **pyright**: Szybki type checker (używany w Pylance)
- **pyre**: Facebook's type checker

### 2. Lepsze IDE support
- Autouzupełnianie
- Wykrywanie błędów przed uruchomieniem
- Refactoring tools

### 3. Dokumentacja w kodzie
```python
def calculate_total(items: List[Item], discount: float) -> float:
    # Już widać z sygnatury:
    # - Przyjmuje listę Item
    # - discount to float (nie int!)
    # - Zwraca float
    ...
```

## Wnioski i Najważniejsze Zasady

1. **Stopniowe wprowadzanie**: Można dodawać type hints stopniowo, plik po pliku
2. **Consistency**: Jeśli zaczynasz używać type hints, rób to konsekwentnie
3. **Type erasure**: Wszystkie adnotacje są usuwane w runtime (nie wpływają na wydajność)
4. **Duck typing nadal działa**: Type hints nie zmieniają dynamicznej natury Pythona

## Najczęstsze Błędy

### ❌ Błąd: Mieszanie starych i nowych składni
```python
# Python 3.9+
from typing import Dict, List

def process(data: dict) -> list:  # Używaj Dict, List zamiast dict, list dla generics
    ...
```

### ✅ Poprawne
```python
from typing import Dict, List

def process(data: Dict[str, int]) -> List[str]:
    ...
```

## Best Practices

### 1. Używaj `Optional` dla wartości mogących być None
```python
from typing import Optional

def get_config(key: str) -> Optional[str]:
    ...
```

### 2. Dla prostych typów używaj wbudowanych (Python 3.9+)
```python
# Python 3.9+
def process(items: list[str]) -> dict[str, int]:
    ...

# Python < 3.9
from typing import List, Dict
def process(items: List[str]) -> Dict[str, int]:
    ...
```

### 3. Type hints dla zmiennych klasowych
```python
from typing import ClassVar, Final

class Config:
    api_url: str
    timeout: Final[int] = 30  # Nie można zmienić
    instance_count: ClassVar[int] = 0  # Zmienna klasy, nie instancji
```

## Kiedy używać

✅ **Zawsze używaj gdy:**
- Pracujesz w większym zespole
- Projekt będzie utrzymywany długoterminowo
- Importujesz funkcje z innych modułów (API contract)

❌ **Można pominąć gdy:**
- Proste skrypty jednorazowe
- Nie masz dostępu do type checkerów
- Prototypy i szybkie eksperymenty

## Powiązane tematy

- [attrs](../libraries/attrs.md) - używa type hints
- [Context Managers](./context-managers.md) - type hints dla `__enter__`
- [Engineering Guidelines](../../../13-software-engineering/engineering-guidelines.md) - typowanie w praktyce

## Źródła / dalsza lektura

- [Oficjalna dokumentacja typing](https://docs.python.org/3/library/typing.html)
- [mypy documentation](https://mypy.readthedocs.io/)
- [Python Type Checking Guide](https://realpython.com/python-type-checking/)
- [typing_extensions](https://github.com/python/typing_extensions) - backported features

