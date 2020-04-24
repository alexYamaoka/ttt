package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;
import Server.ClientConnection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameHandler implements Runnable
{
    private Packet packet;
    private ObjectOutputStream outputStream;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();

    private Queue<ClientConnection> gameWaitingList = new LinkedList<>();


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


    public void addPlayerToWaitingList(ClientConnection clientConnection)
    {
        gameWaitingList.add(clientConnection);
    }

    public ClientConnection getNextPlayerInLine()
    {
        if (! gameWaitingList.isEmpty())
        {
            return gameWaitingList.remove();
        }

        return null;
    }
}
