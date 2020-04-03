package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {

        ArrayList<User> user = new ArrayList<>();




        //User newUser = new User("Firstname", "LastName", "Username", "Password");
        //DatabaseManager.getInstance().addUser(newUser);

        DatabaseManager.getInstance();
        User newUser = new User("Bjackson","Pass","Blair","Jackson");

        DatabaseManager.getInstance().addUser(newUser);
        //launch(args);
        DatabaseManager.getInstance().disconnect();
    }
}
