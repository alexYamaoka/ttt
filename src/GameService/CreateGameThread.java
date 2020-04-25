package GameService;

import Server.ClientConnection;

import java.util.concurrent.atomic.AtomicBoolean;

public class CreateGameThread implements Runnable
{
    // if theres two people in the queue
    // creates new thread and pulls people from the queue
    // pop two players from the queue
    // start the gamethread(player1, player2)


    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread thread;


    public CreateGameThread()
    {
        // needs access to the queue for game
        // need to make retrieving 2 players from the queue synchronized

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
            // if there's two players in the queue, pop the two and start a new thread with the two players

            // start game thread with the 2 players
            ClientConnection player1;
            ClientConnection player2;

            //GameThread gameThread = new GameThread(player1, player2);

        }
    }
}
