"""
Przykład wykorzystania modelu BERT do klasyfikacji tekstu.
Kod pokazuje, jak:
1. Załadować wstępnie wytrenowany model BERT
2. Tokenizować tekst wejściowy
3. Wykonać inferencję
4. Interpretować wyniki

Wymagane biblioteki:
pip install transformers torch
"""

import torch
from transformers import BertTokenizer, BertForSequenceClassification

def classify_sentiment(text, model_name='nlptown/bert-base-multilingual-uncased-sentiment'):
    """
    Klasyfikacja sentymentu tekstu przy użyciu wielojęzycznego modelu BERT.
    
    Args:
        text (str): Tekst do analizy
        model_name (str): Nazwa modelu z Hugging Face Hub
        
    Returns:
        tuple: (ocena sentymentu (1-5), prawdopodobieństwa)
    """
    # Załadowanie modelu i tokenizera
    tokenizer = BertTokenizer.from_pretrained(model_name)
    model = BertForSequenceClassification.from_pretrained(model_name)
    
    # Tokenizacja tekstu
    inputs = tokenizer(text, return_tensors="pt", padding=True, truncation=True, max_length=512)
    
    # Wykonanie inferencji
    with torch.no_grad():
        outputs = model(**inputs)
        predictions = torch.nn.functional.softmax(outputs.logits, dim=-1)
    
    # Interpretacja wyników
    sentiment_score = torch.argmax(predictions).item() + 1  # Skala 1-5
    probabilities = predictions.tolist()[0]
    
    return sentiment_score, probabilities

def main():
    # Przykłady tekstów w różnych językach
    texts = [
        "Ten produkt jest wspaniały, jestem bardzo zadowolony z zakupu!",
        "Nie jestem pewien, czy to było warte swojej ceny.",
        "To była najgorsza decyzja zakupowa, jaką kiedykolwiek podjąłem."
    ]
    
    for text in texts:
        sentiment, probs = classify_sentiment(text)
        print(f"Tekst: {text}")
        print(f"Ocena sentymentu (1-5): {sentiment}")
        print(f"Prawdopodobieństwa klas: {[round(p * 100, 2) for p in probs]}%")
        print("-" * 80)

if __name__ == "__main__":
    main() 