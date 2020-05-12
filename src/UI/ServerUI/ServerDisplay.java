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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDisplay implements Initializable, ServiceListener {

    @FXML
    private TableView<Game> activeGames, games;
    @FXML
    private TableColumn<Game, String> gameID_AG, player1_AG, player2_AG, startTime_AG, gameID_G, player1_G, player2_G, startTime_G, endTime_G, result_G, spectators_G;
    @FXML
    private TableView<UserInformation> activePlayers, accounts;
    @FXML
    private ListView allAccounts = new ListView();
    private DataSource ds = DatabaseManager.getInstance();
    private TableColumn<UserInformation, String> username_AP, status_AP, username_A, password_A, firstName_A, lastName_A, status_A;
    private Main main;
    private BlockingQueue<Packet> packetsReceived = new LinkedBlockingQueue<>();

    /*
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAGTable();
        initializeAPTable();
        initializeGTable();
        initializeATable();
    }
    private void initializeAGTable() {
        gameID_AG.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        player1_AG.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2_AG.setCellValueFactory(new PropertyValueFactory<>("player2"));
        startTime_AG.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        editableAGCols();
    }
    private void initializeAPTable() {
        username_AP.setCellValueFactory(new PropertyValueFactory<>("username"));
        status_AP.setCellValueFactory(new PropertyValueFactory<>("status"));

        editableAPCols();
    }
    private void initializeGTable() {
        gameID_G.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        player1_G.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2_G.setCellValueFactory(new PropertyValueFactory<>("player2"));
        startTime_G.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_G.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        result_G.setCellValueFactory(new PropertyValueFactory<>("result"));
        spectators_G.setCellValueFactory(new PropertyValueFactory<>("spectators"));

        editableGCols();
    }
    private void initializeATable() {
        username_A.setCellValueFactory(new PropertyValueFactory<>("username"));
        password_A.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstName_A.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName_A.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        status_A.setCellValueFactory(new PropertyValueFactory<>("status"));

        editableACols();
    }

    //need to add each information into class and convert the value entered type (String) into each respective type
    private void editableAGCols(){
        gameID_AG.setCellFactory(TextFieldTableCell.forTableColumn());
        gameID_AG.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });
        player1_AG.setCellFactory(TextFieldTableCell.forTableColumn());
        player1_AG.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlayer1Info(e.getNewValue());
        });
        player2_AG.setCellFactory(TextFieldTableCell.forTableColumn());
        player2_AG.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlayer2Info(e.getNewValue());
        });
        startTime_AG.setCellFactory(TextFieldTableCell.forTableColumn());
        startTime_AG.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStartTime(e.getNewValue());
        });
    }
    private void editableAPCols(){
        username_AP.setCellFactory(TextFieldTableCell.forTableColumn());
        username_AP.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUserName(e.getNewValue());
        });
        status_AP.setCellFactory(TextFieldTableCell.forTableColumn());
        status_AP.setOnEditCommit(e -> {
            //e.getTableView().getItems().get(e.getTablePosition().getRow()).setStatus(e.getNewValue());
        });
    }
    private void editableGCols(){
        gameID_G.setCellFactory(TextFieldTableCell.forTableColumn());
        gameID_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });
        player1_G.setCellFactory(TextFieldTableCell.forTableColumn());
        player1_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlayer1Info(e.getNewValue());
        });
        player2_G.setCellFactory(TextFieldTableCell.forTableColumn());
        player2_G.setOnEditCommit(e -> {
            //e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlayer2Info(e.getNewValue());
        });
        startTime_G.setCellFactory(TextFieldTableCell.forTableColumn());
        startTime_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStartTime(e.getNewValue());
        });
        endTime_G.setCellFactory(TextFieldTableCell.forTableColumn());
        endTime_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEndTime(e.getNewValue());
        });
        result_G.setCellFactory(TextFieldTableCell.forTableColumn());
        result_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setResult(e.getNewValue());
        });
        spectators_G.setCellFactory(TextFieldTableCell.forTableColumn());
        spectators_G.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSpectators(e.getNewValue());
        });
    }
    private void editableACols(){
        username_AP.setCellFactory(TextFieldTableCell.forTableColumn());
        username_AP.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUserName(e.getNewValue());
        });
        password_A.setCellFactory(TextFieldTableCell.forTableColumn());
        password_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setLastName(e.getNewValue());
        });
        firstName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        firstName_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setFirstName(e.getNewValue());
        });
        lastName_A.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStatus(e.getNewValue());
        });
        status_A.setCellFactory(TextFieldTableCell.forTableColumn());
        status_A.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStatus(e.getNewValue());
        });
    }

     */


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

    private synchronized void updateUI()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                Packet packet = null;
                try
                {
                    packet = packetsReceived.take();

                    switch (packet.getRequest())
                    {
                        case Packet.REGISTER_CLIENT:
                            UserInformation newRegisteredClient = (UserInformation) packet.getData();

                        case Packet.SIGN_IN:
                            UserInformation newSignedInUser = (UserInformation) packet.getData();
                            break;

                        case Packet.SIGN_OUT:
                            UserInformation newSignedOutUser = (UserInformation) packet.getData();
                            break;

                        case Packet.ACTIVE_GAME:
                            Game newCreatedGame = (Game) packet.getData();
                            break;

                        case Packet.GAME_CLOSE:
                            Game newClosedGame = (Game) packet.getData();

                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }


            }
        });

    }






    public void setMain(Main main)
    {
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
