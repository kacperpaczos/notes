# 📚 Module 1: Fundamentals of AI & Machine Learning

## Cel

Poznanie podstawowych koncepcji sztucznej inteligencji i uczenia maszynowego, ze szczególnym uwzględnieniem różnic między różnymi rodzajami uczenia i ich praktycznych zastosowań.

## Problem

Zrozumienie struktury AI/ML i umiejętność rozróżnienia między różnymi technikami uczenia maszynowego oraz ich zastosowań w realnym świecie.

## Pojęcia kluczowe

### 1. Wprowadzenie do AI/ML

* **AI** – szeroka dziedzina obejmująca computer vision, NLP, generative AI, ML, deep learning
* **ML** – podzbiór AI, uczy się na danych, wymaga feature engineering
* **Deep Learning** – używa głębokich sieci neuronowych, sam wyciąga cechy z danych złożonych

### 2. Rodzaje uczenia maszynowego

* **Supervised learning** – na danych oznakowanych
* **Unsupervised learning** – bez etykiet, szukanie wzorców
* **Semi-supervised learning** – część danych oznakowana, model sam dociąga resztę
* **Reinforcement learning** – agent uczy się przez interakcję ze środowiskiem

### 3. Techniki ML

* **Classification** – przypisanie do kategorii
* **Regression** – przewidywanie wartości liczbowych
* **Clustering** – grupowanie podobnych danych
* **Association** – wyszukiwanie powiązań (np. produkty kupowane razem)
* **Anomaly detection** – wykrywanie nietypowych przypadków
* **Sequence mining** – przewidywanie kolejnych zdarzeń
* **Dimension reduction** – redukcja liczby cech (np. PCA)
* **Recommendation systems** – rekomendacje (np. Netflix, Amazon)
  * **Content-Based Filtering** – podobieństwo na podstawie zawartości produktów
  * **Collaborative Filtering** – podobieństwo na podstawie zachowań użytkowników
  * **Hybrid approaches** – kombinacja obu technik dla najlepszych wyników

### 4. Cykl życia modelu ML (Machine Learning Model Lifecycle)

#### Cele nauki
- **Opisać znaczenie i wymagania każdego procesu w cyklu życia modelu ML**
- **Nazwać procesy, które są bardziej czasochłonne od innych**

#### 1. Problem Definition (Definicja problemu)
**Znaczenie:** Zapewnienie zgodności rozwiązania ML z potrzebami biznesowymi
**Kluczowe elementy:**
- Identyfikacja pain point użytkowników
- Definicja celów biznesowych
- Określenie metryk sukcesu

#### 2. Data Collection (Zbieranie danych) ⭐⭐⭐⭐⭐
**Czasochłonność:** Bardzo wysoka - wymaga zrozumienia wielu źródeł danych

**Typy danych:**
- **Dane użytkowników:** demografia, historia zakupów, transakcje
- **Dane produktów:** inwentarz, składniki, popularność, oceny
- **Dane behawioralne:** zapisane produkty, polubienia, historia wyszukiwań

**ETL Process:**
- **Extract:** zbieranie z różnych źródeł
- **Transform:** agregacja, łączenie, mapowanie
- **Load:** centralizacja w jednym miejscu

#### 3. Data Preparation (Przygotowanie danych) ⭐⭐⭐⭐⭐
**Czasochłonność:** Najwyższa - wymaga uwagi do szczegółów
**Nakładanie:** Często równolegle z Data Collection

**Kluczowe zadania:**
- **Data cleaning:** filtrowanie, usuwanie wartości ekstremalnych
- **Missing values:** usuwanie lub generowanie zastępczych
- **Formatowanie:** standardyzacja formatów (daty, stringi)
- **Feature Engineering:** tworzenie nowych cech

**Przykłady feature engineering:**
- Średni czas między transakcjami
- Najczęściej kupowane produkty
- Mapowanie problemów skórnych do produktów

