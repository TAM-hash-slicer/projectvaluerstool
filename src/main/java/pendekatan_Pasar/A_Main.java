package pendekatan_Pasar;

import pendekatan_Pasar.dao.PropertyDAO;
import pendekatan_Pasar.model.Pembanding;
import pendekatan_Pasar.model.Subjek;
import pendekatan_Pasar.service.PropertyValuationService;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class A_Main {
    public static int[] penawaran_data_sampel = {528800000, 651000000, 590000000};
    public static LocalDate[] tanggal_Transaksi = {
            LocalDate.parse("2018-05-31"),
            LocalDate.parse("2021-01-19"),
            LocalDate.parse("2021-01-19")
    };
    public static LocalDate[] tanggal_Penilaian = {
            LocalDate.parse("2021-01-28")
    };
    public static String[] hak_atas_tanah = {"Hak Guna Bangunan", "Hak Guna Bangunan", "Hak Guna Bangunan"};

    public static void main(String[] args) {
        // Data properti yang dinilai (subject)
        Subjek subject = new Subjek();
        subject.setId("SUBJ-001");
        subject.setType("residensial");
        subject.setLocation("Jakarta");
        subject.setLandArea(120);
        subject.setBuildingArea(90);
        subject.setYearBuilt(2015);
        subject.setTransactionPrice(0); // belum diketahui
        subject.setTransactionDate(null);

        // Ambil data properti pembanding
        PropertyDAO dao = new PropertyDAO();
        List<Pembanding> comparables = dao.getComparables();

        // Lakukan penilaian
        PropertyValuationService service = new PropertyValuationService();
        double estimatedValue = service.estimateMarketValue(subject, comparables);

        // Cetak hasil
        System.out.printf("Estimasi nilai pasar properti: Rp%,.2f\n", estimatedValue);
    };
}