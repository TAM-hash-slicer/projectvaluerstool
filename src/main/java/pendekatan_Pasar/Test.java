package pendekatan_Pasar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        try {
            // Format tanggal yang digunakan
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Tanggal penilaian
            String penilaian = "28/01/2021";
            Date tanggalPenilaian = sdf.parse(penilaian);

            // Tanggal transaksi (array)
            String[] tanggalTransaksi = {
                    "31/05/2018",
                    "19/01/2021",
                    "19/01/2021"
            };

            // Loop untuk menghitung selisih bulan untuk setiap tanggal transaksi
            for (String tglTransaksi : tanggalTransaksi) {
                Date transaksi = sdf.parse(tglTransaksi);

                // Menghitung selisih hari antara tanggal transaksi dan tanggal penilaian
                long diffInMillies = Math.abs(tanggalPenilaian.getTime() - transaksi.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                // Menghitung selisih bulan (asumsi 30 hari per bulan)
                double diffInMonths = diffInDays / 30.0;

                // Output hasil
                System.out.println(diffInDays);
                System.out.println("Selisih bulan dengan tanggal transaksi " + tglTransaksi + ": " + diffInMonths + " bulan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
