package sample;

import Shared.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance = null;

    private DatabaseManager(){

    }

    public static DatabaseManager getInstance(){
        if(instance == null){
            synchronized (DatabaseManager.class){
                if(instance == null){
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public boolean addUser(UserInformation user){
        return true;
    }

    public boolean updateUser(UserInformation user){
        return true;
    }

    public boolean deleteUser(String username){
        return true;
    }

    public boolean deleteUser(int userID){
        return true;
    }

    public List<UserInformation> getAllUsers(){
        return null;
    }


    public List<UserInformation> getUsers(String filter){
        List<UserInformation> users = new ArrayList<>();
        return null;
    }
}
