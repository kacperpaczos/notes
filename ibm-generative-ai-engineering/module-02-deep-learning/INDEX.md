# 🧠 Module 2: Deep Learning

## Cel

Opanowanie koncepcji głębokiego uczenia się, architektur sieci neuronowych oraz technik treningu i optymalizacji modeli DL.

## Problem

Zrozumienie jak działają głębokie sieci neuronowe, jak je trenować oraz jak optymalizować ich wydajność.

## Pojęcia kluczowe

### 1. Architektury sieci neuronowych

* **Perceptron** – podstawowa jednostka sieci neuronowej
* **Sieci wielowarstwowe (MLP)** – Multi-Layer Perceptron
* **Sieci konwolucyjne (CNN)** – Convolutional Neural Networks
* **Sieci rekurencyjne (RNN)** – Recurrent Neural Networks
* **Transformery** – nowoczesne architektury dla NLP

### 2. Funkcje aktywacji

* **ReLU** – Rectified Linear Unit
* **Sigmoid** – dla klasyfikacji binarnej
* **Softmax** – dla klasyfikacji wieloklasowej
* **Tanh** – hiperboliczny tangens

### 3. Techniki treningu

* **Backpropagation** – algorytm wstecznej propagacji błędu
* **Gradient descent** – optymalizacja gradientowa
* **Batch, Mini-batch, Stochastic GD** – różne podejścia do treningu
* **Regularization** – zapobieganie przeuczeniu

## Struktura / Diagram

```
Deep Learning Architecture
├── Input Layer
├── Hidden Layers
│   ├── Dense Layers (MLP)
│   ├── Convolutional Layers (CNN)
│   └── Recurrent Layers (RNN/LSTM)
├── Output Layer
│
├── Training Process:
│   ├── Forward Pass
│   ├── Loss Calculation
│   ├── Backward Pass (Backprop)
│   └── Parameter Update (GD)
│
└── Optimization:
    ├── Adam, RMSProp, SGD
    ├── Learning Rate Scheduling
    └── Regularization (Dropout, L2)
```

## Przepływ działania

1. **Przygotowanie danych** – normalizacja, augmentacja
2. **Definicja architektury** – wybór typu sieci i warstw
3. **Kompilacja modelu** – wybór optymalizatora, funkcji straty
4. **Trening** – iteracyjny proces uczenia
5. **Ewaluacja** – ocena wydajności modelu

## Przykłady użycia

* **Computer Vision** – CNN dla rozpoznawania obrazów
* **NLP** – RNN/LSTM dla przetwarzania języka
* **Generowanie treści** – GAN dla generatywnych zadań
* **Predykcje szeregów czasowych** – RNN dla prognoz

## Kluczowe przesłanie

* DL wymaga dużych ilości danych i mocy obliczeniowej
* Wybór architektury zależy od typu problemu
* Regularization jest kluczowa dla uniknięcia przeuczenia
* Transfer learning przyspiesza rozwój modeli

## Następne kroki

Przejdź do [Module 3: Generative AI](../module-03-generative-ai/) aby poznać techniki generatywnego AI.

## Materiały dodatkowe

- [Deep Learning Book](https://www.deeplearningbook.org/) - Ian Goodfellow
- [Neural Networks and Deep Learning](http://neuralnetworksanddeeplearning.com/) - Michael Nielsen
- Dokumentacja TensorFlow/PyTorch
