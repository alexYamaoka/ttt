package GameService;

import java.util.concurrent.atomic.AtomicBoolean;

public class CreateGame implements Runnable
{
    // if theres two people in the queue
    // creates new thread and pulls people from the queue
    // pop two players from the queue
    // start the gamethread(player1, player2)


    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread thread;


    public CreateGame()
    {

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
        // if theres two players in the queue, start a new thread with the two players
    }
}
