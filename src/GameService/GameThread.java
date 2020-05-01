package GameService;

import Models.Game;
import Models.Move;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameThread implements Runnable {
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

    public GameThread(ClientConnection player1, ClientConnection player2, ArrayList<ClientConnection> GameObservers) {

        this.player1 = player1;
        this.player2 = player2;
        this.GameObservers = GameObservers;
        outputToPlayer1 = player1.getOutputStream();
        outputToPlayer2 = player2.getOutputStream();
        inputFromPlayer1 = player1.getInputStream();
        inputFromPlayer2 = player2.getInputStream();
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning.set(false);
    }


    @Override
    public void run() {
        isRunning.set(true);
        // passing moves to each other.  not the game
        // create a class called game move.
        try {
            game = new Game(player1,game.getGameName());
            Packet joinGamePacket = new Packet(Packet.JOIN_GAME,player2.getInformation(),"Opponent Found!");
            game.join(player2);
            outputToPlayer1.writeObject(joinGamePacket);
            outputToPlayer2.writeObject(joinGamePacket);




        } catch (IOException ex) {
            ex.printStackTrace();
        }
        while (isRunning.get()) {

            try {

                boolean player1MadeMove = false;
                boolean player2MadeMove = false;

                // loops until player 1 makes a valid move
                while (!player1MadeMove) {
                    Packet player1Move = (Packet) inputFromPlayer1.readObject();

                    if (player1Move.getRequest().equals(Packet.GAME_MOVE)) {
                        Move move1 = (Move) player1Move.getData();
                        // check if its a valid move
                        if (game.checkIfValidMove(move1)) {
                            game.player1MakeMove(move1);
                            outputToPlayer2.writeObject(player1Move);
                            outputToPlayer1.writeObject(player1Move);
                            player1MadeMove = true;

                            if (game.isPlayer1Winner(move1)) {
                                System.out.println("Player 1 Wins");
                            }

                            if (game.isTieGame()) {
                                System.out.println("Tie Game");
                            }
                        } else {
                            System.out.println("Not a valid move");
                        }


                        // TODO: isGameOver() inside game
//                        if (game.isGameOver())
//                        {
//
//                        }
                    }
                }


                // loops until player 2 makes a valid move
                while (!player2MadeMove) {
                    Packet player2Move = (Packet) inputFromPlayer2.readObject();

                    if (player2Move.getRequest().equals(Packet.GAME_MOVE)) {
                        Move move2 = (Move) player2Move.getData();

                        if (game.checkIfValidMove(move2)) {
                            game.player2MakeMove(move2);
                            outputToPlayer2.writeObject(player2Move);
                            outputToPlayer1.writeObject(player2Move);
                            player2MadeMove = true;


                            if (game.isPlayer2Winner(move2)) {
                                System.out.println("Player 2 Wins");
                            }

                            if (game.isTieGame()) {
                                System.out.println("Tie Game");
                            }
                        } else {
                            System.out.println("Not a valid move");
                        }


                        // TODO: isGameOver() inside game
//                        if (game.isGameOver())
//                        {
//
//                        }
                    }
                }


            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }



}
