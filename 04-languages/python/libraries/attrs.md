# attrs - Python Library

## Cel

Biblioteka **attrs** służy do łatwego definiowania klas z automatycznie generowanymi metodami i deklaratywnego opisywania typów i walidacji pól w obiektach, eliminując boilerplate code związanego z definiowaniem klas.

## Kluczowe Cechy

### 1. Automatyczne generowanie metod specjalnych
- `__init__()` - konstruktor z automatyczną inicjalizacją pól
- `__repr__()` - czytelna reprezentacja obiektu
- `__eq__()` - porównywanie równości (tylko gdy `eq=True`)
- `__hash__()` - generowanie hash (gdy `frozen=True`)
- `__cmp__()` - porównywanie (gdy `order=True`)

### 2. Deklaratywne definiowanie klas
```python
import attr

@attr.s
class Point:
    x: int = attr.ib()
    y: int = attr.ib()
    z: int = attr.ib(default=0)
```

### 3. Walidacja pól
- Automatyczna walidacja przy inicjalizacji
- Konwertery typów
- Custom validatory

### 4. Immutable objects (frozen)
```python
@attr.s(frozen=True)
class Configuration:
    host: str = attr.ib()
    port: int = attr.ib()
```

### 5. Slots (optymalizacja pamięci)
```python
@attr.s(slots=True)
class LightweightClass:
    data: str = attr.ib()
```

## Unikalne Zalety attrs vs standardowe klasy

### Standardowa klasa Python:
```python
class Point:
    def __init__(self, x, y, z=0):
        self.x = x
        self.y = y
        self.z = z
    
    def __repr__(self):
        return f"Point(x={self.x}, y={self.y}, z={self.z})"
    
    def __eq__(self, other):
        if not isinstance(other, Point):
            return NotImplemented
        return (self.x, self.y, self.z) == (other.x, other.y, other.z)
```

### Z attrs:
```python
@attr.s
class Point:
    x: int = attr.ib()
    y: int = attr.ib()
    z: int = attr.ib(default=0)
```

**Oszczędność:** ~15 linii → 4 linie kodu

## Najważniejsze Parametry

### Decorator `@attr.s()`
- `frozen=True` - tworzy immutable objects (hashable)
- `slots=True` - używa `__slots__` dla oszczędności pamięci
- `auto_attribs=True` - automatycznie używa adnotacji typów jako definicji pól
- `repr=True/false` - włącza/wyłącza `__repr__`
- `eq=True/false` - włącza/wyłącza `__eq__`
- `order=True/false` - generuje metody porównań (`<`, `>`, `<=`, `>=`)

### `attr.ib()`
- `default` - wartość domyślna
- `validator` - funkcja walidująca
- `converter` - funkcja konwertująca wartość
- `kw_only=True` - pole dostępne tylko jako keyword argument

## Przykłady Zaawansowane

### Walidacja z validatorami
```python
@attr.s
class User:
    email: str = attr.ib(validator=attr.validators.instance_of(str))
    age: int = attr.ib(validator=attr.validators.instance_of(int))
    
    @age.validator
    def check_age(self, attribute, value):
        if value < 0:
            raise ValueError("Age cannot be negative")
```

### Konwertery
```python
@attr.s
class Config:
    port: int = attr.ib(converter=int)  # Automatyczna konwersja string → int
    enabled: bool = attr.ib(converter=bool)
```

### Auto-attribs (Python 3.6+)
```python
@attr.s(auto_attribs=True)
class AutoPoint:
    x: int
    y: int
    z: int = 0
```

## Wnioski i Najważniejsze Zasady

1. **DRY Principle**: attrs eliminuje ~70-80% boilerplate code przy definiowaniu klas
2. **Type Safety**: Integracja z `typing` module zapewnia type hints
3. **Performance**: `slots=True` redukuje zużycie pamięci o ~40%
4. **Immutability**: `frozen=True` tworzy bezpieczne, hashable objects
5. **Backward Compatibility**: Kompatybilne ze standardowymi klasami Pythona

## Kiedy używać

✅ **Używaj gdy:**
- Masz wiele klas z podobnym boilerplate (`__init__`, `__repr__`, `__eq__`)
- Potrzebujesz immutable data structures
- Koncentrujesz się na data classes (DTO, value objects)
- Chcesz automatyczną walidację pól

❌ **Nie używaj gdy:**
- Klasa ma złożoną logikę biznesową (lepiej standardowa klasa)
- Potrzebujesz pełnej kontroli nad metodami specjalnymi
- Projekt nie pozwala na zewnętrzne zależności

## Powiązane tematy

- [typing module](../advanced/typing-module.md) - type hints
- [dunder methods](../advanced/dunder-methods.md) - metody specjalne
- Data Classes (Python 3.7+) - alternatywa w standardowej bibliotece

## Źródła / dalsza lektura

- [Oficjalna dokumentacja attrs](https://www.attrs.org/en/stable/index.html)
- [attrs GitHub](https://github.com/python-attrs/attrs)
- [attrs vs dataclasses](https://www.attrs.org/en/stable/why.html#data-classes)

