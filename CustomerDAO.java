package Utilities;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class CustomerDAO.java creates, reads, updates and deletes customers from the database.
 * */
public class CustomerDAO{

    /**
     * Method createCustomer inserts a new customer into the database
     * @param name is the customer name
     * @param address is the address
     * @param postal is the postal code
     * @param phone is the phone number
     * @param divisionId is the state/province ID number
     * */
    public static void createCustomer(String name, String address, String postal, String phone, int divisionId){

        final String INSERT = "INSERT INTO customers(Customer_Name, Address, Postal_Code, " +
                "Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(INSERT);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);

            ps.execute();

            if (ps.getUpdateCount() > 0){
                System.out.println("Rows affected = " + ps.getUpdateCount());
            }else{
                System.out.println("No rows affected.");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method getAllCustomers gets all customers
     * @return all customers
     * */
    public static ObservableList getAllCustomers(){

        final String SELECT_ALL = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, " +
                "customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division_ID, " +
                "first_level_divisions.Division, first_level_divisions.COUNTRY_ID " +
                "FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(SELECT_ALL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                Customer c = new Customer(id, name, address, postal, phone, divisionId, divisionName, countryID);
                allCustomers.add(c);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Method updateCustomer updates a customer in the database
     * @param customer is the customer to update
     * */
    public static void updateCustomer(Customer customer){

        final String UPDATE = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?";

        int customerID = customer.getCustomerID();
        String name = customer.getCustomerName();
        String address = customer.getAddress();
        String postal = customer.getPostalCode();
        String phone = customer.getPhone();
        int divisionID = customer.getDivisionID();

        try {
            DBQuery.getPreparedStatement();
            PreparedStatement ps = DBConnector.getConnection().prepareStatement(UPDATE);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);
            ps.execute();

            System.out.println("Rows affected = " + ps.getUpdateCount());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method deleteCustomer first deletes appointments that a customer is associated with, then deletes the customer
     * @param customer is the customer to delete
     * @param id is the id of the customer to delete
     * */
    public static void deleteCustomer(Customer customer, int id){

        int customerID = customer.getCustomerID();
        if (id == customerID) {
            try {
                final String DELETEAPPTS = "DELETE FROM appointments WHERE Customer_ID = " + id;
                DBQuery.getPreparedStatement();
                PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETEAPPTS);
                preparedStatement.execute();

                final String DELETE ="DELETE FROM customers WHERE Customer_ID = " + id;
                PreparedStatement ps = DBConnector.getConnection().prepareStatement(DELETE);
                ps.execute();

                if (ps.getUpdateCount() > 0) {
                    System.out.println("Rows affected = " + ps.getUpdateCount());
                } else {
                    System.out.println("Customer was not deleted.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
