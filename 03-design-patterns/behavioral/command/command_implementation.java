// Interfejs komendy
public interface Command {
    void execute();
    void undo();
}

// Klasa reprezentująca odbiorcę
public class Light {
    private String location;

    public Light(String location) {
        this.location = location;
    }

    public void turnOn() {
        System.out.println(location + " światło włączone");
    }

    public void turnOff() {
        System.out.println(location + " światło wyłączone");
    }
}

// Klasa reprezentująca odbiorcę
public class GarageDoor {
    public void up() {
        System.out.println("Brama garażowa otwarta");
    }

    public void down() {
        System.out.println("Brama garażowa zamknięta");
    }

    public void stop() {
        System.out.println("Brama garażowa zatrzymana");
    }

    public void lightOn() {
        System.out.println("Światło w garażu włączone");
    }

    public void lightOff() {
        System.out.println("Światło w garażu wyłączone");
    }
}

// Konkretne komendy
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

public class GarageDoorUpCommand implements Command {
    private GarageDoor garageDoor;

    public GarageDoorUpCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.up();
    }

    @Override
    public void undo() {
        garageDoor.down();
    }
}

public class GarageDoorDownCommand implements Command {
    private GarageDoor garageDoor;

    public GarageDoorDownCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.down();
    }

    @Override
    public void undo() {
        garageDoor.up();
    }
}

// Klasa wywołująca komendy
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}

// Pusta komenda
public class NoCommand implements Command {
    @Override
    public void execute() {
        // Nic nie rób
    }

    @Override
    public void undo() {
        // Nic nie rób
    }
}

// Przykład użycia
public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        Light livingRoomLight = new Light("Salon");
        Light kitchenLight = new Light("Kuchnia");
        GarageDoor garageDoor = new GarageDoor();

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
        GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, garageDoorUp, garageDoorDown);

        System.out.println("=== Test pilota ===");
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);
        remote.onButtonWasPushed(1);
        remote.offButtonWasPushed(1);
        remote.onButtonWasPushed(2);
        remote.offButtonWasPushed(2);

        System.out.println("\n=== Test cofania ===");
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);
        remote.undoButtonWasPushed();
    }
} 