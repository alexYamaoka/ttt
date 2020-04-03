package sample;

public class User {

    int id;
    String UserName;
    String Password;
    String firstName;
    String lastName;

    public User(String userName, String password, String firstName, String lastName) {
        UserName = userName;
        Password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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



}
