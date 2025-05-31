/*'''package pendekatan_Pasar;

import pendekatan_Pasar.model.Pembanding;
import pendekatan_Pasar.model.Subjek;
// Jika Anda menggunakan PropertyDAO, uncomment import di bawah
// import pendekatan_Pasar.dao.PropertyDAO;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class G_Rekapitulasi_PenilaianTanah {

    private Subjek objekYangDinilai;
    private List<Pembanding> daftarObjekPembanding;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public G_Rekapitulasi_PenilaianTanah() {
        this.daftarObjekPembanding = new ArrayList<>();
    }

    // Metode untuk mengisi data contoh (sesuai gambar dan file Anda)
    public void isiDataContohDariGambar() {
        // === ISI DATA OBJEK YANG DINILAI ===
        objekYangDinilai = new Subjek();
        objekYangDinilai.setId("OBJDINILAI-001");
        objekYangDinilai.setJenisRealProperti("Tanah dan Bangunan");
        objekYangDinilai.setAlamat("The Green Hill View 2 Blok A8 RT 034");
        objekYangDinilai.setDesaKelurahan("Sumber Rejo");
        objekYangDinilai.setKecamatan("Balikpapan Tengah");
        objekYangDinilai.setKabupatenKota("Balikpapan");
        objekYangDinilai.setProvinsi("Kalimantan Timur");
        objekYangDinilai.setKoordinat("-");
        objekYangDinilai.setLuasTanahM2(82.00);
        objekYangDinilai.setSumberData("Lainnya");
        objekYangDinilai.setTelepon("085252031775");
        objekYangDinilai.setKeteranganUmum("0");
        objekYangDinilai.setJenisTransaksiObjek("-"); // Kolom 9 untuk Objek Dinilai
        objekYangDinilai.setTanggalTransaksiObjek((Object) null); // Kolom 10
        objekYangDinilai.setHakAtasTanahObjek("Hak Guna Bangunan"); // Kolom 11
        objekYangDinilai.setLuasBangunanM2(45.00); // Kolom 12
        objekYangDinilai.setJenisPenggunaanBangunan("Rumah"); // Kolom 13
        objekYangDinilai.setKondisiUmumBangunan("Baik"); // Kolom 14
        objekYangDinilai.setKriteriaBangunan("Sederhana"); // Kolom 15
        objekYangDinilai.setTahunDibangun(2019); // Kolom 16
        objekYangDinilai.setTahunRenovasi("-"); // Kolom 17
        objekYangDinilai.setJumlahLantai(1); // Kolom 18
        objekYangDinilai.setDinding("Bata"); // Kolom 19
        objekYangDinilai.setPelapisDinding("Cat"); // Kolom 20
        objekYangDinilai.setLantaiBangunan("Keramik"); // Kolom 21
        objekYangDinilai.setLangitLangit("Eternit"); // Kolom 22
        objekYangDinilai.setAtap("Genteng Keramik"); // Kolom 23
        // Kolom 24. Nilai Pengembangan Tanah (Rp/m2) untuk Objek Dinilai - Perlu diisi jika ada.
        // Misal: objekYangDinilai.setNilaiPengembanganTanahPerM2(3000000.0);
        objekYangDinilai.setNilaiPengembanganTanahPerM2(null); // Atau null jika tidak ada/belum dihitung


        objekYangDinilai.setBentukTanah("Persegi"); // Kolom 25
        objekYangDinilai.setPosisiTanah("Ujung"); // Kolom 26
        objekYangDinilai.setKonturTopografiTanah("miring"); // Kolom 27
        objekYangDinilai.setLebarDepanM(7.00); // Kolom 28
        objekYangDinilai.setLebarJalanM(6.00); // Kolom 29
        objekYangDinilai.setPenggunaanTanahSaatIni("Perumahan"); // Kolom 30
        objekYangDinilai.setPeruntukanTanahZona("Pemukiman"); // Kolom 31
        objekYangDinilai.setAksesibilitas("Jalan Lingkungan"); // Kolom 32
        objekYangDinilai.setFasilitasRadius1Km("SDN 011, puskesmas sumber rejo, GKJTDI Balikpapan"); // Kolom 33


        // === ISI DATA OBJEK PEMBANDING ===
        Pembanding op1 = new Pembanding();
        op1.setId("OP1");
        op1.setJenisRealProperti("Tanah dan Bangunan");
        op1.setAlamat("The Greenhill View 2 Jalan Binaraga");
        op1.setDesaKelurahan("Sumber Rejo"); op1.setKecamatan("Balikpapan Tengah"); op1.setKabupatenKota("Balikpapan"); op1.setProvinsi("Kalimantan Timur");
        op1.setKoordinat("-"); op1.setLuasTanahM2(75.00); op1.setSumberData("Lainnya"); op1.setTelepon("085290248030"); op1.setKeteranganUmum("PT-50888");
        op1.setHargaTransaksiRp(528800000); op1.setJenisTransaksiObjek("Penawaran"); op1.setTanggalTransaksiObjek("2018-05-31"); op1.setHakAtasTanahObjek("Hak Guna Bangunan");
        op1.setLuasBangunanM2(45.00); op1.setJenisPenggunaanBangunan("Rumah"); op1.setKondisiUmumBangunan("Baik"); op1.setKriteriaBangunan("Sederhana"); op1.setTahunDibangun(2020);
        op1.setNilaiPengembanganTanahPerM2(3170027.0); // Dari gambar page 1, baris 24 untuk OP1
        op1.setBentukTanah("Persegi"); op1.setPosisiTanah("tengah"); op1.setKonturTopografiTanah("Datar"); op1.setLebarDepanM(7.00); op1.setLebarJalanM(6.00);
        op1.setPenggunaanTanahSaatIni("Perumahan"); op1.setPeruntukanTanahZona("Pemukiman"); op1.setAksesibilitas("Jalan Lingkungan"); op1.setFasilitasRadius1Km("SDN 011, puskesmas sumber rejo, GKJTDI Balikpapan");
        op1.setPenyesuaianJenisTransaksiPersen(-17.00); op1.setPenyesuaianWaktuTransaksiPersen(-1.28); op1.setPenyesuaianHakAtasTanahPersen(0.00); op1.setPenyesuaianLokasiDanFisikPersen(-8.12);
        this.daftarObjekPembanding.add(op1);

        Pembanding op2 = new Pembanding();
        op2.setId("OP2");
        op2.setJenisRealProperti("Tanah dan Bangunan");
        op2.setAlamat("The Greenhill View 2 Jalan Binaraga Type 45/110");
        op2.setDesaKelurahan("Sumber Rejo"); op2.setKecamatan("Balikpapan Tengah"); op2.setKabupatenKota("Balikpapan"); op2.setProvinsi("Kalimantan Timur");
        op2.setKoordinat("-"); op2.setLuasTanahM2(110.00); op2.setSumberData("Lainnya"); op2.setTelepon("085246207909"); op2.setKeteranganUmum("PT-70193");
        op2.setHargaTransaksiRp(651000000); op2.setJenisTransaksiObjek("Penawaran"); op2.setTanggalTransaksiObjek("2021-01-19"); op2.setHakAtasTanahObjek("Hak Guna Bangunan");
        op2.setLuasBangunanM2(45.00); op2.setJenisPenggunaanBangunan("Rumah"); op2.setKondisiUmumBangunan("Baik"); op2.setKriteriaBangunan("Sederhana"); op2.setTahunDibangun(2020);
        op2.setNilaiPengembanganTanahPerM2(3679574.0); // Dari gambar page 1, baris 24 untuk OP2
        op2.setBentukTanah("Persegi"); op2.setPosisiTanah("Ujung"); op2.setKonturTopografiTanah("Datar"); op2.setLebarDepanM(7.00); op2.setLebarJalanM(6.00);
        op2.setPenggunaanTanahSaatIni("Perumahan"); op2.setPeruntukanTanahZona("Pemukiman"); op2.setAksesibilitas("Jalan Lingkungan"); op2.setFasilitasRadius1Km("SDN 011, puskesmas sumber rejo, GKJTDI Balikpapan");
        op2.setPenyesuaianJenisTransaksiPersen(-17.00); op2.setPenyesuaianWaktuTransaksiPersen(-0.01); op2.setPenyesuaianHakAtasTanahPersen(0.00); op2.setPenyesuaianLokasiDanFisikPersen(0.00);
        this.daftarObjekPembanding.add(op2);

        Pembanding op3 = new Pembanding();
        op3.setId("OP3");
        op3.setJenisRealProperti("Tanah dan Bangunan");
        op3.setAlamat("The Greenhill View 2 Jalan Binaraga Type 45/90");
        op3.setDesaKelurahan("Sumber Rejo"); op3.setKecamatan("Balikpapan Tengah"); op3.setKabupatenKota("Balikpapan"); op3.setProvinsi("Kalimantan Timur");
        op3.setKoordinat("-"); op3.setLuasTanahM2(90.00); op3.setSumberData("Lainnya"); op3.setTelepon("085246207909"); op3.setKeteranganUmum("PT-70185");
        op3.setHargaTransaksiRp(590000000); op3.setJenisTransaksiObjek("Penawaran"); op3.setTanggalTransaksiObjek("2021-01-19"); op3.setHakAtasTanahObjek("Hak Guna Bangunan");
        op3.setLuasBangunanM2(45.00); op3.setJenisPenggunaanBangunan("Rumah"); op3.setKondisiUmumBangunan("Baik"); op3.setKriteriaBangunan("Sederhana"); op3.setTahunDibangun(2020);
        op3.setNilaiPengembanganTanahPerM2(3679574.0); // Dari gambar page 1, baris 24 untuk OP3 (sama dengan OP2 di gambar)
        op3.setBentukTanah("Persegi"); op3.setPosisiTanah("Ujung"); op3.setKonturTopografiTanah("Datar"); op3.setLebarDepanM(7.00); op3.setLebarJalanM(6.00);
        op3.setPenggunaanTanahSaatIni("Perumahan"); op3.setPeruntukanTanahZona("Pemukiman"); op3.setAksesibilitas("Jalan Lingkungan"); op3.setFasilitasRadius1Km("SDN 011, puskesmas sumber rejo, GKJTDI Balikpapan");
        op3.setPenyesuaianJenisTransaksiPersen(-17.00); op3.setPenyesuaianWaktuTransaksiPersen(-0.01); op3.setPenyesuaianHakAtasTanahPersen(0.00); op3.setPenyesuaianLokasiDanFisikPersen(0.00);
        this.daftarObjekPembanding.add(op3);
    }

    public void tampilkanRekapitulasi() {
        if (objekYangDinilai == null || daftarObjekPembanding.isEmpty()) {
            System.out.println("Data objek dinilai atau objek pembanding belum diisi.");
            return;
        }

        System.out.println("\n=== G. REKAPITULASI PENILAIAN TANAH ===");
        displayBagian("1. INFORMASI UMUM TERKAIT TANAH", this::barisInformasiUmum);
        displayBagian("2. INFORMASI PENGEMBANGAN TANAH", this::barisInformasiPengembanganTanah);
        displayBagian("3. INFORMASI LOKASI DAN FISIK", this::barisInformasiLokasiDanFisik);
        displayBagian4Penghitungan();
    }

    // --- Helper untuk display bagian tabel ---
    private void displayBagian(String judulBagian, java.util.function.Consumer<String[]> displayFunction) {
        System.out.println("\n--- " + judulBagian + " ---");
        printHorizontalLine(165);
        System.out.printf(Locale.US, "%-35s | %-30s | %-30s | %-30s | %-30s\n", "Uraian", "Objek Yang Dinilai", "Objek Pembanding 1", "Objek Pembanding 2", "Objek Pembanding 3");
        printHorizontalLine(165);
        displayFunction.accept(null); // Panggil fungsi untuk menampilkan baris-baris data
        printHorizontalLine(165);
    }

    private void barisInformasiUmum(String[] ignored) { // Parameter tidak digunakan, hanya untuk mencocokkan Consumer
        tampilkanBaris("1. Jenis Real Properti", s -> s.getJenisRealProperti());
        tampilkanBaris("2. Alamat", s -> s.getAlamat());
        tampilkanBaris("   Desa/Kelurahan", s -> s.getDesaKelurahan());
        tampilkanBaris("   Kecamatan", s -> s.getKecamatan());
        tampilkanBaris("   Kabupaten/Kota", s -> s.getKabupatenKota());
        tampilkanBaris("   Provinsi", s -> s.getProvinsi());
        tampilkanBaris("3. Koordinat", s -> s.getKoordinat());
        tampilkanBaris("4. Luas Tanah (m2)", s -> formatDouble(s.getLuasTanahM2(), 2));
        tampilkanBaris("5. Sumber Data", s -> s.getSumberData());
        tampilkanBaris("6. Telepon", s -> s.getTelepon());
        tampilkanBaris("7. Keterangan", s -> s.getKeteranganUmum());
        tampilkanBaris("8. Harga Transaksi (Rp)", s -> (s instanceof Pembanding && ((Pembanding) s).getHargaTransaksiRp() > 0) ? String.format(Locale.US, "%,.0f", s.getHargaTransaksiRp()) : (objekYangDinilai.equals(s) ? "-" : "0"));
        tampilkanBaris("9. Jenis Transaksi", s -> s.getJenisTransaksiObjek());
        tampilkanBaris("10. Tanggal Transaksi", s -> (s.getTanggalTransaksiObjek() != null) ? s.getTanggalTransaksiObjek().format(dtf) : "-");
        tampilkanBaris("11. Hak Atas Tanah", s -> s.getHakAtasTanahObjek());
    }

    private void barisInformasiPengembanganTanah(String[] ignored) {
        tampilkanBaris("12. Luas Bangunan (m2)", s -> formatDouble(s.getLuasBangunanM2(), 2));
        tampilkanBaris("13. Jenis Pengg. Bangunan", s -> s.getJenisPenggunaanBangunan());
        tampilkanBaris("14. Kondisi Umum", s -> s.getKondisiUmumBangunan());
        tampilkanBaris("15. Kriteria Bangunan", s -> s.getKriteriaBangunan());
        tampilkanBaris("16. Tahun Dibangun", s -> String.valueOf(s.getTahunDibangun()));
        tampilkanBaris("17. Tahun Renovasi", s -> s.getTahunRenovasi());
        tampilkanBaris("18. Jumlah Lantai", s -> String.valueOf(s.getJumlahLantai()));
        tampilkanBaris("19. Dinding", s -> s.getDinding());
        tampilkanBaris("20. Pelapis Dinding", s -> s.getPelapisDinding());
        tampilkanBaris("21. Lantai", s -> s.getLantaiBangunan());
        tampilkanBaris("22. Langit-langit", s -> s.getLangitLangit());
        tampilkanBaris("23. Atap", s -> s.getAtap());
        tampilkanBaris("24. Nilai Pengemb. (Rp/m2)", s -> (s.getNilaiPengembanganTanahPerM2() != null) ? String.format(Locale.US, "%,.0f", s.getNilaiPengembanganTanahPerM2()) : "-");
    }

    private void barisInformasiLokasiDanFisik(String[] ignored) {
        tampilkanBaris("25. Bentuk Tanah", s -> s.getBentukTanah());
        tampilkanBaris("26. Posisi Tanah", s -> s.getPosisiTanah());
        tampilkanBaris("27. Kontur/Topografi", s -> s.getKonturTopografiTanah());
        tampilkanBaris("28. Lebar Depan (m)", s -> formatDouble(s.getLebarDepanM(), 2));
        tampilkanBaris("29. Lebar Jalan (m)", s -> formatDouble(s.getLebarJalanM(), 2));
        tampilkanBaris("30. Penggunaan", s -> s.getPenggunaanTanahSaatIni());
        tampilkanBaris("31. Peruntukan", s -> s.getPeruntukanTanahZona());
        tampilkanBaris("32. Aksesibilitas", s -> s.getAksesibilitas());
        tampilkanBaris("33. Fasilitas (radius 1 km)", s -> s.getFasilitasRadius1Km());
    }

    private void displayBagian4Penghitungan() {
        System.out.println("\n--- 4. PENGHITUNGAN INDIKASI NILAI TANAH DENGAN RANGKAIAN PENYESUAIAN ---");
        printHorizontalLine(125); // Adjusted width
        System.out.printf(Locale.US, "%-60s | %-20s | %-20s | %-20s\n", "Uraian", "Objek Pembanding 1", "Objek Pembanding 2", "Objek Pembanding 3");
        printHorizontalLine(125);

        double totalIndeksKesebandingan = 0;
        double totalTertimbangINPWPerM2 = 0;

        // Lakukan semua perhitungan untuk setiap objek pembanding
        for (Pembanding op : daftarObjekPembanding) {
            op.hitungSemuaPenyesuaianBertahap(); // Hitung baris 36, 38, 40, 42, 44, 46
            op.hitungJumlahPenyesuaianAbsolut(); // Hitung kolom (7)
            op.hitungIndeksKesebandingan();      // Hitung kolom (8)
            totalIndeksKesebandingan += op.getIndeksKesebandingan();
        }

        // Hitung Kolom (9) dan Indikasi Nilai Tanah Objek Dinilai (Baris 48)
        for (Pembanding op : daftarObjekPembanding) {
            if (totalIndeksKesebandingan != 0) {
                double bobot = (op.getIndeksKesebandingan() / totalIndeksKesebandingan) * 100.0;
                op.setBobotRekonsiliasiPersen(bobot);
                if (op.getHargaTanahPerM2SetelahPnyLengkap() != null) {
                    totalTertimbangINPWPerM2 += (op.getHargaTanahPerM2SetelahPnyLengkap() * (bobot / 100.0));
                }
            } else {
                op.setBobotRekonsiliasiPersen(0.0);
            }
        }

        // Menampilkan data perhitungan (Page 2)
        tampilkanBarisPny("34. Harga Transaksi (Rp)", p -> String.format(Locale.US, "%,.0f", p.getHargaTransaksiRp()));
        tampilkanBarisPnyPersen("35. Pny. Jenis Transaksi (%) (a)", p -> p.getPenyesuaianJenisTransaksiPersen());
        tampilkanBarisPny("36. Hrg Stlh Pny Jenis Tx (Rp) (c)", p -> String.format(Locale.US, "%,.0f", p.getHargaSetelahPnyJenisTx()));
        tampilkanBarisPnyPersen("37. Pny. Waktu Transaksi (%) (d)", p -> p.getPenyesuaianWaktuTransaksiPersen());
        tampilkanBarisPny("38. Hrg Stlh Pny Jenis&Wkt Tx (Rp) (e)", p -> String.format(Locale.US, "%,.0f", p.getHargaSetelahPnyJenisDanWaktuTx()));
        tampilkanBarisPny("39. Biaya Pengemb. Tanah (Rp) (f)", p -> String.format(Locale.US, "%,.0f", p.getBiayaPengembanganTanahHitung()));
        tampilkanBarisPny("40. Hrg Tnh Tnp Pengemb. Stlh Pny (Rp) (g)", p -> String.format(Locale.US, "%,.0f", p.getHargaTanahSetelahPengembanganDikurangi()));
        tampilkanBarisPny("41. Luas Tanah (m2) (h)", p -> formatDouble(p.getLuasTanahM2(),2) );
        tampilkanBarisPny("42. Hrg Tnh/m2 Stlh Pny Jenis&Wkt (Rp) (i)", p -> String.format(Locale.US, "%,.0f", p.getHargaTanahPerM2SetelahPnyJenisWaktu()));
        tampilkanBarisPnyPersen("43. Pny. Hak Atas Tanah (%) (j)", p -> p.getPenyesuaianHakAtasTanahPersen());
        tampilkanBarisPny("44. Hrg Tnh/m2 Stlh Pny Jns,Wkt,Hak (Rp) (k)", p -> String.format(Locale.US, "%,.0f", p.getHargaTanahPerM2SetelahPnyJenisWaktuHak()));
        tampilkanBarisPnyPersen("45. Pny. Lokasi dan Fisik (%) (l)", p -> p.getPenyesuaianLokasiDanFisikPersen());
        tampilkanBarisPny("46. Hrg Tnh/m2 Stlh Pny Lgkp (INPW/m2) (m)", p -> String.format(Locale.US, "%,.0f", p.getHargaTanahPerM2SetelahPnyLengkap()));

        printHorizontalLine(125);
        System.out.println("   --- Hasil Perhitungan Rekonsiliasi Nilai (Formulir Awal) ---");
        tampilkanBarisPnyPersen("   Jml. Pny. Absolut (%) [Kolom 7]", p -> p.getJumlahPenyesuaianAbsolut());
        tampilkanBarisPnyDesimal("   Indeks Kesebandingan [Kolom 8]", p -> p.getIndeksKesebandingan(), 2);
        tampilkanBarisPnyPersen("   Bobot Rekonsiliasi (%) [Kolom 9]", p -> p.getBobotRekonsiliasiPersen());
        printHorizontalLine(125);

        System.out.printf(Locale.US, "%-60s | %-58s\n", "48. Indikasi Nilai Tanah per m2 Objek Yg Dinilai (Rp)", String.format(Locale.US, "%,.0f", totalTertimbangINPWPerM2));
        printHorizontalLine(125);
    }


    // Helper untuk format angka
    private String formatDouble(double value, int decimalPlaces) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return "-";
        return String.format(Locale.US, "%." + decimalPlaces + "f", value);
    }
    private String formatDoublePersen(Double value) {
        if (value == null) return "-";
        return String.format(Locale.US, "%.2f%%", value);
    }

    // Helper method untuk menampilkan baris data umum (Bagian 1, 2, 3)
    private void tampilkanBaris(String uraian, java.util.function.Function<Subjek, String> extractor) {
        System.out.printf(Locale.US, "%-35s | %-30s | %-30s | %-30s | %-30s\n",
                uraian,
                objekYangDinilai != null && extractor.apply(objekYangDinilai) != null ? extractor.apply(objekYangDinilai) : "-",
                !daftarObjekPembanding.isEmpty() && daftarObjekPembanding.get(0) != null && extractor.apply(daftarObjekPembanding.get(0)) != null ? extractor.apply(daftarObjekPembanding.get(0)) : "-",
                daftarObjekPembanding.size() > 1 && daftarObjekPembanding.get(1) != null && extractor.apply(daftarObjekPembanding.get(1)) != null ? extractor.apply(daftarObjekPembanding.get(1)) : "-",
                daftarObjekPembanding.size() > 2 && daftarObjekPembanding.get(2) != null && extractor.apply(daftarObjekPembanding.get(2)) != null ? extractor.apply(daftarObjekPembanding.get(2)) : "-"
        );
    }

    // Helper method untuk menampilkan baris data perhitungan (Bagian 4) khusus pembanding
    private void tampilkanBarisPny(String uraian, java.util.function.Function<Pembanding, String> extractor) {
        System.out.printf(Locale.US, "%-60s | %-20s | %-20s | %-20s\n",
                uraian,
                !daftarObjekPembanding.isEmpty() && extractor.apply(daftarObjekPembanding.get(0)) != null ? extractor.apply(daftarObjekPembanding.get(0)) : "-",
                daftarObjekPembanding.size() > 1 && extractor.apply(daftarObjekPembanding.get(1)) != null ? extractor.apply(daftarObjekPembanding.get(1)) : "-",
                daftarObjekPembanding.size() > 2 && extractor.apply(daftarObjekPembanding.get(2)) != null ? extractor.apply(daftarObjekPembanding.get(2)) : "-"
        );
    }
    private void tampilkanBarisPnyPersen(String uraian, java.util.function.Function<Pembanding, Double> extractor) {
        System.out.printf(Locale.US, "%-60s | %-18s | %-18s | %-18s\n",
                uraian,
                !daftarObjekPembanding.isEmpty() && extractor.apply(daftarObjekPembanding.get(0)) != null ? formatDoublePersen(extractor.apply(daftarObjekPembanding.get(0))) : "-",
                daftarObjekPembanding.size() > 1 && extractor.apply(daftarObjekPembanding.get(1)) != null ? formatDoublePersen(extractor.apply(daftarObjekPembanding.get(1))) : "-",
                daftarObjekPembanding.size() > 2 && extractor.apply(daftarObjekPembanding.get(2)) != null ? formatDoublePersen(extractor.apply(daftarObjekPembanding.get(2))) : "-"
        );
    }
    private void tampilkanBarisPnyDesimal(String uraian, java.util.function.Function<Pembanding, Double> extractor, int decimal) {
        String format = "%." + decimal + "f";
        System.out.printf(Locale.US, "%-60s | %-20s | %-20s | %-20s\n",
                uraian,
                !daftarObjekPembanding.isEmpty() && extractor.apply(daftarObjekPembanding.get(0)) != null ? String.format(Locale.US, format, extractor.apply(daftarObjekPembanding.get(0))) : "-",
                daftarObjekPembanding.size() > 1 && extractor.apply(daftarObjekPembanding.get(1)) != null ? String.format(Locale.US, format, extractor.apply(daftarObjekPembanding.get(1))) : "-",
                daftarObjekPembanding.size() > 2 && extractor.apply(daftarObjekPembanding.get(2)) != null ? String.format(Locale.US, format, extractor.apply(daftarObjekPembanding.get(2))) : "-"
        );
    }

    private void printHorizontalLine(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        G_Rekapitulasi_PenilaianTanah rekap = new G_Rekapitulasi_PenilaianTanah();
        // Jika Anda ingin menggunakan PropertyDAO untuk mengambil data pembanding:
        // PropertyDAO dao = new PropertyDAO();
        // rekap.daftarObjekPembanding = dao.getComparables(); // Pastikan getComparables() mengembalikan List<Pembanding>
        // Anda masih perlu mengisi objekYangDinilai secara manual atau dari sumber lain
        // Dan mengisi data penyesuaian untuk setiap Pembanding yang diambil dari DAO

        rekap.isiDataContohDariGambar(); // Untuk saat ini, kita gunakan data contoh yang dihardcode
        rekap.tampilkanRekapitulasi();
    }
}'''*/