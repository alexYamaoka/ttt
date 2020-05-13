package UI.Client;

import Client.ClientController;
import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import ObserverPatterns.HistoryListener;
import ObserverPatterns.GameHistoryListener;
import Shared.GameInformation;
import Shared.Packet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class GameHistoryController implements Initializable, GameHistoryListener {
    @FXML
    private TableView gameHistoryTable;
    @FXML
    private TableColumn<Game, String> gameID, opponent, startTime, endTime, results;

    private ClientController clientController;
    private DataSource ds = DatabaseManager.getInstance();

    private ObservableList<Game> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        gameHistoryTable.setItems(data);
        gameID.setCellValueFactory(new PropertyValueFactory<>("id"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        results.setCellValueFactory(new PropertyValueFactory<>("results"));
    }

    public void getListOfGames(HashSet<Game> listOfGames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                gameHistoryTable.getItems().clear();
                loadGames(listOfGames);
            }
        });
    }

    public void GetGameinfo() {
        String id = clientController.getAccountClient().getUserInformation().getId();
        String username = clientController.getAccountClient().getUserInformation().getUsername();
        List<String> user = new ArrayList<>();
        user.add(id);
        user.add(username);
        String data = String.join(" ", user);
        Packet packet = new Packet(Packet.GAME_HISTORY_INFO, clientController.getAccountClient().getUserInformation(), data);
        clientController.getAccountClient().addRequestToServer(packet);
    }


    public void getServerInfo(String message){
        Platform.runLater(()->{
            if(message.equals("SUCCESS")) {


            }
        });
    }
    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void updateHistory(List<Game> list) {
        Platform.runLater(() -> {
            data.clear();
            data.addAll(list);
        });
    }
  
    public void updateGameHistory(GameInformation gameInformation) {

    }
}
