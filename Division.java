package Model;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class Division.java creates Division objects with getter for the division ID and country ID.
 * */
public class Division {

    private int divisionID;
    private int countryID;
    private String divisionName;

    /**
     * Class Constructor
     @param divisionID is the division ID
     @param countryID is the country ID
     @param divisionName is the division name
     */
    public Division(int divisionID, int countryID, String divisionName){
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
    }

    /**
     * Method getDivisionID gets the division ID.
     * @return the division ID
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Method getDivisionName gets the division name.
     * @return the division name
     * */
    public String getDivisionName (){
        return divisionName;
    }

    /**
     * Method getCountryID gets the country ID.
     * @return the country ID
     * */
    public int getCountryID(){
        return countryID;
    }

    /**
     * Method toString overrides the toString method to format the toString to print the division ID in brackets and
     * the division name to the screen.
     * */
    @Override
    public String toString(){
        return ("[" + divisionID + "] " + divisionName);
    }
}
