# Engineering Guidelines

## Zasady wynikające z najlepszych praktyk (ściąga dla devów)

### Linting i formatowanie

#### Python

**Zasady:**
- Uruchamiaj przed pushem: `ruff`, `black`, `isort`, `mypy` (hooki pre-push już to robią).
- Traktuj ostrzeżenia linterów jako błędy; nie wyłączaj reguł globalnie, chyba że to konieczne i lokalnie (najwyżej przy imporcie).
- Formatuj kod automatycznie:
  - `isort`: sortuj importy zgodnie z profilem black.
  - `black`: trzymaj spójny styl i długość linii zgodnie z `pyproject.toml`.

**Przykład:**
```python
# ❌ Zły styl
from typing import Dict,List,Optional
import os
import sys
from pathlib import Path

def process_data(data:Dict[str,any])->Optional[Dict]:
    result={}
    for k,v in data.items():
        if v is not None:
            result[k]=v
    return result

# ✅ Dobry styl (po black + isort)
import os
import sys
from pathlib import Path
from typing import Any, Dict, Optional


def process_data(data: Dict[str, Any]) -> Optional[Dict]:
    result = {}
    for k, v in data.items():
        if v is not None:
            result[k] = v
    return result
```

**Komendy developerskie (lokalnie):**
```bash
# Formatowanie
python -m isort . && python -m black .

# Linting
python -m ruff check .

# Sprawdzanie typów
python -m mypy src tests
```

#### C/C++

**Zasady:**
- Użyj `clang-format` dla spójnego formatowania.
- Konfiguracja w `.clang-format` lub `_clang-format`.
- Uruchamiaj `clang-tidy` dla statycznej analizy.
- Traktuj wszystkie ostrzeżenia kompilatora jako błędy (`-Werror`).

**Przykład:**
```cpp
// ❌ Zły styl
int processData(int*arr,int n){
int sum=0;
for(int i=0;i<n;i++){
sum+=arr[i];
}
return sum;
}

// ✅ Dobry styl (po clang-format)
int process_data(int *arr, int n) {
    int sum = 0;
    for (int i = 0; i < n; i++) {
        sum += arr[i];
    }
    return sum;
}
```

**Komendy:**
```bash
# Formatowanie
clang-format -i src/**/*.{cpp,h}

# Analiza statyczna
clang-tidy src/**/*.cpp -- -I./include

# Kompilacja z wszystkimi ostrzeżeniami
clang++ -Wall -Wextra -Werror -std=c++17 main.cpp
```

---

### Wyjątki i obsługa błędów

#### Python

**Zasada:**
- Nie używaj gołych `except:`. Zawsze łap konkretny wyjątek lub `Exception` i zapisuj do zmiennej.
- Loguj błędy z kontekstem; nie połykaj wyjątków bez informacji.
- Dopuszczalne lokalne `except Exception as e` dla ścieżek niekrytycznych, ale bez cichego `pass`.

**Przykłady:**

```python
# ❌ Zły sposób
try:
    result = divide(10, 0)
except:
    pass

# ✅ Dobry sposób - konkretny wyjątek
try:
    result = divide(10, 0)
except ZeroDivisionError as e:
    logger.error(f"Division by zero: {e}", exc_info=True)
    result = None

# ✅ Dobry sposób - ogólny Exception z logowaniem
try:
    data = load_config_file("config.json")
except FileNotFoundError as e:
    logger.error(f"Config file not found: {e}")
    data = get_default_config()
except json.JSONDecodeError as e:
    logger.error(f"Invalid JSON in config: {e}")
    data = get_default_config()
except Exception as e:
    logger.error(f"Unexpected error loading config: {e}", exc_info=True)
    data = get_default_config()
```

#### C/C++

**Zasada:**
- Używaj wyjątków tylko w sytuacjach rzeczywiście wyjątkowych.
- Zawsze chwytaj wyjątki przez referencję (`catch (const std::exception& e)`).
- Używaj RAII dla zarządzania zasobami.
- W kodzie C preferuj kod zwracający wartości błędów lub użycie `errno`.

