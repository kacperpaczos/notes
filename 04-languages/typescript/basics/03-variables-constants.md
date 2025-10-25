# Variables and Constants in TypeScript

## Variable Declarations

### let

Block-scoped variable declaration:

```typescript
function example() {
  let x = 10;
  
  if (true) {
    let x = 20; // Different x
    console.log(x); // 20
  }
  
  console.log(x); // 10
}
```

### const

Block-scoped constant declaration:

```typescript
const PI = 3.14159;
const name = "John";

// ❌ Error: Cannot reassign const
// PI = 3.14;

// Const with objects
const user = {
  name: "John",
  age: 30
};

// ✅ OK: Can modify object properties
user.age = 31;

// ❌ Error: Cannot reassign the object
// user = { name: "Jane", age: 25 };
```

### var (Avoid in Modern TypeScript)

Function-scoped, legacy syntax:

```typescript
function example() {
  var x = 10;
  
  if (true) {
    var x = 20; // Same x
    console.log(x); // 20
  }
  
  console.log(x); // 20 (not 10!)
}
```

**Why avoid `var`?**
- Hoisting issues
- No block scoping
- Can be redeclared
- `let` and `const` fix these issues

## Differences: let vs const vs var

| Feature | var | let | const |
|---------|-----|-----|-------|
| Scope | Function | Block | Block |
| Hoisted | Yes | Temporal dead zone | Temporal dead zone |
| Can reassign | Yes | Yes | No |
| Can redeclare | Yes | No | No |
| Temporal dead zone | No | Yes | Yes |

## Type Inference

### Automatic Type Inference

```typescript
// Type inferred as string
let message = "Hello";

// Type inferred as number
let count = 42;

// Type inferred as boolean
let isActive = true;

// Type inferred as array of numbers
let numbers = [1, 2, 3];

// Type inferred as object
let user = {
  name: "John",
  age: 30
};
```

### Explicit Type Annotation

```typescript
// When to use explicit types:

// 1. Initialize as undefined
let name: string | undefined;

// 2. Override inference
let value: number = getValue(); // If getValue() returns any

// 3. Complex types
let coordinates: { x: number; y: number } = { x: 0, y: 0 };

// 4. Function parameters
function greet(name: string): string {
  return `Hello, ${name}`;
}

// 5. Complex return types
function parseJson(json: string): { success: boolean; data?: any } {
  // ...
}
```

## Const Assertions

Force readonly behavior:

```typescript
// Regular tuple
let point = [10, 20]; // Type: number[]

// Const assertion
let point2 = [10, 20] as const; // Type: readonly [10, 20]

// point2[0] = 30; // ❌ Error: Cannot assign to readonly

// With object
let user = {
  name: "John",
  role: "admin"
} as const;

// user.name = "Jane"; // ❌ Error: Cannot assign to readonly
```

## Destructuring

### Array Destructuring

```typescript
let [first, second, third] = [1, 2, 3];
// first = 1, second = 2, third = 3

let [first, , third] = [1, 2, 3];
// first = 1, third = 3

let [first, ...rest] = [1, 2, 3, 4];
// first = 1, rest = [2, 3, 4]

let [first = 10] = [];
// first = 10 (default value)
```

### Object Destructuring

```typescript
let { name, age } = { name: "John", age: 30 };
// name = "John", age = 30

let { name: personName, age: personAge } = { name: "John", age: 30 };
// personName = "John", personAge = 30

let { name, ...other } = { name: "John", age: 30, role: "admin" };
// name = "John", other = { age: 30, role: "admin" }

let { name = "Anonymous", age = 0 } = { name: "John" };
// name = "John", age = 0
```

## Practical Examples

### Variable Scoping

```typescript
function scopeExample() {
  let functionScoped = "I'm accessible in entire function";
  
  if (true) {
    let blockScoped = "I'm only in this block";
    console.log(functionScoped); // ✅ OK
  }
  
  // console.log(blockScoped); // ❌ Error: blockScoped is not defined
}
```

### Constants with Objects

```typescript
const config = {
  apiUrl: "https://api.example.com",
  timeout: 5000,
  retries: 3
};

// ✅ Modify properties
config.timeout = 6000;

// ❌ Reassign object
// config = { ... };
```

### Destructuring in Functions

```typescript
// Function parameters
function greet({ name, age }: { name: string; age: number }): void {
  console.log(`${name} is ${age} years old`);
}

greet({ name: "John", age: 30 });

// With defaults
function createUser({ 
  name, 
  age = 18, 
  role = "user" 
}: { 
  name: string; 
  age?: number; 
  role?: string 
}): User {
  return { name, age, role };
}
```

### Rest and Spread

```typescript
// Rest
let [first, ...rest] = [1, 2, 3, 4, 5];

// Spread
let numbers = [1, 2, 3];
let moreNumbers = [...numbers, 4, 5];

// Object spread
let user = { name: "John", age: 30 };
let updatedUser = { ...user, age: 31 };
```

## Best Practices

1. **Use `const` by default**, `let` when reassignment is needed
2. **Avoid `var`** completely
3. **Prefer type inference** unless explicit type adds clarity
4. **Use destructuring** for cleaner code
5. **Use `as const`** for literal types and readonly arrays
6. **Declare variables in the narrowest scope** possible

## Common Patterns

### Typed Destructuring

```typescript
interface Point {
  x: number;
  y: number;
}

let point: Point = { x: 10, y: 20 };
let { x, y } = point;
```

### Conditional Assignment

```typescript
let value = someCondition ? "option1" : "option2";

let result = getData() ?? "default";
```

### Multiple Returns Destructuring

```typescript
function getCoordinates(): [number, number] {
  return [10, 20];
}

let [x, y] = getCoordinates();
```

## Related Topics

- [Basic Types](./02-basic-types.md)
- [Functions](./04-functions.md)
- [Objects](./06-objects.md)
