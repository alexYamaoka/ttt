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
    //private Thread thread;
    private boolean isRunning = true;

    public WriteToServerTask(Socket socket, Client client)
    {
        this.socket = socket;
        this.client = client;


        try
        {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


            //Packet packet = new Packet(Packet.REGISTER_CLIENT, client.getUserInformation(), client.getUserInformation());
            //objectOutputStream.writeObject(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    @Override
    public void run()
    {
        while (isRunning)
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
                isRunning = false;
            }
        }
    }

}
