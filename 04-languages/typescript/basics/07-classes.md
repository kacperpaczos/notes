# Classes in TypeScript

## Class Basics

### Basic Class Syntax

```typescript
class Person {
  name: string;
  age: number;
  
  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
  
  greet(): string {
    return `Hello, my name is ${this.name} and I'm ${this.age} years old.`;
  }
}

const person = new Person("John", 30);
console.log(person.greet());  // "Hello, my name is John and I'm 30 years old."
```

### Shorthand Constructor

```typescript
// Instead of:
class Person1 {
  name: string;
  age: number;
  
  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
}

// You can write:
class Person2 {
  constructor(public name: string, public age: number) {
    // Automatic assignment
  }
}

const person = new Person2("John", 30);
```

## Access Modifiers

### Public (Default)

```typescript
class Person {
  public name: string;  // public is optional
  public age: number;
  
  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
}

const person = new Person("John", 30);
console.log(person.name);  // ✅ Accessible
```

### Private

```typescript
class BankAccount {
  private balance: number = 0;
  
  deposit(amount: number): void {
    if (amount > 0) {
      this.balance += amount;
    }
  }
  
  getBalance(): number {
    return this.balance;
  }
}

const account = new BankAccount();
account.deposit(100);
// account.balance;  // ❌ Error: Property 'balance' is private
console.log(account.getBalance());  // ✅ OK: 100
```

### Protected

```typescript
class Animal {
  protected name: string;
  
  constructor(name: string) {
    this.name = name;
  }
  
  protected makeSound(): void {
    console.log("Some sound");
  }
}

class Dog extends Animal {
  constructor(name: string) {
    super(name);
  }
  
  public bark(): void {
    console.log(`${this.name} barks!`);  // ✅ Can access protected name
    this.makeSound();  // ✅ Can access protected method
  }
}

const dog = new Dog("Rex");
dog.bark();
// dog.name;  // ❌ Error: Property 'name' is protected
```

### Readonly

```typescript
class Point {
  readonly x: number;
  readonly y: number;
  
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }
}

const point = new Point(10, 20);
// point.x = 30;  // ❌ Error: Cannot assign to 'x' because it is read-only
```

## Inheritance

### Basic Inheritance

```typescript
class Animal {
  protected name: string;
  
  constructor(name: string) {
    this.name = name;
  }
  
  move(distance: number): void {
    console.log(`${this.name} moved ${distance}m.`);
  }
}

class Dog extends Animal {
  constructor(name: string) {
    super(name);  // Must call super()
  }
  
  bark(): void {
    console.log(`${this.name} barks!`);
  }
}

const dog = new Dog("Rex");
dog.move(10);  // "Rex moved 10m."
dog.bark();     // "Rex barks!"
```

### Method Overriding

```typescript
class Animal {
  protected name: string;
  
  constructor(name: string) {
    this.name = name;
  }
  
  makeSound(): void {
    console.log("Some sound");
  }
}

class Dog extends Animal {
  constructor(name: string) {
    super(name);
  }
  
  override makeSound(): void {
    console.log("Woof!");
  }
}

const dog = new Dog("Rex");
dog.makeSound();  // "Woof!"
```

## Static Members

### Static Properties and Methods

```typescript
class Counter {
  private static count: number = 0;
  
  constructor() {
    Counter.count++;
  }
  
  static getCount(): number {
    return Counter.count;
  }
}

const c1 = new Counter();
const c2 = new Counter();
const c3 = new Counter();

console.log(Counter.getCount());  // 3
```

### Static Class Members Example

```typescript
class MathHelper {
  static PI: number = 3.14159;
  
  static add(a: number, b: number): number {
    return a + b;
  }
  
  static multiply(a: number, b: number): number {
    return a * b;
  }
}

console.log(MathHelper.PI);            // 3.14159
console.log(MathHelper.add(5, 3));     // 8
console.log(MathHelper.multiply(4, 2)); // 8
```

## Abstract Classes

```typescript
abstract class Shape {
  abstract getArea(): number;
  abstract getPerimeter(): number;
  
  describe(): string {
    return `Shape with area ${this.getArea()} and perimeter ${this.getPerimeter()}`;
  }
}

class Circle extends Shape {
  constructor(private radius: number) {
    super();
  }
  
  getArea(): number {
    return Math.PI * this.radius ** 2;
  }
  
  getPerimeter(): number {
    return 2 * Math.PI * this.radius;
  }
}

const circle = new Circle(5);
console.log(circle.describe());
```

## Interfaces with Classes

### Implementing Interfaces

```typescript
interface Drivable {
  start(): void;
  stop(): void;
  accelerate(): void;
}

class Car implements Drivable {
  private isRunning: boolean = false;
  
  start(): void {
    this.isRunning = true;
    console.log("Car started");
  }
  
  stop(): void {
    this.isRunning = false;
    console.log("Car stopped");
  }
  
  accelerate(): void {
    if (this.isRunning) {
      console.log("Accelerating");
    }
  }
}
```

### Multiple Interface Implementation

```typescript
interface Flyable {
  fly(): void;
}

interface Swimmable {
  swim(): void;
}

class Duck implements Flyable, Swimmable {
  fly(): void {
    console.log("Duck flies");
  }
  
  swim(): void {
    console.log("Duck swims");
  }
}
```

## Getters and Setters

### Property Accessors

```typescript
class Person {
  private _age: number = 0;
  
  get age(): number {
    return this._age;
  }
  
  set age(value: number) {
    if (value >= 0 && value <= 150) {
      this._age = value;
    } else {
      throw new Error("Invalid age");
    }
  }
}

const person = new Person();
person.age = 30;     // Uses setter
console.log(person.age);  // 30 (uses getter)

// person.age = -5;  // Error: Invalid age
```

## Parameter Properties

```typescript
// Long way
class Person1 {
  private name: string;
  private age: number;
  
  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
}

// Short way with parameter properties
class Person2 {
  constructor(
    private name: string,
    private age: number
  ) {
    // Automatic assignment
  }
  
  getName(): string {
    return this.name;
  }
}
```

## Real-World Examples

### Database Model

```typescript
class User {
  constructor(
    private id: string,
    public name: string,
    public email: string,
    private createdAt: Date
  ) {}
  
  getId(): string {
    return this.id;
  }
  
  getCreatedDate(): string {
    return this.createdAt.toISOString();
  }
  
  updateEmail(newEmail: string): void {
    if (this.isValidEmail(newEmail)) {
      this.email = newEmail;
    }
  }
  
  private isValidEmail(email: string): boolean {
    return email.includes("@");
  }
}

const user = new User("123", "John", "john@example.com", new Date());
```

### Repository Pattern

```typescript
interface Repository<T> {
  save(entity: T): void;
  findById(id: string): T | null;
  findAll(): T[];
}

class UserRepository implements Repository<User> {
  private users: Map<string, User> = new Map();
  
  save(user: User): void {
    this.users.set(user.getId(), user);
  }
  
  findById(id: string): User | null {
    return this.users.get(id) || null;
  }
  
  findAll(): User[] {
    return Array.from(this.users.values());
  }
}
```

## Best Practices

1. **Use private/protected** for encapsulation
2. **Use readonly** for immutable properties
3. **Prefer composition over inheritance** when possible
4. **Use abstract classes** for base implementations
5. **Implement interfaces** for contracts
6. **Use getters/setters** for computed properties
7. **Avoid deep inheritance hierarchies**
8. **Use parameter properties** for cleaner constructors

## Related Topics

- [Interfaces](../types/interfaces.md)
- [Generics](../generics/index.md)
- [Objects](./06-objects.md)
