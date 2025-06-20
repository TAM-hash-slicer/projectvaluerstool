package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Kisi Jeruji Kecil (Mini Tower).
 * Umumnya berada di atap gedung (Rooftop).
 * Bentuknya bisa 3 atau 4 kaki.
 */
public class MiniTower extends Menara {

    public MiniTower(BentukMenara bentuk, double ketinggian, int umur) {
        // Tipe Konstruksi: MINI_TOWER (pasti)
        // Lokasi: ROOFTOP (pasti)
        // Bentuk: Diambil dari input (RECTANGULAR atau TRIANGULAR)
        super(TipeKonstruksi.MINI_TOWER, bentuk, LokasiMenara.ROOFTOP, ketinggian, umur);
    }
}