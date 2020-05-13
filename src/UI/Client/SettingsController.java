package UI.Client;

import Client.ClientController;
import Client.Main;
import ObserverPatterns.UpdateUserinformationListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class SettingsController implements Initializable, UpdateUserinformationListener {

    @FXML
    Button confirmChangeButton, promptChangePasswordButton, confirmChangePasswordButton, backButton, deactivateAccountButton;
    @FXML
    private TextField firstName, lastName, username;
    @FXML
    PasswordField password, currentPasswordField, newPasswordField, confirmNewPasswordField;
    @FXML
    private Label usernameErrorLabel, currentPasswordErrorLabel, newPasswordErrorLabel, confirmNewPasswordErrorLabel;

    private ClientController clientController;

    public void setClientController(ClientController controller) {
        this.clientController = controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            if(clientController.getAccountClient().getUserInformation().getIsDeleted() == 1){
                deactivateAccountButton.setText("Activate Account");
            }else{
                deactivateAccountButton.setText("Deactivate Account");
            }
        });
    }

    public void updateInfo() {
        UserInformation information = clientController.getAccountClient().getUserInformation();
        firstName.setPromptText(information.getFirstName());
        lastName.setPromptText(information.getLastName());
        username.setPromptText(information.getUsername());
        password.setText(information.getPassword());
    }

    //needs a method that sets the confirmChangeButton visible

    @Override
    public void updateUserinformation(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!message.equalsIgnoreCase("FAIL")) {
                    String[] str = message.trim().split("\\s+");
                    System.out.println( "Array to string " +Arrays.toString(str));
                    String id = str[0];
                    String firstName = str[1];
                    String lastName = str[2];
                    String username = str[3];
                    String email = str[4];
                    String password = str[5];
                    UserInformation userInformation = new UserInformation(firstName, lastName, username, email, password);
                    userInformation.setId(id);
                    clientController.getAccountClient().setUserInformation(userInformation);
                    clientController.getOptions().updateInfo();
                    usernameErrorLabel.setTextFill(Color.LIMEGREEN);
                    usernameErrorLabel.setText("Information Has Been Updated!");
                } else {
                    usernameErrorLabel.setTextFill(Color.RED);
                    usernameErrorLabel.setText("Username has already been taken!");
                }
            }
        });
    }

    public void confirmChangeButtonClicked() {
        UserInformation information = clientController.getAccountClient().getUserInformation();
        firstName.setPromptText(information.getFirstName());
        lastName.setPromptText(information.getLastName());
        username.setPromptText(information.getUsername());
    }

    public void promptChangePasswordButtonClicked(ActionEvent event){
        currentPasswordField.setVisible(true);
        newPasswordField.setVisible(true);
        confirmNewPasswordField.setVisible(true);
    }

    public boolean checkPasswordField(String currentPassword, String newPassword, String confirmNewPassword) {
        boolean value_entered = true;
        if (currentPassword.isBlank()) {
            currentPasswordField.setStyle("-fx-border-color: red;");
            currentPasswordErrorLabel.setText("Enter an username");
            value_entered = false;
        } else {
            currentPasswordErrorLabel.setText("");
        }
        if (newPassword.isBlank()) {
            newPasswordField.setStyle("-fx-border-color: red;");
            newPasswordErrorLabel.setText("Enter a password");
            value_entered = false;
        } else {
            newPasswordField.setText("");
        }
        if (confirmNewPassword.isBlank()) {
            confirmNewPasswordField.setStyle("-fx-border-color: red;");
            confirmNewPasswordErrorLabel.setText("Confirm your new password");
            value_entered = false;
        } else {
            confirmNewPasswordErrorLabel.setText("");
        }

        return value_entered;
    }

    public void confirmChangePasswordButtonClicked(ActionEvent event){
    }

    public void backButtonClicked(ActionEvent event) {
        Stage stage = null;
        Parent root = null;

        if(event.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
            root = clientController.getMainMenuPain();
        }
        stage.setScene(root.getScene());
        stage.show();
    }

    public void deactivateAccountButtonClicked(ActionEvent event){
        String id = clientController.getAccountClient().getUserInformation().getId();
        List<String> user = new ArrayList<>();
        user.add(id);
        String data = String.join(" ", user);
        if(clientController.getAccountClient().getUserInformation().getIsDeleted() == 1){
            Packet packet = new Packet(Packet.ACTIVATE_ACCOUNT,clientController.getAccountClient().getUserInformation(), data);
            clientController.getAccountClient().addRequestToServer(packet);
        }else {
            Packet packet = new Packet(Packet.DELETE_ACCOUNT, clientController.getAccountClient().getUserInformation(), data);
            clientController.getAccountClient().addRequestToServer(packet);
        }
    }
    @Override
    public void deactivateAccount(String message) {
        Platform.runLater(()->{
            if(message.equals("SUCCESS")) {
                deactivateAccountButton.setText("Activate Account");
                clientController.getAccountClient().getUserInformation().setIsDeleted(1);
                Stage stage = (Stage)deactivateAccountButton.getScene().getWindow();
                Parent root = clientController.getMainMenuPain();
                stage.setScene(root.getScene());
                stage.show();
            }
        });
    }
    @Override
    public void ActivateAccount(String message){
        Platform.runLater(()->{
            if(message.equals("SUCCESS")){
                deactivateAccountButton.setText("Deactivate Account");
                clientController.getAccountClient().getUserInformation().setIsDeleted(0);
                Stage stage = (Stage)deactivateAccountButton.getScene().getWindow();
                Parent root = clientController.getMainMenuPain();
                stage.setScene(root.getScene());
                stage.show();
            }


        });
    }

    public void userDetailsSaved(ActionEvent event) {
        List<String> user = new ArrayList<>();
        String id = clientController.getAccountClient().getUserInformation().getId();
        String username = this.username.getPromptText();
        String firstName = this.firstName.getPromptText();
        String lastName = this.lastName.getPromptText();
        String oldPassword = this.currentPasswordField.getText();
        String newPassword = this.newPasswordField.getText();
        String confirmPassword = this.confirmNewPasswordField.getText();
        user.add(firstName.toString());
        user.add(lastName.toString());
        user.add(username.toString());
        user.add(id.toString());
        user.add(confirmPassword.toString());
/*
            if(!this.userName.getText().equals(this.userName.getPromptText())){
                username = this.userName.getText();
            }else{
                username = this.userName.getPromptText();
            }
                user.add(username);

            if(!this.firstName.getText().equals(this.firstName.getPromptText())){
                    firstName = this.firstName.getText();
                }else{
                    firstName = this.firstName.getPromptText();
                }
                    user.add(firstName);

                if(!this.lastName.getText().equals(this.lastName.getPromptText())){
                        lastName = this.lastName.getText();
                    }else{
                        lastName = this.lastName.getPromptText();
                    }
                        user.add(lastName);

                    if(!this.oldPassword.getText().equals(this.oldPassword.getPromptText())){
                            oldPassword = this.oldPassword.getText();
                        }else{
                            oldPassword = this.oldPassword.getPromptText();
                        }
                            user.add(oldPassword);

                        if(!this.newPassword.getText().equals(this.newPassword.getPromptText())){
                                newPassword = this.newPassword.getText();
                        }else{
                                newPassword = this.newPassword.getPromptText();
                        }
                                user.add(newPassword);
                            if(!this.confirmPassword.getText().equals(this.confirmPassword.getPromptText())){
                                    confirmPassword = this.confirmPassword.getText();
                            }else{
                                    confirmPassword = this.confirmPassword.getPromptText();
                            }
                                    user.add(confirmPassword);

*/
        String data = String.join(" ", user);
        System.out.println(user);
        if (oldPassword.equalsIgnoreCase(clientController.getAccountClient().getUserInformation().getPassword())) {
            if (newPassword.equals(confirmPassword)) {

                Packet packet = new Packet(Packet.UPDATE_USER, clientController.getAccountClient().getUserInformation(), data);
                clientController.getAccountClient().addRequestToServer(packet);
            } else {
                confirmNewPasswordField.setStyle("-fx-border-color: red;");
                confirmNewPasswordErrorLabel.setText("New passwords do not match");
            }
        } else {
            currentPasswordField.setStyle("-fx-border-color: red;");
            currentPasswordErrorLabel.setText("Incorrect password");
        }


    }


}
