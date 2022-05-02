package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.*;

/**
 * Manages application CRUD operations for the Users table in the database.<br><br>
 * @see User
 */
public class UsersManager {

    /**
     * Static method to get an ObservableList of all users from the database.<br><br>
     * @return ObservableList of all users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            Connection connection = DBUtill.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS");

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");

                User newUser = new User(id, username);
                allUsers.add(newUser);
            }
            connection.close();
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    /**
     * Static method to get a single user from the database.<br><br>
     * @param userId the requested User ID number
     * @return the User object or null(not found)
     * @see User
     */
    public static User getUser(int userId) {
        String sql = "SELECT * FROM USERS WHERE User_ID=?";
        User newUser = null;

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");

                newUser = new User(id, username);
            }
            connection.close();
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newUser;
    }

    /**
     * Static method to validate a User's login attempt. User's must provide the correct username and password to be allowed access to the application.<br><br>
     * @param username the username
     * @param password the password
     * @return the User's ID
     */
    public static int validateUser(String username, String password) {
        String sql = "SELECT User_ID FROM USERS WHERE User_Name=? AND Password=?";
        int userId = -1;

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            userId = rs.getInt("User_ID");
            connection.close();
            preparedStatement.close();
        } catch (SQLException ignored) {

        }
        return userId;
    }
}
