package Shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInformationTest
{


    @Test
    void getFirstName_returns_Correct()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");

        assertEquals("firstName", userInformation.getFirstName());
    }

    @Test
    void setFirstName_assigns_Correct()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setFirstName("firstName");
        assertEquals("firstName", userInformation.getFirstName());
    }

    @Test
    void getLastName_returns_Correct()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        assertEquals("lastName", userInformation.getLastName());
    }

    @Test
    void setLastName_assigns_Correct()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setLastName("lastName");
        assertEquals("lastName", userInformation.getLastName());
    }

    @Test
    void getUserName_returns_Correct()
    {
        UserInformation userInformation = new UserInformation("firstName", "lastName", "username", "email", "password");
        assertEquals("username", userInformation.getUsername());
    }

    @Test
    void setUserName_assigns_Correct()
    {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername("username");
        assertEquals("username", userInformation.getUsername());
    }
}