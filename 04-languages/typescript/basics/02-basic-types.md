# Basic Types in TypeScript

## Primitive Types

### string

Represents text data:

```typescript
let name: string = "John";
let message: string = `Hello, ${name}!`;
let multiline: string = `This is 
a multiline 
string`;
```

### number

Represents numeric values (integers and floats):

```typescript
let age: number = 30;
let price: number = 99.99;
let binary: number = 0b1010;  // Binary: 10
let octal: number = 0o744;    // Octal: 484
let hex: number = 0xff;       // Hex: 255
```

### boolean

Represents true/false values:

```typescript
let isActive: boolean = true;
let isComplete: boolean = false;
```

## Special Types

### null

Represents the intentional absence of a value:

```typescript
let data: null = null;
```

### undefined

Represents an uninitialized variable:

```typescript
let value: undefined = undefined;
```

### any

Disables type checking. Use sparingly:

```typescript
let flexible: any = "Hello";
flexible = 42;           // ✅ OK
flexible = true;         // ✅ OK
flexible.toUpperCase();  // ✅ No error (dangerous!)
```

**Avoid `any`**: Use `unknown` when type is truly unknown.

### unknown

Type-safe alternative to `any`:

```typescript
let userInput: unknown = getUserInput();

// ❌ Error: Object is of type 'unknown'
// let str: string = userInput;

// ✅ Type guard needed
if (typeof userInput === "string") {
  let str: string = userInput; // ✅ Now safe
}
```

### void

Indicates absence of a return value (functions):

```typescript
function logMessage(msg: string): void {
  console.log(msg);
  // No return statement
}
```

### never

Represents values that never occur:

```typescript
// Function that never returns
function throwError(message: string): never {
  throw new Error(message);
}

// Infinite loop
function infiniteLoop(): never {
  while (true) {
    // ...
  }
}

// Exhaustive checking
function assertNever(x: never): never {
  throw new Error("Unexpected value");
}
```

## Additional Types

### bigint

For arbitrarily large integers:

```typescript
let largeNumber: bigint = 9007199254740991n;
let anotherBigInt = BigInt(9007199254740991);
```

### symbol

Unique and immutable identifiers:

```typescript
const sym1: symbol = Symbol("description");
const sym2 = Symbol("description");
console.log(sym1 === sym2); // false (unique)

const obj = {
  [sym1]: "value"
};
```

## Literal Types

### String Literals

```typescript
type Status = "pending" | "approved" | "rejected";

let currentStatus: Status = "pending";
currentStatus = "approved"; // ✅ OK
currentStatus = "invalid";  // ❌ Error

// Practical example
function setTheme(theme: "light" | "dark"): void {
  document.body.className = theme;
}
```

### Numeric Literals

```typescript
type DiceRoll = 1 | 2 | 3 | 4 | 5 | 6;

function rollDice(): DiceRoll {
  return 4; // Would be random in real code
}
```

### Boolean Literals

```typescript
type FalseOrTrue = false | true;

// Actually, this is just boolean
let value: boolean = true; // or false
```

## Type Annotations

### Explicit Type Annotation

```typescript
let name: string = "John";
let age: number = 30;
let isActive: boolean = true;
```

### Type Inference

TypeScript infers types automatically:

```typescript
// Type: string (inferred)
let name = "John";

// Type: number (inferred)
let age = 30;

// Type: boolean (inferred)
let isActive = true;
```

### When to Use Explicit vs Inferred?

**Use Inference:**
```typescript
// Clear from context
let count = 0;
let greeting = "Hello";
```

**Use Explicit:**
```typescript
// Initialize as undefined
let name: string | undefined;

// Override inference
let value: number = getValue(); // If getValue() returns any
```

## Type Assertions

Force TypeScript to treat a value as a specific type:

```typescript
// Angle-bracket syntax
let someValue: unknown = "This is a string";
let strLength: number = (<string>someValue).length;

// As-syntax (preferred in TSX)
let someValue2: unknown = "This is a string";
let strLength2: number = (someValue2 as string).length;

// Double assertion
let input = getInput(); // any
let value = input as unknown as string;
```

## Examples

### String Examples

```typescript
let firstName: string = "John";
let lastName: string = "Doe";
let fullName: string = `${firstName} ${lastName}`;

console.log(fullName); // "John Doe"
```

### Number Examples

```typescript
let decimal: number = 6;
let hex: number = 0xf00d;
let binary: number = 0b1010;
let octal: number = 0o744;
let floatingPoint: number = 3.14159;
```

### Boolean Examples

```typescript
let isDone: boolean = false;
let isActive: boolean = true;
let hasPermission: boolean = checkPermissions();
```

### Null and Undefined

```typescript
let nullValue: null = null;
let undefinedValue: undefined = undefined;

// Both are subtypes of other types
let num: number = null;      // ✅ OK (unless strictNullChecks)
let str: string = undefined; // ✅ OK (unless strictNullChecks)
```

### Never Type in Practice

```typescript
// Function that throws
function error(message: string): never {
  throw new Error(message);
}

// Infinite loop
function endless(): never {
  while (true) {}
}

// Type narrowing with never
type Action = 
  | { type: "increment"; amount: number }
  | { type: "decrement"; amount: number };

function handleAction(action: Action) {
  switch (action.type) {
    case "increment":
      // action.amount is number
      break;
    case "decrement":
      // action.amount is number
      break;
    default:
      // TypeScript knows this is impossible
      const _exhaustive: never = action;
  }
}
```

## Common Patterns

### Optional Properties

```typescript
interface User {
  name: string;
  age?: number; // Optional
}

let user: User = { name: "John" }; // age is optional
```

### Union Types

```typescript
let id: number | string;
id = 101;
id = "ABC123";
```

### Type Guard Pattern

```typescript
function isString(value: unknown): value is string {
  return typeof value === "string";
}

let value: unknown = getValue();
if (isString(value)) {
  // TypeScript knows value is string here
  value.toUpperCase(); // ✅ OK
}
```

## Best Practices

1. **Use specific types** instead of `any`
2. **Prefer type inference** when obvious
3. **Use `unknown`** instead of `any` for truly unknown types
4. **Enable strict null checks** for safer code
5. **Use literal types** for fixed sets of values
6. **Use `never`** for impossible states and exhaustive checks

## Related Topics

- [Variables and Constants](./03-variables-constants.md)
- [Functions](./04-functions.md)
- [Union and Intersection Types](../types/union-intersection.md)
