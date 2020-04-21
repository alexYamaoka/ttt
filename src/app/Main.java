package app;

import AccountService.AccountService;
import Client.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // start client
        ClientController clientController = new ClientController(stage);
        stage.setTitle("EndFrame TicTacToe");

        // start AccountService
        AccountService service = new AccountService();
        service.start();
    }
}
