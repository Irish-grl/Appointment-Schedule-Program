package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class DBConnector.java starts a connection to the database, gets a connection to the database, and closes the connection.
 * */
public class DBConnector {

    private static final String protocol = "jdbc:";
    private static final String vendor = "mysql:";
    private static final String ipName = "//wgudb.ucertify.com/";
    private static final String dbName = "WJ07JCb";

    private static final String jdbcUrl = protocol + vendor + ipName + dbName;

    private static Connection connection = null;

    private static final String username = "U07JCb";
    private static final String password = "53689041805";

    /**
     * Method startConnection starts a connection to the database
     * */
    public static Connection startConnection() {

        try {

            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Method getConnection gets the database connection
     * @return the connection
     * */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Method closeConnection closes the connection to the database
     * */
    public static void closeConnection(){

        try {

            connection.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e){
        }
    }

}