package Server;

import Shared.Packet;

import java.io.ObjectOutputStream;

public interface Service {
    public void handle(ClientConnection clientConnection, Packet packet, ObjectOutputStream outputStream);

    public void update(Packet packet);
}
