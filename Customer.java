package Model;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class Customer.java creates customer objects with getters and setters for the fields in the constructor.
 * */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Class Constructor
     * @param id is the customer ID
     * @param customerName is the customer name
     * @param address is the customer address
     * @param postalCode is the customer postal code
     * @param phone is the customer phone number
     * @param divisionID is the ID number for the state/province the customer lives in
     * @param divisionName is the name of the state/province
     * */
    public Customer(int id, String customerName, String address, String postalCode, String phone, int divisionID, String divisionName, int countryID) {
        customerID = id;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Method getCustomerID gets the customer ID.
     * @return the customer ID
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Method getCustomerName gets the customer name.
     * @return the customer name
     * */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Method setCustomerName sets the customer name.
     * @param customerName is the customer name to set
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Method getAddress gets the customer address.
     * @return the customer address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Method setAddress sets the customer name.
     * @param address is the customer address to set
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method getPostalCode gets the customer postal code.
     * @return the customer postal code
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method setPostalCode sets the customer postal code.
     * @param postalCode is the customer postal code to set
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method getPHone gets the customer phone
     * @return the phone
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Method setPhone sets the customer phone.
     * @param phone is the customer phone to set
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Method getDivisionID gets the division ID
     * @return the division ID
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Method setDivisionID sets the division ID.
     * @param divisionID is the division ID to set
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Method getDivisionName gets the division ID
     * @return the division name
     * */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * Method setDivisionName sets the division ID.
     * @param divisionName is the division ID to set
     * */
    public void setDivisionName(String divisionName){
        this.divisionName = divisionName;
    }

    /**
     * Method getCountryID gets the division ID
     * @return the country name
     * */
    public int getCountryID(){
        return countryID;
    }

    /**
     * Method setCountryID sets the division ID.
     * @param countryID is the country ID to set
     * */
    public void setCountryID(int countryID){
        this.countryID = countryID;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the customer ID in brackets and
     * the customer name to the screen.
     * */
    @Override
    public String toString(){
        return ("[" + customerID + "] " + customerName );
    }
}
