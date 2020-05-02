package UI.Client;

import ObserverPatterns.LobbyListener;
import Shared.Packet;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Client.ClientController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameLobby implements Initializable, LobbyListener {


    public ListView LobbyGameListview;

    @FXML
    private Button JoinGameButton;

    @FXML
    private Button SpectateGameButton;
    @FXML
    private Button NewGameButton;
    private ClientController clientController;
    @FXML
    private Button Quit;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onJoinGameButtonClicked(ActionEvent event){
        if (event.getSource() == JoinGameButton) {

        }
    }

    public void onSpectateGameButtonClicked(ActionEvent event){
        if (event.getSource() == SpectateGameButton) {

        }
    }
       
    public void onPlayButtonClicked(ActionEvent event) {
        if (event.getSource() == NewGameButton) {
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), "test");

            System.out.println("New Game Created " + " Created by " + clientController.getAccountClient().getUserInformation().getFirstName() );


            clientController.getGameClient().addRequestToServer(packet);
        }

    }

    public void onQuitClicked(ActionEvent event){

    }

    @Override
    public void newGame(String message) {
        if(message.equalsIgnoreCase("SUCCESS")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage stage = null;
                    Parent root = null;

                    stage = (Stage) NewGameButton.getScene().getWindow();
                    root = clientController.getGameBoardPane();
                    stage.setScene(root.getScene());
                    stage.show();
                }
            });
        }
    }
}
