/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uaspbo2;

public class PermintaanLokal extends Permintaan {
    
    public PermintaanLokal(int id, String namaUser, String divisiUser, String perangkat, String keterangan, double harga, String tipe, boolean isCompleted) {
        super(id, namaUser, divisiUser, perangkat, keterangan, harga, tipe, isCompleted);
    }

    @Override
public double calculateHarga() {
    return getHarga(); // Menggunakan getter
}
}  
