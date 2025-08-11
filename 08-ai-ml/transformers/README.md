# Architektura Transformer

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


## Czym jest Transformer?
Transformer to model oparty na mechanizmie **self-attention**, wprowadzony w pracy ["Attention Is All You Need"](https://arxiv.org/abs/1706.03762) przez zespół Google w 2017 roku. Jest szeroko stosowany w NLP, ale także w innych dziedzinach (np. generowanie obrazów, wideo, muzyki).

## Kluczowe komponenty:
1. **Self-Attention**  
   - Pozwala modelowi "patrzeć" na inne słowa w zdaniu, aby lepiej zrozumieć kontekst.
   - Każde słowo może zwracać uwagę na wszystkie inne słowa w sekwencji.
   - Multi-head attention umożliwia modelowi koncentrację na różnych aspektach jednocześnie.

2. **Encoder-Decoder**  
   - **Encoder** przetwarza dane wejściowe, tworząc reprezentacje kontekstowe.
   - **Decoder** generuje wyniki (np. tłumaczenie) na podstawie reprezentacji z encodera.
   - Niektóre modele używają tylko encodera (np. BERT) lub tylko decodera (np. GPT).

3. **Positional Encoding**  
   - Dodaje informację o kolejności słów (brak rekurencji jak w RNN).
   - Umożliwia modelowi zrozumienie porządku sekwencyjnego mimo równoległego przetwarzania.

4. **Feed-Forward Networks**  
   - Sieci neuronowe przetwarzające reprezentacje dla każdej pozycji niezależnie.
   - Zwiększają moc ekspresyjną modelu.

## Zalety:
- **Równoległe przetwarzanie** - szybsze niż RNN/LSTM.
- **Lepsza obsługa długich zależności** - dzięki self-attention.
- **Skalowalność** - możliwość trenowania ogromnych modeli (np. GPT-3, PaLM).
- **Brak problemu zanikających gradientów** - typowego dla RNN.

## Popularne modele bazujące na Transformerach:
- **BERT** (Google) - dwukierunkowy encoder, dobry do zadań rozumienia języka.
- **GPT** (OpenAI) - jednokierunkowy decoder, specjalizuje się w generowaniu tekstu.
- **T5** (Google) - model encoder-decoder, uniwersalny dla wielu zadań NLP.
- **BART** (Facebook) - encoder-decoder z dwukierunkowym encoderem i jednokierunkowym decoderem.

## Zastosowania:
- Tłumaczenie maszynowe
- Generowanie tekstu
- Podsumowywanie
- Odpowiadanie na pytania
- Analiza sentymentów
- Generowanie kodu

## Jak działają Transformery? - Uproszczony schemat:

1. **Tokenizacja** - zamiana tekstu na tokeny (słowa lub części słów)
2. **Embedding** - przekształcenie tokenów na wektory liczbowe
3. **Positional Encoding** - dodanie informacji o pozycji
4. **Self-Attention** - przetwarzanie z uwzględnieniem kontekstu
5. **Feed-Forward Networks** - dodatkowe przetwarzanie
6. **Normalizacja i residual connections** - stabilizacja uczenia
7. **Ostatnia warstwa** - przewidywanie wyników (np. następnego tokenu)

## Pytania kontrolne:
- Na czym polega mechanizm self-attention?
- Jakie są główne różnice między BERT a GPT?
- Dlaczego Transformery są efektywniejsze od RNN/LSTM w długich sekwencjach?
- Co to jest multi-head attention? 