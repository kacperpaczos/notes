# Flashcards: Delivery Semantics & Idempotency

Q: What are delivery semantics?
A: At-most-once (may lose), at-least-once (may duplicate), exactly-once (hard; simulate via idempotency).

Q: What is idempotency?
A: Processing the same message multiple times yields the same effect — enables safe retries.

Q: Techniques for idempotency?
A: Idempotency keys, deduplication stores, conditional writes, versioning.
