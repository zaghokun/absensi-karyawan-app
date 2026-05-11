/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOShift;
import absensi.Model.Shift;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ControllerShift {

    private DAOShift dao = new DAOShift();

    public boolean tambah(Shift s) {
        return dao.tambah(s);
    }

    public boolean update(Shift s) {
        return dao.update(s);
    }

    public boolean hapus(int id) {
        return dao.hapus(id);
    }

    public List<Shift> getAll() {
        return dao.getAll();
    }

    public Shift getById(int id) {
        return dao.getById(id);
    }

    public int getNextId() {
        return dao.getMaxId();
    }
}