#### 4. Exploratory Data Analysis (EDA)
**Cel:** Zrozumienie danych przed modelowaniem

**Techniki:**
- **Wizualizacje:** identyfikacja wzorców
- **Walidacja ekspercka:** weryfikacja z wiedzą domenową
- **Analiza korelacji:** identyfikacja ważnych zmiennych
- **Strategia podziału:** train/test split

**Strategia podziału danych:**
- Najnowsze transakcje → test set
- Zapewnienie reprezentacji użytkowników w train set

**📊 Szczegółowy przewodnik:** [Exploratory Data Analysis](exploratory-data-analysis.md)

**🔧 Powiązane tematy:**
- [Feature Engineering](feature-engineering.md) - inżynieria cech
- [Data Quality & Preprocessing](data-quality-preprocessing.md) - jakość danych
- [Model Evaluation & Metrics](model-evaluation-metrics.md) - ocena modeli
- [A/B Testing w ML](ab-testing-ml.md) - testowanie modeli
- [Model Monitoring](model-monitoring.md) - monitorowanie modeli

#### 5. Model Development ⭐⭐⭐
**Czasochłonność:** Średnia
**Praktyka:** Wykorzystanie istniejących frameworków

**Techniki rekomendacyjne:**

**Content-Based Filtering:**
- Podobieństwo produktów na podstawie zawartości
- Similarity score między produktami
- Uwzględnianie preferencji użytkowników

**Collaborative Filtering:**
- Podobieństwo użytkowników na podstawie zachowań
- Grupowanie: wiek, region, typ skóry, oceny
- Rekomendacje na podstawie ocen grupy

**Hybrid Model:** Kombinacja obu technik

#### 6. Model Evaluation ⭐⭐⭐
**Czasochłonność:** Średnia

**Fazy oceny:**
1. **Model tuning:** optymalizacja parametrów
2. **A/B testing:** eksperymenty z użytkownikami
3. **Metryki sukcesu:** oceny, kliknięcia, konwersje

#### 7. Model Deployment ⭐⭐⭐⭐
**Czasochłonność:** Wysoka - integracja z systemami

**Elementy:**
- Integracja z aplikacjami/stronami
- **Continuous monitoring:** śledzenie wydajności
- **Iterative improvements:** retraining na nowych danych

#### Charakterystyka cyklu życia
- **Iteracyjny:** powrót do wcześniejszych kroków przy problemach
- **Continuous monitoring:** stałe śledzenie po wdrożeniu
- **Najbardziej czasochłonne:** Data Preparation i Data Collection
- **Krytyczność:** Każdy krok jest niezbędny dla sukcesu

#### ETL Process (Extract, Transform, Load)

Proces **Data Collection and Preparation** jest również nazywany **ETL**:

* **Extract** – zbieranie danych z różnych źródeł
* **Transform** – czyszczenie i transformacja danych
* **Load** – przechowywanie przetworzonych danych w jednym miejscu

Przetworzone dane stają się dostępne dla inżyniera ML, umożliwiając zadania takie jak budowanie modeli ML.

## Struktura / Diagram

