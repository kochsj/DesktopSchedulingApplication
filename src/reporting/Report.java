package reporting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AppointmentReportItem;
import model.ContactScheduleReportItem;
import model.CountryReportItem;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Superclass for creating reports in the application. Handles filling a table view with report data.<br><br>
 * @see reporting.ContactScheduleReport
 * @see reporting.CustomersByCountryReport
 * @see reporting.TotalAppointmentsByTypeReport
 */
public class Report {
    private ObservableList comparisonList;
    private TableView tableView;

    /**
     * Class constructor. Determines the type of comparisonList to create based on the report being created.<br><br>
     * @param type String object to determine type of report
     * @param tableView the TableView object being filled
     */
    public Report(String type, TableView<?> tableView){
        this.tableView = tableView;
        if (Objects.equals(type, "AppointmentType")) {
            this.comparisonList = FXCollections.<AppointmentReportItem>observableArrayList();
        } else if (Objects.equals(type, "ContactSchedule")) {
            this.comparisonList = FXCollections.<ContactScheduleReportItem>observableArrayList();
        } else if (Objects.equals(type, "CountryReportMember")) {
            this.comparisonList = FXCollections.<CountryReportItem>observableArrayList();
        } else {
            this.comparisonList = FXCollections.<Object>observableArrayList();
        }
    }

    /**
     * Fills the table view with report data. Uses objects from the comparisonList to create and add columns to the TableView.<br><br>
     * @param dataMembers ObservableList of Strings representing the data members from subclass
     * @see reporting.ContactScheduleReport
     * @see reporting.CustomersByCountryReport
     * @see reporting.TotalAppointmentsByTypeReport
     */
    public void fillTableView(ObservableList<String> dataMembers) {

        tableView.setItems(comparisonList);

        for (String dataMember:
             dataMembers) {
            TableColumn<Object, ?> newColumn = createTableColumn(dataMember);
            tableView.getColumns().add(newColumn);
        }
    }

    /**
     * Private helper method. Helps fillTableView create TableColumn objects based on the correct datatype. Datatypes are provided by the strings in the datamembers list of strings from fillTableView.<br><br>
     * @param dataMember String that identifies the datatype to use and the PropertyValueFactory to create
     * @return TableColumn Object with the correct datatype and PropertyValueFactory
     * @see javafx.scene.control.cell.PropertyValueFactory
     * @see javafx.scene.control.TableColumn
     */
    private TableColumn<Object, ?> createTableColumn(String dataMember) {
        char typeIdentifier = dataMember.toCharArray()[0];
        dataMember = dataMember.substring(1);

        if (typeIdentifier == 'i') {
            TableColumn<Object, Integer> newColumn = new TableColumn<>(dataMember);
            newColumn.setCellValueFactory(new PropertyValueFactory<>(dataMember));
            return newColumn;
        } else if (typeIdentifier == 's') {
            TableColumn<Object, String> newColumn = new TableColumn<>(dataMember);
            newColumn.setCellValueFactory(new PropertyValueFactory<>(dataMember));
            return newColumn;
        } else if (typeIdentifier == 'l') {
            TableColumn<Object, Long> newColumn = new TableColumn<>(dataMember);
            newColumn.setCellValueFactory(new PropertyValueFactory<>(dataMember));
            return newColumn;
        } else if (typeIdentifier == 't') {
            TableColumn<Object, Timestamp> newColumn = new TableColumn<>(dataMember);
            newColumn.setCellValueFactory(new PropertyValueFactory<>(dataMember));
            return newColumn;
        } else {
            return null;
        }
    }

    /**
     * Gets the comparison list ObservableList Object.<br><br>
     * @return the comparison list
     * @see ObservableList
     */
    public ObservableList getComparisonList() {
        return comparisonList;
    }

    /**
     * Adds an Object to the comparison list.<br><br>
     * @param tableObject non-typed object to be added to the comparison list
     * @see ObservableList
     */
    public void addToComparisonList(Object tableObject) {
        this.comparisonList.add(tableObject);
    }

}
