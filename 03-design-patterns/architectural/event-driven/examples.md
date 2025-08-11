# Przykłady implementacji Event-Driven

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


## 🐍 Python

### Event Bus z asyncio
```python
import asyncio
from typing import Dict, List, Callable, Any
from dataclasses import dataclass
from enum import Enum

class EventType(Enum):
    USER_LOGIN = "user_login"
    USER_LOGOUT = "user_logout"
    DATA_UPDATED = "data_updated"

@dataclass
class Event:
    type: EventType
    data: Any
    timestamp: float

class EventBus:
    def __init__(self):
        self._handlers: Dict[EventType, List[Callable]] = {}
    
    def subscribe(self, event_type: EventType, handler: Callable):
        if event_type not in self._handlers:
            self._handlers[event_type] = []
        self._handlers[event_type].append(handler)
    
    async def publish(self, event: Event):
        if event.type in self._handlers:
            for handler in self._handlers[event.type]:
                await handler(event)

# Przykład użycia
async def user_login_handler(event: Event):
    print(f"Użytkownik zalogowany: {event.data}")

async def data_update_handler(event: Event):
    print(f"Dane zaktualizowane: {event.data}")

async def main():
    bus = EventBus()
    bus.subscribe(EventType.USER_LOGIN, user_login_handler)
    bus.subscribe(EventType.DATA_UPDATED, data_update_handler)
    
    await bus.publish(Event(EventType.USER_LOGIN, "john_doe", 1234567890))
    await bus.publish(Event(EventType.DATA_UPDATED, {"id": 1, "value": "new"}, 1234567890))

asyncio.run(main())
```

## 🟨 JavaScript/Node.js

### Event Emitter
```javascript
const EventEmitter = require('events');

class UserService extends EventEmitter {
    login(username) {
        // Logika logowania
        console.log(`Logowanie użytkownika: ${username}`);
        
        // Emitowanie zdarzenia
        this.emit('userLogin', {
            username,
            timestamp: Date.now()
        });
    }
    
    logout(username) {
        console.log(`Wylogowanie użytkownika: ${username}`);
        this.emit('userLogout', {
            username,
            timestamp: Date.now()
        });
    }
}

// Użycie
const userService = new UserService();

// Rejestracja handlerów
userService.on('userLogin', (data) => {
    console.log(`Handler 1: Użytkownik ${data.username} zalogowany o ${new Date(data.timestamp)}`);
});

userService.on('userLogin', (data) => {
    console.log(`Handler 2: Wysyłanie powiadomienia dla ${data.username}`);
});

userService.on('userLogout', (data) => {
    console.log(`Użytkownik ${data.username} wylogowany`);
});

// Test
userService.login('john_doe');
userService.logout('john_doe');
```

### React Hooks (Frontend)
```javascript
import React, { useState, useEffect } from 'react';

// Custom hook dla event-driven state
function useEventState(initialState) {
    const [state, setState] = useState(initialState);
    const [listeners, setListeners] = useState([]);
    
    const subscribe = (callback) => {
        setListeners(prev => [...prev, callback]);
        return () => {
            setListeners(prev => prev.filter(listener => listener !== callback));
        };
    };
    
    const publish = (newState) => {
        setState(newState);
        listeners.forEach(listener => listener(newState));
    };
    
    return [state, publish, subscribe];
}

function UserComponent() {
    const [user, setUser, subscribeToUser] = useEventState(null);
    
    useEffect(() => {
        const unsubscribe = subscribeToUser((newUser) => {
            console.log('Użytkownik zmieniony:', newUser);
        });
        
        return unsubscribe;
    }, []);
    
    return (
        <div>
            <button onClick={() => setUser({ name: 'John', id: 1 })}>
                Zaloguj
            </button>
            <button onClick={() => setUser(null)}>
                Wyloguj
            </button>
        </div>
    );
}
```

## ☕ Java

### Event Bus z Spring Events
```java
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// Zdarzenie
public class UserLoginEvent extends ApplicationEvent {
    private final String username;
    private final long timestamp;
    
    public UserLoginEvent(Object source, String username) {
        super(source);
        this.username = username;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getUsername() { return username; }
    public long getTimestamp() { return timestamp; }
}

// Publisher
@Component
public class UserService {
    private final ApplicationEventPublisher eventPublisher;
    
    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    public void login(String username) {
        // Logika logowania
        System.out.println("Logowanie użytkownika: " + username);
        
        // Publikacja zdarzenia
        eventPublisher.publishEvent(new UserLoginEvent(this, username));
    }
}

// Listener
@Component
public class UserLoginListener implements ApplicationListener<UserLoginEvent> {
    @Override
    public void onApplicationEvent(UserLoginEvent event) {
        System.out.println("Handler: Użytkownik " + event.getUsername() + 
                          " zalogowany o " + new Date(event.getTimestamp()));
    }
}
```

## 🦀 Rust

### Event System z trait objects
```rust
use std::collections::HashMap;
use std::any::{Any, TypeId};

// Trait dla event handlerów
trait EventHandler: Send + Sync {
    fn handle(&self, event: &dyn Any);
}

// Event Bus
struct EventBus {
    handlers: HashMap<TypeId, Vec<Box<dyn EventHandler>>>,
}

impl EventBus {
    fn new() -> Self {
        EventBus {
            handlers: HashMap::new(),
        }
    }
    
    fn subscribe<E: 'static>(&mut self, handler: Box<dyn EventHandler>) {
        let type_id = TypeId::of::<E>();
        self.handlers.entry(type_id).or_insert_with(Vec::new).push(handler);
    }
    
    fn publish<E: 'static>(&self, event: E) {
        if let Some(handlers) = self.handlers.get(&TypeId::of::<E>()) {
            for handler in handlers {
                handler.handle(&event);
            }
        }
    }
}

// Przykład zdarzenia
#[derive(Debug)]
struct UserLoginEvent {
    username: String,
    timestamp: u64,
}

// Handler implementacja
struct UserLoginHandler;

impl EventHandler for UserLoginHandler {
    fn handle(&self, event: &dyn Any) {
        if let Some(login_event) = event.downcast_ref::<UserLoginEvent>() {
            println!("Użytkownik {} zalogowany o {}", 
                     login_event.username, login_event.timestamp);
        }
    }
}

fn main() {
    let mut bus = EventBus::new();
    bus.subscribe::<UserLoginEvent>(Box::new(UserLoginHandler));
    
    let event = UserLoginEvent {
        username: "john_doe".to_string(),
        timestamp: 1234567890,
    };
    
    bus.publish(event);
}
```

## 🔗 Porównanie implementacji

| Język | Mechanizm | Zalety | Wady |
|-------|-----------|--------|------|
| **Python** | asyncio + EventBus | Asynchroniczny, prosty | Brak typowania |
| **JavaScript** | EventEmitter | Wbudowany, prosty | Callback hell |
| **Java** | Spring Events | Typowane, enterprise | Verbose |
| **Rust** | Trait objects | Bezpieczny, wydajny | Złożony | 