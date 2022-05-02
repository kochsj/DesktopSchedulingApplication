package model;

import java.sql.Timestamp;

/**
 * Model class for an Appointment from the database. Contains data members corresponding to the data from the database. Class methods include getters and setters for data members.<br><br>
 * @see table.AppointmentsManager
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Class constructor. Sets data members on instantiation.<br><br>
     * @param appointment_id the appointment id number
     * @param title the appointment title
     * @param description the Appointment's description
     * @param location the appointment location
     * @param type the type of appointment
     * @param start the appointment starting Timestamp Object
     * @param end the appointment ending Timestamp Object
     * @param customer_id the customer id associated with the appointment
     * @param user_id the user id associated with the appointment
     * @param contact_id the contact id associated with the appointment
     * @see java.sql.Timestamp
     */
    public Appointment(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) {
        this.appointmentId = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
        this.customerId = customer_id;
        this.userId = user_id;
        this.contactId = contact_id;
    }

    /**
     * Gets appointment Id.<br><br>
     * @return the Appointment object's ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment Id.<br><br>
     * @param appointmentId the desired Appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets appointment title.<br><br>
     * @return the Appointment object's title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets appointment title.<br><br>
     * @param title the desired Appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Gets appointment description.<br><br>
     * @return the Appointment object's description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets appointment description.<br><br>
     * @param description the desired Appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets appointment location.<br><br>
     * @return the Appointment object's location.
     */
    public String getLocation() {
        return location;
    }
    /**
     * Sets appointment location.<br><br>
     * @param location the desired Appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * Gets appointment type.<br><br>
     * @return the Appointment object's type.
     */
    public String getType() {
        return type;
    }
    /**
     * Sets appointment type.<br><br>
     * @param type the desired Appointment type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Gets appointment start time.<br><br>
     * @return the Appointment object's start time.
     */
    public Timestamp getStartTime() {
        return startTime;
    }
    /**
     * Sets appointment startTime.<br><br>
     * @param startTime the desired Appointment startTime
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    /**
     * Gets appointment end time.<br><br>
     * @return the Appointment object's end time.
     */
    public Timestamp getEndTime() {
        return endTime;
    }
    /**
     * Sets appointment endTime.<br><br>
     * @param endTime the desired Appointment endTime
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    /**
     * Gets the appointment's associated customer ID.<br><br>
     * @return the Appointment object's customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * Sets appointment customerId.<br><br>
     * @param customerId the desired Appointment customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Gets the appointment's associated user ID.<br><br>
     * @return the Appointment object's user ID.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Sets appointment userId.<br><br>
     * @param userId the desired Appointment userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Gets the appointment's associated contact ID.<br><br>
     * @return the Appointment object's contact ID.
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * Sets appointment contactId.<br><br>
     * @param contactId the desired Appointment contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
