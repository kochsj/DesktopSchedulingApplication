package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Customer;
import table.CountriesManager;
import table.CustomersManager;
import table.FirstLevelDivisionsManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for AddCustomer.fxml.<br><br>
 * Initializes the view by populating the country combobox with Countries from the database<br><br>
 * Handles input validation and facilitates adding a customer to the database.<br><br>
 * @see table.CustomersManager
 */
public class AddCustomer implements Initializable {
    public Text textErrorMessage;
    public TextField textFieldName;
    public TextField textFieldAddress;
    public TextField textFieldPostalCode;
    public TextField textFieldPhone;
    public ComboBox<String> comboBoxCountry;
    public ComboBox<String> comboBoxDivision;

    /**
     * AddCustomer onAction class method.<br><br>
     * Validates user input and adds a customer to the database.<br><br>
     * @param actionEvent onAction event tied to clicking the 'add' button
     * @throws IOException catch IO issues navigating to dashboard
     */
    public void onAddClick(ActionEvent actionEvent) throws IOException {
        textErrorMessage.setText("");
        String red = "-fx-border-color: red; -fx-border-radius: 10;";
        boolean hasError = false;
        int divisionId = 0;

    // VALIDATE NAME
        String name = textFieldName.getText();
        if (name.isEmpty()) {
            hasError = true;
            textFieldName.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldName.setStyle("");
        }
    // VALIDATE ADDRESS
        String address = textFieldAddress.getText();
        if (address.isEmpty()) {
            hasError = true;
            textFieldAddress.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldAddress.setStyle("");
        }
    // VALIDATE POSTAL CODE
        String postalCode = textFieldPostalCode.getText();
        if (postalCode.isEmpty()) {
            hasError = true;
            textFieldPostalCode.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldPostalCode.setStyle("");
        }
    // VALIDATE PHONE
        String phone = textFieldPhone.getText();
        if (phone.isEmpty()) {
            hasError = true;
            textFieldPhone.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        } else {
            textFieldPhone.setStyle("");
        }
    // VALIDATE DIVISION SELECTION
        try {
            divisionId = Integer.parseInt(comboBoxDivision.getValue().split("-",2)[0]);
            comboBoxDivision.setStyle("");
        } catch (NullPointerException | NumberFormatException ignored) {
            hasError = true;
            comboBoxDivision.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }
    // ADD CUSTOMER IF NO ERRORS
        if (!hasError) {
            Customer customer = new Customer(0, name, address, postalCode, phone, divisionId);
            boolean success = CustomersManager.addCustomer(customer);
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
     * On action class method for AddCustomer with no return value.<br><br>
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
     * On action class method for AddCustomer with no return value. <br><br>
     * Populates the Divison ComboBox with Divisions from the database corresponding to the selected country.
     * @see model.FirstLevelDivision
     * @see table.FirstLevelDivisionsManager
     */
    public void onCountrySelection() {
        // clear existing divisions by setting empty list
        ObservableList<String> firstLevelDivisions = FXCollections.observableArrayList();
        comboBoxDivision.setItems(firstLevelDivisions);

        // fill divisions based on country selection
        int selection = Integer.parseInt(comboBoxCountry.getValue().split("-",2)[0]);
        FirstLevelDivisionsManager.getDivisionByCountryId(selection).forEach(firstLevelDivision -> comboBoxDivision.getItems().add(firstLevelDivision.getDivisionId()+ "-" + firstLevelDivision.getDivisionName()));
    }

    /**
     * Overrides the implementation of the Initializable interface.
     * On class instantiation: populates the Country ComboBox with Countries from the database
     * @param url path relative to root scene object
     * @param resourceBundle resources creating AddAppointment instance
     * @see javafx.fxml.Initializable
     * @see table.CountriesManager
     * @see model.Country
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountriesManager.getAllCountries().forEach(country -> comboBoxCountry.getItems().add(country.getCountryId() + "-" + country.getCountryName()));
    }
}
