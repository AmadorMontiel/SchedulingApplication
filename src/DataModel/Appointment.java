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
     * The contructor for the appointment class.
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

    @Override
    public String toString() {
        return "ID: " + appointmentID + " Type: " + type;
    }
}