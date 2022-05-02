package controller;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import table.AppointmentsManager;
import table.ContactsManager;
import table.CustomersManager;
import table.UsersManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Controller class for AddAppointment.fxml.<br><br>
 * Initializes the view with a table showing all appointments currently in the database and with fields for the user to input information for a new appointment.<br><br>
 * Handles input validation and facilitates adding an appointment to the database.<br><br>
 * @see table.AppointmentsManager
 */
public class AddAppointment implements Initializable {

    public TableView<Appointment> tableViewSchedule;
    public TableColumn<Appointment, Integer> tableColumnId;
    public TableColumn<Appointment, String> tableColumnTitle;
    public TableColumn<Appointment, String> tableColumnDescription;
    public TableColumn<Appointment, String> tableColumnLocation;
    public TableColumn<Appointment,String> tableColumnType;
    public TableColumn<Appointment, Integer> tableColumnStartTime;
    public TableColumn<Appointment, Integer> tableColumnEndTime;
    public TableColumn<Appointment, Integer> tableColumnCustomerId;
    public TableColumn<Appointment, Integer> tableColumnUserId;
    public TableColumn<Appointment, Integer> tableColumnContactId;

    public TextField textFieldTitle;
    public TextField textFieldDescription;
    public TextField textFieldLocation;
    public TextField textFieldType;

    public ComboBox<String> comboBoxContact;
    public ComboBox<String> comboBoxCustomer;
    public ComboBox<String> comboBoxUser;
    public ComboBox<String> comboBoxStartTime;
    public ComboBox<String> comboBoxEndTime;
    public DatePicker datePickerStart;
    public DatePicker datePickerEnd;
    public Text textErrorMessage;

