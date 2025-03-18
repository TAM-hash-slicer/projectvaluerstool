package org.example;
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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}