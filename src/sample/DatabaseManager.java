package sample;

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

    public boolean addUser(User user){
        return true;
    }

    public boolean updateUser(User user){
        return true;
    }

    public boolean deleteUser(String username){
        return true;
    }

    public boolean deleteUser(int userID){
        return true;
    }

    public List<User> getAllUsers(){
    }


    public List<User> getUsers(String filter){
        List<User> users = new ArrayList<>;
    }
}
