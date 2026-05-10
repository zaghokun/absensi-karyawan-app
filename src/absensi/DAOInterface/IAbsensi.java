package absensi.DAOInterface;

import absensi.Model.Absensi;
import java.util.List;

public interface IAbsensi {
    boolean tambah(Absensi a);
    boolean update(Absensi a);
    boolean hapus(int idAbsensi);
    Absensi getById(int idAbsensi);
    List<Absensi> getAll();
    List<Absensi> getByTanggal(String tanggal);
    int getMaxId();
}