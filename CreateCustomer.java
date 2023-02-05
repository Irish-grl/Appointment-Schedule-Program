package Controller;

import Model.*;
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
 * Class CreateCustomer.java controls the Create Customer UI screen.
 * */
public class CreateCustomer implements Initializable {

    Stage stage;
    Parent scene;

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
     * Method onActionSaveCreateCustomer gets the values entered by the user in the textfields and combo boxes, then
     * sends this data to the CustomerDAO.createCustomer method to be saved in the database.
     * @param event is the button event initiating the screen change to the Customer List UI screen.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionSaveCreateCustomer(ActionEvent event) throws IOException {

        try {
            String customerName = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            Division division = stateComboBox.getValue();
            int divisionID = division.getDivisionID();
            int countryID = division.getCountryID();

            String empty = "";

            if (customerName.isEmpty()) {

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Please enter a customer name.");
                alert1.showAndWait();
                return;

            } else if (address.isEmpty()) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Please enter a customer address.");
                alert2.showAndWait();
                return;

            } else if (postalCode.isEmpty()) {

                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setContentText("Please enter a postal code.");
                alert3.showAndWait();
                return;

            } else if (phone.isEmpty()) {

                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setContentText("Please enter a phone number.");
                alert4.showAndWait();
                return;

            } else if (division.equals(empty)) {

                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setContentText("Please select a state/province.");
                alert5.showAndWait();
                return;

            }

            CustomerDAO.createCustomer(customerName, address, postalCode, phone, divisionID);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a string for customer name, address, postal code, and phone number.");
            alert.showAndWait();
            return;

        }catch (NullPointerException n) {

            Alert alert7 = new Alert(Alert.AlertType.ERROR);
            alert7.setContentText("State or Country value is empty.");
            alert7.showAndWait();
            return;
        }
    }


    /**
     * Method onActionCountryComboBox gets the selected country from the country combo box, compares it's ID with the
     * country ID in the division and then filters the divisions based off of this country ID.
     * */
    @FXML
    void onActionCountryComboBox() {

            ObservableList<Division> divisions = OtherDAO.getAllDivisions();
            ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();

            Country country = countryComboBox.getValue();
            int thisCountryID = country.getCountryID();

            for (int i = 0; i < divisions.size(); i++) {
                Division division = divisions.get(i);
                int countryID = division.getCountryID();

                if (thisCountryID == countryID) {
                    filteredDivisions.add(division);
                    stateComboBox.setItems(filteredDivisions);
                }
            }
    }

    /**
     * Method initialize populates the country combo box and the state combo box with country and state objects. This
     * method also sets the limit of visible rows to 5 on these combo boxes and sets a prompt text on them as well.
     * @param url is the OtherDAO url that gets all countries and divisions.
     * @param resourceBundle is the resource bundle from OtherDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = OtherDAO.getAllCountries();

        countryComboBox.setVisibleRowCount(5);
        countryComboBox.setPromptText("Select Country");
        countryComboBox.setItems(countries);

        stateComboBox.setPromptText("Choose Country Before State");


    }
}
