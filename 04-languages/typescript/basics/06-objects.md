# Objects in TypeScript

## Object Type Annotations

### Inline Object Types

```typescript
// Inline type annotation
let user: { name: string; age: number } = {
  name: "John",
  age: 30
};

// TypeScript infers object types
let point = { x: 10, y: 20 };
// Type: { x: number; y: number }
```

### Optional Properties

```typescript
let user: { 
  name: string; 
  age: number; 
  email?: string  // Optional
} = {
  name: "John",
  age: 30
  // email is optional
};

user.email = "john@example.com";  // ✅ OK
```

### Readonly Properties

```typescript
let config: { 
  readonly apiKey: string;
  retries: number;
} = {
  apiKey: "secret123",
  retries: 3
};

config.retries = 5;        // ✅ OK
// config.apiKey = "new";  // ❌ Error: Cannot assign to readonly
```

## Index Signatures

### Index Signatures for Dynamic Properties

```typescript
// Object with string keys and number values
let scores: { [key: string]: number } = {
  math: 90,
  science: 85,
  history: 88
};

scores["english"] = 92;  // ✅ OK
scores.math = 95;        // ✅ OK

// Only string or number keys allowed
let letters: { [key: number]: string } = {
  1: "one",
  2: "two"
};
```

### Known Properties with Index Signature

```typescript
interface StringArray {
  [index: number]: string;  // Index signature
  length: number;            // Known property
}

let arr: StringArray = {
  0: "first",
  1: "second",
  length: 2
};
```

## Object Methods

### Methods in Objects

```typescript
let calculator = {
  value: 0,
  
  add(n: number): void {
    this.value += n;
  },
  
  subtract(n: number): void {
    this.value -= n;
  },
  
  getValue(): number {
    return this.value;
  }
};

calculator.add(10);
calculator.subtract(5);
console.log(calculator.getValue());  // 5
```

### Typed Methods

```typescript
let user = {
  name: "John",
  greet: function(message: string): string {
    return `${message}, ${this.name}!`;
  },
  
  // Arrow function (lexical this)
  greetArrow: (message: string): string => {
    // Can't access this.name here
    return `${message}!`;
  }
};
```

## Nested Objects

### Nested Object Types

```typescript
let user: {
  name: string;
  age: number;
  address: {
    street: string;
    city: string;
    zipCode: number;
  }
} = {
  name: "John",
  age: 30,
  address: {
    street: "123 Main St",
    city: "New York",
    zipCode: 10001
  }
};
```

### Nested Optional Properties

```typescript
let user: {
  name: string;
  profile?: {
    bio?: string;
    website?: string;
  }
} = {
  name: "John"
  // profile is optional
};

// Optional chaining (TypeScript 3.7+)
let bio = user.profile?.bio;  // undefined if profile doesn't exist
```

## Object Destructuring

### Basic Destructuring

```typescript
let user = { name: "John", age: 30, city: "NYC" };

let { name, age } = user;
// name: "John", age: 30

// Renaming
let { name: personName, age: personAge } = user;
// personName: "John", personAge: 30
```

### Destructuring with Defaults

```typescript
let user = { name: "John" };

let { name, age = 0 } = user;
// name: "John", age: 0

// Nested destructuring
let config = {
  server: {
    host: "localhost",
    port: 3000
  }
};

let {
  server: { host, port }
} = config;
```

### Rest in Destructuring

```typescript
let user = { name: "John", age: 30, city: "NYC", role: "admin" };

let { name, ...otherProps } = user;
// name: "John"
// otherProps: { age: 30, city: "NYC", role: "admin" }
```

## Spread Operator

### Object Spread

```typescript
let defaults = { theme: "light", fontSize: 14 };
let user = { ...defaults, theme: "dark" };
// user: { theme: "dark", fontSize: 14 }

// Override properties
let config = { ...defaults, ...{ fontSize: 16 } };
```

### Partial Updates

```typescript
function updateUser(user: User, updates: Partial<User>): User {
  return { ...user, ...updates };
}

let user: User = { name: "John", age: 30 };
let updated = updateUser(user, { age: 31 });
// updated: { name: "John", age: 31 }
```

## Object as Records

### Record Utility Type

```typescript
// Record<K, T> - object with keys of type K and values of type T
let scores: Record<string, number> = {
  math: 90,
  science: 85
};

// Specific key types
type Status = "pending" | "approved" | "rejected";
let statuses: Record<Status, number> = {
  pending: 0,
  approved: 1,
  rejected: 2
};
```

## Property Checking

### in Operator

```typescript
type Person = { name: string; age: number };

function getProperty(obj: Person, key: keyof Person) {
  if (key in obj) {
    return obj[key];
  }
  return undefined;
}

let person = { name: "John", age: 30 };
let name = getProperty(person, "name");  // ✅ OK
// let invalid = getProperty(person, "email");  // ❌ Error
```

### hasOwnProperty

```typescript
let obj = { name: "John" };

if (obj.hasOwnProperty("name")) {
  console.log(obj.name);
}
```

## Type Narrowing with Objects

```typescript
interface Admin {
  type: "admin";
  permissions: string[];
}

interface User {
  type: "user";
  role: string;
}

type Person = Admin | User;

function handlePerson(person: Person) {
  if (person.type === "admin") {
    // TypeScript knows person is Admin
    console.log(person.permissions);
  } else {
    // TypeScript knows person is User
    console.log(person.role);
  }
}
```

## Complex Examples

### Configuration Objects

```typescript
interface DatabaseConfig {
  host: string;
  port: number;
  credentials?: {
    username: string;
    password: string;
  };
  options?: {
    ssl?: boolean;
    retries?: number;
  };
}

function connect(config: DatabaseConfig): void {
  console.log(`Connecting to ${config.host}:${config.port}`);
  
  if (config.credentials) {
    console.log(`Authenticating as ${config.credentials.username}`);
  }
}

connect({
  host: "localhost",
  port: 5432,
  credentials: {
    username: "admin",
    password: "secret"
  }
});
```

### API Response Handling

```typescript
type ApiResponse<T> = {
  success: boolean;
  data: T;
  error?: string;
  timestamp: number;
};

async function fetchUser(id: number): Promise<ApiResponse<{ id: number; name: string }>> {
  // ... API call
  return {
    success: true,
    data: { id, name: "John" },
    timestamp: Date.now()
  };
}
```

## Best Practices

1. **Use interfaces** for reusable object types (see Interfaces)
2. **Use type aliases** for unions and complex types
3. **Prefer readonly** for immutable properties
4. **Use optional properties** (?)
5. **Leverage object destructuring** for clean code
6. **Use Record<K, T>** for dynamic objects
7. **Enable strict null checks** for safer code

## Related Topics

- [Interfaces](../types/interfaces.md)
- [Type Aliases](../types/type-aliases.md)
- [Classes](./07-classes.md)
