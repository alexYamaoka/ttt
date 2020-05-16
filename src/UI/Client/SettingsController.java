package UI.Client;

import Client.ClientController;
import ObserverPatterns.UpdateUserinformationListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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
        String username = this.username.getText();
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String oldPassword = this.password.getText();
        String newPassword = this.newPasswordField.getText();
        String confirmPassword = this.confirmNewPasswordField.getText();

        if (!username.isBlank() && !username.equals(this.username.getPromptText())) {
            username = this.username.getText();
            user.add(username);
        } else {
            username = clientController.getAccountClient().getUserInformation().getUsername();
            user.add(username);
        }


        if (!firstName.isBlank() && !firstName.equals(this.firstName.getPromptText())) {
            firstName = this.firstName.getText();
            user.add(firstName);
        } else {
            firstName = clientController.getAccountClient().getUserInformation().getFirstName();
            user.add(firstName);
        }

        System.out.println("first name: " + firstName);

        if (!lastName.isBlank() && !lastName.equals(this.lastName.getPromptText())) {
            lastName = this.lastName.getText();
            user.add(lastName);
        } else {
            lastName = clientController.getAccountClient().getUserInformation().getLastName();
            user.add(lastName);
        }

        System.out.println("last name: " + lastName);
        user.add(clientController.getAccountClient().getUserInformation().getId());


        // keep old password if new password is blank or the same
        if (newPassword.isBlank() && newPassword.equals(newPasswordField.getText())) {
            user.add(clientController.getAccountClient().getUserInformation().getPassword());
            System.out.println("keeping old password");
            String data = String.join(" ", user);
            System.out.println(user);
            Packet packet = new Packet(Packet.UPDATE_USER, clientController.getAccountClient().getUserInformation(), data);
            clientController.getAccountClient().addRequestToServer(packet);
        } else {
            if (newPassword.equals(confirmPassword) && !newPassword.isBlank() && !confirmPassword.isBlank()) {
                user.add(confirmPassword);
                System.out.println("updating password");
                String data = String.join(" ", user);
                Packet packet = new Packet(Packet.UPDATE_USER, clientController.getAccountClient().getUserInformation(), data);
                clientController.getAccountClient().addRequestToServer(packet);
            } else {
                //confirmNewPasswordField.setStyle("-fx-border-color: red;");
                confirmNewPasswordErrorLabel.setText("New passwords do not match");
            }
        }

        // send out packet


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
                    confirmNewPasswordErrorLabel.setTextFill(Color.LIMEGREEN);
                    confirmNewPasswordErrorLabel.setText("Information Has Been Updated!");
                } else {
                    confirmNewPasswordErrorLabel.setTextFill(Color.RED);
                    confirmNewPasswordErrorLabel.setText("Username has already been taken!");
                }
            }
        });
    }

    public void MainMenu(ActionEvent event) {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
            root = clientController.getMainMenuPain();
        }
        stage.setScene(root.getScene());
        stage.show();
    }
}
