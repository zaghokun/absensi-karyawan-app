package absensi.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    private static final String URL  = "jdbc:mysql://localhost:3306/db_absensi_karyawan";
    private static final String USER = "root";
    private static final String PASS = "";  // sesuaikan kalau MySQL kamu pakai password

    public static Connection getKoneksi() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}