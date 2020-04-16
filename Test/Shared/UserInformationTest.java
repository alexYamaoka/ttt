package Shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInformationTest
{

    @Test
    void getFirstName_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        assertEquals("firstName", userInformation.getFirstName());
    }

    @Test
    void setFirstName_assignsCorrect()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setFirstName("firstName");
        assertEquals("firstName", userInformation.getFirstName());
    }

    @Test
    void getLastName_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        assertEquals("lastName", userInformation.getLastName());
    }

    @Test
    void setLastName_assignsCorrect()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setLastName("lastName");
        assertEquals("lastName", userInformation.getLastName());
    }

    @Test
    void getUserName_returnsCorrect()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        assertEquals("username", userInformation.getUserName());
    }

    @Test
    void setUserName_assignsCorrect()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setUserName("username");
        assertEquals("username", userInformation.getUserName());
    }
}