**Przykłady:**

```cpp
// ✅ C++ - Dobry sposób
#include <exception>
#include <iostream>
#include <stdexcept>

void process_data(const std::vector<int>& data) {
    try {
        if (data.empty()) {
            throw std::invalid_argument("Data vector is empty");
        }
        // Przetwarzanie danych
    } catch (const std::invalid_argument& e) {
        std::cerr << "Invalid argument: " << e.what() << std::endl;
        // Obsługa błędu
    } catch (const std::exception& e) {
        std::cerr << "Standard exception: " << e.what() << std::endl;
        // Obsługa ogólnego błędu
    }
}

// ❌ C++ - Zły sposób
void bad_process_data(const std::vector<int>& data) {
    try {
        // kod
    } catch (...) {  // Łapanie wszystkich wyjątków bez informacji
        // bez logowania, bez kontekstu
    }
}

// ✅ C - Dobry sposób (bez wyjątków)
#include <errno.h>
#include <stdio.h>

int process_data(int* arr, size_t n) {
    if (arr == NULL || n == 0) {
        errno = EINVAL;
        return -1;
    }
    
    // Przetwarzanie
    return 0;  // sukces
}
```

---

### Typowanie (mypy - Python)

**Zasady:**
- Unikaj implicit Optional: jeśli wartość może być pusta, użyj `Optional[...]` i sprawdzaj przed użyciem.
- Nie wprowadzaj kodu nieosiągalnego (unreachable). Usuń zbędne gałęzie lub błędne `return`/`raise`.
- Nie owijaj importów w szerokie `try/except`, które powodują "unreachable"; zamiast tego:
  - Zainstaluj stuby typów (preferowane), albo
  - Dodaj lokalne `# type: ignore[import-untyped]` przy konkretnym imporcie.
- Nadawaj precyzyjne typy atrybutom inicjalizowanym `None` (np. `self.thread: Optional[threading.Thread] = None`).
- Gdy słownik przyjmuje heterogeniczne wartości (np. wynik parsowania), użyj `Dict[str, Any]`.

**Przykłady:**

```python
from typing import Optional, Dict, Any, List
import threading

# ❌ Zły sposób - implicit Optional
class Connection:
    def __init__(self):
        self.thread = None  # mypy: Missing type annotation
    
    def start(self):
        if self.thread is None:
            self.thread = threading.Thread(target=self.run)
        self.thread.start()

# ✅ Dobry sposób - explicit Optional
class Connection:
    def __init__(self):
        self.thread: Optional[threading.Thread] = None
    
    def start(self) -> None:
        if self.thread is None:
            self.thread = threading.Thread(target=self.run)
        self.thread.start()

# ❌ Zły sposób - unreachable code
def process_value(value: Optional[int]) -> int:
    if value is None:
        return 0
    return value
    return -1  # Unreachable!

# ✅ Dobry sposób
def process_value(value: Optional[int]) -> int:
    if value is None:
        return 0
    return value

# ❌ Zły sposób - nieprecyzyjne typy dla heterogenicznych danych
def parse_config(data: Dict) -> Dict:  # Zbyt ogólne
    return data

# ✅ Dobry sposób
def parse_config(data: Dict[str, Any]) -> Dict[str, Any]:
    return data

# ❌ Zły sposób - szeroki try/except wokół importu
try:
    from external_lib import SomeClass
except ImportError:
    SomeClass = None  # type: ignore  # Unreachable w mypy

# ✅ Dobry sposób - lokalny type: ignore
try:
    from external_lib import SomeClass  # type: ignore[import-untyped]
except ImportError:
    # Instalacja stubów: pip install types-external-lib
    from typing import TYPE_CHECKING
    if TYPE_CHECKING:
        from external_lib import SomeClass
    else:
        SomeClass = None
```

