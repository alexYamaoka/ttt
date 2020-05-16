package AccountService;

import DataBase.sql.DataSource;
import DataBase.sql.DatabaseManager;
import Models.BaseModel;
import Server.ClientConnection;
import Shared.GameInformation;
import Shared.Packet;
import Shared.UserInformation;
import app.Server;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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
                service.removeAccount(clientConnection);

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
                System.out.println("DataToString  = " + data.toString()); // This comes in fine
                String[] str3 = UpdateString.trim().split("\\s+");
                String UpdateUserName = str3[0];
                String UpdateFirstName = str3[1];
                String UpdateLastName = str3[2];
                String Id = str3[3];
                String UpdatePassword = str3[4];

                try {
                    if (ds.update(UpdateFirstName, UpdateLastName, UpdateUserName, Id, UpdatePassword)) {
                        UserInformation newInformation = new UserInformation(UpdateFirstName, UpdateLastName, UpdateUserName, null, UpdatePassword);
                        newInformation.setId(Id);
                        Packet packet1 = new Packet(Packet.UPDATE_USER, userInformation, newInformation);
                        clientConnection.sendPacketToClient(packet1);
                    }

                } catch (SQLException ex) {
                    stop();
                }
                break;

            case Packet.DELETE_ACCOUNT:
                String DeleteUserString = data.toString();
                String[] str4 = DeleteUserString.trim().split("\\s+");
                String DeleteID = str4[0];
                Packet deletePacket;
                try {
                    if (server.DeleteUser(DeleteID)) {
                        deletePacket = new Packet(Packet.DELETE_ACCOUNT, userInformation, "SUCCESS");
                        clientConnection.sendPacketToClient(deletePacket);
                    }
                } catch (SQLException e) {
                    stop();
                }
                break;

            case Packet.ACTIVATE_ACCOUNT:
                String ActivateAccountString = data.toString();
                String[] str5 = ActivateAccountString.trim().split("\\s+");
                String ActivateAccountID = str5[0];
                Packet ActivateAccountPacket;
                try {
                    if (ds.Activate(ActivateAccountID)) {
                        ActivateAccountPacket = new Packet(Packet.ACTIVATE_ACCOUNT, userInformation, "SUCCESS");
                        clientConnection.sendPacketToClient(ActivateAccountPacket);
                    }
                } catch (SQLException e) {
                    stop();
                }
                break;

            case Packet.GET_ONLINE_PLAYERS:
                Packet packet1 = new Packet(Packet.GET_ONLINE_PLAYERS, userInformation, service.getPlayersOnline());
                clientConnection.sendPacketToClient(packet1);
                break;

            case Packet.GAME_HISTORY_INFO:
                String GameInfoString = data.toString();
                String[] str6 = GameInfoString.trim().split("\\s+");
                System.out.println("GameInfoServer String " + data.toString());
                String id = str6[0];
                String username = str6[1];

                try {
                    List<GameInformation> gameInformation = new ArrayList<>();
                    gameInformation = ds.getPlayerGamesInfo(id, username);


                    if (gameInformation.isEmpty()) {
                        stop();
                    } else {
                        Packet info = new Packet(Packet.GAME_HISTORY_INFO, userInformation, (Serializable) gameInformation);
                        clientConnection.sendPacketToClient(info);
                        System.out.println("Packet sent successfully ");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //Packet packet2 = new Packet(Packet.GAME_INFO_SEVER,userInformation,data);
                //clientConnection.sendPacketToClient(packet2);
        }

        stop();
    }

    public boolean getRunning() {
        return running.get();
    }
}
