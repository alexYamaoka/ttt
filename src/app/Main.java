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
        // start AccountService
        AccountService service = new AccountService();
        service.start();

        // start client
        ClientController clientController = new ClientController(stage);
        stage.setTitle("EndFrame TicTacToe");
        clientController.run();
    }
}
