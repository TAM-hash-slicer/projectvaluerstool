package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Tiang Mini (Mini Pole Tower).
 * Berupa tiang (Pole) yang berada di atap gedung (Rooftop).
 */
public class MiniPoleTower extends Menara {

    public MiniPoleTower(double ketinggian, int umur) {
        // Tipe Konstruksi: MINI_POLE_TOWER (pasti)
        // Bentuk: POLE (pasti)
        // Lokasi: ROOFTOP (pasti)
        super(TipeKonstruksi.MINI_POLE_TOWER, BentukMenara.POLE, LokasiMenara.ROOFTOP, ketinggian, umur);
    }
}