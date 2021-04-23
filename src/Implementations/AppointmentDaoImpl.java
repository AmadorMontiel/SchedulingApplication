package Implementations;

import DataModel.Appointment;
import Utility.DBConnection;
import Utility.TimeConversion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDaoImpl {


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
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                if(start.isAfter(now) && end.isBefore(aWeekFromNow)) {
                    Appointment a = new Appointment(appointmentID,title,description,location,
                            type,start,end,customerID,userID,contactID);
                    appointmentsByWeek.add(a);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentsByWeek;
    }

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
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                if(start.isAfter(now) && end.isBefore(aMonthFromNow)) {
                    Appointment a = new Appointment(appointmentID,title,description,location,
                            type,start,end,customerID,userID,contactID);
                    appointmentsByMonth.add(a);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentsByMonth;
    }

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
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                int customer_ID = rs.getInt("Customer_ID");
                Appointment a = new Appointment(appointmentID,title,type,descripton, start,end,customer_ID);
                appointmentsByContact.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsByContact;
    }

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
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
                    System.out.println("New Start Time Requested: " + UTCStartingTime);
                    System.out.println("Time of Appointment Already: " + LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime());

                    if (UTCStartingTime.equals(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime())) {
                        System.out.println("Start time matches another appointment.");
                        return true;
                    } else if (UTCEndingTime.equals(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("End time matches another appointment.");
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()) &&
                                    UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()))) {
                        System.out.println("Ending time conflicts.");
                        return true;
                    } else if (UTCStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("Starting time conflicts.");
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()))) {
                        System.out.println("New appointment start is before known start and end is after known end");
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

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
                    System.out.println("New Start Time Requested: " + UTCStartingTime);
                    System.out.println("Time of Appointment Already: " + LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime());

                    if (rs.getInt("Appointment_ID") == currentAppointmentID) {
                        continue;
                    }

                    if (UTCStartingTime.equals(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime())) {
                        System.out.println("Start time matches another appointment.");
                        return true;
                    } else if (UTCEndingTime.equals(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("End time matches another appointment.");
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()) &&
                                    UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()))) {
                        System.out.println("Ending time conflicts.");
                        return true;
                    } else if (UTCStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("Starting time conflicts.");
                        return true;
                    } else if (UTCStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            (UTCEndingTime.isAfter(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()))) {
                        System.out.println("New appointment start is before known start and end is after known end");
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

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
