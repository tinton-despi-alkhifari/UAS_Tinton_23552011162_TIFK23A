/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uaspbo2;

/**
 *
 * @author Wendi
 */
import javafx.beans.property.*;

public class User {
    private IntegerProperty id;
    private StringProperty namauser;
    private StringProperty username;
    private StringProperty password;
    private StringProperty role;

    public User(int id, String namauser, String username, String password, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.namauser = new SimpleStringProperty(namauser);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    // Getter and Property for 'id'
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // Getter and Property for 'username'
    public String getNamauser() {
        return namauser.get();
    }

    public StringProperty namauserProperty() {
        return namauser;
    }

    public void setNamauser(String namauser) {
        this.namauser.set(namauser);
    }
    
    // Getter and Property for 'username'
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    // Getter and Property for 'password'
    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getRole() {
        return role.get();
    }
}