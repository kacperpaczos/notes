// Klasa reprezentująca żądanie
public class Request {
    private String type;
    private int amount;

    public Request(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}

// Abstrakcyjna klasa obsługująca
public abstract class Handler {
    protected Handler nextHandler;

    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    public abstract void handleRequest(Request request);
}

// Konkretny handler dla małych kwot
public class SmallAmountHandler extends Handler {
    private static final int MAX_AMOUNT = 1000;

    @Override
    public void handleRequest(Request request) {
        if (request.getAmount() <= MAX_AMOUNT) {
            System.out.println("SmallAmountHandler: Obsługuję żądanie o kwocie " + request.getAmount());
        } else if (nextHandler != null) {
            System.out.println("SmallAmountHandler: Przekazuję żądanie dalej");
            nextHandler.handleRequest(request);
        }
    }
}

// Konkretny handler dla średnich kwot
public class MediumAmountHandler extends Handler {
    private static final int MAX_AMOUNT = 5000;

    @Override
    public void handleRequest(Request request) {
        if (request.getAmount() <= MAX_AMOUNT) {
            System.out.println("MediumAmountHandler: Obsługuję żądanie o kwocie " + request.getAmount());
        } else if (nextHandler != null) {
            System.out.println("MediumAmountHandler: Przekazuję żądanie dalej");
            nextHandler.handleRequest(request);
        }
    }
}

// Konkretny handler dla dużych kwot
public class LargeAmountHandler extends Handler {
    @Override
    public void handleRequest(Request request) {
        System.out.println("LargeAmountHandler: Obsługuję żądanie o kwocie " + request.getAmount());
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        // Tworzenie łańcucha odpowiedzialności
        Handler smallHandler = new SmallAmountHandler();
        Handler mediumHandler = new MediumAmountHandler();
        Handler largeHandler = new LargeAmountHandler();

        // Ustawianie następników w łańcuchu
        smallHandler.setNext(mediumHandler);
        mediumHandler.setNext(largeHandler);

        // Tworzenie żądań
        Request request1 = new Request("payment", 500);
        Request request2 = new Request("payment", 2500);
        Request request3 = new Request("payment", 7500);

        // Obsługa żądań
        System.out.println("Obsługa żądania 1:");
        smallHandler.handleRequest(request1);

        System.out.println("\nObsługa żądania 2:");
        smallHandler.handleRequest(request2);

        System.out.println("\nObsługa żądania 3:");
        smallHandler.handleRequest(request3);
    }
} 