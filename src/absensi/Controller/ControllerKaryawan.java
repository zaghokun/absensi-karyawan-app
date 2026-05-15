/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOKaryawan;
import absensi.Model.Karyawan;
import absensi.Model.TableModelKaryawan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControllerKaryawan {

    private DAOKaryawan dao = new DAOKaryawan();

    // Referensi komponen form
    private JTextField idTF;
    private JTextField namaTF;
    private JTextField jabatanTF;
    private JTextField cariNamaTF;
    private JTable     table;

    // Simpan row yang dipilih
    private int selectedIdKaryawan = -1;

    // Constructor default — dipakai controller lain yang butuh getById dll
    public ControllerKaryawan() {}

    // Constructor dengan komponen — dipakai FormKaryawan
    public ControllerKaryawan(JTextField idTF, JTextField namaTF,
                               JTextField jabatanTF, JTextField cariNamaTF,
                               JTable table) {
        this.idTF       = idTF;
        this.namaTF     = namaTF;
        this.jabatanTF  = jabatanTF;
        this.cariNamaTF = cariNamaTF;
        this.table      = table;
    }

    // ===================== INISIALISASI =====================

    public void init() {
        reset();
        isiTable();

        // Klik baris tabel → isi field otomatis
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                isiFieldDariTabel();
            }
        });
    }

    // ===================== ISI TABEL =====================

    public void isiTable() {
        List<Karyawan> list = dao.getAll();
        table.setModel(new TableModelKaryawan(list));
    }

    // ===================== ISI FIELD DARI TABEL =====================

    public void isiFieldDariTabel() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TableModelKaryawan model = (TableModelKaryawan) table.getModel();
        Karyawan k = model.getKaryawanAt(row);
        selectedIdKaryawan = k.getIdKaryawan();

        idTF.setText(String.valueOf(k.getIdKaryawan()));
        namaTF.setText(k.getNama());
        jabatanTF.setText(k.getJabatan());
    }

    // ===================== INSERT =====================

    public void insert() {
        String inputId      = idTF.getText().trim();
        String inputNama    = namaTF.getText().trim();
        String inputJabatan = jabatanTF.getText().trim();

        // Validasi kosong
        if (inputId.isEmpty() || inputNama.isEmpty() || inputJabatan.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "ID, Nama, dan Jabatan tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi ID harus angka
        int id;
        try {
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "ID harus berupa angka!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek ID sudah dipakai
        if (dao.getById(id) != null) {
            JOptionPane.showMessageDialog(null,
                "ID " + id + " sudah digunakan! Gunakan ID lain.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simpan
        if (dao.tambah(new Karyawan(id, inputNama, inputJabatan))) {
            JOptionPane.showMessageDialog(null,
                "Karyawan berhasil ditambahkan!",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Gagal menambahkan karyawan!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== UPDATE =====================

    public void update() {
        if (selectedIdKaryawan == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel sebelum update!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String inputNama    = namaTF.getText().trim();
        String inputJabatan = jabatanTF.getText().trim();

        if (inputNama.isEmpty() || inputJabatan.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Nama dan Jabatan tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Yakin ingin mengupdate karyawan ID " + selectedIdKaryawan + "?",
            "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (dao.update(new Karyawan(selectedIdKaryawan, inputNama, inputJabatan))) {
                JOptionPane.showMessageDialog(null,
                    "Data karyawan berhasil diupdate!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal mengupdate data karyawan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== DELETE =====================

    public void delete() {
        if (selectedIdKaryawan == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel sebelum hapus!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Yakin ingin menghapus karyawan ID " + selectedIdKaryawan + "?\n"
            + "Data absensi karyawan ini juga akan terpengaruh!",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (dao.hapus(selectedIdKaryawan)) {
                JOptionPane.showMessageDialog(null,
                    "Karyawan berhasil dihapus!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal menghapus karyawan!\n"
                    + "Kemungkinan karyawan ini masih memiliki data absensi.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== RESET =====================

    public void reset() {
        idTF.setText("");
        namaTF.setText("");
        jabatanTF.setText("");
        cariNamaTF.setText("");
        selectedIdKaryawan = -1;
    }

    // ===================== CARI =====================

    public void cariNama() {
        String keyword = cariNamaTF.getText().trim();
        if (keyword.isEmpty()) {
            isiTable();
            return;
        }

        List<Karyawan> semua = dao.getAll();
        List<Karyawan> hasil = new ArrayList<>();
        for (Karyawan k : semua) {
            if (k.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                hasil.add(k);
            }
        }

        if (hasil.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Karyawan dengan nama \"" + keyword + "\" tidak ditemukan!",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            isiTable();
        } else {
            table.setModel(new TableModelKaryawan(hasil));
        }
    }

    // ===================== METHOD LAMA (tetap ada untuk controller lain) =====================

    public Karyawan getById(int id)        { return dao.getById(id); }
    public List<Karyawan> getAll()         { return dao.getAll(); }
    public boolean tambah(Karyawan k)      { return dao.tambah(k); }
    public boolean update(Karyawan k)      { return dao.update(k); }
    public boolean hapus(int id)           { return dao.hapus(id); }
    public int getNextId()                 { return dao.getMaxId(); }
}
