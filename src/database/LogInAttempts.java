package database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

/**
 * File Output class. Writes login attempts to file.
 */
public class LogInAttempts {

    /**
     * Writes login attempts to file.<br><br>
     * @param username the provided username when a login attempt is made
     * @param wasSuccessful boolean value if the login attempt was successful
     * @throws IOException problems writing to file
     * @see controller.Login
     */
    public static void logAttempt(String username, boolean wasSuccessful) throws IOException {
        File file = new File("login_activity.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String successString;

        if (wasSuccessful) {
            successString = "Successful login attempt.";
        } else {
            successString = "Unsuccessful login attempt";
        }

        Path path = Paths.get("login_activity.txt");
        long entries = Files.lines(path).count() + 1;

        String attempt = entries + ". " + successString + " - username: " + username + " - " + timestamp;

        fileWriter.write(attempt);
        fileWriter.write("\r\n");
        fileWriter.close();
    }
}
