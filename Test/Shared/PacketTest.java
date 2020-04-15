package Shared;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest
{

    @org.junit.jupiter.api.Test
    void getRequest_isCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals(Packet.CONNECT, packet.getRequest());
    }

    @org.junit.jupiter.api.Test
    void getData_isCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals("data", packet.getData());
    }

    @org.junit.jupiter.api.Test
    void getInformation_isCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals(userInformation, packet.getInformation());
    }
}