package UI.Client;

import ObserverPatterns.LobbyListener;
import Shared.Packet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Client.ClientController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameLobby implements Initializable, LobbyListener {
    @FXML
    private Button JoinGameButton;

    @FXML
    private Button SpectateGameButton;
    @FXML
    private Button NewGameButton;
    private ClientController clientController;

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
            clientController.getGameClient().addRequestToServer(packet);
        }

    }

    @Override
    public void newGame(String message) {
        System.out.println(message);
    }
}
