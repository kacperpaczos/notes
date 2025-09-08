# 🔧 Feature Engineering - Inżynieria cech

## Cel

Poznanie metod i technik tworzenia, selekcji i optymalizacji cech (features) w projektach Machine Learning.

## Problem

Jak przekształcić surowe dane w informacyjne cechy, które poprawią wydajność modeli ML i zwiększą ich zdolność predykcyjną.

## Pojęcia kluczowe

### 1. Definicja Feature Engineering

**Feature Engineering** to proces tworzenia nowych cech lub modyfikacji istniejących w celu:
- Poprawy wydajności modeli ML
- Zwiększenia zdolności predykcyjnej
- Redukcji wymiarowości danych
- Uwzględnienia wiedzy domenowej

### 2. Typy cech

#### Cechy numeryczne
- **Continuous features:** wiek, cena, temperatura
- **Discrete features:** liczba dzieci, oceny
- **Count features:** liczba zakupów, odwiedzin

#### Cechy kategoryczne
- **Nominal:** kolor, marka, region
- **Ordinal:** poziom wykształcenia, ocena jakości
- **Binary:** płeć, czy ma konto

#### Cechy czasowe
- **Datetime features:** data, godzina, dzień tygodnia
- **Seasonal features:** miesiąc, kwartał, sezon
- **Time-based aggregations:** średnia miesięczna, trendy

### 3. Główne techniki Feature Engineering

#### Transformacje numeryczne
- **Scaling:** MinMaxScaler, StandardScaler, RobustScaler
- **Normalization:** L1, L2 normalization
- **Log transformation:** dla danych z rozkładem wykładniczym
- **Box-Cox transformation:** stabilizacja wariancji

#### Encoding kategoryczne
- **One-Hot Encoding:** dla zmiennych nominalnych
- **Label Encoding:** dla zmiennych ordinalnych
- **Target Encoding:** kodowanie na podstawie target variable
- **Frequency Encoding:** kodowanie na podstawie częstotliwości

#### Cechy interakcji
- **Polynomial features:** x², x³, x₁×x₂
- **Cross features:** kombinacje cech kategorycznych
- **Ratio features:** stosunki między cechami
- **Difference features:** różnice między cechami

#### Cechy agregacyjne
- **Statistical aggregations:** mean, median, std, min, max
- **Time-based aggregations:** rolling mean, exponential smoothing
- **Group-based aggregations:** średnie w grupach użytkowników
- **Ranking features:** percentyle, rankingi

## Struktura / Diagram

```
Feature Engineering Pipeline
├── 1. Feature Creation
│   ├── Domain Knowledge Features
│   ├── Mathematical Transformations
│   ├── Interaction Features
│   └── Aggregation Features
│
├── 2. Feature Transformation
│   ├── Scaling & Normalization
│   ├── Encoding Categorical
│   ├── Handling Missing Values
│   └── Outlier Treatment
│
├── 3. Feature Selection
│   ├── Statistical Methods
│   ├── Model-based Selection
│   ├── Recursive Feature Elimination
│   └── Feature Importance
│
├── 4. Feature Validation
│   ├── Cross-validation
│   ├── Feature Stability
│   ├── Data Leakage Check
│   └── Performance Impact
│
└── 5. Feature Storage
    ├── Feature Store
    ├── Versioning
    ├── Monitoring
    └── Reusability
```

## Przepływ działania

### Faza 1: Analiza i planowanie
1. **Domain analysis:** zrozumienie problemu biznesowego
2. **Data exploration:** analiza dostępnych danych
3. **Feature planning:** planowanie nowych cech
4. **Priority setting:** określenie priorytetów

### Faza 2: Tworzenie cech
1. **Basic features:** podstawowe transformacje
2. **Domain features:** cechy oparte na wiedzy eksperckiej
3. **Interaction features:** kombinacje cech
4. **Aggregation features:** cechy zagregowane

### Faza 3: Transformacja i czyszczenie
1. **Scaling:** normalizacja cech numerycznych
2. **Encoding:** kodowanie cech kategorycznych
3. **Missing values:** obsługa brakujących wartości
4. **Outliers:** identyfikacja i obsługa wartości odstających

