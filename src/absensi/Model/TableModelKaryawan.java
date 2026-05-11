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
        return switch (col) {
            case 0 -> k.getIdKaryawan(); 
            case 1 -> k.getNama();
            case 2 -> k.getJabatan();
            default -> null;
        };
    }

    public void setData(List<Karyawan> data) {
        this.data = data;
        fireTableDataChanged();
    }
    
    public Karyawan getKaryawanAt(int row) {
        return data.get(row);
    }
}
