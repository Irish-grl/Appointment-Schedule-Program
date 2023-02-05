package DB;

import Utilities.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Locale;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class Main.java controls starts the UI, gets the connection and closes the connection.
 * */
public class Main extends Application {

    /**
     * Method start initiates the UI.
     * @param primaryStage is the stage object for the UI.
     * @throws Exception if the loader fails to load.
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /**
     * Method main starts the connection to the database, launches the application, and closes the database connection.
     * */
    public static void main(String[] args){
        //Locale.setDefault(new Locale("fr"));

        Connection connection = DBConnector.startConnection();

        launch(args);

        DBConnector.closeConnection();
    }
}
