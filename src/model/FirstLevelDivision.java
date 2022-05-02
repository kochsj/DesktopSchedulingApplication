package model;

/**
 * This class models the First-Level Division data from the database. Results from the database have their attributes mapped to data members. Includes data member getter and setter methods.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * Class constructor. Sets divisionId, division name, and the country ID for the first-level division<br><br>
     * @param divisionId the Division's ID number
     * @param divisionName the name for the Division
     * @param countryId the associated country ID
     */
    public FirstLevelDivision(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets the Division ID number<br><br>
     * @return the Division ID number
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID number<br><br>
     * @param divisionId the division ID number
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the division name<br><br>
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the division name<br><br>
     * @param divisionName the division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets the country ID number<br><br>
     * @return the Country ID number
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country id number <br><br>
     * @param countryId the country id number
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
