package Model;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class Country.java creates country objects with getter for the country ID and overrides the toString.
 * */
public class Country {

    private int countryID;
    private String countryName;

    /**
     * Class Constructor
     @param countryID is the country ID of the Country
     @param countryName is the country name
     */
    public Country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Method getCountryID gets the country ID.
     * @return the country ID
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the country ID in brackets and
     * the country name to the screen.
     * */
    @Override
    public String toString(){
        return ("[" + countryID + "] " + countryName);
    }
}
