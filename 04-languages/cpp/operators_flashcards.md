# Flashcards: Operatory w C++

## Operatory arytmetyczne

Q: Jakie są podstawowe operatory arytmetyczne w C++?
A: `+` (dodawanie), `-` (odejmowanie), `*` (mnożenie), `/` (dzielenie), `%` (reszta z dzielenia).

Q: Co robi operator `%` w C++?
A: Zwraca resztę z dzielenia (modulo). Działa tylko na typach całkowitych.

## Operatory przypisania

Q: Jakie są operatory złożonego przypisania w C++?
A: `+=`, `-=`, `*=`, `/=`, `%=` - łączą operację arytmetyczną z przypisaniem (np. `x += 3` to `x = x + 3`).

Q: Jaka jest różnica między `=` a `==` w C++?
A: `=` to operator przypisania (nadaje wartość), `==` to operator porównania (sprawdza równość).

## Operatory inkrementacji i dekrementacji

Q: Co robią operatory `++` i `--` w C++?
A: `++` zwiększa wartość o 1 (inkrementacja), `--` zmniejsza wartość o 1 (dekrementacja).

Q: Jaka jest różnica między `x++` a `++x`?
A: `x++` to postinkrementacja (zwraca starą wartość, potem zwiększa), `++x` to preinkrementacja (zwiększa, potem zwraca nową wartość).

## Operatory relacyjne

Q: Jakie są operatory porównania w C++?
A: `==` (równość), `!=` (nierówność), `<` (mniejsze), `<=` (mniejsze/równe), `>` (większe), `>=` (większe/równe).

Q: Co zwracają operatory relacyjne w C++?
A: Wartość typu `bool`: `true` (prawda) lub `false` (fałsz).

## Operatory logiczne

Q: Jakie są operatory logiczne w C++?
A: `&&` (AND - i logiczne), `||` (OR - lub logiczne), `!` (NOT - negacja logiczna).

Q: Co robi operator `&&` w C++?
A: Zwraca `true` tylko wtedy, gdy oba wyrażenia są prawdziwe. Wykorzystuje ewaluację zwarciową (short-circuit).

Q: Co to jest ewaluacja zwarciowa w operatorach logicznych?
A: W `&&` jeśli lewy operand jest fałszywy, prawy nie jest sprawdzany. W `||` jeśli lewy jest prawdziwy, prawy nie jest sprawdzany.

## Operatory bitowe

Q: Jakie są operatory bitowe w C++?
A: `&` (AND bitowy), `|` (OR bitowy), `^` (XOR bitowy), `~` (negacja bitowa), `<<` (przesunięcie w lewo), `>>` (przesunięcie w prawo).

Q: Jaka jest różnica między `&` a `&&` w C++?
A: `&` to operator bitowy AND (działa na bitach), `&&` to operator logiczny AND (działa na wartościach bool). `&` nie używa ewaluacji zwarciowej.

Q: Co robi operator `^` w C++?
A: To operator XOR bitowy (alternatywa rozłączna) - zwraca 1 gdy bity są różne, 0 gdy są takie same. **NIE jest to potęgowanie!**

Q: Jak wykonać potęgowanie w C++?
A: Używamy funkcji `pow(podstawa, wykładnik)` z biblioteki `<cmath>`. Operator `^` to XOR, nie potęgowanie!

Q: Co robi operator `<<` w C++?
A: Przesunięcie bitowe w lewo. `x << n` przesuwa bity x o n pozycji w lewo (równoważne mnożeniu przez 2^n dla liczb dodatnich).

Q: Co robi operator `>>` w C++?
A: Przesunięcie bitowe w prawo. `x >> n` przesuwa bity x o n pozycji w prawo (równoważne dzieleniu całkowitemu przez 2^n).

## Operatory specjalne

Q: Co robi operator `[]` w C++?
A: Dostęp do elementu tablicy lub kontenera. Przykład: `array[0]` zwraca pierwszy element.

Q: Jaka jest różnica między `.` a `->` w C++?
A: `.` służy do dostępu do składowej obiektu, `->` służy do dostępu do składowej przez wskaźnik.

Q: Co robi operator `::` w C++?
A: Operator zakresu (scope resolution) - służy do dostępu do elementów przestrzeni nazw, statycznych składowych klasy itp. Przykład: `std::cout`, `MyClass::staticMethod()`.

Q: Jak działa operator warunkowy `?:` w C++?
A: To operator trójargumentowy (ternary): `warunek ? wartość_jeśli_true : wartość_jeśli_false`. Przykład: `x > 0 ? x : -x` zwraca wartość bezwzględną.

## Priorytet operatorów

Q: Co określa priorytet operatorów w C++?
A: Priorytet określa kolejność wykonywania operacji w wyrażeniu. Np. `*` i `/` mają wyższy priorytet niż `+` i `-`.

Q: Jak wymusić inną kolejność wykonywania operacji niż wynika z priorytetu?
A: Używając nawiasów `()` - operacje w nawiasach są wykonywane jako pierwsze.

## Najczęstsze błędy

Q: Jaki jest najczęstszy błąd związany z operatorem `^`?
A: Myślenie, że `^` to potęgowanie. W C++ `^` to XOR bitowy! Do potęgowania używamy `pow()`.

Q: Dlaczego `x / 2` może dać inny wynik niż `x / 2.0`?
A: Gdy `x` jest typu całkowitego, `x / 2` to dzielenie całkowite (ucina część ułamkową), a `x / 2.0` to dzielenie zmiennoprzecinkowe.

## Źródła

- [Operatory w C++ - Algorytm.edu.pl](https://www.algorytm.edu.pl/wstp-do-c/operatory-w-c.html)
- [Microsoft C++ Operators](https://learn.microsoft.com/pl-pl/cpp/cpp/cpp-built-in-operators-precedence-and-associativity?view=msvc-170)
- [Operatory przypisania - Microsoft](https://learn.microsoft.com/pl-pl/cpp/cpp/assignment-operators?view=msvc-170)
- [Kurs C++ - Mirosław Zelent](https://miroslawzelent.pl/kurs-c++/operatory-w-c++/)

