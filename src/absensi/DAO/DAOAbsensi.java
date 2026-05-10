package absensi.DAO;

import absensi.DAOInterface.IAbsensi;
import absensi.koneksi.Koneksi;
import absensi.Model.Absensi;
import java.sql.*;
import java.util.*;

public class DAOAbsensi implements IAbsensi {

    @Override
    public boolean tambah(Absensi a) {
        String sql = "INSERT INTO tabel_absensi VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getIdAbsensi());
            ps.setInt(2, a.getFkKaryawan());
            ps.setInt(3, a.getFkShift());
            ps.setString(4, a.getTanggal());
            ps.setString(5, a.getWaktuCheckin());
            ps.setString(6, a.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error tambah absensi: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Absensi a) {
        String sql = "UPDATE tabel_absensi SET fk_karyawan = ?, fk_shift = ?, tanggal = ?, waktu_checkin = ?, status = ? WHERE id_absensi = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getFkKaryawan());
            ps.setInt(2, a.getFkShift());
            ps.setString(3, a.getTanggal());
            ps.setString(4, a.getWaktuCheckin());
            ps.setString(5, a.getStatus());
            ps.setInt(6, a.getIdAbsensi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error update absensi: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hapus(int idAbsensi) {
        String sql = "DELETE FROM tabel_absensi WHERE id_absensi = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAbsensi);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error hapus absensi: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Absensi getById(int idAbsensi) {
        String sql = "SELECT * FROM tabel_absensi WHERE id_absensi = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAbsensi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Absensi a = new Absensi();
                a.setIdAbsensi(rs.getInt("id_absensi"));
                a.setFkKaryawan(rs.getInt("fk_karyawan"));
                a.setFkShift(rs.getInt("fk_shift"));
                a.setTanggal(rs.getString("tanggal"));
                a.setWaktuCheckin(rs.getString("waktu_checkin"));
                a.setStatus(rs.getString("status"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println("Error getById absensi: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Absensi> getAll() {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_absensi ORDER BY id_absensi";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Absensi a = new Absensi();
                a.setIdAbsensi(rs.getInt("id_absensi"));
                a.setFkKaryawan(rs.getInt("fk_karyawan"));
                a.setFkShift(rs.getInt("fk_shift"));
                a.setTanggal(rs.getString("tanggal"));
                a.setWaktuCheckin(rs.getString("waktu_checkin"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error getAll absensi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Absensi> getByTanggal(String tanggal) {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_absensi WHERE tanggal = ? ORDER BY id_absensi";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tanggal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Absensi a = new Absensi();
                a.setIdAbsensi(rs.getInt("id_absensi"));
                a.setFkKaryawan(rs.getInt("fk_karyawan"));
                a.setFkShift(rs.getInt("fk_shift"));
                a.setTanggal(rs.getString("tanggal"));
                a.setWaktuCheckin(rs.getString("waktu_checkin"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error getByTanggal absensi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT MAX(id_absensi) FROM tabel_absensi";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getObject(1) == null ? 1 : rs.getInt(1) + 1;
        } catch (SQLException e) {
            System.out.println("Error getMaxId absensi: " + e.getMessage());
        }
        return 1;
    }
}