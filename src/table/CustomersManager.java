package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.*;

/**
 * Manages application CRUD operations for the Customers table in the database.<br><br>
 * @see model.Customer
 */
public class CustomersManager {

    /**
     * Static method to get an ObservableList of all Customers from the database.<br><br>
     * @return ObservableList of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            Connection connection = DBUtill.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM CUSTOMERS");

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                Customer newCustomer = new Customer(id, customerName, address, postalCode, phone, divisionId);
                newCustomer.setCountryName(FirstLevelDivisionsManager.getCountry(divisionId).getCountryName());

                allCustomers.add(newCustomer);
            }
            connection.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }

    /**
     * Static method to get a single customer from the database.<br><br>
     * @param customerId the ID number for the Customer requested from the database
     * @return the Customer or null(not found)
     */
    public static Customer getCustomer(int customerId) {
        Customer newCustomer = null;
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID=?";

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                newCustomer = new Customer(rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"));
            }
            connection.close();
            preparedStatement.close();
            rs.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return newCustomer;
    }
    /**
     * Static method to add a customer to the database.<br><br>
     * @param customer Customer Object to add to the database
     * @return true/false if the Customer was added to the database
     */
    public static boolean addCustomer(Customer customer) {
        String sql = "INSERT into CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID) VALUES (?,?,?,?,?,?,?)";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(6, DBUtill.getCurrentUser().getUsername());
            preparedStatement.setInt(7, customer.getDivisionId());

            preparedStatement.execute();

            connection.close();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * Static method to update a customer in the database.<br><br>
     * @param customer Customer Object to update in the database
     * @return true/false if the Customer was updated in the database
     */
    public static boolean updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMERS SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Last_Update=?, Last_Updated_By=? WHERE Customer_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setInt(5, customer.getDivisionId());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, DBUtill.getCurrentUser().getUsername());
            preparedStatement.setInt(8, customer.getCustomerId());

            preparedStatement.execute();

            connection.close();
            preparedStatement.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * Static method to delete a customer from the database. Checks that the customer does not have any scheduled appointments prior to deletion.<br><br>
     * @param customerId Customer's ID number to delete from the database
     * @return true/false if the Customer was deleted from the database
     */
    public static boolean deleteCustomer(int customerId) {
        String sqlAppointments = "SELECT * FROM APPOINTMENTS WHERE Customer_ID=?";
        String sqlCustomers = "DELETE FROM CUSTOMERS WHERE Customer_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlAppointments);
            preparedStatement.setInt(1, customerId);

            ResultSet rs = preparedStatement.executeQuery();

            // do not delete a customer that has >= 1 appointment associated with them
            if (rs.next()) {
                return false;
            } else {
                PreparedStatement deleteStatement = connection.prepareStatement(sqlCustomers);
                deleteStatement.setInt(1, customerId);
                deleteStatement.execute();

                connection.close();
                preparedStatement.close();
                deleteStatement.close();
                return true;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}
