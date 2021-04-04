package Implementations;

import Utility.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

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
}
