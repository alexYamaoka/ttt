package ClientUI;

import DataBase.sql.DatabaseManager;
import Shared.UserInformation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    // publishing sign in into
    @FXML
    StackPane parentContainer;
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

    //ArrayList<Sub> subs = new ArrayList<>();
    PreparedStatement pr = null;
    ResultSet rs = null;
    Socket userSocket;
    UserInformation user;

    public boolean checkField(String username,String password){
        boolean value_entered = true;
        if (username.isBlank()) {
            txtF_Username.setStyle("-fx-border-color: red;");
            usernameError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_Username.setStyle("");
            usernameError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (password.isBlank()){
            txtF_Password.setStyle("-fx-border-color: red;");
            passwordError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_Password.setStyle("");
            passwordError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }

        return value_entered;
    }

    public void signIn(ActionEvent event) throws SQLException {
        String username = txtF_Username.getText();
        String password = txtF_Password.getText();
        if (checkField(username, password)){
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

    public void signUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/SignUp.fxml"));
        Scene scene = btn_SignUp.getScene();

        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(anchorPane);
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

    }
}
