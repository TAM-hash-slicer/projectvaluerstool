package pendekatan_Pasar;

public class Test2 {float persentaseUntukPenyesuaianTransaksi = 0.95f; // Atau dapatkan nilai ini dari sumber lain
    float[] hargaPenawaranTelahDisesuaikan = B_Penyesuaian_transaksi.hitungHargaSetelahPenyesuaian(A_Main.penawaran_data_sampel, persentaseUntukPenyesuaianTransaksi);
    for (int i = 0; i < hargaPenawaranTelahDisesuaikan.length; i++) {
        System.out.printf("Harga transaksi (setelah penyesuaian) %d adalah sebesar: %.2f%n", (i + 1), hargaPenawaranTelahDisesuaikan[i]);
    }
};
