package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserConnection /*implements Runnable */
{

    private Socket Socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /*
    public UserConnection(Socket socket) throws IOException {
        this.Socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true){
            if(!Socket.isConnected()){
                return;
            }


        }
    }


     */

}
