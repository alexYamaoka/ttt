package ComputerPlayer;

import Models.Game;
import Models.Move;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class AiPlayer implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Game game;
    private UserInformation userInformation;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Thread thread;
    private Playable playMethod;

    public AiPlayer(Game game, Playable playMethod) {
        this.game = game;
        this.playMethod = playMethod;
        // mimic a real player
        try {
            userInformation = new UserInformation("Computer", "Computer", "Computer", "Computer", "Computer");
            socket = new Socket("localhost", 8080);

            // join the game
            Packet packet = new Packet(Packet.JOIN_GAME, userInformation, game.getId());
            outputStream.writeObject(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                Packet response = (Packet) inputStream.readObject();
                switch(response.getRequest()) {
                    case Packet.GAME_MOVE:
                        MinimaxAi.Move aiMove = playMethod.play(game);
                        Move move = new Move(aiMove.row, aiMove.col, userInformation, game.getId());
                        Packet packet = new Packet(Packet.GAME_MOVE, userInformation, move);
                        outputStream.writeObject(packet);
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                running.set(false);
            }
        }
    }
}
