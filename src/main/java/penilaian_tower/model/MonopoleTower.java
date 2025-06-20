package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Tiang (Monopole Tower).
 */
public class MonopoleTower extends Menara {

    public MonopoleTower(double ketinggian, int umur) {
        // Properti untuk Monopole sudah lebih pasti:
        // Tipe Konstruksi: MONOPOLE_TOWER
        // Bentuk: POLE
        // Lokasi: GREENFIELD (Umumnya di atas tanah)
        super(TipeKonstruksi.MONOPOLE_TOWER, BentukMenara.POLE, LokasiMenara.GREENFIELD, ketinggian, umur);
    }
}