package UI.ServerUI;

import AccountService.AccountService;
import DataBase.sql.DatabaseManager;
import GameService.GameService;
import Models.Game;
import Models.Move;
import ObserverPatterns.ServiceListener;
import Shared.GameInformation;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDisplay implements Initializable, ServiceListener {

    public static GameService instance = null;
    @FXML
    private TableView<Game> activeGames;
    @FXML
    private TableView<GameInformation> games;
    @FXML
    private TableColumn<Game, String> gameID_AG, player1_AG, player2_AG, gameID_G, player1_G, player2_G, startTime_G, endTime_G, result_G, spectators_G;
    @FXML
    private TableColumn<Game, Timestamp> startTime_AG;
    @FXML
    private TableView<UserInformation> activePlayers, accounts;
    @FXML
    private TableColumn<UserInformation, String> username_AP, playerId_AP, username_A, password_A, firstName_A, lastName_A;
    @FXML
    private TableColumn<UserInformation, Integer> deleted_A;

    private DatabaseManager ds = DatabaseManager.getInstance();
    private BlockingQueue<Packet> packetsReceived = new LinkedBlockingQueue<>();
    private ObservableList<Game> activeGamesList = FXCollections.observableArrayList();
    private ObservableList<GameInformation> allGamesList = FXCollections.observableArrayList();
    private ObservableList<UserInformation> onlinePlayersList = FXCollections.observableArrayList();
    private ObservableList<UserInformation> allPlayersList = FXCollections.observableArrayList();
    private ServiceListener serviceListener = AccountService.getInstance();
    private Parent display;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAGTable();
        initializeAPTable();
        initializeGTable();
        initializeATable();
    }

    private void initializeAGTable() {
        activeGames.setItems(activeGamesList);
        gameID_AG.setCellValueFactory(new PropertyValueFactory<>("id"));
        player1_AG.setCellValueFactory(new PropertyValueFactory<>("player1Username"));
        player2_AG.setCellValueFactory(new PropertyValueFactory<>("player2Username"));
        startTime_AG.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    }

    private void initializeAPTable() {
        activePlayers.setItems(onlinePlayersList);
        username_AP.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerId_AP.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void initializeGTable() {
        games.setItems(allGamesList);
        gameID_G.setCellValueFactory(new PropertyValueFactory<>("id"));
        player1_G.setCellValueFactory(new PropertyValueFactory<>("player1Username"));
        player2_G.setCellValueFactory(new PropertyValueFactory<>("player2Username"));
        startTime_G.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_G.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        result_G.setCellValueFactory(new PropertyValueFactory<>("winningPlayerId"));
        spectators_G.setCellValueFactory(new PropertyValueFactory<>("spectators"));
        games.setRowFactory(tv -> {
            TableRow<GameInformation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())) {
                    GameInformation information = row.getItem();
                    List<Move> moves = null;
                    try {
                        moves = ds.moves(information.getId());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    loadGameStats(information, moves);
                }
            });
            return row;
        });
        try {
            allGamesList.addAll(ds.getAllGamesInfo());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGameStats(GameInformation information, List<Move> moves) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameHistoryStats.fxml"));
                Parent root = loader.load();
                GameHistoryStatsController controller = loader.getController();
                controller.setServerDisplay(this);
                controller.importGameInformation(information, moves);

                Stage stage = null;
                stage = (Stage) games.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initializeATable() {
        accounts.setItems(allPlayersList);

        username_A.setCellValueFactory(new PropertyValueFactory<>("username"));
        username_A.setCellFactory(TextFieldTableCell.forTableColumn());
        username_A.setOnEditCommit(event -> {
            UserInformation information = event.getTableView().getItems().get(event.getTablePosition().getRow());
            information.setUsername(event.getNewValue());

            if(event.getNewValue().isBlank()){

                System.out.println("Enter UserName");
            }else {
                try {
                    if(ds.updateServerUIUserName(information)){
                        System.out.println("SUCCESS");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        password_A.setCellValueFactory(new PropertyValueFactory<>("password"));
        password_A.setCellFactory(TextFieldTableCell.forTableColumn());
        password_A.setOnEditCommit(event -> {
            UserInformation information = event.getTableView().getItems().get(event.getTablePosition().getRow());
            information.setPassword(event.getNewValue());

            if(event.getNewValue().isBlank()){
                System.out.println("Enter password");
            }else{
                try {
                    if(ds.updateServerUIPassword(information)) {
                        System.out.println("SUCCESS");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        firstName_A.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        firstName_A.setOnEditCommit(event -> {
            UserInformation information = event.getTableView().getItems().get(event.getTablePosition().getRow());
            information.setFirstName(event.getNewValue());
            if(event.getNewValue().isBlank()){
                System.out.println("Enter FirstName");
            }else{
                try {
                    if(ds.updateServerUIFirstName(information)){
                        System.out.println("SUCESS");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        lastName_A.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName_A.setOnEditCommit(event -> {
            UserInformation information = event.getTableView().getItems().get(event.getTablePosition().getRow());
            information.setLastName(event.getNewValue());
            if(event.getNewValue().isBlank()){
                System.out.println("Enter a LastName");
            }else{
                try {
                    if(ds.updateServerUILastName(information)){
                        System.out.println("SUCESS");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        deleted_A.setCellValueFactory(new PropertyValueFactory<>("isDeleted"));
        deleted_A.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        deleted_A.setOnEditCommit(event -> {
            UserInformation information = event.getTableView().getItems().get(event.getTablePosition().getRow());
            information.setIsDeleted(event.getNewValue());
            if(event.getNewValue() > 1 || event.getNewValue() < 0){
                System.out.println("Incorrect Input 0 = Active  | 1 = Deactivate");
            }else{
                try {
                    ds.updateServerUIIsDeleted(information);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        try {
            allPlayersList.addAll(ds.AllUserInfo());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDataChanged(Packet packet) {
        packetsReceived.add(packet);
        updateUI();
    }

    private synchronized void updateUI() {
        Platform.runLater(() -> {
            Packet packet;
            try {
                packet = packetsReceived.take();

                switch (packet.getRequest()) {
                    case Packet.REGISTER_CLIENT:
                        UserInformation newRegisteredClient = (UserInformation) packet.getData();
                        System.out.println("<--- New Registered User: " + newRegisteredClient.getUsername() + " --->");
                        allPlayersList.add(newRegisteredClient);


                    case Packet.SIGN_IN:
                        UserInformation newSignedInUser = (UserInformation) packet.getData();
                        System.out.println("<--- New Signed In User: " + newSignedInUser.getUsername() + " --->");
                        onlinePlayersList.add(newSignedInUser);
                        break;

                    case Packet.SIGN_OUT:
                        UserInformation newSignedOutUser = (UserInformation) packet.getData();
                        System.out.println("<--- New Signed Out User: " + newSignedOutUser.getUsername() + " --->");
                        onlinePlayersList.remove(newSignedOutUser);
                        break;

                    case Packet.ACTIVE_GAME:
                        Game newCreatedGame = (Game) packet.getData();
                        System.out.println("<--- New Created Game: " + newCreatedGame.getId() + " --->");
                        System.out.println("Start Time: " + newCreatedGame.getStartTime());
                        activeGamesList.add(newCreatedGame);
                        break;

                    case Packet.GAME_CLOSE:
                        Game newClosedGame = (Game) packet.getData();
                        System.out.println("<--- New Closed Game: " + newClosedGame.getId() + " --->");
                        activeGamesList.remove(newClosedGame);
                        GameInformation information = new GameInformation();
                        information.setId(newClosedGame.getId());
                        information.setPlayer1Username(newClosedGame.getPlayer1Username());
                        information.setPlayer2Username(newClosedGame.getPlayer2Username());
                        information.setStartTime(newClosedGame.getStartTime());
                        information.setEndTime(newClosedGame.getEndTime());
                        information.setWinningPlayerId(newClosedGame.getWinningPlayerId());
                        information.setStartingPlayerId(newClosedGame.getStartingPlayerId());
                        information.setSpectators(newClosedGame.getSpectators());
                        allGamesList.add(information);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void notifyAccountsServer(Packet packet) {
        serviceListener.onDataChanged(packet);
    }

    public void setDisplay(Parent pane) {
        this.display = pane;
    }

    public Parent getDisplay() {
        return display;
    }
}
