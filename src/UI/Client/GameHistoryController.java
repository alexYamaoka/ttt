package UI.Client;

import Client.ClientController;
import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.Game;
import Models.Move;
import ObserverPatterns.GameHistoryListener;
import Shared.GameInformation;
import Shared.Packet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameHistoryController implements Initializable, GameHistoryListener {
    @FXML
    private TableView gameHistoryTable;
    @FXML
    private TableColumn<GameInformation, String> gameID, opponent, startTime, endTime, results;
    @FXML
    private Button backButton;

    private ClientController clientController;
    private DatabaseManager ds = DatabaseManager.getInstance();

    private ObservableList<GameInformation> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        gameHistoryTable.setItems(data);
        gameID.setCellValueFactory(new PropertyValueFactory<>("id"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("player2Username"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        results.setCellValueFactory(new PropertyValueFactory<>("winningPlayerId"));
        gameHistoryTable.setRowFactory(tv -> {
            TableRow<GameInformation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    GameInformation information = row.getItem();
                    List<Move> moves = null;
                    // query database for moves
                    try {
                        moves = ds.moves(information.getId());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    // switch to GameHistoryStats instance for game
                    loadGameStats(information, moves);
                }
            });
            return row;
        });
    }

    private void loadGameStats(GameInformation information, List<Move> moves) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameHistoryStats.fxml"));
                Parent root = loader.load();
                GameHistoryStatsController controller = loader.getController();
                controller.setClientController(clientController);
                controller.importGameInformation(information, moves);

                Stage stage = null;
                stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
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


    public void getServerInfo(String message) {
        Platform.runLater(() -> {
            if (message.equals("SUCCESS")) {


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
    public void updateHistory(List<GameInformation> list) {
        Platform.runLater(() -> {
            data.clear();
            data.addAll(list);
        });
    }

    public void updateGameHistory(GameInformation gameInformation) {

    }

    public void backButtonClicked(ActionEvent event) {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == backButton) {
            stage = (Stage) backButton.getScene().getWindow();
            root = clientController.getGameHistoryPane();
        }
        stage.setScene(root.getScene());
        stage.show();
    }
}
