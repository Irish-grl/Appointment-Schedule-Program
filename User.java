package Model;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class User.java creates User objects with getters for the user ID, user name and user password.
 * */
public class User {

    private int userID;
    private String userName;
    private String password;

    /**
     *Class Constructor
     @param userID is the user ID
     @param userName is the user name
     @param password is the user password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Method getUserID gets the user ID.
     * @return the user ID
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * Method getUserName gets the user name.
     * @return the user name
     * */
    public String getUserName() {
        return userName;
    }

    /**
     * Method getPassword gets the password.
     * @return the password
     * */
    public String getPassword() {
        return password;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the user ID in brackets and
     * the user name to the screen.
     * */
    @Override
    public String toString(){
        return ("[" + userID + "] " + userName);
    }
}

