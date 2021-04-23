package DataModel;

/**
 * Represents a customer of the company in the database.
 */
public class Customer {

    /**
     * The ID of the customer.
     */
    private int id;
    /**
     * The name of the customer.
     */
    private String name;
    /**
     * The address of the customer.
     */
    private String address;
    /**
     * The postalcode of the customer.
     */
    private String postalCode;
    /**
     * The phone number of the customer.
     */
    private String phoneNumber;
    /**
     * The associated division ID of the customer.
     */
    private int divisionID;

    /**
     * Customer constructor.
     * @param id The ID of the customer.
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phoneNumber The phone number of the custoner.
     * @param divisionID The division ID of the customer.
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * Additional constructor used in getCustomerByID method.
     * @param id The ID of the customer.
     * @param name The name of the customer.
     */
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID of the customer.
     * @return the ID of the customer.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the customer.
     * @param id The ID to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer.
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the customer.
     * @return The address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address The address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the postal code of the customer.
     * @return The postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer.
     * @param postalCode The postal code to be set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone number of the customer.
     * @return The phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     * @param phoneNumber The phone number to be set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the division ID of the customer.
     * @return The division ID of the customer.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the division ID of the customer.
     * @param divisionID The division ID to set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Overridden toString method for display purposes.
     * @return The ID and Name.
     */
    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name;
    }
}
