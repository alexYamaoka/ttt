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

    }

    public void updateInfo() {
        Platform.runLater(() -> {
            if (clientController.getAccountClient().getUserInformation().getIsDeleted() == 1) {
                deactivateAccountButton.setText("Activate Account");
            } else {
                deactivateAccountButton.setText("Deactivate Account");
            }
        });
        UserInformation information = clientController.getAccountClient().getUserInformation();
        firstName.setPromptText(information.getFirstName());
        lastName.setPromptText(information.getLastName());
        username.setPromptText(information.getUsername());
        password.setPromptText(information.getPassword());
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
        String oldPassword = this.password.getPromptText();
        String newPassword = this.newPasswordField.getText();
        String confirmPassword = this.confirmNewPasswordField.getText();
//        user.add(firstName);
//        user.add(lastName);
//        user.add(username);
//        user.add(id);
//        user.add(confirmPassword);

        if (!username.equals(this.username.getPromptText())) {
            username = this.username.getText();
        } else {
            username = clientController.getAccountClient().getUserInformation().getUsername();
        }
        user.add(username);

        if (!firstName.equals(this.firstName.getPromptText())) {
            firstName = this.firstName.getText();
        } else {
            firstName = clientController.getAccountClient().getUserInformation().getFirstName();
        }
        user.add(firstName);
        System.out.println("first name: " + firstName);

        if (!lastName.equals(this.lastName.getPromptText())) {
            lastName = this.lastName.getText();
        } else {
            lastName = clientController.getAccountClient().getUserInformation().getLastName();
        }
        user.add(lastName);
        System.out.println("last name: " + lastName);

        user.add(clientController.getAccountClient().getUserInformation().getId());


        if (this.newPasswordField.getText().equals(this.newPasswordField.getPromptText()) &&
             this.confirmChangePasswordButton.getText().equals(this.confirmNewPasswordField.getPromptText()))
        {
            user.add(clientController.getAccountClient().getUserInformation().getPassword());
            System.out.println("keeping old password");
        }
        else
        {
            if (this.newPasswordField.getText().equals(this.confirmNewPasswordField.getText()))
            {
                user.add(this.confirmNewPasswordField.getText());
                System.out.println("updating password");

                String data = String.join(" ", user);
                System.out.println(user);
                Packet packet = new Packet(Packet.UPDATE_USER, clientController.getAccountClient().getUserInformation(), data);
                clientController.getAccountClient().addRequestToServer(packet);
            }
            else
            {
                confirmNewPasswordField.setStyle("-fx-border-color: red;");
                confirmNewPasswordErrorLabel.setText("New passwords do not match");
            }
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
