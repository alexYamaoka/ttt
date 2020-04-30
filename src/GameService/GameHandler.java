package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;
import Server.ClientConnection;
import java.io.IOException;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameHandler implements Runnable
{
    private Packet packet;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();
    private GameService service;

    private ClientConnection clientConnection;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public GameHandler(ClientConnection clientConnection, Packet packet,GameService service) {
        this.service = service;
        this.packet = packet;
        this.clientConnection = clientConnection;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
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
            case Packet.JOIN_GAME:
               // GameRoomInformation game = service.getGame(data.toString());
                //game.join(clientConnection);
                //Database call
                //game.start();
                break;

            case Packet.OBSERVE_GAME:
                //GameRoomInformation ObserverGame = service.getGame(data.toString());
                break;

            case Packet.NEW_GAME_CREATED:
                //service.addGame(new GameRoomInformation(clientConnection,data.toString())); //pull game name from data

                break;

            case Packet.GET_GAMES:
                try {
                   // clientConnection.getOutputStream().writeObject(new Packet(Packet.GET_GAMES, userInformation, (Serializable) service.getGames())); // list of current games
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }



}
