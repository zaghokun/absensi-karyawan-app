/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Model;

/**
 *
 * @author LENOVO
 */
public class Absensi {

    public Integer getIdAbsensi() {
        return idAbsensi;
    }

    public void setIdAbsensi(Integer idAbsensi) {
        this.idAbsensi = idAbsensi;
    }

    public Integer getFkKaryawan() {
        return fkKaryawan;
    }

    public void setFkKaryawan(Integer fkKaryawan) {
        this.fkKaryawan = fkKaryawan;
    }

    public Integer getFkShift() {
        return fkShift;
    }

    public void setFkShift(Integer fkShift) {
        this.fkShift = fkShift;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktuCheckin() {
        return waktuCheckin;
    }

    public void setWaktuCheckin(String waktuCheckin) {
        this.waktuCheckin = waktuCheckin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private Integer idAbsensi;
    private Integer fkKaryawan;
    private Integer fkShift;
    private String tanggal;
    private String waktuCheckin;
    private String status;
}
