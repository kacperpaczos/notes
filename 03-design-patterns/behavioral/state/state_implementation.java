// Interfejs stanu
public interface VendingMachineState {
    void insertCoin(VendingMachine machine);
    void ejectCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispense(VendingMachine machine);
}

// Kontekst
public class VendingMachine {
    private VendingMachineState state;
    private int coins;
    private boolean hasProduct;

    public VendingMachine() {
        this.state = new NoCoinState();
        this.coins = 0;
        this.hasProduct = true;
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public void insertCoin() {
        state.insertCoin(this);
    }

    public void ejectCoin() {
        state.ejectCoin(this);
    }

    public void selectProduct() {
        state.selectProduct(this);
    }

    public void dispense() {
        state.dispense(this);
    }

    public void addCoin() {
        coins++;
    }

    public void removeCoin() {
        coins--;
    }

    public boolean hasCoins() {
        return coins > 0;
    }

    public boolean hasProduct() {
        return hasProduct;
    }

    public void removeProduct() {
        hasProduct = false;
    }

    public void addProduct() {
        hasProduct = true;
    }
}

// Konkretne stany
public class NoCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Moneta wrzucona");
        machine.addCoin();
        machine.setState(new HasCoinState());
    }

    @Override
    public void ejectCoin(VendingMachine machine) {
        System.out.println("Nie ma monety do zwrócenia");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Najpierw wrzuć monetę");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Najpierw wrzuć monetę");
    }
}

public class HasCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Moneta już wrzucona");
    }

    @Override
    public void ejectCoin(VendingMachine machine) {
        System.out.println("Moneta zwrócona");
        machine.removeCoin();
        machine.setState(new NoCoinState());
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Produkt wybrany");
        machine.setState(new SoldState());
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Najpierw wybierz produkt");
    }
}

public class SoldState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Poczekaj na wydanie produktu");
    }

    @Override
    public void ejectCoin(VendingMachine machine) {
        System.out.println("Nie można zwrócić monety po wybraniu produktu");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Produkt już wybrany");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Produkt wydany");
        machine.removeProduct();
        if (machine.hasProduct()) {
            machine.setState(new NoCoinState());
        } else {
            machine.setState(new SoldOutState());
        }
    }
}

public class SoldOutState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Automat pusty");
    }

    @Override
    public void ejectCoin(VendingMachine machine) {
        System.out.println("Nie ma monety do zwrócenia");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Automat pusty");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Automat pusty");
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        // Symulacja użycia automatu
        System.out.println("=== Test 1: Normalna sekwencja ===");
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();

        System.out.println("\n=== Test 2: Próba wydania bez monety ===");
        machine.dispense();

        System.out.println("\n=== Test 3: Próba zwrotu monety po wybraniu produktu ===");
        machine.insertCoin();
        machine.selectProduct();
        machine.ejectCoin();

        System.out.println("\n=== Test 4: Próba użycia pustego automatu ===");
        machine.removeProduct();
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();
    }
} 