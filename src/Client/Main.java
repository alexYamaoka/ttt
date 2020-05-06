package Client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientController clientController = new ClientController(primaryStage);
        clientController.run();
        primaryStage.setTitle("EndFrame TicTacToe");
    }
}
