package AccountService;

import Shared.Packet;
import Shared.UserInformation;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountHandler implements Runnable {
    private Packet packet;
    private ObjectOutputStream outputStream;
    private Thread worker;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public AccountHandler(Packet packet, ObjectOutputStream outputStream) {
        this.packet = packet;
        this.outputStream = outputStream;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        running.set(false);
        System.out.println("AccountHandler has stopped!");
    }

    @Override
    public void run() {
        running.set(true);
        String request = packet.getRequest();
        UserInformation userInformation = packet.getInformation();
        Serializable data = packet.getData();

        switch(request) {
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
        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
