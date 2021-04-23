package Implementations;

import DataModel.Country;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO implementation of the Country Class.
 */
public class CountryDaoImpl {

    /**
     * Gets an observable list of all countries from the database.
     * @return An observable list of countries.
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryID,countryName);
                countries.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

    /**
     * Gets a country by its associated ID.
     * @param countryID The associated Country ID.
     * @return The country associated with the ID.
     */
    public static Country getCountryByID(int countryID) {
        Country country = null;

        try {
            String sql = "SELECT * from countries WHERE Country_ID = " + countryID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                country = new Country(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }
}
