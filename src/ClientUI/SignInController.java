package ClientUI;

import Client.Client;
import Client.*;

import DataBase.DatabaseManager;
import Pub_Sub.Sub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class SignInController {  // publishing sign in into

        @FXML
        private TextField txtF_UserName;
        String userName;
        boolean enteredUserName;
        @FXML
        private TextField txtF_Password;
        String password;
        boolean enteredPassword;

        @FXML
        private Button btn_LogIn;

        @FXML
        private Button btn_SignUp;
        //Socket userSocket = userSocket = new Socket("localhost",800);
        ArrayList<Sub> subs = new ArrayList<>();



        public SignInController() throws IOException {

        }


        public void setLogIn(ActionEvent event) throws SQLException {
                btn_LogIn = (Button) event.getTarget();
                userName = txtF_UserName.getText();
                password = txtF_Password.getText();
                User user = new User();
                new Thread( () -> {
                        try {
                                DatabaseManager.getInstance().getUser(userName,password);
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                }).start();



        }


        public void SignUp(ActionEvent event){


        }

        public void ConnectToServer(){

        }


}




