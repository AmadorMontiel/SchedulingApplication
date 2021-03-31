package Implementations;

import DataModel.Country;
import DataModel.FirstLevelDivision;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDaoImpl {

    private static ObservableList<FirstLevelDivision> firstLevelDivisions;

    public static ObservableList<FirstLevelDivision> getFirstLevelDivisions(int countryID) {
        firstLevelDivisions = FXCollections.observableArrayList();
        try {
            //The SQL statement you want to run in the DB
            String sql = "SELECT * from first_level_divisions";
            //Creates a prepared statement by first getting the connection, and calling
            //the prepareStatement method on the connection using the SQL command from above
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            //Once the rows have been retrieved, sets the result of the query
            //equal to the result set
            ResultSet rs = ps.executeQuery();

            //This while loop loops through the result set getting the ID of the country and
            //the name of the country, creating a new Country from that data, and then
            //adds the new Country to the list for display

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
}
