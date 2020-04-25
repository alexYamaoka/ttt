package GameService;

import Server.ClientConnection;

import java.util.concurrent.atomic.AtomicBoolean;


public class GameThread implements Runnable
{
    // takes in two players
    ClientConnection player1;
    ClientConnection player2;
    Thread thread;
    AtomicBoolean isRunning = new AtomicBoolean(false);




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
        isRunning.set(false);
    }


    @Override
    public void run()
    {
        isRunning.set(true);

        while (isRunning.get())
        {
            // play ttt game
            player1.getOutputStream();
            player2.getOutputStream();

            // while game has not ended
            // send and receive packet with game inside
        }


    }


}
