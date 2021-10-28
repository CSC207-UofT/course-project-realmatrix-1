package manager;

import entity.Pack;
import entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * A manager that manages all Users.
 */
public class UserManager extends Manager<User>{
    private boolean loggedIn = false; // by default, no user logged in
    private Object currUser = null; // by default, no current user who is logged in

    public UserManager() {
        super();
    }

    public User getCurrUser() throws Exception {
        if (this.currUser instanceof User) {
            return (User) this.currUser;
        } else {
            throw new Exception("There's no logged-in user.");
        }
    }

    public void createNewUser(String name, String password) {
        String id = super.generateId();
        User user = new User(id, name, password);
        this.idToItem.put(id, user);
    }

    /**
     * Return the User with given name and password
     * @param name name of a User
     * @param password password of a User (matches the name)
     * @return User or if not found (which can't be the case; included for completeness reason) return null
     */
    private Object findUser(String name, String password) {
        for (User user : this.idToItem.values()) {
            if (Objects.equals(user.getName(), name) && Objects.equals(user.getPassword(), password)) {
                return user;
            }
        }
        return null;
    }

    public void logInUser(String name, String password) throws Exception {
        if (this.loggedIn) {
            throw new Exception("Please sign off before logging in.");
        } else if (this.findUser(name, password) == null) {
            throw new Exception("Invalid name or password. If you are new, please create an account first.");
        }
        this.loggedIn = true;
        this.currUser = this.findUser(name, password);
    }

    public void SignOffUser() throws Exception {
        if (!this.loggedIn) {
            throw new Exception("Already signed off.");
        }
        this.loggedIn = false;
        this.currUser = null;
    }

    /**
     * Change name/password of current user.
     * @param func 'N' for name or 'P' for password
     * @param newInfo new name or new password
     */
    public void changeInfo(char func, String newInfo) {
        if (loggedIn) {
            if (func == 'N') {
                ((User) this.currUser).changeName(newInfo);
            } else if (func == 'P') {
                ((User) this.currUser).changePassword(newInfo);
            }
        }
    }

    public Pack choosePack(String name) throws Exception{
        for(Pack p: ((User)this.currUser).getPackList()){
            if(p.getName() == name){
                return p;
            }
        }
        throw new Exception("no such pack");
    }

    /**
     * For testing purposes only.
     * @param args
     */
    public static void main(String[] args) {
        UserManager um = new UserManager();
        um.createNewUser("Xing", "password");
        um.createNewUser("SuperDog", "super");
        um.createNewUser("FunkyCat", "funky");
        for (User user : um.idToItem.values()) {
            System.out.println(user.getId());
        }
    }
}
