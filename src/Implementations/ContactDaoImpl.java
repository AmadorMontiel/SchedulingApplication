package Implementations;

import DataModel.Contact;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO Implementation of the Contact Class
 */
public class ContactDaoImpl {

    /**
     * The List of Contacts from the DB
     */
    private static ObservableList<Contact> contacts;

    /**
     * Returns the list of Contacts
     * @return the list of Contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        contacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                Contact c = new Contact(contactID,name);
                contacts.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contacts;
    }
    public static Contact getContactByID(int contactID) {
        Contact contact = null;

        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = " + contactID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                contact = new Contact(id,name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }
}
