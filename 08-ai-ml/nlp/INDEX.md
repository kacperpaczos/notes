# NLP - Przetwarzanie Języka Naturalnego

Ten katalog jest częścią sekcji 08-ai-ml i zawiera materiały dotyczące przetwarzania języka naturalnego (NLP).

## Zawartość katalogu

### Podkatalogi

- [sentiment-analysis/](sentiment-analysis/) - Analiza sentymentów
- [machine-translation/](machine-translation/) - Tłumaczenie maszynowe
- [named-entity-recognition/](named-entity-recognition/) - Rozpoznawanie jednostek nazewniczych
- [tokenization/](tokenization/) - Tokenizacja tekstu
- [embeddings/](embeddings/) - Osadzenia wyrazów i zdań

## Transformery w NLP

Transformery zrewolucjonizowały dziedzinę NLP od 2017 roku, wprowadzając nową architekturę opartą na mechanizmie uwagi (attention).

### Kluczowe zalety transformerów w NLP:

- **Równoległe przetwarzanie** - w przeciwieństwie do RNN, transformery przetwarzają całe zdanie jednocześnie
- **Kontekst dwukierunkowy** - modele jak BERT uwzględniają kontekst z obu stron słowa
- **Skalowalność** - możliwość trenowania coraz większych modeli (od BERT do GPT-4)
- **Transfer learning** - możliwość dostrajania wstępnie wytrenowanych modeli do specyficznych zadań

### Porównanie z modelami RNN/LSTM:

| Cecha | Transformery | RNN/LSTM |
|-------|-------------|----------|
| Przetwarzanie | Równoległe | Sekwencyjne |
| Długie zależności | Bardzo dobre | Problematyczne |
| Szybkość treningu | Szybsze | Wolniejsze |
| Wykorzystanie GPU | Efektywne | Mniej efektywne |
| Zanikające gradienty | Brak problemu | Występują |

### Popularne modele transformerowe w NLP:

- **BERT** - Bidirectional Encoder Representations from Transformers
- **GPT** - Generative Pre-trained Transformer
- **T5** - Text-to-Text Transfer Transformer
- **RoBERTa** - Robustly Optimized BERT Pretraining Approach
- **XLNet** - Generalized Autoregressive Pretraining
- **ALBERT** - A Lite BERT

Więcej szczegółów o architekturze transformerów można znaleźć w katalogu: [../transformers/](../transformers/)

## Pytania kontrolne:

- Jakie są główne różnice między modelami opartymi na RNN a transformerami?
- Dlaczego mechanizm self-attention jest kluczowy dla transformerów?
- Które zadania NLP zyskały najwięcej dzięki wprowadzeniu transformerów?
- Jakie są typowe etapy przetwarzania tekstu w pipeline'ach opartych na transformerach?

