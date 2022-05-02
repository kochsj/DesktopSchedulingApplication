package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manages application CRUD operations for the First_Level_Divisions table in the database.<br><br>
 * @see FirstLevelDivision
 */
public class FirstLevelDivisionsManager {

    /**
     * Static method to get an ObservableList of first level divisions for a given country. Gets all divisions by countryId.<br><br>
     * @param countryId the ID number for the country that all divisions are requested for
     * @return ObservableList of all associated divisions
     * @see model.FirstLevelDivision
     * @see model.Country
     */
    public static ObservableList<FirstLevelDivision> getDivisionByCountryId(int countryId) {
        ObservableList<FirstLevelDivision> associatedFirstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID=?";

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, countryId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                associatedFirstLevelDivisions.add(new FirstLevelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        countryId
                        ));
            }
            connection.close();
            preparedStatement.close();
            rs.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return associatedFirstLevelDivisions;
    }

    /**
     * Static method to get a single FirstLevelDivision from the database.<br><br>
     * @param divisionId the ID number for the requested division
     * @return FirstLevelDivision Object or null(not found)
     * @see FirstLevelDivision
     */
    public static FirstLevelDivision getDivision(int divisionId) {
        FirstLevelDivision division = null;
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, divisionId);
            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            division = new FirstLevelDivision(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
            connection.close();
            preparedStatement.close();
            rs.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return division;
    }

    /**
     * Static method to get the Country associated with a given Division.<br><br>
     * @param divisionId the division ID number
     * @return the associated Country object
     * @see model.Country
     */
    public static Country getCountry(int divisionId){
        Country country = null;
        int countryId = divisionIdToCountryId(divisionId);

        if (countryId<0) {
            return country;
        }

        String sql = "SELECT * FROM COUNTRIES WHERE Country_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, countryId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            country = new Country(countryId, rs.getString("Country"));

            connection.close();
            preparedStatement.close();
            rs.close();

        } catch (SQLException ignored) {

        }

        return country;
    }

    /**
     * Static method to convert a given divisionId into its associated countryId.<br><br>
     * @param divisionId the divisionId to convert
     * @return the associated countryId
     */
    public static int divisionIdToCountryId(int divisionId) {
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID=?";
        int countryID = -1;

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, divisionId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            countryID = rs.getInt("Country_ID");

            connection.close();
            preparedStatement.close();
            rs.close();
        } catch (SQLException ignored) {

        }
        return countryID;
    }
}