---

### Typowanie (C/C++)

**Zasady:**
- Używaj statycznych asercji typu (`static_assert`) do walidacji w compile-time.
- Preferuj `auto` gdy typ jest oczywisty z kontekstu.
- Unikaj niebezpiecznych rzutowań (`reinterpret_cast`, `const_cast`).
- Używaj `const` i `constexpr` gdzie tylko możliwe.

**Przykłady:**

```cpp
// ✅ Dobry sposób - auto gdy typ jest oczywisty
auto result = std::make_unique<DataProcessor>(config);
auto items = std::vector<int>{1, 2, 3};

// ❌ Niepotrzebne powtarzanie typu
std::unique_ptr<DataProcessor> result = std::make_unique<DataProcessor>(config);
std::vector<int> items = std::vector<int>{1, 2, 3};

// ✅ Dobry sposób - const i constexpr
constexpr int MAX_SIZE = 1024;
const std::string& get_name() const;

// ✅ Dobry sposób - static_assert dla typów
static_assert(sizeof(int) == 4, "int must be 32 bits");

// ❌ Zły sposób - niebezpieczne rzutowanie
int* ptr = (int*)malloc(100);  // Stary styl C
void* vptr = ptr;
char* cptr = (char*)vptr;  // Niebezpieczne

// ✅ Dobry sposób - bezpieczne rzutowanie
auto ptr = std::make_unique<int[]>(25);
auto vptr = static_cast<void*>(ptr.get());  // Jeśli konieczne
```

---

### Interfejsy i dziedziczenie

#### Python

**Zasada:**
- Nie wywołuj `super().execute(...)`, gdy metoda bazowa jest abstrakcyjna lub nie zapewnia bezpiecznej implementacji. Implementacja powinna być kompletna w klasie pochodnej.

**Przykłady:**

```python
from abc import ABC, abstractmethod

# ✅ Dobry sposób - abstrakcyjna klasa bazowa
class Processor(ABC):
    @abstractmethod
    def process(self, data: Dict[str, Any]) -> Dict[str, Any]:
        """Procesuje dane. Musi być zaimplementowana w klasie pochodnej."""
        pass
    
    def validate(self, data: Dict[str, Any]) -> bool:
        """Wspólna walidacja dla wszystkich procesorów."""
        return isinstance(data, dict) and len(data) > 0

# ✅ Dobry sposób - kompletna implementacja w klasie pochodnej
class JsonProcessor(Processor):
    def process(self, data: Dict[str, Any]) -> Dict[str, Any]:
        # Kompletna implementacja bez wywołania super()
        if not self.validate(data):
            raise ValueError("Invalid data")
        return {"processed": data, "format": "json"}

# ❌ Zły sposób - wywołanie super() na abstrakcyjnej metodzie
class BadProcessor(Processor):
    def process(self, data: Dict[str, Any]) -> Dict[str, Any]:
        return super().process(data)  # Błąd! Metoda bazowa jest abstrakcyjna
```

#### C++

**Zasady:**
- Używaj czystych funkcji wirtualnych (`= 0`) dla interfejsów.
- Implementuj wszystkie metody wirtualne w klasach pochodnych.
- Unikaj wywołań wirtualnych metod w destruktorach.

**Przykłady:**

```cpp
// ✅ Dobry sposób - interfejs z czystymi funkcjami wirtualnymi
class IProcessor {
public:
    virtual ~IProcessor() = default;
    virtual void process(const std::vector<int>& data) = 0;
    virtual bool validate(const std::vector<int>& data) const {
        return !data.empty();
    }
};

// ✅ Dobry sposób - kompletna implementacja
class JsonProcessor : public IProcessor {
public:
    void process(const std::vector<int>& data) override {
        if (!validate(data)) {
            throw std::invalid_argument("Invalid data");
        }
        // Kompletna implementacja bez wywołania metody bazowej
    }
};

// ❌ Zły sposób - próba wywołania metody bazowej która jest abstrakcyjna
class BadProcessor : public IProcessor {
public:
    void process(const std::vector<int>& data) override {
        IProcessor::process(data);  // Błąd kompilacji!
    }
};
```

