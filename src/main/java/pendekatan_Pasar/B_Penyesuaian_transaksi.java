package pendekatan_Pasar;

public class B_Penyesuaian_transaksi {

    /**
     * Menghitung harga transaksi setelah penyesuaian.
     * @param dataPenawaranArray Array berisi harga penawaran awal.
     * @param persentasePenyesuaian Faktor penyesuaian (misalnya, 0.95f untuk 95%).
     * @return Array float berisi harga setelah penyesuaian.
     */
    public static float[] hitungHargaSetelahPenyesuaian(int[] dataPenawaranArray, float persentasePenyesuaian) {
        if (dataPenawaranArray == null) {
            return new float[0]; // Kembalikan array kosong jika input null
        }
        float[] hargaSetelahPenyesuaian = new float[dataPenawaranArray.length];
        for (int i = 0; i < dataPenawaranArray.length; i++) {
            hargaSetelahPenyesuaian[i] = dataPenawaranArray[i] * persentasePenyesuaian;
        }
        return hargaSetelahPenyesuaian;
    }

    // Metode main bisa tetap ada untuk pengujian kelas ini secara terpisah
    public static void main(String[] args) {
        float persentase_penyesuaian = 0.95f; // 95%

        // Menggunakan data sampel dari A_Main untuk pengujian
        float[] hasilPenyesuaian = hitungHargaSetelahPenyesuaian(A_Main.penawaran_data_sampel, persentase_penyesuaian);

        // Mencetak hasil dari metode
        for (int i = 0; i < hasilPenyesuaian.length; i++) {
            System.out.printf("Harga transaksi (setelah penyesuaian) %d adalah sebesar: %.2f%n", (i + 1), hasilPenyesuaian[i]);
        }
    }
}