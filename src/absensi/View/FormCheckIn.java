/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package absensi.View;

import absensi.Controller.ControllerAbsensi;
import absensi.Controller.ControllerKaryawan;
import absensi.Controller.ControllerShift;
import absensi.Model.Absensi;
import absensi.Model.Karyawan;
import absensi.Model.Shift;
import absensi.Model.TableModelAbsensi;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class FormCheckIn extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormCheckIn.class.getName());
    private final ControllerKaryawan controllerKaryawan = new ControllerKaryawan();
    private final ControllerShift controllerShift       = new ControllerShift();
    private final ControllerAbsensi controllerAbsensi   = new ControllerAbsensi();
    private TableModelAbsensi tableModel;
    private javax.swing.Timer timerJam;
    /**
     * Creates new form FormCheckIn
     */
    
    private int selectedIdAbsensi = -1;
    
    public FormCheckIn() {
        initComponents();
        setupForm();
    }
    
    private void setupForm() {
        // Tanggal otomatis
        tanggalLabel.setText("Tanggal: " + LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        // Jam otomatis update tiap detik
        timerJam = new javax.swing.Timer(1000, e -> {
            jamLabel.setText("Jam: " + LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        timerJam.start();
        jamLabel.setText("Jam: " + LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Field nama tidak bisa diedit manual
        jTextField2.setEditable(false);
        jTextField2.setBackground(java.awt.Color.LIGHT_GRAY);

        // Field cari placeholder kosong
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");

        // Load shift ke ComboBox dari database
        loadComboShift();

        // Load tabel absensi hari ini
        refreshTabel();

        // Ketik ID lalu Enter → nama muncul otomatis
        jTextField1.addActionListener(e -> cariNamaKaryawan());
        
        statusCombo.addItem("Hadir Tepat Waktu");
        statusCombo.addItem("Terlambat");
        statusCombo.addItem("Alpha");

        // Klik baris tabel → isi semua field otomatis
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row >= 0) {
                    Absensi a = tableModel.getAbsensiAt(row);
                    selectedIdAbsensi = a.getIdAbsensi();

                    // Isi field ID karyawan
                    jTextField1.setText(String.valueOf(a.getFkKaryawan()));

                    // Cari dan tampilkan nama otomatis
                    Karyawan k = controllerKaryawan.getById(a.getFkKaryawan());
                    if (k != null) {
                        jTextField2.setText(k.getNama());
                    }

                    // Set ComboBox ke shift yang sesuai
                    for (int i = 0; i < shiftCombo.getItemCount(); i++) {
                        Shift s = (Shift) shiftCombo.getItemAt(i);
                        if (s.getId_shift() == a.getFkShift()) {
                            shiftCombo.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    statusCombo.setSelectedItem(a.getStatus());
                }
            }
        });
    }
    
    private void loadComboShift() {
        shiftCombo.removeAllItems();
        List<Shift> listShift = controllerShift.getAll();
        for (Shift s : listShift) {
            shiftCombo.addItem(s);
        }
    }

    private void cariNamaKaryawan() {
        String inputId = jTextField1.getText().trim();
        if (inputId.isEmpty()) {
            jTextField2.setText("");
            return;
        }
        try {
            int id = Integer.parseInt(inputId);
            Karyawan k = controllerKaryawan.getById(id);
            if (k != null) {
                jTextField2.setText(k.getNama());
            } else {
                jTextField2.setText("Tidak ditemukan");
            }
        } catch (NumberFormatException e) {
            jTextField2.setText("ID tidak valid");
        }
    }

    private void refreshTabel() {
        String tanggalHariIni = LocalDate.now().toString();
        List<Absensi> listAbsensi = controllerAbsensi.getByTanggal(tanggalHariIni);
        tableModel = new TableModelAbsensi(listAbsensi);
        jTable1.setModel(tableModel);
        selectedIdAbsensi = -1;
    }

    private void bersihkanField() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        selectedIdAbsensi = -1;
        if (shiftCombo.getItemCount() > 0) shiftCombo.setSelectedIndex(0);
        if (statusCombo.getItemCount() > 0) statusCombo.setSelectedIndex(0); // TAMBAHAN BARU
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        namaLabel = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        tanggalLabel = new javax.swing.JLabel();
        jamLabel = new javax.swing.JLabel();
        checkInBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        cariBtn = new javax.swing.JButton();
        shiftLabel = new javax.swing.JLabel();
        shiftCombo = new javax.swing.JComboBox<>();
        backBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        statusCombo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Form Check In");

        idLabel.setText("Id:");

        jTextField1.setText("Masukkan id Karyawan");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        namaLabel.setText("Nama:");

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        tanggalLabel.setText("Tanggal:");

        jamLabel.setText("Jam:");

        checkInBtn.setText("Check In");
        checkInBtn.addActionListener(this::checkInBtnActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        updateBtn.setText("Update");
        updateBtn.addActionListener(this::updateBtnActionPerformed);

        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(this::deleteBtnActionPerformed);

        refreshBtn.setText("Refresh");
        refreshBtn.addActionListener(this::refreshBtnActionPerformed);

        jLabel6.setText("Cari:");

        jTextField3.setText("Masukkan Id Karyawan");
        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        cariBtn.setText("Cari");
        cariBtn.addActionListener(this::cariBtnActionPerformed);

        shiftLabel.setText("Shift:");

        backBtn.setText("Back");
        backBtn.addActionListener(this::backBtnActionPerformed);

        jLabel2.setText("Status:");

        statusCombo.addActionListener(this::statusComboActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tanggalLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(namaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(shiftLabel)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(10, 10, 10)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(statusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(shiftCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addComponent(backBtn)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkInBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(cariBtn)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaLabel)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tanggalLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jamLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shiftLabel)
                            .addComponent(shiftCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(statusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkInBtn)
                            .addComponent(updateBtn)
                            .addComponent(deleteBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(backBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cariBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        cariNamaKaryawan();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void checkInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInBtnActionPerformed
        String inputId = jTextField1.getText().trim();

        // Validasi ID tidak kosong
        if (inputId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "ID karyawan tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi ID harus angka
        int idKaryawan;
        try {
            idKaryawan = Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "ID karyawan harus berupa angka!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek karyawan ada di database
        Karyawan karyawan = controllerKaryawan.getById(idKaryawan);
        if (karyawan == null) {
            JOptionPane.showMessageDialog(this,
                "Karyawan dengan ID " + idKaryawan + " tidak ditemukan!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil shift yang dipilih dari ComboBox
        Shift shift = (Shift) shiftCombo.getSelectedItem();
        if (shift == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih shift terlebih dahulu!\nPastikan data shift sudah diisi di menu Shift.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek sudah check-in hari ini
        String tanggalHariIni = LocalDate.now().toString();
        if (controllerAbsensi.isSudahCheckin(idKaryawan, tanggalHariIni)) {
            JOptionPane.showMessageDialog(this,
                karyawan.getNama() + " sudah melakukan check-in hari ini!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil waktu sekarang
        LocalTime waktuCheckin = LocalTime.now();
        String waktuStr = waktuCheckin.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Hitung status otomatis berdasarkan shift yang dipilih
        LocalTime jamMasukShift = LocalTime.parse(shift.getJam_masuk());
        String status = waktuCheckin.compareTo(jamMasukShift) <= 0
                        ? "Hadir Tepat Waktu"
                        : "Terlambat";

        // Simpan ke database
        int newId = controllerAbsensi.getNextId();
        Absensi absensi = new Absensi(
            newId, idKaryawan, shift.getId_shift(),
            tanggalHariIni, waktuStr, status
        );

        boolean berhasil = controllerAbsensi.tambah(absensi);

        if (berhasil) {
            JOptionPane.showMessageDialog(this,
                "Check-in berhasil!\n"
                + "Nama   : " + karyawan.getNama() + "\n"
                + "Shift  : " + shift.getNama_shift() + "\n"
                + "Jam    : " + waktuStr + "\n"
                + "Status : " + status,
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            bersihkanField();
            refreshTabel();
        } else {
            JOptionPane.showMessageDialog(this,
                "Check-in gagal! Coba lagi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_checkInBtnActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        if (selectedIdAbsensi == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih dulu data absensi dari tabel yang ingin dihapus!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus data absensi ID " + selectedIdAbsensi + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean berhasil = controllerAbsensi.hapus(selectedIdAbsensi);
            if (berhasil) {
                JOptionPane.showMessageDialog(this,
                    "Data absensi berhasil dihapus!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                bersihkanField();
                refreshTabel();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Gagal menghapus data absensi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        cariBtnActionPerformed(evt);
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        if (selectedIdAbsensi == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih dulu data absensi dari tabel yang ingin diupdate!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Shift shift = (Shift) shiftCombo.getSelectedItem();
        if (shift == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih shift terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil status dari ComboBox — tidak dihitung otomatis lagi
        String statusBaru = (String) statusCombo.getSelectedItem();

        Absensi absensiLama = controllerAbsensi.getById(selectedIdAbsensi);
        if (absensiLama == null) {
            JOptionPane.showMessageDialog(this,
                "Data absensi tidak ditemukan!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Update absensi ID " + selectedIdAbsensi + "?\n"
            + "Shift baru  : " + shift.getNama_shift() + "\n"
            + "Status baru : " + statusBaru,
            "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Absensi absensiUpdate = new Absensi(
                selectedIdAbsensi,
                absensiLama.getFkKaryawan(),
                shift.getId_shift(),
                absensiLama.getTanggal(),
                absensiLama.getWaktuCheckin(),
                statusBaru                      // dari statusCombo
            );

            boolean berhasil = controllerAbsensi.update(absensiUpdate);

            if (berhasil) {
                JOptionPane.showMessageDialog(this,
                    "Data absensi berhasil diupdate!",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                bersihkanField();
                refreshTabel();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Gagal mengupdate data absensi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_updateBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        bersihkanField();
        loadComboShift();
        refreshTabel();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void cariBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariBtnActionPerformed
        String keyword = jTextField3.getText().trim();

        if (keyword.isEmpty()) {
            refreshTabel();
            return;
        }

        // Cari berdasarkan ID karyawan
        try {
            int idKaryawan = Integer.parseInt(keyword);
            String tanggalHariIni = LocalDate.now().toString();
            List<Absensi> semua = controllerAbsensi.getByTanggal(tanggalHariIni);
            List<Absensi> hasil = new java.util.ArrayList<>();

            for (Absensi a : semua) {
                if (a.getFkKaryawan() == idKaryawan) {
                    hasil.add(a);
                }
            }

            if (hasil.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Data absensi untuk karyawan ID " + idKaryawan + " tidak ditemukan!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
                refreshTabel();
            } else {
                tableModel = new TableModelAbsensi(hasil);
                jTable1.setModel(tableModel);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "ID karyawan harus berupa angka!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_cariBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        new FormDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void statusComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusComboActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormCheckIn().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton cariBtn;
    private javax.swing.JButton checkInBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel jamLabel;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JComboBox<Shift> shiftCombo;
    private javax.swing.JLabel shiftLabel;
    private javax.swing.JComboBox<String> statusCombo;
    private javax.swing.JLabel tanggalLabel;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
