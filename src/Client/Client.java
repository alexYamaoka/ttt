package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable{

    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Client(Socket socket) throws IOException {
        System.out.println("Before socket ");
        this.socket = socket;
        System.out.println("Before In ");
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Before thread ");
        Thread t2 = new Thread(this);
        t2.start();
        System.out.println("after thread ");
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Trying to read Message");
            //Message msg = (Message)in.readObject();
        }

    }
}
