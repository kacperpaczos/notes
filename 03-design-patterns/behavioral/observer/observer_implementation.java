// Subject.java
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Observer.java
public interface Observer {
    void update(String status);
}

// Channel.java
import java.util.List;
import java.util.ArrayList;

public class Channel implements Subject {
    private List<Observer> observers;
    private String channelName;
    private String status;

    public Channel(String channelName) {
        this.channelName = channelName;
        this.observers = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        System.out.println("[" + channelName + "] New status: " + status);
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }
}

// Follower.java
public class Follower implements Observer {
    private String followerName;

    public Follower(String followerName) {
        this.followerName = followerName;
    }

    @Override
    public void update(String status) {
        System.out.println("Follower " + followerName + " received update: " + status);
        play();
    }

    public void play() {
        System.out.println(followerName + " is now playing the new video!");
    }
}

// Main.java - Example usage
public class Main {
    public static void main(String[] args) {
        // Create a YouTube channel
        Channel youtubeChannel = new Channel("Programming Tutorials");
        
        // Create followers
        Follower follower1 = new Follower("John");
        Follower follower2 = new Follower("Mike");
        Follower follower3 = new Follower("Sarah");
        
        // Register followers to the channel
        youtubeChannel.registerObserver(follower1);
        youtubeChannel.registerObserver(follower2);
        youtubeChannel.registerObserver(follower3);
        
        // Channel uploads a new video
        youtubeChannel.setStatus("New video: Java Design Patterns");
        
        System.out.println("\n--- After follower2 unsubscribes ---\n");
        
        // Follower2 unsubscribes
        youtubeChannel.removeObserver(follower2);
        
        // Another video upload
        youtubeChannel.setStatus("New video: Advanced Java Techniques");
    }
} 