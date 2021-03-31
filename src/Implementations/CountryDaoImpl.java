package Implementations;

import DataModel.Country;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImpl {

    private static ObservableList<Country> countries;

    public static ObservableList<Country> getAllCountries() {

        countries = FXCollections.observableArrayList();
        try {
            //The SQL statement you want to run in the DB
            String sql = "SELECT * from countries";
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
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryID,countryName);
                countries.add(c);
            }
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countries;
    }
}
