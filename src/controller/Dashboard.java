package controller;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import reporting.*;
import table.AppointmentsManager;
import table.CustomersManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
// header pane
    public Label labelUsername;
    public Label labelCurrentDatetime;
    public Text textAlertMessage;
// schedule pane
    public Text textScheduleErrorMessage;
    public TabPane tabPaneSchedule;
    public Tab tabCurrentWeek;
    public TableView<Appointment> tableViewScheduleWeekly;
    public TableColumn<Appointment, Integer> tableColumnIdWeekly;
    public TableColumn<Appointment, String> tableColumnTitleWeekly;
    public TableColumn<Appointment, String> tableColumnDescriptionWeekly;
    public TableColumn<Appointment, String> tableColumnLocationWeekly;
    public TableColumn<Appointment, String> tableColumnTypeWeekly;
    public TableColumn<Appointment, Integer> tableColumnStartTimeWeekly;
    public TableColumn<Appointment, Integer> tableColumnEndTimeWeekly;
    public TableColumn<Appointment, Integer> tableColumnCustomerIdWeekly;
    public TableColumn<Appointment, Integer> tableColumnUserIdWeekly;
    public TableColumn<Appointment, Integer> tableColumnContactIdWeekly;
    public Tab tabCurrentMonth;
    public TableView<Appointment> tableViewScheduleMonthly;
    public TableColumn<Appointment, Integer> tableColumnIdMonthly;
    public TableColumn<Appointment, String> tableColumnTitleMonthly;
    public TableColumn<Appointment, String> tableColumnDescriptionMonthly;
    public TableColumn<Appointment, String> tableColumnLocationMonthly;
    public TableColumn<Appointment, String> tableColumnTypeMonthly;
    public TableColumn<Appointment, Integer> tableColumnStartTimeMonthly;
    public TableColumn<Appointment, Integer> tableColumnEndTimeMonthly;
    public TableColumn<Appointment, Integer> tableColumnCustomerIdMonthly;
    public TableColumn<Appointment, Integer> tableColumnUserIdMonthly;
    public TableColumn<Appointment, Integer> tableColumnContactIdMonthly;
    public Tab tabAllAppointments;
    public TableView<Appointment> tableViewScheduleAll;
    public TableColumn<Appointment, Integer> tableColumnIdAll;
    public TableColumn<Appointment, String> tableColumnTitleAll;
    public TableColumn<Appointment, String> tableColumnDescriptionAll;
    public TableColumn<Appointment, String> tableColumnLocationAll;
    public TableColumn<Appointment, String> tableColumnTypeAll;
    public TableColumn<Appointment, Timestamp> tableColumnStartTimeAll;
    public TableColumn<Appointment, Timestamp> tableColumnEndTimeAll;
    public TableColumn<Appointment, Integer> tableColumnCustomerIdAll;
    public TableColumn<Appointment, Integer> tableColumnUserIdAll;
    public TableColumn<Appointment, Integer> tableColumnContactIdAll;
// customer pane
    public Text textCustomerErrorMessage;
    public TableView<Customer> tableViewCustomer;
    public TableColumn<Customer, Integer> tableColumnCustomerId;
    public TableColumn<Customer, String> tableColumnCustomerName;
    public TableColumn<Customer, String> tableColumnCustomerAddress;
    public TableColumn<Customer, String> tableColumnCustomerPostalCode;
    public TableColumn<Customer, String> tableColumnCustomerPhone;
    public TableColumn<Customer, String> tableColumnCustomerCountry;
    public TableColumn<Customer, Integer> tableColumnCustomerDivision;
