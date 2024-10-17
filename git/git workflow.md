### 1. **`git fetch`**
`git fetch` pobiera najnowsze zmiany ze zdalnego repozytorium, ale **nie wprowadza ich automatycznie do bieżącej gałęzi**. Zmiany są pobierane na lokalną kopię zdalnych gałęzi (tzw. `origin/*`), ale Twoja bieżąca gałąź nie zostanie zmieniona.

- Użycie:
  ```bash
  git fetch origin
  ```
- Scenariusz: Kiedy chcesz zobaczyć zmiany zdalne bez wprowadzania ich od razu do swojej gałęzi. Może to być przydatne, gdy chcesz najpierw przejrzeć zmiany, zanim je scalasz.

### 2. **`git pull`**
`git pull` jest połączeniem komend `git fetch` i `git merge`. Pobiera najnowsze zmiany z zewnętrznego repozytorium (fetch) i **automatycznie scala** je z Twoją aktualną gałęzią (merge). Używaj, gdy chcesz szybko zsynchronizować swoją pracę ze zdalnymi zmianami.

- Użycie:
  ```bash
  git pull origin main
  ```
- Scenariusz: Używane, gdy pracujesz z zespołem i chcesz pobrać oraz połączyć wszystkie zmiany w jednej komendzie, aby Twoja lokalna gałąź była aktualna z zdalną.

### 3. **`git merge`**
`git merge` łączy zmiany z innej gałęzi do Twojej bieżącej gałęzi. Używa się tego zazwyczaj po `git fetch`, aby zaktualizować bieżącą gałąź lub po zakończeniu pracy na innej gałęzi i chcesz zmergować zmiany.

- Użycie:
  ```bash
  git merge feature/nazwa-funkcjonalności
  ```
- Scenariusz: Używasz, gdy skończysz pracę na innej gałęzi (np. `feature/nazwa-funkcjonalności`) i chcesz wprowadzić te zmiany do bieżącej gałęzi (np. `main`). Jeśli pojawią się konflikty, musisz je rozwiązać ręcznie.

### 4. **`git rebase`**
`git rebase` przepisuje historię commitów, pozwalając na przeniesienie pracy z jednej gałęzi na drugą bez tworzenia dodatkowego commita scalającego, jak w przypadku `merge`. Jest to przydatne, aby utrzymać czystą historię projektu.

- Użycie:
  ```bash
  git rebase main
  ```
- Scenariusz: Kiedy chcesz na nowo "oprzeć" swoją gałąź o najnowsze zmiany z `main`, aby uniknąć zbyt wielu commitów scalających. Uważaj, aby nie używać rebasa na zdalnych, udostępnionych gałęziach.

### 5. **`git reset`**
`git reset` cofa zmiany w lokalnej gałęzi do określonego commita. Może cofać zarówno commity, jak i zmiany w plikach.

- Użycie:
  ```bash
  git reset --hard HEAD~1
  ```
  Lub dla bardziej "miękkiego" cofania:
  ```bash
  git reset --soft HEAD~1
  ```
- Scenariusz: Kiedy przypadkowo popełniłeś błąd w ostatnim commicie i chcesz go wycofać. Opcja `--hard` resetuje także zmiany w plikach, a `--soft` zatrzymuje zmiany w staging area, aby można było je poprawić.

### 6. **`git stash`**
`git stash` zapisuje bieżące, niezcommittowane zmiany "na później" i przywraca stan gałęzi do ostatniego commita. Zmiany są przechowywane w pamięci podręcznej, skąd można je później przywrócić.

- Użycie:
  ```bash
  git stash
  git stash pop
  ```
- Scenariusz: Kiedy pracujesz nad czymś, ale musisz szybko zmienić gałąź lub zaktualizować swój kod (np. `git pull`) i nie chcesz tracić niezapisanych zmian.

### 7. **`git cherry-pick`**
`git cherry-pick` pozwala na wybranie konkretnego commita z innej gałęzi i "skopiowanie" go do bieżącej gałęzi.

- Użycie:
  ```bash
  git cherry-pick <commit_hash>
  ```
- Scenariusz: Kiedy masz jeden commit z innej gałęzi, który chcesz dodać do swojej bieżącej gałęzi, bez łączenia wszystkich zmian z tamtej gałęzi.

### Przykładowy scenariusz pracy:
1. **Sprawdzasz najnowsze zmiany bez ich scalania**:
   ```bash
   git fetch origin
   git log origin/main --oneline
   ```
   Zobaczysz, jakie zmiany zostały wprowadzone w zdalnej gałęzi `main`.

2. **Scalasz pobrane zmiany** (po wcześniejszym `fetch`):
   ```bash
   git merge origin/main
   ```

3. **Scalasz zmiany z inną lokalną gałęzią** (np. gdy skończysz pracę na funkcji):
   ```bash
   git checkout main
   git merge feature/nazwa-funkcjonalności
   ```

4. **Rebasujesz lokalną gałąź na głównej** (jeśli preferujesz rebase nad merge):
   ```bash
   git checkout feature/nazwa-funkcjonalności
   git rebase main
   ```
