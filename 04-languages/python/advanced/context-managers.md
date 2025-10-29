# Context Managers w Pythonie

## Cel

Context Managers (menedżery kontekstu) to mechanizm w Pythonie służący do zarządzania zasobami (pliki, połączenia, transakcje), który **gwarantuje** poprawną inicjalizację i cleanup, nawet w przypadku wystąpienia wyjątków.

## Instrukcja `with`

### Podstawowa składnia
```python
with open('file.txt', 'r') as f:
    data = f.read()
# Plik automatycznie zamknięty, nawet jeśli wystąpi wyjątek
```

### Bezpieczne zarządzanie zasobami
```python
# ❌ Niebezpieczne
file = open('file.txt')
data = file.read()
file.close()  # Co jeśli wystąpi błąd przed close()?

# ✅ Bezpieczne z context manager
with open('file.txt') as file:
    data = file.read()
    # Zawsze zamknięte, nawet przy wyjątku
```

## Protokół Context Manager

Klasa implementująca context manager musi mieć dwie metody:

### 1. `__enter__` - Wejście do kontekstu
```python
def __enter__(self):
    # Kod inicjalizacyjny
    # Zwraca obiekt dostępny jako 'as variable'
    return self
```

### 2. `__exit__` - Wyjście z kontekstu
```python
def __exit__(self, exc_type, exc_val, traceback):
    # Kod cleanup (zawsze wykonywany)
    # exc_type, exc_val, traceback - informacje o wyjątku (None jeśli brak)
    # Zwraca True/False - jeśli True, wyjątek jest "połknięty"
    return False  # Nie tłumimy wyjątku
```

## Implementacja Własnego Context Managera

### Przykład: Zarządzanie połączeniem
```python
class DatabaseConnection:
    def __init__(self, host: str, port: int):
        self.host = host
        self.port = port
        self.connection = None
    
    def __enter__(self):
        print(f"Connecting to {self.host}:{self.port}")
        self.connection = self._connect()
        return self.connection
    
    def __exit__(self, exc_type, exc_val, traceback):
        print("Closing connection")
        if self.connection:
            self.connection.close()
        return False  # Propaguj wyjątki
    
    def _connect(self):
        # Symulacja połączenia
        return {"status": "connected"}

# Użycie
with DatabaseConnection("localhost", 5432) as conn:
    # Użycie połączenia
    print(conn)
```

### Przykład: Timer Context Manager
```python
import time
from contextlib import contextmanager

class Timer:
    def __init__(self, name: str = "Operation"):
        self.name = name
    
    def __enter__(self):
        self.start = time.time()
        return self
    
    def __exit__(self, exc_type, exc_val, traceback):
        elapsed = time.time() - self.start
        print(f"{self.name} took {elapsed:.2f} seconds")
        return False

with Timer("Data processing"):
    # Kod do zmierzenia
    time.sleep(1)
```

## Decorator `@contextmanager`

### Prostszy sposób z funkcją generatorową
```python
from contextlib import contextmanager

@contextmanager
def file_manager(filename: str, mode: str):
    file = open(filename, mode)
    try:
        yield file  # Wszystko przed yield to __enter__, po yield to __exit__
    finally:
        file.close()

with file_manager('data.txt', 'r') as f:
    data = f.read()
```

### Przykład: Temporary Directory
```python
from contextlib import contextmanager
import tempfile
import shutil

@contextmanager
def temporary_directory():
    temp_dir = tempfile.mkdtemp()
    try:
        yield temp_dir
    finally:
        shutil.rmtree(temp_dir)  # Zawsze usuń

with temporary_directory() as temp:
    # Praca z temp directory
    ...
```

## Obsługa Wyjątków w `__exit__`

### Parametry `__exit__`
```python
def __exit__(self, exc_type, exc_val, traceback):
    # exc_type - typ wyjątku (None jeśli brak wyjątku)
    # exc_val - instancja wyjątku
    # traceback - obiekt traceback
    
    if exc_type is not None:
        print(f"Exception occurred: {exc_type.__name__}: {exc_val}")
    
    # Cleanup zawsze wykonywany
    self.cleanup()
    
    # Zwróć True aby "połknąć" wyjątek
    # Zwróć False aby propagować wyjątek
    return False
```

