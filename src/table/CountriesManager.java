package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.*;

/**
 * Manages application CRUD operations for the Countries table in the database.<br><br>
 * @see model.Country
 */
public class CountriesManager {

    /**
     * Static method to get an ObservableList of all countries from the database.<br><br>
     * @return ObservableList of all countries
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            Connection connection = DBUtill.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM COUNTRIES");

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country newCountry = new Country(id, countryName);
                allCountries.add(newCountry);
            }

            statement.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCountries;
    }

//    /**
//     * Static method to get a single country from the database<br><br>
//     * @param countryName the Country's name
//     * @return
//     */
//    public static int getCountry(String countryName) {
//        int countryId = 0;
//        String sql = "SELECT Country_ID FROM COUNTRIES WHERE Country=?";
//        try {
//            Connection connection = DBUtill.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, countryName);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            rs.next();
//            countryId = rs.getInt("Country_ID");
//
//            preparedStatement.close();
//            connection.close();
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return countryId;
//    }
//
//    public static String getCountry(int countryId) {
//        String countryName = "";
//        String sql = "SELECT Country FROM COUNTRIES WHERE Country_ID=?";
//        try {
//            Connection connection = DBUtill.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, countryId);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            rs.next();
//            countryName = rs.getString("Country_ID");
//
//            preparedStatement.close();
//            connection.close();
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return countryName;
//    }

}
