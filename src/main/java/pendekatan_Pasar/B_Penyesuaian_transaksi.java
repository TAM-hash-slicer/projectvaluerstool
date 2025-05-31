package pendekatan_Pasar;

public class B_Penyesuaian_transaksi {
    public static void main(String[] args) {

        float persentase_penyesuaian = 0.95f;

        for (int i = 0; i < A_Main.penawaran_data_sampel.length; i++) {
            float harga_transaksi = A_Main.penawaran_data_sampel[i] * persentase_penyesuaian;
            System.out.printf("Harga transaksi %d adalah sebesar: %.2f%n", (i + 1), harga_transaksi);
        }
    }
}