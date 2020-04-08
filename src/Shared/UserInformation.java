package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class UserInformation implements Serializable {
    private String firstName, lastName;
    private String userName, userId;
    private String email;
    private ArrayList<String> chatRooms = new ArrayList<>();

    public UserInformation(String username) {
        this("", "", username, "");
    }

    public UserInformation(String firstName, String lastName, String userName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.userId = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }
}
