/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.DAO;

import absensi.DAOInterface.IUser;
import absensi.koneksi.Koneksi;
import absensi.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author ASUS
 */
public class DAOUser implements IUser {

    @Override
    public User login(String username, String password) {

        User user = null;

        String sql = "SELECT * FROM tabel_users WHERE username=? AND password=?";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));

                int idKaryawan = rs.getInt("id_karyawan");

                if (!rs.wasNull()) {
                    user.setIdKaryawan(idKaryawan);
                }

            }

        } catch (Exception e) {
            System.out.println("Login gagal: " + e.getMessage());
        }

        return user;
    }
}