    /**
     * AddAppointment onAction class method.<br><br>
     * Adds an appointment to the database.<br><br>
     * Validates user input and checks for rule violations. Violations include: scheduling an appointment that overlaps with an existing appointment for a given customer, scheduling an appointment outside of the office hours (8:00AM to 10:00PM EST), and scheduling an appointment on the weekend.<br><br>
     * @param actionEvent onAction event tied to clicking the 'add' button
     * @throws IOException catch IO issues navigating to dashboard
     */
    public void onAddClick(ActionEvent actionEvent) throws IOException {
        textErrorMessage.setText("");
        String red = "-fx-border-color: red; -fx-border-radius: 10;";
        var error = new Object() {
            boolean hasError = false;
        };

    // VALIDATE TITLE
        String title = textFieldTitle.getText();
        if (title.isEmpty()) {
            error.hasError = true;
            textFieldTitle.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldTitle.setStyle("");
        }

    // VALIDATE DESCRIPTION
        String description = textFieldDescription.getText();
        if (description.isEmpty()) {
            error.hasError = true;
            textFieldDescription.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldDescription.setStyle("");
        }

    // VALIDATE LOCATION
        String location = textFieldLocation.getText();
        if (location.isEmpty()) {
            error.hasError = true;
            textFieldLocation.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldLocation.setStyle("");
        }

    // VALIDATE TYPE
        String type = textFieldType.getText();
        if (type.isEmpty()) {
            error.hasError = true;
            textFieldType.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldType.setStyle("");
        }

    // VALIDATE START DATE & TIME (TIMESTAMP)
        Timestamp timestampStart = null;
        try {
            LocalDate localDateStart = datePickerStart.getValue();
            String stringStartTime = comboBoxStartTime.getValue();
            datePickerStart.setStyle("");
            comboBoxStartTime.setStyle("");

            timestampStart = createTimestampUTC(localDateStart, stringStartTime);
        } catch (NullPointerException nullPointerException) {
            error.hasError = true;
            datePickerStart.setStyle(red);
            comboBoxStartTime.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }

    // VALIDATE END DATE & TIME (TIMESTAMP)
        Timestamp timestampEnd = null;
        try {
            LocalDate localDateEnd = datePickerEnd.getValue();
            String stringEndTime = comboBoxEndTime.getValue();
            datePickerEnd.setStyle("");
            comboBoxEndTime.setStyle("");

            timestampEnd = createTimestampUTC(localDateEnd, stringEndTime);

        } catch (NullPointerException nullPointerException) {
            error.hasError = true;
            datePickerEnd.setStyle(red);
            comboBoxEndTime.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }

    // VALIDATE CUSTOMER SELECTION
        int customerId = 0;
        try {
            customerId = Integer.parseInt(comboBoxCustomer.getValue().split("-",2)[0]);
            comboBoxCustomer.setStyle("");
        } catch (NullPointerException nullPointerException){
            error.hasError = true;
            comboBoxCustomer.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }

    // VALIDATE USER SELECTION
        int userId = 0;
        try {
            userId = Integer.parseInt(comboBoxUser.getValue().split("-",2)[0]);
            comboBoxUser.setStyle("");
        } catch (NullPointerException nullPointerException){
            error.hasError = true;
            comboBoxUser.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }

    // VALIDATE CONTACT SELECTION
        int contactId = 0;
        try {
            contactId = Integer.parseInt(comboBoxContact.getValue().split("-",2)[0]);
            comboBoxContact.setStyle("");
        } catch (NullPointerException ignored){
            error.hasError = true;
            comboBoxContact.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }

    // CHECK RULE: NO WEEKENDS
        // cannot schedule on weekend (SATURDAY & SUNDAY)
        DayOfWeek dayOfWeekStart = timestampStart.toLocalDateTime().getDayOfWeek();
        DayOfWeek dayOfWeekEnd = timestampEnd.toLocalDateTime().getDayOfWeek();

        if (dayOfWeekStart == DayOfWeek.SATURDAY || dayOfWeekStart == DayOfWeek.SUNDAY || dayOfWeekEnd == DayOfWeek.SATURDAY || dayOfWeekEnd == DayOfWeek.SUNDAY) {
            datePickerStart.setStyle(red);
            datePickerEnd.setStyle(red);
            textErrorMessage.setText("Cannot schedule appointment on the weekend.");
            error.hasError = true;
        }

    // CHECK RULE: BUSINESS HOURS
        // per requirements: cannot "schedule an appointment outside business hours defined as 8am to 10pm EST"
        // 8am EST = 12:00 UTC/GMT
        // 10pm EST = 02:00 UTC/GMT
        // values between 2:00 - 12:00 are not valid
        long startHours = (TimeUnit.MILLISECONDS.toHours(timestampStart.getTime())%24);
        long endHours = (TimeUnit.MILLISECONDS.toHours(timestampEnd.getTime())%24);

        if (startHours>2 && startHours<12) {
            comboBoxStartTime.setStyle(red);
            textErrorMessage.setText("Outside business hours.");
            error.hasError = true;
        } else if (endHours>2 && endHours<12) {
            comboBoxEndTime.setStyle(red);
            textErrorMessage.setText("Outside business hours.");
            error.hasError = true;
        } else if (startHours<2 && endHours>12) {
            comboBoxEndTime.setStyle(red);
            textErrorMessage.setText("Outside business hours.");
            error.hasError = true;
        } else {
            comboBoxStartTime.setStyle("");
            comboBoxEndTime.setStyle("");
        }

    // CHECK RULE: NO CUSTOMER OVERLAP
        long newStartTimeMilliseconds = timestampStart.getTime();
        long newEndTImeMilliseconds = timestampEnd.getTime();

        ObservableList<Appointment> customerAppointmentList = AppointmentsManager.getAllAppointmentsForCustomer(customerId);
        customerAppointmentList.forEach((appointment -> {

            long appointmentStartTimeMilliseconds = appointment.getStartTime().getTime();
            long appointmentEndTimeMilliseconds = appointment.getEndTime().getTime();

            // new start falls between the appointments start & end time
            if (newStartTimeMilliseconds>=appointmentStartTimeMilliseconds && newStartTimeMilliseconds<=appointmentEndTimeMilliseconds) {
                comboBoxStartTime.setStyle(red);
                textErrorMessage.setText("Start time overlaps a scheduled appointment.");
                error.hasError = true;
            // new end ALSO falls between the appointment start and end time
                if (newEndTImeMilliseconds>=appointmentStartTimeMilliseconds && newEndTImeMilliseconds<=appointmentEndTimeMilliseconds) {
                    comboBoxEndTime.setStyle(red);
                    textErrorMessage.setText("Start and end times overlap a scheduled appointment.");
                    error.hasError = true;
                }
            // new end time falls between the appointments start and end time
            } else if (newEndTImeMilliseconds>=appointmentStartTimeMilliseconds && newEndTImeMilliseconds<=appointmentEndTimeMilliseconds) {
                comboBoxEndTime.setStyle(red);
                textErrorMessage.setText("End time overlaps a scheduled appointment.");
                error.hasError = true;
            }
        }));

    // ADD APPOINTMENT TO DATA BASE IF THERE ARE NO ERRORS
        if (!error.hasError) {
            Appointment appointment = new Appointment(0, title, description, location,type, timestampStart, timestampEnd, customerId, userId, contactId);
            boolean success = AppointmentsManager.addAppointment(appointment);
            if (success) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Dashboard.fxml")));
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Desktop Scheduling Application");
                stage.setScene(new Scene(root, 860, 600));
            } else {
                textErrorMessage.setText("Failed to add Appointment.");
            }
        }
    }
    /**
     * On action class method for AddAppointment with no return value.<br><br>
     * Loads the Dashboard and sets the scene - returns the user to the Dashboard resource.<br><br>
     * @param actionEvent onAction event tied to clicking the 'cancel' button
     * @throws IOException catch problems with IO
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Dashboard.fxml")));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(root, 860, 600));
    }

    /**
     * Overrides the implementation of the Initializable interface.
     * On class instantiation: populates the schedule table with Appointments from the database, populates the ComboBoxes (Contact, Customer, User) from the database, and sets start/end time ComboBox values.<br><br>
     * @param url path relative to root scene object
     * @param resourceBundle resources creating AddAppointment instance
     * @see javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewSchedule.setItems(AppointmentsManager.getAllAppointments());
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumnEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableColumnContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        ContactsManager.getAllContacts().forEach(contact -> comboBoxContact.getItems().add(contact.getContactId() + "-" + contact.getContactName()));
        CustomersManager.getAllCustomers().forEach(customer -> comboBoxCustomer.getItems().add(customer.getCustomerId() + "-" + customer.getCustomerName()));
        UsersManager.getAllUsers().forEach(user -> comboBoxUser.getItems().add(user.getUserId() + "-" + user.getUsername()));

        comboBoxStartTime.setItems(FXCollections.observableArrayList("12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am", "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm", "10:00pm", "10:30pm", "11:00pm", "11:30pm"));
        comboBoxEndTime.setItems(FXCollections.observableArrayList("12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am", "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm", "10:00pm", "10:30pm", "11:00pm", "11:30pm"));

    }

    /**
     * Converts a LocalDate Object and String Object into a Timestamp value.<br><br>
     * @param localDate LocalDate object in the format yyyy-MM-dd
     * @param timeString string specific to combobox (see TimeTable)
     * @return A sql Timestamp Object representing corresponding date and time
     * @see model.TimeTable
     */
    private Timestamp createTimestampUTC(LocalDate localDate, String timeString) {

        // get users intended time from form
        LocalTime time = TimeTable.getTime(timeString);
        LocalTime timeUTC = convertToUTC(time);

        long hours = timeUTC.getHour();
        long msHours = hoursToMilliseconds(hours);
        long minutes = timeUTC.getMinute();
        long msMinutes = minutesToMilliseconds(minutes);
        long days = localDate.toEpochDay();
        long msDays = daysToMilliseconds(days);

        long ms = msDays + msHours + msMinutes;
        return new Timestamp(ms);

    }

    /**
     * Converts minutes to milliseconds.<br><br>
     * @param minutes a long value of minutes
     * @return the corresponding number of milliseconds
     */
    private long minutesToMilliseconds(long minutes) {
        return (minutes * 60000);
    }

    /**
     * Converts hours to milliseconds.<br><br>
     * @param hours a long value of minutes
     * @return the corresponding number of milliseconds
     */
    private long hoursToMilliseconds(long hours) {
        return (hours * 3600000);
    }

    /**
     * Converts days to milliseconds.<br><br>
     * @param days a long value of minutes
     * @return the corresponding number of milliseconds
     */
    private long daysToMilliseconds(long days) {
        return (days * 86400000);
    }

    /**
     * Calculates the number of hours that the user is offset from UTC/GMT. <br><br>
     * @return the number of hours (-18 to +18) that the user is offset from UTC
     */
    private long calculateUTCOffset() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(LocalDateTime.now());
        return zoneOffset.getLong(ChronoField.OFFSET_SECONDS)/3600;
    }

    /**
     * Converts a LocalTime Object into the corresponding UTC LocalTime
     * @param time the LocalTime to be converted
     * @return the corresponding equivalent LocalTime at UTC/GMT
     */
    private LocalTime convertToUTC(LocalTime time) {
        long offsetHoursFromUTC = calculateUTCOffset();
        return time.plusHours(-1*offsetHoursFromUTC);
    }

}
