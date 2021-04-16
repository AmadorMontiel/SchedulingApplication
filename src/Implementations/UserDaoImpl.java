package Implementations;

import DataModel.User;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

    private static ObservableList<User> users;

    /**
     * Method that checks if the user logging into the application is a valid user with a valid password.
     * @param username The username that the user is attempting to log in with.
     * @param password The password that the user is attempting to log in with.
     * @return True is the username and password are correct. False if either or both the username and password
     *  are incorrect.
     */
    public static boolean isValidLogin(String username, String password) {

        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if (username.equals(rs.getString("User_Name"))) {
                    if (password.equals(rs.getString("Password"))) {
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ObservableList<User> getAllUsers() {
        users = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userID,username,password);
                users.add(u);
            }

            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public static User getUserByID(int userID) {
        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                user = new User(id,name,null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public static User getUserByName(String username) {
        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE User_Name = \"test\"";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                user = new User(id, name, null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
