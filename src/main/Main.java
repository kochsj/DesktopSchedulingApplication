package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * Main runnable class extending the Application class.<br><br>
 * Adds Login scene to the application stage.<br><br>
 * @see controller.Login
 * @see javafx.application.Application
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(root, 860,600));
        stage.show();
    }
}
