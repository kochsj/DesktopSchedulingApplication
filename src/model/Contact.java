package model;

/**
 * Model class for a Contact from the database. Contains data members corresponding to the data from the database. Class methods include getters and setters for data members.<br><br>
 * @see table.ContactsManager
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
    /**
     * Gets the Contact's ID.<br><br>
     * @return the Contact object's ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the Contact's ID.<br><br>
     * @param contactId the desired ID number
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /**
     * Gets the Contact's name.<br><br>
     * @return the Contact object's name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the Contact's name.<br><br>
     * @param contactName the desired name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * Gets the Contact's email.<br><br>
     * @return the Contact object's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Contact's email<br><br>
     * @param email the desired email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
