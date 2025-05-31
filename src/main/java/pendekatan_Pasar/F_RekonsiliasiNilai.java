package pendekatan_Pasar; // Pastikan package sesuai

import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // Impor untuk Locale jika ingin format desimal Indonesia

public class F_RekonsiliasiNilai { // Kelas publik utama, namanya sama dengan nama file

    public void lakukanProsesRekonsiliasi() {
        System.out.println("Memulai proses rekonsiliasi nilai...");
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf(Locale.US, "%-20s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s\n",
                "Objek Pembanding", "Jenis Transaksi", "Waktu Transaksi", "Hak Tanah", "LokasiFisik", "Jml Abs", "Indeks", "Bobot");
        System.out.println("---------------------------------------------------------------------------------------------------");


        // Membuat daftar objek pembanding
        List<ObjekPembanding> daftarObjekPembanding = new ArrayList<>();
        // Data objek sesuai dengan contoh di gambar Anda
        // Objek Pembanding 1
        daftarObjekPembanding.add(new ObjekPembanding("Objek Pembanding 1", -17.00, -1.28, 0.00, -8.12));
        // Objek Pembanding 2
        daftarObjekPembanding.add(new ObjekPembanding("Objek Pembanding 2", -17.00, -0.01, 0.00, 0.00));
        // Objek Pembanding 3 (sesuai gambar, sama dengan Objek 2 untuk kolom 3-6)
        daftarObjekPembanding.add(new ObjekPembanding("Objek Pembanding 3", -17.00, -0.01, 0.18, 0.00));


        // Menghitung kolom (7) dan (8) untuk setiap objek
        // dan menghitung total Indeks Kesebandingan (Σ kolom (8))
        double totalIndeksKesebandingan = 0;
        for (ObjekPembanding op : daftarObjekPembanding) {
            op.setJumlahPenyesuaianAbsolut(op.hitungInternalJumlahPenyesuaianAbsolut());
            op.setIndeksKesebandingan(op.hitungInternalIndeksKesebandingan());
            totalIndeksKesebandingan += op.getIndeksKesebandingan();
        }

        // Menghitung kolom (9) untuk setiap objek dan menampilkannya
        for (ObjekPembanding op : daftarObjekPembanding) {
            double bobotRekonsiliasi = (op.getIndeksKesebandingan() / totalIndeksKesebandingan) * 100;
            op.setBobotRekonsiliasi(bobotRekonsiliasi);

            // Menampilkan hasil dengan format seperti tabel
            // Locale.US digunakan agar format desimal menggunakan titik (.)
            System.out.printf(Locale.US, "%-20s | %-9.2f%% | %-9.2f%% | %-9.2f%% | %-9.2f%% | %-9.2f%% | %-9.2f | %-9.2f%%\n",
                    op.getNama(),
                    op.getPersenJenisTransaksi(),
                    op.getPersenWaktuTransaksi(),
                    op.getPersenHakAtasTanah(),
                    op.getPersenLokasiDanFisik(),
                    op.getJumlahPenyesuaianAbsolut(),
                    op.getIndeksKesebandingan(),
                    op.getBobotRekonsiliasi());
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf(Locale.US, "Total Indeks Kesebandingan (Σ Kolom 8): %.4f\n", totalIndeksKesebandingan);
    }

    public static void main(String[] args) {
        F_RekonsiliasiNilai rekon = new F_RekonsiliasiNilai();
        rekon.lakukanProsesRekonsiliasi();
    }

    // --- Kelas ObjekPembanding sebagai STATIC INNER CLASS ---
    public static class ObjekPembanding {
        private String nama; // Tambahan field nama untuk identifikasi
        private double persenJenisTransaksi;
        private double persenWaktuTransaksi;
        private double persenHakAtasTanah;
        private double persenLokasiDanFisik;

        // Hasil perhitungan
        private double jumlahPenyesuaianAbsolut; // Kolom (7)
        private double indeksKesebandingan;      // Kolom (8)
        private double bobotRekonsiliasi;        // Kolom (9)


        public ObjekPembanding(String nama, double persenJenisTransaksi, double persenWaktuTransaksi, double persenHakAtasTanah, double persenLokasiDanFisik) {
            this.nama = nama;
            this.persenJenisTransaksi = persenJenisTransaksi;
            this.persenWaktuTransaksi = persenWaktuTransaksi;
            this.persenHakAtasTanah = persenHakAtasTanah;
            this.persenLokasiDanFisik = persenLokasiDanFisik;
        }

        // Metode internal untuk menghitung kolom (7)
        public double hitungInternalJumlahPenyesuaianAbsolut() {
            return Math.abs(this.persenJenisTransaksi) +
                    Math.abs(this.persenWaktuTransaksi) +
                    Math.abs(this.persenHakAtasTanah) +
                    Math.abs(this.persenLokasiDanFisik);
        }

        // Metode internal untuk menghitung kolom (8)
        // Menggunakan hasil dari kolom (7) yang sudah dihitung
        public double hitungInternalIndeksKesebandingan() {
            // jumlahPenyesuaianAbsolut adalah persentase (misal 26.40)
            // Ubah ke desimal sebelum dikurangkan dari 1
            double nilaiKolom7Desimal = this.jumlahPenyesuaianAbsolut / 100.0;
            return 1.0 - nilaiKolom7Desimal;
        }

        // Getters
        public String getNama() { return nama; }
        public double getPersenJenisTransaksi() { return persenJenisTransaksi; }
        public double getPersenWaktuTransaksi() { return persenWaktuTransaksi; }
        public double getPersenHakAtasTanah() { return persenHakAtasTanah; }
        public double getPersenLokasiDanFisik() { return persenLokasiDanFisik; }
        public double getJumlahPenyesuaianAbsolut() { return jumlahPenyesuaianAbsolut; }
        public double getIndeksKesebandingan() { return indeksKesebandingan; }
        public double getBobotRekonsiliasi() { return bobotRekonsiliasi; }

        // Setters untuk hasil perhitungan (dipanggil dari luar setelah dihitung)
        public void setJumlahPenyesuaianAbsolut(double jumlahPenyesuaianAbsolut) {
            this.jumlahPenyesuaianAbsolut = jumlahPenyesuaianAbsolut;
        }

        public void setIndeksKesebandingan(double indeksKesebandingan) {
            this.indeksKesebandingan = indeksKesebandingan;
        }

        public void setBobotRekonsiliasi(double bobotRekonsiliasi) {
            this.bobotRekonsiliasi = bobotRekonsiliasi;
        }

        // Setter untuk field input jika diperlukan (contoh)
        public void setPersenJenisTransaksi(double persenJenisTransaksi) {
            this.persenJenisTransaksi = persenJenisTransaksi;
        }
    }
    // --- Akhir dari inner class ObjekPembanding ---
}