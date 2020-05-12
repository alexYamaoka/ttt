package Client;

import Models.Game;
import Models.Move;
import ObserverPatterns.*;
import Shared.Packet;
import Shared.UserInformation;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadMessageBus implements Runnable {
    private ClientController clientController;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);

    private SignInResultListener signInResultListener;
    private SignUpResultListener signUpResultListener;
    private UpdateUserinformationListener updateUserinformationListener;
    private LobbyListener lobbyListener;
    private HistoryListener historyListener;

    public ReadMessageBus(ClientController clientController) {
        this.clientController = clientController;
        signInResultListener = clientController.getSignInController();
        signUpResultListener = clientController.getSignUpController();
        updateUserinformationListener = clientController.getOptions();
        lobbyListener = clientController.getGameLobby();
        historyListener = clientController.getGameHistoryController();
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
        Packet response = null;
        try {
            running.set(true);
            while (running.get()) {
                response = clientController.getNextResponse();

                if (response != null) {
                    switch (response.getRequest()) {
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

                        case Packet.DELETE_ACCOUNT:
                            updateUserinformationListener.deactivateAccount(response.getData().toString());
                            break;

                        case Packet.ACTIVATE_ACCOUNT:
                            updateUserinformationListener.ActivateAccount(response.getData().toString());
                            break;

                        case Packet.GET_GAMES:
                            HashSet<Game> listOfGames = (HashSet<Game>) response.getData();
                            if (listOfGames != null) {
                                lobbyListener.getListOfGames(listOfGames);
                            } else {
                                lobbyListener.clearGameList();
                            }
                            break;

                        case Packet.GET_ONLINE_PLAYERS:
                            HashSet<UserInformation> listOfPlayers = (HashSet<UserInformation>) response.getData();
                            if (listOfPlayers != null) {
                                lobbyListener.getListOfOnlinePlayers(listOfPlayers);
                            }
                            break;

                        case Packet.JOIN_GAME:
                            if (response.getData() != null) {
                                System.out.println("Response: JOIN_GAME");
                                lobbyListener.joinGame((Game) response.getData());
                            }
                            break;

                        case Packet.OBSERVE_GAME:
                            if(response.getData() != null) {
                                lobbyListener.spectateGame((Game) response.getData());
                            }
                            break;

                        case Packet.GAME_MOVE:
                            lobbyListener.updateMove((Move) response.getData());
                            break;

                        case Packet.NEW_GAME_CREATED:
                            if (response.getData() != null) {
                                lobbyListener.newGame((Game) response.getData());
                            } else {
                                // notify bad game
                            }
                            break;

                        case Packet.PLAYER_ONE_USERNAME:
                            lobbyListener.setPlayer1Username(response.getData().toString());
                            break;

                        case Packet.PLAYER_TWO_USERNAME:
                            lobbyListener.setPlayer2Username(response.getData().toString());
                            break;

                        case Packet.GAME_STATUS:
                            lobbyListener.updateStatus(response.getData().toString());
                            break;

                        case Packet.GAME_HISTORY:
                            if(response.getData() != null) {
                                historyListener.updateHistory((List<Game>)response.getData());
                            }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Read Message Exception: " + ex.getMessage());
            System.out.println("Type: " + response.getRequest());
            System.out.println("Data: " + response.getData());
        }
    }
}
