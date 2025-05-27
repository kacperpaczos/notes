# Sztuczna Inteligencja i Machine Learning

Ten katalog zawiera materiały dotyczące sztucznej inteligencji i uczenia maszynowego.

## Zawartość katalogu

### Podkatalogi

- [dl/](dl/)
- [computer-vision/](computer-vision/)
- [ml/](ml/)
- [nlp/](nlp/)
- [transformers/](transformers/) - Architektura Transformer i popularne modele

---

## AI, modele, agenci, narzędzia

### 1. Wprowadzenie do AI

**Opis:**
Sztuczna inteligencja (AI) to dziedzina informatyki zajmująca się tworzeniem systemów zdolnych do wykonywania zadań wymagających inteligencji ludzkiej, takich jak rozumienie języka, rozpoznawanie obrazów, podejmowanie decyzji czy uczenie się.

**Podstawowe pojęcia:**
- Uczenie maszynowe (ML)
- Uczenie głębokie (Deep Learning)
- Modele językowe (LLM)
- Dane treningowe, inferencja, fine-tuning

**Przykłady zastosowań:**
- Asystenci głosowi
- Systemy rekomendacyjne
- Analiza obrazów medycznych
- Chatboty

**Pytania kontrolne:**
- Czym różni się AI od ML?
- Jakie są przykłady zastosowań AI w codziennym życiu?
- Co to jest inferencja w kontekście AI?

### 2. Modele AI

**Opis:**
Model AI to matematyczna reprezentacja procesu, która na podstawie danych wejściowych generuje przewidywania lub decyzje.

**Rodzaje modeli:**
- Regresja
- Klasyfikacja
- Modele generatywne
- Modele językowe (LLM)

**Popularne modele:**
- GPT (OpenAI)
- BERT, RoBERTa, T5 (Google, Hugging Face)
- PaLM (Google)
- Llama (Meta)
- Stable Diffusion (generowanie obrazów)

**Pytania kontrolne:**
- Jakie są różnice między modelem klasyfikacyjnym a generatywnym?
- Do czego służy model BERT?
- Jakie zadania można rozwiązywać za pomocą modeli LLM?

### 3. Agenci AI

**Opis:**
Agent AI to system, który autonomicznie podejmuje decyzje i wykonuje zadania na podstawie danych wejściowych i środowiska.

**Rodzaje agentów:**
- **Agenci konwersacyjni** - chatboty, asystenci głosowi
- **Agenci zadaniowi** - systemy wykonujące konkretne zadania
- **Agenci autonomiczni** - systemy podejmujące samodzielne decyzje
- **Agenci współpracujący** - systemy współpracujące z innymi agentami lub ludźmi

**Przykłady agentów:**
- Asystenci głosowi (np. Siri, Alexa)
- Chatboty
- Agenci zadaniowi (np. AutoGPT, BabyAGI)

**Architektury agentów:**
- **ReAct** (Reasoning and Acting) - łączenie rozumowania z działaniem
- **AutoGPT** - autonomiczne wykonywanie złożonych zadań
- **BabyAGI** - planowanie i realizacja celów
- **A2A** (Agent-to-Agent) - komunikacja i współpraca między agentami
- **MCP** (Multi-Agent Collaboration Protocol) - protokół współpracy wielu agentów

**Frameworks i narzędzia do budowy agentów:**

1. **LangChain**
   - **Opis:** Framework do budowy aplikacji opartych na LLM
   - **Kluczowe cechy:**
     - Łańcuchy (chains) - sekwencje akcji
     - Narzędzia (tools) - interfejsy do zasobów zewnętrznych
     - Pamięć (memory) - przechowywanie historii interakcji
     - Agenci - autonomiczne podejmowanie decyzji
   - **Zastosowania:** Chatboty, systemy RAG, asystenci AI

2. **Google AI Studio i ADK (AI Development Kit)**
   - **Opis:** Narzędzia Google do tworzenia aplikacji AI i agentów
   - **Kluczowe cechy:**
     - Dostęp do modeli Google (Gemini, PaLM)
     - Tworzenie przetwarzanych promptów
     - Narzędzia do testowania i wdrażania agentów
     - Integracja z Google Cloud
   - **Zastosowania:** Asystenci AI, chatboty biznesowe, automatyzacja

3. **AutoGPT**
   - **Opis:** Framework do tworzenia autonomicznych agentów opartych na GPT
   - **Kluczowe cechy:**
     - Definiowanie celów
     - Autonomiczne planowanie
     - Pamięć długoterminowa
     - Samoocena wykonania
   - **Zastosowania:** Automatyzacja złożonych zadań, asystenci badawczy

**A2A (Agent-to-Agent Communication):**
- **Definicja:** Paradygmat komunikacji między autonomicznymi agentami AI
- **Cechy:**
  - Strukturyzowane protokoły komunikacji
  - Wymiana informacji i zadań
  - Negocjacja i koordynacja działań
  - Możliwość tworzenia systemów multi-agentowych
- **Zastosowania:**
  - Symulacje społeczne
  - Automatyzacja złożonych procesów
  - Systemy współpracy człowiek-AI

**MCP (Multi-Agent Collaboration Protocol):**
- **Definicja:** Protokół umożliwiający współpracę wielu agentów AI
- **Cechy:**
  - Definiowanie ról i odpowiedzialności
  - Mechanizmy koordynacji i synchronizacji
  - Rozwiązywanie konfliktów
  - Dzielenie się wiedzą i zasobami
- **Zastosowania:**
  - Złożone systemy decyzyjne
  - Środowiska symulacyjne
  - Automatyzacja pracy zespołowej

**Integracja agentów:**
- API
- Pluginy
- Połączenie z narzędziami zewnętrznymi

