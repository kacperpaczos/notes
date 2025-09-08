# 📊 Model Evaluation & Metrics - Ocena modeli i metryki

## Cel

Poznanie metod oceny modeli Machine Learning, metryk wydajności oraz technik walidacji w kontekście różnych typów problemów ML.

## Problem

Jak systematycznie ocenić jakość modeli ML, wybrać odpowiednie metryki dla danego problemu oraz uniknąć błędów w procesie ewaluacji.

## Pojęcia kluczowe

### 1. Typy problemów ML i metryki

#### Klasyfikacja binarna
- **Accuracy:** dokładność klasyfikacji
- **Precision:** precyzja (TP / (TP + FP))
- **Recall (Sensitivity):** czułość (TP / (TP + FN))
- **Specificity:** specyficzność (TN / (TN + FP))
- **F1-Score:** harmoniczna średnia precision i recall
- **AUC-ROC:** pole pod krzywą ROC
- **AUC-PR:** pole pod krzywą Precision-Recall

#### Klasyfikacja wieloklasowa
- **Macro-averaging:** średnia metryk dla każdej klasy
- **Micro-averaging:** globalna metryka dla wszystkich klas
- **Weighted-averaging:** średnia ważona liczbą próbek
- **Confusion Matrix:** macierz pomyłek
- **Classification Report:** szczegółowy raport klasyfikacji

#### Regresja
- **MAE (Mean Absolute Error):** średni błąd bezwzględny
- **MSE (Mean Squared Error):** średni błąd kwadratowy
- **RMSE (Root Mean Squared Error):** pierwiastek z MSE
- **R² (R-squared):** współczynnik determinacji
- **MAPE (Mean Absolute Percentage Error):** średni błąd procentowy
- **SMAPE (Symmetric MAPE):** symetryczny MAPE

#### Systemy rekomendacyjne
- **Precision@K:** precyzja dla top-K rekomendacji
- **Recall@K:** recall dla top-K rekomendacji
- **NDCG (Normalized Discounted Cumulative Gain):** znormalizowany skumulowany zysk
- **MAP (Mean Average Precision):** średnia precyzja
- **Hit Rate:** wskaźnik trafień
- **Coverage:** pokrycie katalogu produktów

### 2. Techniki walidacji

#### Holdout Validation
- **Train/Test Split:** podział na zbiór treningowy i testowy
- **Train/Validation/Test Split:** dodatkowy zbiór walidacyjny
- **Stratified Split:** zachowanie proporcji klas

#### Cross-Validation
- **K-Fold CV:** podział na K części
- **Stratified K-Fold:** zachowanie proporcji klas
- **Time Series CV:** walidacja dla danych czasowych
- **Leave-One-Out CV:** walidacja z jedną próbką

#### Advanced Validation
- **Nested CV:** podwójna walidacja krzyżowa
- **Group K-Fold:** grupowanie próbek
- **Repeated K-Fold:** powtarzanie K-Fold
- **Bootstrap:** próbkowanie z powtórzeniami

### 3. Metryki biznesowe

#### E-commerce
- **Conversion Rate:** wskaźnik konwersji
- **Revenue per User:** przychód na użytkownika
- **Click-Through Rate (CTR):** wskaźnik kliknięć
- **Average Order Value:** średnia wartość zamówienia

#### Marketing
- **Customer Lifetime Value (CLV):** wartość życia klienta
- **Churn Rate:** wskaźnik odejść klientów
- **Retention Rate:** wskaźnik retencji
- **Engagement Rate:** wskaźnik zaangażowania

#### Finanse
- **Profit/Loss:** zysk/strata
- **Return on Investment (ROI):** zwrot z inwestycji
- **Risk-Adjusted Returns:** zwroty skorygowane o ryzyko
- **Sharpe Ratio:** współczynnik Sharpe'a

## Struktura / Diagram

```
Model Evaluation Framework
├── 1. Problem Definition
│   ├── Classification vs Regression
│   ├── Binary vs Multiclass
│   ├── Imbalanced vs Balanced
│   └── Business Objectives
│
├── 2. Metric Selection
│   ├── Technical Metrics
│   │   ├── Accuracy, Precision, Recall
│   │   ├── MAE, MSE, RMSE
│   │   └── AUC, F1-Score
│   ├── Business Metrics
│   │   ├── Conversion Rate
│   │   ├── Revenue Impact
│   │   └── Customer Satisfaction
│   └── Domain-Specific Metrics
│       ├── NDCG (Recommendations)
│       ├── BLEU (NLP)
│       └── IoU (Computer Vision)
│
├── 3. Validation Strategy
│   ├── Data Splitting
│   │   ├── Holdout
│   │   ├── K-Fold CV
│   │   └── Time Series Split
│   ├── Hyperparameter Tuning
│   │   ├── Grid Search
│   │   ├── Random Search
│   │   └── Bayesian Optimization
│   └── Model Selection
│       ├── Cross-Validation
│       ├── Nested CV
│       └── Bootstrap
│
├── 4. Evaluation Execution
│   ├── Training Metrics
│   ├── Validation Metrics
│   ├── Test Metrics
│   └── Statistical Significance
│
└── 5. Results Interpretation
    ├── Performance Analysis
    ├── Error Analysis
    ├── Business Impact
    └── Recommendations
```

