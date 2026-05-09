# 📋 Aplikasi Absensi Karyawan
> Tugas Project Mata Kuliah Pemrograman Berorientasi Objek (PBO)  
> Menggunakan Java Swing + MySQL dengan pola arsitektur MVC

---

## 👥 Anggota Kelompok

| No | Nama | NIM |
|----|------|-----|
| 1  | -    | -   |
| 2  | -    | -   |
| 3  | -    | -   |

---

## 🗂️ Struktur Project

```
AbsensiKaryawan/
├── Source Packages/
│   ├── absensi.Controller/
│   │   └── ControllerAbsensi.java
│   │   └── ControllerKaryawan.java
│   │   └── ControllerShift.java
│   ├── absensi.DAO/
│   │   └── DAOAbsensi.java
│   │   └── DAOKaryawan.java
│   │   └── DAOShift.java
│   ├── absensi.DAOInterface/
│   │   └── IAbsensi.java
│   │   └── IKaryawan.java
│   │   └── IShift.java
│   ├── absensi.Koneksi/
│   │   └── Koneksi.java
│   ├── absensi.Model/
│   │   └── Absensi.java
│   │   └── Karyawan.java
│   │   └── Shift.java
│   │   └── TableModelAbsensi.java
│   │   └── TableModelKaryawan.java
│   │   └── TableModelShift.java
│   └── absensi.View/
│       └── FormDashboard.java
│       └── FormCheckIn.java
│       └── FormKaryawan.java
│       └── FormShift.java
│       └── FormLaporan.java
└── Test Packages/
```

---

## 🗃️ Skema Database

Jalankan file `db_absensi_karyawan.sql` terlebih dahulu di MySQL sebelum menjalankan aplikasi.

```
tabel_karyawan ──┐
                 ├──→ tabel_absensi
tabel_shift    ──┘
```

| Tabel | Fungsi |
|-------|--------|
| `tabel_karyawan` | Master data karyawan |
| `tabel_shift` | Master data shift kerja |
| `tabel_absensi` | Transaksi check-in harian |

---

## 🏗️ Alur Pembuatan Project (2 Minggu)

---

### ✅ MINGGU 1 — Fondasi & Backend

Fokus minggu pertama: semua lapisan backend (database, koneksi, model, DAO) selesai dan teruji sebelum menyentuh tampilan.

---

#### Langkah 1 — Setup Project & Database

**Yang dibuat:** Project NetBeans baru + jalankan SQL

1. Buka NetBeans → `File > New Project > Java Application`
2. Beri nama project: `AbsensiKaryawan`
3. Tambahkan library **MySQL JDBC Connector** (`mysql-connector-j-x.x.x.jar`)
   - Klik kanan project → `Properties > Libraries > Add JAR/Folder`
4. Jalankan file `db_absensi_karyawan.sql` di MySQL Workbench
5. Verifikasi 3 tabel sudah terbuat: `tabel_karyawan`, `tabel_shift`, `tabel_absensi`

---

#### Langkah 2 — Package `absensi.Koneksi` → `Koneksi.java`

**Yang dibuat:** Satu class untuk koneksi ke database, dipakai oleh semua DAO.

```java
package absensi.Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    private static final String URL  = "jdbc:mysql://localhost:3306/db_absensi_karyawan";
    private static final String USER = "root";
    private static final String PASS = "";  // sesuaikan password MySQL kalian

    public static Connection getKoneksi() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}
```

> **Test:** Buat `main()` sementara, print `getKoneksi()`, pastikan tidak null sebelum lanjut.

---

#### Langkah 3 — Package `absensi.Model` → 3 class Model

**Yang dibuat:** Class POJO (Plain Old Java Object) yang merepresentasikan tiap tabel. Tidak ada logika bisnis di sini, hanya atribut + getter/setter.

**`Karyawan.java`**
```java
package absensi.Model;

public class Karyawan {
    private int    idKaryawan;
    private String nama;
    private String jabatan;

    // Constructor kosong
    public Karyawan() {}

    // Constructor isi semua
    public Karyawan(int idKaryawan, String nama, String jabatan) {
        this.idKaryawan = idKaryawan;
        this.nama       = nama;
        this.jabatan    = jabatan;
    }

    // Getter & Setter
    public int    getIdKaryawan() { return idKaryawan; }
    public String getNama()       { return nama; }
    public String getJabatan()    { return jabatan; }
    public void   setIdKaryawan(int idKaryawan) { this.idKaryawan = idKaryawan; }
    public void   setNama(String nama)           { this.nama = nama; }
    public void   setJabatan(String jabatan)     { this.jabatan = jabatan; }
}
```

