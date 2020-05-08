package ObserverPatterns;

import Shared.Packet;

public interface ServiceListener {
    void onDataChanged(Packet packet);
}
