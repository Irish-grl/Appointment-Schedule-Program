package Controller;

import Model.Appointment;
import Utilities.AppointmentDAO;
import Utilities.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class LogInController.java controls the Log In UI
 * */
public class LogInController implements Initializable {

    public Label companyLabel;
    public Label userLabel;
    public Label passwordLabel;
    public Button signInButton;
    public Button exitButton;

    Stage stage;
    Parent scene;

    @FXML
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Bundle/programLang", Locale.getDefault());

    @FXML
    private Label locationLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;


    /**
     * Method onActionExitProgram asks the user if they would like to exit the program. If the user clicks the 'yes'
     * button, the method closes the program.
     */
    @FXML
    void onActionExitProgram() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("alert"));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            System.exit(0);
        }
    }

    /**
     * Method onActionSignIn gets the values user entered in the fields, and checks that these values match a user in the
     * database. Method also saves successful/unsuccessful log-in attempts to login_activity.txt file for reporting on.
     * If the user matches a user in the database, this method takes the user to the Schedule UI screen. First lambda
     * checks start times for all appointments to see if the start time is between the current time and 15 minutes from
     * the current time. Second lambda then filters these appointments by the User ID of the user who is signing into
     * the program. Utilizing the two lambdas keeps from having to utilize a nested pair of for-loops to search for the
     * same data, which initially significantly slowed the program. Finally, if there is an appointment for the user within 15
     * minutes of signing into the program, alert is generated warning the user of the appointment coming up. If there is no
     * appointment scheduled within 15 minutes of the user signing on, then the alert message states there are not appointments
     * coming up within the next 15 minutes.
     *
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     */
    @FXML
    void onActionSignIn(ActionEvent event) throws IOException {

        String filename = "src/Files/login_activity.txt";
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        LocalDate date = LocalDate.now();
        LocalTime userLogTime = LocalTime.now();
        userLogTime.toString();

        UserDAO.validateUser(userName, password);

        String valid = "true";
        String inValid = "false";

        FileWriter fw = new FileWriter(filename, true);
        PrintWriter printWriter = new PrintWriter(fw);

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();

        if (UserDAO.validateUser(userName, password)) {

            printWriter.print(" | " + userName + " ");
            printWriter.print(" " + password + " ");
            printWriter.print(" " + valid + " ");
            printWriter.print(" " + date + " ");
            printWriter.println(" " + userLogTime + " | ");
            printWriter.close();
            fw.close();

            int appointmentID = 0;
            LocalDate startDate = null;
            LocalTime startTime = null;
            ObservableList<Appointment> filteredByUser = getFilteredAppointments(appointments, userName, password);

            for (int i = 0; i < appointments.size(); i++) {

                Appointment appointment = appointments.get(i);
                appointmentID = appointment.getAppointmentID();
                Timestamp start = appointment.getStart();
                LocalDateTime startLDT = start.toLocalDateTime();
                startDate = startLDT.toLocalDate();
                startTime = startLDT.toLocalTime();
                startTime.toString();
            }

            if (filteredByUser.size() > 0) {

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setContentText(resourceBundle.getString("alert2") + filteredByUser +
                        " " + appointmentID + " " + startDate + " " + startTime);
                alert2.showAndWait();
            }else {

                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText(resourceBundle.getString("alert3"));
                alert3.showAndWait();
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Schedule.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {

            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(resourceBundle.getString("alert1"));
            alert1.showAndWait();

            printWriter.print(" | " + userName + " ");
            printWriter.print(" " + password + " ");
            printWriter.print(" " + inValid + " ");
            printWriter.print(" " + date + " ");
            printWriter.print(" " + userLogTime + " | ");
            printWriter.close();
            fw.close();
        }
    }

    /**
     * Method initialize sets the location label to the zone of the user's default zone, sets UI components in the Log
     * In screen to English or French based off of the resourceBundle attribute's call to get the default Locale of the
     * user's computer(see instantiation of resourceBundle in attribute section at the top of this class).
     * @param url is the Resource Bundle url
     * @param rb  is the Resource Bundle from Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        locationLabel.setText("" + ZoneId.systemDefault().toString());
        exitButton.setText(resourceBundle.getString("exitButton"));
        companyLabel.setText(resourceBundle.getString("companyLabel"));
        userLabel.setText(resourceBundle.getString("userLabel"));
        passwordLabel.setText(resourceBundle.getString("passwordLabel"));
        signInButton.setText(resourceBundle.getString("signInButton"));

    }

    /**
     * Method getFilteredAppointments searches for appointments with the username and password passed into this method
     * and filters the appointment list by user ID of the user signing in for appointments that start within 15 minutes
     * of log in.
     * @param appointments is the list of appointments to filter
     * @param userName is the username to search for in the appointments list
     * @param password is the password to search for in the appointments list
     * */
    @FXML
    public ObservableList<Appointment> getFilteredAppointments(ObservableList<Appointment> appointments, String userName, String password){

        ObservableList<Appointment> filteredByUser = FXCollections.observableArrayList();
        appointments = AppointmentDAO.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            int appointmentID = appointment.getAppointmentID();
            Timestamp start = appointment.getStart();
            LocalDateTime startLDT = start.toLocalDateTime();
            LocalDate startDate = startLDT.toLocalDate();
            LocalTime startTime = startLDT.toLocalTime();
            startTime.toString();

            ObservableList<Appointment> filteredAppointments = appointments.filtered(s -> {
                if (s.getStart().after(Timestamp.valueOf(now)) && s.getStart().before(Timestamp.valueOf(now.plusMinutes(15)))) {
                    return true;
                }
                return false;
            });

            filteredByUser = filteredAppointments.filtered(u -> {
                if (u.getUserID() == UserDAO.getUserID(userName, password)) {
                    return true;
                }
                return false;
            });
        }
        return filteredByUser;
    }
}
