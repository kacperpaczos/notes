# Wzorzec Template Method

## Cel
Definiuje szkielet algorytmu w metodzie, odkładając niektóre kroki na podklasy. Template Method pozwala podklasom redefiniować określone kroki algorytmu bez zmiany jego struktury.

## Problem
Masz algorytm składający się z kilku kroków, gdzie niektóre kroki są wspólne dla wszystkich implementacji, a inne mogą się różnić w zależności od konkretnego przypadku użycia.

## Rozwiązanie
1. Zdefiniuj abstrakcyjną klasę bazową
2. Zaimplementuj wspólne kroki jako konkretne metody
3. Zdefiniuj abstrakcyjne metody dla kroków, które mogą się różnić
4. Pozwól podklasom implementować te abstrakcyjne metody

## Struktura
```
AbstractClass
    |
    |-- templateMethod()
    |-- primitiveOperation1()
    |-- primitiveOperation2()
    |
ConcreteClass
    |
    |-- primitiveOperation1()
    |-- primitiveOperation2()
```

## Przykład użycia
- Frameworki aplikacyjne
- Biblioteki
- Systemy raportowania
- Procesy biznesowe

## Zalety
- Eliminuje duplikację kodu
- Ułatwia rozszerzanie funkcjonalności
- Zapewnia kontrolę nad algorytmem
- Wymusza spójną strukturę

## Wady
- Może prowadzić do zbyt sztywnej hierarchii klas
- Narusza zasadę LSP (Liskov Substitution Principle) w niektórych przypadkach
- Może być trudny do utrzymania przy zbyt wielu wariantach

## Przykład implementacji
```java
public abstract class DataMiner {
    // Template method
    public final void mine() {
        openFile();
        extractData();
        parseData();
        analyzeData();
        sendReport();
        closeFile();
    }

    // Concrete methods
    private void openFile() {
        System.out.println("Opening file...");
    }

    private void closeFile() {
        System.out.println("Closing file...");
    }

    // Abstract methods
    protected abstract void extractData();
    protected abstract void parseData();
    protected abstract void analyzeData();
    protected abstract void sendReport();
}

public class PDFDataMiner extends DataMiner {
    @Override
    protected void extractData() {
        System.out.println("Extracting data from PDF...");
    }

    @Override
    protected void parseData() {
        System.out.println("Parsing PDF data...");
    }

    @Override
    protected void analyzeData() {
        System.out.println("Analyzing PDF data...");
    }

    @Override
    protected void sendReport() {
        System.out.println("Sending PDF analysis report...");
    }
}
```

## Powiązane wzorce
- Strategy Pattern - alternatywa gdy potrzebujemy większej elastyczności
- Factory Method - często używany w implementacji Template Method
- Hook Method - wariant Template Method z opcjonalnymi krokami 