package penilaian_tower.model;

/**
 * Representasi spesifik untuk Menara Kamuflase.
 * Desain, bentuk, dan lokasinya sangat bervariasi karena disesuaikan
 * dengan lingkungan sekitar.
 */
public class CamouflageTower extends Menara {

    public CamouflageTower(BentukMenara bentuk, LokasiMenara lokasi, double ketinggian, int umur) {
        // Tipe Konstruksi: CAMOUFLAGE_TOWER (pasti)
        // Bentuk: Diambil dari input
        // Lokasi: Diambil dari input
        super(TipeKonstruksi.CAMOUFLAGE_TOWER, bentuk, lokasi, ketinggian, umur);
    }
}