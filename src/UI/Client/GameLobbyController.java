package UI.Client;

import Client.ClientController;
import Models.Game;
import ObserverPatterns.LobbyListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
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
        // Send message to server to get packet of games online
    }

    private void initializeTable() {
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1Info"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2Info"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("gameStatus"));
//        optionsColumn.setCellValueFactory(new PropertyValueFactory<>("options"));
    }

    // import an ObservableList of all active games from server
    private void loadGames(HashSet<Game> listOfGames) {
        activeGames.getItems().addAll(listOfGames);
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

    public void onCreateGameButtonClicked(ActionEvent event) {
        if (event.getSource() == newGameButton) {
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), "NEW-GAME");
            clientController.getGameClient().addRequestToServer(packet);
            System.out.println("New Game Created " + " Created by " + clientController.getAccountClient().getUserInformation().getFirstName());
        }

    }

    public void updateTableButtonClicked(ActionEvent actionEvent) {
        // send packet to get list of games
    }

    @Override
    public void newGame(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void updateUIWithNewGame(String gameName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void getListOfGames(HashSet<Game> listOfGames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadGames(listOfGames);
            }
        });
    }

    @Override
    public void getListOfOnlinePlayers(HashSet<UserInformation> listOfOnlinePlayers) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
