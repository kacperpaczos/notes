# Reading UML Diagrams

UML (Unified Modeling Language) is a standardized modeling language used in software engineering. This guide covers how to read the most common UML diagrams.

## Class Diagrams

Class diagrams show the static structure of a system - its classes, interfaces, attributes, and relationships.

### Basic Elements

```
┌─────────────────────┐
│      ClassName      │
├─────────────────────┤
│ - privateAttribute  │
│ # protectedAttribute│
│ + publicAttribute   │
├─────────────────────┤
│ + publicMethod()    │
│ # protectedMethod() │
│ - privateMethod()   │
└─────────────────────┘
```

#### Visibility Modifiers
- `+` Public
- `-` Private
- `#` Protected
- `~` Package/Default

### Relationships

#### Inheritance (Generalization)
```
    ┌────────┐
    │ Animal │
    └────┬───┘
         │
         │ extends
         ▼
    ┌────────┐
    │  Dog   │
    └────────┘
```

Represented by a solid line with an empty triangle pointing to the parent class.

#### Implementation (Interface Realization)
```
┌───────────────┐
│ «interface»   │
│ Serializable  │
└───────┬───────┘
        │
        │ implements
        ▼
    ┌────────┐
    │ Person │
    └────────┘
```

Represented by a dashed line with an empty triangle pointing to the interface.

#### Association
```
┌────────┐           ┌────────┐
│ Person │-----------│ Address│
└────────┘           └────────┘
```

Represented by a solid line between classes.

#### Aggregation (Has-A)
```
┌─────────┐       ┌───────┐
│ Library │◇------│ Book  │
└─────────┘       └───────┘
```

Represented by a solid line with an empty diamond at the container class. Indicates that the part can exist independently from the whole.

#### Composition (Contains-A)
```
┌─────────┐       ┌───────┐
│  House  │◆------│ Room  │
└─────────┘       └───────┘
```

Represented by a solid line with a filled diamond at the container class. Indicates that the part cannot exist without the whole.

#### Multiplicity
```
┌────────┐    1     *    ┌────────┐
│ Teacher│◆-----------───│ Student│
└────────┘                └────────┘
```

Numbers or symbols near the relationship line:
- `1` - Exactly one
- `*` or `0..*` - Zero or more
- `1..*` - One or more
- `0..1` - Zero or one

## Sequence Diagrams

Sequence diagrams show object interactions arranged in time sequence.

```
    ┌───┐          ┌────────┐          ┌─────────┐
    │User│          │Controller│          │Database │
    └─┬─┘          └────┬───┘          └────┬────┘
      │     login()     │                    │
      │─────────────────>                    │
      │                 │   validate()       │
      │                 │───────────────────>│
      │                 │                    │
      │                 │    result          │
      │                 │<───────────────────│
      │   response      │                    │
      │<────────────────│                    │
    ┌─┴─┐          ┌────┴───┐          ┌────┴────┐
    │User│          │Controller│          │Database │
    └───┘          └────────┘          └─────────┘
```

- **Lifelines**: Vertical dashed lines showing object's lifetime
- **Messages**: Arrows between lifelines indicating interactions
- **Activation Boxes**: Rectangles on lifelines indicating when an object is active

## Activity Diagrams

Activity diagrams represent workflows of stepwise activities and actions.

```
      ┌──────┐
      │Start │
      └───┬──┘
          ▼
    ┌─────────────┐
    │Login Screen │
    └──────┬──────┘
           ▼
    ┌─────────────┐
    │Enter Details│
    └──────┬──────┘
           ▼
      ┌────────┐     No    ┌───────────┐
      │Valid?  │──────────>│Show Error │
      └────┬───┘           └─────┬─────┘
           │ Yes                 │
           ▼                     │
    ┌─────────────┐              │
    │ Home Screen │<─────────────┘
    └──────┬──────┘
           ▼
      ┌────────┐
      │  End   │
      └────────┘
```

- **Start/End nodes**: Filled circles and bulls-eye symbols
- **Activities**: Rounded rectangles
- **Decisions**: Diamonds
- **Flows**: Arrows between activities

## Use Case Diagrams

Use case diagrams represent user interactions with the system.

```
          ┌──────────────────────────────┐
          │         System               │
          │  ┌─────────────────────┐     │
          │  │                     │     │
          │  │    Login to System  │     │
 ┌─────┐  │  │                     │     │
 │     │  │  └─────────────────────┘     │
 │User │──┼─────────────────┐            │
 │     │  │  ┌──────────────┴──────┐     │
 └─────┘  │  │                     │     │
          │  │   Manage Profile    │     │
          │  │                     │     │
          │  └─────────────────────┘     │
          └──────────────────────────────┘
```

- **Actors**: Stick figures representing users or external systems
- **Use Cases**: Ovals representing functionality
- **System Boundary**: Rectangle containing use cases
- **Relationships**: Lines connecting actors to use cases

## Component Diagrams

Component diagrams show the organization and dependencies among components.

```
    ┌───────────────┐        ┌────────────────┐
    │               │        │                │
    │  Web Browser  │◯────────◻│  Web Server   │
    │               │        │                │
    └───────────────┘        └────────────────┘
                                     │
                                     │
                              ┌──────▼───────┐
                              │              │
                              │  Database    │
                              │              │
                              └──────────────┘
```

- **Components**: Rectangles with two small rectangles on left side
- **Interfaces**: Circle (provided) and semicircle (required)
- **Dependencies**: Dashed arrows

## State Diagrams

State diagrams show states of an object and transitions between states.

```
    ┌───────────┐  submit   ┌─────────┐  approve  ┌──────────┐
    │ Created   │───────────>│Submitted│──────────>│ Approved │
    └───────────┘           └─────────┘           └──────────┘
          ▲                       │                     │
          │                       │                     │
          │                       │                     │
          │                       ▼        reject       ▼
          │                  ┌──────────┐         ┌──────────┐
          └──────────────────│ Rejected │<────────│  Review  │
                             └──────────┘         └──────────┘
```

- **States**: Rounded rectangles
- **Initial State**: Filled circle
- **Final State**: Filled circle within a circle
- **Transitions**: Arrows with event labels

## Best Practices for Reading UML Diagrams

1. **Start with the overview** - Understand the purpose and type of diagram
2. **Identify key elements** - Focus on classes, actors, or components first
3. **Follow relationships** - Understand how elements connect to each other
4. **Note multiplicities** - Check the numbers near relationship lines
5. **Read annotations** - Pay attention to text notes and constraints
6. **Understand stereotypes** - Look for «stereotype» notations that provide additional meaning

## Common UML Stereotypes

- `«interface»` - Defines an interface
- `«abstract»` - Marks an abstract class
- `«entity»` - Represents persistent information
- `«control»` - Represents control logic
- `«boundary»` - Represents system boundaries/UI
- `«service»` - Represents a stateless service
- `«model»` - Represents a data model

## Conclusion

UML diagrams are powerful tools for visualizing software systems from different perspectives. Understanding how to read them is essential for software developers, architects, and anyone involved in the software development process. 