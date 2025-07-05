// Pastikan package declaration ini sesuai dengan lokasi file
package penilaian_tower;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Kode ini mencari file FXML Anda di dalam folder 'resources'
        // Pastikan path "/javaFX/dashboard.fxml" sudah benar
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/javaFX/dashboard.fxml"));

        // Mengatur ukuran jendela aplikasi (lebar 800px, tinggi 700px)
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);

        stage.setTitle("Alat Bantu Penilaian Menara");
        stage.setScene(scene);
        stage.show(); // Menampilkan jendela ke layar
    }

    public static void main(String[] args) {
        launch();
    }
}