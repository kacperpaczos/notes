# 🎴 Flashcards: `trait` in Rust

## Basic Questions

**Q:** What is a `trait` in Rust?  
**A:** A collection of methods (interface) defining behavior that a type can implement.

**Q:** How do you declare a trait in Rust?  
**A:** Using the `trait` keyword and defining methods without implementation.

**Q:** How do you implement a trait for a type?  
**A:** Using the syntax `impl TraitName for TypeName { ... }`.

**Q:** Can a trait have default methods?  
**A:** Yes – you can provide a default implementation that can be overridden.

## Polymorphism

**Q:** How does static polymorphism differ from dynamic polymorphism in Rust?  
**A:** Static uses generics and is resolved at compile time; dynamic uses `dyn Trait` and vtable.

**Q:** How is static polymorphism achieved in Rust?  
**A:** Through generics and monomorphization (`impl Trait`, `T: Trait`).

**Q:** How is dynamic polymorphism achieved in Rust?  
**A:** Through `dyn Trait` (trait objects, vtable).

**Q:** What is monomorphization?  
**A:** Compiler process that creates specialized versions of functions for each used type.

## Comparisons with Other Languages

**Q:** How does `trait` differ from abstract class in C++?  
**A:** Rust has no inheritance, only composition; `trait` provides both static and dynamic polymorphism.

**Q:** How does Rust differ from Java in terms of interfaces?  
**A:** Rust has static and dynamic polymorphism + zero-cost abstractions; Java has only dynamic with runtime overhead.

**Q:** What are the main differences between trait in Rust and interface in Go?  
**A:** Go has only dynamic polymorphism; Rust offers both types + compile-time safety.

## Advanced Concepts

**Q:** What are associated types in traits?  
**A:** Types associated with a trait that the implementing type can specify (e.g., `type Item`).

**Q:** What are trait bounds?  
**A:** Constraints on generic types that require implementation of specific traits.

**Q:** What are supertraits?  
**A:** A trait that requires implementation of other traits (e.g., `trait A: B` means A requires B).

**Q:** Can a type in Rust implement multiple traits?  
**A:** Yes, without diamond problems like in C++.

## Performance and Safety

**Q:** Why are `trait`s in Rust "zero-cost"?  
**A:** Because static polymorphism is resolved at compile time – no additional runtime cost.

**Q:** What are the costs of using `dyn Trait`?  
**A:** Runtime overhead through vtable lookup and usually requires heap allocation.

**Q:** How do you constrain generic types to specific behaviors in Rust?  
**A:** Using **trait bounds**: `fn foo<T: Trait>(x: T)`.

**Q:** What is the "diamond problem" and how does Rust solve it?  
**A:** Multiple inheritance problem; Rust avoids it through composition instead of hierarchy.

## Practical Applications

**Q:** What are practical uses of trait bounds?  
**A:** Constraining generic types to those that have needed methods/behaviors.

**Q:** When to use `impl Trait` instead of `dyn Trait`?  
**A:** When you know concrete types at compile time and want zero-cost abstractions.

**Q:** When to use `dyn Trait` instead of generics?  
**A:** When you need collections of different types implementing the same trait at runtime.

**Q:** What are marker traits and what are examples?  
**A:** Traits without methods that mark type properties (e.g., `Send`, `Sync`, `Copy`).

## Iterator Traits

**Q:** What is the `Iterator` trait?  
**A:** Trait defining how to iterate over a collection with a `next()` method.

**Q:** How do you implement a custom iterator?  
**A:** By implementing the `Iterator` trait with `next() -> Option<Self::Item>` method.

**Q:** What is the `IntoIterator` trait?  
**A:** Allows converting a type into an iterator using `into_iter()`.

## Operators and Standard Traits

**Q:** How do you overload operators in Rust?  
**A:** By implementing appropriate traits like `Add`, `Sub`, `Mul`, etc.

**Q:** What is the `Display` trait?  
**A:** Used to format a type into a human-readable representation (`{}` in `println!`).

**Q:** What is the `Debug` trait?  
**A:** Used to format a type for debugging (`{:?}` in `println!`).

**Q:** What traits are needed to use a type in `HashMap`?  
**A:** `Eq` and `Hash` for keys, optionally `PartialEq` and `PartialOrd`.

## Thread Safety

**Q:** What does implementing the `Send` trait mean?  
**A:** Type can be safely sent between threads.

**Q:** What does implementing the `Sync` trait mean?  
**A:** Type can be safely shared between threads.

**Q:** Do all types implement `Send` and `Sync`?  
**A:** Most do, but types with internal mutexes may not implement `Sync`.

## Inheritance and Composition

**Q:** Does Rust support class inheritance like C++ or Java?  
**A:** No, Rust favors composition through traits instead of inheritance hierarchies.

**Q:** How to achieve similar effect to inheritance in Rust?  
**A:** Through composition: a struct contains other structs and implements traits.

**Q:** What is trait inheritance in Rust?  
**A:** Supertraits - a trait can require implementation of other traits.

---

## 💡 Learning Tips

1. **Practice implementation** - write several of your own traits and implement them for different types
2. **Compare approaches** - for the same problem, try both `impl Trait` and `dyn Trait`
3. **Check standard traits** - review standard library documentation
4. **Experiment with bounds** - use complex trait bounds in generic functions

## 🔗 Related Topics to Learn

- Generics and monomorphization
- Ownership and borrowing
- Standard traits (`Debug`, `Display`, `Clone`, etc.)
- Iterator pattern
- Type system in Rust
