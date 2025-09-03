# 🎴 Fiszki: `trait` w Rust

## Podstawowe pytania

**Q:** Co to jest `trait` w Rust?  
**A:** Zbiór metod (interfejs) definiujących zachowanie, które typ może zaimplementować.

**Q:** Jak zadeklarować trait w Rust?  
**A:** Używając słowa kluczowego `trait` i definiując metody bez implementacji.

**Q:** Jak zaimplementować trait dla typu?  
**A:** Używając składni `impl TraitName for TypeName { ... }`.

**Q:** Czy trait może mieć domyślne metody?  
**A:** Tak – można dostarczyć implementację domyślną, która może być nadpisana.

## Polimorfizm

**Q:** Czym różni się polimorfizm statyczny od dynamicznego w Rust?  
**A:** Statyczny używa generics i jest rozwiązywany w czasie kompilacji; dynamiczny używa `dyn Trait` i vtable.

**Q:** Jak w Rust osiąga się polimorfizm statyczny?  
**A:** Przez generics i monomorfizację (`impl Trait`, `T: Trait`).

**Q:** Jak osiąga się polimorfizm dynamiczny w Rust?  
**A:** Przez `dyn Trait` (trait objects, vtable).

**Q:** Co to jest monomorfizacja?  
**A:** Proces kompilatora, który tworzy specjalizowaną wersję funkcji dla każdego użytego typu.

## Porównania z innymi językami

**Q:** Czym różni się `trait` od klasy abstrakcyjnej w C++?  
**A:** Rust nie ma dziedziczenia, tylko kompozycję; `trait` daje statyczny i dynamiczny polimorfizm.

**Q:** Jak Rust różni się od Javy w kontekście interfejsów?  
**A:** Rust ma statyczny i dynamiczny polimorfizm + zero-cost abstractions; Java tylko dynamiczny z runtime overhead.

**Q:** Jakie są główne różnice między trait w Rust a interface w Go?  
**A:** Go ma tylko dynamiczny polimorfizm; Rust oferuje oba rodzaje + bezpieczeństwo kompilacji.

## Zaawansowane koncepcje

**Q:** Co to są associated types w trait?  
**A:** Typy powiązane z trait, które implementujący typ może określić (np. `type Item`).

**Q:** Co to są trait bounds?  
**A:** Ograniczenia na typy generyczne, które wymagają implementacji określonych traitów.

**Q:** Co to są supertraits?  
**A:** Trait, który wymaga implementacji innych traitów (np. `trait A: B` oznacza, że A wymaga B).

**Q:** Czy typ w Rust może implementować wiele `trait`?  
**A:** Tak, bez problemów diamentu jak w C++.

## Wydajność i bezpieczeństwo

**Q:** Dlaczego `trait` w Rust są „zero-cost”?  
**A:** Bo statyczny polimorfizm jest rozwiązywany w czasie kompilacji – nie ma dodatkowego kosztu w runtime.

**Q:** Jakie są koszty używania `dyn Trait`?  
**A:** Runtime overhead przez vtable lookup i zwykle wymaga heap allocation.

**Q:** Jak w Rust ogranicza się typy generyczne do określonych zachowań?  
**A:** Za pomocą **trait bounds**: `fn foo<T: Trait>(x: T)`.

**Q:** Co to jest "problem diamentu" i jak Rust go rozwiązuje?  
**A:** Problem wielokrotnego dziedziczenia; Rust unika go przez kompozycję zamiast hierarchii.

## Praktyczne zastosowania

**Q:** Jakie są praktyczne zastosowania trait bounds?  
**A:** Ograniczanie typów generycznych do tych, które mają potrzebne metody/behaviors.

**Q:** Kiedy używać `impl Trait` zamiast `dyn Trait`?  
**A:** Gdy znamy konkretne typy w czasie kompilacji i chcemy zero-cost abstractions.

**Q:** Kiedy używać `dyn Trait` zamiast generics?  
**A:** Gdy potrzebujemy kolekcji różnych typów implementujących ten sam trait w runtime.

**Q:** Co to są marker traits i jakie są przykłady?  
**A:** Traits bez metod, które oznaczają właściwości typu (np. `Send`, `Sync`, `Copy`).

## Iterator traits

**Q:** Co to jest `Iterator` trait?  
**A:** Trait definiujący sposób iterowania po kolekcji z metodą `next()`.

**Q:** Jak zaimplementować własny iterator?  
**A:** Implementując trait `Iterator` z metodą `next() -> Option<Self::Item>`.

**Q:** Co to jest `IntoIterator` trait?  
**A:** Pozwala konwertować typ na iterator za pomocą `into_iter()`.

## Operatory i standardowe traits

**Q:** Jak przeciążać operatory w Rust?  
**A:** Implementując odpowiednie traits jak `Add`, `Sub`, `Mul` itd.

**Q:** Co to jest `Display` trait?  
**A:** Używany do formatowania typu do czytelnej reprezentacji ( `{}` w `println!` ).

**Q:** Co to jest `Debug` trait?  
**A:** Używany do formatowania typu do debugowania ( `{:?}` w `println!` ).

**Q:** Jakie traits są potrzebne do używania typu w `HashMap`?  
**A:** `Eq` i `Hash` dla kluczy, opcjonalnie `PartialEq` i `PartialOrd`.

## Bezpieczeństwo wątków

**Q:** Co oznacza implementacja `Send` trait?  
**A:** Typ może być bezpiecznie przesłany między wątkami.

**Q:** Co oznacza implementacja `Sync` trait?  
**A:** Typ może być bezpiecznie współdzielony między wątkami.

**Q:** Czy wszystkie typy implementują `Send` i `Sync`?  
**A:** Większość tak, ale typy z wewnętrznymi muteksami mogą nie implementować `Sync`.

## Dziedziczenie i kompozycja

**Q:** Czy Rust wspiera dziedziczenie klas jak C++ lub Java?  
**A:** Nie, Rust stawia na kompozycję przez traits zamiast hierarchii dziedziczenia.

**Q:** Jak osiągnąć podobny efekt do dziedziczenia w Rust?  
**A:** Poprzez kompozycję: struktura zawiera inne struktury i implementuje traits.

**Q:** Co to jest trait inheritance w Rust?  
**A:** Supertraits - trait może wymagać implementacji innych traits.

---

## 💡 Wskazówki do nauki

1. **Ćwicz implementację** - napisz kilka własnych traits i zaimplementuj je dla różnych typów
2. **Porównaj podejścia** - dla tego samego problemu spróbuj `impl Trait` i `dyn Trait`
3. **Sprawdź standardowe traits** - przejrzyj dokumentację standardowej biblioteki
4. **Eksperymentuj z bounds** - używaj złożonych trait bounds w funkcjach generycznych

## 🔗 Powiązane tematy do nauki

- Generics i monomorfizacja
- Ownership i borrowing
- Standardowe traits (`Debug`, `Display`, `Clone`, etc.)
- Iterator pattern
- Type system w Rust
