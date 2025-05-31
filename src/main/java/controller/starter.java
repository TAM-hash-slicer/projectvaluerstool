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
    public void start(Stage primaryStage) {
        try {
            // Path to your FXML file.
            // Make sure this path is correct. If dashboard.fxml is in src/main/resources/javaFX/,
            // then "/javaFX/dashboard.fxml" should work.
            URL fxmlLocation = getClass().getResource("/javaFX/dashboard.fxml");
            if (fxmlLocation == null) {
                System.err.println("Cannot find FXML file. Check the path.");
                // You might want to throw an exception or show an alert here
                return;
            }

            Parent root = FXMLLoader.load(fxmlLocation);

            Scene scene = new Scene(root);

            primaryStage.setTitle("My Dashboard");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML or setting up the stage:");
            e.printStackTrace();
            // Handle the exception, maybe show an error dialog to the user
        } catch (Exception e) {
            System.err.println("An unexpected error occurred:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}