package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class starter extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Path relative to the resources folder
        URL fxmlLocation = getClass().getResource("/javaFX/dashboard.fxml");
        if (fxmlLocation == null) {
            System.err.println("Cannot find FXML file: /javaFX/dashboard.fxml");
            System.err.println("Please ensure it's in: C:\\Project_Valuers_Tools\\src\\main\\resources\\javaFX\\dashboard.fxml");
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        Scene scene = new Scene(root, 1366, 768); // Adjust size as needed

        URL cssLocation = getClass().getResource("/javaFX/dashboard-styles.css");
        if (cssLocation != null) {
            scene.getStylesheets().add(cssLocation.toExternalForm());
        } else {
            System.err.println("Cannot find CSS file: /javaFX/dashboard-styles.css");
            System.err.println("Expected in: C:\\Project_Valuers_Tools\\src\\main\\resources\\javaFX\\dashboard-styles.css");
        }

        primaryStage.setTitle("Valuer Tools Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}