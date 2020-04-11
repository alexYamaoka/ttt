package Server;

import Shared.Packet;

import java.io.ObjectOutputStream;

public interface Service {
    public void handle(Packet packet, ObjectOutputStream outputStream);
}
