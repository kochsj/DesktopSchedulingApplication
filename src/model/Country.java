package model;

/**
 * Class that models the Country data received from the database. Includes data member getter and setter methods.
 * @see table.CountriesManager
 */
public class Country {

    private int countryId;
    private String countryName;

    /**
     * Class constructor. Sets the Country ID and country name.<br><br>
     * @param countryId the Country ID
     * @param countryName the Country Name
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Gets the country ID.<br><br>
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the country ID.<br><br>
     * @param countryId the country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the country name.<br><br>
     * @return the Country's name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the Country's name<br><br>
     * @param countryName the Country's name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
