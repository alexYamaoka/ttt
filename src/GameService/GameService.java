package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Server.ClientConnection;
import Server.Service;
import Shared.Packet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameService implements Runnable, Service {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final int PORT_NUMBER = 8080;
    private Thread worker;
    private HashSet<Service> serviceListeners = new HashSet<>();
    private DataSource ds = DatabaseManager.getInstance();
    private HashSet<ClientConnection> clientConnections = new HashSet<>();
    private HashMap<String, Game> ongoingGameRooms = new HashMap<>();
    private HashMap<String, GameThread> gameThreadList = new HashMap<>();

    public GameService() {
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
        System.out.println("Game Service Has Stopped!");
    }

    @Override
    public void run() {
        running.set(true);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER, 0, InetAddress.getByName("localhost"));
            var pool = Executors.newFixedThreadPool(20);
            System.out.println("Game Service started");
            while (running.get()) {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(socket, this);
                clientConnections.add(connection);                              // adds to hashMap of client connections
                pool.execute(connection);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public void addGame(Game game) {
        ongoingGameRooms.put(game.getId(), game);
        Packet packet = new Packet(Packet.GET_GAMES, null, getGames());
        broadcast(packet);
    }

    public Game getGame(String id) {
        return ongoingGameRooms.get(id);
    }

    public HashSet<Game> getGames() {
        if (!ongoingGameRooms.isEmpty()) {
            return new HashSet<Game>(ongoingGameRooms.values());
        }
        return null;
    }

    public HashMap<String, GameThread> getGameThreadList() {
        return gameThreadList;
    }


    public void handle(ClientConnection clientConnection, Packet packet) {

        GameHandler handler = new GameHandler(clientConnection, packet, this);
        handler.start();
    }

    public void addServiceListener(Service service) {
        serviceListeners.add(service);
    }

    public void broadcast(Packet packet) {
        for (ClientConnection connection : clientConnections) {
            try {
                connection.sendPacketToClient(packet);
                //connection.getOutputStream().flush();
                //connection.getOutputStream().writeObject(packet);
                //connection.getOutputStream().flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for (Service service : serviceListeners) {
            service.update(packet);
        }
    }


    public void update(Packet packet) {

    }


}