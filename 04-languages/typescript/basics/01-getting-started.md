# Getting Started with TypeScript

## What is TypeScript?

TypeScript is a statically typed superset of JavaScript that adds:
- **Type annotations** for variables, functions, and objects
- **Compile-time type checking** to catch errors early
- **Better IDE support** with autocomplete and refactoring
- **Modern JavaScript features** with backward compatibility

## Why Use TypeScript?

### Problems it Solves

**JavaScript's Dynamic Nature:**
```javascript
// JavaScript - runs without errors, fails at runtime
function greet(name) {
  return `Hello, ${name.toUpperCase()}`;
}

greet(null); // ❌ TypeError at runtime
```

**Large Codebases:**
- Difficult to maintain without explicit types
- Hard to refactor safely
- Limited IDE support
- More runtime errors

### Benefits

1. **Early Error Detection**: Catch bugs during development
2. **Better Code Documentation**: Types act as living documentation
3. **Improved IDE Support**: Autocomplete, go-to-definition, refactoring
4. **Safer Refactoring**: Changes are type-checked
5. **Team Collaboration**: Clear contracts between components

## Installation and Setup

### Global Installation

```bash
npm install -g typescript
```

### Verify Installation

```bash
tsc --version
# Output: Version X.X.X
```

### Project Setup

```bash
# Initialize a TypeScript project
tsc --init

# Creates tsconfig.json
```

## Basic Workflow

### 1. Create a TypeScript File

```typescript
// app.ts
function greet(name: string): string {
  return `Hello, ${name}!`;
}

console.log(greet("World"));
```

### 2. Compile to JavaScript

```bash
tsc app.ts
```

This generates `app.js`:

```javascript
function greet(name) {
  return "Hello, " + name + "!";
}
console.log(greet("World"));
```

### 3. Run the JavaScript

```bash
node app.js
```

## TypeScript vs JavaScript

| Feature | JavaScript | TypeScript |
|---------|-----------|------------|
| Type Checking | Runtime | Compile-time |
| Type Annotations | No | Yes |
| Compilation | No | Yes (to JS) |
| IDE Support | Limited | Excellent |
| Learning Curve | Easy | Moderate |
| Best For | Small projects | Large applications |

### Key Differences

**JavaScript:**
```javascript
let age = 30;
age = "thirty"; // No error
```

**TypeScript:**
```typescript
let age: number = 30;
age = "thirty"; // ❌ Error: Type 'string' is not assignable to type 'number'
```

## tsconfig.json Overview

```json
{
  "compilerOptions": {
    "target": "ES2020",           // Output JavaScript version
    "module": "commonjs",          // Module system
    "lib": ["ES2020"],            // Library files
    "outDir": "./dist",           // Output directory
    "rootDir": "./src",           // Source directory
    "strict": true,               // Enable strict type checking
    "esModuleInterop": true,      // Interoperability with CommonJS
    "skipLibCheck": true,         // Skip type checking of declaration files
    "forceConsistentCasingInFileNames": true
  },
  "include": ["src/**/*"],        // Files to compile
  "exclude": ["node_modules"]     // Files to exclude
}
```

### Important Compiler Options

- **strict**: Enables all strict type checking
- **target**: JavaScript version to compile to
- **module**: Module system (commonjs, es2020, etc.)
- **outDir**: Where compiled files go
- **rootDir**: Source directory
- **noImplicitAny**: Error on implicit any types
- **strictNullChecks**: Strict null checking

## Quick Start Example

```typescript
// calc.ts
function add(a: number, b: number): number {
  return a + b;
}

function subtract(a: number, b: number): number {
  return a - b;
}

console.log(add(5, 3));      // 8
console.log(subtract(10, 4)); // 6
```

Compile and run:

```bash
tsc calc.ts
node calc.js
```

## Common Commands

```bash
# Compile TypeScript file
tsc filename.ts

# Compile with watch mode
tsc --watch

# Compile entire project
tsc

# Check types without emitting files
tsc --noEmit

# Watch mode for project
tsc --watch
```

## Best Practices

1. **Start Strict**: Enable `strict: true` in tsconfig.json
2. **Use Type Inference**: Let TypeScript infer types when obvious
3. **Avoid `any`**: Use `unknown` if type is truly unknown
4. **Incremental Adoption**: Add TypeScript gradually to existing projects
5. **Use Type Definitions**: Install `@types/*` packages for libraries

## Next Steps

- [Basic Types →](./02-basic-types.md)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/handbook/intro.html)
- [TypeScript Playground](https://www.typescriptlang.org/play)

## Resources

- **Official Docs**: https://www.typescriptlang.org/docs
- **Handbook**: https://www.typescriptlang.org/docs/handbook/intro.html
- **Playground**: https://www.typescriptlang.org/play
- **GitHub**: https://github.com/microsoft/TypeScript
