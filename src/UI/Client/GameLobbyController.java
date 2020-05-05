package UI.Client;

import Client.ClientController;
import Models.Game;
import ObserverPatterns.LobbyListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GameLobbyController implements Initializable, LobbyListener {
    @FXML
    private TableView<Game> activeGames;
    @FXML
    private TableColumn<Game, String> player1Column, player2Column, statusColumn;
    @FXML
    private TableColumn<Game, Void> actionColumn;
    @FXML
    private Button updateTableButton, newGameButton, newGameAgainstComputerButton;
    @FXML
    private ClientController clientController;

    private ObservableList<Game> data = FXCollections.observableArrayList();

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        // Send message to server to get packet of games online
    }

    private void initializeTable() {
        activeGames.setItems(data);
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1Username"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2Username"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("gameStatus"));
        addButtonsToTable();
    }

    // import an ObservableList of all active games from server
    private void loadGames(HashSet<Game> listOfGames) {
        System.out.println("Load games called!");
        activeGames.getItems().addAll(listOfGames);
    }

    public void onPlayAgainstComputerButtonClicked(ActionEvent event) {
    }

    public void onCreateGameButtonClicked(ActionEvent event) {
        if (event.getSource() == newGameButton) {
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), "NEW-GAME");
            clientController.getGameClient().addRequestToServer(packet);
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
                if (message.equalsIgnoreCase("SUCCESS")) {
                    System.out.println("New game created Successfully");
                }
            }
        });
    }

    @Override
    public void getListOfGames(HashSet<Game> listOfGames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                activeGames.getItems().clear();
                data.clear();
                data.addAll(listOfGames);
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

    private void addButtonsToTable() {
        Callback<TableColumn<Game, Void>, TableCell<Game, Void>> cellFactory = new Callback<TableColumn<Game, Void>, TableCell<Game, Void>>() {
            @Override
            public TableCell<Game, Void> call(final TableColumn<Game, Void> param) {
                final TableCell<Game, Void> cell = new TableCell<>() {
                    private final Button joinButton = new Button("Join");
                    {
                        joinButton.setOnAction(event-> {
                            Game game = getTableView().getItems().get(getIndex());
                            if(!game.getPlayer1Username().equalsIgnoreCase(clientController.getAccountClient().getUserInformation().getUserName())) {
                                Packet packet = new Packet(Packet.JOIN_GAME, clientController.getAccountClient().getUserInformation(), game);
                                clientController.getGameClient().addRequestToServer(packet);
                            } else {
                                // switch to gameBoard
                            }
                        });
                    }
                    private final Button spectateButton = new Button("Spectate");
                    {
                        spectateButton.setOnAction(event -> {
                            // send spectate game packet to game server
                            Game game = getTableView().getItems().get(getIndex());
                            if (!game.getPlayer1Username().equalsIgnoreCase(clientController.getAccountClient().getUserInformation().getUserName())) {
                                Packet packet = new Packet(Packet.OBSERVE_GAME, clientController.getAccountClient().getUserInformation(), game);
                                clientController.getGameClient().addRequestToServer(packet);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setGraphic(null);
                        } else {
                            HBox pane = new HBox(joinButton, spectateButton);
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }
}
