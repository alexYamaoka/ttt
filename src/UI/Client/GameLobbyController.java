package UI.Client;

import Client.ClientController;
import Models.Game;
import Models.Move;
import ObserverPatterns.GameListener;
import ObserverPatterns.LobbyListener;
import Shared.Packet;
import Shared.UserInformation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class GameLobbyController implements Initializable, LobbyListener, GameListener {
    @FXML
    private TableView<Game> activeGames;
    @FXML
    private TableColumn<Game, String> player1Column, player2Column, statusColumn;
    @FXML
    private TableColumn<Game, Void> actionColumn;
    @FXML
    private Button returnToMainMenuButton, newGameButton, newGameAgainstComputerButton;
    @FXML
    private ClientController clientController;

    private ObservableList<Game> data = FXCollections.observableArrayList();

    private HashMap<String, Pair<Pane, GameBoardController>> gameBoards = new HashMap<>();

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

    public void onPlayAgainstComputerButtonClicked(ActionEvent event) {
        if(event.getSource() == newGameAgainstComputerButton) {
            Packet packet = new Packet(Packet.AI_GAME, clientController.getAccountClient().getUserInformation(), "NEW-GAME");
            clientController.getGameClient().addRequestToServer(packet);
        }
    }

    public void onCreateGameButtonClicked(ActionEvent event) {
        if (event.getSource() == newGameButton) {
            Packet packet = new Packet(Packet.NEW_GAME_CREATED, clientController.getAccountClient().getUserInformation(), "NEW-GAME");
            clientController.getGameClient().addRequestToServer(packet);
        }
    }

    public void returnToMainMenuButtonClicked(ActionEvent actionEvent) {
        //return to main menu
    }

    @Override
    public void newGame(Game game) {
        Platform.runLater(() -> {
            System.out.println("New Game Created: " + game.getId());
            createGame(game);
        });
    }

    private void createGame(Game game) {
        System.out.println("Create Game: " + game.getId());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
            Pane pane = loader.load();
            GameBoardController gameBoardController = loader.getController();
            gameBoardController.setClientController(clientController);
            gameBoardController.setGame(game);
            Pair<Pane, GameBoardController> pair = new Pair<>(pane, gameBoardController);
            gameBoards.put(game.getId(), pair);
            Scene scene = new Scene(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void getListOfGames(HashSet<Game> listOfGames) {
        Platform.runLater(() -> {
            data.clear();
            data.addAll(listOfGames);
        });
    }

    @Override
    public void getListOfOnlinePlayers(HashSet<UserInformation> listOfOnlinePlayers) {
        Platform.runLater(() -> {

        });
    }

    @Override
    public void updateMove(Move move) {
        Platform.runLater(() -> {
            System.out.println("UpdateMove: " + gameBoards.containsKey(move.getGameId()));
            if (gameBoards.containsKey(move.getGameId())) {
                gameBoards.get(move.getGameId()).getValue().updateMove(move);
            }
        });
    }

    @Override
    public void updateStatus(String message) {
        Platform.runLater(() -> {
            String[] str = message.trim().split("\\s+");
            String gameId = str[0];
            String status = str[1];
            System.out.println("Update Status: " + message);
            if (gameBoards.containsKey(gameId)) {
                gameBoards.get(gameId).getValue().updateStatus(status);
            }
        });
    }

    @Override
    public void setPlayer1Username(String player1Username) {
        Platform.runLater(() -> {
            String[] str = player1Username.trim().split("\\s+");
            String gameId = str[0];
            String username = str[1];
            System.out.println("Set Player 1 Username: " + player1Username);
            if (gameBoards.containsKey(gameId)) {
                gameBoards.get(gameId).getValue().setPlayer1Username(username);
            }
        });
    }

    @Override
    public void setPlayer2Username(String player2Username) {
        Platform.runLater(() -> {
            String[] str = player2Username.trim().split("\\s+");
            String gameId = str[0];
            String username = str[1];
            System.out.println("Set Player 2 Username: " + player2Username);
            if (gameBoards.containsKey(gameId)) {
                gameBoards.get(gameId).getValue().setPlayer2Username(username);
            }
        });
    }

    @Override
    public void joinGame(Game game) {
        Platform.runLater(() -> {
            if (!gameBoards.containsKey(game.getId())) {
                createGame(game);
            }
            // switch to the new scene
            Stage stage = (Stage) newGameButton.getScene().getWindow();
            Parent root = gameBoards.get(game.getId()).getKey();
            stage.setScene(root.getScene());
            stage.show();
        });
    }

    @Override
    public void clearGameList() {
        activeGames.getItems().clear();
    }

    @Override
    public void spectateGame(Game game) {
        Platform.runLater(() -> {
            if(!gameBoards.containsKey(game.getId())) {
                createGame(game);
            }
            // switch to the new scene
            Stage stage = (Stage) newGameButton.getScene().getWindow();
            Parent root = gameBoards.get(game.getId()).getKey();
            GameBoardController gameBoardController = gameBoards.get(game.getId()).getValue();
            gameBoardController.setPlayer1Username(game.getPlayer1Username());
            gameBoardController.setPlayer2Username(game.getPlayer2Username());
            stage.setScene(root.getScene());
            stage.show();
        });
    }

    private void addButtonsToTable() {
        Callback<TableColumn<Game, Void>, TableCell<Game, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Game, Void> call(final TableColumn<Game, Void> param) {
                final TableCell<Game, Void> cell = new TableCell<>() {
                    private final Button joinButton = new Button("Join");
                    private final Button spectateButton = new Button("Spectate");

                    {
                        joinButton.setOnAction(event -> {
                            Game game = getTableView().getItems().get(getIndex());
                            String player1 = game.getPlayer1Username();
                            String player2 = game.getPlayer2Username();
                            String clientUsername = clientController.getAccountClient().getUserInformation().getUserName();
                            System.out.println("Join Button: " + player1 + " " + player2 + " " + clientUsername);

                            if (player1.equals(clientUsername)) {
                                joinGame(game);
                            } else if (player2 == null) {
                                Packet packet = new Packet(Packet.JOIN_GAME, clientController.getAccountClient().getUserInformation(), game.getId());
                                clientController.getGameClient().addRequestToServer(packet);
                            } else if (player2.equals(clientUsername)) {
                                joinGame(game);
                            }
                        });
                    }

                    {
                        spectateButton.setOnAction(event -> {
                            // send spectate game packet to game server
                            Game game = getTableView().getItems().get(getIndex());
                            if (!game.getPlayer1Username().equalsIgnoreCase(clientController.getAccountClient().getUserInformation().getUserName())) {
                                if(!game.getPlayer2Username().equalsIgnoreCase(clientController.getAccountClient().getUserInformation().getUserName())) {
                                    Packet packet = new Packet(Packet.OBSERVE_GAME, clientController.getAccountClient().getUserInformation(), game.getId());
                                    clientController.getGameClient().addRequestToServer(packet);
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
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
