package Client;

import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;

public class Client {
    private String hostName;
    private int port;
    private UserInformation userInformation;
    private ClientController controller;
    private WriteToServerTask writeToServerTask;
    private ReadFromServerTask readFromServerTask;

    private ArrayDeque<Packet> requestsToServer = new ArrayDeque<>();

    public Client(String hostName, int port, UserInformation userInformation, ClientController controller) {
        this.hostName = hostName;
        this.port = port;
        this.userInformation = userInformation;
        this.controller = controller;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostName, port);
            writeToServerTask = new WriteToServerTask(socket, this);
            readFromServerTask = new ReadFromServerTask(socket, this);
            writeToServerTask.start();
            readFromServerTask.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addRequestToServer(Packet packet) {
        requestsToServer.add(packet);
    }

    public void addResponseFromServer(Packet packet) {
        controller.addResponse(packet);
    }


    public Packet getNextRequestToServer() {
        if (!requestsToServer.isEmpty()) {
            return requestsToServer.pop();
        }
        return null;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public void stop() {
        writeToServerTask.stop();
        readFromServerTask.stop();
    }

}
