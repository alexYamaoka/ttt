package ObserverPatterns;

public interface UpdateUserinformationListener {
    void updateUserinformation(String message);
    void deactivateAccount(String message);
    void ActivateAccount(String message);
}
