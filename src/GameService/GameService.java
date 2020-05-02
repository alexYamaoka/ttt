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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameService implements Runnable, Service
{
    private Thread worker;
    private HashSet<Service> serviceListeners = new HashSet<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private DataSource ds = DatabaseManager.getInstance();
    private final int PORT_NUMBER = 8080;

    private static Lock lock = new ReentrantLock();
    private HashSet<ClientConnection> clientConnections = new HashSet<>();

    private final HashMap<String, Game> ongoingGameRooms = new HashMap<>();

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
                var pool = Executors.newFixedThreadPool(100);
                System.out.println("Game Service started");
                while (running.get()) {
                    Socket socket = serverSocket.accept();
                    ClientConnection connection = new ClientConnection(socket, this);
                    clientConnections.add(connection);
                    pool.execute(connection);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public void addGame(Game game){
        ongoingGameRooms.put(game.getGameName(),game);
    }

    public Game getGame(String Id){
        return ongoingGameRooms.get(Id);
    }

    public Set<String> getGames(){
        return ongoingGameRooms.keySet();
    }

    public void handle(ClientConnection clientConnection, Packet packet) {

        GameHandler handler = new GameHandler(clientConnection, packet, this);
        handler.start();
    }

    public void addServiceListener(Service service) {
        serviceListeners.add(service);
    }

    public void broadcast(Packet packet) {
        for(ClientConnection connection : clientConnections) {
            try {
                connection.getOutputStream().writeObject(packet);
            } catch (IOException ex) {
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
