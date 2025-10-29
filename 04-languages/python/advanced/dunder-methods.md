# Dunder Methods (Magic Methods) w Pythonie

## Cel

Metody specjalne (dunder methods) to metody, których nazwa zaczyna się i kończy podwójnym podkreśleniem (`__method__`). Pozwalają na definiowanie zachowania obiektów w różnych kontekstach, przeładowywanie operatorów i dostosowanie interakcji z obiektami.

## Kluczowe Kategorie

### 1. Inicjalizacja i reprezentacja

#### `__init__` - Konstruktor
```python
class Point:
    def __init__(self, x: int, y: int) -> None:
        self.x = x
        self.y = y
```

#### `__new__` - Tworzenie instancji (factory)
```python
class Singleton:
    _instance = None
    
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance
```

#### `__repr__` - Oficjalna reprezentacja (dla devów)
```python
def __repr__(self) -> str:
    return f"Point(x={self.x}, y={self.y})"  # Powinno być możliwe do eval()
```

#### `__str__` - Czytelna reprezentacja (dla użytkowników)
```python
def __str__(self) -> str:
    return f"Point at ({self.x}, {self.y})"
```

### 2. Operatory arytmetyczne

#### Podstawowe operacje
```python
class Vector:
    def __add__(self, other):       # +
    def __sub__(self, other):       # -
    def __mul__(self, other):       # *
    def __truediv__(self, other):   # /
    def __floordiv__(self, other):  # //
    def __mod__(self, other):       # %
    def __pow__(self, other):       # **
```

#### Operatory porównań
```python
def __eq__(self, other):    # ==
def __ne__(self, other):    # !=
def __lt__(self, other):    # <
def __le__(self, other):    # <=
def __gt__(self, other):    # >
def __ge__(self, other):    # >=
```

### 3. Dostęp do atrybutów i indeksów

#### Atrybuty
```python
def __getattr__(self, name):      # Gdy atrybut nie istnieje
def __setattr__(self, name, val): # Przy przypisaniu
def __delattr__(self, name):      # Przy usunięciu
def __getattribute__(self, name): # Wykonywane przed __getattr__
```

#### Indeksowanie (jak listy/słowniki)
```python
def __getitem__(self, key):    # obj[key]
def __setitem__(self, key, val): # obj[key] = val
def __delitem__(self, key):   # del obj[key]
```

### 4. Context Managers

#### `__enter__` i `__exit__`
```python
class FileManager:
    def __enter__(self):
        self.file = open('file.txt')
        return self.file
    
    def __exit__(self, exc_type, exc_val, traceback):
        self.file.close()
        return False  # Nie tłum wyjątków
```

### 5. Iterator Protocol

#### `__iter__` i `__next__`
```python
class Counter:
    def __init__(self, max_val: int):
        self.max_val = max_val
        self.current = 0
    
    def __iter__(self):
        return self
    
    def __next__(self):
        if self.current >= self.max_val:
            raise StopIteration
        self.current += 1
        return self.current - 1
```

### 6. Callable Objects

#### `__call__` - obiekt jak funkcja
```python
class Multiplier:
    def __init__(self, factor: int):
        self.factor = factor
    
    def __call__(self, value: int) -> int:
        return value * self.factor

mult = Multiplier(5)
result = mult(10)  # 50
```

### 7. Hash i Równość

#### `__hash__` - dla hashable objects
```python
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __eq__(self, other):
        return (self.x, self.y) == (other.x, other.y)
    
    def __hash__(self):
        return hash((self.x, self.y))
```

**Ważne**: Jeśli `__eq__` jest zdefiniowane, `__hash__` musi być zdefiniowane lub ustawione na `None`.

### 8. Descriptors

#### `__get__`, `__set__`, `__delete__`
```python
class Descriptor:
    def __get__(self, instance, owner):
        return instance._value
    
    def __set__(self, instance, value):
        if value < 0:
            raise ValueError("Cannot be negative")
        instance._value = value
```

## Najważniejsze Wnioski

### 1. Operator Overloading
- Python pozwala na przeładowanie prawie wszystkich operatorów
- Umożliwia tworzenie czytelnego, "naturalnego" API

### 2. Protocol-based Programming
- Nie trzeba dziedziczyć - wystarczy zaimplementować odpowiednie metody
- Duck typing w działaniu

### 3. Immutability Pattern
```python
class ImmutablePoint:
    __slots__ = ('_x', '_y')
    
    def __init__(self, x, y):
        object.__setattr__(self, '_x', x)
        object.__setattr__(self, '_y', y)
    
    def __setattr__(self, name, value):
        raise AttributeError(f"{self.__class__.__name__} is immutable")
```

### 4. Right-hand Operations
Gdy lewy operand nie obsługuje operacji, Python próbuje prawy:
```python
def __radd__(self, other):  # other + self gdy other.__add__ nie działa
def __rsub__(self, other):
# itd. dla wszystkich operatorów
```

## Kompletna Lista Najważniejszych

### Lifecycle
- `__new__`, `__init__`, `__del__`

### Reprezentacja
- `__repr__`, `__str__`, `__format__`, `__bytes__`

### Operatory
- `__add__`, `__sub__`, `__mul__`, `__truediv__`, `__floordiv__`, `__mod__`, `__pow__`
- `__eq__`, `__ne__`, `__lt__`, `__le__`, `__gt__`, `__ge__`
- `__and__`, `__or__`, `__xor__`, `__invert__`, `__lshift__`, `__rshift__`

### Dostęp
- `__getattr__`, `__setattr__`, `__delattr__`, `__getattribute__`
- `__getitem__`, `__setitem__`, `__delitem__`
- `__get__`, `__set__`, `__delete__` (descriptors)

### Iterator
- `__iter__`, `__next__`
- `__reversed__`

### Context Management
- `__enter__`, `__exit__`

### Callable
- `__call__`

### Type Conversion
- `__int__`, `__float__`, `__bool__`, `__complex__`
- `__index__` (dla slice operations)

### Inne
- `__len__` (dla len())
- `__contains__` (dla `in`)
- `__hash__` (dla hash())

## Najczęstsze Błędy

### ❌ Błąd: Nieprawidłowy `__hash__` z mutable objects
```python
class BadPoint:
    def __init__(self, x, y):
        self.x = x  # mutable!
        self.y = y
    
    def __hash__(self):
        return hash((self.x, self.y))  # Błąd! Mutable nie może być hashable
```

### ✅ Poprawne
```python
class Point:
    __slots__ = ('x', 'y')
    
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __eq__(self, other):
        return (self.x, self.y) == (other.x, other.y)
    
    def __hash__(self):
        return hash((self.x, self.y))
```

## Best Practices

1. **Konsekwencja**: `__eq__` wymaga `__hash__` (lub `__hash__ = None` dla mutable)
2. **`__repr__` powinno być eval-able**: Idealnie możliwe do skopiowania i wykonania
3. **Unikaj `__getattribute__`**: Używaj tylko gdy naprawdę potrzebujesz pełnej kontroli
4. **Type hints**: Dodawaj type hints także do magic methods

## Powiązane tematy

- [Context Managers](./context-managers.md) - `__enter__` i `__exit__`
- [attrs](../libraries/attrs.md) - automatyczne generowanie dunder methods
- [typing module](./typing-module.md) - type hints dla magic methods

## Źródła / dalsza lektura

- [Python Data Model](https://docs.python.org/3/reference/datamodel.html)
- [Every Dunder Method](https://www.pythonmorsels.com/every-dunder-method/)
- [Python Magic Methods](https://rszalski.github.io/magicmethods/)

