package Server;

import Shared.Packet;

public interface Service {
    void handle(ClientConnection clientConnection, Packet packet);

    void update(Packet packet);


}
