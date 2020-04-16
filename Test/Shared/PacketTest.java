package Shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacketTest
{

    @Test
    void getRequest_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals(Packet.CONNECT, packet.getRequest());
    }

    @Test
    void getData_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals("data", packet.getData());
    }

    @Test
    void getInformation_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        Packet packet = new Packet(Packet.CONNECT, userInformation, "data");

        assertEquals(userInformation, packet.getInformation());
    }
}