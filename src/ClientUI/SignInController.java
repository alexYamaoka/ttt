package ClientUI;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignInController {

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


        public SignInController() throws IOException {
                Socket clientSocket = new Socket("localhost",8000);
                Client client = new Client(clientSocket);
        }


        public void setLogIn(ActionEvent event) throws SQLException {
                btn_LogIn = (Button) event.getTarget();
                userName = txtF_UserName.getText();
                password = txtF_Password.getText();

        }

        public void SignUp(ActionEvent event){


        }

        public void ConnectToServer(){

        }


}




