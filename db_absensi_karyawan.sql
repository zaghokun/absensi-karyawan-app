/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  LENOVO
 * Created: 9 May 2026
 */

-- ============================================================
--  DATABASE: Aplikasi Absensi Karyawan
--  Mata Kuliah: Pemrograman Berorientasi Objek (PBO)
--  Tabel: tabel_karyawan, tabel_shift, tabel_absensi
-- ============================================================
 
CREATE DATABASE IF NOT EXISTS db_absensi_karyawan;
USE db_absensi_karyawan;
 
-- ============================
-- TABLE: TABEL_KARYAWAN
-- ============================
CREATE TABLE tabel_karyawan (
    id_karyawan INT PRIMARY KEY,
    nama        VARCHAR(100) NOT NULL,
    jabatan     VARCHAR(100) NOT NULL
);
 
-- ============================
-- TABLE: TABEL_SHIFT
-- ============================
CREATE TABLE tabel_shift (
    id_shift   INT PRIMARY KEY,
    nama_shift VARCHAR(50)  NOT NULL,
    jam_masuk  TIME         NOT NULL
);
 
-- ============================
-- TABLE: TABEL_ABSENSI
-- ============================
CREATE TABLE tabel_absensi (
    id_absensi    INT PRIMARY KEY,
    fk_karyawan   INT  NOT NULL,
    fk_shift      INT  NOT NULL,
    tanggal       DATE NOT NULL,
    waktu_checkin TIME NOT NULL,
    status        ENUM('Hadir Tepat Waktu', 'Terlambat', 'Alpha') NOT NULL,
    FOREIGN KEY (fk_karyawan) REFERENCES tabel_karyawan(id_karyawan)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (fk_shift)    REFERENCES tabel_shift(id_shift)
        ON UPDATE CASCADE ON DELETE RESTRICT
);