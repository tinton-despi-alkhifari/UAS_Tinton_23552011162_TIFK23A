package com.mycompany.uaspbo2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermintaanOperations {
    private Connection connection;

    public PermintaanOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create
    public void addPermintaan(Permintaan permintaan) {
        String query = "INSERT INTO permintaan (nama_user, divisi_user, perangkat, keterangan, harga, tipe, is_complete) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, permintaan.getNamaUser());
            stmt.setString(2, permintaan.getDivisiUser());
            stmt.setString(3, permintaan.getPerangkat());
            stmt.setString(4, permintaan.getKeterangan());
            stmt.setDouble(5, permintaan.calculateHarga()); // Menggunakan calculateHarga untuk mendapatkan harga yang sudah dihitung
            stmt.setString(6, permintaan.getTipe());
            stmt.setBoolean(7, permintaan.IsCompleted());
            stmt.executeUpdate();
            System.out.println("Permintaan berhasil ditambahkan.");
        } catch (SQLException e) {
            showError("Gagal menambahkan permintaan: " + e.getMessage());
        }
    }

    // Read
    public List<Permintaan> getPermintaan() {
        List<Permintaan> permintaanList = new ArrayList<>();
        String query = "SELECT * FROM permintaan";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String namaUser = rs.getString("nama_user");
                String divisiUser = rs.getString("divisi_user");
                String perangkat = rs.getString("perangkat");
                String keterangan = rs.getString("keterangan");
                double harga = rs.getDouble("harga");
                String tipe = rs.getString("tipe");
                boolean isCompleted = rs.getBoolean("is_complete");

                Permintaan permintaan = "Lokal".equalsIgnoreCase(tipe) ?
                        new PermintaanLokal(id, namaUser, divisiUser, perangkat, keterangan, harga, tipe, isCompleted) :
                        new PermintaanImport(id, namaUser, divisiUser, perangkat, keterangan, harga, tipe, isCompleted);

                permintaanList.add(permintaan);
            }
        } catch (SQLException e) {
            showError("Gagal mengambil data permintaan: " + e.getMessage());
        }
        return permintaanList;
    }

    // Update
    public void updatePermintaan(int id, String newNamaUser, String newDivisiUser, String newPerangkat, String newKeterangan, Double newHarga, String newTipe) {
        // Validasi apakah ID ada sebelum update
        if (!exists(id)) {
            showError("Gagal update: Permintaan dengan ID " + id + " tidak ditemukan.");
            return;
        }

        String query = "UPDATE permintaan SET nama_user = ?, divisi_user = ?, perangkat = ?, keterangan = ?, harga = ?, tipe = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newNamaUser);
            stmt.setString(2, newDivisiUser);
            stmt.setString(3, newPerangkat);
            stmt.setString(4, newKeterangan);

            // Update harga berdasarkan tipe permintaan
            double finalHarga = newTipe.equalsIgnoreCase("Lokal") ? newHarga : newHarga * 2; // Harga dikalikan 2 untuk tipe Impor
            stmt.setDouble(5, finalHarga);

            stmt.setString(6, newTipe);
            stmt.setInt(7, id);
            stmt.executeUpdate();
            System.out.println("Permintaan berhasil diupdate.");
        } catch (SQLException e) {
            showError("Gagal mengupdate permintaan: " + e.getMessage());
        }
    }

    // Delete
    public void deletePermintaan(int id) {
        if (!exists(id)) {
            showError("Gagal menghapus: Permintaan dengan ID " + id + " tidak ditemukan.");
            return;
        }

        String query = "DELETE FROM permintaan WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Permintaan berhasil dihapus.");
        } catch (SQLException e) {
            showError("Gagal menghapus permintaan: " + e.getMessage());
        }
    }

    // Mark as Completed
    public void markAsCompleted(int id) {
        if (!exists(id)) {
            showError("Gagal menyelesaikan: Permintaan dengan ID " + id + " tidak ditemukan.");
            return;
        }

        String query = "UPDATE permintaan SET is_complete = 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Permintaan sudah selesai diproses.");
        } catch (SQLException e) {
            showError("Gagal menandai sebagai selesai: " + e.getMessage());
        }
    }

    // Helper Method: Cek apakah ID ada dalam database sebelum update atau delete
    private boolean exists(int id) {
        String query = "SELECT COUNT(*) FROM permintaan WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            showError("Gagal memeriksa ID: " + e.getMessage());
        }
        return false;
    }

    // Show Error message (UI feedback)
    private void showError(String message) {
        System.err.println(message);  // Anda bisa mengganti ini dengan menampilkan pesan di UI.
    }
}