**Pytania kontrolne:**
- Czym różni się agent AI od zwykłego modelu?
- Jakie są przykłady zastosowań agentów AI?
- Co to jest architektura ReAct?
- Jakie są różnice między LangChain a Google ADK?
- Co to jest A2A i jakie ma zastosowania?
- Jak MCP wspiera współpracę między agentami?

### 4. Narzędzia Google do AI

**Vertex AI:**
- Platforma do trenowania, wdrażania i zarządzania modelami ML
- Automatyzacja pipeline'ów, monitorowanie modeli

**PaLM API:**
- Dostęp do dużych modeli językowych Google
- Przykłady zapytań, integracja z aplikacjami

**ADK (AI Development Kit):**
- Zestaw narzędzi do tworzenia aplikacji AI
- Integracja z modelami Google (Gemini, PaLM)
- Tworzenie agentów i konwersacyjnych interfejsów
- Narzędzia do testowania i wdrażania rozwiązań AI

**Inne narzędzia:**
- TensorFlow
- Google Colab

**Pytania kontrolne:**
- Do czego służy Vertex AI?
- Jak uzyskać dostęp do PaLM API?
- Jakie są zalety korzystania z Google Colab?
- Czym jest ADK i jakie problemy rozwiązuje?

### 5. Hugging Face i biblioteka Transformers

**Hugging Face:**
- Platforma do udostępniania i korzystania z modeli AI
- Model Hub – ogromna baza gotowych modeli

**Transformers:**
- Biblioteka do pracy z modelami NLP i LLM
- Instalacja: `pip install transformers`
- Przykłady użycia: ładowanie modelu, inferencja, fine-tuning

**Inne biblioteki HF:**
- Datasets
- Tokenizers
- Accelerate

**Pytania kontrolne:**
- Jak zainstalować bibliotekę Transformers?
- Jak znaleźć gotowy model na Hugging Face?
- Do czego służy biblioteka Tokenizers?

### 6. Phidata

**Opis:**
Phidata to narzędzie do budowy agentów AI, automatyzacji zadań i integracji z różnymi usługami.

**Kluczowe cechy:**
- Bazuje na LangChain i innych bibliotekach AI
- Upraszcza tworzenie autonomicznych agentów
- Zapewnia gotowe komponenty i narzędzia
- Wspiera integrację z popularnymi modelami i API

**Przykłady zastosowań:**
- Automatyzacja pracy biurowej
- Integracja z API
- Tworzenie własnych agentów
- Budowanie aplikacji AI-driven

**Różnice w stosunku do LangChain:**
- Wyższy poziom abstrakcji
- Bardziej opiniotwórcza struktura
- Łatwiejszy deployment
- Mniejsza elastyczność, ale szybszy rozwój

**Integracja:**
- Połączenie z narzędziami Google, OpenAI, Hugging Face
- Integracja z bazami danych i API
- Wsparcie dla wektorowych baz danych

**Pytania kontrolne:**
- Do czego można użyć Phidata?
- Jakie są przykłady integracji Phidata z innymi narzędziami?
- Jak zbudować prostego agenta w Phidata?
- Jakie są główne różnice między Phidata a LangChain?

### 7. Przykłady kodu i integracji

**Przykłady:**
- Użycie modelu z Hugging Face Transformers (ładowanie, inferencja, fine-tuning)
- Przykład zapytania do Vertex AI lub PaLM API
- Tworzenie prostego agenta AI
- Integracja Phidata z modelem LLM

**Pytania kontrolne:**
- Jak załadować model GPT-2 z Hugging Face?
- Jak wysłać zapytanie do PaLM API?
- Jak zintegrować agenta z zewnętrznym API?

### 8. Słowniczek pojęć

**Opis:**
Zbiór najważniejszych terminów i skrótów używanych w AI, ML i agentach.

**Przykładowe hasła:**
- **LLM** - Large Language Model (Duży Model Językowy)
- **A2A** - Agent-to-Agent Communication (Komunikacja między agentami)
- **MCP** - Multi-Agent Collaboration Protocol (Protokół współpracy wielu agentów)
- **ADK** - AI Development Kit (Zestaw narzędzi do tworzenia aplikacji AI)
- **RAG** - Retrieval-Augmented Generation (Generowanie wspierane wyszukiwaniem)
- **Inferencja** - Proces generowania przewidywań przez model
- **Fine-tuning** - Dostrajanie wstępnie wytrenowanego modelu
- **Prompt** - Tekst wejściowy dla modelu językowego
- **Tokenizacja** - Proces dzielenia tekstu na mniejsze jednostki (tokeny)
- **Pipeline** - Sekwencja przetwarzania danych

**Pytania kontrolne:**
- Co oznacza skrót LLM?
- Czym jest tokenizacja?
- Na czym polega fine-tuning?
- Co to jest RAG i jakie ma zastosowania?

### 9. Linki i materiały dodatkowe

**Oficjalna dokumentacja:**
- Hugging Face: https://huggingface.co/docs
- Google Vertex AI: https://cloud.google.com/vertex-ai/docs
- Phidata: https://docs.phidata.com/
- LangChain: https://python.langchain.com/
- Google ADK: https://ai.google.dev/

**Tutoriale, kursy, blogi:**
- Kursy na Coursera, Udemy
- Blog Hugging Face
- YouTube: Two Minute Papers, StatQuest

**Społeczności i fora:**
- Stack Overflow
- Discord Hugging Face
- Reddit r/MachineLearning

**Pytania kontrolne:**
- Gdzie znaleźć oficjalną dokumentację do Transformers?
- Jakie są polecane źródła do nauki AI?
- Gdzie szukać pomocy, gdy napotkasz problem z modelem?

