package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utilities.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class CreateAppointment.java controls the Create Appointment UI
 * */
public class CreateAppointment implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private ComboBox<Customer> customerIDComboBox;

    @FXML
    private ComboBox<User> userIDComboBox;


    /**
     * Method onActionBackToSchedule takes the user back to the Schedule UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionBackToSchedule(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Schedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method onActionCustomerList takes the user to the Customer List UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionCustomerList(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method onActionSaveAppointment sends the values entered into the textfields/comboboxes to the AppointmentDAO.createAppointment
     * method to be saved in the database after checking that values user enters are consistent with Strings and that
     * all attributes have entered or selected values. Method also checks that the created appointment is within the
     * company's Eastern Standard Time working hours and that there is not an overlapping duplicate appointments for this
     * If the appointment time is outside the working hours, the user
     * is alerted that the appointment was not saved.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException {

        try {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalTime startTime = startTimeComboBox.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            Customer customer = customerIDComboBox.getValue();
            Contact contact = contactComboBox.getValue();
            User user = userIDComboBox.getValue();
            int customerID = customer.getCustomerID();
            int contactID = contact.getContactID();
            int userID = user.getUserID();

            String empty = "";

            if (title.equals(empty)){

                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setContentText("Please enter a title.");
                alert3.showAndWait();
                return;

            }else if (description.equals(empty)){

                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setContentText("Please enter a description.");
                alert4.showAndWait();
                return;

            }else if (location.equals(empty)){

                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setContentText("Please enter a location.");
                alert5.showAndWait();
                return;

            }else if (type.equals(empty)) {

                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setContentText("Please enter a type.");
                alert6.showAndWait();
                return;

            }else if (startDate.isBefore(endDate) || startDate.isAfter(endDate)) {

                Alert alert12 = new Alert(Alert.AlertType.ERROR);
                alert12.setContentText("Start Date and End Date should be the same.");
                alert12.showAndWait();
                return;

            }else if (endTime.isBefore(startTime)){

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Start time must be before End time.");
                alert2.showAndWait();
                return;

            }

            LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);
            LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);

            LocalTime estStart = LocalTime.of(8, 00);
            LocalTime estEnd = LocalTime.of(22, 00);

            LocalDateTime startEST = AllAboutTimeZones.getTimeInESTTimeZone(startLDT);
            LocalDateTime endEST = AllAboutTimeZones.getTimeInESTTimeZone(endLDT);
            LocalTime startESTTime = startEST.toLocalTime();
            LocalTime endESTTime = endEST.toLocalTime();

            ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
            for (Appointment a: appointments){
                int thisCustomerID = a.getCustomerID();
                Timestamp start = a.getStart();
                Timestamp end = a.getEnd();
                int thisContactID = a.getContactID();

                if (customerID == thisCustomerID && startLDT.isAfter(start.toLocalDateTime()) && startLDT.isBefore(end.toLocalDateTime())){

                    Alert alert7 = new Alert(Alert.AlertType.WARNING);
                    alert7.setContentText("This customer has an overlapping appointment already scheduled for this" +
                            " date and time.");
                    alert7.showAndWait();
                    return;

                }else if (startLDT.isAfter(start.toLocalDateTime()) && startLDT.isBefore(end.toLocalDateTime()) && contactID
                 == thisContactID){

                    Alert alert8 = new Alert(Alert.AlertType.WARNING);
                    alert8.setContentText("The contact already has an appointment booked with another customer at the" +
                            " same date and time.");
                    alert8.showAndWait();
                    return;
                }
            }
            if (startESTTime.isAfter(estStart) && endESTTime.isBefore(estEnd)) {

                Timestamp newStart = Timestamp.valueOf(startLDT);
                Timestamp newEnd = Timestamp.valueOf(endLDT);

                AppointmentDAO.createAppointment(title, description, location, type, newStart, newEnd, customerID, userID, contactID);

            }else{

                Alert alert9 = new Alert(Alert.AlertType.WARNING);
                alert9.setContentText("Appointment time is outside of Eastern Standard Time working hours. Please update" +
                        " the start and end times for the appointment.");
                alert9.showAndWait();
                return;
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Schedule.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NumberFormatException e){

            Alert alert10 = new Alert(Alert.AlertType.ERROR);
            alert10.setContentText("Please enter a string for title, location, description, and type.");
            alert10.showAndWait();

        }catch (NullPointerException n){

            Alert alert11 = new Alert(Alert.AlertType.ERROR);
            alert11.setContentText("Start Date, End Date, Start Time, End Time, User, Contact, or Customer does" +
                    " not have a value selected. Please " +
                    "enter a value.");
            alert11.showAndWait();
            return;
        }
    }

    /**
     * Method initialize populates the startTimeComboBox, endTimeComboBox, contactComboBox, customerComboBox, and
     * userComboBox with startTime, endTime, contact, customer, and user objects. This method also limits the visible
     * row count on the combo boxes to 5 and sets prompt text on each of these combo boxes.
     * @param url is the CustomerDAO, OtherDAO and UserDAO urls that bring in all customers, contacts and users.
     * @param resourceBundle is the resource bundle from CustomerDAO, OtherDAO and UserDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
        ObservableList<Contact> contacts = OtherDAO.getAllContacts();
        ObservableList<User> users = UserDAO.getAllUsers();

        LocalTime startTime = LocalTime.of(0, 00);
        LocalTime endTime = LocalTime.of(23, 00);

        while (startTime.isBefore(endTime)){
            startTimeComboBox.getItems().add(startTime);
            endTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        startTimeComboBox.setVisibleRowCount(5);
        endTimeComboBox.setVisibleRowCount(5);
        contactComboBox.setVisibleRowCount(5);
        customerIDComboBox.setVisibleRowCount(5);
        userIDComboBox.setVisibleRowCount(5);

        startTimeComboBox.setPromptText("Pick Start Time");
        endTimeComboBox.setPromptText("Pick End Time");
        customerIDComboBox.setPromptText("Select Customer");
        contactComboBox.setPromptText("Select Contact");
        userIDComboBox.setPromptText("Select User");

        customerIDComboBox.setItems(customers);
        contactComboBox.setItems(contacts);
        userIDComboBox.setItems(users);
    }
}
