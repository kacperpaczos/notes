# MCP Server z SQLite - Wyciąg z artykułu

## 📋 Cel
Model Context Protocol (MCP) to otwarty standard wprowadzony przez Antrobic w listopadzie 2024, który pozwala AI komunikować się z zewnętrznymi źródłami danych, repozytoriami treści, wywołaniami funkcji i szablonami promptów w sposób bezpieczny, elastyczny i z minimalnym wysiłkiem.

## 🎯 Problem
- LLM są izolowane od rzeczywistych systemów i danych
- Trudność w łączeniu AI z bazami danych, API, narzędziami biznesowymi
- Potrzeba budowania indywidualnych integracji dla każdego systemu
- Czasochłonność utrzymywania integracji przy ewolucji stosu technologicznego

## 🔧 Rozwiązanie
MCP oferuje uniwersalny interfejs (jak USB dla AI):
- Plug & play - podłącz serwer, udostępnij zasoby
- Model lub agenci AI robią resztę
- Każde narzędzie, dokument lub dataset staje się dostępny dla LLM
- Brak potrzeby hardkodowania logiki w modelu lub aplikacji

## 🏗️ Architektura MCP

### Klient-Serwer
- **Klienty**: aplikacje hostujące (Claude Desktop, IDE)
- **Serwery**: lekkie serwisy udostępniające zasoby
- **Transport**: STDIO (lokalnie) lub HTTP + SSE (zdalnie)
- **Komunikacja**: JSON-RPC 2.0

### Typy wiadomości
1. **Requests** - żądania od klientów
2. **Results** - odpowiedzi serwerów
3. **Notifications** - powiadomienia o zmianach
4. **Errors** - komunikaty o błędach

### Handshake (3 kroki)
1. **Initialize Request** - klient inicjuje połączenie
2. **Initialize Response** - serwer odpowiada z wersją i możliwościami
3. **Acknowledgment** - klient wysyła `initialized` notification

## 🔄 Przepływ działania

1. **Inicjalizacja** - Serwer MCP startuje i nasłuchuje
2. **Handshake** - Klient i serwer wymieniają informacje o możliwościach
3. **Ekspozycja narzędzi** - Serwer udostępnia funkcje jako narzędzia
4. **Wywołania** - LLM wywołuje narzędzia przez MCP
5. **Odpowiedzi** - Serwer zwraca wyniki do LLM

## 📚 Implementacja praktyczna

### Wymagania
- Python 3.10+
- `mcp` SDK (`uv add mcp[cli]` lub `pip install mcp`)
- `sqlite3` (wbudowane)
- `asyncio` (podstawowa znajomość)

### Struktura projektu
```
mcp-memory-server/
├── pyproject.toml
├── mcp-server.py
└── memory.db (generowany)
```

### Kluczowe komponenty

#### 1. Inicjalizacja bazy danych
```python
async def init_db():
    async with aiosqlite.connect(DB_FILE) as db:
        await db.execute("""
            CREATE TABLE IF NOT EXISTS memory (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT NOT NULL
            )
        """)
        await db.commit()
```

#### 2. Narzędzia CRUD
```python
@mcp.tool()
async def create_memory(content: str) -> str:
    """Save a new memory entry."""
    async with aiosqlite.connect(DB_FILE) as db:
        await db.execute("INSERT INTO memory (content) VALUES (?)", (content,))
        await db.commit()
    return "Memory saved."

@mcp.tool()
async def list_memories() -> list[str]:
    """List all saved memories."""
    async with aiosqlite.connect(DB_FILE) as db:
        cursor = await db.execute("SELECT id, content FROM memory")
        rows = await cursor.fetchall()
    return [f"{id}: {content}" for id, content in rows]

@mcp.tool()
async def update_memory(id: int, content: str) -> str:
    """Update a memory by ID."""
    async with aiosqlite.connect(DB_FILE) as db:
        await db.execute("UPDATE memory SET content = ? WHERE id = ?", (content, id))
        await db.commit()
    return f"Memory {id} updated."

@mcp.tool()
async def delete_memory(id: int) -> str:
    """Delete a memory by ID."""
    async with aiosqlite.connect(DB_FILE) as db:
        await db.execute("DELETE FROM memory WHERE id = ?", (id,))
        await db.commit()
    return f"Memory {id} deleted."
```

#### 3. Uruchomienie serwera
```python
async def run():
    await init_db()
    await mcp.run_stdio_async()

if __name__ == "__main__":
    anyio.run(run)
```

## 🔧 Konfiguracja Claude Desktop

