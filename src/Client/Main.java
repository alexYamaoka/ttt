package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/MainMenu.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/SignIn.fxml"));
        primaryStage.setTitle("EndFrame TicTacToe");
        primaryStage.setScene(new Scene(root, 600, 450));
        //root.getStylesheets().add("Desktop/style.css");
        primaryStage.show();
    }

    public void testDatabase() {
        //ArrayList<User> user = new ArrayList<>();

        //User newUser = new User("Firstname", "LastName", "Username", "Password");
        //DatabaseManager.getInstance().addUser(newUser);

        //DatabaseManager.getInstance();
        //User newUser = new User("Hcorupe","Pass","Harrison","Corupe");

        //DatabaseManager.getInstance().addUser(newUser);
        //DatabaseManager.getInstance().deleteUser(15);
        //DatabaseManager.getInstance().deleteUser(16);
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
