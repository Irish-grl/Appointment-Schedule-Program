package Controller;

import Model.*;
import Utilities.AppointmentDAO;
import Utilities.OtherDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class ReportsController.java controls the Reports UI
 * */
public class ReportsController implements Initializable {


    Stage stage;
    Parent scene;

    @FXML
    private TextArea reportsTextArea;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> typeComboBox;



    /**
     * Method onActionAppointmentsByContact filters a list of appointments by the contact user selects in the contact
     * combo box, and then prints these appointments to the Reports UI screen.
     * */
    @FXML
    void onActionAppointmentsByContact() {

        Contact contact = contactComboBox.getValue();
        int selectedContactID = contact.getContactID();

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> contactAppointments1 = FXCollections.observableArrayList();

        for (Appointment a : appointments){
            int id = a.getContactID();

            if (selectedContactID == id){
                contactAppointments1.add(a);
            }
        }
        reportsTextArea.setText("Contact Number: " + selectedContactID + "  " + contactAppointments1);
    }

    /**
     * Method onActionLogInByUser prints the contents of login_activity.text file to the Reports UI screen.
     * */
    @FXML
    void onActionLogInByUser() throws FileNotFoundException {

        File file = new File("src/Files/login_activity.txt");
        Scanner scanner = new Scanner(file);
        ObservableList logIn = FXCollections.observableArrayList();

        while (scanner.hasNext()){
            String log = scanner.nextLine();
            logIn.add(log);
            reportsTextArea.setText(String.valueOf(logIn));
        }
    }

    /**
     * Method onActionNumberOfAppointments gets all appointments then counts the number of each appointment and prints
     * these values to the Reports UI screen. The first lambda filters the appointments by month. The second lambda then
     * filters the first filtered list of appointments by type. This way there is a list of appointments by month then
     * by type.  Utilizing the two lambdas removes the need to utilize multiple nested for-loops to search first for all
     * appointments booked each month, then within the nested for-loop, searching for all appointments within that month
     * that are the same type, which significantly slows the program.
     * */
    @FXML
    void onActionNumberOfAppointments() {

        String month = monthComboBox.getValue();
        String type = typeComboBox.getValue();

        if (month == null || type == null){
            return;
        }

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> filteredAppointmentsByDate = appointments.filtered(d -> {
         if (d.getStart().toLocalDateTime().toLocalDate().getMonthValue() == monthComboBox.getSelectionModel().getSelectedIndex()+1)
            return true;
         return false;
         });
         ObservableList<Appointment> filteredAppointmentsByType = filteredAppointmentsByDate.filtered(t -> {
         if (t.getType().equals(type))
            return true;
         return false;
         });
        reportsTextArea.setText("The count is: " + filteredAppointmentsByType.size());
    }

    /**
     * Method onActionTotalNumberAppointments gets all appointments, counts the number of the appointments and then
     * prints that total to the Reports UI screen.
     * */
    @FXML
    void onActionTotalNumberAppointments(){
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        int count = 0;
        for (int i = 0; i < appointments.size(); i++) {
            count++;
        }
        reportsTextArea.setText("The total number of currently scheduled appointments is  " + count);
    }

    /**
     * Method onActionToCustomerList takes the user to the Customer List UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionToCustomerList(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method onActionHomeScreen takes the user to the Log In UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionHomeScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method onActionToSchedule takes the user to the Schedule UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionToSchedule(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Schedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method initialize populates the contacts combo box with contact objects, sets the visible row count on the box to
     * 5, and sets prompt text on the combo box. Method also sets items of monthComboBox to all months of the year and
     * sets typeComboBox with all appointment types.
     * @param url is the OtherDAO url that bring in all contacts.
     * @param resourceBundle is the resource bundle from OtherDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Contact> contacts = OtherDAO.getAllContacts();

        monthComboBox.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May",
              "June", "July", "August", "September", "October", "November", "December"));

        typeComboBox.setItems(FXCollections.observableArrayList(AppointmentDAO.getTypeFromAppointments()));

        monthComboBox.setVisibleRowCount(5);
        monthComboBox.setPromptText("Select Month");

        typeComboBox.setVisibleRowCount(5);
        typeComboBox.setPromptText("Select Type");

        contactComboBox.setVisibleRowCount(5);
        contactComboBox.setPromptText("Select Contact");
        contactComboBox.setItems(contacts);
    }
}


