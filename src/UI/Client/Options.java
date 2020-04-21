package UI.Client;

import Client.ClientController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;

public class Options {

    private AnchorPane Ach_pane3;
    private AnchorPane Ach_Pane1;
    private Button btn_UserDetails;
    private Button btn_ChangePassword;


    private AnchorPane Ach_Pane2;
    private Button btn_button;
    private SplitPane splitPane; //BackGround Split Pane
    private AnchorPane split1; //BackGround left SplitPane  User detail and change password are on it
    private AnchorPane split2; // BackGround right split pane. one i want to update visable
    private AnchorPane Pane2; // User detail pane
    private AnchorPane Pane1; // Change Password pane
    private Button btn2;
    private Button btn_MainMenu;

    private ClientController controller;

    public void UserDetailButton(ActionEvent event){
        Pane2.setVisible(false);
        Pane1.managedProperty().bind(Pane1.visibleProperty());
        Pane1.setVisible(true);

    }

    public void ChangePassword(ActionEvent event){
        Pane1.setVisible(false);
        Pane2.managedProperty().bind(Pane2.visibleProperty());
        Pane2.setVisible(true);
    }

    public void MainMenu(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btn_MainMenu) {
            stage = (Stage) btn_MainMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setClientController(ClientController controller) {
        this.controller = controller;
    }



}
