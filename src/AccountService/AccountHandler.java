package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
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


        switch(request)
        {
            case Packet.SIGN_IN:
                break;

            case Packet.SIGN_OUT:
                break;

            case Packet.REGISTER_CLIENT:
                System.out.println(data.toString());
                break;

            case Packet.UPDATE_USER:
                break;

            case Packet.DELETE_ACCOUNT:
                break;
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
