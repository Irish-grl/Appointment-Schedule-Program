package Controller;

import Model.Customer;
import Utilities.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * Class CustomerList.java controls the Customer List UI
 * */
public class CustomerList implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customerListTableView;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, String> address;

    @FXML
    private TableColumn<Customer, String> stateProvince;

    @FXML
    private TableColumn<Customer, String> postalCode;

    @FXML
    private TableColumn<Customer, String> phone;



    /**
     * Method onActionAddCustomer takes the user to the Create Customer UI screen
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CreateCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method onActionBackToSchedule takes the user to the Schedule UI screen
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
     * Method onActionToReports takes the user to the Reports UI screen
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
     * Method onActionDeleteCustomer gets the selected customer in the list and deletes this customer from the
     * database and the list.
     * @param event is the button event initiating the deletion of the customer.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        if (customerListTableView.getSelectionModel().getSelectedItem().equals(null)) {
            return;
        }else{
            Customer customer = customerListTableView.getSelectionModel().getSelectedItem();
            int id = customer.getCustomerID();

            CustomerDAO.deleteCustomer(customer, id);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Customer with ID of " + id + " and any currently scheduled appointments for this " +
                    "customer have been deleted from the database.");
            alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Method onActionModifyCustomer gets the selected customer's data from the Customer List and sends this data to
     * the Update Customer UI screen.
     * @param event is the button event initiating the screen change.
     * @throws IOException if load error occurs.
     * */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        if(customerListTableView.getSelectionModel().getSelectedItem() == null){

            return;
        }else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomer updateCustomer = loader.getController();
            updateCustomer.sendCustomer(customerListTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Method initialize populates the Customer List table with customers.
     * @param url is the CustomerDAO url that bring in all customers
     * @param resourceBundle is the resource bundle from CustomerDAO.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();

        customerListTableView.setItems(customers);

        customerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        stateProvince.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));

    }
}
