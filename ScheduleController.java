package Controller;

import Model.Appointment;
import Utilities.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class ScheduleController.java controls the Schedule UI
 * */
public class ScheduleController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentId;

    @FXML
    private TableColumn<Appointment, String> title;

    @FXML
    private TableColumn<Appointment, String> description;

    @FXML
    private TableColumn<Appointment, String> location;

    @FXML
    private TableColumn<Appointment, Integer> contact;

    @FXML
    private TableColumn<Appointment, String> type;

    @FXML
    private TableColumn<Appointment, LocalTime> startDateTime;

    @FXML
    private TableColumn<Appointment, LocalTime> endDateTime;

    @FXML
    private TableColumn<Appointment, Integer> customerId;


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
     * Method onActionDeleteAppt gets the appointment selected by the user and deletes the appointment from the data-
     * base. The method then gets the remaining appointments from the database and populates the appointment table with
     * them.
     * */
    @FXML
    void onActionDeleteAppt() {

        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        int id = appointment.getAppointmentID();
        String appointmentType = appointment.getType();


        if (appointment == null){
            return;

        }else{

            AppointmentDAO.deleteAppointment(appointment);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setContentText("Selected appointment " + "" + id + "" + " and with type " + "" + appointmentType + "" + " has been deleted.");
            alert1.showAndWait();

            appointmentTableView.setItems(AppointmentDAO.getAllAppointments());

            appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            description.setCellValueFactory(new PropertyValueFactory<>("Description"));
            location.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));

        }
    }

    /**
     * Method onActionHomeScreen takes the user to the Log In UI screen.
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
     * Method onActionNewAppt takes the user to the Create Appointment UI screen.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionNewAppt(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CreateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method onActionToReports takes the user to the Reports UI screen.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionToReports(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method onActionUpdateAppt gets selected appointment that the user selects and sends it to the Update Appointment
     * UI screen.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionUpdateAppt(ActionEvent event) throws IOException {

        if (appointmentTableView.getSelectionModel().getSelectedItem() == null) {
            return;

        }else{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointment updateAppointment = loader.getController();
            updateAppointment.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Method onActionViewWeek gets all appointments from the database, gets the start timestamp from each appointment,
     * converts it to a LocalDateTime, then checks to see if the start is within a week of today. If it is, the
     * appointment is added to a filtered list and the appointment table is then populated with this filtered
     * appointment list.
     * */
    @FXML
    void onActionViewWeek() {

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            Timestamp start = appointment.getStart();
            LocalDateTime startLDT = start.toLocalDateTime();
            LocalDateTime week = LocalDateTime.now().plusDays(7);
            LocalDateTime now = LocalDateTime.now();

            if (startLDT.isBefore(week) && startLDT.isAfter(now)) {
                weeklyAppointments.add(appointment);

                appointmentTableView.setItems(weeklyAppointments);

                appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
                title.setCellValueFactory(new PropertyValueFactory<>("Title"));
                description.setCellValueFactory(new PropertyValueFactory<>("Description"));
                location.setCellValueFactory(new PropertyValueFactory<>("Location"));
                contact.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
                type.setCellValueFactory(new PropertyValueFactory<>("Type"));
                startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
                endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
                customerId.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            }
        }
    }

    /**
     * Method onActionViewMonth gets all appointments from the database, gets the start timestamp from each appointment,
     * converts it to a LocalDateTime, then checks to see if the start is within a month of today. If it is, the
     * appointment is added to a filtered list and the appointment table is then populated with this filtered
     * appointment list.
     * */
    @FXML
    void onActionViewMonth() {

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            Timestamp start = appointment.getStart();
            LocalDateTime startLDT = start.toLocalDateTime();
            LocalDateTime month = LocalDateTime.now().plusDays(30);
            LocalDateTime now = LocalDateTime.now();

            if (startLDT.isBefore(month) && startLDT.isAfter(now)) {
                monthlyAppointments.add(appointment);

                appointmentTableView.setItems(monthlyAppointments);

                appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
                title.setCellValueFactory(new PropertyValueFactory<>("Title"));
                description.setCellValueFactory(new PropertyValueFactory<>("Description"));
                location.setCellValueFactory(new PropertyValueFactory<>("Location"));
                contact.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
                type.setCellValueFactory(new PropertyValueFactory<>("Type"));
                startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
                endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
                customerId.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            }
        }
    }

    /**
     * Method initialize populates the appointment table with the current week of appointments from the database.
     * @param url is the AppointmentDAO url that bring in all customers
     * @param resourceBundle is the resource bundle from AppointmentDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            Timestamp start = appointment.getStart();
            LocalDateTime startLDT = start.toLocalDateTime();
            LocalDateTime week = LocalDateTime.now().plusDays(7);
            LocalDateTime now = LocalDateTime.now();

            if (startLDT.isBefore(week) && startLDT.isAfter(now)) {
                weeklyAppointments.add(appointment);

                appointmentTableView.setItems(weeklyAppointments);

                appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
                title.setCellValueFactory(new PropertyValueFactory<>("Title"));
                description.setCellValueFactory(new PropertyValueFactory<>("Description"));
                location.setCellValueFactory(new PropertyValueFactory<>("Location"));
                contact.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
                type.setCellValueFactory(new PropertyValueFactory<>("Type"));
                startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
                endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
                customerId.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            }
        }

    }
}
