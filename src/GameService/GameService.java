package GameService;

import AccountService.AccountHandler;
import Client.Client;
import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Server.ClientConnection;
import Server.Service;
import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameService implements Runnable, Service
{
    private Thread worker;
    private HashSet<Service> serviceListeners = new HashSet<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private DataSource ds = DatabaseManager.getInstance();
    private final int PORT_NUMBER = 8080;

    // socket
    // packet (userinformation is provided)

    private HashSet<ClientConnection> clientConnections = new HashSet<>();
    private final Queue<ClientConnection> gameWaitingList = new LinkedList<>();
    private final HashMap<String, GameRoomInformation> ongoingGameRooms = new HashMap<>();


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
                // Create a server socket for listening for requests
                ServerSocket serverSocket = new ServerSocket(PORT_NUMBER, 0, InetAddress.getByName("localhost"));
                var pool = Executors.newFixedThreadPool(100);
                System.out.println("Game Service started");
                while (running.get()) {
                    Socket socket = serverSocket.accept();
                    ClientConnection connection = new ClientConnection(socket, this);

                    // need a way to connect the client connection with the users information who is signed in
                    //clientConnections.add(connection);

                    pool.execute(connection);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public void handle(ClientConnection clientConnection, Packet packet, ObjectOutputStream outputStream) {
        GameHandler handler = new GameHandler(clientConnection, packet, outputStream);
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
