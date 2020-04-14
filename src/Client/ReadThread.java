package Client;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadThread implements Runnable
{
    private ObjectInputStream input;
    private Socket socket;
    private Client client;
    private Thread thread;
    private boolean isRunning = false;

    



    @Override
    public void run()
    {

    }
}
