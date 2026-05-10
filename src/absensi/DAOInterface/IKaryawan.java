/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package absensi.DAOInterface;

import absensi.Model.Karyawan;
import java.util.List;
/**
 *
 * @author ASUS
 */
public interface IKaryawan {
    boolean tambah(Karyawan k);
    boolean update(Karyawan k);
    boolean hapus(int idKaryawan);
    Karyawan getById(int idKaryawan);
    List<Karyawan> getAll();
    int getMaxId();
}
