[[ Heap ]]
Objects are allocated in a heap which is just a name to denote a large (mostly unstructured) region of memory.


In Heap we have [[ Stack ]] of frames and [[ Queue ]] of messages.

[[ Stack ]]

```js
function foo(b) {
  const a = 10;
  return a + b + 11;
}

function bar(x) {
  const y = 3;
  return foo(x * y);
}

const baz = bar(7); // assigns 42 to baz
```

Order of operations:

When calling bar, a first frame is created containing references to bar's arguments and local variables.
When bar calls foo, a second frame is created and pushed on top of the first one, containing references to foo's arguments and local variables.
When foo returns, the top frame element is popped out of the stack (leaving only bar's call frame).
When bar returns, the stack is empty.

[[ Queue ]]

A JavaScript runtime uses a message queue, which is a list of messages to be processed. Each message has an associated function that gets called to handle the message.

At some point during the [[ Event Loop ]], the runtime starts handling the messages on the queue, starting with the oldest one. To do so, the message is removed from the queue and its corresponding function is called with the message as an input parameter. As always, calling a function creates a new stack frame for that function's use.

The processing of functions continues until the stack is once again empty. Then, the event loop will process the next message in the queue (if there is one).

[[ Event Loop ]]

```js
while (queue.waitForMessage()) {
  queue.processNextMessage();
}
```

queue.waitForMessage() waits synchronously for a message to arrive (if one is not already available and waiting to be handled).