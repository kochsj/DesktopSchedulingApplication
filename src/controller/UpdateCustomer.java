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
import model.Country;
import model.Customer;
import table.CountriesManager;
import table.CustomersManager;
import table.FirstLevelDivisionsManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for UpdateCustomer.fxml.<br><br>
 * Initializes the view with fields populated with current data for an existing appointment.<br><br>
 * Handles input validation and facilitates updating a customer in the database.<br><br>
 * @see table.CustomersManager
 */
public class UpdateCustomer implements Initializable {
    public Text textErrorMessage;
    public TextField textFieldId;
    public TextField textFieldName;
    public TextField textFieldAddress;
    public TextField textFieldPostalCode;
    public TextField textFieldPhone;
    public ComboBox<String> comboBoxCountry;
    public ComboBox<String> comboBoxDivision;

    /**
     * Instance method to establish initial field values when UpdateCustomer view is loaded.<br><br>
     * @param customer Customer Object passed during instantiation to populate fields
     */
    void setupVariables(Customer customer){
        int id = customer.getCustomerId();
        int divisionId = customer.getDivisionId();
        String name = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();

        textFieldId.setText(Integer.toString(id));
        textFieldName.setText(name);
        textFieldAddress.setText(address);
        textFieldPostalCode.setText(postalCode);
        textFieldPhone.setText(phone);

        CountriesManager.getAllCountries().forEach(country -> comboBoxCountry.getItems().add(country.getCountryId() + "-" + country.getCountryName()));
        Country currentCountry = FirstLevelDivisionsManager.getCountry(divisionId);
        String currentCountryString = currentCountry.getCountryId() + "-" + currentCountry.getCountryName();
        comboBoxCountry.setValue(currentCountryString);

        FirstLevelDivisionsManager.getDivisionByCountryId(currentCountry.getCountryId()).forEach(division -> comboBoxDivision.getItems().add(division.getDivisionId() + "-" + division.getDivisionName()));
        String currentDivisionString = divisionId + "-" + FirstLevelDivisionsManager.getDivision(divisionId).getDivisionName();
        comboBoxDivision.setValue(currentDivisionString);

    }

    /**
     * UpdateCustomer onAction class method.<br><br>
     * Validates user input and updates an existing customer in the database.<br><br>
     * @param actionEvent onAction event tied to clicking the 'update' button
     * @throws IOException catch IO issues navigating to dashboard
     */
    public void onUpdateClick(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(textFieldId.getText());
        boolean hasError = false;
        int divisionId = 0;
        String red = "-fx-border-color: red; -fx-border-radius: 10;";
        textErrorMessage.setText("");

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

    // VALIDATE DIVISION
        try {
            divisionId = Integer.parseInt(comboBoxDivision.getValue().split("-",2)[0]);
            comboBoxDivision.setStyle("");
        } catch (NullPointerException | NumberFormatException ignored) {
            hasError = true;
            comboBoxDivision.setStyle(red);
            textErrorMessage.setText("Please fix invalid field(s).");
        }
    // UPDATE CUSTOMER IF NO ERRORS
        if (!hasError) {
            Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
            boolean success = CustomersManager.updateCustomer(customer);

            if (success) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Dashboard.fxml")));
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Desktop Scheduling Application");
                stage.setScene(new Scene(root, 860, 600));
            } else {
                textErrorMessage.setText("Failed to update Customer.");
            }
        }
    }

    /**
     * On action class method for UpdateCustomer with no return value.<br><br>
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
     * On action class method for UpdateCustomer with no return value. <br><br>
     * Populates the Divison ComboBox with Divisions from the database corresponding to the selected country.
     * @see model.FirstLevelDivision
     * @see table.FirstLevelDivisionsManager
     */
    public void onCountrySelection() {
        ObservableList<String> firstLevelDivisions = FXCollections.observableArrayList();
        comboBoxDivision.setItems(firstLevelDivisions);
        int selection = Integer.parseInt(comboBoxCountry.getValue().split("-",2)[0]);
        FirstLevelDivisionsManager.getDivisionByCountryId(selection).forEach(firstLevelDivision -> comboBoxDivision.getItems().add(firstLevelDivision.getDivisionId()+ "-" + firstLevelDivision.getDivisionName()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
