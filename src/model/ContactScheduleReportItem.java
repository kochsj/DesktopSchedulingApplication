package model;

import java.sql.Timestamp;

/**
 * Model for a row of data in the Contact Schedule report. Each data member corresponds to a column value in the report table that is generated. <br><br>
 * @see reporting.ContactScheduleReport
 */
public class ContactScheduleReportItem {
    private final int contactId;
    private final String contactName;
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private int customerId;

    /**
     * Class constructor. Sets the Contact name and ID on instantiation.<br><br>
     * @param contactId the Contact's ID
     * @param contactName the Contact's name
     */
    public ContactScheduleReportItem(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Gets the contact id<br><br>
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets the contact name<br><br>
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the appointment's id<br><br>
     * @return the appointment's id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment's id<br><br>
     * @param appointmentId the appointment's id number
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the title for the appointment row<br><br>
     * @return the title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for the appointment row item<br><br>
     * @param title the appointment's titme
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of appointment <br><br>
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type<br><br>
     * @param type the appointment's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment's description<br><br>
     * @return the appointment's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment's description<br><br>
     * @param description the appointment's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment start Timestamp<br><br>
     * @return the appointment's start Timestamp
     */
    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    /**
     * Sets the appointment's Start Timestamp<br><br>
     * @param startDatetime the appointment's start Timestamp
     */
    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     * Gets the appointment's end timestamp<br><br>
     * @return the appointments end timestamp
     */
    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    /**
     * Sets the appointment's end timestamp<br><br>
     * @param endDatetime the appointment's end timestamp
     */
    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    /**
     * Gets the customer ID associated with the appointment row<br><br>
     * @return the customer ID number
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with the appointment<br><br>
     * @param customerId the customer ID number
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
