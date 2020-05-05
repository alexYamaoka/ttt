package UI.Client;

import Client.Client;
import Client.ClientController;
import DataBase.sql.DatabaseManager;
import ObserverPatterns.SignInResultListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable, SignInResultListener
{
    PreparedStatement pr = null;
    ResultSet rs = null;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txtF_Username;
    @FXML
    private PasswordField txtF_Password;
    @FXML
    private Button btn_LogIn, btn_SignUp;
    @FXML
    private Label usernameError, passwordError, logInError;
    @FXML
    private BorderPane parentContainerSignIn;
    @FXML
    private AnchorPane middleAnchorPane;
    @FXML
    private StackPane signInPane;

    private ClientController controller;

    @FXML
    public void onEnterKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String username = txtF_Username.getText().trim();
            String password = txtF_Password.getText().trim();
            signInUser(username, password);
        }
    }


    @FXML
    public void signIn(ActionEvent event) throws SQLException {
        String username = txtF_Username.getText();
        String password = txtF_Password.getText();
        signInUser(username, password);
    }


    private void signInUser(String username, String password)
    {
        if (!checkField(username, password)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // notify user that login failed
                }
            });
        } else {
            Packet packet = new Packet(Packet.SIGN_IN, controller.getAccountClient().getUserInformation(), username + " " + password);
            controller.getAccountClient().addRequestToServer(packet);

        }
    }


    public boolean checkField(String username, String password) {
        boolean value_entered = true;
        if (username.isBlank()) {
            txtF_Username.setStyle("-fx-border-color: red;");
            usernameError.setText("Enter an username");
            value_entered = false;
        }
        else{
            usernameError.setText("");
        }
        if (password.isBlank()) {
            txtF_Password.setStyle("-fx-border-color: red;");
            passwordError.setText("Enter a password");
            value_entered = false;
        }
        else{
            passwordError.setText("");
        }

        return value_entered;
    }


    @FXML
    public void signUp(ActionEvent event) throws IOException {
        // The new pane that is being added to middleAnchorPane
        Parent root = controller.getSignUpPane();
        Scene scene = btn_SignUp.getScene();
        Parent root1 = signInPane;
        root.translateXProperty().set(scene.getWidth() * -0.5);
        // Add second scene. Now both first and second scene is present
        middleAnchorPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue);
        KeyValue keyValue1 = new KeyValue(root1.translateXProperty(), 300, Interpolator.EASE_IN);
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.3), keyValue1);
        timeline.getKeyFrames().add(keyFrame);
        timeline.getKeyFrames().add(keyFrame1);
        timeline.setOnFinished(event1 -> {
            middleAnchorPane.getChildren().remove(signInPane);
        });
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Sign In initialized");
    }

    public ClientController getClientController() {
        return this.controller;
    }

    public void setClientController(ClientController controller) {
        this.controller = controller;
    }

    public AnchorPane getMiddleAnchorPane() {
        return middleAnchorPane;
    }

    public StackPane getSignInPane() {
        return signInPane;
    }

    @Override
    public void updateSignInResult(String message)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run() {
                if (!message.equalsIgnoreCase("FAIL")) {
                    String[] str = message.trim().split("\\s+");
                    String id = str[0];
                    String firstName = str[1];
                    String lastName = str[2];
                    String username = str[3];
                    String email = str[4];
                    String password = str[5];
                    UserInformation userInformation = new UserInformation(firstName, lastName, username, email, password);
                    userInformation.setId(id);
                    controller.getAccountClient().setUserInformation(userInformation);
                    Stage stage = (Stage) btn_LogIn.getScene().getWindow();
                    Parent root = controller.getMainMenuPain();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    controller.getOptions().updateInfo();

                    Client client = new Client("localhost", 8080, userInformation, controller);
                    controller.setGameClient(client);
                    client.execute();


                    // requests for the list of available games on the server to display it into the listview for user's UI
                    Packet requestingListOfGames = new Packet(Packet.GET_GAMES, userInformation, "requesting list of games");
                    client.addRequestToServer(requestingListOfGames);


                    // requests for the list of online players
                    Packet requestingListOfOnlinePlayers = new Packet(Packet.GET_ONLINE_PLAYERS, client.getUserInformation(), client.getUserInformation());
                    client.addRequestToServer(requestingListOfOnlinePlayers);
                }
                else{
                    logInError.setText("The username or password provided is incorrect.");
                }
            }
        });
    }
}
