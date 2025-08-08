# Fiszki: Programowanie zdarzeniowe & Observer

P: Czym jest event w programowaniu?
O: To wystąpienie określonego faktu w systemie (np. kliknięcie, nadejście wiadomości, zmiana stanu).

P: Co to jest event source?
O: Komponent generujący zdarzenia.

P: Czym jest wzorzec Observer?
O: Wzorzec projektowy, w którym obiekt obserwowany (subject) powiadamia zarejestrowanych obserwatorów o zmianie stanu.

P: Jakie są elementy wzorca Observer?
O: Subject (Observable), Observer, ConcreteSubject, ConcreteObserver.

P: Czym różni się Observer od Pub/Sub?
O: Observer działa zwykle w tym samym procesie i obserwatorzy są bezpośrednio podłączeni do obiektu; Pub/Sub korzysta z pośrednika (brokera) i może działać w rozproszonym środowisku.

P: Co to jest event handler?
O: Funkcja lub metoda wywoływana, gdy wystąpi zdarzenie.

P: Czym jest event loop?
O: Mechanizm, który nasłuchuje zdarzeń i wywołuje odpowiednie handlery asynchronicznie (np. w Node.js, asyncio, GUI).

P: Co to jest event bus?
O: Mechanizm dystrybucji zdarzeń w pamięci aplikacji, często implementujący wzorzec Pub/Sub.

P: Na czym polega event sourcing?
O: Na przechowywaniu historii stanu systemu jako sekwencji zdarzeń, z których można odtworzyć aktualny stan.

P: Jakie są cechy systemu event-driven?
O: Reaktywność, dekouplowanie komponentów, asynchroniczność, dynamiczność, brak potrzeby ciągłego pollingu.

P: Jaki jest typowy przepływ zdarzenia w systemie event-driven?
O: Event Source → Dispatcher/Event Bus/Event Loop → Observers/Event Handlers.

P: Przykłady zastosowania frontend: React (useEffect), Vue (watch), DOM Event Listeners.
O: Backend: WebSocket, webhooki, Kafka, RabbitMQ.
