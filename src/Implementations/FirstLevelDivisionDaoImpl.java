package Implementations;

import DataModel.FirstLevelDivision;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DAO implementation of the FirstLevelDivision Class
 */
public class FirstLevelDivisionDaoImpl {

    /**
     * Gets a list of first level divisions based on a Country ID.
     * @param countryID Country ID used to determine the first level divisions.
     * @return An observable list of first level divisions.
     */
    public static ObservableList<FirstLevelDivision> getFirstLevelDivisions(int countryID) {
        ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs.getInt("COUNTRY_ID") == countryID) {
                    int divisionID = rs.getInt("Division_ID");
                    String division = rs.getString("Division");
                    FirstLevelDivision fld = new FirstLevelDivision(divisionID,division,countryID);
                    firstLevelDivisions.add(fld);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return firstLevelDivisions;
    }

    /**
     * Gets a specific first level division based on the ID.
     * @param divisionID The ID of the division.
     * @return The division related to the ID.
     */
    public static FirstLevelDivision getDivisionByID(int divisionID) {
        FirstLevelDivision fld = null;
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                fld = new FirstLevelDivision(id,division, countryID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fld;
    }
}
