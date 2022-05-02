package reporting;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.ContactScheduleReportItem;
import java.sql.*;

/**
 * Sub-class extending Report class.<br><br>
 * Reports on the schedules for all contacts in the database. Report is constructed in a TableView for use by the application.<br><br>
 * @see reporting.Report
 */
public class ContactScheduleReport extends Report {
    private ObservableList<String> dataMembers = FXCollections.observableArrayList();

    /**
     * Class constructor. Takes a TableView Object from the application as a parameter. Passes report type and TableView to superclass.<br><br>
     * @param tableView the TableView to render the report in
     */
    public ContactScheduleReport(TableView<ContactScheduleReportItem> tableView) {
        super("ContactSchedule", tableView);
        this.dataMembers.addAll("icontactId","scontactName", "iappointmentId", "stitle", "stype", "sdescription", "tstartDatetime", "tendDatetime", "icustomerId");
    }

    /**
     * Public method for constructing a contact schedule report.<br><br>
     * 1. Gets all contacts from CONTACTS table<br>
     * 2. Instantiates ContactSchedule objects for every contact and add to superclass comparisonList<br>
     *     2a. comparisonList is used to hold all the ContactSchedule objects to fill the table rows<br>
     * 3. for each contact:<br>
     *     3a. Select all appointments matching the contact's id from a ContactSchedule object in the comparison list<br>
     *     3b. While there are matching appointments, call ContactSchedule.addToSchedule to add all the matching appointments for that unique contact<br>
     *     3c. After assigning all appointments, the comparison list is full of ContactSchedule objects with every appointment<br>
     * 4. Then fill the table with the ContactSchedule objects. <br>
     *     - assign and add TableColumns<br>
     *     - populate with factories<br><br>
     * @throws SQLException problems getting Appointments or Contacts from the database
     * @see model.ContactScheduleReportItem
     */
    public void constructReport() throws SQLException {

        Connection connection = DBUtill.getConnection();
        PreparedStatement firstPreparedStatement = connection.prepareStatement("SELECT * FROM CONTACTS");
        ResultSet contactResultSet = firstPreparedStatement.executeQuery();

        while (contactResultSet.next()) {
            int contactId = contactResultSet.getInt("Contact_ID");
            String contactName = contactResultSet.getString("Contact_Name");

            PreparedStatement secondPreparedStatement = connection.prepareStatement("SELECT * FROM APPOINTMENTS WHERE Contact_ID=?");
            secondPreparedStatement.setInt(1, contactId);
            ResultSet rs = secondPreparedStatement.executeQuery();

            while (rs.next()) {
                ContactScheduleReportItem contactSchedule = new ContactScheduleReportItem(contactId, contactName);
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Timestamp startDatetime = rs.getTimestamp("Start");
                Timestamp endDatetime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");

                contactSchedule.setAppointmentId(appointmentId);
                contactSchedule.setTitle(title);
                contactSchedule.setType(type);
                contactSchedule.setDescription(description);
                contactSchedule.setStartDatetime(startDatetime);
                contactSchedule.setEndDatetime(endDatetime);
                contactSchedule.setCustomerId(customerId);
                addToComparisonList(contactSchedule);
            }
            secondPreparedStatement.close();
            rs.close();
        }
        firstPreparedStatement.close();
        contactResultSet.close();
        connection.close();

        fillTableView(this.dataMembers);
    }

}
