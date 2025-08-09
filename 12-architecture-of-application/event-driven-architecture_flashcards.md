# Flashcards: Pub/Sub vs Observer

Q: What is the key difference between Observer and Pub/Sub?
A: Observer is in-process direct subscription to a concrete subject; Pub/Sub uses a broker to decouple publishers and subscribers (often cross-process).

Q: What benefits does a broker bring?
A: Decoupling, durability, routing, fan-out, replay (with persisted logs), and horizontal scalability.

Q: When to choose Observer?
A: Simple in-process notifications, minimal latency, no broker dependency, tight coupling to the observed component.

Q: When to choose Pub/Sub?
A: Cross-service communication, scaling consumers, reliable delivery, routing, and analytics over streams.
