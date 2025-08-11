# property-getters

## Cel

## Problem

## Pojęcia kluczowe

## Struktura / Diagram (opcjonalnie)

## Przepływ działania

## Przykłady użycia

## Implementacja (fragmenty kodu)

## Zalety

## Wady

## Kiedy używać / kiedy nie

## Powiązane tematy/wzorce

## Źródła / dalsza lektura


### Flashcard 1
**Q:** What is a **field** in object-oriented programming?  
**A:** A field is a variable stored in a class (or object) that holds data. Its visibility is controlled by access modifiers (e.g., public, private, protected).

---

### Flashcard 2
**Q:** How do you declare fields in different languages?  
**A:**  
- **PHP:** `public $name;` or `private $email;`  
- **JavaScript (ES6):**  
  - Public: `name;`  
  - Private: `#email;`  
- **C#:** `public string Name;` or `private string email;`  
- **Python:**  
  - Public: `self.name`  
  - Private (using name mangling): `self.__email`

---

### Flashcard 3
**Q:** What are **getters** and **setters**?  
**A:** They are methods that control the access to an object's fields. Getters retrieve field values, while setters modify them, often including additional logic like validation.

---

### Flashcard 4
**Q:** Give an example of getters and setters in **PHP**.  
**A:** 
```php
class User {
    private $email;

    public function getEmail() {
        return $this->email;
    }

    public function setEmail($email) {
        $this->email = $email;
    }
}
```

---

### Flashcard 5
**Q:** How do you implement getters and setters in **JavaScript** (ES6)?  
**A:** 
```js
class User {
    #email;

    get email() {
        return this.#email;
    }

    set email(value) {
        this.#email = value;
    }
}
```

---

### Flashcard 6
**Q:** What are **properties** and how do they differ from regular getters/setters?  
**A:** Properties provide a cleaner syntax to access values like fields while behind the scenes using getter/setter logic. They allow you to expose data safely without changing the interface from a simple attribute access.

---

### Flashcard 7
**Q:** How are properties implemented in **C#**?  
**A:**  
```csharp
class User {
    private string email;

    public string Email {
        get { return email; }
        set { email = value; }
    }
}
```  
*Note: C# also supports auto-properties:*  
```csharp
public string Email { get; set; }
```

---

### Flashcard 8
**Q:** How do you define properties in **Python**?  
**A:**  
```python
class User:
    def __init__(self):
        self._email = ""

    @property
    def email(self):
        return self._email

    @email.setter
    def email(self, value):
        if "@" in value:
            self._email = value
        else:
            raise ValueError("Invalid email")
```

---

### Flashcard 9
**Q:** Summarize the language-specific implementations for fields, getters/setters, and properties.  
**A:**  
- **PHP:**  
  - **Fields:** `$var` with modifiers like `public` or `private`.  
  - **Getters/Setters:** Methods like `getVar()` and `setVar($val)`.  
  - **Properties:** Magic methods `__get()` and `__set()`.
- **JavaScript:**  
  - **Fields:** Public fields (e.g., `name;`) and private fields (`#email;`).  
  - **Getters/Setters & Properties:** Use the `get` and `set` keywords.
- **C#:**  
  - **Fields:** Regular variables (e.g., `public string Name;`).  
  - **Getters/Setters:** Explicit methods if needed.  
  - **Properties:** Built-in syntax `{ get; set; }` for cleaner code.
- **Python:**  
  - **Fields:** Instance variables (e.g., `self.name` or `self.__email`).  
  - **Getters/Setters:** Regular methods (e.g., `get_email()`, `set_email()`).  
  - **Properties:** Decorators (`@property` and `@<prop>.setter`) for elegant attribute access.
