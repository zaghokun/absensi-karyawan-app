/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Model;

/**
 *
 * @author LENOVO
 */
public class Karyawan {

    private Integer idKaryawan;
    private String nama;
    private String jabatan;

    // Constructor kosong — wajib ada
    public Karyawan() {}

    // Constructor 3 parameter — ini yang kurang!
    public Karyawan(int idKaryawan, String nama, String jabatan) {
        this.idKaryawan = idKaryawan;
        this.nama       = nama;
        this.jabatan    = jabatan;
    }

    public Integer getIdKaryawan() { return idKaryawan; }
    public void setIdKaryawan(Integer idKaryawan) { this.idKaryawan = idKaryawan; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }
}
