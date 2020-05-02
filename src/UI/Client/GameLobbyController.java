package UI.Client;

import Models.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Client.ClientController;
import javafx.fxml.Initializable;

import javax.swing.text.TabableView;
import java.net.URL;
import java.util.ResourceBundle;

public class GameLobbyController implements Initializable {
    @FXML
    private TableView<Game> activeGames;
    @FXML
    private TableColumn<Game, String> player1Column, player2Column, statusColumn;
    @FXML
    private TableColumn<Game, Button> optionsColumn;
    @FXML
    private Button spectateButton, joinGameButton, NewGameButton;
    @FXML
    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player1Column.setCellValueFactory(new PropertyValueFactory<Game, String>("player1"));
        player2Column.setCellValueFactory(new PropertyValueFactory<Game, String>("player2"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("status"));
        optionsColumn.setCellValueFactory(new PropertyValueFactory<Game, Button>("options"));

        activeGames.setItems(getGames());
    }

    // import an ObservableList of all active games from server
    private ObservableList<Game> getGames() {
        ObservableList<Game> games = FXCollections.observableArrayList();

        /*add game information
        example:
        games.add(game.getPlayer1Info().getUserName(), game.getPlayer2Info().getUserName(), game.getGameStatus(), getButton(game));
        */

        return games;
    }

    private Object getButton(Game game) {
        if (game.getGameStatus() == "Ongoing")
            return spectateButton;
        else
            return joinGameButton;
    }

    public void onPlayButtonClicked(ActionEvent event) {

        if (event.getSource() == NewGameButton) {

        }

    }
}
