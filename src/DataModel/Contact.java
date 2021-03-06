package DataModel;

/**
 * Represents a contact of the company in the database.
 */
public class Contact {

    /**
     * The ID of the Contact
     */
    private int contactID;
    /**
     * The name of the Contact
     */
    private String name;

    /**
     * Constructor for contacts.
     * @param contactID The ID of the contact.
     * @param name The name of the contact.
     */
    public Contact(int contactID, String name){
        this.contactID = contactID;
        this.name = name;
    }

    /**
     * Gets the ID of the contact
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Gets the name of the Contact
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the ID of the contact
     * @param contactID contactID to set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Sets the name of the contact
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overridden toString method for display purposes.
     * @return The ID and Name.
     */
    @Override
    public String toString() {
        return "ID: " + contactID + " Name: " + name;
    }
}
