package service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane; // Or another pane like StackPane, etc.

import java.io.IOException;
import java.util.Objects;

/**
 * A Singleton service to handle all page navigation within the application.
 * This centralizes navigation logic, decoupling controllers from each other.
 * Any controller can request a page change without needing a direct reference
 * to the main application container.
 */
public class NavigationService {

    // The single, static instance of the service (Singleton pattern)
    private static final NavigationService instance = new NavigationService();

    // The main content area where pages will be displayed.
    // Using a generic Pane would be more flexible, but BorderPane is common.
    private BorderPane mainContentPane;

    // Private constructor to prevent anyone else from creating an instance
    private NavigationService() {}

    /**
     * Gets the single, shared instance of the NavigationService.
     *
     * @return The singleton instance.
     */
    public static NavigationService getInstance() {
        return instance;
    }

    /**
     * Sets the main content pane that the service will use to display new pages.
     * This MUST be called once by the main application controller upon startup.
     *
     * @param mainContentPane The primary layout pane for page content.
     */
    public void setMainContentPane(BorderPane mainContentPane) {
        this.mainContentPane = mainContentPane;
    }

    /**
     * The primary navigation method. Loads the specified FXML file into the main content pane.
     *
     * @param fxmlPath The path to the FXML file to load (e.g., "/javaFX/some/Page.fxml").
     * @param <T> The type of the controller.
     * @return The controller instance of the loaded FXML, or null if loading fails.
     */
    public <T> T navigate(String fxmlPath) {
        if (mainContentPane == null) {
            System.err.println("Navigation Error: Main content pane has not been set in NavigationService.");
            return null;
        }

        try {
            // Use Objects.requireNonNull to provide a clearer error message if the path is wrong
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Node page = loader.load();
            mainContentPane.setCenter(page);
            return loader.getController(); // <-- KEY CHANGE: Return the controller
        } catch (IOException e) {
            System.err.println("Failed to load page: " + fxmlPath);
            e.printStackTrace();
            // Optionally, you could show an error dialog to the user here
        } catch (NullPointerException e) {
            System.err.println("Navigation Error: FXML file not found at path: " + fxmlPath);
            e.printStackTrace();
        }
        return null; // Return null on failure
    }
}