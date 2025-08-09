# Flashcards: Outbox & Transactional Messaging

Q: What is the Outbox pattern?
A: Persist domain changes and outgoing messages in the same DB transaction, then relay messages asynchronously.

Q: Why use Outbox?
A: Ensures atomicity between state change and message publish; avoids lost messages.

Q: With which delivery semantics is Outbox commonly paired?
A: At-least-once delivery with idempotent consumers.
