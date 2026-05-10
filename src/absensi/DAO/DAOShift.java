package absensi.DAO;

import absensi.DAOInterface.IShift;
import absensi.koneksi.Koneksi;
import absensi.Model.Shift;
import java.sql.*;
import java.util.*;

public class DAOShift implements IShift {

    @Override
    public boolean tambah(Shift s) {
        String sql = "INSERT INTO tabel_shift VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getId_shift());
            ps.setString(2, s.getNama_shift());
            ps.setString(3, s.getJam_masuk());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error tambah shift: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Shift s) {
        String sql = "UPDATE tabel_shift SET nama_shift = ?, jam_masuk = ? WHERE id_shift = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNama_shift());
            ps.setString(2, s.getJam_masuk());
            ps.setInt(3, s.getId_shift());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error update shift: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hapus(int idShift) {
        String sql = "DELETE FROM tabel_shift WHERE id_shift = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idShift);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error hapus shift: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Shift getById(int idShift) {
        String sql = "SELECT * FROM tabel_shift WHERE id_shift = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idShift);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Shift s = new Shift();
                s.setId_shift(rs.getInt("id_shift"));
                s.setNama_shift(rs.getString("nama_shift"));
                s.setJam_masuk(rs.getString("jam_masuk"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error getById shift: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Shift> getAll() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_shift ORDER BY id_shift";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Shift s = new Shift();
                s.setId_shift(rs.getInt("id_shift"));
                s.setNama_shift(rs.getString("nama_shift"));
                s.setJam_masuk(rs.getString("jam_masuk"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getAll shift: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT MAX(id_shift) FROM tabel_shift";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getObject(1) == null ? 1 : rs.getInt(1) + 1;
        } catch (SQLException e) {
            System.out.println("Error getMaxId shift: " + e.getMessage());
        }
        return 1;
    }
}