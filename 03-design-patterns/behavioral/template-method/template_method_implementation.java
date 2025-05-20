// Abstrakcyjna klasa bazowa definiująca szkielet algorytmu
public abstract class DataMiner {
    // Template method - definiuje szkielet algorytmu
    public final void mine() {
        openFile();
        extractData();
        parseData();
        analyzeData();
        sendReport();
        closeFile();
    }

    // Konkretne metody - wspólne dla wszystkich implementacji
    private void openFile() {
        System.out.println("Opening file...");
    }

    private void closeFile() {
        System.out.println("Closing file...");
    }

    // Abstrakcyjne metody - do zaimplementowania przez podklasy
    protected abstract void extractData();
    protected abstract void parseData();
    protected abstract void analyzeData();
    protected abstract void sendReport();
}

// Konkretna implementacja dla plików PDF
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

// Konkretna implementacja dla plików CSV
public class CSVDataMiner extends DataMiner {
    @Override
    protected void extractData() {
        System.out.println("Extracting data from CSV...");
    }

    @Override
    protected void parseData() {
        System.out.println("Parsing CSV data...");
    }

    @Override
    protected void analyzeData() {
        System.out.println("Analyzing CSV data...");
    }

    @Override
    protected void sendReport() {
        System.out.println("Sending CSV analysis report...");
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        // Użycie dla pliku PDF
        DataMiner pdfMiner = new PDFDataMiner();
        System.out.println("Processing PDF file:");
        pdfMiner.mine();

        System.out.println("\nProcessing CSV file:");
        // Użycie dla pliku CSV
        DataMiner csvMiner = new CSVDataMiner();
        csvMiner.mine();
    }
} 