// reports pane
    public Text textReportsErrorMessage;
    public ComboBox<String> comboBoxReports;
    public AnchorPane anchorPaneReports;
    public Pane paneReports;
    public ScrollPane scrollPaneReports;

    public void onRefreshClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Dashboard.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(root, 860, 600));
    }

    public void onLogoutClick(ActionEvent actionEvent) throws IOException {
        DBUtill.setCurrentUser(null);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 860, 600));
    }

    public void onScheduleAddClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 860, 600));
    }

    public void onScheduleUpdateClick(ActionEvent actionEvent) throws IOException {
        TableView<Appointment> tableView = (TableView<Appointment>) tabPaneSchedule.getSelectionModel().getSelectedItem().getContent(); // selected item is the Tab that is selected. getContent() gets the first child - which is always a TableView<Appointment> for any tab
        String tableId = tableView.getId();
        Appointment appointment = null;

        if (Objects.equals(tableId, "tableViewScheduleWeekly")) {
            appointment = tableViewScheduleWeekly.getSelectionModel().getSelectedItem();
        } else if (Objects.equals(tableId, "tableViewScheduleMonthly")) {
            appointment = tableViewScheduleMonthly.getSelectionModel().getSelectedItem();
        } else if (Objects.equals(tableId, "tableViewScheduleAll")) {
            appointment = tableViewScheduleAll.getSelectionModel().getSelectedItem();
        }

        if (appointment == null){
            textScheduleErrorMessage.setText("Please select an appointment to update.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        try {
            stage.setTitle("Update Appointment");
            UpdateAppointment updateAppointmentController = loader.getController();
            updateAppointmentController.setupVariables(appointment);
            stage.setScene(new Scene(root, 860,600));
        } catch (NullPointerException ignored) {
            textCustomerErrorMessage.setText("Please select an appointment to update.");
        }
    }

    public void onScheduleDeleteClick() {
        boolean success = false;
        int appointmentId = 0;
        String type = "";

        try {
            TableView<Appointment> tableView = (TableView<Appointment>) tabPaneSchedule.getSelectionModel().getSelectedItem().getContent(); // selected item is the Tab that is selected. getContent() gets the first child - which is always a TableView<Appointment> for any tab
            String tableId = tableView.getId();

            if (Objects.equals(tableId, "tableViewScheduleWeekly")) {
                appointmentId = tableViewScheduleWeekly.getSelectionModel().getSelectedItem().getAppointmentId();
                type = tableViewScheduleWeekly.getSelectionModel().getSelectedItem().getType();
                success = AppointmentsManager.deleteAppointment(appointmentId);
            }

            if (Objects.equals(tableId, "tableViewScheduleMonthly")) {
                appointmentId = tableViewScheduleMonthly.getSelectionModel().getSelectedItem().getAppointmentId();
                type = tableViewScheduleMonthly.getSelectionModel().getSelectedItem().getType();
                success = AppointmentsManager.deleteAppointment(appointmentId);
            }

            if (Objects.equals(tableId, "tableViewScheduleAll")) {
                appointmentId = tableViewScheduleAll.getSelectionModel().getSelectedItem().getAppointmentId();
                type = tableViewScheduleAll.getSelectionModel().getSelectedItem().getType();
                success = AppointmentsManager.deleteAppointment(appointmentId);
            }

            if (success) {
                textScheduleErrorMessage.setText("Appointment ID#" + appointmentId + " - Type: " + type + " canceled.");
                populateScheduleTables();
            } else {
                textScheduleErrorMessage.setText("Failed to delete.");
            }

        } catch (NullPointerException ignored) {
            textScheduleErrorMessage.setText("Please select an appointment to delete.");
        }

    }

    public void onCustomerAddClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root, 860, 600));
    }

    public void onCustomerUpdateClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Customer customer = tableViewCustomer.getSelectionModel().getSelectedItem();
            stage.setTitle("Update Customer");
            UpdateCustomer updateCustomerController = loader.getController();
            updateCustomerController.setupVariables(customer);
            stage.setScene(new Scene(root, 860,600));
        } catch (NullPointerException ignored) {
            textCustomerErrorMessage.setText("Please select a customer in the table to modify.");
        }

    }

    public void onCustomerDeleteClick() {

        try {
            int id = tableViewCustomer.getSelectionModel().getSelectedItem().getCustomerId();
            boolean success = CustomersManager.deleteCustomer(id);

            if (success) {
                textCustomerErrorMessage.setText("Successfully deleted record.");
                populateCustomerTable();
            } else {
                textCustomerErrorMessage.setText("Failed to delete. Check for existing appointments.");
            }

        } catch (NullPointerException ignored) {
            textCustomerErrorMessage.setText("Please select a customer in the table to delete.");
        }

    }

    public void onReportsSubmitClick() throws SQLException {
        String selection = comboBoxReports.getValue();
        // clear any previous reports shown
        anchorPaneReports.getChildren().removeAll();
        textReportsErrorMessage.setText("");

        try {
            switch (selection) {
                case "Total Appointments by Type/Month":
                    // create table view
                    TableView<AppointmentReportItem> appointmentTableView = new TableView<>();
                    appointmentTableView.setPrefHeight(280.0);
                    appointmentTableView.setPrefWidth(800.0);
                    appointmentTableView.setStyle("-fx-background-color: cadetblue");
                    paneReports.setPrefHeight(320.0);
                    scrollPaneReports.setVisible(true);
                    anchorPaneReports.getChildren().add(appointmentTableView);
                    // construct report
                    TotalAppointmentsByTypeReport totalAppointmentsByTypeReport = new TotalAppointmentsByTypeReport(appointmentTableView);
                    totalAppointmentsByTypeReport.constructReport();
                    break;
                case "Schedule by Contact":
                    // create table view
                    TableView<ContactScheduleReportItem> contactTableView = new TableView<>();
                    contactTableView.setPrefHeight(280.0);
                    contactTableView.setPrefWidth(800.0);
                    contactTableView.setStyle("-fx-background-color: cadetblue");
                    paneReports.setPrefHeight(320.0);
                    scrollPaneReports.setVisible(true);
                    anchorPaneReports.getChildren().add(contactTableView);
                    // construct report
                    ContactScheduleReport contactScheduleReport = new ContactScheduleReport(contactTableView);
                    contactScheduleReport.constructReport();
                    break;
                case "Number of Customers by Country":
                    // create table view
                    TableView<CountryReportItem> countryTableView = new TableView<>();
                    countryTableView.setPrefHeight(280.0);
                    countryTableView.setPrefWidth(800.0);
                    countryTableView.setStyle("-fx-background-color: cadetblue");
                    paneReports.setPrefHeight(320.0);
                    scrollPaneReports.setVisible(true);
                    anchorPaneReports.getChildren().add(countryTableView);
                    // construct report
                    CustomersByCountryReport customersByCountryReport = new CustomersByCountryReport(countryTableView);
                    customersByCountryReport.constructReport();
                    break;
            }

        } catch (NullPointerException nullPointerException) {
            textReportsErrorMessage.setText("Please select a valid report prior to clicking submit.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set user's name
        labelUsername.setText("User: " + DBUtill.getCurrentUser().getUsername());

        // Set time/alert header at top of page
        String stringLabel = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
        labelCurrentDatetime.setText(stringLabel);

        String appointmentWithinFifteenMinutes = AppointmentsManager.getAppointmentsWithinFifteenMinutes();
        if (appointmentWithinFifteenMinutes.isEmpty()) {
            textAlertMessage.setText("No appointments in the next fifteen minutes!");
        } else {
            textAlertMessage.setText(appointmentWithinFifteenMinutes);
        }

        // Fill reports combo box
        comboBoxReports.setItems(FXCollections.observableArrayList("Total Appointments by Type/Month", "Schedule by Contact", "Number of Customers by Country"));

        // Set customer table values
        populateCustomerTable();

        // Set schedule table values (weekly, monthly, all)
        populateScheduleTables();
    }

    private void populateCustomerTable() {
        tableViewCustomer.setItems(CustomersManager.getAllCustomers());
        tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableColumnCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableColumnCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumnCustomerDivision.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        tableColumnCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }

    private void populateScheduleTables() {
        ObservableList<Appointment> appointmentObservableList = AppointmentsManager.getAllAppointments();

        tabCurrentWeek.setText("w.e " + weekEndingDate());
        tableViewScheduleWeekly.setItems(AppointmentsManager.filterAppointments(false, appointmentObservableList));
        tableColumnIdWeekly.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnTitleWeekly.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnDescriptionWeekly.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnLocationWeekly.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnTypeWeekly.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnStartTimeWeekly.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumnEndTimeWeekly.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColumnCustomerIdWeekly.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnUserIdWeekly.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableColumnContactIdWeekly.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        tabCurrentMonth.setText(LocalDate.now().getMonth().toString());
        tableViewScheduleMonthly.setItems(AppointmentsManager.filterAppointments(true, appointmentObservableList));
        tableColumnIdMonthly.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnTitleMonthly.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnDescriptionMonthly.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnLocationMonthly.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnTypeMonthly.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnStartTimeMonthly.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumnEndTimeMonthly.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColumnCustomerIdMonthly.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnUserIdMonthly.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableColumnContactIdMonthly.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        tableViewScheduleAll.setItems(appointmentObservableList);
        tableColumnIdAll.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnTitleAll.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnDescriptionAll.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnLocationAll.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnTypeAll.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnStartTimeAll.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumnEndTimeAll.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColumnCustomerIdAll.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnUserIdAll.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableColumnContactIdAll.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    private String weekEndingDate() {
        LocalDate now = LocalDate.now();
        int dayOfYear = now.getDayOfYear();
        int year = now.getYear();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int offset = 0;

        switch (dayOfWeek) {
            case SUNDAY: offset += 6; break;
            case MONDAY: offset += 5; break;
            case TUESDAY: offset += 4; break;
            case WEDNESDAY: offset += 3; break;
            case THURSDAY: offset += 2; break;
            case FRIDAY: offset += 1; break;
            case SATURDAY: break;
        }

        LocalDate localDate = LocalDate.ofYearDay(year, (dayOfYear+offset));
        return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }
}
