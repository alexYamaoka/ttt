package GameService;

import Server.ClientConnection;

import java.util.concurrent.atomic.AtomicBoolean;


public class GameThread implements Runnable
{
    // takes in two players
    ClientConnection player1;
    ClientConnection player2;
    Thread thread;
    AtomicBoolean running = new AtomicBoolean(false);




    public GameThread(ClientConnection player1, ClientConnection player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }


    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    public void stop()
    {
        running.set(false);
    }


    @Override
    public void run()
    {
        running.set(true);

        while (running.get())
        {

        }


    }


}
