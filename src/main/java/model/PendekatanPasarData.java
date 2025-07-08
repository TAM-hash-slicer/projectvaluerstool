package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model specifically for the Market Approach (Pendekatan Pasar).
 * This class holds all inputs and calculated values for this valuation method.
 */
public class PendekatanPasarData {

    // Sample variables for the subject property
    private String deskripsiProperti;
    private double luasTanah;
    private double luasBangunan;
    private String alamatProperti;

    // A list to hold all comparable properties used in the analysis
    private List<PembandingPasar> pembandingPasarList;

    // Sample variables for adjustments and results
    private String ringkasanPenyesuaian;
    private double nilaiPasarIndikasi;

    // Constructor to initialize lists
    public PendekatanPasarData() {
        this.pembandingPasarList = new ArrayList<>();
    }

    // --- Getters and Setters ---
    // (These are crucial for Jackson to be able to save/load the data)

    public String getDeskripsiProperti() {
        return deskripsiProperti;
    }

    public void setDeskripsiProperti(String deskripsiProperti) {
        this.deskripsiProperti = deskripsiProperti;
    }

    public double getLuasTanah() {
        return luasTanah;
    }

    public void setLuasTanah(double luasTanah) {
        this.luasTanah = luasTanah;
    }

    public double getLuasBangunan() {
        return luasBangunan;
    }

    public void setLuasBangunan(double luasBangunan) {
        this.luasBangunan = luasBangunan;
    }

    public String getAlamatProperti() {
        return alamatProperti;
    }

    public void setAlamatProperti(String alamatProperti) {
        this.alamatProperti = alamatProperti;
    }

    // CHANGED: Renamed methods to match the field name for consistency.
    public List<PembandingPasar> getPembandingPasarList() {
        return pembandingPasarList;
    }

    public void setPembandingPasarList(List<PembandingPasar> pembandingPasarList) {
        this.pembandingPasarList = pembandingPasarList;
    }

    public String getRingkasanPenyesuaian() {
        return ringkasanPenyesuaian;
    }

    public void setRingkasanPenyesuaian(String ringkasanPenyesuaian) {
        this.ringkasanPenyesuaian = ringkasanPenyesuaian;
    }

    public double getNilaiPasarIndikasi() {
        return nilaiPasarIndikasi;
    }

    public void setNilaiPasarIndikasi(double nilaiPasarIndikasi) {
        this.nilaiPasarIndikasi = nilaiPasarIndikasi;
    }
}