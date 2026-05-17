/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOAbsensi;
import absensi.Model.Absensi;
import absensi.Model.TableModelAbsensi;
import absensi.Model.Shift;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControllerAbsensi {

    private DAOAbsensi dao = new DAOAbsensi();

    // Referensi ke komponen form
    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtCari;
    private JComboBox  comboShift;
    private JComboBox  comboStatus;
    private JLabel     labelTanggal;
    private JLabel     labelJam;
    private JTable     table;
    
    public ControllerAbsensi() {
    }

    // Simpan id absensi yang sedang dipilih di tabel
    private int selectedIdAbsensi = -1;

    // Constructor — form mengoper komponen-komponennya ke sini
    public ControllerAbsensi(JTextField txtId, JTextField txtNama,
                              JTextField txtCari, JComboBox comboShift,
                              JComboBox comboStatus, JLabel labelTanggal,
                              JLabel labelJam, JTable table) {
        this.txtId        = txtId;
        this.txtNama      = txtNama;
        this.txtCari      = txtCari;
        this.comboShift   = comboShift;
        this.comboStatus  = comboStatus;
        this.labelTanggal = labelTanggal;
        this.labelJam     = labelJam;
        this.table        = table;
    }

    // ===================== INISIALISASI =====================

    public void init() {
        // Tanggal otomatis
        labelTanggal.setText("Tanggal: " + LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        // Jam otomatis tampil pertama kali
        labelJam.setText("Jam: " + LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Isi statusCombo
        comboStatus.removeAllItems();
        comboStatus.addItem("Hadir Tepat Waktu");
        comboStatus.addItem("Terlambat");
        comboStatus.addItem("Alpha");

        // Field nama tidak bisa diedit
        txtNama.setEditable(false);
        txtNama.setBackground(java.awt.Color.LIGHT_GRAY);

        // Kosongkan field
        reset();

        // Load tabel
        isiTable();
    }

    // ===================== ISI TABEL =====================

    public void isiTable() {
        String tanggal = LocalDate.now().toString();
        List<Absensi> list = dao.getByTanggal(tanggal);
        table.setModel(new TableModelAbsensi(list));
    }

    // ===================== ISI COMBOBOX SHIFT =====================
    // Dipanggil dari Form setelah comboShift sudah diload shiftnya
    // Tidak perlu method tersendiri karena shift diload dari ControllerShift

    // ===================== CARI NAMA KARYAWAN =====================

    public void cariNama(absensi.Controller.ControllerKaryawan controllerKaryawan) {
        String inputId = txtId.getText().trim();
        if (inputId.isEmpty()) {
            txtNama.setText("");
            return;
        }
        try {
            int id = Integer.parseInt(inputId);
            absensi.Model.Karyawan k = controllerKaryawan.getById(id);
            txtNama.setText(k != null ? k.getNama() : "Tidak ditemukan");
        } catch (NumberFormatException e) {
            txtNama.setText("ID tidak valid");
        }
    }

    // ===================== ISI FIELD DARI TABEL =====================

    public void isiFieldDariTabel(absensi.Controller.ControllerKaryawan controllerKaryawan) {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TableModelAbsensi model = (TableModelAbsensi) table.getModel();
        Absensi a = model.getAbsensiAt(row);
        selectedIdAbsensi = a.getIdAbsensi();

        txtId.setText(String.valueOf(a.getFkKaryawan()));

        absensi.Model.Karyawan k = controllerKaryawan.getById(a.getFkKaryawan());
        if (k != null) txtNama.setText(k.getNama());

        // Set comboShift
        for (int i = 0; i < comboShift.getItemCount(); i++) {
            Shift s = (Shift) comboShift.getItemAt(i);
            if (s.getId_shift() == a.getFkShift()) {
                comboShift.setSelectedIndex(i);
                break;
            }
        }

        // Set comboStatus
        comboStatus.setSelectedItem(a.getStatus());
    }

    // ===================== INSERT (CHECK IN) =====================

    public void insert() {
        String inputId = txtId.getText().trim();

        // Validasi ID
        if (inputId.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "ID karyawan tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idKaryawan;
        try {
            idKaryawan = Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "ID karyawan harus berupa angka!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek karyawan ada
        if (txtNama.getText().equals("Tidak ditemukan") || txtNama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Karyawan dengan ID " + idKaryawan + " tidak ditemukan!\n"
                + "Ketik ID lalu tekan Enter untuk mencari nama.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil shift
        Shift shift = (Shift) comboShift.getSelectedItem();
        if (shift == null) {
            JOptionPane.showMessageDialog(null,
                "Pilih shift terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek sudah check-in hari ini
        String tanggal = LocalDate.now().toString();
        if (dao.isSudahCheckin(idKaryawan, tanggal)) {
            JOptionPane.showMessageDialog(null,
                txtNama.getText() + " sudah melakukan check-in hari ini!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil waktu sekarang
        String waktuStr = LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Hitung status otomatis
        LocalTime waktuCheckin  = LocalTime.parse(waktuStr);
        LocalTime jamMasukShift = LocalTime.parse(shift.getJam_masuk());
        String status = waktuCheckin.compareTo(jamMasukShift) <= 0
                        ? "Hadir Tepat Waktu" : "Terlambat";

        // Simpan
        Absensi absensi = new Absensi(
            dao.getMaxId(), idKaryawan, shift.getId_shift(),
            tanggal, waktuStr, status
        );

        if (dao.tambah(absensi)) {
            JOptionPane.showMessageDialog(null,
                "Check-in berhasil!\n"
                + "Nama   : " + txtNama.getText() + "\n"
                + "Shift  : " + shift.getNama_shift() + "\n"
                + "Jam    : " + waktuStr + "\n"
                + "Status : " + status,
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Check-in gagal!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== UPDATE =====================

    public void update() {
        if (selectedIdAbsensi == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel yang ingin diupdate!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Shift shift = (Shift) comboShift.getSelectedItem();
        if (shift == null) {
            JOptionPane.showMessageDialog(null,
                "Pilih shift terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String statusBaru = (String) comboStatus.getSelectedItem();

        Absensi lama = dao.getById(selectedIdAbsensi);
        if (lama == null) {
            JOptionPane.showMessageDialog(null,
                "Data absensi tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Update absensi ID " + selectedIdAbsensi + "?\n"
            + "Shift baru  : " + shift.getNama_shift() + "\n"
            + "Status baru : " + statusBaru,
            "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Absensi updated = new Absensi(
                selectedIdAbsensi, lama.getFkKaryawan(),
                shift.getId_shift(), lama.getTanggal(),
                lama.getWaktuCheckin(), statusBaru
            );
            if (dao.update(updated)) {
                JOptionPane.showMessageDialog(null,
                    "Data absensi berhasil diupdate!", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal mengupdate data absensi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== DELETE =====================

    public void delete() {
        if (selectedIdAbsensi == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel yang ingin dihapus!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Yakin ingin menghapus data absensi ID " + selectedIdAbsensi + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (dao.hapus(selectedIdAbsensi)) {
                JOptionPane.showMessageDialog(null,
                    "Data absensi berhasil dihapus!", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal menghapus data absensi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== RESET =====================

    public void reset() {
        txtId.setText("");
        txtNama.setText("");
        txtCari.setText("");
        selectedIdAbsensi = -1;
        if (comboShift.getItemCount() > 0)  comboShift.setSelectedIndex(0);
        if (comboStatus.getItemCount() > 0) comboStatus.setSelectedIndex(0);
    }

    // ===================== CARI =====================

    public void cariById() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            isiTable();
            return;
        }
        try {
            int idKaryawan = Integer.parseInt(keyword);
            String tanggal = LocalDate.now().toString();
            List<Absensi> semua = dao.getByTanggal(tanggal);
            List<Absensi> hasil = new ArrayList<>();
            for (Absensi a : semua) {
                if (a.getFkKaryawan() == idKaryawan) hasil.add(a);
            }
            if (hasil.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Data absensi untuk ID " + idKaryawan + " tidak ditemukan!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
                isiTable();
            } else {
                table.setModel(new TableModelAbsensi(hasil));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "ID karyawan harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ===================== METHOD LAMA (tetap ada) =====================

    public boolean isSudahCheckin(int idKaryawan, String tanggal) {
        return dao.isSudahCheckin(idKaryawan, tanggal);
    }

    public List<Absensi> getByTanggal(String tanggal) {
        return dao.getByTanggal(tanggal);
    }

    public Absensi getById(int id) {
        return dao.getById(id);
    }

    public boolean hapus(int id) {
        return dao.hapus(id);
    }

    public int getNextId() {
        return dao.getMaxId();
    }
    
    public List<Absensi> getAllAbsensi() {
        return dao.getAll();
    }
}