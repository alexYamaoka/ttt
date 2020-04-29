package GameService;

import Models.Game;
import Models.Move;
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
    // takes in two players
    private ClientConnection player1;
    private ClientConnection player2;
    private Thread thread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private ObjectOutputStream outputToPlayer1;
    private ObjectOutputStream outputToPlayer2;
    private ObjectInputStream inputFromPlayer1;
    private ObjectInputStream inputFromPlayer2;
    private ArrayList<ClientConnection> GameObservers;
    private Game game;

    public GameThread(ClientConnection player1, ClientConnection player2, ArrayList<ClientConnection> GameObservers)
    {
        game = new Game(player1.getInformation(), player2.getInformation());
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


        // passing moves to each other.  not the game
        // create a class called game move.

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
                // check if valid move,
                // checks the game model
                // if its a valid move, send it to both players.
                // notify observers
                // want to update UI from the server side.
                // if game ends, break out of loop and end the task


                boolean player1MadeMove = false;


                // loops until player 1 makes a valid move
                while (!player1MadeMove)
                {
                    Packet player1Move = (Packet) inputFromPlayer1.readObject();
                    Move move1 = (Move) player1Move.getData();


                    if (player1Move.getRequest().equals(Packet.GAME_MOVE))
                    {
                        if (game.checkIfValidMove(move1))
                        {
                            game.makeMove(move1);
                            outputToPlayer2.writeObject(player1Move);
                            outputToPlayer1.writeObject(player1Move);
                            player1MadeMove = true;
                        }
                    }
                }




                boolean player2MadeMove = false;



                // loops until player 2 makes a valid move
                while (!player2MadeMove)
                {
                    Packet player2Move = (Packet) inputFromPlayer2.readObject();
                    Move move2 = (Move) player2Move.getData();

                    if (player2Move.getRequest().equals(Packet.GAME_MOVE))
                    {
                        if (game.checkIfValidMove(move2))
                        {
                            game.makeMove(move2);
                            outputToPlayer2.writeObject(player2Move);
                            outputToPlayer1.writeObject(player2Move);
                            player2MadeMove = true;
                        }
                    }
                }

            }
            catch (IOException | ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }


        }


    }


}
