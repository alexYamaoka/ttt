package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Server.ClientConnection;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

//import com.mysql.cj.protocol.Resultset;

public class AccountHandler implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Packet packet;
    private ObjectOutputStream outputStream;
    private Thread worker;
    private DataSource ds = DatabaseManager.getInstance();
    private Server server = new Server();
    private ClientConnection clientConnection;
    private AccountService service;

    public AccountHandler(ClientConnection clientConnection, Packet packet) {
        this.packet = packet;
        this.clientConnection = clientConnection;
        this.outputStream = clientConnection.getOutputStream();
        this.service = (AccountService) clientConnection.getService();
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
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
        switch (request) {
            case Packet.SIGN_IN:
                String SignInStr = data.toString();
                String[] str = SignInStr.trim().split("\\s+");
                String userName = str[0];
                String password = str[1];

                Packet packet;
                try {
                    if (server.login(userName, password)) { //if true the DB found user record
                        System.out.println("Successfully Logged In!");
                        List<BaseModel> items;
                        items = ds.query(UserInformation.class, " username = '" + userName + "' AND password = '" + password + "'");
                        packet = new Packet(Packet.SIGN_IN, userInformation, items.get(0));

                        clientConnection.sendPacketToClient(packet);
                        clientConnection.setInformation((UserInformation) items.get(0));
                        service.addOnlinePlayer((UserInformation) items.get(0));


                        // update server display
                        Packet notifyLoginSuccessful = new Packet(Packet.SIGN_IN, null, clientConnection.getInformation());
                        service.notifyServerDisplay(notifyLoginSuccessful);


                    } else {
                        packet = new Packet(Packet.SIGN_IN, userInformation, "FAIL");
                        clientConnection.sendPacketToClient(packet);
                    }

                } catch (SQLException e) {
                    stop();
                }
                break;
            case Packet.SIGN_OUT:

                // update server display
                Packet notifySignOut = new Packet(Packet.SIGN_OUT, null, clientConnection.getInformation());
                service.notifyServerDisplay(notifySignOut);

                break;
            case Packet.REGISTER_CLIENT:
                String registerString = data.toString();
                System.out.println("data = " + data.toString());
                String[] str2 = registerString.trim().split("\\s+");
                String newFirstName = str2[0];
                String newLastName = str2[1];
                String newUserName = str2[2];
                String newPassword = str2[3];
                Packet regPacket = new Packet();
                try {
                    if (server.registerUser(newFirstName, newLastName, newUserName, newPassword)) {
                        regPacket = new Packet(Packet.REGISTER_CLIENT, userInformation, data);

                        // update server display
                        UserInformation newRegisteredUser = new UserInformation(newFirstName, newLastName, newUserName, null, newPassword);
                        Packet notifyRegistration = new Packet(Packet.REGISTER_CLIENT, null, newRegisteredUser);
                        service.notifyServerDisplay(notifyRegistration);
                    }
                } catch (SQLException e) {
                    regPacket = new Packet(Packet.REGISTER_CLIENT, userInformation, "FAIL");
                }

                clientConnection.sendPacketToClient(regPacket);
                break;
            case Packet.UPDATE_USER:
                String UpdateString = data.toString();
                UserInformation user = new UserInformation();
                String[] str3 = UpdateString.trim().split("\\s+");
                String UpdateFirstName = str3[0];
                String UpdateLastName = str3[1];
                String UpdateUserName = str3[2];
                String UpdateEmail = str3[3];
                String UpdatePassword = str3[4];
                user.setFirstName(UpdateFirstName);
                user.setLastName(UpdateLastName);
                user.setUserName(UpdateUserName);
                user.setPassword(UpdatePassword);


                // **** fix the write object line
                try {
                    if (server.updateUser(user))
                    {
                        outputStream.writeObject(new UserInformation(UpdateFirstName, UpdateLastName, UpdateUserName, UpdateEmail, UpdatePassword));
                    }

                } catch (IOException | SQLException ex) {
                    stop();
                }
                break;

            case Packet.DELETE_ACCOUNT:
                String DeleteUserString = data.toString();
                String[] str4 = DeleteUserString.trim().split("\\s+");
                String DeleteFirstName = str4[0];
                String DeleteLastName = str4[1];
                String DeleteUserName = str4[2];
                String DeleteEmail = str4[3];
                String DeletePassword = str4[4];
                Packet deletePacket;
                try {
                    if (server.DeleteUser(DeleteUserName, DeleteFirstName, DeleteLastName, DeletePassword)) {
                        deletePacket = new Packet(Packet.DELETE_ACCOUNT, userInformation, data);

                        clientConnection.sendPacketToClient(deletePacket);
                    }
                } catch (SQLException e) {
                    stop();
                }
                break;

            case Packet.GET_ONLINE_PLAYERS:
                Packet packet1 = new Packet(Packet.GET_ONLINE_PLAYERS, userInformation, service.getPlayersOnline());
                clientConnection.sendPacketToClient(packet1);
                break;
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
