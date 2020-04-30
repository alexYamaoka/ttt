package Shared;

import Models.BaseModel;

import java.io.Serializable;
import java.util.UUID;

public class UserInformation extends BaseModel implements Serializable {

    private String firstName, lastName;
    private String userName;
    private String email = "email";
    private String password;
    private int isDeleted;

    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isDeleted = 0;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getId() + " " + firstName + " " + lastName + " " + userName + " " + email + " " + password);
        return builder.toString();
    }
}
