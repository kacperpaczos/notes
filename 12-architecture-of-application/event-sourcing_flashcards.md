# Flashcards: Event Sourcing

Q: What is Event Sourcing?
A: A pattern where system state is derived by replaying an append-only log of immutable domain events.

Q: Benefits of Event Sourcing?
A: Full audit trail, temporal queries (state at time T), integration via event streams, and natural CQRS pairing.

Q: What is a snapshot?
A: A periodic persisted materialized state to speed rebuilds by reducing the number of events to replay.
