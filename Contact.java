package Model;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class Contact.java creates Contact objects with getter for the contact ID.
 * */
public class Contact {

    private int contactID;
    private String contactName;

    /**
     * Class Constructor
     @param contactID is the contact ID of the Contact
     @param contactName is the contact name
     */
    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Method getContactID gets the contact ID.
     * @return the contact ID
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the contact ID in brackets and
     * the contact name to the screen.
     * */
    @Override
    public String toString(){
        return ("[" + contactID + "] " + contactName);
    }
}
