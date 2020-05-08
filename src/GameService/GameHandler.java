package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Models.Move;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameHandler implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Packet packet;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();
    private GameService service;
    private ClientConnection clientConnection;


    public GameHandler(ClientConnection clientConnection, Packet packet, GameService service) {
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

        System.out.println("Request: " + request);

        switch (request) {
            case Packet.GET_GAMES:
                clientConnection.sendPacketToClient(new Packet(Packet.GET_GAMES, userInformation, service.getGames()));
                break;

            case Packet.NEW_GAME_CREATED:
                Game game = new Game(clientConnection);
                service.addGame(game); // add game to game list and broadcast
                Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientConnection.getInformation(), game);

                clientConnection.sendPacketToClient(packet);

                break;

            case Packet.JOIN_GAME:
                try {
                    Game joinGame = service.getGame(data.toString());
                    joinGame.join(clientConnection);
                    GameThread gameThread = new GameThread(joinGame, joinGame.getPlayer1ClientConnection(), clientConnection);
                    gameThreadList.put(joinGame.getId(), gameThread);
                    gameThread.start();

                    // Send successful join message
                    Game sendGame = new Game(joinGame);
                    Packet joinPacket = new Packet(Packet.JOIN_GAME, userInformation, sendGame);
                    System.out.println(joinGame.getPlayer2Username());

                    clientConnection.sendPacketToClient(joinPacket);

                    Packet broadcast = new Packet(Packet.GET_GAMES, clientConnection.getInformation(), service.getGames());
                    System.out.println("right before service.broadcast()");
                    service.broadcast(broadcast);


                    // update server display
                    Packet notifyGameJoined = new Packet(Packet.ACTIVE_GAME, null, "Game ID: " + sendGame.getId());
                    service.notifyServerDisplay(notifyGameJoined);


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    running.set(false);
                }
                break;

            case Packet.OBSERVE_GAME:
                Game observerGame = service.getGame(data.toString());
                observerGame.addGameObserver(clientConnection);
                break;

            case Packet.GAME_MOVE:
                try {
                    Move newMove = (Move) data;

                    if (gameThreadList.containsKey(newMove.getGameId())) {
                        GameThread gameThreadForMove = gameThreadList.get(newMove.getGameId());
                        gameThreadForMove.addMove(newMove);

                    } else {
                        // else statement is for when opponent has not been found yet.
                        Packet errorPacket = new Packet(Packet.GAME_STATUS, clientConnection.getInformation(), newMove.getGameId() + " " + "No-Opponent-Found");
                        clientConnection.sendPacketToClient(errorPacket);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    running.set(false);
                }
                break;

            case Packet.GAME_CLOSE:
                try {
                    if (service.removeGameFromLobby(data.toString())) {
                        System.out.println("Game closed!");
                    } else {
                        System.out.println("Game not closed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}