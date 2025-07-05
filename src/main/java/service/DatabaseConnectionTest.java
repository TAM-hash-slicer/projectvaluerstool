package starter; // atau package 'test' jika Anda membuatnya

import service.DatabaseService;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Kelas ini hanya digunakan untuk mengetes koneksi ke database secara independen.
 * Ini bukan bagian dari aplikasi utama, tetapi alat bantu untuk development.
 */
public class DatabaseConnectionTest {

    /**
     * Metode main adalah titik awal eksekusi.
     * Kita akan mencoba membuat koneksi database dari sini.
     */
    public static void main(String[] args) {
        System.out.println("Mencoba membuat koneksi ke database...");

        // 1. Buat instance dari DatabaseService
        DatabaseService dbService = new DatabaseService();

        // 2. Panggil metode connect()
        Connection conn = dbService.connect();

        // 3. Periksa hasilnya
        if (conn != null) {
            System.out.println("==============================================");
            System.out.println("SELAMAT! Koneksi ke database berhasil.");
            System.out.println("==============================================");
            try {
                // Jangan lupa untuk selalu menutup koneksi setelah selesai menggunakannya.
                conn.close();
                System.out.println("Koneksi berhasil ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        } else {
            System.out.println("==============================================");
            System.out.println("GAGAL! Tidak dapat terhubung ke database.");
            System.out.println("Periksa pesan error di atas untuk petunjuk.");
            System.out.println("==============================================");
        }
    }
}
