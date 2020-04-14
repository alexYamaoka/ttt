package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Server.ClientConnection;
import Server.Service;
import Shared.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountService implements Service, Runnable {
    private Thread worker;
    private HashMap<String, ClientConnection> clientConnections = new HashMap<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private DataSource ds = DatabaseManager.getInstance();
    public AccountService() {
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
            var pool = Executors.newFixedThreadPool(100);
            System.out.println("Server started");
            while (running.get()) {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(socket, this);
                pool.execute(connection);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handle(Packet packet, ObjectOutputStream outputStream) {
        AccountHandler handler = new AccountHandler(packet, outputStream);
        handler.start();
    }
}
