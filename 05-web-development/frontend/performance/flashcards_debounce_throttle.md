# Flashcards: Debounce vs Throttle

Q: Debounce?
A: Delays execution until no events have occurred for a time window — collapses bursts into one call.

Q: Throttle?
A: Ensures execution at most once per time window — limits call rate during bursts.

Q: Typical uses?
A: Debounce: search input; Throttle: scroll/resize handlers, rate-limited API calls.
