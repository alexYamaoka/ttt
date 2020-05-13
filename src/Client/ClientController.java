package Client;

import Shared.Packet;
import Shared.UserInformation;
import UI.Client.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayDeque;

public class ClientController {
    private Client accountClient;
    private Client gameClient;
    private Stage stage;

    // Controllers to be initialized
    private SignInController signInController;
    private MainMenuController mainMenuController;
    private UI.Client.SignUpController signUpController;
    private UI.Client.Options options;
    private GameLobbyController gameLobby;
    private GameHistoryController gameHistoryController;

    // Scenes
    private Pane signInPane;
    private Pane signUpPane;
    private Pane mainMenuPain;
    private Pane optionsPane;
    private Pane lobbyPane;
    private Pane gameHistoryPane;

    private ReadMessageBus readMessageBus;

    private ArrayDeque<Packet> responses = new ArrayDeque<>();

    public ClientController(Stage stage) {
        this.stage = stage;
        accountClient = new Client("localhost", 8000, new UserInformation("NA", "NA", "Anonymous", "NA", "NA"), this);
        initialize();
        setUpClientToUI();
    }

    // initializes controllers
    private void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/Client/SignIn.fxml"));
            signInPane = loader.load();
            signInController = loader.getController();
            signInController.setClientController(this);

            loader = new FXMLLoader(getClass().getResource("../UI/Client/MainMenu.fxml"));
            mainMenuPain = loader.load();
            mainMenuController = loader.getController();
            mainMenuController.setClientController(this);

            loader = new FXMLLoader(getClass().getResource("../UI/Client/SignUp.fxml"));
            signUpPane = loader.load();
            signUpController = loader.getController();
            signUpController.setClientController(this);
            signUpController.setSignInController(signInController);

            loader = new FXMLLoader(getClass().getResource("../UI/Client/Options.fxml"));
            optionsPane = loader.load();
            options = loader.getController();
            options.setClientController(this);
            Scene optionsScene = new Scene(optionsPane);

            loader = new FXMLLoader(getClass().getResource("../UI/Client/GameLobby.fxml"));
            lobbyPane = loader.load();
            gameLobby = loader.getController();
            gameLobby.setClientController(this);
            Scene lobbyScene = new Scene(lobbyPane);

            loader = new FXMLLoader(getClass().getResource("../UI/Client/GameHistory.fxml"));
            gameHistoryPane = loader.load();
            gameHistoryController = loader.getController();
            gameHistoryController.setClientController(this);
            Scene historyScene = new Scene(gameHistoryPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setUpClientToUI() {
        try {
            Scene scene = new Scene(signInPane);
            stage.setScene(scene);
            stage.show();
            readMessageBus = new ReadMessageBus(this);
            readMessageBus.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        stage.setOnCloseRequest(windowEvent -> {
            Packet packet = new Packet(Packet.SIGN_OUT, accountClient.getUserInformation(), "SIGN-OUT");
            if(accountClient != null) {
                accountClient.addRequestToServer(packet);
            }
            if(gameClient != null) {
                gameClient.addRequestToServer(packet);
            }
            this.stop();
            stage.close();
            Platform.exit();
            System.exit(0);
        });
        accountClient.execute();
    }

    public void stop() {
        System.out.println("Stopping client controller");
        // stop read message bus
        readMessageBus.stop();
        // stop account client
        accountClient.stop();
        //stop game client
        gameClient.stop();

    }

    public Client getAccountClient() {
        return accountClient;
    }

    public void setAccountClient(Client client) {
        this.accountClient = client;
    }

    public Packet getNextResponse() {
        if (!responses.isEmpty()) {
            return responses.pop();
        }

        return null;
    }

    public void addResponse(Packet packet) {
        responses.add(packet);
    }

    public Pane getSignInPane() {
        return signInPane;
    }

    public Pane getSignUpPane() {
        return signUpPane;
    }

    public Pane getMainMenuPain() {
        return mainMenuPain;
    }

    public Pane getOptionsPane() {
        return optionsPane;
    }

    public SignInController getSignInController() {
        return signInController;
    }

    public SignUpController getSignUpController() {
        return signUpController;
    }

    public Options getOptions() {
        return options;
    }

    public Client getGameClient() {
        return gameClient;
    }

    public void setGameClient(Client client) {
        this.gameClient = client;
    }

    public Pane getLobbyPane() {
        return lobbyPane;
    }

    public GameLobbyController getGameLobby() {
        return gameLobby;
    }

    public GameHistoryController getGameHistoryController() {
        return gameHistoryController;
    }

    public Pane getGameHistoryPane() {
        return gameHistoryPane;
    }
}
