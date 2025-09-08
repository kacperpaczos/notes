# 🧹 Data Quality & Preprocessing - Jakość danych i preprocessing

## Cel

Poznanie metod oceny jakości danych, technik preprocessing oraz strategii radzenia sobie z problemami jakościowymi w projektach ML.

## Problem

Jak systematycznie ocenić i poprawić jakość danych, aby zapewnić wysoką wydajność modeli ML i uniknąć błędów wynikających z problemów z danymi.

## Pojęcia kluczowe

### 1. Wymiary jakości danych

#### Completeness (Kompletność)
- **Missing values:** brakujące wartości
- **Coverage:** pokrycie danych
- **Completeness ratio:** wskaźnik kompletności
- **Missing patterns:** wzorce brakujących danych

#### Accuracy (Dokładność)
- **Data validation:** walidacja danych
- **Format compliance:** zgodność z formatem
- **Range validation:** walidacja zakresów
- **Business rule validation:** walidacja reguł biznesowych

#### Consistency (Spójność)
- **Cross-source consistency:** spójność między źródłami
- **Temporal consistency:** spójność czasowa
- **Referential integrity:** integralność referencyjna
- **Format consistency:** spójność formatów

#### Timeliness (Aktualność)
- **Data freshness:** świeżość danych
- **Update frequency:** częstotliwość aktualizacji
- **Latency:** opóźnienie danych
- **SLA compliance:** zgodność z SLA

### 2. Typy problemów z danymi

#### Missing Data (Brakujące dane)
- **MCAR (Missing Completely At Random):** brakujące losowo
- **MAR (Missing At Random):** brakujące zależnie od obserwowanych
- **MNAR (Missing Not At Random):** brakujące zależnie od nieobserwowanych

#### Outliers (Wartości odstające)
- **Statistical outliers:** odstające statystycznie
- **Domain outliers:** odstające w kontekście domeny
- **Global outliers:** odstające globalnie
- **Local outliers:** odstające lokalnie

#### Duplicates (Duplikaty)
- **Exact duplicates:** identyczne rekordy
- **Near duplicates:** prawie identyczne rekordy
- **Fuzzy duplicates:** duplikaty rozmyte
- **Cross-source duplicates:** duplikaty między źródłami

#### Data Inconsistencies (Niespójności)
- **Format inconsistencies:** niespójności formatów
- **Value inconsistencies:** niespójności wartości
- **Schema inconsistencies:** niespójności schematów
- **Temporal inconsistencies:** niespójności czasowe

### 3. Techniki preprocessing

#### Handling Missing Values
- **Deletion:** usuwanie rekordów/kolumn
- **Imputation:** uzupełnianie wartości
  - **Mean/Median/Mode:** uzupełnianie statystykami
  - **Forward/Backward fill:** uzupełnianie czasowe
  - **Interpolation:** interpolacja
  - **Model-based:** uzupełnianie modelami

#### Outlier Treatment
- **Detection methods:**
  - **Statistical:** Z-score, IQR, Modified Z-score
  - **Distance-based:** Local Outlier Factor (LOF)
  - **Density-based:** DBSCAN
  - **Isolation Forest:** las izolacji
- **Treatment strategies:**
  - **Removal:** usuwanie outliers
  - **Capping:** ograniczanie wartości
  - **Transformation:** transformacje
  - **Separate modeling:** osobne modelowanie

#### Data Transformation
- **Scaling:** MinMax, Standard, Robust scaling
- **Normalization:** L1, L2 normalization
- **Log transformation:** transformacja logarytmiczna
- **Box-Cox transformation:** transformacja Box-Cox
- **Power transformation:** transformacja potęgowa

#### Encoding Categorical Data
- **One-Hot Encoding:** kodowanie binarne
- **Label Encoding:** kodowanie etykiet
- **Ordinal Encoding:** kodowanie porządkowe
- **Target Encoding:** kodowanie na podstawie target
- **Frequency Encoding:** kodowanie częstotliwości

## Struktura / Diagram

```
Data Quality & Preprocessing Pipeline
├── 1. Data Quality Assessment
│   ├── Completeness Analysis
│   │   ├── Missing Value Detection
│   │   ├── Coverage Analysis
│   │   └── Completeness Metrics
│   ├── Accuracy Validation
│   │   ├── Format Validation
│   │   ├── Range Validation
│   │   └── Business Rule Validation
│   ├── Consistency Check
│   │   ├── Cross-source Validation
│   │   ├── Temporal Consistency
│   │   └── Referential Integrity
│   └── Timeliness Assessment
│       ├── Data Freshness
│       ├── Update Frequency
│       └── Latency Analysis
│
├── 2. Data Cleaning
│   ├── Missing Value Treatment
│   │   ├── Deletion Strategies
│   │   ├── Imputation Methods
│   │   └── Model-based Imputation
│   ├── Outlier Handling
│   │   ├── Detection Methods
│   │   ├── Treatment Strategies
│   │   └── Validation
│   ├── Duplicate Removal
│   │   ├── Exact Duplicate Detection
│   │   ├── Fuzzy Duplicate Detection
│   │   └── Cross-source Deduplication
│   └── Inconsistency Resolution
│       ├── Format Standardization
│       ├── Value Standardization
│       └── Schema Alignment
│
├── 3. Data Transformation
│   ├── Scaling & Normalization
│   ├── Encoding Categorical
│   ├── Feature Engineering
│   └── Dimensionality Reduction
│
├── 4. Quality Validation
│   ├── Post-cleaning Assessment
│   ├── Statistical Validation
│   ├── Business Validation
│   └── Performance Impact
│
└── 5. Documentation & Monitoring
    ├── Quality Reports
    ├── Change Tracking
    ├── Monitoring Setup
    └── Alert Configuration
```

