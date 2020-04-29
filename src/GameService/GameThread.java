package GameService;

import Server.ClientConnection;
import Shared.Packet;
import javafx.scene.chart.ScatterChart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameThread implements Runnable
{
    private ClientConnection player1;
    private ClientConnection player2;
    private Thread thread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private ObjectOutputStream outputToPlayer1;
    private ObjectOutputStream outputToPlayer2;
    private ObjectInputStream inputFromPlayer1;
    private ObjectInputStream inputFromPlayer2;
    private ArrayList<ClientConnection> GameObservers;

    public GameThread(ClientConnection player1, ClientConnection player2, ArrayList<ClientConnection> GameObservers)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.GameObservers = GameObservers;
        outputToPlayer1 = player1.getOutputStream();
        outputToPlayer2 = player2.getOutputStream();
        inputFromPlayer1 = player1.getInputStream();
        inputFromPlayer2 = player2.getInputStream();
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

        Packet move; //gameReceived

        try
        {
            Packet packet1 = new Packet(Packet.NEW_GAME_CREATED, player1.getInformation(), "Opponent Found!");
            Packet packet2 = new Packet(Packet.NEW_GAME_CREATED, player2.getInformation(), "Opponent Found!");
            outputToPlayer1.writeObject(packet2);
            outputToPlayer2.writeObject(packet1);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }


        while (isRunning.get())
        {
            // play ttt game
            // while game has not ended
            // send and receive packet with game inside

            try
            {
                move = (Packet) inputFromPlayer1.readObject();
                //check move
                outputToPlayer2.writeObject(move);
                move = (Packet) inputFromPlayer2.readObject();
                //check move
                outputToPlayer1.writeObject(move);

                // if game ends, break out of loop and end the task

            }
            catch (IOException | ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }


        }


    }


}
