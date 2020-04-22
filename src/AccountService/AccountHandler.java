package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;
//import com.mysql.cj.protocol.Resultset;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountHandler implements Runnable {
    private Packet packet;
    private ObjectOutputStream outputStream;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();


    private final AtomicBoolean running = new AtomicBoolean(false);

    public AccountHandler(Packet packet, ObjectOutputStream outputStream) {
        this.packet = packet;
        this.outputStream = outputStream;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        running.set(false);
        System.out.println("AccountHandler has stopped!");
    }

    @Override
    public void run() {
        running.set(true);
        System.out.println("AccountHandler has started!");
        String request = packet.getRequest();
        UserInformation userInformation = packet.getInformation();
        Serializable data = packet.getData();

        switch(request)
        {
            case Packet.SIGN_IN:
                String SignInStr = data.toString();
                String[] str = SignInStr.trim().split("\\s+");
                String userName = str[0];
                String password = str[1];

                Packet packet;
                try {
                    if(server.login(userName,password)){ //if true the DB found user record
                        System.out.println("Successfully Logged In!");
                        List<BaseModel> items;
                        items = ds.query(UserInformation.class," username = '" + userName + "' AND password = '" + password + "'");
                        packet = new Packet(Packet.SIGN_IN,userInformation, (UserInformation) items.get(0));
                        outputStream.writeObject(packet);
                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case Packet.SIGN_OUT:
                break;
            case Packet.REGISTER_CLIENT:
                String registerString = data.toString();
                String[] str2 = registerString.trim().split("\\s+");
                String newFirstName = str2[0];
                String newLastName = str2[1];
                String newUserName = str2[2];
                String newEmail = str2[3];
                String newPassword = str2[4];
                try {
                    server.registerUser(newFirstName,newLastName,newUserName,newEmail,newPassword);
                    outputStream.writeObject(new UserInformation(newFirstName, newLastName, newUserName, newEmail, newPassword));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                break;
            case Packet.UPDATE_USER:
                String UpdateString = data.toString();
                String[] str3 = UpdateString.trim().split("\\s+");
                String UpdateFirstName = str3[0];
                String UpdateLastName = str3[1];
                String UpdateUserName = str3[2];
                String UpdateEmail = str3[3];
                String UpdatePassword = str3[4];
                //server.updateUser()
                try {
                    outputStream.writeObject(new UserInformation(UpdateFirstName, UpdateLastName, UpdateUserName, UpdateEmail, UpdatePassword));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;

            case Packet.DELETE_ACCOUNT:
                break;
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
