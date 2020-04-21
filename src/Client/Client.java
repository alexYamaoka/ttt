package Client;

import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;

public class Client
{
    private String hostName;
    private int port;
    private UserInformation userInformation;
    private ArrayDeque<Packet> requestsToServer = new ArrayDeque<>();
    private ArrayDeque<Packet> responseFromServer = new ArrayDeque<>();



    public Client(String hostName, int port, UserInformation userInformation)
    {
        this.hostName = hostName;
        this.port = port;
        this.userInformation = userInformation;
    }


    public void execute()
    {
        try
        {
            Socket socket = new Socket(hostName, port);

            WriteToServerTask writeToServerTask = new WriteToServerTask(socket, this);
            ReadFromServerTask readTask = new ReadFromServerTask(socket, this);


            Thread writeThread = new Thread(writeToServerTask);
            Thread readThread = new Thread(readTask);

            writeThread.start();
            readThread.start();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }




    public void addRequestToServer(Packet packet)
    {
        requestsToServer.add(packet);
    }

    public void addResponseFromServer(Packet packet)
    {
        responseFromServer.add(packet);
    }


    public Packet getNextRequestToServer()
    {
        if (! requestsToServer.isEmpty())
        {
            return requestsToServer.pop();
        }

        return null;
    }

    public Packet getNextResponseFromServer()
    {
        if (! responseFromServer.isEmpty())
        {
            return responseFromServer.pop();
        }

        return null;
    }

    public UserInformation getUserInformation()
    {
        return userInformation;
    }

}
