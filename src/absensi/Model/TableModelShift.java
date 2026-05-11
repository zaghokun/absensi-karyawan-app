package absensi.Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class TableModelShift extends AbstractTableModel {
    private final String[] KOLOM = {"ID Shift", "Nama Shift", "Jam Masuk"};
    private List<Shift> data;

    public TableModelShift(List<Shift> data) {
        this.data = data;
    }

    @Override public int getRowCount()    { return data.size(); }
    @Override public int getColumnCount() { return KOLOM.length; }
    @Override public String getColumnName(int col) { return KOLOM[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Shift s = data.get(row);
        return switch (col) {
            case 0 -> s.getId_shift();
            case 1 -> s.getNama_shift();
            case 2 -> s.getJam_masuk();
            default -> null;
        }; 
    }

    public void setData(List<Shift> data) {
        this.data = data;
        fireTableDataChanged();
    }
    public Shift getShiftAt(int row) {
        return data.get(row);
    }
}