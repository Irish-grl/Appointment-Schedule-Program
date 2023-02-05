package Model;

import java.sql.Timestamp;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class Appointment.java creates appointment objects with getters and setters for the fields in the constructor.
 * */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int contactID;
    private int userID;


    /**
     *Class Constructor
     @param id is the appointment ID
     @param title is appointment title
     @param description is the stock to set
     @param location is the minimum on hand to set
     @param type is the maximum to have on hand
     @param start is the start time of appointment
     @param end is the end time of the appointment
     @param customerID is the customer ID of customer attached to the appointment
     @param contactID is the contact ID of the Contact attached to the appointment
     @param userID is the user ID of the User attached to the appointment
     */
    public Appointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int contactID, int userID) {
        appointmentID = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
    }

    /**
     * Method getAppointmentID gets the appointment ID.
     * @return the appointment ID
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * Method getTitle gets the title.
     * @return the title.
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Method getDescription gets the description.
     * @return the description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Method getLocation gets the location.
     * @return the location
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Method getType gets the type.
     * @return the type
     * */
    public String getType() {
        return type;
    }

    /**
     * Method getStart gets the start time/date.
     * @return the start date/time
     * */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Method getEnd gets the end time/date.
     * @return the end date/time
     * */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Method setTitle sets the title.
     * @param title is the title to set
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method setDescription sets the description.
     * @param description is the description to set
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method setLocation sets the location.
     * @param location is the location to set
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method setType sets the type.
     * @param type is the type to set
     * */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method setStart sets the start time/date.
     * @param start is the start time/date to set
     * */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Method setEnd sets the end time/date.
     * @param end is the end time/date to set
     * */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Method getCustomerID gets and returns the customer ID.
     * @return customer ID
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Method setCustomerID sets the customer ID.
     * @param customerID is the customer ID to set
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Method setContactID sets the contact ID.
     * @param contactID is the contact ID to set
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Method setUserID sets the user ID.
     * @param userID is the user ID to set
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Method getContactID gets the contact ID.
     * @return the contact ID
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Method getUserID gets the user ID.
     * @return the user ID
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the appointment ID in brackets and
     * the type to the screen.
     * */
    @Override
    public String toString(){
        return (title + " " + " " + description + " " +  " " + location + " " +  " "
                + type + " " +  " " + start + " " +  " " + end + " " + " " + customerID);
    }
}
