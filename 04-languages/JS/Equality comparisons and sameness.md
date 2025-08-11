# Equality comparisons and sameness

## Cel

## Problem

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Zalety

## Wady

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


- [`===`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Strict_equality) — strict equality (triple equals)
- [`==`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Equality) — loose equality (double equals)
- [`Object.is()`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/is)

- Double equals (`==`) will perform a type conversion when comparing two things, and will handle `NaN`, `-0`, and `+0` specially to conform to IEEE 754 (so `NaN != NaN`, and `-0 == +0`);
- Triple equals (`===`) will do the same comparison as double equals (including the special handling for `NaN`, `-0`, and `+0`) but without type conversion; if the types differ, `false` is returned.
- `Object.is()` does no type conversion and no special handling for `NaN`, `-0`, and `+0` (giving it the same behavior as `===` except on those special numeric values).

```js
console.log("Double Equals (==):");
// Operator == sprawdza równość po konwersji typów (type coercion)

console.log(1 == "1");        // true, ponieważ "1" zostaje przekonwertowane na liczbę 1
console.log(0 == false);      // true, ponieważ false zostaje przekonwertowane na liczbę 0
console.log(NaN == NaN);      // false, NaN nie jest równe żadnemu innemu NaN
console.log(-0 == +0);        // true, -0 i +0 są uznawane za równe
console.log(null == undefined); // true, null i undefined są równe w ==

console.log("\nTriple Equals (===):");
// Operator === sprawdza równość bez konwersji typów

console.log(1 === "1");        // false, różne typy: liczba vs. string
console.log(0 === false);      // false, różne typy: liczba vs. boolean
console.log(NaN === NaN);      // false, NaN nie jest równe żadnemu innemu NaN
console.log(-0 === +0);        // true, -0 i +0 są uznawane za równe
console.log(null === undefined); // false, różne typy: null vs. undefined

console.log("\nObject.is():");
// Object.is() sprawdza równość podobnie do ===, ale ma inne podejście do -0 i NaN

console.log(Object.is(1, "1"));  // false, różne typy: liczba vs. string
console.log(Object.is(0, false));// false, różne typy: liczba vs. boolean
console.log(Object.is(NaN, NaN));  // true, Object.is() rozpoznaje, że NaN jest równe NaN
console.log(Object.is(-0, +0));  // false, Object.is() rozróżnia -0 i +0
console.log(Object.is(null, undefined)); // false, różne typy: null vs. undefined
```
