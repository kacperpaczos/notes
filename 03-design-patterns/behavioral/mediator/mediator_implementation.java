// Interfejs mediatora
public interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

// Klasa reprezentująca użytkownika
public abstract class User {
    protected ChatMediator mediator;
    protected String name;

    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void send(String message);
    public abstract void receive(String message);
}

// Konkretny mediator
public class ChatRoom implements ChatMediator {
    private List<User> users;

    public ChatRoom() {
        this.users = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message, User user) {
        for (User u : users) {
            if (u != user) {
                u.receive(message);
            }
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}

// Konkretni użytkownicy
public class ChatUser extends User {
    public ChatUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println(name + " wysyła: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void receive(String message) {
        System.out.println(name + " otrzymuje: " + message);
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        // Tworzenie mediatora
        ChatMediator mediator = new ChatRoom();

        // Tworzenie użytkowników
        User user1 = new ChatUser(mediator, "Jan");
        User user2 = new ChatUser(mediator, "Anna");
        User user3 = new ChatUser(mediator, "Piotr");
        User user4 = new ChatUser(mediator, "Maria");

        // Dodawanie użytkowników do czatu
        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);
        mediator.addUser(user4);

        // Symulacja rozmowy
        System.out.println("=== Rozpoczęcie rozmowy ===\n");
        
        user1.send("Cześć wszystkim!");
        System.out.println();
        
        user2.send("Cześć Jan, jak się masz?");
        System.out.println();
        
        user3.send("Witajcie! Co słychać?");
        System.out.println();
        
        user4.send("Dzień dobry! Miło was widzieć.");
        System.out.println();
        
        user1.send("Wszystko dobrze, pracuję nad nowym projektem.");
        System.out.println();
        
        user2.send("To świetnie! Powodzenia!");
    }
} 