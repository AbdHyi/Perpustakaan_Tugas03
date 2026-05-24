-- ============================================
-- SETUP DATABASE PERPUSTAKAAN
-- Jalankan di HeidiSQL / phpMyAdmin / MySQL CLI
-- ============================================

-- Query terakhir diedit pada Minggu, 24 Mei 2026

-- 1. Buat database
CREATE DATABASE IF NOT EXISTS perpustakaan;

-- 2. Gunakan database tersebut
USE perpustakaan;

-- 3. Buat tabel buku
CREATE TABLE IF NOT EXISTS buku (
    id           INT          AUTO_INCREMENT PRIMARY KEY,
    judul        VARCHAR(150) NOT NULL,
    pengarang    VARCHAR(100) NOT NULL,
    tahun_terbit INT,
    stok         INT          DEFAULT 0
);

-- (Opsional) Lihat isi tabel
-- SELECT * FROM buku;
