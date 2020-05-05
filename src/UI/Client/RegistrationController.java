package UI.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    public Label lbl_Error;

    public Button btn_Login;
    @FXML
    private TextField firstName, lastName, username;
    @FXML
    private PasswordField password, confirmPassword;
    @FXML
    private Button register;
    private String first_Name, last_Name, user_name, pass_word, confirm_Password;

    public boolean checkPasswords(String pass, String confirm) {
        if (!pass.equals(confirm)) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Passwords Do not Match");
            return false;
        } else return true;
    }


    public boolean checkField(String first, String last, String user, String pass, String confirm) {
        if (first.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter FirstName");
            return false;
        } else if (last.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter LastName");
            return false;
        } else if (user.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter UserName");
            return false;
        }
        if (pass.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter Password");
            return false;
        } else if (confirm.isBlank()) {
            lbl_Error.setTextFill(Color.RED);
            lbl_Error.setText("Enter Confirmation Password");
            return false;
        } else return true;
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
        if (checkField(first_Name, last_Name, user_name, pass_word, confirm_Password) && checkPasswords(pass_word, confirm_Password)) {
            if (event.getSource() == register) {
                stage = (Stage) register.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("Client/SignIn.fxml"));
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void BackClicked(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        btn_Login = (Button) event.getSource();
        if (event.getSource() == btn_Login) {
            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Client/SignIn.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void clickedLastNam(ActionEvent actionEvent) {
    }

    public void clickedUsernam(ActionEvent actionEvent) {
    }

    public void clickedPassword(ActionEvent actionEvent) {
    }

    public void clickedConfirmPassword(ActionEvent actionEvent) {
    }


}
