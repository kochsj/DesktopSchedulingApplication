package table;

import database.DBUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Objects;

/**
 * Manages application CRUD operations for the Appointments table in the database.<br><br>
 * @see model.Appointment
 */
public class AppointmentsManager {

    /**
     * Static method to get a ObservableList of all appointments from the database.<br><br>
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            Connection connection = DBUtill.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM APPOINTMENTS");

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment newAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
                allAppointments.add(newAppointment);
            }
            statement.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Static method to get a ObservableList of all appointments for a given customer from the database.<br><br>
     * @param customerId the ID number for the customer to get all appointments for
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAllAppointmentsForCustomer(int customerId) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID=?";

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment newAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
                allAppointments.add(newAppointment);
            }
            preparedStatement.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Static method to convert an ObservableList of appointments into a new ObservableList filtered by month or by week.<br><br>
     * @param monthly boolean value to filter/not-filter by month
     * @param appointmentObservableList ObservableList of all appointments to filter
     * @return ObservableList of appointments filtered by month or week
     */
    public static ObservableList<Appointment> filterAppointments(boolean monthly, ObservableList<Appointment> appointmentObservableList) {
        ObservableList<Appointment> newAppointmentObservableList = FXCollections.observableArrayList();
        LocalDate localDate = LocalDate.now();

        int dayOfYear = localDate.getDayOfYear();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (monthly) {
            Month month = localDate.getMonth();
            appointmentObservableList.forEach((appointment) -> {
                if (Objects.equals(appointment.getStartTime().toLocalDateTime().toLocalDate().getMonth(), month)) {
                    newAppointmentObservableList.add(appointment);
                }
            });
        } else {
            // weekly
            // here we will define a week by the Gregorian North American Calendar, starting on a Sunday and going through Saturday
            var bounds = new Object() {
                int lowerBound = dayOfYear;
                int upperBound = dayOfYear;
            };

            switch (dayOfWeek) {
                case SUNDAY: bounds.upperBound += 6; break;
                case MONDAY: bounds.lowerBound -= 1; bounds.upperBound += 5; break;
                case TUESDAY: bounds.lowerBound -= 2; bounds.upperBound += 4; break;
                case WEDNESDAY: bounds.lowerBound -= 3; bounds.upperBound += 3; break;
                case THURSDAY: bounds.lowerBound -= 4; bounds.upperBound += 2; break;
                case FRIDAY: bounds.lowerBound -= 5; bounds.upperBound += 1; break;
                case SATURDAY: bounds.lowerBound -= 6; break;
            }

            appointmentObservableList.forEach((appointment -> {
                int appointmentDayOfYear = appointment.getStartTime().toLocalDateTime().getDayOfYear();
                if (appointmentDayOfYear >= bounds.lowerBound) {
                    if (appointmentDayOfYear <= bounds.upperBound) {
                        newAppointmentObservableList.add(appointment);
                    }
                }
            }));
        }

        return newAppointmentObservableList;
    }
    /**
     * Static method to get an appointment from the database.<br><br>
     * @param appointmentId the appointment ID number to retrieve
     * @return Appointment object
     */
    public static Appointment getAppointment(int appointmentId) {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Appointment_Id=?";
        Appointment newAppointment = null;

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, appointmentId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                newAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
            }
            preparedStatement.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newAppointment;
    }
    /**
     * Static method to add an appointment to the database.<br><br>
     * @param appointment Appointment Object to add to the database
     * @return true/false if the Appointment was added to the database
     */
    public static boolean addAppointment(Appointment appointment) {
        String sql = "INSERT into APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, appointment.getStartTime());
            preparedStatement.setTimestamp(6, appointment.getEndTime());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(8, DBUtill.getCurrentUser().getUsername());
            preparedStatement.setInt(9, appointment.getCustomerId());
            preparedStatement.setInt(10, appointment.getUserId());
            preparedStatement.setInt(11, appointment.getContactId());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * Static method to update an appointment in the database.<br><br>
     * @param appointment Appointment Object to update in the database
     * @return true/false if the Appointment was updated in the database
     */
    public static boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE APPOINTMENTS SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, appointment.getStartTime());
            preparedStatement.setTimestamp(6, appointment.getEndTime());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(8, DBUtill.getCurrentUser().getUsername());
            preparedStatement.setInt(9, appointment.getCustomerId());
            preparedStatement.setInt(10, appointment.getUserId());
            preparedStatement.setInt(11, appointment.getContactId());
            preparedStatement.setInt(12, appointment.getAppointmentId());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * Static method to delete an appointment from the database.<br><br>
     * @param appointmentId id number of the Appointment to delete from the database
     * @return true/false if the Appointment was deleted from the database
     */
    public static boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID=?";
        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, appointmentId);

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
    /**
     * Static method to check the database for any appointments that start within 15minutes of a user's local time.<br><br>
     * @return a string indicating if there are/not appointment(s) within 15min
     */
    public static String getAppointmentsWithinFifteenMinutes() {
        String sql = "SELECT * FROM APPOINTMENTS WHERE User_ID=?";
        int userId = DBUtill.getCurrentUser().getUserId();
        int appointmentId = 0;
        String dateTimeString = "";
        long currentTimeMs = System.currentTimeMillis();

        try {
            Connection connection = DBUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long start = rs.getTimestamp("Start").getTime();
                long difference = (start - currentTimeMs);
                // 15 min = 900,000ms
                if (difference<=900000 && difference>=0) {
                    appointmentId = rs.getInt("Appointment_ID");
                    dateTimeString += ("Appointment #" + appointmentId + " starts in " + (difference/60000) + "minutes.\nScheduled: ");
                    dateTimeString += (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getTimestamp("Start")) + "\n");
                }
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return dateTimeString;
    }
}
