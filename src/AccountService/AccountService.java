package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import ObserverPatterns.ServiceListener;
import Server.ClientConnection;
import Server.Service;
import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountService implements Service, Runnable {
    private static AccountService instance = null;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;
    private HashSet<ClientConnection> clientConnections = new HashSet<>();
    private HashSet<ServiceListener> serviceListeners = new HashSet<>();
    private DataSource ds = DatabaseManager.getInstance();
    private HashSet<UserInformation> playersOnline = new HashSet<>();

    public AccountService() {
        start();
    }

    public static AccountService getInstance() {
        if (instance == null) {
            synchronized (AccountService.class) {
                if(instance == null) {
                    instance = new AccountService();
                }
            }
        }
        return instance;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
        System.out.println("Account Service Has Stopped!");
    }

    @Override
    public void run() {
        running.set(true);
        try {
            // Create a server socket for listening for requests
            ServerSocket serverSocket = new ServerSocket(8000, 0, InetAddress.getByName("localhost"));
            var pool = Executors.newFixedThreadPool(20);
            System.out.println("Account Service started");
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

    @Override
    public void handle(ClientConnection clientConnection, Packet packet) {
        AccountHandler handler = new AccountHandler(clientConnection, packet);
        handler.start();
    }

    public void addServiceListener(ServiceListener serviceListener) {
        serviceListeners.add(serviceListener);
    }

    public void broadcast(Packet packet) {
        for (ClientConnection connection : clientConnections) {
            try {
                connection.getOutputStream().reset();
                connection.getOutputStream().writeObject(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//        for (ServiceListener serviceListener : serviceListeners) {
//            serviceListener.onDataChanged(packet);
//        }
    }

    public void notifyServerDisplay(Packet packet)
    {
        for (ServiceListener serviceListener : serviceListeners) {
            serviceListener.onDataChanged(packet);
        }
    }

    public void addOnlinePlayer(UserInformation user) {
        playersOnline.add(user);
        Packet packet = new Packet(Packet.GET_ONLINE_PLAYERS, null, getPlayersOnline());
        broadcast(packet);
    }

    public HashSet<UserInformation> getPlayersOnline() {
        if (!playersOnline.isEmpty()) {
            return playersOnline;
        }
        return null;
    }


    public void update(Packet packet) {

    }
}
