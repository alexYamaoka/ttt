package Pub_Sub;

import Client.User;

public interface Sub {
    void add(Pub p);
    void notifySubs();
}
