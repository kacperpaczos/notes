# Functions in TypeScript

## Function Declarations

### Named Functions

```typescript
function greet(name: string): string {
  return `Hello, ${name}!`;
}

console.log(greet("John")); // "Hello, John!"
```

### Function Parameters

#### Required Parameters

```typescript
function add(a: number, b: number): number {
  return a + b;
}
```

#### Optional Parameters

```typescript
function greet(name: string, age?: number): string {
  if (age) {
    return `Hello, ${name}! You are ${age} years old.`;
  }
  return `Hello, ${name}!`;
}

greet("John");        // ✅ OK
greet("John", 30);    // ✅ OK
```

#### Default Parameters

```typescript
function greet(name: string, greeting: string = "Hello"): string {
  return `${greeting}, ${name}!`;
}

greet("John");              // "Hello, John!"
greet("John", "Hi");        // "Hi, John!"
greet("John", undefined);   // "Hello, John!" (uses default)
```

#### Rest Parameters

```typescript
function sum(...numbers: number[]): number {
  return numbers.reduce((total, num) => total + num, 0);
}

sum(1, 2, 3);           // 6
sum(1, 2, 3, 4, 5);     // 15
sum();                  // 0
```

## Arrow Functions

### Basic Syntax

```typescript
// Named function
function add(a: number, b: number): number {
  return a + b;
}

// Arrow function equivalent
const add = (a: number, b: number): number => {
  return a + b;
};

// Implicit return
const add = (a: number, b: number): number => a + b;

// Single parameter (no parentheses)
const double = (x: number): number => x * 2;

// No parameters
const getRandom = (): number => Math.random();
```

### Common Use Cases

```typescript
// Array methods
const numbers = [1, 2, 3, 4, 5];
const doubled = numbers.map(n => n * 2);
const evens = numbers.filter(n => n % 2 === 0);
const sum = numbers.reduce((acc, n) => acc + n, 0);

// Callbacks
setTimeout(() => {
  console.log("Delayed");
}, 1000);

// Event handlers
button.addEventListener("click", (e) => {
  console.log("Clicked");
});
```

## Function Types

### Function Type Annotations

```typescript
// Named function type
type AddFunction = (a: number, b: number) => number;

const add: AddFunction = (a, b) => a + b;

// Function as parameter
function doMath(operation: (x: number, y: number) => number): number {
  return operation(5, 3);
}

doMath((a, b) => a + b);  // 8
doMath((a, b) => a * b);  // 15
```

### Typing Callbacks

```typescript
function processData(
  data: any[],
  callback: (item: any, index: number) => void
): void {
  data.forEach(callback);
}

processData([1, 2, 3], (item, index) => {
  console.log(`${index}: ${item}`);
});
```

## Return Types

### Explicit Return Types

```typescript
function calculate(x: number): number {
  return x * 2;
}

function greet(name: string): string {
  return `Hello, ${name}!`;
}

function log(message: string): void {
  console.log(message);
  // No return needed
}

function throwError(message: string): never {
  throw new Error(message);
  // Never returns
}
```

### Inferred Return Types

```typescript
// TypeScript infers: (x: number) => number
function double(x: number) {
  return x * 2;
}
```

## Function Overloading

### Basic Overloading

```typescript
function combine(x: number, y: number): number;
function combine(x: string, y: string): string;
function combine(x: number | string, y: number | string): number | string {
  if (typeof x === "number" && typeof y === "number") {
    return x + y;
  }
  return x.toString() + y.toString();
}

combine(1, 2);        // 3 (number)
combine("a", "b");    // "ab" (string)
```

### Complex Overloading

```typescript
interface Options {
  timeout?: number;
  retries?: number;
}

function request(url: string): Promise<string>;
function request(url: string, method: string): Promise<string>;
function request(url: string, options: Options): Promise<string>;
function request(
  url: string,
  methodOrOptions?: string | Options
): Promise<string> {
  // Implementation
  return Promise.resolve("data");
}

request("/api");                              // ✅ OK
request("/api", "GET");                       // ✅ OK
request("/api", { timeout: 5000 });          // ✅ OK
```

## Higher-Order Functions

### Functions Returning Functions

```typescript
function createMultiplier(factor: number): (x: number) => number {
  return (x: number) => x * factor;
}

const double = createMultiplier(2);
const triple = createMultiplier(3);

console.log(double(5));  // 10
console.log(triple(5));  // 15
```

### Partial Application

```typescript
function add(a: number, b: number, c: number): number {
  return a + b + c;
}

function partial<T extends (...args: any[]) => any>(
  func: T,
  ...boundArgs: any[]
): (...args: any[]) => any {
  return (...remainingArgs: any[]) => 
    func(...boundArgs, ...remainingArgs);
}

const add5 = partial(add, 5);
const result = add5(3, 2); // 5 + 3 + 2 = 10
```

## this Binding

### Regular Functions

```typescript
const obj = {
  name: "Object",
  regularFunction: function() {
    console.log(this.name); // 'this' refers to obj
  }
};

obj.regularFunction(); // "Object"
```

### Arrow Functions (Lexical this)

```typescript
const obj = {
  name: "Object",
  regularFunction: function() {
    setTimeout(function() {
      // 'this' is undefined or window
      // console.log(this.name); // Error
    }, 100);
  },
  arrowFunction: function() {
    setTimeout(() => {
      // 'this' refers to obj (lexical binding)
      console.log(this.name); // "Object"
    }, 100);
  }
};
```

## Common Patterns

### Currying

```typescript
function curry(fn: Function): Function {
  return function curried(...args: any[]): any {
    if (args.length >= fn.length) {
      return fn.apply(this, args);
    } else {
      return function(...args2: any[]) {
        return curried.apply(this, args.concat(args2));
      };
    }
  };
}

function add(a: number, b: number): number {
  return a + b;
}

const curriedAdd = curry(add);
const add5 = curriedAdd(5);
console.log(add5(3)); // 8
```

### Memoization

```typescript
function memoize<T extends (...args: any[]) => any>(
  fn: T
): T {
  const cache = new Map();
  
  return ((...args: any[]) => {
    const key = JSON.stringify(args);
    
    if (cache.has(key)) {
      return cache.get(key);
    }
    
    const result = fn(...args);
    cache.set(key, result);
    return result;
  }) as T;
}

const expensiveFunction = (n: number): number => {
  // Expensive computation
  return n * n;
};

const memoized = memoize(expensiveFunction);
memoized(5);  // Computes
memoized(5);  // Returns cached result
```

### Function Composition

```typescript
function compose<A, B, C>(
  f: (b: B) => C,
  g: (a: A) => B
): (a: A) => C {
  return (a: A) => f(g(a));
}

const double = (x: number) => x * 2;
const addOne = (x: number) => x + 1;

const doubleThenAddOne = compose(addOne, double);
console.log(doubleThenAddOne(5)); // 11 (5 * 2 + 1)
```

## Best Practices

1. **Prefer arrow functions** for short, simple functions
2. **Use explicit return types** for complex functions
3. **Leverage function overloading** for flexible APIs
4. **Use rest parameters** instead of arguments object
5. **Define this types** explicitly when needed
6. **Prefer function declarations** for hoisting needs

## Related Topics

- [Type Aliases](../types/type-aliases.md)
- [Generics](../generics/index.md)
- [Async/Await](../advanced/async-patterns.md)
