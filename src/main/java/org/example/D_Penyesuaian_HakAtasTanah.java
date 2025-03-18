package org.example;

public class D_Penyesuaian_HakAtasTanah {
    // Variabel public static untuk menyimpan jenis penyesuaian hasil
    public static String[] jenis_penyesuaian_hak = new String[A_Main.hak_atas_tanah.length];

    public static void main(String[] args) {
        // Loop untuk menentukan jenis penyesuaian berdasarkan hak atas tanah
        for (int i = 0; i < A_Main.hak_atas_tanah.length; i++) {
            jenis_penyesuaian_hak[i] = getJenisPenyesuaian(A_Main.hak_atas_tanah[i]);
        }

        // Print hasil penyesuaian
        for (int i = 0; i < jenis_penyesuaian_hak.length; i++) {
            System.out.println("Jenis penyesuaian hak atas tanah objek " + (i + 1) + ": " + jenis_penyesuaian_hak[i]);
        }
    }

    // Fungsi untuk menentukan jenis penyesuaian
    public static String getJenisPenyesuaian(String hakAtasTanah) {
        if (hakAtasTanah.equalsIgnoreCase("GIRIK") ||
                hakAtasTanah.equalsIgnoreCase("LETTER C") ||
                hakAtasTanah.equalsIgnoreCase("BELUM PUNYA HAK")) {
            return "6%";
        } else {
            return "0%";
        }
    }
}
