package Implementations;

import DataModel.Appointment;
import DataModel.Contact;
import DataModel.Customer;
import Utility.DBConnection;
import Utility.TimeConversion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * DAO implementation of the Appointment class.
 */
public class AppointmentDaoImpl {

    /**
     * Gets an observable list all appointments within the database.
     * @return An observable list of all appointments in the database.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appointmentID,title,description,location,
                        type,start,end,customerID,userID,contactID);
                appointments.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    /**
     * Gets an observable list all appointments within the database with included Customer and Contact Names.
     * @return An observable list of all appointments in the database.
     */
    public static ObservableList<Appointment> getAllAppointmentsWithNames() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                Customer customer = CustomerDaoImpl.getCustomerByID(rs.getInt("Customer_ID"));
                int userID = rs.getInt("User_ID");
                Contact contact = ContactDaoImpl.getContactByID(rs.getInt("Contact_ID"));
                Appointment a = new Appointment(appointmentID,title,description,location,
                        type,start,end,customer.getName(),userID,contact.getName());
                appointments.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    /**
     * Gets an observable list of appointments that fall within a week of the current date/time.
     * @return The observable list of appointments within the next week.
     */
    public static ObservableList<Appointment> getAppointmentsByWeek() {
        ObservableList<Appointment> appointmentsByWeek = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aWeekFromNow = now.plusDays(7);

        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                Customer customer = CustomerDaoImpl.getCustomerByID(rs.getInt("Customer_ID"));
                int userID = rs.getInt("User_ID");
                Contact contact = ContactDaoImpl.getContactByID(rs.getInt("Contact_ID"));

                if(start.isAfter(now) && end.isBefore(aWeekFromNow)) {
                    Appointment a = new Appointment(appointmentID,title,description,location,
                            type,start,end,customer.getName(),userID,contact.getName());
                    appointmentsByWeek.add(a);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentsByWeek;
    }

    /**
     * Gets an observable list of appointments that fall within a month of the current date/time.
     * @return An observable list of appointments within the next month.
     */
    public static ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aMonthFromNow = now.plusMonths(1);

        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                Customer customer = CustomerDaoImpl.getCustomerByID(rs.getInt("Customer_ID"));
                int userID = rs.getInt("User_ID");
                Contact contact = ContactDaoImpl.getContactByID(rs.getInt("Contact_ID"));

                if(start.isAfter(now) && end.isBefore(aMonthFromNow)) {
                    Appointment a = new Appointment(appointmentID,title,description,location,
                            type,start,end,customer.getName(),userID,contact.getName());
                    appointmentsByMonth.add(a);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentsByMonth;
    }

    /**
     * Gets an observable list of appointments grouped by type and month.
     * @return The observable list of appointments grouped by type and month.
     */
    public static ObservableList<Appointment> getAppointmentsByTypeAndMonth() {
        ObservableList<Appointment> appointmentsByTypeAndMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT monthname(Start), type, COUNT(*) FROM appointments WHERE month(Start) GROUP BY monthname(Start), type";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String month = rs.getString("monthname(Start)");
                String type = rs.getString("type");
                int count = rs.getInt("COUNT(*)");

                Appointment a = new Appointment(month, type, count);
                appointmentsByTypeAndMonth.add(a);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  appointmentsByTypeAndMonth;
    }

    /**
     * Gets an observable list of appointments based on a Contact ID.
     * @param contactID The Contact ID of the associated appointments.
     * @return An observable list of appointments associated with the Contact ID.
     */
    public static ObservableList<Appointment> getAppointmentsByContact(int contactID) {
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID " +
                    "FROM appointments WHERE Contact_ID = " + contactID + " ORDER BY Start";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String descripton = rs.getString("Description");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                int customer_ID = rs.getInt("Customer_ID");
                Appointment a = new Appointment(appointmentID,title,type,descripton, start,end,customer_ID);
                appointmentsByContact.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsByContact;
    }

    /**
     * Gets an obeservable list of appointments based on a User ID.
     * @param userID The User ID of the associated appointments.
     * @return An observable list of appointments assocaited with the User ID.
     */
    public static ObservableList<Appointment> getAppointmentsByUser(int userID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Start FROM appointments WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                LocalDateTime appointmentStartTime = LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Appointment a = new Appointment(appointmentID,appointmentStartTime,userID);
                appointments.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }

    /**
     * Gets an observable list of appointments based on a Customer ID.
     * @param customerID The Customer ID of the associated appointments.
     * @return An observable list of appointments associated with the Customer ID.
     */
    public static ObservableList<Appointment> getAppointmentsByCustomer(int customerID) {
        ObservableList<Appointment> appointmentsByCustomer = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Contact_ID, User_ID " +
                    "FROM appointments WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String descripton = rs.getString("Description");
                LocalDateTime start = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                LocalDateTime end = TimeConversion.localTimeConversion(LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                int contactID = rs.getInt("Contact_ID");
                int userID = rs.getInt("User_ID");
                Appointment a = new Appointment(appointmentID,title,type,descripton, start,end,contactID, userID);
                appointmentsByCustomer.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsByCustomer;
    }

    /**
     * Gets the number of appointments associated with a Customer ID.
     * @param customerID The Customer ID of the associated appointments.
     * @return The number of associated appointments.
     */
    public static int getAssociatedAppointments(int customerID) {

        int associatedAppointmentsCounter = 0;

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                associatedAppointmentsCounter++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return associatedAppointmentsCounter;
    }

    /**
     * Adds a new appointment to the database.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of the appointment.
     * @param start The start date/time of the appointment.
     * @param end The end date/time of the appointment.
     * @param customerID The associated Customer ID.
     * @param userID The associated User ID.
     * @param contactID The associated Contact ID.
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start,
                                      LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, " +
                    "Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setObject(5, start);
            ps.setObject(6, end);
            ps.setString(7,"User");
            ps.setString(8,"User");
            ps.setInt(9, customerID);
            ps.setInt(10,userID);
            ps.setInt(11,contactID);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Updates an appointment that already exists in the database.
     * @param id The Appointment ID of the appointment to be modified.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of the appointment.
     * @param start The start date/time of the appointment.
     * @param end The end date/time of the appointment.
     * @param customerID The associated Customer ID.
     * @param userID The associated User ID.
     * @param contactID The associated Contact ID.
     */
    public static void modifyAppointment(int id, String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?" +
                ", Start = ?, End = ?, Created_By = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = " + id;

        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setObject(5, start);
            ps.setObject(6, end);
            ps.setString(7, "User");
            ps.setString(8, "User");
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes an appointment from the database based on the Appointment ID.
     * @param appointmentID The Appointment ID of the appointment to be deleted.
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Checks if a new appointment overlaps with another appointment based on the Customer ID.
     * @param start The start date/time of the new appointment.
     * @param end The end date/time of the new appointment.
     * @param customerID The associated Customer ID.
     * @return Returns true if the new appointment would overlap with an existing appointment.
     * Otherwise, returns false.
     */
    public static boolean isOverlappingAppointment(LocalDateTime start, LocalDateTime end, int customerID) {

        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId UTC = ZoneId.of("UTC");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime currentStartTime = start.atZone(localTimeZone);
        ZonedDateTime currentEndTime = end.atZone(localTimeZone);

        ZonedDateTime UTCStartTimeAndDate = currentStartTime.withZoneSameInstant(UTC);
        LocalTime UTCStartingTime = UTCStartTimeAndDate.toLocalTime();
        LocalDate UTCStartDate = UTCStartTimeAndDate.toLocalDate();

        ZonedDateTime UTCEndTimeAndDate = currentEndTime.withZoneSameInstant(UTC);
        LocalTime UTCEndingTime = UTCEndTimeAndDate.toLocalTime();
        LocalDate UTCEndDate = UTCEndTimeAndDate.toLocalDate();

        try {
            String sql = "SELECT Start, End, Appointment_ID FROM appointments where Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(UTCStartDate.isEqual(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalDate()) &&
                        UTCEndDate.isEqual(LocalDateTime.parse(rs.getString("End"), dtf).toLocalDate())) {

                    if (UTCStartingTime.equals(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCEndingTime.equals(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()) &&
                                    UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()))) {
                        return true;
                    } else if (UTCStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()))) {
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if an updated appointment overlaps with another appointment. Also skips checking the appointment against itself.
     * @param start The new start date/time of the appointment.
     * @param end The new end date/time of the appointment.
     * @param customerID The associated Customer ID of the appointment.
     * @param currentAppointmentID The appointmentID of the current appointment.
     * @return Returns true if the updated times would overlap with an existing appointment.
     * Otherwise, returns false.
     */
    public static boolean isOverlappingAppointment(LocalDateTime start, LocalDateTime end, int customerID, int currentAppointmentID) {

        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId UTC = ZoneId.of("UTC");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime currentStartTime = start.atZone(localTimeZone);
        ZonedDateTime currentEndTime = end.atZone(localTimeZone);

        ZonedDateTime UTCStartTimeAndDate = currentStartTime.withZoneSameInstant(UTC);
        LocalTime UTCStartingTime = UTCStartTimeAndDate.toLocalTime();
        LocalDate UTCStartDate = UTCStartTimeAndDate.toLocalDate();

        ZonedDateTime UTCEndTimeAndDate = currentEndTime.withZoneSameInstant(UTC);
        LocalTime UTCEndingTime = UTCEndTimeAndDate.toLocalTime();
        LocalDate UTCEndDate = UTCEndTimeAndDate.toLocalDate();

        try {
            String sql = "SELECT Start, End, Appointment_ID FROM appointments where Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(UTCStartDate.isEqual(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalDate()) &&
                        UTCEndDate.isEqual(LocalDateTime.parse(rs.getString("End"), dtf).toLocalDate())) {
                    if (rs.getInt("Appointment_ID") == currentAppointmentID) {
                        continue;
                    }

                    if (UTCStartingTime.equals(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCEndingTime.equals(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()) &&
                                    UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()))) {
                        return true;
                    } else if (UTCStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()))) {
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Checks the current appointmentID to see if the appointment starts within 15 minutes of the current time.
     * @param currentAppointmentID The appointment ID that is being checked.
     * @return True if the appointment starts in the next 15 minutes.
     * Otherwise, returns false.
     */
    public static boolean isAppointmentWithin15Minutes(int currentAppointmentID) {

        try {
            String sql = "SELECT Start FROM appointments WHERE Appointment_ID = " + currentAppointmentID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDateTime appointmentStartTime = LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                appointmentStartTime = TimeConversion.localTimeConversion(appointmentStartTime);
                if(appointmentStartTime.toLocalDate().isEqual(LocalDateTime.now().toLocalDate()) && appointmentStartTime.isBefore(LocalDateTime.now().plusMinutes(15)) &&
                appointmentStartTime.isAfter(LocalDateTime.now())){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
