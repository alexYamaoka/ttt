package Client;

import Shared.Packet;
import Shared.UserInformation;

import java.util.ArrayDeque;

public class Client
{
    private String hostName;
    private int port;
    private UserInformation userInformation;
    private ArrayDeque<Packet> requests = new ArrayDeque<>();
    private ArrayDeque<Packet> response = new ArrayDeque<>();




}
