package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {

    int id;
    String UserName;
    String Password;
    String firstName;
    String lastName;
    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public User(){

    }

    /*
    public User(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }
    */
    public User(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public User(String userName, String password, String firstName, String lastName) {
        UserName = userName;
        Password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public int getId() {

        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}