---

### Zgodność API

#### Python

**Zasada:**
- Przekazuj tylko te argumenty, które występują w sygnaturze docelowej funkcji/klasy.
- Nie przekazuj "losowych" `**kwargs` do konstruktorów/wywołań bez weryfikacji — najpierw odfiltruj lub jawnie mapuj.

**Przykłady:**

```python
# ✅ Dobry sposób - zgodność z sygnaturą
class SshBackend:
    def __init__(self, host: str, port: int, username: str):
        self.host = host
        self.port = port
        self.username = username

# Użycie
backend = SshBackend(
    host="example.com",
    port=22,
    username="user"
)

# ❌ Zły sposób - przekazywanie nieznanych argumentów
config = {
    "host": "example.com",
    "port": 22,
    "username": "user",
    "password": "secret",  # Nie występuje w __init__
    "timeout": 30  # Nie występuje w __init__
}
backend = SshBackend(**config)  # TypeError!

# ✅ Dobry sposób - filtrowanie argumentów
def create_ssh_backend(config: Dict[str, Any]) -> SshBackend:
    allowed_keys = {"host", "port", "username"}
    filtered_config = {k: v for k, v in config.items() if k in allowed_keys}
    return SshBackend(**filtered_config)
```

#### C++

**Zasady:**
- Używaj funkcji przeciążonych zamiast `**kwargs`-podobnych rozwiązań.
- Waliduj argumenty funkcji przed użyciem.
- Używaj `std::optional` dla opcjonalnych parametrów.

**Przykłady:**

```cpp
// ✅ Dobry sposób - jasna sygnatura funkcji
class SshBackend {
public:
    SshBackend(const std::string& host, int port, const std::string& username)
        : host_(host), port_(port), username_(username) {}
    
private:
    std::string host_;
    int port_;
    std::string username_;
};

// ✅ Dobry sposób - opcjonalne parametry
class SshBackend {
public:
    SshBackend(
        const std::string& host,
        int port,
        const std::string& username,
        std::optional<int> timeout = std::nullopt
    ) : host_(host), port_(port), username_(username), timeout_(timeout) {}
    
private:
    std::string host_;
    int port_;
    std::string username_;
    std::optional<int> timeout_;
};
```

---

### Obsługa wartości opcjonalnych

#### Python

**Zasada:**
- Gdy obiekt/atrybut może być pusty (np. Flask `rule.methods`), stosuj bezpieczne gardy.
- Zanim użyjesz elementów z `Optional[...]`, sprawdź `if value is not None:`.

**Przykłady:**

```python
from typing import Optional, Set

# ✅ Dobry sposób - bezpieczne gardy
def get_http_methods(rule) -> Set[str]:
    methods = [m for m in (rule.methods or set()) if m not in {"HEAD", "OPTIONS"}]
    return set(methods)

# ❌ Zły sposób - brak sprawdzenia None
def bad_get_methods(rule) -> Set[str]:
    methods = [m for m in rule.methods if m not in {"HEAD", "OPTIONS"}]  # Może rzucić AttributeError
    return set(methods)

# ✅ Dobry sposób - sprawdzanie Optional przed użyciem
def process_data(data: Optional[Dict[str, Any]]) -> Dict[str, Any]:
    if data is None:
        return {}
    return {k: v for k, v in data.items() if v is not None}

# ❌ Zły sposób
def bad_process_data(data: Optional[Dict[str, Any]]) -> Dict[str, Any]:
    return {k: v for k, v in data.items() if v is not None}  # AttributeError gdy data jest None
```

#### C++

**Zasady:**
- Używaj `std::optional` dla wartości opcjonalnych.
- Zawsze sprawdzaj `has_value()` przed dostępem do `std::optional`.

