package Utilities;

import Model.Contact;
import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */

/**
 * Class OtherDAO.java reads from the database to get all divisions, countries, and contacts.
 * */
public class OtherDAO {

    private static final String DIVISIONS = "SELECT * FROM first_level_divisions";
    private static final String COUNTRIES = "SELECT * FROM countries";
    private static final String CONTACTS = "SELECT * FROM contacts";

    /**
     * Method getAllDivisions gets all divisions
     * @return all divisions
     * */
    public static ObservableList<Division> getAllDivisions(){

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(DIVISIONS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");

                Division d = new Division(divisionID, countryID, divisionName);
                divisions.add(d);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return divisions;
    }

    /**
     * Method getAllCountries gets all divisions
     * @return all countries
     * */
    public static ObservableList<Country> getAllCountries(){

        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(COUNTRIES);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryID, countryName);
                countries.add(c);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return countries;
    }

    /**
     * Method getAllContacts gets all divisions
     * @return all contacts
     * */
    public static ObservableList<Contact> getAllContacts(){

        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(CONTACTS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contact c = new Contact(contactID, contactName);
                contacts.add(c);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }
}
