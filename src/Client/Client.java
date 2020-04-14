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
    private ArrayDeque<Packet> requests = new ArrayDeque<>();
    private ArrayDeque<Packet> response = new ArrayDeque<>();
    WriteThread writeThread;


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



            ReadTask readTask = new ReadTask(socket, this);
            Thread readThread = new Thread(readTask);
            readThread.start();

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }



}