**Przykłady:**

```cpp
#include <optional>
#include <vector>
#include <string>

// ✅ Dobry sposób - std::optional z walidacją
std::optional<std::vector<std::string>> get_methods(const Rule& rule) {
    if (!rule.methods.has_value()) {
        return std::nullopt;
    }
    
    std::vector<std::string> filtered;
    for (const auto& method : rule.methods.value()) {
        if (method != "HEAD" && method != "OPTIONS") {
            filtered.push_back(method);
        }
    }
    return filtered;
}

// ✅ Dobry sposób - bezpieczny dostęp
void process_data(const std::optional<std::vector<int>>& data) {
    if (!data.has_value()) {
        // Obsługa przypadku None
        return;
    }
    
    for (const auto& value : data.value()) {
        // Przetwarzanie
    }
}

// ❌ Zły sposób - brak sprawdzenia
void bad_process_data(const std::optional<std::vector<int>>& data) {
    for (const auto& value : data.value()) {  // Może rzucić std::bad_optional_access
        // ...
    }
}
```

---

### Parsowanie i walidacja danych

#### Python

**Zasada:**
- Rzutuj dane wejściowe defensywnie; dla wyników poleceń shell używaj konwersji z walidacją i typów ogólnych gdy to konieczne (`Dict[str, Any]`).

**Przykłady:**

```python
import subprocess
from typing import Dict, Any, List

# ✅ Dobry sposób - defensywne parsowanie
def parse_process_output(output: str) -> Dict[str, Any]:
    try:
        result = {}
        lines = output.strip().split('\n')
        for line in lines:
            if ':' in line:
                key, value = line.split(':', 1)
                result[key.strip()] = value.strip()
        return result
    except Exception as e:
        logger.error(f"Failed to parse output: {e}")
        return {}

# ✅ Dobry sposób - walidacja przed użyciem
def safe_int_convert(value: Any) -> Optional[int]:
    try:
        return int(value)
    except (ValueError, TypeError):
        return None

# ❌ Zły sposób - brak walidacji
def unsafe_parse(output: str) -> Dict[str, Any]:
    result = {}
    for line in output.split('\n'):
        key, value = line.split(':')  # Może rzucić ValueError
        result[key] = int(value)  # Może rzucić ValueError
    return result
```

#### C++

**Zasady:**
- Waliduj dane wejściowe przed parsowaniem.
- Używaj bezpiecznych funkcji parsujących z obsługą błędów (`std::stoi` z `try-catch`, `std::from_chars`).

**Przykłady:**

```cpp
#include <string>
#include <stdexcept>
#include <sstream>
#include <optional>

// ✅ Dobry sposób - bezpieczne parsowanie
std::optional<int> safe_string_to_int(const std::string& str) {
    try {
        size_t pos;
        int value = std::stoi(str, &pos);
        if (pos == str.length()) {  // Cały string został sparsowany
            return value;
        }
        return std::nullopt;  // Część stringa nie była liczbą
    } catch (const std::exception&) {
        return std::nullopt;
    }
}

// ✅ Dobry sposób - walidacja przed użyciem
bool validate_and_process(const std::string& data) {
    if (data.empty()) {
        return false;
    }
    
    auto value = safe_string_to_int(data);
    if (!value.has_value()) {
        return false;
    }
    
    // Przetwarzanie
    return true;
}

// ❌ Zły sposób - brak walidacji
int unsafe_parse(const std::string& str) {
    return std::stoi(str);  // Może rzucić wyjątek
}
```

---

### Testy

#### Python

**Zasady:**
- Minimalizuj `# type: ignore`; jeśli konieczne, stosuj precyzyjny kod błędu i tylko w ściśle problematycznej linii.
- Unikaj konstrukcji, które mogą generować "unreachable" w testach (np. niepotrzebne try/except wokół cleanupów).

**Przykłady:**

