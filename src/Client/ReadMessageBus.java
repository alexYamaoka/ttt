package Client;

import Models.Move;
import ObserverPatterns.*;
import Shared.Packet;
import Shared.UserInformation;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadMessageBus implements Runnable
{
    private ClientController clientController;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);

    private SignInResultListener signInResultListener;
    private SignUpResultListener signUpResultListener;
    private UpdateUserinformationListener updateUserinformationListener;
    private GameListener gameListener;
    private LobbyListener lobbyListener;
    private HashSet<String> listOfGames;



    public ReadMessageBus(ClientController clientController)
    {
        this.clientController = clientController;
        signInResultListener = clientController.getSignInController();
        signUpResultListener = clientController.getSignUpController();
        updateUserinformationListener = clientController.getOptions();
        gameListener = clientController.getGameBoardController();
        lobbyListener = clientController.getGameLobby();
    }

    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }



    @Override
    public void run()
    {
        running.set(true);
        while (running.get())
        {
            Packet response = clientController.getNextResponse();

            if (response != null)
            {
                switch (response.getRequest())
                {
                    case Packet.REGISTER_CLIENT:
                        signUpResultListener.updateSignInResult(response.getData().toString());
                        break;

                    case Packet.SIGN_IN:
                        signInResultListener.updateSignInResult(response.getData().toString());
                        break;

                    case Packet.SIGN_OUT:
                        break;

                    case Packet.UPDATE_USER:
                        updateUserinformationListener.updateUserinformation(response.getData().toString());
                        break;


                    case Packet.GET_GAMES:
                        listOfGames = (HashSet<String>)response.getData();
                        System.out.println("Received list of games: " + listOfGames);
                        if (listOfGames != null)
                        {
                            lobbyListener.getListOfGames(listOfGames);
                        }
                        break;

                    case Packet.JOIN_GAME:
                        System.out.println("join game inside readMessageBus");

                        break;


                    case Packet.GAME_MOVE:
                        gameListener.updateMove((Move)response.getData());
                        break;

                    case Packet.NEW_GAME_CREATED:
                        lobbyListener.newGame(response.getData().toString());
                        break;

                    case Packet.Game_Name:
                        lobbyListener.updateUIWithNewGame(response.getData().toString());
                        gameListener.setGameName(response.getData().toString());
                        break;


                    case Packet.PLAYER_ONE_USERNAME:
                        gameListener.setPlayer1Username(response.getData().toString());
                        break;

                    case Packet.PLAYER_TWO_USERNAME:
                        gameListener.setPlayer2Username(response.getData().toString());
                        break;

                    case Packet.PLAYER_ONE_WINS:
                        gameListener.updateStatus(response.getData().toString());
                        break;

                    case Packet.PLAYER_TWO_WINS:
                        gameListener.updateStatus(response.getData().toString());
                        break;

                    case Packet.TIE_GAME:
                        gameListener.updateStatus(response.getData().toString());
                        break;
                }
            }
        }
    }
}
