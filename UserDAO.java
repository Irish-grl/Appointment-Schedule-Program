package Utilities;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class UserDAO.java reads and validates users in the database.
 * */
public class UserDAO {

    private static final String SELECT_ALL = "SELECT * FROM users";

    /**
     * Method getAllUsers gets all users
     * @return users
     * */
    public static ObservableList getAllUsers(){

        ObservableList<User> users = FXCollections.observableArrayList();

        try {

            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(SELECT_ALL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User allUsers = new User(userID, userName, password);
                users.add(allUsers);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Method validateUser checks to see if the username and password match a user in the database
     * @param userName is the username to check
     * @param password is the password to check
     * */
    public static boolean validateUser(String userName, String password){

        ObservableList<User> allUsers = UserDAO.getAllUsers();
        for(int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            String thisUserName = user.getUserName();
            String thisPassword = user.getPassword();

            if (userName.equals(thisUserName) && password.equals(thisPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method getUserID takes the user name and password passed into the method and finds the User ID in the database
     * @param userName is the username to check
     * @param password is the password to check
     * */
    public static int getUserID(String userName, String password){

        ObservableList<User> allUsers = UserDAO.getAllUsers();
        int userID = 0;
        for(int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            String thisUserName = user.getUserName();
            String thisPassword = user.getPassword();
            int thisUserID = user.getUserID();

            if (userName.equals(thisUserName) && password.equals(thisPassword)) {
               userID = thisUserID;
            }
        }
        return userID;
    }
}
