# SQL i Transakcyjność

## Podstawy SQL
- **SELECT**: Pobieranie danych z bazy (`SELECT kolumna FROM tabela WHERE warunek`)
- **INSERT**: Dodawanie nowych wierszy (`INSERT INTO tabela (kolumny) VALUES (wartości)`)
- **UPDATE**: Aktualizacja istniejących wierszy (`UPDATE tabela SET kolumna=wartość WHERE warunek`)
- **DELETE**: Usuwanie wierszy (`DELETE FROM tabela WHERE warunek`)
- **JOIN**: Łączenie tabel (`INNER JOIN`, `LEFT JOIN`, `RIGHT JOIN`, `FULL JOIN`)
- **GROUP BY**: Grupowanie wyników (`SELECT kolumna, COUNT(*) FROM tabela GROUP BY kolumna`)
- **ORDER BY**: Sortowanie wyników (`SELECT * FROM tabela ORDER BY kolumna ASC/DESC`)

## Transakcyjność
- **Definicja**: Sekwencja operacji traktowanych jako jedna atomowa jednostka
- **ACID**:
  - **Atomicity**: Wszystkie operacje w transakcji kończą się sukcesem lub żadna
  - **Consistency**: Transakcja pozostawia bazę danych w spójnym stanie
  - **Isolation**: Transakcje są izolowane od siebie nawzajem
  - **Durability**: Zatwierdzone zmiany są trwałe, nawet w przypadku awarii

## Zarządzanie Transakcjami
- **BEGIN TRANSACTION**: Rozpoczęcie transakcji
- **COMMIT**: Zatwierdzenie wszystkich zmian
- **ROLLBACK**: Cofnięcie wszystkich zmian
- **SAVEPOINT**: Punkt kontrolny wewnątrz transakcji
- **Poziomy izolacji**:
  - **READ UNCOMMITTED**: Najniższy poziom (możliwy dirty read)
  - **READ COMMITTED**: Domyślny w wielu bazach
  - **REPEATABLE READ**: Zapobiega non-repeatable read
  - **SERIALIZABLE**: Najwyższy poziom (pełna izolacja)

## SOAP (Simple Object Access Protocol)
- **Definicja**: Protokół komunikacyjny oparty na XML do wymiany danych między aplikacjami
- **Cechy**: Niezależny od platformy i języka, formalnie zdefiniowany (WSDL)
- **Struktura**: Envelope (koperta) zawierająca Header (nagłówek) i Body (ciało)
- **Zastosowanie**: Usługi internetowe w środowiskach korporacyjnych, bankowość
- **Zalety**: Ścisła definicja kontraktu, bezpieczeństwo, obsługa transakcji 