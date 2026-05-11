/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOAbsensi;
import absensi.Model.Absensi;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ControllerAbsensi {

    private DAOAbsensi dao = new DAOAbsensi();

    public boolean tambah(Absensi a) {
        return dao.tambah(a);
    }

    public boolean update(Absensi a) {
        return dao.update(a);
    }

    public boolean hapus(int id) {
        return dao.hapus(id);
    }

    public List<Absensi> getAll() {
        return dao.getAll();
    }

    public Absensi getById(int id) {
        return dao.getById(id);
    }

    public List<Absensi> getByTanggal(String tanggal) {
        return dao.getByTanggal(tanggal);
    }

    public int getNextId() {
        return dao.getMaxId();
    }
}