# Observer Pattern

## Intent
Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

## Motivation
The need to maintain consistency between related objects without making classes tightly coupled. For instance, when one object changes state, other objects that depend on it need to be notified.

## Structure
![Observer Pattern Structure](https://refactoring.guru/images/patterns/diagrams/observer/structure.png)

## Participants
- **Subject**: Knows its observers and provides an interface for attaching and detaching Observer objects.
- **ConcreteSubject**: Stores state of interest to ConcreteObserver objects and sends notifications when its state changes.
- **Observer**: Defines an updating interface for objects that should be notified of changes in a subject.
- **ConcreteObserver**: Maintains a reference to a ConcreteSubject object, stores state that should stay consistent with the subject's, and implements the Observer updating interface.

## Collaborations
- ConcreteSubject notifies its observers whenever a change occurs that could make its observers' state inconsistent with its own.
- After being informed of a change in the concrete subject, a ConcreteObserver object may query the subject for information to reconcile its state with that of the subject.

## Consequences
- **Loose coupling**: Subjects don't need to know anything about their observers.
- **Support for broadcast communication**: Notifications are sent automatically to all interested objects.
- **Unexpected updates**: Observers might be notified about changes they don't care about.

## Implementation
- A simple implementation in Java uses an ArrayList to store references to observers:
  - [Observer Implementation in Java](observer_implementation.java)

## Sample Code
```java
// Subject
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Observer
public interface Observer {
    void update(String status);
}

// ConcreteSubject
public class Channel implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String status;
    
    // Subject implementation...
}

// ConcreteObserver
public class Follower implements Observer {
    private String name;
    
    // Observer implementation...
}
```

## Real-World Examples
- Social media notifications
- Event management systems
- News subscription services
- MVC (Model-View-Controller) architecture
- GUI components that need to reflect changes in the data model

## Related Patterns
- **Mediator**: By encapsulating complex update semantics, the Observer pattern can be used with Mediator to coordinate complex updates between objects.
- **Singleton**: The Subject is often a Singleton if there's only one source of state or events in the system.
- **Command**: Commands can be used to implement the update actions in the observers.
