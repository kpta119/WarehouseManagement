package com.example.magazyn;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class MainWindowController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label connectionStatusLabel;

    public void initialize() {
        checkConnection();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private void checkConnection() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            if (conn.isValid(2)) {
                connectionStatusLabel.setText("Połączenie OK ");
            }
        } catch (SQLException e) {
            connectionStatusLabel.setText("Connection error: " + e.getMessage());
        }
    }
}
