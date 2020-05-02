package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;
import Server.ClientConnection;
import java.io.IOException;

import java.io.Serializable;

import java.sql.SQLException;
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
                Game game = service.getGame(data.toString());
                game.join(clientConnection);

                try {
                    ds.insertGame(game);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                game.start();
                break;

            case Packet.OBSERVE_GAME:
                Game ObserverGame = service.getGame(data.toString());
                break;

            case Packet.NEW_GAME_CREATED:
                try {
                    service.addGame(new Game(clientConnection, data.toString())); //pull game name from data
                    Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientConnection.getInformation(), "SUCCESS");
                    clientConnection.getOutputStream().writeObject(packet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;

            case Packet.GET_GAMES:
                try {
                   clientConnection.getOutputStream().writeObject(new Packet(Packet.GET_GAMES, userInformation, (Serializable) service.getGames())); // list of current games
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
