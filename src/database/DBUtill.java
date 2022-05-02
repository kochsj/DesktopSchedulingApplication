package database;

import model.User;
import java.sql.*;

/**
 * SQL Database helper class for application.<br><br>
 * Gets connection to the database and keeps reference of the current user.
 */
public class DBUtill {
    private static User currentUser = null;
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static final String connectionString = "jdbc:mysql://localhost:3306/client_schedule";

    /**
     * Creates a connection to mysql database.
     * @return the resulting Connection Object
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    connectionString,
                    username,
                    password
            );
//            System.out.println("Connection Successful");
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
        }
        return connection;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        DBUtill.currentUser = currentUser;
    }

}
