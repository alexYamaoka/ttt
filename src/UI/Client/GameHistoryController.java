package UI.Client;

import Client.ClientController;
import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Shared.Packet;
import javafx.application.Platform;
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


public class GameHistoryController implements Initializable {
    @FXML
    private TableView gameHistoryTable;
    @FXML
    private TableColumn<Game, String> gameID, opponent, startTime, endTime, results;

    private ClientController clientController;
    private DataSource ds = DatabaseManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        gameID.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        results.setCellValueFactory(new PropertyValueFactory<>("results"));
    }

    // import an ObservableList of all game history from server
    private void loadGames(HashSet<Game> listOfGames) {
        System.out.println("Load games called!");


        gameHistoryTable.getItems().addAll(listOfGames);
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
        String username = clientController.getAccountClient().getUserInformation().getUserName();
        List<String> user = new ArrayList<>();
        user.add(id);
        user.add(username);
        String data = String.join(" ", user);
        Packet packet = new Packet(Packet.GAME_INFO_SEVER, clientController.getAccountClient().getUserInformation(), data);
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
}
