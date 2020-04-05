package Client;

import DataBase.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../ClientUI/SignIn.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        //ArrayList<User> user = new ArrayList<>();

        //User newUser = new User("Firstname", "LastName", "Username", "Password");
        //DatabaseManager.getInstance().addUser(newUser);

        DatabaseManager.getInstance();
        User newUser = new User("Hcorupe","Pass","Harrison","Corupe");

        //DatabaseManager.getInstance().addUser(newUser);
        //DatabaseManager.getInstance().deleteUser(15);
        //DatabaseManager.getInstance().deleteUser(16);


        launch(args);
        DatabaseManager.getInstance().disconnect();
        //System.out.println("After disconnect");
    }
}
