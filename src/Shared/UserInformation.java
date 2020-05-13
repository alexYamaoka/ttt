package Shared;

import Models.BaseModel;

import java.io.Serializable;

public class UserInformation extends BaseModel implements Serializable {

    private String firstName, lastName;
    private String username;
    private String email = "email";
    private String password;
    private int isDeleted;


    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserInformation) {
            UserInformation information = (UserInformation) obj;
            return this.getId().equals(information.getId());
        }
        return false;
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
        builder.append(getId() + " " + firstName + " " + lastName + " " + username + " " + email + " " + password + " " + isDeleted);
        return builder.toString();
    }
}
