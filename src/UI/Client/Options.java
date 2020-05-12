package UI.Client;

import Client.ClientController;
import ObserverPatterns.UpdateUserinformationListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Options implements Initializable, UpdateUserinformationListener {
    @FXML
    private AnchorPane Ach_pane3;
    @FXML
    private AnchorPane Ach_Pane1;
    @FXML
    private Button btn_UserDetails;
    @FXML
    private Button btn_ChangePassword;
    @FXML
    private AnchorPane Ach_Pane2;
    @FXML
    private Button btn_button;
    @FXML
    private SplitPane splitPane; //BackGround Split Pane
    @FXML
    private AnchorPane split1; //BackGround left SplitPane  User detail and change password are on it
    @FXML
    private AnchorPane split2; // BackGround right split pane. one i want to update visable
    @FXML
    private AnchorPane Pane2; // User detail pane
    @FXML
    private AnchorPane Pane1; // Change Password pane
    @FXML
    private Button btn2;
    @FXML
    private Button btn_MainMenu;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField userName;
    @FXML
    private Label updateError;
    @FXML
    private TextField oldPassword, newPassword, confirmPassword;

    private ClientController controller;


    public void UserDetailButton(ActionEvent event) {
        Pane2.setVisible(false);
        Pane1.managedProperty().bind(Pane1.visibleProperty());
        Pane1.setVisible(true);

    }

    public void ChangePassword(ActionEvent event) {
        Pane1.setVisible(false);
        Pane2.managedProperty().bind(Pane2.visibleProperty());
        Pane2.setVisible(true);
    }

    public void MainMenu(ActionEvent event) throws IOException {
        Stage stage = null;
        Pane root = null;

        if (event.getSource() == btn_MainMenu) {
            stage = (Stage) btn_MainMenu.getScene().getWindow();
            root = controller.getMainMenuPain();
        }
        stage.setScene(root.getScene());
        stage.show();

    }

    public void setClientController(ClientController controller) {
        this.controller = controller;
    }

    public void updateInfo() {
        UserInformation information = controller.getAccountClient().getUserInformation();
        firstName.setPromptText(information.getFirstName());
        lastName.setPromptText(information.getLastName());
        userName.setPromptText(information.getUserName());
    }


    public void userDetailsSaved(ActionEvent event) {

        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String username = this.userName.getText();

        Properties properties = new Properties();
        properties.setProperty("firstName", firstName);
        properties.setProperty("lastName", lastName);
        properties.setProperty("username", username);

        Packet packet = new Packet(Packet.UPDATE_USER, controller.getAccountClient().getUserInformation(), properties);
        controller.getAccountClient().addRequestToServer(packet);
    }

    public void passwordSaved(ActionEvent event) {
        String oldPassword = this.oldPassword.getText();
        String newPassword = this.newPassword.getText();
        String confirmPassword = this.confirmPassword.getText();

        if (oldPassword.equals(controller.getAccountClient().getUserInformation().getPassword())) {
            if (newPassword.equals(confirmPassword)) {
                Packet packet = new Packet(Packet.UPDATE_USER, controller.getAccountClient().getUserInformation(), newPassword);
                controller.getAccountClient().addRequestToServer(packet);
            } else {
                updateError.setTextFill(Color.RED);
                updateError.setText("New passwords do not match!");
            }
        } else {
            updateError.setTextFill(Color.RED);
            updateError.setText("You did not enter the correct old password!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void updateUserinformation(String message) {
        Platform.runLater(new Runnable() {
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
                    controller.getOptions().updateInfo();
                    updateError.setTextFill(Color.LIMEGREEN);
                    updateError.setText("Information Has Been Updated!");
                } else {
                    updateError.setTextFill(Color.RED);
                    updateError.setText("Username has already been taken!");
                }
            }
        });
    }
}
