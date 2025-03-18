import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class dashboard extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello world");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("JavaFX Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}