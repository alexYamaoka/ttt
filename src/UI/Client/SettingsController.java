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
    PasswordField password, currentPasswordField, newPasswordField, confirmNewPasswordField;
    @FXML
    private TextField firstName, lastName, username;
    @FXML
    private Label usernameErrorLabel, currentPasswordErrorLabel, newPasswordErrorLabel, confirmNewPasswordErrorLabel;

    private ClientController clientController;

    public void setClientController(ClientController controller) {
        this.clientController = controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if (clientController.getAccountClient().getUserInformation().getIsDeleted() == 1) {
                deactivateAccountButton.setText("Activate Account");
            } else {
                deactivateAccountButton.setText("Deactivate Account");
            }
        });
    }

    public void updateInfo() {
        UserInformation information = clientController.getAccountClient().getUserInformation();
        firstName.setPromptText(information.getFirstName());
        lastName.setPromptText(information.getLastName());
        username.setPromptText(information.getUsername());
//        btnOldpass.setPromptText(information.getPassword());
    }

    public void DeactivateAccount(ActionEvent event) {
        String id = clientController.getAccountClient().getUserInformation().getId();
        List<String> user = new ArrayList<>();
        user.add(id);
        String data = String.join(" ", user);
        if (clientController.getAccountClient().getUserInformation().getIsDeleted() == 1) {
            Packet packet = new Packet(Packet.ACTIVATE_ACCOUNT, clientController.getAccountClient().getUserInformation(), data);
            clientController.getAccountClient().addRequestToServer(packet);
        } else {
            Packet packet = new Packet(Packet.DELETE_ACCOUNT, clientController.getAccountClient().getUserInformation(), data);
            clientController.getAccountClient().addRequestToServer(packet);
        }
    }

    @Override
    public void deactivateAccount(String message) {
        Platform.runLater(() -> {
            if (message.equals("SUCCESS")) {
                deactivateAccountButton.setText("Activate Account");
                clientController.getAccountClient().getUserInformation().setIsDeleted(1);
                Stage stage = (Stage) deactivateAccountButton.getScene().getWindow();
                Parent root = clientController.getMainMenuPain();
                stage.setScene(root.getScene());
                stage.show();
            }
        });
    }

    @Override
    public void ActivateAccount(String message) {
        Platform.runLater(() -> {
            if (message.equals("SUCCESS")) {
                deactivateAccountButton.setText("Deactivate Account");
                clientController.getAccountClient().getUserInformation().setIsDeleted(0);
                Stage stage = (Stage) deactivateAccountButton.getScene().getWindow();
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
        user.add(firstName);
        user.add(lastName);
        user.add(username);
        user.add(id);
        user.add(confirmPassword);

        if (!this.username.getText().equals(this.username.getPromptText())) {
            username = this.username.getText();
        } else {
            username = this.username.getPromptText();
        }
        user.add(username);

        if (!this.firstName.getText().equals(this.firstName.getPromptText())) {
            firstName = this.firstName.getText();
        } else {
            firstName = this.firstName.getPromptText();
        }
        user.add(firstName);
        System.out.println("first name: " + firstName);

        if (!this.lastName.getText().equals(this.lastName.getPromptText())) {
            lastName = this.lastName.getText();
        } else {
            lastName = this.lastName.getPromptText();
        }
        user.add(lastName);
        System.out.println("last name: " + lastName);

        if (!this.password.getPromptText().equals(clientController.getAccountClient().getUserInformation().getPassword())) {

            oldPassword = this.password.getText();
        } else {
            oldPassword = clientController.getAccountClient().getUserInformation().getPassword();
        }
        user.add(oldPassword);
        System.out.println("old password: " + oldPassword);


        if (!this.newPasswordField.getText().equals(this.newPasswordField.getPromptText())) {
            newPassword = this.newPasswordField.getText();
        } else {
            newPassword = clientController.getAccountClient().getUserInformation().getPassword();
        }
        user.add(newPassword);
        System.out.println("new password: " + newPassword);

        if (!this.confirmChangePasswordButton.getText().equals(this.confirmNewPasswordField.getPromptText())) {
            confirmPassword = this.confirmNewPasswordField.getText();
        } else {
            confirmPassword = clientController.getAccountClient().getUserInformation().getPassword();
        }
        user.add(confirmPassword);
        System.out.println("confirm password: " + confirmPassword);

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

    @Override
    public void updateUserinformation(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!message.equalsIgnoreCase("FAIL")) {
                    String[] str = message.trim().split("\\s+");
                    System.out.println("Array to string " + Arrays.toString(str));
                    String id = str[0];
                    String firstName = str[1];
                    String lastName = str[2];
                    String username = str[3];
                    String email = str[4];
                    String password = str[5];
                    UserInformation userInformation = new UserInformation(firstName, lastName, username, email, password);
                    userInformation.setId(id);
                    clientController.getAccountClient().setUserInformation(userInformation);
                    updateInfo();
                    usernameErrorLabel.setTextFill(Color.LIMEGREEN);
                    usernameErrorLabel.setText("Information Has Been Updated!");
                } else {
                    usernameErrorLabel.setTextFill(Color.RED);
                    usernameErrorLabel.setText("Username has already been taken!");
                }
            }
        });
    }

    public void MainMenu(ActionEvent event) {

    }
}
