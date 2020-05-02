package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Models.Move;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;
import Server.ClientConnection;
import com.sun.javafx.iio.ios.IosDescriptor;

import java.io.IOException;

import java.io.Serializable;

import java.sql.SQLException;
import java.util.HashMap;
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
        HashMap<String, GameThread> gameThreadList = service.getGameThreadList();



        switch(request)
        {
            case Packet.GET_GAMES:
                try {
                    clientConnection.getOutputStream().writeObject(new Packet(Packet.GET_GAMES, userInformation, (Serializable) service.getGames())); // list of current games
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;



            case Packet.NEW_GAME_CREATED:
                try {
                    System.out.println("New Game started start");
                    service.addGame(new Game(clientConnection, data.toString())); //pull game name from data
                    Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientConnection.getInformation(), "SUCCESS");
                    clientConnection.getOutputStream().writeObject(packet);
                    System.out.println("New Game end");



                    Packet gameNamePacket = new Packet(Packet.Game_Name, clientConnection.getInformation(), "test");
                    clientConnection.getOutputStream().writeObject(gameNamePacket);





                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;

            case Packet.JOIN_GAME:
                Game game = service.getGame(data.toString());
                game.join(clientConnection);

                GameThread gameThread = new GameThread(game, game.getPlayer1ClientConnection(), clientConnection);
                gameThreadList.put(game.getGameName(), gameThread);
                gameThread.start();

                /*
                try {
                    ds.insertGame(game);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                game.start();
                */
                break;

            case Packet.OBSERVE_GAME:
                Game ObserverGame = service.getGame(data.toString());
                break;

            case Packet.GAME_MOVE:
                // packet request type game move

                Move newMove = (Move)data;

                if (gameThreadList.containsKey(newMove.getGameName()))
                {
                    GameThread gameThreadForMove = gameThreadList.get(newMove.getGameName());
                    gameThreadForMove.addMove(newMove);
                }
                else
                {
                    System.out.println("Opponent has not been found!");
                    Packet errorPacket = new Packet(Packet.NO_OPPONENT_FOUND, clientConnection.getInformation(), "No Opponent Found");
                    try
                    {
                        clientConnection.getOutputStream().writeObject(errorPacket);
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }

                break;


        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}