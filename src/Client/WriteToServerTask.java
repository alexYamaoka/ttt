package Client;

import Shared.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class WriteToServerTask implements Runnable
{
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private Client client;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);

    public WriteToServerTask(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;
        try
        {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Packet("CONNECT", client.getUserInformation(), "CONNECT"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run()
    {
        running.set(true);
        while (running.get())
        {
            try
            {
                Packet packet = client.getNextRequestToServer();

                if (packet != null)
                {
                    objectOutputStream.writeObject(packet);
                    objectOutputStream.flush();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
                running.set(false);
            }
        }
    }

}
