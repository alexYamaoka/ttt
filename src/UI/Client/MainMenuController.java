package UI.Client;

import Client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button playButton, watchMatchesButton, exitButton, optionsButton;



    private ClientController clientController;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onPlayButtonClicked(ActionEvent event) throws IOException {
//        Stage stage = null;
//        Parent root = null;
//        if (event.getSource() == playButton) {
//            stage = (Stage) playButton.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
//        }
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();


        System.out.println("Options Button Clicked!");
        System.out.println("Play Button Clicked!");
    }


    @FXML
    public void onWatchMatchesButtonClicked(ActionEvent event) {
        System.out.println("Watch Matches Button Clicked!");
    }


    @FXML
    public void onOptionsButtonClicked(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == optionsButton) {
            stage = (Stage) optionsButton.getScene().getWindow();
            root = clientController.getOptionsPane();
        }
        stage.setScene(root.getScene());
        stage.show();
    }


    @FXML
    public void onExitButtonClicked(ActionEvent event) {
        System.out.println("Exit Button Clicked!");
        //((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }



    public void setClientController(ClientController clientController)
    {
        this.clientController = clientController;
    }
}
