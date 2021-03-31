package Implementations;

import DataModel.Customer;
import DataModel.User;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DAO implementation of the Customer Class
 */
public class CustomerDaoImpl {

    /**
     * List of customers stored as an observable list
     */
    private static ObservableList<Customer> customers;
    public static int currentCustomerID;

    /**
     * Returns the list of customers from the database.
     * @return the list of customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        customers = FXCollections.observableArrayList();
        currentCustomerID = 1;
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
                currentCustomerID++;
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

        String sql = "INSERT INTO customers VALUES(?, ?, ?, ?, ?" +
                ", ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, currentCustomerID);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phoneNumber);
            ps.setObject(6, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(7, "User");
            ps.setObject(8, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(9, "User");
            ps.setInt(10, divisionID);
            ps.executeUpdate();
            currentCustomerID++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void modifyCustomer(Customer customer) {

    }
}