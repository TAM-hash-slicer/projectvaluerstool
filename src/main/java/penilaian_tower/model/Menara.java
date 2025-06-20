package penilaian_tower.model;

/**
 * Kelas dasar (induk) yang merepresentasikan sebuah menara telekomunikasi.
 * Kelas ini 'abstract' karena tidak boleh ada objek "Menara" generik,
 * harus spesifik seperti "LatticeTower" atau "MonopoleTower".
 */
public abstract class Menara {

    protected TipeKonstruksi tipeKonstruksi;
    protected BentukMenara bentukMenara;
    protected LokasiMenara lokasiMenara;
    protected double ketinggian; // dalam meter
    protected int umur; // dalam tahun

    // Constructor untuk diisi oleh kelas anak
    public Menara(TipeKonstruksi tipeKonstruksi, BentukMenara bentukMenara, LokasiMenara lokasiMenara, double ketinggian, int umur) {
        this.tipeKonstruksi = tipeKonstruksi;
        this.bentukMenara = bentukMenara;
        this.lokasiMenara = lokasiMenara;
        this.ketinggian = ketinggian;
        this.umur = umur;
    }

    // Getter untuk mengambil data (nanti akan digunakan oleh bagian perhitungan)
    public TipeKonstruksi getTipeKonstruksi() {
        return tipeKonstruksi;
    }

    public BentukMenara getBentukMenara() {
        return bentukMenara;
    }

    public LokasiMenara getLokasiMenara() {
        return lokasiMenara;
    }

    public double getKetinggian() {
        return ketinggian;
    }

    public int getUmur() {
        return umur;
    }
}