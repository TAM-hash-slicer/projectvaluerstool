package penilaian_tower.model;

/**
 * Merepresentasikan tipe konstruksi menara sesuai dokumen.
 */
public enum TipeKonstruksi {
    GUYED_WIRED_MAST("Menara dengan Penyangga Kabel (Guyed)"), //
    LATTICE_TOWER("Menara Kisi Jeruji (Lattice Tower/SST)"), //
    MINI_TOWER("Menara Kisi Jeruji Kecil (Mini Tower)"), //
    MONOPOLE_TOWER("Menara Tiang (Monopole)"), //
    MICRO_CELL_POLE("Menara Tiang Mikroselular (MCP)"), //
    MINI_POLE_TOWER("Menara Tiang Mini (Mini Pole)"), //
    CAMOUFLAGE_TOWER("Menara Kamuflase"); //

    private final String displayName;

    TipeKonstruksi(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}