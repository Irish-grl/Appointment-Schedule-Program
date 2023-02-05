package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import Utilities.CustomerDAO;
import Utilities.OtherDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class UpdateCustomer.java controls the Update Customer UI screen.
 * */
public class UpdateCustomer implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<Division> stateComboBox;

    @FXML
    private ComboBox<Country> countryComboBox;


    /**
     * Method sendCustomer gets the customer that user selected from the Customer List UI screen and sets the textfields
     * and combo boxes with this data.
     * @param customer is the customer object that user selected in the Customer List.
     * */
    @FXML
    public void sendCustomer(Customer customer){

        customerIDTextField.setText(String.valueOf(customer.getCustomerID()));
        nameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());
        int divisionID = customer.getDivisionID();

        ObservableList<Division> divisions = OtherDAO.getAllDivisions();
        ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();
        Country country = getCountry(customer);
        int countryID = country.getCountryID();

        countryComboBox.setValue(country);

        for (Division division : divisions){
            division.getCountryID();
            division.getDivisionID();

            if (division.getDivisionID() == divisionID){
                stateComboBox.setValue(division);
            }

            if(division.getCountryID() == countryID){
                filteredDivisions.add(division);
                stateComboBox.setItems(filteredDivisions);
            }
        }
    }

    /**
     * Method getCountry takes the parameter Customer and gets the country ID from the Customer. Then the method gets
     * the country that matches the country ID.
     * @param customer is the customer to get the country ID from.
     * */
    public Country getCountry(Customer customer){

        int countryID = customer.getCountryID();
        Country country;
        ObservableList<Country> countries = OtherDAO.getAllCountries();

        for (int i = 0; i < countries.size(); i++) {
            country = countries.get(i);

            if (country.getCountryID() == countryID) {
                return country;
            }
        }
        return null;
    }

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
     * Method onActionSaveUpdateCustomer gets the data that the user entered in the textfields and combo boxes, creates
     * a new customer object, and sends the customer object to the CustomerDAO.updateCustomer method to be saved.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionSaveUpdateCustomer(ActionEvent event) throws IOException {

        try {
            int customerID = Integer.parseInt(customerIDTextField.getText());
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            Division division = stateComboBox.getValue();
            int divisionID = division.getDivisionID();
            String divisionName = division.getDivisionName();
            int divisionCountryID = division.getCountryID();

            String empty = "";

            if (name.equals(empty)) {

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Entry Error");
                alert1.setContentText("Please enter a customer name.");
                alert1.showAndWait();
                return;

            } else if (address.equals(empty)) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Entry Error");
                alert2.setContentText("Please enter a customer address.");
                alert2.showAndWait();
                return;

            } else if (postalCode.equals(empty)) {

                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Entry Error");
                alert3.setContentText("Please enter a postal code.");
                alert3.showAndWait();
                return;

            } else if (phone.equals(empty)) {

                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Entry Error");
                alert4.setContentText("Please enter a phone number.");
                alert4.showAndWait();
                return;

            } else if (division.equals(empty)) {

                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setContentText("Please select a state/province.");
                alert5.showAndWait();
                return;

            }

            Customer customer = new Customer(customerID, name, address, postalCode, phone, divisionID, divisionName, divisionCountryID);
            CustomerDAO.updateCustomer(customer);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }catch (NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a string for name, address, postal code, and phone number.");
            alert.showAndWait();
            return;

        }catch (NullPointerException n){

            Alert alert7 = new Alert(Alert.AlertType.ERROR);
            alert7.setContentText("State or Country value is empty, and both must have a value.");
            alert7.showAndWait();
            return;
        }
    }

    /**
     * Method onActionCountryComboBox gets the selected country from the country combo box, compares it's ID with the
     * country ID in the division and then filters the divisions based off of this country ID.
     * */
    @FXML
    void onActionCountryComboBox(){

        ObservableList<Division> divisions = OtherDAO.getAllDivisions();
        ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();

        Country country = countryComboBox.getValue();
        int thisCountryID = country.getCountryID();

        for (int i = 0; i < divisions.size(); i++) {
            Division division = divisions.get(i);
            int countryID = division.getCountryID();

            if(thisCountryID == countryID){
                filteredDivisions.add(division);
                stateComboBox.setItems(filteredDivisions);
            }
        }
    }

    /**
     * Method initialize populates the state combo box and the country combo box with division and country objects. The
     * method also sets the visible rows to 5.
     * @param url is the OtherDAO url.
     * @param resourceBundle is the resource bundle from OtherDAO.
     * */
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

       ObservableList<Country> countries = OtherDAO.getAllCountries();
       ObservableList<Division> divisions = OtherDAO.getAllDivisions();

       stateComboBox.setItems(divisions);
       stateComboBox.setVisibleRowCount(5);
       countryComboBox.setItems(countries);
       countryComboBox.setVisibleRowCount(5);

   }
}