## Przepływ działania

### Faza 1: Planowanie ewaluacji
1. **Problem analysis:** określenie typu problemu
2. **Metric selection:** wybór odpowiednich metryk
3. **Validation strategy:** plan walidacji
4. **Baseline establishment:** ustalenie baseline

### Faza 2: Implementacja walidacji
1. **Data splitting:** podział danych
2. **Model training:** trening modeli
3. **Hyperparameter tuning:** optymalizacja parametrów
4. **Cross-validation:** walidacja krzyżowa

### Faza 3: Ocena wydajności
1. **Metric calculation:** obliczenie metryk
2. **Statistical testing:** testy statystyczne
3. **Error analysis:** analiza błędów
4. **Performance comparison:** porównanie modeli

### Faza 4: Interpretacja wyników
1. **Results analysis:** analiza wyników
2. **Business impact:** wpływ biznesowy
3. **Recommendations:** rekomendacje
4. **Documentation:** dokumentacja

## Przykłady użycia

### W systemie rekomendacyjnym:
- **NDCG@10:** jakość rekomendacji w top-10
- **Precision@5:** precyzja dla top-5 rekomendacji
- **Coverage:** % produktów w rekomendacjach
- **Diversity:** różnorodność rekomendacji

### W klasyfikacji medycznej:
- **Sensitivity:** wykrywanie przypadków pozytywnych
- **Specificity:** unikanie fałszywych alarmów
- **AUC-ROC:** ogólna wydajność modelu
- **Precision:** dokładność diagnoz

### W analizie predykcyjnej:
- **RMSE:** błąd predykcji
- **MAE:** średni błąd bezwzględny
- **R²:** wyjaśniona wariancja
- **MAPE:** błąd procentowy

## Metryki i wskaźniki

### Klasyfikacja
- **Confusion Matrix:** TP, TN, FP, FN
- **Precision:** TP / (TP + FP)
- **Recall:** TP / (TP + FN)
- **F1-Score:** 2 × (Precision × Recall) / (Precision + Recall)
- **AUC-ROC:** pole pod krzywą ROC

### Regresja
- **MAE:** Σ|y_true - y_pred| / n
- **MSE:** Σ(y_true - y_pred)² / n
- **RMSE:** √(MSE)
- **R²:** 1 - (SS_res / SS_tot)

### Walidacja
- **Cross-validation score:** średnia z K-fold
- **Standard deviation:** odchylenie standardowe
- **Confidence interval:** przedział ufności
- **Statistical significance:** istotność statystyczna

## Wskazówki praktyczne

### Narzędzia
- **Python:** scikit-learn, pandas, matplotlib
- **R:** caret, mlr, ggplot2
- **SQL:** agregacje i statystyki
- **Visualization:** seaborn, plotly, tableau

### Best practices
- **Multiple metrics:** używaj wielu metryk
- **Business alignment:** dopasuj metryki do celów biznesowych
- **Statistical significance:** sprawdź istotność statystyczną
- **Error analysis:** analizuj błędy modelu
- **Documentation:** dokumentuj proces ewaluacji

### Częste błędy
- **Data leakage:** wyciek danych z test set
- **Overfitting:** zbyt dobre wyniki na train set
- **Metric mismatch:** niewłaściwe metryki dla problemu
- **Insufficient validation:** za mało walidacji

## Powiązane tematy

- **Model Selection:** wybór modeli
- **Hyperparameter Tuning:** optymalizacja parametrów
- **Cross-Validation:** walidacja krzyżowa
- **Statistical Testing:** testy statystyczne
- **Business Metrics:** metryki biznesowe

## Kluczowe przesłanie

- **Wybór metryk** zależy od typu problemu i celów biznesowych
- **Walidacja krzyżowa** jest kluczowa dla rzetelnej oceny
- **Metryki biznesowe** są równie ważne co techniczne
- **Analiza błędów** pomaga w ulepszaniu modeli
- **Dokumentacja** procesu ewaluacji jest niezbędna

## Następne kroki

Po Model Evaluation przejdź do:
- [A/B Testing w ML](ab-testing-ml.md) - testowanie modeli
- [Model Monitoring](model-monitoring.md) - monitorowanie w produkcji
- [Model Deployment](../module-04-applications/) - wdrażanie modeli

## Źródła / dalsza lektura

- "Hands-On Machine Learning" - Aurélien Géron
- "The Elements of Statistical Learning" - Hastie, Tibshirani, Friedman
- "Python for Data Analysis" - Wes McKinney
- Dokumentacja scikit-learn
- Kaggle: Model Evaluation competitions
