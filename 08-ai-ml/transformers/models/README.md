# Popularne modele oparte na architekturze Transformer

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


## Modele typu Encoder

### BERT (Bidirectional Encoder Representations from Transformers)
- **Twórca:** Google (2018)
- **Architektura:** Dwukierunkowy encoder
- **Rozmiar:** Od 110M (BERT-base) do 340M (BERT-large) parametrów
- **Kluczowe cechy:** 
  - Wykorzystuje dwukierunkowy kontekst (lewy i prawy)
  - Trenowany na zadaniach Masked Language Modeling i Next Sentence Prediction
  - Dobry do zadań rozumienia języka
- **Zastosowania:** Klasyfikacja tekstu, odpowiadanie na pytania, NER

### RoBERTa (Robustly Optimized BERT Pretraining Approach)
- **Twórca:** Facebook/Meta (2019)
- **Architektura:** Udoskonalony BERT
- **Kluczowe cechy:**
  - Dłuższy trening na większych danych
  - Usunięcie zadania Next Sentence Prediction
  - Dynamiczne maskowanie
  - Lepsze wyniki niż oryginalny BERT

## Modele typu Decoder

### GPT (Generative Pre-trained Transformer)
- **Twórca:** OpenAI
- **Architektura:** Jednokierunkowy decoder (generatywny)
- **Wersje:**
  - GPT-1 (2018): 117M parametrów
  - GPT-2 (2019): do 1.5B parametrów
  - GPT-3 (2020): do 175B parametrów
  - GPT-4 (2023): rozmiar nieujawniony
- **Kluczowe cechy:**
  - Autoregresyjne przewidywanie następnego tokenu
  - Trening nienadzorowany na ogromnych zbiorach tekstu
  - Z każdą wersją znacząco zwiększona liczba parametrów
- **Zastosowania:** Generowanie tekstu, tłumaczenie, odpowiadanie na pytania, pisanie kodu

### LLaMA (Large Language Model Meta AI)
- **Twórca:** Meta
- **Architektura:** Jednokierunkowy decoder
- **Wersje:**
  - LLaMA 1 (2023): od 7B do 65B parametrów
  - LLaMA 2 (2023): od 7B do 70B parametrów
- **Kluczowe cechy:**
  - Otwarty model (dostępny dla badaczy)
  - Efektywna architektura wymagająca mniej zasobów
  - Podstawa dla wielu dostrojonych modeli (np. Alpaca, Vicuna)
- **Zastosowania:** Podobne do GPT, bazowy model dla wielu projektów

## Modele typu Encoder-Decoder

### T5 (Text-to-Text Transfer Transformer)
- **Twórca:** Google (2019)
- **Architektura:** Encoder-decoder
- **Rozmiar:** Od 60M do 11B parametrów
- **Kluczowe cechy:**
  - Wszystkie zadania NLP sformułowane jako zadania text-to-text
  - Jednolite podejście do wielu zadań NLP
  - Trening wielozadaniowy
- **Zastosowania:** Uniwersalny model do różnych zadań NLP

### BART (Bidirectional and Auto-Regressive Transformers)
- **Twórca:** Facebook/Meta (2019)
- **Architektura:** Encoder-decoder
- **Kluczowe cechy:**
  - Łączy dwukierunkowy encoder (jak BERT) z jednokierunkowym decoderem (jak GPT)
  - Trenowany na zadaniach rekonstrukcji tekstu
  - Dobry do zadań generatywnych
- **Zastosowania:** Podsumowywanie, tłumaczenie, generowanie tekstu

## Modele Multimodalne

### CLIP (Contrastive Language-Image Pre-training)
- **Twórca:** OpenAI (2021)
- **Architektura:** Dwa encodery (tekst i obraz)
- **Kluczowe cechy:**
  - Łączy przetwarzanie tekstu i obrazu
  - Trenowany na 400 milionach par tekst-obraz
  - Zrozumienie semantyczne obrazów
- **Zastosowania:** Wyszukiwanie obrazów, generowanie tekstu na podstawie obrazów

### DALL-E
- **Twórca:** OpenAI
- **Architektura:** Bazuje na GPT-3 i CLIP
- **Kluczowe cechy:**
  - Generowanie obrazów na podstawie opisu tekstowego
  - Rozumienie złożonych instrukcji
  - Kreatywność i elastyczność
- **Zastosowania:** Generowanie obrazów, projektowanie, sztuka

## Pytania kontrolne:
- Jakie są główne różnice między BERT a GPT?
- Dlaczego T5 jest nazywany "uniwersalnym" modelem?
- Co oznacza, że model jest "autoregresyjny"?
- Jakie zadania najlepiej realizują modele typu encoder, a jakie modele typu decoder? 