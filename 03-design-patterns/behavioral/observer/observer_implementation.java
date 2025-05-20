// Interfejs Subject
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Interfejs Observer
public interface Observer {
    void update(String message);
}

// Klasa reprezentująca kanał YouTube
public class YouTubeChannel implements Subject {
    private List<Observer> observers;
    private String channelName;
    private String latestVideo;

    public YouTubeChannel(String channelName) {
        this.channelName = channelName;
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Nowy subskrybent: " + ((Subscriber)observer).getName());
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Subskrybent usunięty: " + ((Subscriber)observer).getName());
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(latestVideo);
        }
    }

    public void uploadVideo(String videoTitle) {
        this.latestVideo = videoTitle;
        System.out.println("Kanał " + channelName + " opublikował nowy film: " + videoTitle);
        notifyObservers();
    }
}

// Klasa reprezentująca subskrybenta
public class Subscriber implements Observer {
    private String name;
    private List<String> notifications;

    public Subscriber(String name) {
        this.name = name;
        this.notifications = new ArrayList<>();
    }

    @Override
    public void update(String videoTitle) {
        notifications.add(videoTitle);
        System.out.println(name + " otrzymał powiadomienie o nowym filmie: " + videoTitle);
    }

    public String getName() {
        return name;
    }

    public List<String> getNotifications() {
        return notifications;
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        // Tworzenie kanału YouTube
        YouTubeChannel channel = new YouTubeChannel("Programowanie w Javie");

        // Tworzenie subskrybentów
        Subscriber subscriber1 = new Subscriber("Jan");
        Subscriber subscriber2 = new Subscriber("Anna");
        Subscriber subscriber3 = new Subscriber("Piotr");

        // Rejestracja subskrybentów
        channel.registerObserver(subscriber1);
        channel.registerObserver(subscriber2);
        channel.registerObserver(subscriber3);

        // Publikacja nowego filmu
        System.out.println("\n=== Publikacja nowego filmu ===");
        channel.uploadVideo("Wzorce projektowe w Javie");

        // Usunięcie subskrybenta
        System.out.println("\n=== Usunięcie subskrybenta ===");
        channel.removeObserver(subscriber2);

        // Publikacja kolejnego filmu
        System.out.println("\n=== Publikacja kolejnego filmu ===");
        channel.uploadVideo("Spring Framework - wprowadzenie");

        // Wyświetlenie powiadomień dla każdego subskrybenta
        System.out.println("\n=== Powiadomienia subskrybentów ===");
        System.out.println("Powiadomienia dla " + subscriber1.getName() + ":");
        subscriber1.getNotifications().forEach(System.out::println);

        System.out.println("\nPowiadomienia dla " + subscriber3.getName() + ":");
        subscriber3.getNotifications().forEach(System.out::println);
    }
} 