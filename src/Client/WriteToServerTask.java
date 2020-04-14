package Client;

import Shared.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteToServerTask implements Runnable
{
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private Client client;
    private Thread thread;
    private boolean isRunning;

    public WriteToServerTask(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;


        try
        {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());





        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
