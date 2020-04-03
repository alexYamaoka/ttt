package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/tictactoe?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST";
        //String url = "jdbc:mysql://localhost:3306/tictactoe";
        String username = "test";
        String password = "test123password";
        Connection myConn;

        //User newUser = new User("Firstname", "LastName", "Username", "Password");
        //DatabaseManager.getInstance().addUser(newUser);

        try{
            myConn = DriverManager.getConnection(url,username,password);
            Statement myStatement = myConn.createStatement();
            System.out.println("JDBC Database Connected");

            String sql = "select * from tictactoe.user";
            ResultSet rs = myStatement.executeQuery(sql);

            while(rs.next()){
                System.out.println(rs.getString("username"));
            }

            myConn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }


        launch(args);
    }
}
