package DataModel;

import java.time.LocalDateTime;

/**
 * Represents an appointment within the program.
 */
public class Appointment {
    /**
     * The individual ID assigned to the apopointement.
     */
    private int appointmentID;
    /**
     * The title of the appointment.
     */
    private String title;
    /**
     * The description of the appointment.
     */
    private String description;
    /**
     * The location of the appointment.
     */
    private String location;
    /**
     * The type of appointment.
     */
    private String type;
    /**
     * The start date and time of the appointment, in local time.
     */
    private LocalDateTime start;
    /**
     * The end date and time of the appointment, in local time.
     */
    private LocalDateTime end;
    /**
     * The customer ID of the customer in the apppointment.
     */
    private int customerID;
    /**
     * The user ID of the user creating the apppointment.
     */
    private int userID;
    /**
     * The contact ID of the contact associated with the appointment.
     */
    private int contactID;
    /**
     *  The month of the appointment.
     */
    private String month;
    /**
     * The count of appointments.
     */
    private int count;

    /**
     * The main contructor for the appointment class.
     * @param appointmentID the appointment ID.
     * @param title the appointment title.
     * @param description the appointment description.
     * @param location the appointment location.
     * @param type the type of appointment.
     * @param start the start date and time of the appointment.
     * @param end the end date and time of the appointment.
     * @param customerID the associated customer ID.
     * @param userID the associated user ID.
     * @param contactID the associated contact ID.
     */
    public Appointment(int appointmentID, String title, String description,
                       String location, String type, LocalDateTime start,
                       LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Additional constructor used in getAppointsByUser method.
     * @param appointmentID The appointmentID of the appointment.
     * @param start Start date and time of the appointment.
     * @param userID Associated userID of the appointment
     */
    public Appointment(int appointmentID, LocalDateTime start, int userID){
        this.appointmentID = appointmentID;
        this.start = start;
        this.userID = userID;
    }

    /**
     * Additional constructor used in getAppointmentsByTypeAndMonth method.
     * @param month The month of the appointment.
     * @param type The type of appointment.
     * @param count The count of the appointments.
     */
    public Appointment(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Additional constructor used in the getAppointmentsByContact method.
     * @param appointmentID The AppointmentID of the appointment.
     * @param title The Title of the appointment.
     * @param type The type of appointment.
     * @param descripton The description of the appointment.
     * @param start The start date/time of the appointment.
     * @param end The end date/time of the appointment.
     * @param customer_id The customerID associated with the appointment
     */
    public Appointment(int appointmentID, String title, String type, String descripton, LocalDateTime start, LocalDateTime end, int customer_id) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = descripton;
        this.start = start;
        this.end = end;
        this.customerID = customer_id;
    }

    /**
     * Additional constructor used in the getAppointmentsByCustomer method.
     * @param appointmentID The appointmentID of the appointment.
     * @param title The title of the appointment.
     * @param type The type of the appointment.
     * @param descripton The description of the appointment.
     * @param start The start date/time of the appointment.
     * @param end The end date/time of the appointment.
     * @param contactID The associated contactID.
     * @param userID The associated userID.
     */
    public Appointment(int appointmentID, String title, String type, String descripton, LocalDateTime start, LocalDateTime end, int contactID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = descripton;
        this.start = start;
        this.end = end;
        this.contactID = contactID;
        this.userID = userID;
    }

    /**
     * Gets the appointment ID.
     * @return the appointment ID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     * @param appointmentID the appointment ID to set.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the title of the appointment.
     * @return the title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the apppointment.
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the appointment.
     * @return the description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointment.
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location of the appointment.
     * @return the location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment.
     * @param location the location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the type of the appointment.
     * @return the type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of appointment.
     * @param type the type of appointment to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start time and date of the appointment.
     * @return the start time and date.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start time and date of the appointment.
     * @param start the time and date to set.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the end time and date of the appointment.
     * @return the end time and date of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end time and date of the appointment.
     * @param end the end time and date to set.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets the associated customer ID.
     * @return the customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the associated customer ID.
     * @param customerID the customer ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the associated user ID.
     * @return the user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the associated user ID.
     * @param userID the user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the associated contact ID.
     * @return the contact ID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the associated contact ID.
     * @param contactID the contact ID to set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the month of the appointment.
     * @return the month of the appointment.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month of the appointment.
     * @param month the month to set.
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the count of appointments.
     * @return the count of appointments.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of the appointments.
     * @param count the count to set.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Overridden toString method for display purposes.
     * @return The ID and Type of the appointment.
     */
    @Override
    public String toString() {
        return "ID: " + appointmentID + " Type: " + type;
    }
}