Buat dengan pola yang sama untuk **`Shift.java`** (atribut: `idShift`, `namaShift`, `jamMasuk`) dan **`Absensi.java`** (atribut: `idAbsensi`, `fkKaryawan`, `fkShift`, `tanggal`, `waktuCheckin`, `status`).

---

#### Langkah 4 — Package `absensi.DAOInterface` → 3 Interface

**Yang dibuat:** Interface yang mendefinisikan kontrak operasi CRUD untuk tiap tabel.

**`IKaryawan.java`**
```java
package absensi.DAOInterface;

import absensi.Model.Karyawan;
import java.util.List;

public interface IKaryawan {
    boolean tambah(Karyawan k);
    boolean update(Karyawan k);
    boolean hapus(int idKaryawan);
    Karyawan getById(int idKaryawan);
    List<Karyawan> getAll();
    int getMaxId();
}
```

Buat dengan pola yang sama untuk **`IShift.java`** dan **`IAbsensi.java`**.  
Khusus `IAbsensi`, tambahkan method `List<Absensi> getByTanggal(String tanggal)` untuk kebutuhan laporan.

---

#### Langkah 5 — Package `absensi.DAO` → 3 class DAO

**Yang dibuat:** Implementasi nyata dari interface — berisi query SQL ke database.

**`DAOKaryawan.java`** (contoh method `tambah` dan `getAll`)
```java
package absensi.DAO;

import absensi.DAOInterface.IKaryawan;
import absensi.Koneksi.Koneksi;
import absensi.Model.Karyawan;
import java.sql.*;
import java.util.*;

public class DAOKaryawan implements IKaryawan {

    @Override
    public boolean tambah(Karyawan k) {
        String sql = "INSERT INTO tabel_karyawan VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, k.getIdKaryawan());
            ps.setString(2, k.getNama());
            ps.setString(3, k.getJabatan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error tambah karyawan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Karyawan> getAll() {
        List<Karyawan> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_karyawan ORDER BY id_karyawan";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Karyawan(
                    rs.getInt("id_karyawan"),
                    rs.getString("nama"),
                    rs.getString("jabatan")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAll karyawan: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT MAX(id_karyawan) FROM tabel_karyawan";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getObject(1) == null ? 1 : rs.getInt(1) + 1;
        } catch (SQLException e) {
            System.out.println("Error getMaxId: " + e.getMessage());
        }
        return 1;
    }

    // Lengkapi method update(), hapus(), getById() dengan pola yang sama
}
```

Buat **`DAOShift.java`** dan **`DAOAbsensi.java`** dengan pola yang sama.  
Khusus `DAOAbsensi`, tambahkan logika penghitungan status:
```java
// Di dalam method tambah() atau di DAOAbsensi, sebelum INSERT:
String status;
if (waktuCheckin.compareTo(jamMasukShift) <= 0) {
    status = "Hadir Tepat Waktu";
} else {
    status = "Terlambat";
}
```

---

#### Langkah 6 — Package `absensi.Model` → 3 class TableModel

**Yang dibuat:** Class `AbstractTableModel` agar data dari DAO bisa langsung ditampilkan di `JTable`.

**`TableModelKaryawan.java`**
```java
package absensi.Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModelKaryawan extends AbstractTableModel {
    private final String[] KOLOM = {"ID", "Nama", "Jabatan"};
    private List<Karyawan> data;

    public TableModelKaryawan(List<Karyawan> data) {
        this.data = data;
    }

    @Override public int getRowCount()    { return data.size(); }
    @Override public int getColumnCount() { return KOLOM.length; }
    @Override public String getColumnName(int col) { return KOLOM[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Karyawan k = data.get(row);
        switch (col) {
            case 0: return k.getIdKaryawan();
            case 1: return k.getNama();
            case 2: return k.getJabatan();
            default: return null;
        }
    }

    public Karyawan getKaryawanAt(int row) { return data.get(row); }

    public void setData(List<Karyawan> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
```

