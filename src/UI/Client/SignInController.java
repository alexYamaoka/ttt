package UI.Client;
import Shared.UserInformation;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.Socket;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
        // publishing sign in into

        public Label lblError;
        public AnchorPane rootPane;
        @FXML
        private TextField txtF_UserName;
        String userName;
        boolean enteredUserName;
        @FXML
        private PasswordField txtF_Password;
        String password;
        boolean enteredPassword;
        @FXML
        private Button btn_LogIn;
        @FXML
        private Button btn_SignUp;
        SignInController controller;
        //ArrayList<Sub> subs = new ArrayList<>();
        PreparedStatement pr = null;
        ResultSet rs = null;
        Socket userSocket;
        UserInformation user;
        private DataSource ds = DatabaseManager.getInstance();

        public void setLogIn(ActionEvent event) throws SQLException {
                btn_LogIn = (Button) event.getTarget();
                userName = txtF_UserName.getText();
                password = txtF_Password.getText();
                ds.query(Shared.UserInformation.class, " username = ' " + userName + " ' AND password = ' " + password + " ' ");
                /*
                try {
                        String sql = "SELECT * FROM user WHERE username = ? and password = ?";
                        pr = DatabaseManager.getInstance().myConn.prepareStatement(sql);
                        pr.setString(1, userName);
                        pr.setString(2, password);
                        rs = pr.executeQuery();
                        if (!rs.next()) {
                                //System.out.println(rs.getString(1));
                                lblError.setTextFill(Color.RED);
                                lblError.setText("Enter Correct Username/Password");
                                System.out.println("Enter Correct Username/Password");
                        } else {
                                MainMenuScene();

                                //User newUser = new User(userSocket);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }

                 */
        }


        public void SignUp(ActionEvent event) throws IOException {
                Stage stage = null;
                Parent root = null;

                if (event.getSource() == btn_SignUp) {
                        stage = (Stage) btn_SignUp.getScene().getWindow();
                        root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

        }

        public void MainMenuScene() throws IOException {
                Stage stage = null;
                Parent root = null;
                stage = (Stage) btn_LogIn.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }



        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}




