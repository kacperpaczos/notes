# 🎨 Module 3: Generative AI

## Cel

Opanowanie technik generatywnego AI, modeli GAN, VAEs oraz nowoczesnych architektur transformerowych.

## Problem

Zrozumienie jak AI może generować nowe treści, obrazy, tekst i dane, oraz jak wdrażać te technologie w praktyce.

## Pojęcia kluczowe

### 1. Modele Generatywne

* **GAN (Generative Adversarial Networks)** – rywalizacja generatora z dyskryminatorem
* **VAE (Variational Autoencoders)** – probabilistyczne autoenkodery
* **Flow-based models** – normalizujące przepływy
* **Diffusion models** – modele dyfuzyjne (DALL-E, Stable Diffusion)

### 2. Transformery i Large Language Models

* **Transformer architecture** – mechanizm uwagi i pozycjonowania
* **BERT, GPT, T5** – architektury transformerowe
* **Prompt engineering** – sztuka tworzenia efektywnych promptów
* **Fine-tuning** – dostosowywanie modeli do specyficznych zadań

### 3. Techniki generowania

* **Text generation** – generowanie tekstu (GPT, Claude)
* **Image generation** – tworzenie obrazów (DALL-E, Midjourney)
* **Code generation** – generowanie kodu (GitHub Copilot)
* **Music/Audio generation** – tworzenie muzyki i dźwięków

## Struktura / Diagram

```
Generative AI Landscape
├── Traditional Generative Models
│   ├── GANs
│   │   ├── Generator
│   │   └── Discriminator
│   ├── VAEs
│   └── Autoregressive Models
│
├── Modern Approaches
│   ├── Transformer-based
│   │   ├── Encoder-only (BERT)
│   │   ├── Decoder-only (GPT)
│   │   └── Encoder-Decoder (T5)
│   ├── Diffusion Models
│   └── Multimodal Models
│
└── Applications:
    ├── Text Generation
    ├── Image Synthesis
    ├── Code Generation
    └── Creative Content
```

## Przepływ działania

1. **Wybór modelu** – GAN, VAE, Transformer
2. **Przygotowanie danych** – preprocessing i tokenizacja
3. **Trening modelu** – optymalizacja parametrów
4. **Generowanie treści** – sampling z nauczonego rozkładu
5. **Ocena jakości** – metryki jakości generowanych treści

## Przykłady użycia

* **Chatboty i asystenci** – GPT, Claude, Gemini
* **Tworzenie obrazów** – Midjourney, DALL-E, Stable Diffusion
* **Pisanie kodu** – GitHub Copilot, Tabnine
* **Tłumaczenie maszynowe** – Google Translate, DeepL
* **Generowanie muzyki** – AIVA, Suno AI

## Kluczowe przesłanie

* Generatywne AI potrafi tworzyć nowe, oryginalne treści
* Jakość zależy od jakości danych treningowych
* Ethika jest kluczowa - wykrywanie deepfakes, plagiatów
* Modele wymagają ogromnych zasobów obliczeniowych

## Wyzwania i ryzyka

* **Bias w danych** – modele mogą perpetuować uprzedzenia
* **Deepfakes** – fałszywe treści wizualne/audio
* **Prawa autorskie** – generowane treści vs własność intelektualna
* **Zatrudnienie** – automatyzacja kreatywnych zawodów

## Następne kroki

Przejdź do [Module 4: Applications & Deployment](../module-04-applications/) aby poznać praktyczne aspekty wdrażania AI.

## Materiały dodatkowe

- [Generative Deep Learning](https://www.oreilly.com/library/view/generative-deep-learning/9781492041944/) - David Foster
- [The Alignment Problem](https://brianchristian.org/the-alignment-problem/) - Brian Christian
- Dokumentacja OpenAI, Hugging Face, Stability AI
