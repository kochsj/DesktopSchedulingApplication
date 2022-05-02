package reporting;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.AppointmentReportItem;
import java.sql.*;
import java.time.Month;
import java.util.Objects;

/**
 * Sub-class extending Report class.<br><br>
 * Reports on the total number of appointments for every appointment type. Report is constructed in a TableView for use by the application.<br><br>
 * @see reporting.Report
 */
public class TotalAppointmentsByTypeReport extends Report {
    private ObservableList<String> dataMembers = FXCollections.observableArrayList();

    /**
     * Class constructor. Takes a TableView Object from the application as a parameter. Passes report type and TableView to superclass.<br><br>
     * @param tableView the TableView to render the report in
     */
    public TotalAppointmentsByTypeReport(TableView<AppointmentReportItem> tableView) {
        super("AppointmentType", tableView);
        this.dataMembers.addAll("stype", "ijanuary", "ifebruary", "imarch", "iapril", "imay", "ijune", "ijuly", "iaugust", "iseptember", "ioctober", "inovember", "idecember");
    }

    /**
     * Public method for constructing a contact schedule report.<br><br>
     * 1. Get appointment type and  starttime from appointments table instantiate appointment type objects for every unique type AND add them to comparisonList<br>
     * 2a. comparisonList is used to check - "is this unique"?<br>
     * 2b. If so, instantiate and add to the list<br>
     * 2c. If not, increment the already existing AppointmentType object<br>
     * 3. Increment month based on the start time provided.<br>
     * 4. Fill table view.<br><br>
     * @throws SQLException problems getting Appointments or Contacts from the database
     * @see model.AppointmentReportItem
     */
    public void constructReport() throws SQLException {
        ObservableList<String> currentTypes = FXCollections.observableArrayList();

        Connection connection = DBUtill.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT Type, Start FROM APPOINTMENTS");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String type = resultSet.getString("Type");
            Timestamp timestamp = resultSet.getTimestamp("Start");
            if (currentTypes.contains(type)) {
                // already have node in comparison list
                // get node from comparisonList
                // increment month
                ObservableList<AppointmentReportItem> comparisonList = this.getComparisonList();
                comparisonList.forEach((appointment) ->{
                    if (Objects.equals(appointment.getType(), type)) {
                        appointment.incrementMonth(computeMonth(timestamp));
                    }
                });

            } else {
                // add node to comparison list
                AppointmentReportItem appointmentType = new AppointmentReportItem(type);
                appointmentType.incrementMonth(computeMonth(timestamp));
                currentTypes.add(type);
                this.addToComparisonList(appointmentType);
            }
        }
        this.fillTableView(this.dataMembers);
    }

    /**
     * Private helper method. Helps constructReport convert a Timestamp Object into a Month object.<br><br>
     * @param timestamp Timestamp Object to be converted
     * @return Month Object representing timestamp
     */
    private Month computeMonth(Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate().getMonth();
    }

}