```python
import pytest
from typing import Optional

# ✅ Dobry sposób - minimalne type: ignore
def test_process_data():
    data = {"key": "value"}  # type: ignore[dict-item]  # Precyzyjny kod
    
    result = process_data(data)
    assert result is not None

# ❌ Zły sposób - szerokie type: ignore
def bad_test_process_data():
    data = {"key": "value"}  # type: ignore  # Zbyt ogólne
    
    result = process_data(data)
    assert result is not None

# ✅ Dobry sposób - czysty cleanup bez niepotrzebnego try/except
def test_with_cleanup():
    resource = acquire_resource()
    try:
        result = process_resource(resource)
        assert result is not None
    finally:
        release_resource(resource)  # Zawsze wykonane

# ❌ Zły sposób - niepotrzebny try/except powodujący unreachable
def bad_test_with_cleanup():
    resource = acquire_resource()
    try:
        try:
            result = process_resource(resource)
            assert result is not None
        except Exception:  # Może powodować "unreachable" w mypy
            pass
    finally:
        release_resource(resource)
```

#### C++

**Zasady:**
- Używaj frameworków testowych (Google Test, Catch2).
- Testuj zarówno przypadki sukcesu jak i błędu.
- Używaj RAII dla cleanup w testach.

**Przykłady:**

```cpp
#include <gtest/gtest.h>
#include <memory>

// ✅ Dobry sposób - czytelne testy
TEST(DataProcessorTest, ProcessValidData) {
    auto processor = std::make_unique<DataProcessor>();
    std::vector<int> data = {1, 2, 3};
    
    auto result = processor->process(data);
    EXPECT_TRUE(result.has_value());
    EXPECT_EQ(result->size(), 3);
}

TEST(DataProcessorTest, ProcessEmptyData) {
    auto processor = std::make_unique<DataProcessor>();
    std::vector<int> data = {};
    
    auto result = processor->process(data);
    EXPECT_FALSE(result.has_value());
}

// ✅ Dobry sposób - RAII dla cleanup
class TestFixture {
public:
    TestFixture() { /* setup */ }
    ~TestFixture() { /* cleanup */ }
};

TEST_F(TestFixture, TestWithFixture) {
    // Test automatycznie korzysta z setup i cleanup
}
```

---

### Zależności zewnętrzne

#### Python

**Zasada:**
- Preferuj instalację stubów typów (`types-...`) dla bibliotek zewnętrznych.
- Jeśli to niemożliwe, ogranicz `# type: ignore[...]` do pojedynczego importu, nie globalnie.

**Przykłady:**

```bash
# ✅ Preferowane - instalacja stubów
pip install types-requests
pip install types-flask
pip install types-PyYAML
```

```python
# ✅ Dobry sposób - używanie stubów
import requests  # Działa z types-requests

def fetch_data(url: str) -> Optional[Dict[str, Any]]:
    try:
        response = requests.get(url, timeout=5)
        response.raise_for_status()
        return response.json()
    except requests.RequestException:
        return None

# ✅ Dopuszczalne - lokalny type: ignore dla konkretnego importu
try:
    from external_lib_without_stubs import SomeClass  # type: ignore[import-untyped]
except ImportError:
    SomeClass = None

# ❌ Zły sposób - globalne wyłączenie typów
# type: ignore  # Na początku pliku - złe!
```

#### C++

**Zasady:**
- Używaj systemów zarządzania zależnościami (vcpkg, Conan, CMake FetchContent).
- Preferuj nagłówki bibliotek z typami zamiast surowych wskaźników.

**Przykłady:**

```cmake
# ✅ Dobry sposób - CMake FetchContent
include(FetchContent)
FetchContent_Declare(
  json
  GIT_REPOSITORY https://github.com/nlohmann/json.git
  GIT_TAG v3.11.2
)
FetchContent_MakeAvailable(json)
```

