package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara dengan Penyangga Kabel (Guyed Wired Mast).
 * Menara ini disokong oleh kabel dan umumnya berdiri di atas tanah.
 */
public class GuyedWiredMast extends Menara {

    public GuyedWiredMast(BentukMenara bentuk, double ketinggian, int umur) {
        // Tipe Konstruksi: GUYED_WIRED_MAST (pasti)
        // Lokasi: GREENFIELD (pasti)
        // Bentuk: Diambil dari input (bisa TRIANGULAR atau POLE)
        super(TipeKonstruksi.GUYED_WIRED_MAST, bentuk, LokasiMenara.GREENFIELD, ketinggian, umur);
    }
}