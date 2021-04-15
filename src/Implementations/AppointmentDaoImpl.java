package Implementations;

import DataModel.Appointment;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDaoImpl {

    public static int currentAppointmentID;
    private static ObservableList<Appointment> appointments;
    private static ObservableList<Appointment> associatedAppointments;

    public static ObservableList<Appointment> getAllAppointments() {
        appointments = FXCollections.observableArrayList();
        currentAppointmentID = 1;
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
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appointmentID,title,description,location,
                        type,start,end,customerID,userID,contactID);
                appointments.add(a);
                currentAppointmentID++;
            }
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start,
                                      LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            String sql = "INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,currentAppointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setObject(6, start);
            ps.setObject(7, end);
            ps.setObject(8, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(9,"User");
            ps.setObject(10, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(11,"User");
            ps.setInt(12, customerID);
            ps.setInt(13,userID);
            ps.setInt(14,contactID);
            ps.execute();
            currentAppointmentID++;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void modifyAppointment(int id, String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?" +
                ", Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = " + id;

        try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setObject(5, start);
            ps.setObject(6, end);
            ps.setObject(7, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(8, "User");
            ps.setObject(9, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(10, "User");
            ps.setInt(11, customerID);
            ps.setInt(12, userID);
            ps.setInt(13, contactID);
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

    public static boolean isOverlappingAppointment(LocalDateTime start, LocalDateTime end, int customerID) {

        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId UTC = ZoneId.of("UTC");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime currentStartTime = start.atZone(localTimeZone);
        ZonedDateTime currentEndTime = end.atZone(localTimeZone);

        ZonedDateTime USTStartTimeAndDate = currentStartTime.withZoneSameInstant(UTC);
        LocalTime USTStartingTime = USTStartTimeAndDate.toLocalTime();
        LocalDate USTStartDate = USTStartTimeAndDate.toLocalDate();

        ZonedDateTime USTEndTimeAndDate = currentEndTime.withZoneSameInstant(UTC);
        LocalTime USTEndingTime = USTEndTimeAndDate.toLocalTime();
        LocalDate USTEndDate = USTEndTimeAndDate.toLocalDate();

        try {
            String sql = "SELECT Start, End FROM appointments where Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(USTStartDate.isEqual(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalDate()) &&
                        USTEndDate.isEqual(LocalDateTime.parse(rs.getString("End"), dtf).toLocalDate())) {
                    System.out.println("Date matches date of another appointment.");
                    System.out.println("Continuing to check the times");
                    System.out.println(USTStartingTime);
                    System.out.println(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime());


                    if (USTStartingTime.equals(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime())) {
                        System.out.println("Start time matches another appointment.");
                        return true;
                    } else if (USTStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            USTEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("Ending time conflicts.");
                        return true;
                    } else if (USTStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                            USTStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                        System.out.println("Starting time conflicts.");
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
