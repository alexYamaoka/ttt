package Server;

import Shared.Packet;

import java.io.ObjectOutputStream;

public interface Service {
    public void handle(ClientConnection clientConnection, Packet packet);

    public void update(Packet packet);


}
