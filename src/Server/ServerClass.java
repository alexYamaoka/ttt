package Server;

import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class ServerClass {

    public static void main(String[] args) {
        ServerSocket socket;
        try {
            socket = new ServerSocket(8000);
            System.out.println("After socket");
            while (true) {
                System.out.println("Before accept ");
                socket.accept();
                System.out.println("after accept ");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
