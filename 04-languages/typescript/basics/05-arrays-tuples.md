# Arrays and Tuples in TypeScript

## Arrays

### Array Type Syntax

```typescript
// Method 1: Element type followed by []
let list1: number[] = [1, 2, 3, 4, 5];

// Method 2: Array generic type
let list2: Array<number> = [1, 2, 3, 4, 5];

// Both are equivalent
```

### String Arrays

```typescript
let fruits: string[] = ["apple", "banana", "orange"];

// TypeScript infers: string[]
let colors = ["red", "green", "blue"];
```

### Mixed Arrays

```typescript
// Array of multiple types
let mixed: (string | number)[] = ["hello", 42, "world", 100];

// Array of specific types
let userInfo: [string, number][] = [
  ["John", 30],
  ["Jane", 25]
];
```

### Readonly Arrays

```typescript
let readonlyArr: readonly number[] = [1, 2, 3];
// or
let readonlyArr2: ReadonlyArray<number> = [1, 2, 3];

// readonlyArr[0] = 10;  // ❌ Error: Index signature is readonly
// readonlyArr.push(4);  // ❌ Error: Property 'push' doesn't exist
```

## Array Methods

### Common Array Methods

```typescript
let numbers: number[] = [1, 2, 3, 4, 5];

// map - Transform each element
let doubled = numbers.map(n => n * 2);  // [2, 4, 6, 8, 10]

// filter - Select elements
let evens = numbers.filter(n => n % 2 === 0);  // [2, 4]

// reduce - Accumulate values
let sum = numbers.reduce((acc, n) => acc + n, 0);  // 15

// find - Find first matching element
let found = numbers.find(n => n > 3);  // 4

// forEach - Execute for each element
numbers.forEach(n => console.log(n));
```

### Typed Array Methods

```typescript
interface User {
  name: string;
  age: number;
}

let users: User[] = [
  { name: "John", age: 30 },
  { name: "Jane", age: 25 }
];

// Type-safe map
let names = users.map((user: User): string => user.name);

// Type-safe filter
let adults = users.filter((user: User): boolean => user.age >= 18);
```

## Tuples

### Basic Tuples

```typescript
// Tuple - fixed length, ordered types
let user: [string, number] = ["John", 30];

// Accessing tuple elements
let name = user[0];  // string: "John"
let age = user[1];   // number: 30

// ❌ Error: incorrect types
// let wrong: [string, number] = [30, "John"];

// ❌ Error: incorrect length
// let wrong: [string, number] = ["John", 30, "extra"];
```

### Named Tuple Elements (TypeScript 4.0+)

```typescript
let point: [x: number, y: number] = [10, 20];

function getCoordinates(): [lat: number, lng: number] {
  return [40.7128, -74.0060];
}
```

### Optional Tuple Elements

```typescript
let optional: [string, number?] = ["hello"];
let optional2: [string, number?] = ["hello", 42];

let many: [string, number, boolean?, number?] = ["a", 1];
let many2: [string, number, boolean?, number?] = ["a", 1, true, 2];
```

### Rest Elements in Tuples

```typescript
// At the end
type Numbers = [string, ...number[]];
let n1: Numbers = ["hello"];  // ✅ OK
let n2: Numbers = ["hello", 1];  // ✅ OK
let n3: Numbers = ["hello", 1, 2, 3];  // ✅ OK

// Anywhere (TypeScript 4.2+)
type More = [...boolean[], string, ...number[]];
let more: More = [true, false, "hello", 1, 2];
```

### Tuple with Labels

```typescript
type Person = [name: string, age: number, city: string];

function createPerson(): Person {
  return ["John", 30, "New York"];
}
```

## Practical Examples

### API Response Type

```typescript
// HTTP Status Code, Status Message, Data
type ApiResponse<T> = [number, string, T?];

function fetchData(): ApiResponse<{ users: string[] }> {
  // ... fetch logic
  return [200, "Success", { users: ["John", "Jane"] }];
}

const [status, message, data] = fetchData();
if (status === 200 && data) {
  console.log(data.users);
}
```

### Database Row

```typescript
type UserRow = [id: number, name: string, email: string, active: boolean];

function getUserById(id: number): UserRow | null {
  // ... database logic
  return [1, "John", "john@example.com", true];
}
```

### CSV Parsing

```typescript
function parseCSVLine(line: string): [string, number, boolean] {
  const parts = line.split(",");
  return [parts[0], parseInt(parts[1]), parts[2] === "true"];
}

const row = parseCSVLine("John,30,true");
// row: [string, number, boolean]
```

### Readonly Tuples

```typescript
const readonlyTuple: readonly [string, number] = ["hello", 42];

// readonlyTuple[0] = "world";  // ❌ Error
```

## Array vs Tuple vs Readonly Array

```typescript
// Array - variable length, same type
let arr: number[] = [1, 2, 3, 4, 5];

// Tuple - fixed length, specific types
let tuple: [number, string] = [1, "hello"];

// Readonly Array - immutable array
let readonlyArr: readonly number[] = [1, 2, 3];

// Readonly Tuple - immutable tuple
let readonlyTuple: readonly [number, string] = [1, "hello"];
```

## Destructuring Arrays and Tuples

```typescript
// Array destructuring
let arr = [1, 2, 3, 4, 5];
let [first, second, ...rest] = arr;
// first: number = 1
// second: number = 2
// rest: number[] = [3, 4, 5]

// Tuple destructuring
let tuple: [string, number] = ["John", 30];
let [name, age] = tuple;
// name: string = "John"
// age: number = 30

// Skip elements
let [first, , third] = [1, 2, 3];
// first: 1, third: 3

// Default values
let [a = 10, b = 20] = [1];
// a: 1, b: 20
```

## Type Assertions with Arrays

```typescript
// Assert as array type
let unknownValue: unknown = [1, 2, 3];
let numbers = unknownValue as number[];

// Assert as tuple
let arrayData = [1, "hello"] as [number, string];
```

## Common Patterns

### Zipping Arrays

```typescript
function zip<T, U>(a: T[], b: U[]): [T, U][] {
  const minLength = Math.min(a.length, b.length);
  const result: [T, U][] = [];
  
  for (let i = 0; i < minLength; i++) {
    result.push([a[i], b[i]]);
  }
  
  return result;
}

let names = ["John", "Jane"];
let ages = [30, 25];
let zipped = zip(names, ages);
// zipped: [string, number][] = [["John", 30], ["Jane", 25]]
```

### Chunking Arrays

```typescript
function chunk<T>(arr: T[], size: number): T[][] {
  const chunks: T[][] = [];
  
  for (let i = 0; i < arr.length; i += size) {
    chunks.push(arr.slice(i, i + size));
  }
  
  return chunks;
}

let nums = [1, 2, 3, 4, 5, 6];
let chunks = chunk(nums, 2);
// chunks: [[1, 2], [3, 4], [5, 6]]
```

### Sorting with Type Safety

```typescript
interface Product {
  name: string;
  price: number;
}

let products: Product[] = [
  { name: "Apple", price: 1.5 },
  { name: "Banana", price: 2.0 }
];

// Type-safe sort
products.sort((a, b) => a.price - b.price);
```

## Best Practices

1. **Use arrays** for homogeneous data of variable length
2. **Use tuples** for fixed-length, heterogeneous data
3. **Use readonly** when data shouldn't be modified
4. **Prefer type inference** for simple cases
5. **Use generic arrays** for reusable code
6. **Label tuple elements** for better readability (TypeScript 4.0+)

## Related Topics

- [Basic Types](./02-basic-types.md)
- [Objects](./06-objects.md)
- [Generics](../generics/index.md)
