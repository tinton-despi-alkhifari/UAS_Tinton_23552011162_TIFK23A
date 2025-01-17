# UAS
UAS-TIF K 23 A
# Final Proyek Pemrograman Berorientasi Obyek 1
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 1</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Kelompok
<ul>
  <li>Kelompok: 9</li>
  <li>Proyek: Aplikasi ITFix & Supply</li>
  <li>Anggota:</li>
  <ul>
    <li>Ketua: <a href="">Wendi Rahmawan</a></li>
    <li>Anggota 1: <a href="">Tinton Despi Alkhifari</a></li>
    <li>Anggota 2: <a href="">Ichsan Pratama Putra</a></li>
  </ul>
</ul> 

## Judul Studi Kasus
<p>Aplikasi ITFix & Supply</p>

## Penjelasan Studi Kasus
<p>Aplikasi ITFix & Supply adalah sistem manajemen yang dirancang khusus untuk membantu pemilik jasa IT dalam mengelola permintaan dari pengguna terkait perbaikan perangkat dan pengadaan perangkat baru. Aplikasi ini menyediakan formulir yang berbeda untuk masing-masing jenis permintaan, yaitu:

Permintaan Perbaikan Perangkat: Formulir ini memungkinkan pengguna untuk mengajukan permintaan perbaikan perangkat yang rusak, mencakup informasi seperti jenis kerusakan, keluhan, jenis perangkat yang rusak, serta apakah ada kebutuhan untuk penggantian sparepart.

Permintaan Pengadaan Perangkat Baru: Formulir ini digunakan oleh pengguna yang membutuhkan pengadaan perangkat baru, baik dalam jumlah besar maupun kecil, dengan memasukkan informasi tentang jenis perangkat, kebutuhan, kualitas barang (original atau lokal), serta pemasok barang (dalam atau luar negeri).  </p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>Implementasi dari Inheritance terletak pada kelas JenisPermintaan sebagai kelas induk, dengan kelas turunan Perbaikan dan Pengadaan.

Kelas JenisPermintaan mencakup atribut umum seperti JenisPerangkat, MerkPerangkat dan NamaUser.
Kelas Perbaikan memiliki tambahan atribut:
Keluhan, untuk mencatat masalah perangkat.
JenisKerusakan, yang menentukan apakah kerusakan termasuk "berat" atau "ringan".
SparePart, untuk menentukan apakah ada penggantian suku cadang.
Kelas Pengadaan memiliki tambahan atribut:
Kebutuhan, untuk mencatat jumlah atau spesifikasi barang yang diperlukan.
KualitasBarang, untuk mencatat apakah barang bersifat "Original" atau "Lokal".
PemasokBarang, untuk mencatat apakah pemasok berasal dari "Dalam Negeri" atau "Luar Negeri".
Implementasi pewarisan:
Saat pengguna melakukan input data permintaan, atribut dari kelas induk seperti JenisPerangkat dan MerkPerangkat harus diisi terlebih dahulu sebelum data spesifik dari kelas turunan (Perbaikan atau Pengadaan) dimasukkan.</p>

### 2. Encapsulation
<p>Kelas Induk jenis permintaan ini menyimpan atribut umum yang dimiliki oleh semua jenis permintaan, seperti jenisPerangkat dan merkPerangkat. Variabel-variabel ini diatur sebagai private untuk menjaga data tetap aman, dan hanya dapat diakses melalui metode getter dan setter. Kelas Turunan Perbaikan ini menambahkan atribut khusus seperti keluhan dan metode untuk menghitung harga berdasarkan jenis kerusakan (jenisKerusakan) dan penggunaan sparepart. Sedangkan Kelas Turunan Pengadaan ini menambahkan atribut khusus seperti kebutuhan dan metode untuk menghitung harga berdasarkan kualitas barang dan pemasok.</p>

### 3. Polymorphism
<p>Implementasi dari Polimorphism ini ada Pada metode hitung harga, cara perhitungan harga bisa berbeda antara jenis permintaan. Perbaikan menghitung harga berdasarkan jenis Kerusakan dan Apa ada SparePart yang perlu diganti atau tidak, sementara Pengadaan menghitung harga berdasarkan kualitas barang (Original atau KW) dan pemasok (luar atau dalam negeri).</p>

### 4. Abstract
<p>LayananPermintaan adalah kelas abstrak yang menyediakan struktur dasar untuk mengelola permintaan, dengan metode abstrak seperti inputPermintaan(), prosesPermintaan() dan selesaikanPermintaan().
Subclass Perbaikan mengimplementasikan metode ini dengan logika untuk menangani keluhan dan penggantian sparepart.
Subclass Pengadaan mengimplementasikan metode ini untuk menentukan kebutuhan barang, kualitas, dan pemasok.</p>

## Struktur Tabel Aplikasi
<p>Lorem ipsum sir dolor amet.</p>

## Tampilan Aplikasi
<p>Lorem ipsum sir dolor amet.</p>

## Demo Proyek
<ul>
  <li>Github: <a href="">Github</a></li>
  <li>Youtube: <a href="">Youtube</a></li>
</ul>
