/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ASUS
 */
public class TableModelAbsensi extends AbstractTableModel {
    private final String[] KOLOM = {"ID", "Karyawan", "Shift", "Tanggal", "Waktu", "Status"};
    private List<Absensi> data;

    public TableModelAbsensi(List<Absensi> data) {
        this.data = data;
    }

    @Override public int getRowCount()    { return data.size(); }
    @Override public int getColumnCount() { return KOLOM.length; }
    @Override public String getColumnName(int col) { return KOLOM[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Absensi a = data.get(row);
        switch (col) {
            case 0: return a.getIdAbsensi();
            case 1: return a.getFkKaryawan();
            case 2: return a.getFkShift();
            case 3: return a.getTanggal();
            case 4: return a.getWaktuCheckin();
            case 5: return a.getStatus();
            default: return null;
        }
    }

    public void setData(List<Absensi> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
