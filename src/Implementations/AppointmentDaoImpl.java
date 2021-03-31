package Implementations;

import DataModel.Appointment;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDaoImpl {

    private static ObservableList<Appointment> appointments;

    public static ObservableList<Appointment> getAllAppointments() {
        appointments = FXCollections.observableArrayList();
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
            String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setObject(5, start);
            ps.setObject(6, end);
            ps.setObject(7, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(8,"User");
            ps.setObject(9, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ps.setString(10,"User");
            ps.setInt(11, customerID);
            ps.setInt(12,userID);
            ps.setInt(13,contactID);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
