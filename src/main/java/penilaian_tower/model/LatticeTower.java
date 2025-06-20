package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Kisi Jeruji (Lattice Tower / SST).
 */
public class LatticeTower extends Menara {

    // Constructor untuk membuat objek LatticeTower
    public LatticeTower(BentukMenara bentuk, double ketinggian, int umur) {
        // Saat membuat LatticeTower, kita langsung tentukan beberapa properti:
        // Tipe Konstruksi: LATTICE_TOWER
        // Lokasi: GREENFIELD (Umumnya di atas tanah)
        // Bentuk: Diambil dari input (bisa Rectangular atau Triangular)
        super(TipeKonstruksi.LATTICE_TOWER, bentuk, LokasiMenara.GREENFIELD, ketinggian, umur);
    }
}