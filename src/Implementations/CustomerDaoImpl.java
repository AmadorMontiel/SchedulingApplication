package Implementations;

import DataModel.Customer;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * DAO implementation of the Customer Class
 */
public class CustomerDaoImpl {

    /**
     * Returns the list of customers from the database.
     * @return the list of customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customer c = new Customer(customerID,customerName, address, postalCode, phoneNumber,
                        divisionID);
                customers.add(c);
            }
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customers;
    }

    /**
     * Adds a new customer to the database.
     * @param name the customer name.
     * @param address the customer address.
     * @param postalCode the customer postal code.
     * @param phoneNumber the customer phone number.
     * @param divisionID the customer division ID.
     */
    public static void addCustomer(String name, String address, String postalCode,
                            String phoneNumber, int divisionID) {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setString(5, "User");
            ps.setString(6, "User");
            ps.setInt(7, divisionID);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a Customer that already exists within the Database.
     * @param ID The ID of the Customer.
     * @param name The name of the Customer.
     * @param address The address of the Customer.
     * @param postalCode The postal code of the Customer.
     * @param phoneNumber The phone number of the Customer.
     * @param divisionID The divison ID of the customer.
     */
    public static void modifyCustomer(int ID, String name, String address, String postalCode,
                                      String phoneNumber, int divisionID) {
        String sql = "UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?" +
                ", Phone = ?,  Division_ID = ? WHERE Customer_ID = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionID);
            ps.setInt(6, ID);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a customer from the database based on the Customer ID.
     * @param customerID The ID of the customer to be deleted.
     */
    public static void deleteCustomer(int customerID) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a Customer based on its ID.
     * @param customerID The ID of the Customer.
     * @return The Customer based on the ID.
     */
    public static Customer getCustomerByID(int customerID) {
        Customer customer = null;

        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                customer = new Customer(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }
}
