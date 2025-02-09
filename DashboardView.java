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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {
    private Stage primaryStage;
    private UserOperations userOperations;
    private String username;

    public DashboardView(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
    }

    public BorderPane getView() {
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(20));

    // Menu Navigasi dengan Tampilan Lebih Menarik
    VBox menu = new VBox(15);
    menu.setPadding(new Insets(15));
    menu.setStyle("-fx-background-color: #2c3e50; " + 
                  "-fx-padding: 15px; " +
                  "-fx-border-radius: 10px; " + 
                  "-fx-background-radius: 10px; " +
                  "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

    Label titleLabel = new Label("Aplikasi Pengadaan");
    titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

    Button todoButton = createStyledButton("📋 Permintaan");
    todoButton.setOnAction(e -> {
        PermintaanView todoView = new PermintaanView(primaryStage, username);
        primaryStage.setScene(new Scene(todoView.getView(), 800, 600));
    });

    // Ambil data pengguna
    try {
        userOperations = new UserOperations();
    } catch (SQLException ex) {
        Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
    }

    User user = userOperations.getProfile(username);

    menu.getChildren().addAll(titleLabel, todoButton);

    // Jika user adalah admin, tambahkan tombol manajemen pengguna
    if (user != null && "admin".equalsIgnoreCase(user.getRole())) {
        Button userButton = createStyledButton("👤 Manage User");
        userButton.setOnAction(e -> {
            RegisterView registerView = new RegisterView(primaryStage, username);
            primaryStage.setScene(new Scene(registerView.getView(), 800, 600));
        });
        menu.getChildren().add(userButton);
    }

    // Tombol Logout
    Button logoutButton = createStyledButton("🚪 Logout");
    logoutButton.setOnAction(e -> {
        LoginView loginView = new LoginView(primaryStage);
        primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
    });

    menu.getChildren().add(logoutButton);
    root.setLeft(menu);

    // Tampilan Utama
    VBox content = new VBox(15);
    content.setPadding(new Insets(20));
    content.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

    Label welcomeLabel = new Label("Selamat datang, " + user.getNamauser() + "!");
    welcomeLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

    if (user != null) {
        Label profileLabel = new Label("Username: " + user.getUsername());
        Label roleLabel = new Label("Role: " + user.getRole());
        profileLabel.setStyle("-fx-font-size: 14px;");
        roleLabel.setStyle("-fx-font-size: 14px;");
        content.getChildren().addAll(welcomeLabel, profileLabel, roleLabel);
    } else {
        content.getChildren().add(welcomeLabel);
    }

    root.setCenter(content);
    return root;
}

// Metode untuk membuat tombol dengan style yang seragam
private Button createStyledButton(String text) {
    Button button = new Button(text);
    button.setStyle("-fx-padding: 12px 24px; " +
                    "-fx-font-size: 14px; " +
                    "-fx-background-color: #3498db; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 5px; " +
                    "-fx-font-weight: bold;");
    button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
    button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));
    return button;
}
}
