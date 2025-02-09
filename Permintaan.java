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

public abstract class Permintaan {
    protected IntegerProperty id;
    protected StringProperty namaUser;
    protected StringProperty divisiUser;
    protected StringProperty perangkat;
    protected StringProperty keterangan;
    protected DoubleProperty harga;
    protected StringProperty tipe;
    protected BooleanProperty isCompleted;
    protected DoubleProperty isCalculateHarga;

    public Permintaan(int id, String namaUser, String divisiUser, String perangkat, String keterangan, double harga, String tipe, boolean isCompleted) {
        this.id = new SimpleIntegerProperty(id);
        this.namaUser = new SimpleStringProperty(namaUser);
        this.divisiUser = new SimpleStringProperty(divisiUser);
        this.perangkat = new SimpleStringProperty(perangkat);
        this.keterangan = new SimpleStringProperty(keterangan);
        this.harga = new SimpleDoubleProperty(harga);
        this.tipe = new SimpleStringProperty(tipe);
        this.isCompleted = new SimpleBooleanProperty(isCompleted);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNamaUser() {
        return namaUser.get();
    }

    public StringProperty namaUserProperty() {
        return namaUser;
    }

    
    public String getDivisiUser() {
        return divisiUser.get();
    }

    public StringProperty divisiUserProperty() {
        return divisiUser;
    }
    
     public String getPerangkat() {
        return perangkat.get();
    }

    public StringProperty perangkatProperty() {
        return perangkat;
    }

    public String getKeterangan() {
        return keterangan.get();
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }
    
     public Double getHarga() {
        return harga.get();
    }
     
     public void setHarga(double harga) {
        this.harga.set(harga);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }
    
    public String getTipe() {
        return tipe.get();
    }

    public StringProperty tipeProperty() {
        return tipe;
    }
    
    public boolean IsCompleted() {
        return isCompleted.get();
    }

    public BooleanProperty isCompletedProperty() {
        return isCompleted;
    }
    public abstract double calculateHarga();
}