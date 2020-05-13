package UI.ServerUI;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Shared.UserInformation;
import ObserverPatterns.ServiceListener;
import Shared.Packet;
import Shared.UserInformation;
import app.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDisplay implements Initializable, ServiceListener {

    @FXML
    private TableView<Game> activeGames, games;
    @FXML
    private TableColumn<Game, String> gameID_AG, player1_AG, player2_AG, gameID_G, player1_G, player2_G, startTime_G, endTime_G, result_G, spectators_G;
    @FXML
    private TableColumn<Game, Timestamp> startTime_AG;
    @FXML
    private TableView<UserInformation> activePlayers, accounts;
    @FXML
    private TableColumn<UserInformation, String> username_AP, playerId_AP, username_A, password_A, firstName_A, lastName_A, deleted_A;

    private DataSource ds = DatabaseManager.getInstance();
    private BlockingQueue<Packet> packetsReceived = new LinkedBlockingQueue<>();


    private ObservableList<Game> activeGamesList = FXCollections.observableArrayList();
    private ObservableList<Game> allGamesList = FXCollections.observableArrayList();
    private ObservableList<UserInformation> onlinePlayersList = FXCollections.observableArrayList();
    private ObservableList<UserInformation> allPlayersList = FXCollections.observableArrayList();


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
        result_G.setCellValueFactory(new PropertyValueFactory<>("result"));
        spectators_G.setCellValueFactory(new PropertyValueFactory<>("spectators"));
    }

    private void initializeATable() {
        accounts.setItems(allPlayersList);
        username_A.setCellValueFactory(new PropertyValueFactory<>("username"));
        password_A.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstName_A.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName_A.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        deleted_A.setCellValueFactory(new PropertyValueFactory<>("isDeleted"));
        editableACols();
    }

    private void editableACols(){
        username_AP.setCellFactory(TextFieldTableCell.forTableColumn());
        username_AP.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });
        password_A.setCellFactory(TextFieldTableCell.forTableColumn());
        password_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });
        firstName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        firstName_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setFirstName(e.getNewValue());
        });
        lastName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setLastName(e.getNewValue());
        });
        deleted_A.setCellFactory(TextFieldTableCell.forTableColumn());
        deleted_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setIsDeleted(Integer.parseInt(e.getNewValue()));
        });
    }

    @FXML
    public void onOnlinePlayerClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String username = activePlayers.getSelectionModel().getSelectedItem().toString();
            System.out.println("username selected: " + username);
        }
    }

    @FXML
    public void onAccountClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String username = activePlayers.getSelectionModel().getSelectedItem().toString();
            System.out.println("username selected: " + username);

        }
    }

    @FXML
    public void onActiveGameClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String game = activeGames.getSelectionModel().getSelectedItem().toString();
            System.out.println("game selected: " + game);
        }
    }

    @FXML
    public void onAllGameClicked(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            String game = games.getSelectionModel().getSelectedItem().toString();
            System.out.println("game selected: " + game);
        }
    }

    public void display(ActionEvent event) {

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
                        allGamesList.add(newClosedGame);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
