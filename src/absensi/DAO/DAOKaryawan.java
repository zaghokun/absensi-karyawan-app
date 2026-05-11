package absensi.DAO;

import absensi.DAOInterface.IKaryawan;
import absensi.koneksi.Koneksi;
import absensi.Model.Karyawan;
import java.sql.*;
import java.util.*;

public class DAOKaryawan implements IKaryawan {

    @Override
    public boolean tambah(Karyawan k) {
        String sql = "INSERT INTO tabel_karyawan VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, k.getIdKaryawan());
            ps.setString(2, k.getNama());
            ps.setString(3, k.getJabatan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error tambah karyawan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Karyawan k) {
        String sql = "UPDATE tabel_karyawan SET nama = ?, jabatan = ? WHERE id_karyawan = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getNama());
            ps.setString(2, k.getJabatan());
            ps.setInt(3, k.getIdKaryawan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error update karyawan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hapus(int idKaryawan) {
        String sql = "DELETE FROM tabel_karyawan WHERE id_karyawan = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKaryawan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error hapus karyawan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Karyawan getById(int idKaryawan) {
        String sql = "SELECT * FROM tabel_karyawan WHERE id_karyawan = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKaryawan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Karyawan k = new Karyawan();
                k.setIdKaryawan(rs.getInt("id_karyawan"));
                k.setNama(rs.getString("nama"));
                k.setJabatan(rs.getString("jabatan"));
                return k;
            }
        } catch (SQLException e) {
            System.out.println("Error getById karyawan: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Karyawan> getAll() {
        List<Karyawan> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_karyawan ORDER BY id_karyawan";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Karyawan k = new Karyawan();
                k.setIdKaryawan(rs.getInt("id_karyawan"));
                k.setNama(rs.getString("nama"));
                k.setJabatan(rs.getString("jabatan"));
                list.add(k);
            }
        } catch (SQLException e) {
            System.out.println("Error getAll karyawan: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT MAX(id_karyawan) FROM tabel_karyawan";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getObject(1) == null ? 1 : rs.getInt(1) + 1;
        } catch (SQLException e) {
            System.out.println("Error getMaxId: " + e.getMessage());
        }
        return 1;
    }
}