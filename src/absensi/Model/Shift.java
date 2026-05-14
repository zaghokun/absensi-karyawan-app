/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Model;

/**
 *
 * @author LENOVO
 */
public class Shift {
    private Integer id_shift;
    private String nama_shift;
    private String jam_masuk;

    // Constructor kosong
    public Shift() {}

    // Constructor 3 parameter — ini yang kurang!
    public Shift(int id_shift, String nama_shift, String jam_masuk) {
        this.id_shift   = id_shift;
        this.nama_shift = nama_shift;
        this.jam_masuk  = jam_masuk;
    }

    public Integer getId_shift()  { return id_shift; }
    public void setId_shift(Integer id_shift) { this.id_shift = id_shift; }

    public String getNama_shift() { return nama_shift; }
    public void setNama_shift(String nama_shift) { this.nama_shift = nama_shift; }

    public String getJam_masuk()  { return jam_masuk; }
    public void setJam_masuk(String jam_masuk) { this.jam_masuk = jam_masuk; }
    
    @Override
    public String toString() {
        return nama_shift + " (" + jam_masuk + ")";
    }
}
