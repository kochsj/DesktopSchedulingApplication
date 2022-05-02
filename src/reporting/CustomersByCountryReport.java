package reporting;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.CountryReportItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sub-class extending Report class.<br><br>
 * Reports on the number of customers for every country in the database. Report is constructed in a TableView for use by the application.<br><br>
 * @see reporting.Report
 */
public class CustomersByCountryReport extends Report {
    private ObservableList<String> dataMembers = FXCollections.observableArrayList();

    /**
     * Class constructor. Takes a TableView Object from the application as a parameter. Passes report type and TableView to superclass.<br><br>
     * @param tableView the TableView to render the report in
     */
    public CustomersByCountryReport(TableView<CountryReportItem> tableView) {
        super("CountryReportMember", tableView);
        this.dataMembers.addAll("icountryId", "scountryName", "inumCustomers", "inumDivisions");
    }

    /**
     * Public method for constructing a contact schedule report.<br><br>
     * Creates CountryReportItem Objects for every country in the database. Then assigns divisions with CountryID value to the matching CountryReportItem Object's CountryID value. DivisionIDs are used to then tally the number of Customers in the database with division IDs associated with a given CountryReportItem. The result is a number of customers for every CountryReportItem and a total number of divisions.<br><br>
     * @throws SQLException problems getting Customers or Countries from the database
     * @see model.CountryReportItem
     */
    public void constructReport() throws SQLException {
        assignDivisionIds();
        tallyCustomersByDivision();
        fillTableView(this.dataMembers);
    }

    /**
     * Private helper method. Assigns DivisionIDs to their associated Country.<br>
     * 1. get all countries from COUNTRIES table<br>
     * 2. instantiate CountryReportMember objects for every country and add to comparisonList<br>
     *     2a. comparisonList is used to hold all the objects
     * 3. for each country:<br>
     *     3a. select all the division ids from the database where the Country_ID="the CountryReportItem's countryId"<br>
     *     3b. for every division that results, add the division id to the CountryReportItem's divisionID list<br>
     * @throws SQLException problems getting Countries or Divisions from the database
     */
    private void assignDivisionIds() throws SQLException {

        Connection connection = DBUtill.getConnection();
        PreparedStatement firstPreparedStatement = connection.prepareStatement("SELECT * FROM COUNTRIES");
        ResultSet countryResultSet = firstPreparedStatement.executeQuery();

        while(countryResultSet.next()) {
            int countryId = countryResultSet.getInt("Country_ID");
            String countryName = countryResultSet.getString("Country");

            CountryReportItem countryReportMember = new CountryReportItem(countryId, countryName);

            PreparedStatement secondPreparedStatement = connection.prepareStatement("SELECT (Division_ID) FROM first_level_divisions WHERE Country_ID=?");
            secondPreparedStatement.setInt(1, countryId);
            ResultSet rs = secondPreparedStatement.executeQuery();

            while (rs.next()) {
                countryReportMember.addToDivisionIdList(rs.getInt("Division_ID"));
                countryReportMember.incrementNumDivisions();
            }

            this.addToComparisonList(countryReportMember);

            secondPreparedStatement.close();
            rs.close();
        }
        firstPreparedStatement.close();
        countryResultSet.close();
        connection.close();
    }
    /**
     * Private helper method. For every Customer in the database, checks their DivisionID and incrememnts the number of customers in the CountryReportItem that has the matching DivisionID associated with it.<br><br>
     * @throws SQLException problems getting Customers from the database
     */
    private void tallyCustomersByDivision() throws SQLException {
        Connection connection = DBUtill.getConnection();
        String sql = "SELECT COUNT(Customer_ID) FROM CUSTOMERS WHERE Division_ID=?";
        ObservableList<CountryReportItem> comparisonList = this.getComparisonList();

        comparisonList.forEach((countryReportMember -> {
            ObservableList<Integer> divisionIdList = countryReportMember.getDivisionIdList();
            divisionIdList.forEach((divisionId) -> {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, divisionId);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    countryReportMember.addNumCustomers(rs.getInt(1));
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }));
        connection.close();
    }

}
