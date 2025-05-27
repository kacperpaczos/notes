# Przykłady wykorzystania Transformerów i Agentów AI

Ten katalog zawiera praktyczne przykłady wykorzystania modeli opartych na architekturze Transformer oraz frameworków do budowy agentów AI.

## Dostępne przykłady

### 1. BERT - Klasyfikacja sentymentu (`bert_classification.py`)

Przykład pokazuje:
- Jak załadować wstępnie wytrenowany model BERT do analizy sentymentu
- Jak tokenizować tekst wejściowy
- Jak wykonać inferencję
- Jak interpretować wyniki

Wymagane biblioteki:
```
pip install transformers torch
```

### 2. Agent LangChain (`langchain_agent.py`)

Przykład pokazuje:
- Jak zbudować prostego agenta AI przy użyciu LangChain
- Jak zintegrować model językowy z narzędziami (wyszukiwanie, obliczenia)
- Jak konfigurować zachowanie agenta
- Jak obsługiwać zapytania użytkownika

Wymagane biblioteki:
```
pip install langchain langchain-openai tavily-python
```

## Jak uruchomić przykłady

1. Zainstaluj wymagane biblioteki dla wybranego przykładu
2. Dostosuj parametry (np. klucze API) w kodzie źródłowym
3. Uruchom przykład za pomocą Pythona:

```bash
python bert_classification.py
# lub
python langchain_agent.py
```

## Uwagi

- Przykłady wymagają dostępu do internetu dla pobierania modeli lub dostępu do API
- W przypadku przykładów korzystających z zewnętrznych API (np. OpenAI), musisz posiadać własny klucz API
- Dla uproszczenia, przykłady nie zawierają obsługi błędów i walidacji wejścia, które byłyby konieczne w środowisku produkcyjnym

## Pytania kontrolne

- Jak zmodyfikować kod klasyfikacji BERT, aby obsługiwał inny model?
- Jakie narzędzia można dodać do agenta LangChain, aby zwiększyć jego możliwości?
- Jak zmienić zachowanie agenta przez modyfikację prompt template? 