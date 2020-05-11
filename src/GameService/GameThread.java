package GameService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Models.Move;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameThread implements Runnable {
    // takes in two players
    private Thread thread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ArrayList<ClientConnection> gameObservers;
    private Game game;
    private DataSource ds = DatabaseManager.getInstance();

    private ClientConnection player1;
    private ClientConnection player2;
    private UserInformation player1UserInformation;
    private UserInformation player2UserInformation;
    private boolean isPlayer1Turn = true;
    private boolean hasPlayerMadeMove = true;

    private BlockingQueue<Move> moveQueue = new LinkedBlockingQueue<>();


    public GameThread(Game game, ClientConnection player1, ClientConnection player2) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
        player1UserInformation = player1.getInformation();
        player2UserInformation = player2.getInformation();
    }

    public synchronized void addMove(Move move) throws InterruptedException {
        System.out.println("Inside GameThread, adding move to the queue");
        moveQueue.add(move);
    }


    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning.set(false);
        game.setGameStatus("GAME ENDED");
        // Notify users game has stopped
        Packet packet = new Packet(Packet.GAME_CLOSE, null, "GAME ENDED");
        player1.sendPacketToClient(packet);
        player2.sendPacketToClient(packet);
        game.notifyObservers(packet);
    }


    @Override
    public void run() {
        isRunning.set(true);
        // Update game Status
        game.setGameStatus("IN PROGRESS");
        GameService gameService = (GameService)player1.getService();
        Packet gameStatus = new Packet(Packet.GET_GAMES, null, gameService.getGames());
        gameService.broadcast(gameStatus);

        // sends the username of player1
        Packet whoIsPlayer1 = new Packet(Packet.PLAYER_ONE_USERNAME, player1UserInformation, game.getId() + " " + player1UserInformation.getUserName());

        player1.sendPacketToClient(whoIsPlayer1);
        player2.sendPacketToClient(whoIsPlayer1);
        System.out.println("sending packet about player1");

        // sends the username of player2
        Packet whoIsPlayer2 = new Packet(Packet.PLAYER_TWO_USERNAME, player2UserInformation, game.getId() + " " + player2UserInformation.getUserName());
        player1.sendPacketToClient(whoIsPlayer2);
        player2.sendPacketToClient(whoIsPlayer2);
        System.out.println("sending packet about player2");

        isPlayer1Turn = true;

        while (isRunning.get()) {

            Move newMove = null;

            try {
                if (!moveQueue.isEmpty()) {
                    System.out.println("Inside game thread, removing move from the queue ");
                    newMove = moveQueue.take();
                    game.setStartTime();

                    if (newMove.getUserInformation() == player1UserInformation && isPlayer1Turn) {
                        System.out.println("game player1.make move is called");

                        if (game.checkIfValidMove(newMove)) {
                            game.player1MakeMove(newMove);
                            isPlayer1Turn = false;
                            Packet packet = new Packet(Packet.GAME_MOVE, player1UserInformation, newMove);

                            player1.sendPacketToClient(packet);
                            player2.sendPacketToClient(packet);
                            game.notifyObservers(packet);
                            System.out.println("move is outputted to both players");

                            isPlayer1Turn = false;

                            if (game.isPlayer1Winner(newMove)) {
                                System.out.println("Player 1 Wins!");
                                String winner = game.getPlayer1Info().getUserName();
                                Packet player1Wins = new Packet(Packet.GAME_STATUS, player1UserInformation, game.getId() + " " + winner);
                                player1.sendPacketToClient(player1Wins);
                                player2.sendPacketToClient(player1Wins);;
                                game.notifyObservers(player1Wins);
                                game.setEndTime();

                                // Update Game Status
                                game.setGameStatus("Player 1 Wins!");
                                gameStatus = new Packet(Packet.GET_GAMES, null, gameService.getGames());
                                gameService.broadcast(gameStatus);

                                // add game to database
                                try {
                                    ds.insertGame(game);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            } else if (game.isTieGame()) {
                                System.out.println("Tie Game");
                                Packet tieGame = new Packet(Packet.GAME_STATUS, null, game.getId() + " " + "Tie!");

                                player1.sendPacketToClient(tieGame);
                                player2.sendPacketToClient(tieGame);
                                game.notifyObservers(tieGame);
                                game.setEndTime();
                            }
                        } else {
                            System.out.println("Not a valid move");
                            Packet invalidMove = new Packet(Packet.GAME_STATUS, null, game.getId() + " " + "invalid-move");
                            player1.sendPacketToClient(invalidMove);
                        }
                    } else if (newMove.getUserInformation() == player2UserInformation && !isPlayer1Turn) {
                        System.out.println("game player2.make move is called");

                        if (game.checkIfValidMove(newMove)) {
                            game.player2MakeMove(newMove);

                            Packet packet = new Packet(Packet.GAME_MOVE, player2UserInformation, newMove);

                            player1.sendPacketToClient(packet);
                            player2.sendPacketToClient(packet);
                            game.notifyObservers(packet);
                            System.out.println("move is outputted to both players");

                            isPlayer1Turn = true;

                            if (game.isPlayer2Winner(newMove)) {
                                System.out.println("Player 2 Wins!");
                                String winner = game.getPlayer2Info().getUserName();
                                Packet player2Wins = new Packet(Packet.GAME_STATUS, player2UserInformation, game.getId() + " " + winner);
                                player1.sendPacketToClient(player2Wins);
                                player2.sendPacketToClient(player2Wins);
                                game.notifyObservers(player2Wins);
                                game.setEndTime();

                                // Update Game Status
                                game.setGameStatus("Player 2 Wins!");
                                gameStatus = new Packet(Packet.GET_GAMES, null, gameService.getGames());
                                gameService.broadcast(gameStatus);

                                // add game to database
                                try {
                                    ds.insertGame(game);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            } else if (game.isTieGame()) {
                                System.out.println("Tie Game");
                                Packet tieGame = new Packet(Packet.GAME_STATUS, null, game.getId() + " " + "Tie!");

                                player1.sendPacketToClient(tieGame);
                                player2.sendPacketToClient(tieGame);
                                game.notifyObservers(tieGame);
                                game.setEndTime();
                            }
                        } else {
                            System.out.println("Not a valid move");
                            Packet invalidMove = new Packet(Packet.GAME_STATUS, null, game.getId() + " " + "invalid-move");
                            player2.sendPacketToClient(invalidMove);
                        }
                    } else {
                        System.out.println("make move if statements have been skipped");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}