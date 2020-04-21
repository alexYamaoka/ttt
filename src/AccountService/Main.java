package AccountService;
import Shared.Packet;
import Shared.UserInformation;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
   public static void main(String[] args) {
       AccountService service = new AccountService();
       service.start();
   }
}
