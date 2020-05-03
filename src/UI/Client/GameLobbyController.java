package UI.Client;

import Client.ClientController;
import Models.Game;
import ObserverPatterns.LobbyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class GameLobbyController implements Initializable, LobbyListener {
    @FXML
    private TableView<Game> activeGames;
    @FXML
    private TableColumn<Game, String> player1Column, player2Column, statusColumn;
    @FXML
    private TableColumn<Game, Button> optionsColumn;
    @FXML
    private Button updateTableButton, newGameButton, newGameAgainstComputerButton;
    @FXML
    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadGames();
    }

    private void initializeTable() {
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        optionsColumn.setCellValueFactory(new PropertyValueFactory<>("options"));
    }

    // import an ObservableList of all active games from server
    private void loadGames(/* List of active games */) {
        ObservableList<Game> games = FXCollections.observableArrayList();

        /*add game information
        for (int n = 0; ) {
            games.add(gameof.getPlayer1Info().getUserName(), listofgame.getPlayer2Info().getUserName(), listofgame.getGameStatus(), getButton(game));
        }
        */

        activeGames.setItems(games);
    }

    private Button getButton(Game game) {
        Button button = new Button();
        button.setPrefWidth(800);
        if (game.getGameStatus() == "Ongoing") {
            button.setText("Spectate");
            button.setId("spectateButton");
            button.setOnAction(click -> spectateButtonClicked());
        } else {
            button.setText("Join Game");
            button.setId("joinGameButton");
            button.setOnAction(click -> joinGameButtonClicked());
        }

        return button;
    }

    public void spectateButtonClicked() {
    }

    public void joinGameButtonClicked() {

    }

    public void onPlayAgainstComputerButtonClicked(ActionEvent event) {
    }

    public void onCreateGameButtonClicked() {
    }

    public void updateTableButtonClicked(ActionEvent actionEvent) {
        loadGames();
    }

    @Override
    public void newGame(String message) {

    }

    @Override
    public void updateUIWithNewGame(String gameName) {

    }

    @Override
    public void getListOfGames(HashSet<String> listOfGames) {

    }

    @Override
    public void getListOfOnlinePlayers(HashSet<String> listOfOnlinePlayers) {

    }
}
