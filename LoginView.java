/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uaspbo2;

/**
 *
 * @author Wendi
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage primaryStage;
    private UserOperations userOperations;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public VBox getView() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #4ca1af); " +
                      "-fx-alignment: center;");

        // Judul
        Label titleLabel = new Label("🔑 Aplikasi Pengadaan");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Field Username
        TextField usernameField = createStyledTextField("Username");

        // Field Password
        PasswordField passwordField = createStyledPasswordField("Password");

        // Tombol Login
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #27ae60; " +
                             "-fx-text-fill: white; " +
                             "-fx-font-size: 16px; " +
                             "-fx-padding: 12px 30px; " +
                             "-fx-background-radius: 5px;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                userOperations = new UserOperations();
            } catch (SQLException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (userOperations.loginUser(username, password)) {
                DashboardView dashboardView = new DashboardView(primaryStage, username);
                Scene dashboardScene = new Scene(dashboardView.getView(), 800, 600);
                primaryStage.setScene(dashboardScene);
            } else {
                showError("❌ Login gagal! Periksa username dan password Anda.");
            }
        });

        root.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);
        return root;
    }

    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-pref-width: 320px; " +
                           "-fx-padding: 12px; " +
                           "-fx-font-size: 14px; " +
                           "-fx-border-radius: 5px; " +
                           "-fx-background-radius: 5px; " +
                           "-fx-border-color: #bdc3c7;");
        return textField;
    }

    private PasswordField createStyledPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.setStyle("-fx-pref-width: 320px; " +
                               "-fx-padding: 12px; " +
                               "-fx-font-size: 14px; " +
                               "-fx-border-radius: 5px; " +
                               "-fx-background-radius: 5px; " +
                               "-fx-border-color: #bdc3c7;");
        return passwordField;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 14px;");
        alert.showAndWait();
    }
}
