/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uaspbo2;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class PermintaanImport extends Permintaan {
    public PermintaanImport(int id, String namaUser, String divisiUser, String perangkat, String keterangan, double harga, String tipe, boolean isCompleted) {
        super(id, namaUser, divisiUser, perangkat, keterangan, harga, tipe, isCompleted);
    }

    @Override
    public double calculateHarga() {
        // Implementasi perhitungan harga, contoh sederhana:
        return getHarga() * 2; // Misalnya ada kenaikan 10% untuk permintaan import
    }
}

    
