package Client;

import Models.Move;
import ObserverPatterns.*;
import Shared.Packet;
import Shared.UserInformation;

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

                    case Packet.GAME_MOVE:
                        gameListener.updateMove(((Move)response.getData()).getMove());
                        break;

                    case Packet.NEW_GAME_CREATED:
                        System.out.println("READ MESSAGE BUS!" + response.getData());
                        lobbyListener.newGame(response.getData().toString());
                        break;
                }

            }
        }
    }
}