Buat **`TableModelShift.java`** dan **`TableModelAbsensi.java`** dengan pola yang sama.

> **Akhir Minggu 1:** Semua backend selesai. Tidak ada UI sama sekali. Test setiap DAO lewat `main()` sementara untuk pastikan query berjalan benar.

---

### ✅ MINGGU 2 — Frontend (View) & Controller

Fokus minggu kedua: membangun semua form Swing dan menghubungkannya ke DAO lewat Controller.

---

#### Langkah 7 — Package `absensi.Controller` → 3 class Controller

**Yang dibuat:** Jembatan antara View dan DAO. View tidak boleh langsung memanggil DAO.

**`ControllerKaryawan.java`**
```java
package absensi.Controller;

import absensi.DAO.DAOKaryawan;
import absensi.Model.Karyawan;
import java.util.List;

public class ControllerKaryawan {
    private final DAOKaryawan dao = new DAOKaryawan();

    public List<Karyawan> getAllKaryawan()        { return dao.getAll(); }
    public boolean tambahKaryawan(Karyawan k)     { return dao.tambah(k); }
    public boolean updateKaryawan(Karyawan k)     { return dao.update(k); }
    public boolean hapusKaryawan(int id)          { return dao.hapus(id); }
    public Karyawan getKaryawanById(int id)       { return dao.getById(id); }
    public int getNextId()                        { return dao.getMaxId(); }
}
```

Buat **`ControllerShift.java`** dan **`ControllerAbsensi.java`** dengan pola yang sama.

---

#### Langkah 8 — Package `absensi.View` → `FormDashboard.java`

**Yang dibuat:** Halaman utama sebagai titik masuk aplikasi dengan tombol navigasi.

- Buat JFrame baru di NetBeans: klik kanan package `absensi.View` → `New > JFrame Form`
- Beri nama `FormDashboard`
- Tambahkan komponen:
  - `JLabel` judul: **"Aplikasi Absensi Karyawan"**
  - `JButton` → **"Form Check-In"** → buka `FormCheckIn`
  - `JButton` → **"Data Karyawan"** → buka `FormKaryawan`
  - `JButton` → **"Data Shift"** → buka `FormShift`
  - `JButton` → **"Laporan Absensi"** → buka `FormLaporan`
  - `JButton` → **"Keluar"** → `System.exit(0)`
- Set `FormDashboard` sebagai class yang dijalankan di `main()`

---

#### Langkah 9 — `FormKaryawan.java` & `FormShift.java`

**Yang dibuat:** Dua form CRUD untuk master data.

Untuk setiap form, tambahkan komponen berikut:
- `JTextField` untuk setiap kolom input
- `JTable` + `JScrollPane` untuk menampilkan data
- 4 tombol: **Tambah**, **Update**, **Hapus**, **Refresh**

**Alur kerja tiap tombol:**

| Tombol | Yang dilakukan |
|--------|---------------|
| **Tambah** | Ambil nilai dari semua `JTextField` → buat object Model → panggil `controller.tambah()` → refresh tabel |
| **Update** | Klik baris di tabel → data muncul di field → edit → klik Update → panggil `controller.update()` → refresh tabel |
| **Hapus** | Klik baris di tabel → ambil id-nya → tampilkan `JOptionPane` konfirmasi → panggil `controller.hapus()` → refresh tabel |
| **Refresh** | Panggil `controller.getAll()` → set ke `TableModel` → `fireTableDataChanged()` |

```java
// Contoh event klik baris tabel → isi field otomatis
tabelKaryawan.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tabelKaryawan.getSelectedRow();
        Karyawan k = tableModel.getKaryawanAt(row);
        txtId.setText(String.valueOf(k.getIdKaryawan()));
        txtNama.setText(k.getNama());
        txtJabatan.setText(k.getJabatan());
    }
});
```

---

#### Langkah 10 — `FormCheckIn.java`

**Yang dibuat:** Form simulasi check-in karyawan — inti dari aplikasi ini.

Komponen yang dibutuhkan:
- `JComboBox` pilih karyawan (isi dari `ControllerKaryawan.getAll()`)
- `JComboBox` pilih shift (isi dari `ControllerShift.getAll()`)
- `JLabel` menampilkan tanggal hari ini (otomatis dari `LocalDate.now()`)
- `JLabel` menampilkan jam saat ini (otomatis, update tiap detik dengan `javax.swing.Timer`)
- `JButton` **"CHECK IN"** → simpan ke `tabel_absensi`
- `JTable` menampilkan riwayat absensi hari ini

