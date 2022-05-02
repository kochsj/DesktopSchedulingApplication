package controller;

import database.DBUtill;
import database.LogInAttempts;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import table.UsersManager;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login  implements Initializable {
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Text textErrorMessage;
    public Label labelZoneId;
    public Text textTitle;
    public Text textUsername;
    public Text textPassword;
    public Button buttonLogin;

    public void onLoginClick(ActionEvent actionEvent) throws IOException {
        // get user locale to assign language resources
        Locale locale = Locale.getDefault();
        ResourceBundle loginStrings = ResourceBundle.getBundle("Login", locale);

        textErrorMessage.setText("");
        textFieldUsername.setStyle("");
        textFieldPassword.setStyle("");

        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        if (username.isEmpty()) {
            textFieldUsername.setStyle("-fx-border-color: red; -fx-border-radius: 10;");
            textErrorMessage.setText(loginStrings.getString("e_username"));
            if (password.isEmpty()) {
                textFieldPassword.setStyle("-fx-border-color: red; -fx-border-radius: 10;");
                textErrorMessage.setText(loginStrings.getString("e_username_password"));
            }
        } else if (password.isEmpty()) {
            textFieldPassword.setStyle("-fx-border-color: red; -fx-border-radius: 10;");
            textErrorMessage.setText(loginStrings.getString("e_password"));
        } else {
            int userId = UsersManager.validateUser(username, password);
            if (userId>=0) {
                LogInAttempts.logAttempt(username, true);
                User user = new User(userId, username);
                DBUtill.setCurrentUser(user);

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Dashboard.fxml")));
                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Desktop Scheduling Application");
                stage.setScene(new Scene(root, 860, 600));
            } else {
                LogInAttempts.logAttempt(username, false);
                textErrorMessage.setText(loginStrings.getString("e_login"));
                textFieldUsername.setStyle("-fx-border-color: red; -fx-border-radius: 10;");
                textFieldPassword.setStyle("-fx-border-color: red; -fx-border-radius: 10;");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // gets the default locale for the JVM when created by the user
        Locale locale = Locale.getDefault();
        ZoneId zoneId = ZoneId.systemDefault();

        String stringZoneId = zoneId.toString();
        ResourceBundle loginStrings = ResourceBundle.getBundle("Login", locale);

        textTitle.setText(loginStrings.getString("label"));
        textUsername.setText(loginStrings.getString("username"));
        textPassword.setText(loginStrings.getString("password"));
        buttonLogin.setText(loginStrings.getString("login"));
        labelZoneId.setText(loginStrings.getString("zone") + " " + stringZoneId);

    }
}