### Przykład: Rollback przy błędzie
```python
class Transaction:
    def __init__(self):
        self.changes = []
    
    def __enter__(self):
        return self
    
    def __exit__(self, exc_type, exc_val, traceback):
        if exc_type is not None:
            # Wyjątek - rollback
            print("Rolling back transaction")
            self._rollback()
        else:
            # Sukces - commit
            print("Committing transaction")
            self._commit()
        return False
    
    def add_change(self, change):
        self.changes.append(change)
```

## Najważniejsze Wnioski

### 1. Zawsze wykonany cleanup
```python
with Resource() as r:
    raise ValueError("Error!")  # Wyjątek!
    # __exit__ i tak zostanie wywołane
```

### 2. Wiele context managerów
```python
with open('input.txt') as inp, open('output.txt', 'w') as out:
    data = inp.read()
    out.write(data.upper())
```

### 3. Context manager może zwracać różne obiekty
```python
class ResourceManager:
    def __enter__(self):
        self.setup()
        return self.client  # Zwraca konkretny obiekt, nie self
```

## Wbudowane Context Managers w Pythonie

### Standardowa biblioteka
- `open()` - pliki
- `threading.Lock()` - mutex
- `multiprocessing.Pool()` - pool procesów
- `decimal.localcontext()` - kontekst decimal
- `unittest.mock.patch()` - mocking

### Przykłady
```python
# Lock
import threading

lock = threading.Lock()
with lock:
    # Sekcja krytyczna
    shared_data += 1

# Decimal context
from decimal import localcontext, Decimal, getcontext

with localcontext() as ctx:
    ctx.prec = 50
    result = Decimal(1) / Decimal(7)
```

## Najczęstsze Wzorce

### 1. Resource Acquisition Pattern
```python
class Resource:
    def __init__(self):
        self.acquired = False
    
    def __enter__(self):
        self.acquire()
        self.acquired = True
        return self
    
    def __exit__(self, *args):
        if self.acquired:
            self.release()
        return False
```

### 2. Context Decorator Pattern
```python
@contextmanager
def suppress_exceptions(*exceptions):
    try:
        yield
    except exceptions:
        pass

with suppress_exceptions(ValueError, KeyError):
    # Błędy są tłumione
    risky_operation()
```

### 3. Nested Context Managers
```python
class NestedTransaction:
    def __init__(self):
        self.level = 0
    
    def __enter__(self):
        self.level += 1
        print(f"Entering transaction level {self.level}")
        return self
    
    def __exit__(self, *args):
        print(f"Exiting transaction level {self.level}")
        self.level -= 1
        return False
```

## Best Practices

1. **Zawsze cleanup w `__exit__`**: Nawet jeśli wystąpi wyjątek
2. **Zwracaj `False` z `__exit__`**: Chyba że chcesz świadomie tłumić wyjątek
3. **Używaj `@contextmanager` dla prostych przypadków**: Lepsze niż klasa dla prostych context managerów
4. **Type hints**: Dodawaj type hints do `__enter__` i `__exit__`

```python
from typing import Optional, Type
from types import TracebackType

def __enter__(self) -> Resource:
    ...

def __exit__(
    self,
    exc_type: Optional[Type[BaseException]],
    exc_val: Optional[BaseException],
    traceback: Optional[TracebackType]
) -> bool:
    ...
```

## Najczęstsze Błędy

### ❌ Błąd: Brak obsługi wyjątków w `__exit__`
```python
def __exit__(self, *args):
    self.cleanup()  # Jeśli cleanup() rzuci wyjątek, __exit__ nie może go obsłużyć
```

### ✅ Poprawne
```python
def __exit__(self, *args):
    try:
        self.cleanup()
    except Exception as e:
        logger.error(f"Cleanup error: {e}")
    return False
```

### ❌ Błąd: Tłumienie wszystkich wyjątków
```python
def __exit__(self, *args):
    self.cleanup()
    return True  # Tłumi WSZYSTKIE wyjątki - niebezpieczne!
```

## Powiązane tematy

- [dunder methods](./dunder-methods.md) - `__enter__` i `__exit__`
- [Exception Handling](../Exception) - obsługa wyjątków
- [typing module](./typing-module.md) - type hints dla context managers

## Źródła / dalsza lektura

- [Context Manager Protocol](https://docs.python.org/3/reference/datamodel.html#context-managers)
- [with Statement](https://docs.python.org/3/reference/compound_stmts.html#with)
- [contextlib module](https://docs.python.org/3/library/contextlib.html)
- [Real Python: Context Managers](https://realpython.com/python-with-statement/)