### Faza 4: Selekcja cech
1. **Statistical selection:** testy statystyczne
2. **Model-based selection:** selekcja na podstawie modeli
3. **Recursive elimination:** iteracyjna eliminacja
4. **Feature importance:** analiza ważności cech

### Faza 5: Walidacja i optymalizacja
1. **Cross-validation:** walidacja krzyżowa
2. **Performance testing:** testowanie wydajności
3. **Stability check:** sprawdzenie stabilności
4. **Final optimization:** końcowa optymalizacja

## Przykłady użycia

### W systemie rekomendacyjnym:
- **User features:** wiek, lokalizacja, preferencje
- **Item features:** kategoria, cena, popularność
- **Interaction features:** czas spędzony, liczba kliknięć
- **Behavioral features:** wzorce zakupowe, częstotliwość

### W analizie predykcyjnej:
- **Temporal features:** trendy czasowe, sezonowość
- **Lag features:** wartości z poprzednich okresów
- **Rolling statistics:** średnie ruchome, odchylenia
- **Ratio features:** stosunki między zmiennymi

### W analizie tekstu:
- **TF-IDF:** term frequency-inverse document frequency
- **Word embeddings:** Word2Vec, GloVe
- **N-grams:** bigramy, trigramy
- **Sentiment features:** analiza sentymentu

## Metryki i wskaźniki

### Jakość cech
- **Feature importance:** ważność w modelu
- **Correlation:** korelacja z target variable
- **Variance:** wariancja cechy
- **Information gain:** przyrost informacji

### Wydajność modelu
- **Accuracy improvement:** poprawa dokładności
- **AUC improvement:** poprawa AUC
- **RMSE reduction:** redukcja błędu
- **Training time:** czas treningu

### Stabilność cech
- **Feature stability:** stabilność w czasie
- **Distribution shift:** zmiana rozkładu
- **Correlation stability:** stabilność korelacji
- **Missing rate:** wskaźnik brakujących wartości

## Wskazówki praktyczne

### Narzędzia
- **Python:** pandas, scikit-learn, feature-engine
- **R:** caret, mlr, recipes
- **SQL:** agregacje, window functions
- **Feature stores:** Feast, Tecton, Hopsworks

### Best practices
- **Start simple:** zacznij od prostych cech
- **Domain knowledge:** wykorzystaj wiedzę ekspercką
- **Iterative approach:** iteracyjne ulepszanie
- **Validation:** waliduj każdą cechę
- **Documentation:** dokumentuj proces

### Częste błędy
- **Data leakage:** wyciek danych z przyszłości
- **Overfitting:** zbyt skomplikowane cechy
- **Correlation bias:** ignorowanie korelacji
- **Missing validation:** brak walidacji cech

## Powiązane tematy

- **Data Preprocessing:** przygotowanie danych
- **Feature Selection:** selekcja cech
- **Model Evaluation:** ocena modeli
- **Data Quality:** jakość danych
- **Domain Knowledge:** wiedza ekspercka

## Kluczowe przesłanie

- **Feature Engineering jest kluczowe** dla sukcesu modeli ML
- **Domain knowledge** jest niezbędna dla tworzenia dobrych cech
- **Iteracyjny proces** - ulepszanie cech w czasie
- **Walidacja** każdej cechy przed użyciem
- **Balance** między złożonością a interpretowalnością

## Następne kroki

Po Feature Engineering przejdź do:
- [Model Evaluation & Metrics](model-evaluation-metrics.md) - ocena modeli
- [Feature Selection](feature-selection.md) - selekcja cech
- [Model Development](../module-02-deep-learning/) - budowa modeli

## Źródła / dalsza lektura

- "Feature Engineering for Machine Learning" - Alice Zheng
- "Hands-On Machine Learning" - Aurélien Géron
- "Python for Data Analysis" - Wes McKinney
- Dokumentacja scikit-learn, pandas
- Kaggle: Feature Engineering competitions
