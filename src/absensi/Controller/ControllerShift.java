/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOShift;
import absensi.Model.Shift;
import absensi.Model.TableModelShift;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControllerShift {

    private DAOShift dao = new DAOShift();

    // Referensi komponen form
    private JTextField idTF;
    private JTextField namaTF;
    private JTextField jamTF;
    private JTextField cariNamaTF;
    private JTable     table;

    // Simpan id shift yang sedang dipilih
    private int selectedIdShift = -1;

    // Constructor default — dipakai controller lain yang butuh getAll dll
    public ControllerShift() {}

    // Constructor dengan komponen — dipakai FormShift
    public ControllerShift(JTextField idTF, JTextField namaTF,
                            JTextField jamTF, JTextField cariNamaTF,
                            JTable table) {
        this.idTF       = idTF;
        this.namaTF     = namaTF;
        this.jamTF      = jamTF;
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
        List<Shift> list = dao.getAll();
        table.setModel(new TableModelShift(list));
    }

    // ===================== ISI FIELD DARI TABEL =====================

    public void isiFieldDariTabel() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TableModelShift model = (TableModelShift) table.getModel();
        Shift s = model.getShiftAt(row);
        selectedIdShift = s.getId_shift();

        idTF.setText(String.valueOf(s.getId_shift()));
        namaTF.setText(s.getNama_shift());
        jamTF.setText(s.getJam_masuk());
    }

    // ===================== INSERT =====================

    public void insert() {
        String inputId   = idTF.getText().trim();
        String inputNama = namaTF.getText().trim();
        String inputJam  = jamTF.getText().trim();

        // Validasi kosong
        if (inputId.isEmpty() || inputNama.isEmpty() || inputJam.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "ID, Nama Shift, dan Jam Masuk tidak boleh kosong!",
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

        // Validasi format jam HH:mm:ss
        if (!inputJam.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            JOptionPane.showMessageDialog(null,
                "Format jam masuk salah!\nGunakan format HH:mm:ss\nContoh: 08:00:00",
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
        if (dao.tambah(new Shift(id, inputNama, inputJam))) {
            JOptionPane.showMessageDialog(null,
                "Shift berhasil ditambahkan!",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Gagal menambahkan shift!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== UPDATE =====================

    public void update() {
        if (selectedIdShift == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel sebelum update!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String inputNama = namaTF.getText().trim();
        String inputJam  = jamTF.getText().trim();

        if (inputNama.isEmpty() || inputJam.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Nama Shift dan Jam Masuk tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi format jam
        if (!inputJam.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            JOptionPane.showMessageDialog(null,
                "Format jam masuk salah!\nGunakan format HH:mm:ss\nContoh: 14:00:00",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Yakin ingin mengupdate shift ID " + selectedIdShift + "?",
            "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (dao.update(new Shift(selectedIdShift, inputNama, inputJam))) {
                JOptionPane.showMessageDialog(null,
                    "Data shift berhasil diupdate!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal mengupdate data shift!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== DELETE =====================

    public void delete() {
        if (selectedIdShift == -1) {
            JOptionPane.showMessageDialog(null,
                "Pilih dulu data dari tabel sebelum hapus!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
            "Yakin ingin menghapus shift ID " + selectedIdShift + "?\n"
            + "Data absensi yang menggunakan shift ini juga akan terpengaruh!",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (dao.hapus(selectedIdShift)) {
                JOptionPane.showMessageDialog(null,
                    "Shift berhasil dihapus!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal menghapus shift!\n"
                    + "Kemungkinan shift ini masih digunakan di data absensi.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== RESET =====================

    public void reset() {
        idTF.setText("");
        namaTF.setText("");
        jamTF.setText("");
        cariNamaTF.setText("");
        selectedIdShift = -1;
    }

    // ===================== CARI =====================

    public void cariNama() {
        String keyword = cariNamaTF.getText().trim();
        if (keyword.isEmpty()) {
            isiTable();
            return;
        }

        List<Shift> semua = dao.getAll();
        List<Shift> hasil = new ArrayList<>();
        for (Shift s : semua) {
            if (s.getNama_shift().toLowerCase().contains(keyword.toLowerCase())) {
                hasil.add(s);
            }
        }

        if (hasil.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Shift dengan nama \"" + keyword + "\" tidak ditemukan!",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            isiTable();
        } else {
            table.setModel(new TableModelShift(hasil));
        }
    }

    // ===================== METHOD LAMA (tetap ada untuk controller lain) =====================

    public Shift getById(int id)      { return dao.getById(id); }
    public List<Shift> getAll()       { return dao.getAll(); }
    public boolean tambah(Shift s)    { return dao.tambah(s); }
    public boolean update(Shift s)    { return dao.update(s); }
    public boolean hapus(int id)      { return dao.hapus(id); }
    public int getNextId()            { return dao.getMaxId(); }
}