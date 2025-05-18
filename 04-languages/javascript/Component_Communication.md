# Komunikacja Między Komponentami w JS

## Event Bus (Szyna Zdarzeń)
- **Definicja**: Centralny obiekt do publikowania i subskrybowania zdarzeń w aplikacji
- **Działanie**: Komponenty emitują zdarzenia, które są odbierane przez inne komponenty
- **Zastosowanie**: Komunikacja między niespokrewnionymi komponentami
- **Implementacja**:
  ```javascript
  class EventBus {
    constructor() {
      this.events = {};
    }
    
    on(eventName, callback) {
      if (!this.events[eventName]) {
        this.events[eventName] = [];
      }
      this.events[eventName].push(callback);
    }
    
    emit(eventName, data) {
      if (this.events[eventName]) {
        this.events[eventName].forEach(callback => callback(data));
      }
    }
  }
  ```
- **Zalety**: Luźne powiązanie komponentów, łatwa implementacja
- **Wady**: Trudność w śledzeniu przepływu danych, potencjalne memory leaks

## Mniejsze Szyny Zdarzeń (Mini Bus)
- **Definicja**: Ograniczona wersja Event Busa, często dla specyficznej części aplikacji
- **Zastosowanie**: Moduły z określonym zakresem komunikacji
- **Zalety**: Mniejsza złożoność, łatwiejsze śledzenie zdarzeń

## Komunikacja w Hierarchii Komponentów

### Komunikacja z Rodzica do Dziecka
- **Props**: Przekazywanie danych jako właściwości komponentu
  ```javascript
  <ChildComponent data={parentData} />
  ```
- **Referencje**: Bezpośredni dostęp do metod komponentu-dziecka
  ```javascript
  <ChildComponent ref={childRef} />
  childRef.current.childMethod();
  ```

### Komunikacja z Dziecka do Rodzica
- **Callback Props**: Funkcje przekazywane do dziecka, wywoływane gdy dziecko chce zakomunikować zdarzenie
  ```javascript
  <ChildComponent onAction={handleChildAction} />
  ```
- **Emit (Vue.js)**: Metoda emitowania zdarzeń z komponentu-dziecka do rodzica
  ```javascript
  // W dziecku
  this.$emit('action', data);
  
  // W rodzicu
  <child-component @action="handleAction" />
  ```

## Globalne Rozwiązania
- **Redux/Vuex/Pinia**: Centralne store'y do zarządzania stanem
- **Context API (React)**: Dostarczanie danych głęboko w drzewie komponentów bez props drilling
- **Provide/Inject (Vue)**: Wstrzykiwanie zależności do dowolnego potomka
- **MobX**: Reaktywne zarządzanie stanem
- **RxJS**: Reaktywne programowanie dla zaawansowanych przepływów danych 