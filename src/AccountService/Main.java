package AccountService;

import Client.User;
import Shared.Packet;
import Shared.UserInformation;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
   public static void main(String[] args) {
       AccountService service = new AccountService();
       service.start();
       try {
           Socket socket = new Socket("localhost", 8000);
           UserInformation information = new UserInformation("illogical", "test", "test", "test");
           Packet packet = new Packet("REGISTER", information, "Hello World");
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
           objectOutputStream.writeObject(packet);

       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
}
