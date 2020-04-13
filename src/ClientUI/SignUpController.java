package ClientUI;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpController {
    @FXML
    StackPane parentContainer;
    @FXML
    AnchorPane anchorPane;
    @FXML
    private TextField txtF_FirstName, txtF_LastName, txtF_Username;
    @FXML
    private PasswordField txtF_Password, txtF_ConfirmPassword;
    @FXML
    private Button btn_SignIn, btn_SignUp;
    @FXML
    private Label firstNameError, lastNameError, usernameError, passwordError, confirmPasswordError;

    public boolean checkPasswords(String password, String confirmPassword){
        if (!password.equals(confirmPassword)){
            usernameError.setTextFill(Color.RED);
            passwordError.setText("Passwords do not match");
            return false;
        }
        else
            return true;
    }
    public boolean checkField(String firstName,String lastName,String username,String password,String confirmPassword){
        boolean value_entered = true;
        if (firstName.isBlank()) {
            txtF_FirstName.setStyle("-fx-border-color: red;");
            firstNameError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_FirstName.setStyle("");
            firstNameError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (lastName.isBlank()) {
            txtF_LastName.setStyle("-fx-border-color: red;");
            lastNameError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_LastName.setStyle("");
            lastNameError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (username.isBlank()) {
            txtF_Username.setStyle("-fx-border-color: red;");
            usernameError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_Username.setStyle("");
            usernameError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (password.isBlank()) {
            txtF_Password.setStyle("-fx-border-color: red;");
            passwordError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_Password.setStyle("");
            passwordError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }
        if (confirmPassword.isBlank()) {
            txtF_ConfirmPassword.setStyle("-fx-border-color: red;");
            confirmPasswordError.setStyle("-fx-text-fill: red;");
            value_entered = false;
        } else{
            txtF_ConfirmPassword.setStyle("");
            confirmPasswordError.setStyle("-fx-text-fill: white;");
            value_entered = false;
        }

        return value_entered;
    }

    public void signUp(ActionEvent event) throws IOException {
        String first_Name = txtF_FirstName.getText();
        String last_Name = txtF_LastName.getText();
        String username = txtF_Username.getText();
        String password = txtF_Password.getText();
        String confirm_Password = txtF_ConfirmPassword.getText();
        if(checkField(first_Name, last_Name, username, password, confirm_Password) && checkPasswords(password, confirm_Password)){
            Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/SignIn.fxml"));
            Scene scene = btn_SignIn.getScene();

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
    }

    public void signIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/SignIn.fxml"));
        Scene scene = btn_SignIn.getScene();

        root.translateXProperty().set(scene.getWidth() * -1);
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
}
