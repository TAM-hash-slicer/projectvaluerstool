package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Tiang Mikroselular (Micro Cell Pole).
 * Berupa tiang (Pole) yang berdiri di atas tanah (Greenfield).
 */
public class MicroCellPole extends Menara {

    public MicroCellPole(double ketinggian, int umur) {
        // Tipe Konstruksi: MICRO_CELL_POLE (pasti)
        // Bentuk: POLE (pasti)
        // Lokasi: GREENFIELD (pasti)
        super(TipeKonstruksi.MICRO_CELL_POLE, BentukMenara.POLE, LokasiMenara.GREENFIELD, ketinggian, umur);
    }
}