## Przepływ działania

### Faza 1: Ocena jakości
1. **Data profiling:** profilowanie danych
2. **Quality metrics:** obliczenie metryk jakości
3. **Issue identification:** identyfikacja problemów
4. **Impact assessment:** ocena wpływu na model

### Faza 2: Planowanie czyszczenia
1. **Strategy selection:** wybór strategii czyszczenia
2. **Tool selection:** wybór narzędzi
3. **Process design:** projektowanie procesu
4. **Validation plan:** plan walidacji

### Faza 3: Implementacja czyszczenia
1. **Missing value treatment:** obsługa brakujących wartości
2. **Outlier handling:** obsługa wartości odstających
3. **Duplicate removal:** usuwanie duplikatów
4. **Inconsistency resolution:** rozwiązywanie niespójności

### Faza 4: Transformacja danych
1. **Scaling:** skalowanie cech
2. **Encoding:** kodowanie kategoryczne
3. **Feature engineering:** inżynieria cech
4. **Dimensionality reduction:** redukcja wymiarów

### Faza 5: Walidacja i monitoring
1. **Quality validation:** walidacja jakości
2. **Performance testing:** testowanie wydajności
3. **Monitoring setup:** konfiguracja monitorowania
4. **Documentation:** dokumentacja procesu

## Przykłady użycia

### W systemie rekomendacyjnym:
- **User data cleaning:** czyszczenie danych użytkowników
- **Product data standardization:** standaryzacja danych produktów
- **Interaction data validation:** walidacja danych interakcji
- **Temporal data alignment:** wyrównanie danych czasowych

### W analizie predykcyjnej:
- **Time series preprocessing:** preprocessing szeregów czasowych
- **Missing value imputation:** uzupełnianie brakujących wartości
- **Outlier detection:** wykrywanie wartości odstających
- **Feature scaling:** skalowanie cech

### W analizie tekstu:
- **Text cleaning:** czyszczenie tekstu
- **Encoding standardization:** standaryzacja kodowania
- **Noise removal:** usuwanie szumu
- **Format normalization:** normalizacja formatów

## Metryki i wskaźniki

### Jakość danych
- **Completeness:** % kompletnych rekordów
- **Accuracy:** % dokładnych wartości
- **Consistency:** % spójnych danych
- **Timeliness:** świeżość danych

### Preprocessing
- **Missing value rate:** wskaźnik brakujących wartości
- **Outlier rate:** wskaźnik wartości odstających
- **Duplicate rate:** wskaźnik duplikatów
- **Transformation impact:** wpływ transformacji

### Wydajność
- **Processing time:** czas przetwarzania
- **Memory usage:** użycie pamięci
- **Data reduction:** redukcja danych
- **Quality improvement:** poprawa jakości

## Wskazówki praktyczne

### Narzędzia
- **Python:** pandas, numpy, scikit-learn
- **R:** dplyr, tidyr, VIM
- **SQL:** agregacje, window functions
- **Data quality tools:** Great Expectations, Deequ

### Best practices
- **Profile first:** najpierw profiluj dane
- **Document everything:** dokumentuj wszystko
- **Validate assumptions:** waliduj założenia
- **Monitor continuously:** monitoruj ciągle
- **Version control:** kontroluj wersje

### Częste błędy
- **Over-cleaning:** zbyt agresywne czyszczenie
- **Under-cleaning:** niedostateczne czyszczenie
- **Data leakage:** wyciek danych
- **Assumption bias:** błąd założeń

## Powiązane tematy

- **Data Profiling:** profilowanie danych
- **Data Validation:** walidacja danych
- **Feature Engineering:** inżynieria cech
- **Data Monitoring:** monitorowanie danych
- **Data Governance:** zarządzanie danymi

## Kluczowe przesłanie

- **Jakość danych** jest fundamentem sukcesu modeli ML
- **Systematyczne podejście** do oceny i poprawy jakości
- **Dokumentacja** procesu jest niezbędna
- **Ciągły monitoring** jakości danych
- **Balance** między czystością a zachowaniem informacji

## Następne kroki

Po Data Quality & Preprocessing przejdź do:
- [Feature Engineering](feature-engineering.md) - inżynieria cech
- [Exploratory Data Analysis](exploratory-data-analysis.md) - analiza eksploracyjna
- [Model Development](../module-02-deep-learning/) - budowa modeli

## Źródła / dalsza lektura

- "Python for Data Analysis" - Wes McKinney
- "Hands-On Machine Learning" - Aurélien Géron
- "Data Quality: The Field Guide" - Thomas C. Redman
- Dokumentacja pandas, scikit-learn
- Great Expectations documentation
