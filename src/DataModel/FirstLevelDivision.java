package DataModel;

/**
 * Represents the first level division of a particular country.
 * i.e. State, Province, etc.
 */
public class FirstLevelDivision {

    /**
     * The ID number of the division.
     */
    private int divisionID;

    /**
     * The name of the division.
     */
    private String division;

    /**
     * The ID of the associated country.
     */
    private int countryID;

    /**
     * Constructor for a first level division.
     * @param divisionID the ID of the division
     * @param division the name of the division
     * @param countryID the ID of the associated country
     */
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Gets the division ID.
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the division ID.
     * @param divisionID the division ID to set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the division name.
     * @return the division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division name.
     * @param division the division name to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the country ID.
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the country ID.
     * @param countryID the country ID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString(){
        return division;
    }
}
