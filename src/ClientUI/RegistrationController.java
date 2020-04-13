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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RegistrationController {
    public Label lbl_Error;

    public Button btn_Login;
    @FXML
    AnchorPane anchorPane;
    @FXML
    private TextField firstName, lastName, username;
    @FXML
    private PasswordField password, confirmPassword;
    @FXML
    private Button register;
    private String first_Name, last_Name, user_name, pass_word, confirm_Password;

    public boolean checkPasswords(String password,String confirmPassword){
        if (!password.equals(confirmPassword)){
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Passwords Do not Match");
            return false;
        }
        else
            return true;
    }
    public boolean checkField(String first,String last,String user,String pass,String confirm){
        boolean value_entered = true;
        if(first.isBlank()){
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter FirstName");
            value_entered = false;
        }if (last.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter LastName");
            value_entered = false;
        }if (user.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter UserName");
            value_entered = false;
        }if (pass.isBlank()){
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter Password");
            value_entered = false;
        }
        if (confirm.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter Confirmation Password");
            value_entered = false;
        }
        return value_entered;
    }


    public void register(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        register = (Button) event.getTarget();
        first_Name = firstName.getText();
        last_Name = lastName.getText();
        user_name = username.getText();
        pass_word = password.getText();
        confirm_Password = confirmPassword.getText();
        if(checkField(first_Name, last_Name, user_name, pass_word, confirm_Password)
                && checkPasswords(pass_word, confirm_Password)){
            if(event.getSource() == register ){
                stage = (Stage) register.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void BackClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene scene = btn_Login.getScene();

        root.translateXProperty().set(scene.getWidth() * -1);

        StackPane parentContainer = (StackPane) scene.getRoot();
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
