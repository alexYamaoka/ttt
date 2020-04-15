package Client;

import ClientUI.MainMenuController;
import ClientUI.SignInController;
import ClientUI.SignUpController;
import ClientUI.Options;

import Shared.UserInformation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class ClientController
{
    private Client client;
    private Stage stage;

    // Controllers to be initialized
    private SignInController signInController;
    private MainMenuController mainMenuController;
    private SignUpController signUpController;
    private Options options;

    // Scenes
    private Pane signInPane;
    private Pane signUpPane;
    private Pane mainMenuPain;
    private Pane optionsPane;

    private ReadMessageBus readMessageBus;


    public ClientController(Stage stage) {
        this.stage = stage;
        initialize();
        setUpClientToUI();
    }

    // initializes controllers
    private void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ClientUI/MainMenu.fxml"));
            mainMenuPain = loader.load();
            mainMenuController = loader.getController();
            mainMenuController.setClientController(this);

            loader = new FXMLLoader(getClass().getResource("../ClientUI/SignInUp.fxml"));
            signInPane = loader.load();
            signInController = loader.getController();
            signInController.setClientController(this);

            loader = new FXMLLoader(getClass().getResource("../ClientUI/SignUp.fxml"));
            signUpPane = loader.load();
            signUpController = loader.getController();
            signUpController.setClientController(this);

            loader = new FXMLLoader(getClass().getResource("../ClientUI/Options.fxml"));
            optionsPane = loader.load();
            options = loader.getController();
            options.setClientController(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setUpClientToUI()
    {
        try
        {
            Scene scene = new Scene(signInPane);
            stage.setScene(scene);
            stage.show();

            readMessageBus = new ReadMessageBus(this);
            readMessageBus.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Client getClient()
    {
        return client;
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
}
