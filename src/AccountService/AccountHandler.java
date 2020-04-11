package AccountService;

import Shared.Packet;
import Shared.UserInformation;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountHandler implements Runnable {
    private Packet packet;
    private Thread worker;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public AccountHandler(Packet packet) {
        this.packet = packet;

    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        String type = packet.getType();
        UserInformation userInformation = packet.getInformation();
        Serializable data = packet.getData();

        switch(type) {
            case "SIGN-IN":
                break;
            case "SIGN-OUT":
                break;
            case "REGISTER":
                System.out.println(data.toString());
                break;
            case "UPDATE-USER":
                break;
            case "DELETE-ACCOUNT":
                break;
        }
        running.set(false);
    }

    public boolean getRunning() {
        return running.get();
    }
}
