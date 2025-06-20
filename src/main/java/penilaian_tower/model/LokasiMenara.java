package penilaian_tower.model;

/**
 * Merepresentasikan lokasi menara berdasarkan dokumen.
 * - Rooftop: Menara di atas atap gedung.
 * - Greenfield: Menara di atas tanah langsung.
 */
public enum LokasiMenara {
    ROOFTOP("Rooftop Tower"),
    GREENFIELD("Greenfield Tower");

    private final String displayName;

    LokasiMenara(String displayName) {
        this.displayName = displayName;
    }

    // Metode ini agar nanti di UI bisa tampil nama yang bagus
    @Override
    public String toString() {
        return displayName;
    }
}