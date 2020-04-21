package Client;

import Shared.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadFromServerTask implements Runnable
{
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private Client client;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);


    public ReadFromServerTask(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;

        try
        {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
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
                Packet response = (Packet) objectInputStream.readObject();
                client.addResponseFromServer(response);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                running.set(false);
            }
        }

        try
        {
            socket.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            running.set(false);
        }
    }
}
