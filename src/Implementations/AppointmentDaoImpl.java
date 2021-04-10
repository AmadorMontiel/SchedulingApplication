package Implementations;

import DataModel.Appointment;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
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

                if(USTStartDate.isEqual(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalDate())) {
                    return true;
                }
                else if(USTStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                    USTEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                    System.out.println("Appointment falls in the middle of another appointment");
                    return true;
                }
                else if(USTStartingTime.isBefore(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                        (USTEndingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                                USTEndingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime()))) {
                    System.out.println("Start time is before another appointment. End time is in the middle of another appointment.");
                    return true;
                }
                else if((USTStartingTime.isAfter(LocalDateTime.parse(rs.getString("Start"), dtf).toLocalTime()) &&
                        USTStartingTime.isBefore(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) &&
                        USTEndingTime.isAfter(LocalDateTime.parse(rs.getString("End"), dtf).toLocalTime())) {
                    System.out.println("Start time is in middle of another appointment. End is after the end of another appointment.");
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
