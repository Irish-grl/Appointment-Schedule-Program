package Controller;

import Model.*;
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
 * Class UpdateAppointment.java controls the Update Appointment UI screen.
 * */
public class UpdateAppointment implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentIDTextField;

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
     * Method sendAppointment brings in the user-selected appointment data from the Schedule UI screen and populates
     * the textfields, combo boxes, and date pickers with the data.
     * */
    @FXML
    public void sendAppointment(Appointment appointment) {

        Timestamp start = appointment.getStart();
        Timestamp end = appointment.getEnd();

        LocalDateTime startDateTime = start.toLocalDateTime();
        LocalDateTime endDateTime = end.toLocalDateTime();

        LocalDate newStartDate = startDateTime.toLocalDate();
        LocalTime newstartTime = startDateTime.toLocalTime();

        LocalDate newEndDate = endDateTime.toLocalDate();
        LocalTime newEndTime = endDateTime.toLocalTime();

        appointmentIDTextField.setText(String.valueOf(appointment.getAppointmentID()));
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        locationTextField.setText(appointment.getLocation());
        typeTextField.setText(appointment.getType());
        startDatePicker.setValue(newStartDate);
        startTimeComboBox.setValue(newstartTime);
        endDatePicker.setValue(newEndDate);
        endTimeComboBox.setValue(newEndTime);

        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
        for (int i = 0; i < customers.size(); i ++) {
            Customer customer = customers.get(i);
            int customerID = customer.getCustomerID();
            int customerID1 = appointment.getCustomerID();

            if (customerID == customerID1) {
                customerIDComboBox.setValue(customer);
            }
        }

        ObservableList<Contact> contacts = OtherDAO.getAllContacts();
        for (int i = 0; i < contacts.size(); i ++) {
            Contact contact = contacts.get(i);
            int contactID1 = contact.getContactID();
            int contactID = appointment.getContactID();

            if (contactID1 == contactID) {
                contactComboBox.setValue(contact);
            }
        }

        ObservableList<User> users = UserDAO.getAllUsers();
        for (int i = 0; i < users.size(); i ++) {
            User user = users.get(i);
            int userID1 = user.getUserID();
            int userID = appointment.getUserID();

            if(userID1 == userID) {
                userIDComboBox.setValue(user);
            }
        }
    }

    /**
     * Method onActionBackToSchedule takes the user to the Schedule UI screen.
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
     * Method onActionCustomerList takes the user to the Customer List UI screen.
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
     * Method onActionSaveUpdateAppt gets the data the user enters in the text fields, combo boxes, and date pickers, then
     * checks the appointment time against the Eastern Standard Time hours to make sure the time is not out of the
     * working hours range and that the customer doesn't have an overlapping appointment for the same day/time. If the
     * start/end times of the appointment are not out of range and there are no duplicate appointments, then the
     * appointment is saved to the database.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionSaveUpdateAppt(ActionEvent event) throws IOException {

        try {
            int appointmentID = Integer.parseInt(appointmentIDTextField.getText());
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

            }else if (type.equals(empty)){

                Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setContentText("Please enter a type.");
                alert6.showAndWait();
                return;

            }else if (startDate.isAfter(endDate) || startDate.compareTo(endDate) < 0){

                Alert alert7 = new Alert(Alert.AlertType.ERROR);
                alert7.setContentText("Start Date should be the same as End date.");
                alert7.showAndWait();
                return;

            }else if (endTime.isBefore(startTime)){

                Alert alert10 = new Alert(Alert.AlertType.ERROR);
                alert10.setContentText("Start time must be before End time.");
                alert10.showAndWait();
                return;

            }

            int customerID = customer.getCustomerID();
            int contactID = contact.getContactID();
            int userID = user.getUserID();

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

                    Alert alert8 = new Alert(Alert.AlertType.WARNING);
                    alert8.setContentText("This customer has an overlapping appointment already scheduled for this" +
                            " date and time.");
                    alert8.showAndWait();
                    return;

                }else if (startLDT.isAfter(start.toLocalDateTime()) && startLDT.isBefore(end.toLocalDateTime()) && contactID
                        == thisContactID) {

                    Alert alert9 = new Alert(Alert.AlertType.WARNING);
                    alert9.setContentText("The contact already has an appointment booked with another customer at the" +
                            " same date and time.");
                    alert9.showAndWait();
                    return;
                }
            }
            if (startESTTime.isAfter(estStart) && endESTTime.isBefore(estEnd)) {

                Timestamp newStart = Timestamp.valueOf(startLDT);
                Timestamp newEnd = Timestamp.valueOf(endLDT);

                Appointment appointment = new Appointment(appointmentID, title, description, location, type, newStart, newEnd, customerID, contactID, userID);
                AppointmentDAO.updateAppointment(appointment);

            }else{
                Alert alert12 = new Alert(Alert.AlertType.WARNING);
                alert12.setContentText("Appointment time is outside of Eastern Standard Time working hours. Please update" +
                " the start end end times for the appointment.");
                alert12.showAndWait();
                return;
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Schedule.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NumberFormatException e){

            Alert alert13 = new Alert(Alert.AlertType.ERROR);
            alert13.setContentText("Please enter a string for title, description, location and type.");
            alert13.showAndWait();
            return;

        }catch (NullPointerException n){

            Alert alert14 = new Alert(Alert.AlertType.ERROR);
            alert14.setContentText("The Start Date, End Date, Start Time or End time do not have a value.");
            alert14.showAndWait();
            return;

        }
    }

    /**
     * Method initialize populates the startTimeComboBox, endTimeComboBox, contactComboBox, customerComboBox, and
     * userComboBox with start, end, contact, customer, and user objects.
     * @param url is the OtherDAO, CustomerDAO, and UserDAO urls that bring in all customers, contacts and users.
     * @param resourceBundle is the resource bundles from OtherDAO, CustomerDAO, and UserDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Contact> contacts = OtherDAO.getAllContacts();
        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
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

        contactComboBox.setItems(contacts);
        customerIDComboBox.setItems(customers);
        userIDComboBox.setItems(users);
    }
}