### Plik konfiguracyjny
```json
{
  "mcpServers": {
    "memory-server": {
      "command": "uv",
      "args": [
        "--directory",
        "/ABSOLUTE/PATH/TO/YOUR/PROJECT",
        "run",
        "mcp-server.py"
      ]
    }
  }
}
```

### Kroki konfiguracji
1. Otwórz **File → Settings → Developer → Edit Config**
2. Dodaj konfigurację serwera
3. Zapisz i zrestartuj Claude Desktop
4. Sprawdź ikonę 🔨 (narzędzia)

## ✅ Zalety MCP

- **Uniwersalność** - jeden standard dla wszystkich integracji
- **Modularność** - każdy serwer działa niezależnie
- **Bezpieczeństwo** - izolacja procesów
- **Elastyczność** - obsługa różnych transportów
- **Łatwość implementacji** - prosty protokół JSON-RPC
- **Skalowalność** - łatwe dodawanie nowych serwerów

## ⚠️ Wady i ograniczenia

- **Overload narzędzi** - LLM gorzej radzą sobie z dużą liczbą narzędzi
- **Bezpieczeństwo** - HTTP transport wymaga dodatkowych zabezpieczeń
- **Obsługa błędów** - wymaga starannego planowania
- **Debugowanie** - złożone przepływy komunikacji
- **Wydajność** - overhead przy dużej liczbie wywołań

## 🔧 Najlepsze praktyki

### 1. Minimalizm narzędzi
- **Zasada**: Mniej = lepiej
- **Problem**: LLM gorzej radzą sobie z dużą liczbą opcji
- **Rozwiązanie**: Tylko niezbędne narzędzia

### 2. Bezpieczeństwo
- **TLS** - szyfrowanie połączeń
- **Autoryzacja** - kontrola dostępu
- **Rate limiting** - ograniczenie liczby żądań
- **Walidacja** - sprawdzanie danych wejściowych

### 3. Obsługa błędów
- **Try/except** - obsługa wyjątków
- **Czyste komunikaty** - zrozumiałe błędy
- **Walidacja** - sprawdzanie przed zapisem
- **Logowanie** - śledzenie problemów

## 🎯 Przykłady użycia

### Systemy pamięci
- **Notatki AI** - przechowywanie i wyszukiwanie
- **Dziennik** - zapisywanie refleksji
- **Baza wiedzy** - organizacja informacji

### Integracje biznesowe
- **CRM** - dostęp do danych klientów
- **Analytics** - raporty i metryki
- **Komunikacja** - Slack, email, SMS

### Narzędzia deweloperskie
- **Git** - zarządzanie repozytoriami
- **Docker** - zarządzanie kontenerami
- **Monitoring** - alerty i metryki

## 🔗 Rozszerzenia i warianty

### 1. **Wyszukiwanie**
- Dodanie funkcji search do memory server
- Indeksowanie treści
- Wyszukiwanie semantyczne

### 2. **Autoryzacja**
- System uprawnień
- Kontrola dostępu
- Logowanie użytkowników

### 3. **Zaawansowane funkcje**
- AI-powered journal
- Task tracker
- Knowledge hub

## 🎯 Kiedy używać MCP

### ✅ Użyj gdy:
- Potrzebujesz połączyć AI z zewnętrznymi systemami
- Masz wiele różnych źródeł danych
- Chcesz uniknąć custom integracji
- Potrzebujesz modularnej architektury

### ❌ Nie używaj gdy:
- Masz tylko jeden prosty system do integracji
- Potrzebujesz bardzo wysokiej wydajności
- Masz ograniczenia bezpieczeństwa
- Nie masz doświadczenia z async programming

## 📚 Źródła

- [Model Context Protocol - Anthropic](https://www.anthropic.com/news/model-context-protocol)
- [MCP Architecture](https://modelcontextprotocol.io/docs/concepts/architecture)
- [MCP Quickstart](https://modelcontextprotocol.io/quickstart/server)
- [MetaTool Benchmark Paper](https://arxiv.org/abs/2310.03128)

## 🔮 Przyszłość MCP

MCP reprezentuje nową erę AI, gdzie modele językowe:
- Nie są ograniczone do statycznych promptów
- Mogą wchodzić w interakcje z żywymi narzędziami
- Mają dostęp do aktualnej wiedzy w czasie rzeczywistym
- Są modularne i kontekstowo świadome

**Kluczowe przesłanie**: MCP to nie tylko osiągnięcie techniczne, ale wgląd w przyszłość modularnego, kontekstowo świadomego AI. 