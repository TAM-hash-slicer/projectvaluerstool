package penilaian_tower.model;

/**
 * Merepresentasikan bentuk menara berdasarkan dokumen.
 * - Rectangular: Menara kisi jeruji segi empat.
 * - Triangular: Menara kisi jeruji segi tiga.
 * - Pole: Menara berupa tiang pancang.
 */
public enum BentukMenara {
    RECTANGULAR("Rectangular Tower"),
    TRIANGULAR("Triangular Tower"),
    POLE("Pole/Tiang");

    private final String displayName;

    BentukMenara(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}