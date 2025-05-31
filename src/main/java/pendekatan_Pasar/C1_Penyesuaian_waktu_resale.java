package pendekatan_Pasar;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class C1_Penyesuaian_waktu_resale {
    public static void main (String[] args) {
        // Tanggal jual pertama
        LocalDate tgl_jual_pertama = LocalDate.parse("2016-12-20");
        DateTimeFormatter formattedTgl_jual_pertama = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate1 = tgl_jual_pertama.format(formattedTgl_jual_pertama);
        System.out.println("Tanggal penjualan pertama: " + formattedDate1);

        // Tanggal jual kedua
        LocalDate tgl_jual_kedua = LocalDate.parse("2018-08-08");
        DateTimeFormatter formattedTgl_jual_kedua = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate2 = tgl_jual_kedua.format(formattedTgl_jual_kedua);
        System.out.println("Tanggal penjualan kedua: " + formattedDate2);

        // Selisih jumlah bulan
        Period selisih = Period.between(tgl_jual_pertama, tgl_jual_kedua);
        float totalBulan = selisih.getYears() * 12 + selisih.getMonths() + (float) selisih.getDays() / 30; // Total bulan
        double ceil_totalBulan = Math.round(totalBulan * 10) / 10.0;
        System.out.printf("Selisih bulan: %.1f%n" , ceil_totalBulan );

        // Harga jual
        int hargaJual_pertama = 800000000;
        int hargaJual_kedua = 825000000;
        int selisihHarga = hargaJual_kedua - hargaJual_pertama;
        System.out.println("Selisih Harga Jual: " + selisihHarga);

        // Perbedaan harga per bulan
        double beda_harga;
        DecimalFormat df = new DecimalFormat("#");
        if (ceil_totalBulan != 0) {
            beda_harga = Double.parseDouble(df.format(selisihHarga / ceil_totalBulan));
            System.out.println("Perbedaan harga per bulan: " + beda_harga);
        } else {
            beda_harga = selisihHarga;
            System.out.println("Perbedaan harga per bulan: " + beda_harga);
        }

        // Persentase
        double persen_harga = beda_harga / hargaJual_pertama;
        double ceil_persenHarga = Math.round(persen_harga * 10000) / 10000.0;
        System.out.println("Persentase perbedaan harga per bulan: " + ceil_persenHarga);

        // Selisih tgl transaksi dgn tgl penilaian
        LocalDate tgl_penilaian = A_Main.tanggal_Penilaian [0];
        LocalDate tgl_pembanding1 = A_Main.tanggal_Transaksi [0];
        LocalDate tgl_pembanding2 = A_Main.tanggal_Transaksi [1];
        LocalDate tgl_pembanding3 = A_Main.tanggal_Transaksi [2];

        // Selisih pembanding 1
        Period selisihPembanding1 = Period.between(tgl_pembanding1, tgl_penilaian);
        float selisihBulan1 = selisihPembanding1.getYears() * 12 + selisihPembanding1.getMonths() + (float) selisihPembanding1.getDays() / 30; // Total bulan
        double ceil_selisihBulan1 = Math.round(selisihBulan1 * 10) / 10.0;
        System.out.printf("Selisih bulan Pembanding 1: %.1f%n" , ceil_selisihBulan1 );

        // Selisih pembanding 2
        Period selisihPembanding2 = Period.between(tgl_pembanding2, tgl_penilaian);
        float selisihBulan2 = selisihPembanding2.getYears() * 12 + selisihPembanding2.getMonths() + (float) selisihPembanding2.getDays() / 30; // Total bulan
        double ceil_selisihBulan2 = Math.round(selisihBulan2 * 10) / 10.0;
        System.out.printf("Selisih bulan Pembanding 2: %.1f%n" , ceil_selisihBulan2 );

        // Selisih pembanding 3
        Period selisihPembanding3 = Period.between(tgl_pembanding3, tgl_penilaian);
        float selisihBulan3 = selisihPembanding3.getYears() * 12 + selisihPembanding3.getMonths() + (float) selisihPembanding3.getDays() / 30; // Total bulan
        double ceil_selisihBulan3 = Math.round(selisihBulan3 * 10) / 10.0;
        System.out.printf("Selisih bulan Pembanding 3: %.1f%n" , ceil_selisihBulan3 );

        // Penyesuaian Waktu Transaksi Resale
        double Pembanding1_resale = ceil_persenHarga * ceil_selisihBulan1;
        double Pembanding2_resale = ceil_persenHarga * ceil_selisihBulan2;
        double Pembanding3_resale = ceil_persenHarga * ceil_selisihBulan3;
        System.out.println("Objek Pembanding 1: " + Pembanding1_resale);
        System.out.printf("Objek Pembanding 2: %.4f%n" , Pembanding2_resale);
        System.out.printf("Objek Pembanding 3: %.4f%n" , Pembanding3_resale);
    }
}