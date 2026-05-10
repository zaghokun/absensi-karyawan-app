/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package absensi.DAOInterface;

import absensi.Model.Absensi;
import java.util.List;
/**
 *
 * @author ASUS
 */
public interface IAbsensi {
    boolean tambah(Absensi a);
    List<Absensi> getAll();
    List<Absensi> getByTanggal(String tanggal);
}
