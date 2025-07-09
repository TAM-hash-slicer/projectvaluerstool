package controller.penilaian_tower;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PenilaianTowerHomeController implements Initializable {

    @FXML
    private ComboBox<String> towerTypeComboBox;

    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Mengisi ComboBox dengan pilihan jenis tower
        towerTypeComboBox.setItems(FXCollections.observableArrayList(
                "Lattice Tower",
                "Minipole Tower",
                "Guyed Mast Tower" // Menggunakan istilah yang lebih umum
        ));

        // Opsional: Memberi nilai default pada ComboBox
        towerTypeComboBox.getSelectionModel().selectFirst();

        // 2. Menetapkan aksi untuk tombol
        startButton.setOnAction(event -> handleStartValuation());
    }

    /**
     * Method ini akan dipanggil saat tombol "Mulai Penilaian" ditekan.
     */
    private void handleStartValuation() {
        String selectedTower = towerTypeComboBox.getSelectionModel().getSelectedItem();

        if (selectedTower == null || selectedTower.isEmpty()) {
            showAlert("Peringatan", "Silakan pilih jenis tower terlebih dahulu.");
            return;
        }

        // Untuk saat ini, kita hanya menampilkan informasi tower yang dipilih.
        // Nantinya, di sini Anda bisa memuat halaman FXML lain yang sesuai
        // dengan jenis tower yang dipilih.
        System.out.println("Memulai penilaian untuk: " + selectedTower);
        showAlert("Informasi", "Memulai proses penilaian untuk tower jenis: " + selectedTower);

        // Contoh langkah selanjutnya:
        // loadValuationPageFor(selectedTower);
    }

    /**
     * Helper untuk menampilkan dialog informasi sederhana.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}