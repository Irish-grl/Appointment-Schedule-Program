package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class DBQuery.java creates a preparedStatement object
 * */
public class DBQuery {

    private static PreparedStatement stmt;

    /**
     * Method setPreparedStatement sets the preparedStatement
     * @param connection is the connection to use
     * @param sqlStatement is the string to use
     * */
    public static void setPreparedStatement(Connection connection, String sqlStatement) {
        try {
            stmt = connection.prepareStatement(sqlStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method getPreparedStatement gets the preparedStatement
     * @return the preparedStatement
     * */
    public static PreparedStatement getPreparedStatement(){
        return stmt;
    }

}
