class Monad {
    constructor(value) {
        this.value = value;
    }

    bind(func) {
        return func(this.value);
    }

    static of(value) {
        return new Monad(value);
    }
}
