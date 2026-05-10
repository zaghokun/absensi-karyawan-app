/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package absensi.DAOInterface;

import absensi.Model.Shift;
import java.util.List;
/**
 *
 * @author ASUS
 */
public interface IShift {
    boolean tambah(Shift s);
    boolean update(Shift s);
    boolean hapus(int idShift);
    Shift getById(int idShift);
    List<Shift> getAll();
    int getMaxId();
}
