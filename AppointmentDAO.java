package Utilities;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class AppointmentDAO.java creates, reads, updates and deletes appointments from the database.
 * */
public class AppointmentDAO {

    private static final String INSERT = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM appointments";
    private static final String SELECT = "SELECT * FROM appointments WHERE Appointment_ID = ?";
    private static final String UPDATE = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";

    /**
     * Method createAppointment inserts an appointment in the database
     * @param title is the appointment title
     * @param description is the appointment description
     * @param location is the appointment location
     * @param type is the appointment type
     * @param start is the start time
     * @param end is the end time
     * @param customerID is the customer ID
     * @param userID is the user ID
     * @param contactID is the contact ID
     * */
    public static void createAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID){

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(INSERT);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method getAllAppointments gets all appointments
     * @return appointments
     * */
    public static ObservableList getAllAppointments(){

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(SELECT_ALL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");
                int userID = rs.getInt("User_ID");

                Appointment appointment1 = new Appointment(id, title, description, location, type, start, end, customerID, contactID, userID);
                appointments.add(appointment1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Method getAppointment gets a single appointment
     * @return appointment
     * */
    public static ObservableList<Appointment> getAppointment(){

        ObservableList<Appointment> appointment = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(SELECT);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int contactID = rs.getInt("Contact_ID");
                int userID = rs.getInt("User_ID");

                Appointment appointment1 = new Appointment(id, title, description, location, type, start, end, customerID, contactID, userID);
                appointment.add(appointment1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointment;
    }

    /**
     * Method updateAppointment updates an appointment in the database
     * @param appointment is the appointment to update in the database
     * */
    public static void updateAppointment(Appointment appointment){

        int appointmentID = appointment.getAppointmentID();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        Timestamp start = appointment.getStart();
        Timestamp end = appointment.getEnd();
        int customerID = appointment.getCustomerID();
        int contactID = appointment.getContactID();
        int userID = appointment.getUserID();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(UPDATE);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, contactID);
            ps.setInt(9, userID);
            ps.setInt(10, appointmentID);

            ps.execute();

            System.out.println("Rows affected = " + ps.getUpdateCount());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method deleteAppointment deletes an appointment from the database
     * @param appointment is the appointment to delete
     * */
    public static void deleteAppointment(Appointment appointment){

        int id = appointment.getAppointmentID();

        try {

            final String DELETE = "DELETE FROM appointments WHERE Appointment_ID = " + id;
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(DELETE);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method getTypeFromAppointments gets the type only from the appointments table in the database and returns an
     * arraylist of the types.
     * */
    public static ArrayList<String> getTypeFromAppointments(){

        ArrayList<String> types = new ArrayList<>();
        final String SELECTTYPES = "SELECT DISTINCT Type FROM appointments";
        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(SELECTTYPES);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String type = rs.getString("Type");

                types.add(type);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return types;
    }

}
