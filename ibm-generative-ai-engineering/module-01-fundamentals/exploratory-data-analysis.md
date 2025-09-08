# 📊 Exploratory Data Analysis (EDA) - Analiza eksploracyjna danych

## Cel

Poznanie metod i technik analizy eksploracyjnej danych w kontekście projektów Machine Learning.

## Problem

Jak systematycznie zbadać i zrozumieć dane przed budowaniem modeli ML, aby uniknąć błędów i poprawić jakość rozwiązań.

## Pojęcia kluczowe

### 1. Definicja EDA

**Exploratory Data Analysis (EDA)** to proces systematycznego badania danych w celu:
- Zrozumienia struktury i charakterystyki danych
- Identyfikacji wzorców, trendów i anomalii
- Weryfikacji założeń przed modelowaniem
- Przygotowania danych do dalszej analizy

### 2. Cele EDA

- **Zrozumienie danych:** struktura, typy, rozkłady
- **Wykrywanie problemów:** brakujące wartości, outliers, błędy
- **Identyfikacja wzorców:** korelacje, trendy, grupowania
- **Walidacja hipotez:** sprawdzenie założeń biznesowych
- **Przygotowanie do modelowania:** feature selection, preprocessing

### 3. Główne techniki EDA

#### Wizualizacje
- **Histogramy:** rozkłady zmiennych
- **Box plots:** identyfikacja outliers
- **Scatter plots:** relacje między zmiennymi
- **Heatmaps:** macierze korelacji
- **Time series plots:** trendy czasowe

#### Analiza statystyczna
- **Podstawowe statystyki:** mean, median, std, percentyle
- **Analiza korelacji:** Pearson, Spearman
- **Testy hipotez:** normalność, równość wariancji
- **Analiza wariancji:** ANOVA, t-testy

#### Analiza jakości danych
- **Missing data analysis:** identyfikacja i analiza brakujących wartości
- **Outlier detection:** wykrywanie wartości odstających
- **Data quality assessment:** sprawdzenie spójności danych

## Struktura / Diagram

```
EDA Process Flow
├── 1. Data Overview
│   ├── Shape & Structure
│   ├── Data Types
│   └── Basic Statistics
│
├── 2. Data Quality Assessment
│   ├── Missing Values Analysis
│   ├── Outlier Detection
│   └── Data Consistency Check
│
├── 3. Univariate Analysis
│   ├── Distribution Analysis
│   ├── Central Tendency
│   └── Variability Measures
│
├── 4. Bivariate Analysis
│   ├── Correlation Analysis
│   ├── Cross-tabulation
│   └── Relationship Visualization
│
├── 5. Multivariate Analysis
│   ├── Principal Component Analysis
│   ├── Clustering Analysis
│   └── Advanced Visualizations
│
└── 6. Insights & Recommendations
    ├── Key Findings
    ├── Data Issues
    └── Next Steps
```

## Przepływ działania

### Faza 1: Przegląd danych
1. **Shape i struktura:** wymiary, typy kolumn
2. **Podstawowe statystyki:** summary statistics
3. **Próbka danych:** pierwsze/ostatnie wiersze

### Faza 2: Ocena jakości
1. **Missing values:** identyfikacja i analiza
2. **Outliers:** wykrywanie wartości odstających
3. **Spójność:** sprawdzenie logiki biznesowej

### Faza 3: Analiza uniwariantowa
1. **Rozkłady:** histogramy, box plots
2. **Tendencje centralne:** mean, median, mode
3. **Zmienność:** std, variance, range

### Faza 4: Analiza biwariantowa
1. **Korelacje:** macierze korelacji
2. **Relacje:** scatter plots, cross-tabulation
3. **Zależności:** testy statystyczne

### Faza 5: Analiza wielowariantowa
1. **PCA:** redukcja wymiarów
2. **Clustering:** grupowanie danych
3. **Advanced plots:** heatmaps, 3D plots

### Faza 6: Wnioski
1. **Kluczowe odkrycia:** główne wzorce
2. **Problemy danych:** issues do rozwiązania
3. **Rekomendacje:** następne kroki

## Przykłady użycia

### W systemie rekomendacyjnym:
- **Analiza zachowań użytkowników:** wzorce zakupowe, preferencje
- **Analiza produktów:** popularność, kategorie, ceny
- **Korelacje:** relacje między produktami a użytkownikami

### W analizie predykcyjnej:
- **Feature importance:** identyfikacja ważnych zmiennych
- **Data distribution:** sprawdzenie normalności
- **Correlation analysis:** unikanie multicollinearity

### W analizie biznesowej:
- **Trend analysis:** zmiany w czasie
- **Segmentation:** grupowanie klientów
- **Performance metrics:** KPI analysis

## Metryki i wskaźniki

### Jakość danych
- **Completeness:** % brakujących wartości
- **Accuracy:** dokładność danych
- **Consistency:** spójność między źródłami
- **Timeliness:** aktualność danych

### Statystyki opisowe
- **Central tendency:** mean, median, mode
- **Variability:** std, variance, IQR
- **Distribution:** skewness, kurtosis
- **Correlation:** Pearson, Spearman coefficients

### Wizualizacje
- **Distribution plots:** histogramy, density plots
- **Relationship plots:** scatter, line plots
- **Comparison plots:** box plots, bar charts
- **Multivariate plots:** heatmaps, pair plots

## Wskazówki praktyczne

### Narzędzia
- **Python:** pandas, matplotlib, seaborn, plotly
- **R:** ggplot2, dplyr, corrplot
- **SQL:** podstawowe agregacje i JOIN-y
- **Excel:** pivot tables, charts

### Best practices
- **Zacznij od przeglądu:** shape, types, basic stats
- **Sprawdź jakość:** missing values, outliers
- **Wizualizuj systematycznie:** od prostych do złożonych
- **Dokumentuj odkrycia:** notatki i wnioski
- **Iteruj:** EDA to proces iteracyjny

### Częste błędy
- **Pomijanie jakości danych:** nie sprawdzanie missing values
- **Brak kontekstu biznesowego:** analiza bez zrozumienia domeny
- **Overfitting wizualizacji:** zbyt skomplikowane wykresy
- **Ignorowanie outliers:** nie analizowanie wartości odstających

## Powiązane tematy

- **Data Preprocessing:** przygotowanie danych do modelowania
- **Feature Engineering:** tworzenie nowych cech
- **Statistical Analysis:** testy hipotez i analiza statystyczna
- **Data Visualization:** zaawansowane techniki wizualizacji
- **Data Quality:** zarządzanie jakością danych

## Kluczowe przesłanie

- **EDA jest fundamentem** każdego projektu ML
- **Systematyczne podejście** zapewnia lepsze wyniki
- **Wizualizacje są kluczowe** dla zrozumienia danych
- **Kontekst biznesowy** jest niezbędny dla interpretacji
- **Iteracyjny proces** - EDA nie kończy się na pierwszym przejściu

## Następne kroki

Po EDA przejdź do:
- [Data Preprocessing](data-preprocessing.md) - przygotowanie danych
- [Feature Engineering](feature-engineering.md) - tworzenie cech
- [Model Development](../module-02-deep-learning/) - budowa modeli

## Źródła / dalsza lektura

- "Exploratory Data Analysis" - John Tukey
- "Python for Data Analysis" - Wes McKinney
- "R for Data Science" - Hadley Wickham
- Dokumentacja pandas, matplotlib, seaborn
- Coursera: "Data Science Specialization" - Johns Hopkins
