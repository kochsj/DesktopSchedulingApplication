package model;

/**
 * User class models user data received from the database. Includes data member getter and setter methods.
 */
public class User {
    private int userId;
    private String username;

    /**
     * Class constructor. Sets userId and username on instantiation.<br><br>
     * @param userId the User's ID number
     * @param username the User's name
     */
    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Gets the User's ID number<br><br>
     * @return the User's ID number
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the User's ID number<br><br>
     * @param userId the User's ID number
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the User's username<br><br>
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the User's username<br><br>
     * @param username the User's username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
