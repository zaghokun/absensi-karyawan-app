/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOKaryawan;
import absensi.Model.Karyawan;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ControllerKaryawan {

    private DAOKaryawan dao = new DAOKaryawan();

    public boolean tambah(Karyawan k) {
        return dao.tambah(k);
    }

    public boolean update(Karyawan k) {
        return dao.update(k);
    }

    public boolean hapus(int id) {
        return dao.hapus(id);
    }

    public List<Karyawan> getAll() {
        return dao.getAll();
    }

    public Karyawan getById(int id) {
        return dao.getById(id);
    }

    public int getNextId() {
        return dao.getMaxId();
    }
}
