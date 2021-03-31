package Implementations;

import DataModel.Contact;
import javafx.collections.ObservableList;

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
        return contacts;
    }
}
