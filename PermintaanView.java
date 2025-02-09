/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uaspbo2;

/**
 *
 * @author Wendi
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class PermintaanView {
    protected PermintaanOperations permintaanOperations;
    protected TableView<Permintaan> tableView;
    protected ObservableList<Permintaan> permintaanList;
    protected Stage primaryStage;
    protected String username;
    private String userRole; // Menyimpan role pengguna

    public PermintaanView(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;

        try {
            // Ambil data role user yang sedang login
            UserOperations userOperations = new UserOperations();
            User user = userOperations.getProfile(username);
            this.userRole = (user != null) ? user.getRole() : "user"; // Default sebagai "user" jika tidak ditemukan

            permintaanOperations = new PermintaanOperations();
            permintaanList = FXCollections.observableArrayList(permintaanOperations.getPermintaan());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading permintaans: " + e.getMessage());
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

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        tableView = new TableView<>();
        tableView.setItems(permintaanList);

        TableColumn<Permintaan, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());

        TableColumn<Permintaan, String> namaUserColumn = new TableColumn<>("Nama User");
        namaUserColumn.setCellValueFactory(data -> data.getValue().namaUserProperty());

        TableColumn<Permintaan, String> divisiUserColumn = new TableColumn<>("Divisi User");
        divisiUserColumn.setCellValueFactory(data -> data.getValue().divisiUserProperty());

        TableColumn<Permintaan, String> perangkatColumn = new TableColumn<>("Detail Perangkat");
        perangkatColumn.setCellValueFactory(data -> data.getValue().perangkatProperty());

        TableColumn<Permintaan, String> keteranganColumn = new TableColumn<>("Keterangan");
        keteranganColumn.setCellValueFactory(data -> data.getValue().keteranganProperty());
        
        TableColumn<Permintaan, Double> hargaColumn = new TableColumn<>("Harga");
        hargaColumn.setCellValueFactory(data -> data.getValue().hargaProperty().asObject());
        
        TableColumn<Permintaan, String> tipeColumn = new TableColumn<>("Tipe");
        tipeColumn.setCellValueFactory(data -> data.getValue().tipeProperty());

        TableColumn<Permintaan, Boolean> isCompletedColumn = new TableColumn<>("Completed");
        isCompletedColumn.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().IsCompleted()));

        // Kolom action hanya untuk admin
        TableColumn<Permintaan, Void> actionColumn = new TableColumn<>("Actions");

        if ("admin".equalsIgnoreCase(userRole)) {
            actionColumn.setCellFactory(param -> new TableCell<>() {
                private final Button editButton = new Button("Edit");
                private final Button deleteButton = new Button("Delete");
                private final Button markCompletedButton = new Button("Mark as Completed");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Permintaan selected = getTableView().getItems().get(getIndex());
                        editButton.setOnAction(event -> showPermintaanModal(selected));
                        deleteButton.setOnAction(event -> {
                            permintaanOperations.deletePermintaan(selected.getId());
                            refreshTable();
                            showSuccess("Permintaan telah dihapus");
                        });
                        markCompletedButton.setOnAction(event -> {
                            permintaanOperations.markAsCompleted(selected.getId());
                            refreshTable();
                            showSuccess("Permintaan telah selesai");
                        });

                        HBox buttonContainer = new HBox(5, editButton, deleteButton, markCompletedButton);
                        setGraphic(buttonContainer);
                    }
                }
            });

            tableView.getColumns().addAll(idColumn, namaUserColumn, divisiUserColumn, perangkatColumn, keteranganColumn, hargaColumn, tipeColumn, isCompletedColumn, actionColumn);
        } else {
            tableView.getColumns().addAll(idColumn, namaUserColumn, divisiUserColumn, perangkatColumn, keteranganColumn, hargaColumn, tipeColumn, isCompletedColumn);
        }
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showPermintaanModal(null));

        content.getChildren().addAll(addButton, tableView);
        root.setCenter(content);

        return root;
    }

    protected void showPermintaanModal(Permintaan permintaan) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(permintaan == null ? "Add Permintaan" : "Edit Permintaan");

        TextField namaUserField = new TextField(permintaan == null ? "" : permintaan.getNamaUser());
        TextField divisiUserField = new TextField(permintaan == null ? "" : permintaan.getDivisiUser());
        TextField perangkatField = new TextField(permintaan == null ? "" : permintaan.getPerangkat());
        TextField keteranganField = new TextField(permintaan == null ? "" : permintaan.getKeterangan());
        TextField hargaField = new TextField(permintaan == null ? "" : String.valueOf(permintaan.getHarga()));
       ComboBox<String> tipeComboBox = new ComboBox<>(FXCollections.observableArrayList("Lokal", "Impor"));
       tipeComboBox.setValue(permintaan == null ? "Lokal" : permintaan.getTipe());
        
        Button saveButton = new Button(permintaan == null ? "Add" : "Save");
        saveButton.setOnAction(e -> {
    double harga;
    try {
        harga = Double.parseDouble(hargaField.getText());
    } catch (NumberFormatException ex) {
        showError("Harga harus berupa angka!");
        return;
    }

    // Create the correct Permintaan object based on the selected type
    Permintaan newPermintaan;
    if ("Lokal".equals(tipeComboBox.getValue())) {
        newPermintaan = new PermintaanLokal(0, namaUserField.getText(), divisiUserField.getText(),
            perangkatField.getText(), keteranganField.getText(), harga, tipeComboBox.getValue(), false);
    } else {
        newPermintaan = new PermintaanImport(0, namaUserField.getText(), divisiUserField.getText(),
            perangkatField.getText(), keteranganField.getText(), harga, tipeComboBox.getValue(), false);
    }

    // Set the calculated price based on the type
   // newPermintaan.setHarga(newPermintaan.calculateHarga());

    if (permintaan == null) {
        permintaanOperations.addPermintaan(newPermintaan);
        showSuccess("Permintaan added successfully!");
    } else {
        permintaanOperations.updatePermintaan(permintaan.getId(), namaUserField.getText(),
            divisiUserField.getText(), perangkatField.getText(), keteranganField.getText(), harga, tipeComboBox.getValue());
        showSuccess("Permintaan updated successfully!");
    }
    refreshTable();
    modalStage.close();
});

        VBox modalContent = new VBox(10, new Label("Nama User:"), namaUserField, new Label("Divisi User:"), divisiUserField, new Label("Detail Perangkat:"), perangkatField, new Label("Keterangan:"), keteranganField, new Label("Harga:"), hargaField,  new Label("Tipe :"), tipeComboBox, saveButton);
        modalStage.setScene(new Scene(modalContent));
        modalStage.showAndWait();
    }


    protected void refreshTable() {
        permintaanList.setAll(permintaanOperations.getPermintaan());
    }

    protected void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}