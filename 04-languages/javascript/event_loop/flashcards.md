# Flashcards: JS Event Loop (microtask vs macrotask)

Q: What is the event loop?
A: A runtime mechanism that dequeues tasks and executes callbacks; it interleaves macrotasks and microtasks.

Q: Microtask examples?
A: Promise.then/catch/finally, MutationObserver, queueMicrotask.

Q: Macrotask examples?
A: setTimeout, setInterval, setImmediate (Node), I/O, message events.
