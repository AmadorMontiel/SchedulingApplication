package DataModel;

/**
 * Represents a Country in the datebase.
 */
public class Country {
    /**
     * The ID of the Country.
     */
    private int countryID;
    /**
     * The name of the Country.
     */
    private String country;

    /**
     * Constructor for contries.
     * @param countryID ID of the country.
     * @param country The name of the country.
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Gets the countryID.
     * @return The countryID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the countryID.
     * @param countryID The countryID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the name of the country.
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     * @param country The name to set.
     */
    public void setName(String country) {
        this.country = country;
    }

    /**
     * Overridden toString method for display purposes.
     * @return the name of the country.
     */
    @Override
    public String toString() {
        return country;
    }
}
