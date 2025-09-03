# 📓 Notes: `trait` in Rust and Comparison with Other Languages

## 🔹 Definition

* `trait` in Rust = **interface + set of methods** that types can implement.
* Enables **polymorphism**, **shared behaviors**, **abstraction**.

---

## 🔹 Key Features of `trait` in Rust

1. **Declaration**

   ```rust
   trait Speak {
       fn speak(&self) -> String;
   }
   ```

2. **Implementation**

   ```rust
   struct Dog;

   impl Speak for Dog {
       fn speak(&self) -> String {
           "Woof!".to_string()
       }
   }
   ```

3. **Default methods** – you can define behavior that doesn't need to be overridden.

4. **Static polymorphism** – `fn foo<T: Trait>(x: T)` → generics (monomorphization, zero-cost abstraction).

5. **Dynamic polymorphism** – `fn foo(x: &dyn Trait)` → trait object, vtable (runtime cost).

6. **Multiple implementation** – one type can have multiple traits (no diamond problem).

7. **No classical inheritance** – Rust favors composition over class hierarchies.

---

## 🔹 Comparison to Other Languages

* **Java (`interface`)**: only dynamic polymorphism, runtime overhead, default methods since Java 8.
* **C++ (abstract classes / interface)**: dynamic polymorphism (vtable) or static (templates), but with error risks and multiple inheritance issues.
* **Python (ABC, protocols)**: duck typing, dynamic runtime checking, less safe but very flexible.
* **Rust (`trait`)**: unique combination – compile-time safety, static and dynamic polymorphism, zero-cost abstractions.

---

## 🔹 Why Rust is Exceptional?

1. **Safe multiple implementation** – no conflicts like in C++.
2. **Zero-cost abstractions** – static polymorphism has no runtime cost.
3. **No class inheritance** → instead "composition" of traits.
4. **Consistent approach** to generics (`T: Trait` as bounds).

---

## 🔹 Usage Example

```rust
// Trait definition
trait Animal {
    fn make_sound(&self) -> String;
    fn name(&self) -> String;

    // Default method
    fn describe(&self) -> String {
        format!("{} says {}", self.name(), self.make_sound())
    }
}

// Implementation for dog
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

// Implementation for cat
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

// Function accepting any animal (static polymorphism)
fn greet_animal<T: Animal>(animal: T) {
    println!("Hello! {}", animal.describe());
}

// Function with dynamic polymorphism
fn greet_animal_dyn(animal: &dyn Animal) {
    println!("Hello! {}", animal.describe());
}

fn main() {
    let dog = Dog { name: "Rex".to_string() };
    let cat = Cat { name: "Whiskers".to_string() };

    // Static polymorphism
    greet_animal(dog);
    greet_animal(cat);

    // Dynamic polymorphism
    let animals: Vec<&dyn Animal> = vec![&Dog { name: "Buddy".to_string() }, &Cat { name: "Mittens".to_string() }];
    for animal in animals {
        greet_animal_dyn(animal);
    }
}
```

---

## 🔹 Important Differences Between `impl Trait` and `dyn Trait`

### `impl Trait` (Static Polymorphism)
- **When to use**: Compiler knows the concrete type at compile time
- **Advantages**: Zero-cost, monomorphization, inline optimization
- **Disadvantages**: Code is duplicated for each type
- **Example**: `fn foo(x: impl Animal) -> impl Animal`

### `dyn Trait` (Dynamic Polymorphism)
- **When to use**: We don't know what type we'll have at runtime
- **Advantages**: Smaller binary size, dynamic behavior
- **Disadvantages**: Runtime overhead (vtable lookup), heap allocation usually required
- **Example**: `fn foo(x: &dyn Animal)`

---

## 🔹 Advanced Concepts

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
    // T must implement both Animal and Display
}
```

### Supertraits
```rust
trait Animal: Display {
    // Animal requires Display implementation
}
```

### Marker Traits
```rust
trait Send {} // Type can be safely sent between threads
trait Sync {} // Type can be safely shared between threads
```
