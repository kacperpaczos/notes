# 📓 Notatki: `trait` w Rust i porównanie z innymi językami

## 🔹 Definicja

* `trait` w Rust = **interfejs + zestaw metod**, które mogą być implementowane przez typy.
* Umożliwia **polimorfizm**, **wspólne zachowania**, **abstrakcję**.

---

## 🔹 Kluczowe cechy `trait` w Rust

1. **Deklaracja**

   ```rust
   trait Speak {
       fn speak(&self) -> String;
   }
   ```

2. **Implementacja**

   ```rust
   struct Dog;

   impl Speak for Dog {
       fn speak(&self) -> String {
           "Woof!".to_string()
       }
   }
   ```

3. **Domyślne metody** – można zdefiniować zachowanie, które nie musi być nadpisane.

4. **Polimorfizm statyczny** – `fn foo<T: Trait>(x: T)` → generics (monomorfizacja, zero-cost abstraction).

5. **Polimorfizm dynamiczny** – `fn foo(x: &dyn Trait)` → obiekt trait, vtable (koszt runtime).

6. **Wielokrotna implementacja** – jeden typ może mieć wiele traitów (bez problemu diamentu).

7. **Brak klasycznego dziedziczenia** – Rust stawia na kompozycję, a nie hierarchie klas.

---

## 🔹 Porównanie do innych języków

* **Java (`interface`)**: tylko dynamiczny polimorfizm, runtime overhead, od Java 8 domyślne metody.
* **C++ (klasy abstrakcyjne / interface)**: dynamiczny polimorfizm (vtable) lub statyczny (templates), ale z ryzykiem błędów i wielokrotnego dziedziczenia.
* **Python (ABC, protocols)**: duck typing, dynamiczne sprawdzanie w runtime, mało bezpieczne, ale bardzo elastyczne.
* **Rust (`trait`)**: unikalne połączenie – bezpieczeństwo kompilacji, statyczny i dynamiczny polimorfizm, zero-cost abstractions.

---

## 🔹 Dlaczego Rust jest wyjątkowy?

1. **Bezpieczna wielokrotna implementacja** – bez konfliktów jak w C++.
2. **Zero-cost abstractions** – statyczny polimorfizm nie kosztuje w runtime.
3. **Brak dziedziczenia klas** → zamiast tego „łączenie” traitów (kompozycja).
4. **Spójne podejście** do generics (`T: Trait` jako bounds).

---

## 🔹 Przykład użycia

```rust
// Definicja trait
trait Animal {
    fn make_sound(&self) -> String;
    fn name(&self) -> String;

    // Domyślna metoda
    fn describe(&self) -> String {
        format!("{} says {}", self.name(), self.make_sound())
    }
}

// Implementacja dla psa
struct Dog {
    name: String,
}

impl Animal for Dog {
    fn make_sound(&self) -> String {
        "Woof!".to_string()
    }

    fn name(&self) -> String {
        self.name.clone()
    }
}

// Implementacja dla kota
struct Cat {
    name: String,
}

impl Animal for Cat {
    fn make_sound(&self) -> String {
        "Meow!".to_string()
    }

    fn name(&self) -> String {
        self.name.clone()
    }
}

// Funkcja przyjmująca dowolne zwierzę (polimorfizm statyczny)
fn greet_animal<T: Animal>(animal: T) {
    println!("Hello! {}", animal.describe());
}

// Funkcja z dynamicznym polimorfizmem
fn greet_animal_dyn(animal: &dyn Animal) {
    println!("Hello! {}", animal.describe());
}

fn main() {
    let dog = Dog { name: "Rex".to_string() };
    let cat = Cat { name: "Whiskers".to_string() };

    // Statyczny polimorfizm
    greet_animal(dog);
    greet_animal(cat);

    // Dynamiczny polimorfizm
    let animals: Vec<&dyn Animal> = vec![&Dog { name: "Buddy".to_string() }, &Cat { name: "Mittens".to_string() }];
    for animal in animals {
        greet_animal_dyn(animal);
    }
}
```

---

## 🔹 Ważne różnice między `impl Trait` a `dyn Trait`

### `impl Trait` (Statyczny polimorfizm)
- **Kiedy używać**: Kompilator zna konkretny typ w czasie kompilacji
- **Zalety**: Zero-cost, monomorfizacja, inline optimization
- **Wady**: Kod jest duplikowany dla każdego typu
- **Przykład**: `fn foo(x: impl Animal) -> impl Animal`

### `dyn Trait` (Dynamiczny polimorfizm)
- **Kiedy używać**: Nie wiemy jaki typ będziemy mieć w runtime
- **Zalety**: Mniejszy rozmiar binarnego, dynamiczne zachowanie
- **Wady**: Runtime overhead (vtable lookup), heap allocation zwykle wymagane
- **Przykład**: `fn foo(x: &dyn Animal)`

---

## 🔹 Zaawansowane koncepcje

### Associated Types
```rust
trait Container {
    type Item;
    fn get(&self) -> Self::Item;
}
```

### Trait Bounds
```rust
fn process<T: Animal + Display>(item: T) {
    // T musi implementować zarówno Animal jak i Display
}
```

### Supertraits
```rust
trait Animal: Display {
    // Animal wymaga implementacji Display
}
```

### Marker Traits
```rust
trait Send {} // Typ może być bezpiecznie przesłany między wątkami
trait Sync {} // Typ może być bezpiecznie współdzielony między wątkami
```
