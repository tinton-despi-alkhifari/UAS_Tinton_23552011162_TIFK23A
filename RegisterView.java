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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterView {
    private Stage primaryStage;
    private UserOperations userOperations;
    private TableView<User> tableView;
    private ObservableList<User> userList;
    private String username;

    public RegisterView(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
        try {
            this.userOperations = new UserOperations();
            this.userList = FXCollections.observableArrayList(userOperations.getUsers());
        } catch (SQLException e) {
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Menu Navigasi
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));
        menu.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;"); 

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        dashboardButton.setOnAction(e -> {
            System.out.println("Dashboard");
             DashboardView dashboardView = new DashboardView(primaryStage, username); 
             primaryStage.setScene(new Scene(dashboardView.getView(), 800, 600)); 
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-padding: 10px 20px; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        menu.getChildren().addAll(new Label("Aplikasi Pengadaan"), dashboardButton, logoutButton);
        root.setLeft(menu);

        // Tampilan Utama
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // TableView
        tableView = new TableView<>();
        tableView.setItems(userList); 

        // Kolom
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());

        TableColumn<User, String> namauserColumn = new TableColumn<>("Nama User");
        namauserColumn.setCellValueFactory(data -> data.getValue().namauserProperty());
        
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(data -> data.getValue().passwordProperty());

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(data -> data.getValue().roleProperty());

        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private Button editButton = new Button("Edit");
            private Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    User selected = getTableView().getItems().get(getIndex());
                    editButton.setOnAction(event -> {
                        showUserModal(selected);
                    });

                    deleteButton.setOnAction(event -> {
                        userOperations.deleteUser(selected.getId());
                        refreshTable();
                        showSuccess("User deleted successfully!");
                    });

                    HBox buttonContainer = new HBox(5);
                    buttonContainer.getChildren().addAll(editButton, deleteButton);
                    setGraphic(buttonContainer);
                }
            }
        });

        tableView.getColumns().addAll(idColumn, namauserColumn, usernameColumn, passwordColumn, roleColumn, actionColumn);
        tableView.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;"); 
        actionColumn.setMinWidth(200); // Atur lebar minimum kolom
        
        // Add Button
        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            showUserModal(null);
        });
        
        // Add TableView to content
        content.getChildren().addAll(addButton, tableView); 

        root.setCenter(content);

        return root;
    }
    
    private void showUserModal(User user) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(user == null ? "Add User" : "Edit User");

        TextField namauserField = new TextField(user == null ? "" : user.getNamauser());
        TextField usernameField = new TextField(user == null ? "" : user.getUsername());
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        ComboBox<String> roleComboBox = new ComboBox<>(FXCollections.observableArrayList("User", "Admin"));
        roleComboBox.setValue(user == null ? "User" : user.getRole());

        Button saveButton = new Button(user == null ? "Add" : "Save");
        saveButton.setOnAction(e -> {
            String namauser = namauserField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || namauser.isEmpty()) {
                showError("Username dan password tidak boleh kosong!");
                return;
            }

            if (user == null) {
                userOperations.addUser(new User(0, namauser, username, password, role));
                showSuccess("User added successfully!");
            } else {
                userOperations.updateUser(user.getId(), namauser, username, password, role);
                showSuccess("User updated successfully!");
            }
            refreshTable();
            modalStage.close();
        });

        VBox modalContent = new VBox(10);
        modalContent.setPadding(new Insets(10));
        modalContent.getChildren().addAll(
            new Label("Nama User:"), namauserField,
            new Label("Username:"), usernameField,
            new Label("Password:"), passwordField,
            new Label("Role:"), roleComboBox,
            saveButton
        );

        Scene modalScene = new Scene(modalContent);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private void refreshTable() {
        userList.setAll(userOperations.getUsers());
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}