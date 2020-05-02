package UI.Client;

import Client.ClientController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameLobby implements Initializable {

    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
