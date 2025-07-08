package model;

/**
 * Represents a single comparable property sale (Data Pembanding Pasar)
 * used specifically for the Market Approach.
 */
public class PembandingPasar {

    private String alamat;
    private double hargaTransaksi;
    private double penyesuaianTotalPersen; // e.g., -5.0 for -5% adjustment
    private double nilaiIndikasiSetelahPenyesuaian;

    // --- Getters and Setters ---

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getHargaTransaksi() {
        return hargaTransaksi;
    }

    public void setHargaTransaksi(double hargaTransaksi) {
        this.hargaTransaksi = hargaTransaksi;
    }

    public double getPenyesuaianTotalPersen() {
        return penyesuaianTotalPersen;
    }

    public void setPenyesuaianTotalPersen(double penyesuaianTotalPersen) {
        this.penyesuaianTotalPersen = penyesuaianTotalPersen;
    }

    public double getNilaiIndikasiSetelahPenyesuaian() {
        return nilaiIndikasiSetelahPenyesuaian;
    }

    public void setNilaiIndikasiSetelahPenyesuaian(double nilaiIndikasiSetelahPenyesuaian) {
        this.nilaiIndikasiSetelahPenyesuaian = nilaiIndikasiSetelahPenyesuaian;
    }
}