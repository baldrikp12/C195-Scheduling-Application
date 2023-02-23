package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.DAO;
import kbaldr2.model.DataCache;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {

    private final Locale CURRENT_LOCALE = Locale.getDefault();
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label locationLabel;

    /**
     * Sets up the login.fxml scene.
     */
    @FXML
    public void initialize() {
        //currentLocale = new Locale("fr", "FR");
        ZoneId zone = ZoneId.systemDefault();
        // Set the text for the location label based on the user's locale
        ResourceBundle messages = ResourceBundle.getBundle("Messages", CURRENT_LOCALE);

        usernameField.setPromptText(messages.getString("username.text"));
        passwordField.setPromptText(messages.getString("password.text"));
        loginButton.setText(messages.getString("login.button"));
        exitButton.setText(messages.getString("exit.button"));
        titleLabel.setText(messages.getString("title.label"));
        locationLabel.setText(messages.getString("location.label") + " " + zone);

        // Handle the login button click
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty() || !checkCredentials(username, password)) {
                logAttempt("Failed");
                errorLabel.setText(messages.getString("error.credentials"));
            } else {
                logAttempt("Success");
                try {
                    DAO.setUsername(username);
                    SceneManager.buildDashboardScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SceneManager.getStage("login").close();
            }

        });

        // Handle the cancel button click
        exitButton.setOnAction(event -> {
            // Exit the program
            System.exit(0);
        });
    }

    /**
     * Checks credentials against database.
     * If credentials check out passes sets the User_ID for DataCache.
     *
     * @param username The user's username
     * @param password The user's password
     * @return The Results
     */
    private boolean checkCredentials(String username, String password) {

        DBConnection.openConnection();
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        DataCache.setUserId(rs.getInt("User_ID"));
                        System.out.println("we got in");
                        return true;

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    /**
     * Saves login attempt to file  login_activity.txt
     * [date] [timestamp] [user] [status]
     */
    private void logAttempt(String theLoginResult) {

        String username = "null";
        int rows = getRowCount();

        try {
            File logFile = new File("login_activity.txt");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            FileWriter writer = new FileWriter(logFile, true);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            if (!usernameField.getText().isEmpty()) {
                username = usernameField.getText();
            }

            writer.write(rows + "     " + date + "     " + time + "     " + username + "     " + theLoginResult + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * @return rows The row count in login_activity.txt
     */
    private int getRowCount() {

        int rows = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("login_activity.txt"))) {
            while (br.readLine() != null) {
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private void clearFields() {

        usernameField.clear();
        passwordField.clear();
    }

    public void close() {

    }

}
