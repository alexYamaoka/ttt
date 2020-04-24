package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameHandler implements Runnable
{
    private Packet packet;
    private ObjectOutputStream outputStream;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();



    private final AtomicBoolean running = new AtomicBoolean(false);

    public GameHandler(Packet packet, ObjectOutputStream outputStream) {
        this.packet = packet;
        this.outputStream = outputStream;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        running.set(false);
        System.out.println("GameHandler has stopped!");
    }

    @Override
    public void run() {
        running.set(true);
        System.out.println("GameHandler has started!");
        String request = packet.getRequest();
        UserInformation userInformation = packet.getInformation();
        Serializable data = packet.getData();

        switch(request)
        {
            case Packet.JOIN_WAITING_LIST:

                break;
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
