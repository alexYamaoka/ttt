package ClientUI;

import Client.ClientController;
import DataBase.sql.DatabaseManager;
import ObserverPatterns.SignInResultListener;
import Shared.Packet;
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
    private Label usernameError, passwordError;
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

            if (!checkField(username, password)) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // notify user that login failed
                    }
                });
            } else {
                // sign in user function
                // notify listener



                Packet packet = new Packet(Packet.SIGN_IN, controller.getClient().getUserInformation(), username + " " + password);
                controller.getClient().addRequestToServer(packet);

            }
        }
    }




    @FXML
    public void signIn(ActionEvent event) throws SQLException {
        String username = txtF_Username.getText();
        String password = txtF_Password.getText();

        if (checkField(username, password)) {
            try {
                String sql = "SELECT * FROM user WHERE username = ? and password = ?";
                pr = DatabaseManager.getInstance().myConn.prepareStatement(sql);
                pr.setString(1, username);
                pr.setString(2, password);
                rs = pr.executeQuery();
                if (!rs.next()) {
                    //System.out.println(rs.getString(1));
                } else {
                    MainMenuScene();
                    //userSocket = new Socket("localhost", 800);
                    //User newUser = new User(userSocket);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkField(String username, String password) {
        boolean value_entered = true;
        if (username.isBlank()) {
            txtF_Username.setStyle("-fx-border-color: red;");
            usernameError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else {
            txtF_Username.setStyle("");
            usernameError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (password.isBlank()) {
            txtF_Password.setStyle("-fx-border-color: red;");
            passwordError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else {
            txtF_Password.setStyle("");
            passwordError.setStyle("-fx-text-fill: white;");
            value_entered = false;
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

    public void MainMenuScene() throws IOException {
        Stage stage = null;
        Parent root = null;
        stage = (Stage) btn_LogIn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            public void run()
            {
                System.out.println(message);
            }
        });
    }
}
