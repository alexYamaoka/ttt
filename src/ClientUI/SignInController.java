package ClientUI;
import Client.*;

import DataBase.DatabaseManager;
import Pub_Sub.Sub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInController {
         // publishing sign in into

        public Label lblError;
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
        SignInController controller;
        ArrayList<Sub> subs = new ArrayList<>();
        PreparedStatement pr = null;
        ResultSet rs = null;
        Socket userSocket;
        User user;

        public SignInController() throws IOException {

        }


        public void setLogIn(ActionEvent event) throws SQLException {

                btn_LogIn = (Button) event.getTarget();
                userName = txtF_UserName.getText();
                password = txtF_Password.getText();
                        try {
                                String sql = "SELECT * FROM user WHERE username = ? and password = ?";
                                pr = DatabaseManager.getInstance().myConn.prepareStatement(sql);
                                pr.setString(1,userName);
                                pr.setString(2,password);
                                rs = pr.executeQuery();
                                if(!rs.next()){
                                        //System.out.println(rs.getString(1));
                                        lblError.setTextFill(Color.RED);
                                        lblError.setText("Enter Correct Username/Password");
                                        System.out.println("Enter Correct Username/Password");
                                }else{
                                        lblError.setTextFill(Color.GREEN);
                                        lblError.setText("Login Successful");
                                        userSocket = new Socket("localhost",800);
                                        //User newUser = new User(userSocket);
                                }
                        } catch (SQLException e) {
                                e.printStackTrace();
                        } catch (UnknownHostException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
        }


        public void SignUp(ActionEvent event){


        }




}




