# Large Language Models (LLM)

Ten katalog zawiera materiały dotyczące Large Language Models (LLM) - dużych modeli językowych.

## 📚 Zawartość katalogu

### Pliki

- **[current_models.md](current_models.md)** - Aktualne modele LLM (2024-2025)
- **[architecture_comparison.md](architecture_comparison.md)** - Porównanie architektur LLM
- **[training_methods.md](training_methods.md)** - Metody trenowania LLM
- **[evaluation_benchmarks.md](evaluation_benchmarks.md)** - Benchmarki i ewaluacja LLM

## 🎯 Czym są LLM?

Large Language Models (LLM) to modele sztucznej inteligencji trenowane na ogromnych zbiorach danych tekstowych, zdolne do:
- Generowania tekstu podobnego do ludzkiego
- Rozumienia i przetwarzania języka naturalnego
- Wykonywania zadań bez specjalnego treningu (few-shot learning)
- Transferu wiedzy między różnymi domenami

## 🔧 Kluczowe cechy LLM

### Architektura
- **Transformer-based** - oparte na architekturze Transformer
- **Attention mechanism** - mechanizm uwagi do przetwarzania kontekstu
- **Massive scale** - miliardy parametrów (GPT-4: ~1.7T parametrów)
- **Pre-training** - wstępne trenowanie na ogromnych zbiorach danych

### Możliwości
- **Generative AI** - generowanie tekstu, kodu, obrazów
- **Reasoning** - rozumowanie i wnioskowanie
- **Multimodal** - przetwarzanie tekstu, obrazów, audio
- **Tool use** - korzystanie z zewnętrznych narzędzi i API

## 🏗️ Rodzaje LLM

### Według architektury
- **Decoder-only** - GPT, LLaMA, PaLM
- **Encoder-only** - BERT, RoBERTa
- **Encoder-Decoder** - T5, BART, FLAN-T5

### Według rozmiaru
- **Small** (< 1B parametrów) - dla urządzeń edge
- **Medium** (1B - 10B parametrów) - zrównoważone
- **Large** (10B - 100B parametrów) - wysokie możliwości
- **Very Large** (> 100B parametrów) - cutting-edge

### Według dostępności
- **Closed-source** - GPT-4, Claude, Gemini
- **Open-source** - LLaMA, Mistral, CodeLlama
- **Open-weight** - dostępne wagi modeli

## 🎯 Aktualne trendy (2024-2025)

### 1. **Multimodalność**
- Integracja tekstu, obrazów, audio, wideo
- Modele: GPT-4V, Claude 3.5 Sonnet, Gemini 1.5

### 2. **Efektywność**
- **Mixture of Experts (MoE)** - aktywacja tylko części modelu
- **Quantization** - redukcja precyzji bez utraty jakości
- **Pruning** - usuwanie niepotrzebnych parametrów

### 3. **Reasoning i Planning**
- **Chain-of-Thought (CoT)** - krokowe rozumowanie
- **Tree of Thoughts (ToT)** - drzewiaste planowanie
- **ReAct** - rozumowanie + działanie

### 4. **Tool Use i Function Calling**
- Integracja z zewnętrznymi API
- Wywołania funkcji w czasie rzeczywistym
- MCP (Model Context Protocol)

### 5. **Alignment i Safety**
- **RLHF** (Reinforcement Learning from Human Feedback)
- **Constitutional AI** - zasady etyczne
- **Red teaming** - testowanie bezpieczeństwa

## 📊 Porównanie głównych modeli (2024-2025)

| Model | Firma | Rozmiar | Typ | Dostępność | Kluczowe cechy |
|-------|-------|---------|-----|------------|----------------|
| GPT-4 Turbo | OpenAI | ~1.7T | Decoder | Closed | Multimodal, tool use |
| Claude 3.5 Sonnet | Anthropic | ~200B | Decoder | Closed | Constitutional AI |
| Gemini 1.5 Pro | Google | ~175B | Decoder | Closed | Long context (1M tokens) |
| LLaMA 3 70B | Meta | 70B | Decoder | Open-weight | Otwarty, wydajny |
| Mistral 7B | Mistral AI | 7B | Decoder | Open-weight | Mały ale wydajny |
| CodeLlama 70B | Meta | 70B | Decoder | Open-weight | Specjalizacja kod |

## 🔧 Metody trenowania

### 1. **Pre-training**
- **Masked Language Modeling** (BERT)
- **Causal Language Modeling** (GPT)
- **Span Corruption** (T5)

### 2. **Fine-tuning**
- **Supervised Fine-tuning (SFT)**
- **Reinforcement Learning from Human Feedback (RLHF)**
- **Direct Preference Optimization (DPO)**

### 3. **Prompt Engineering**
- **Few-shot learning**
- **Chain-of-thought prompting**
- **System prompts**

## ⚠️ Wyzwania i ograniczenia

### Techniczne
- **Hallucinations** - generowanie nieprawdziwych informacji
- **Context window** - ograniczenia długości kontekstu
- **Computational cost** - wysokie koszty trenowania i inferencji
- **Bias** - uprzedzenia w danych treningowych

### Etyczne
- **Privacy** - ochrona danych osobowych
- **Safety** - zapobieganie szkodliwym zastosowaniom
- **Transparency** - zrozumiałość decyzji modelu
- **Accountability** - odpowiedzialność za wyniki

## 🎯 Zastosowania

### Produkcyjne
- **Chatbots i asystenci** - ChatGPT, Claude, Bard
- **Generowanie kodu** - GitHub Copilot, CodeWhisperer
- **Analiza dokumentów** - podsumowywanie, ekstrakcja informacji
- **Tłumaczenie** - automatyczne tłumaczenie tekstu

### Badawcze
- **Nauka o języku** - analiza semantyczna
- **Psychologia** - modelowanie procesów poznawczych
- **Filozofia** - badania nad świadomością i inteligencją

## 🔗 Powiązane tematy

- **Transformers** - architektura bazowa
- **NLP** - przetwarzanie języka naturalnego
- **MCP** - protokół komunikacji z systemami zewnętrznymi
- **RAG** - retrieval-augmented generation
- **Prompt Engineering** - optymalizacja promptów

## 📚 Źródła

- [Papers With Code - Language Models](https://paperswithcode.com/task/language-modelling)
- [Hugging Face Model Hub](https://huggingface.co/models)
- [OpenAI Research](https://openai.com/research)
- [Anthropic Research](https://www.anthropic.com/research)
- [Google AI Blog](https://ai.googleblog.com/) 