```
AI (szeroka dziedzina)
├── Computer Vision
├── NLP
├── Generative AI
├── ML (podzbiór AI)
│   ├── Deep Learning (głębokie sieci neuronowe)
│   └── Traditional ML (wymaga feature engineering)
│
├── Rodzaje uczenia:
│   ├── Supervised Learning
│   ├── Unsupervised Learning
│   ├── Semi-supervised Learning
│   └── Reinforcement Learning
│
├── Techniki ML:
│   ├── Classification vs Regression
│   ├── Clustering
│   ├── Association
│   ├── Anomaly Detection
│   └── Recommendation Systems
│
└── Cykl życia modelu ML (iteracyjny):
    ├── 1. Problem Definition
    │   ├── Określenie problemu/sytuacji
    │   └── Business alignment (pain points)
    ├── 2. Data Collection (⭐⭐⭐⭐⭐)
    │   ├── ETL: Extract → Transform → Load
    │   ├── User data, Product data, Behavioral data
    │   └── Data wrangling & aggregation
    ├── 3. Data Preparation (⭐⭐⭐⭐⭐)
    │   ├── Data cleaning & formatting
    │   ├── Feature engineering
    │   └── Exploratory Data Analysis (EDA)
    ├── 4. Model Development (⭐⭐⭐)
    │   ├── Content-Based Filtering
    │   ├── Collaborative Filtering
    │   └── Hybrid approaches
    ├── 5. Model Evaluation (⭐⭐⭐)
    │   ├── A/B testing & user feedback
    │   └── Success metrics tracking
    └── 6. Model Deployment (⭐⭐⭐⭐)
        ├── Production integration
        ├── Continuous monitoring
        └── Iterative improvements
```

## Przepływ działania

### Cykl życia modelu ML:

1. **Problem Definition** – określenie problemu lub sytuacji
2. **Data Collection** – zbieranie danych (ETL process)
3. **Data Preparation** – przygotowanie i czyszczenie danych
4. **Model Development & Evaluation** – budowa i ocena modelu
5. **Model Deployment** – wdrażanie modelu do produkcji

### Podejście do uczenia maszynowego:

1. **Czym jest ML i jakie są rodzaje uczenia**
2. **Jakie techniki ML istnieją i kiedy się je stosuje**
3. **Przykłady zastosowań w realnym świecie**

**Uwaga:** Cykl życia ML jest **iteracyjny** - często wracamy do wcześniejszych kroków przy napotkaniu problemów.

## Przykłady użycia

### Zastosowania praktyczne:

* **Medycyna** – wykrywanie nowotworów na podstawie cech komórek
* **Bankowość** – decyzje kredytowe
* **Telekomunikacja** – przewidywanie odejścia klienta (churn)
* **E-commerce/Streaming** – systemy rekomendacji
* **Computer vision** – klasyfikacja kotów i psów
* **Codzienność** – chatboty, rozpoznawanie twarzy, gry (np. szachy)

### Przykład wizualny:
Klasyfikacja vs regresja z klastrami (kolory: zielony, czerwony, niebieski + czarne jako "szum")

## Kluczowe przesłanie

### Z cyklu życia modelu ML:
* **Wszystkie procesy są ważne** dla sukcesu rozwiązania ML
* **Najbardziej czasochłonne procesy:** Data Collection i Data Preparation
* **Ciągły monitoring po wdrożeniu** jest wymagany do utrzymania jakości
* **Iteracyjne ulepszenia:** retraining modelu na nowych danych rozszerza jego możliwości
* **ETL process**: Extract (zbieranie) → Transform (czyszczenie) → Load (przechowywanie)
* **Techniki rekomendacyjne:** Content-Based Filtering + Collaborative Filtering = hybrydowy model
* **Kluczowe metryki sukcesu:** oceny użytkowników, kliknięcia, konwersje zakupowe

### Z podstawowych koncepcji AI/ML:
* ML to **podzbiór AI**
* Modele ML mogą być **supervised, unsupervised, semi-supervised, reinforcement**
* Główne techniki: classification, regression, clustering, association, anomaly detection, sequence mining, dimension reduction, recommendation systems
* Zastosowania: od medycyny, przez bankowość, po rozrywkę i codzienne życie

## Następne kroki

Przejdź do [Module 2: Deep Learning](../module-02-deep-learning/) aby poznać głębokie sieci neuronowe i zaawansowane techniki ML.

## Źródła / dalsza lektura

- IBM Generative AI Engineering - materiał wideo
- Kursy ML/AI na platformach edukacyjnych
- Dokumentacja bibliotek ML (scikit-learn, TensorFlow, PyTorch)
