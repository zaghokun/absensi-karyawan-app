/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Model;

/**
 *
 * @author ASUS
 */
public class User {

    private int idUser;
    private String username;
    private String password;
    private String role;
    private Integer idKaryawan;

    public User() {
    }

    public User(int idUser, String username, String password, String role, Integer idKaryawan) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
        this.idKaryawan = idKaryawan;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Integer getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIdKaryawan(Integer idKaryawan) {
        this.idKaryawan = idKaryawan;
    }
}