```cpp
// ✅ Dobry sposób - używanie bibliotek z typami
#include <nlohmann/json.hpp>
using json = nlohmann::json;

json parse_config(const std::string& config_str) {
    try {
        return json::parse(config_str);
    } catch (const json::exception& e) {
        // Obsługa błędu
        return json{};
    }
}
```

---

### Zasada DRY i prostoty

**Zasady:**
- Usuwaj zduplikowane pętle "czyszczące" dane; preferuj jedno, czytelne miejsce transformacji.
- Utrzymuj kod krótki, deterministyczny i z jasnymi warunkami brzegowymi.

**Przykłady - Python:**

```python
# ❌ Zły sposób - zduplikowany kod
def clean_data_v1(data: List[str]) -> List[str]:
    result = []
    for item in data:
        cleaned = item.strip().lower()
        if cleaned and len(cleaned) > 2:
            result.append(cleaned)
    return result

def clean_data_v2(data: List[str]) -> List[str]:
    result = []
    for item in data:
        cleaned = item.strip().lower()
        if cleaned and len(cleaned) > 2:
            result.append(cleaned)
    return result

# ✅ Dobry sposób - jedna funkcja transformacji
def clean_item(item: str) -> Optional[str]:
    cleaned = item.strip().lower()
    if cleaned and len(cleaned) > 2:
        return cleaned
    return None

def clean_data(data: List[str]) -> List[str]:
    return [item for item in map(clean_item, data) if item is not None]
```

**Przykłady - C++:**

```cpp
// ❌ Zły sposób - zduplikowany kod
std::vector<std::string> clean_data_v1(const std::vector<std::string>& data) {
    std::vector<std::string> result;
    for (const auto& item : data) {
        std::string cleaned = trim_and_lower(item);
        if (!cleaned.empty() && cleaned.length() > 2) {
            result.push_back(cleaned);
        }
    }
    return result;
}

// ✅ Dobry sposób - jedna funkcja transformacji
std::optional<std::string> clean_item(const std::string& item) {
    std::string cleaned = trim_and_lower(item);
    if (!cleaned.empty() && cleaned.length() > 2) {
        return cleaned;
    }
    return std::nullopt;
}

std::vector<std::string> clean_data(const std::vector<std::string>& data) {
    std::vector<std::string> result;
    for (const auto& item : data) {
        if (auto cleaned = clean_item(item)) {
            result.push_back(*cleaned);
        }
    }
    return result;
}
```

---

## Podsumowanie

### Checklist przed commitem (Python)

- [ ] Uruchom `python -m isort . && python -m black .`
- [ ] Uruchom `python -m ruff check .`
- [ ] Uruchom `python -m mypy src tests`
- [ ] Sprawdź czy nie ma `# type: ignore` bez precyzyjnego kodu błędu
- [ ] Sprawdź czy wszystkie `Optional` są prawidłowo obsłużone
- [ ] Sprawdź czy nie ma gołych `except:`
- [ ] Sprawdź czy błędy są logowane z kontekstem

### Checklist przed commitem (C/C++)

- [ ] Uruchom `clang-format -i src/**/*.{cpp,h}`
- [ ] Uruchom `clang-tidy src/**/*.cpp`
- [ ] Kompiluj z `-Wall -Wextra -Werror`
- [ ] Sprawdź czy wszystkie wyjątki są łapane przez referencję
- [ ] Sprawdź czy używasz `std::optional` dla wartości opcjonalnych
- [ ] Sprawdź czy używasz RAII do zarządzania zasobami
- [ ] Uruchom testy jednostkowe

---

## Źródła i dalsza lektura

- [PEP 8 - Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/)
- [Google C++ Style Guide](https://google.github.io/styleguide/cppguide.html)
- [C++ Core Guidelines](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines)
- [mypy Documentation](https://mypy.readthedocs.io/)
- [Ruff Documentation](https://docs.astral.sh/ruff/)
- [Black Code Formatter](https://black.readthedocs.io/)

