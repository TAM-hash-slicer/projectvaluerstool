package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Mengelola koneksi ke database SQLite untuk aplikasi.
 * Kelas ini hanya bertanggung jawab untuk membuat dan menyediakan koneksi.
 */
public class DatabaseService {

    // String koneksi untuk SQLite. Ini akan mencari file 'valuers_tools.db'
    // di folder root proyek Anda saat development.
    private static final String JDBC_URL = "jdbc:sqlite:valuers_tools.db";

    /**
     * Metode publik untuk membuat dan mendapatkan koneksi ke database.
     * Metode ini bisa dipanggil dari kelas lain untuk mendapatkan koneksi.
     *
     * @return Objek Connection jika berhasil, atau null jika gagal.
     */
    public Connection connect() {
        Connection conn = null;
        try {
            // Baris ini adalah inti dari koneksi database
            conn = DriverManager.getConnection(JDBC_URL);
            System.out.println("Koneksi ke SQLite berhasil dibuat.");
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal: " + e.getMessage());
            // Di aplikasi nyata, Anda mungkin ingin menampilkan dialog error kepada pengguna
        }
        return conn;
    }

    // Anda bisa menambahkan metode lain untuk mengambil data (SELECT),
    // menyimpan data (INSERT/UPDATE), dll. di sini nanti.
}