**Logika tombol CHECK IN:**
```java
btnCheckIn.addActionListener(e -> {
    // 1. Ambil karyawan dan shift yang dipilih
    Karyawan karyawan = (Karyawan) comboKaryawan.getSelectedItem();
    Shift shift       = (Shift) comboShift.getSelectedItem();

    // 2. Ambil waktu sekarang dari sistem
    LocalTime waktuCheckin = LocalTime.now();
    LocalDate tanggal      = LocalDate.now();

    // 3. Hitung status otomatis
    String status = waktuCheckin.compareTo(shift.getJamMasuk()) <= 0
                    ? "Hadir Tepat Waktu" : "Terlambat";

    // 4. Buat object Absensi dan simpan
    Absensi absensi = new Absensi(
        controllerAbsensi.getNextId(),
        karyawan.getIdKaryawan(),
        shift.getIdShift(),
        tanggal.toString(),
        waktuCheckin.toString(),
        status
    );
    boolean berhasil = controllerAbsensi.tambah(absensi);

    // 5. Tampilkan notifikasi
    if (berhasil) {
        JOptionPane.showMessageDialog(this,
            "Check-in berhasil!\nStatus: " + status,
            "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        refreshTabel();
    }
});
```

---

#### Langkah 11 — `FormLaporan.java`

**Yang dibuat:** Halaman untuk melihat rekap absensi dengan filter tanggal.

Komponen yang dibutuhkan:
- `JDateChooser` atau 2 `JTextField` untuk filter tanggal awal dan akhir
- `JButton` **"Tampilkan"** → query `tabel_absensi` sesuai rentang tanggal
- `JTable` menampilkan hasil dengan kolom: Nama Karyawan, Shift, Tanggal, Jam Check-in, Status
- `JButton` **"Kembali"** → kembali ke dashboard

---

#### Langkah 12 — Finishing & Uji Coba

**Checklist sebelum demo:**

- [ ] Koneksi database berhasil di semua komputer anggota kelompok
- [ ] CRUD Karyawan: tambah, edit, hapus, tampil data berjalan benar
- [ ] CRUD Shift: tambah, edit, hapus, tampil data berjalan benar
- [ ] Form Check-In: status terhitung otomatis (Tepat Waktu / Terlambat)
- [ ] Form Check-In: tidak bisa check-in dua kali di hari yang sama (validasi duplikat)
- [ ] Laporan: data tampil sesuai filter tanggal
- [ ] Semua form bisa dibuka dan ditutup dari Dashboard tanpa error
- [ ] Tidak ada data yang hilang saat form ditutup dan dibuka kembali

---

## 🔧 Cara Menjalankan Project

1. Clone atau download repository ini
2. Buka project di **NetBeans IDE**
3. Tambahkan `mysql-connector-j-x.x.x.jar` ke Libraries project
4. Jalankan `db_absensi_karyawan.sql` di MySQL
5. Sesuaikan `USER` dan `PASS` di `Koneksi.java`
6. Klik **Run Project** (F6) — aplikasi akan membuka `FormDashboard`

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Versi | Fungsi |
|-----------|-------|--------|
| Java | 8+ | Bahasa pemrograman utama |
| Java Swing | - | Library antarmuka grafis (GUI) |
| MySQL | 8.0+ | Database |
| MySQL Connector/J | 8.x | Driver JDBC koneksi Java ke MySQL |
| NetBeans IDE | 17+ | IDE pengembangan |

---

## 📐 Pola Arsitektur MVC

```
View (Form*.java)
    │  memanggil
    ▼
Controller (Controller*.java)
    │  memanggil
    ▼
DAO (DAO*.java)  ←→  Database MySQL
    │  menggunakan
    ▼
Model (*.java)   ←  hasil query dikemas ke object ini
```

- **Model** — representasi data (sama persis dengan kolom tabel di database)
- **View** — tampilan form Swing, tidak boleh tahu soal SQL
- **Controller** — penghubung View dan DAO, berisi logika bisnis
- **DAO** — satu-satunya lapisan yang boleh menulis query SQL
- **DAOInterface** — kontrak yang memastikan semua DAO punya method yang seragam