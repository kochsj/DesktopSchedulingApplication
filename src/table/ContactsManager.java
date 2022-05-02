package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.*;

/**
 * Manages application CRUD operations for the Contacts table in the database.<br><br>
 * @see model.Contact
 */
public class ContactsManager {

    /**
     * Static method for getting an ObservableList of all contacts from the database.<br><br>
     * @return ObservableList of all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            Connection connection = DBUtill.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM CONTACTS");

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact newContact = new Contact(id, contactName, email);
                allContacts.add(newContact);
            }
            statement.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    /**
     * Static method to get a single contact from the database<br><br>
     * @param contactId the ID number for the contact requested from the database
     * @return Contact Object or null(not found)
     */
    public static Contact getContact(int contactId) {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID=?";
        Contact newContact = null;

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                newContact = new Contact(id, contactName, email);
            }
            preparedStatement.close();
            connection.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newContact;
    }
}
