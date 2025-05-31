package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable; // Optional: for an initialize method
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent; // For event handling

import java.net.URL; // For Initializable
import java.util.ResourceBundle; // For Initializable

public class dashboardMain implements Initializable { // Implementing Initializable is common but optional

    // Example @FXML fields - these names MUST match fx:id attributes in your FXML
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button myActionButton;

    // Constructor (optional)
    public dashboardMain() {
        // Constructor logic, if any.
        // Note: @FXML fields are not yet injected at this point.
        // Use initialize() for setup that depends on @FXML fields.
        System.out.println("DashboardMain Controller constructor called.");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded and @FXML fields have been injected.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This method is called after FXML fields are injected.
        // You can do setup work here.
        System.out.println("DashboardMain initialize method called.");
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome to the Dashboard!");
        } else {
            System.out.println("welcomeLabel is null in initialize. Check fx:id in FXML.");
        }

        if (myActionButton != null) {
            myActionButton.setText("Click Me!");
        } else {
            System.out.println("myActionButton is null in initialize. Check fx:id in FXML.");
        }
    }

    // Example event handler method for a button click
    // The method name `handleMyActionButtonClick` should match the onAction attribute in your FXML for the button.
    @FXML
    private void handleMyActionButtonClick(ActionEvent event) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Action button was clicked!");
        }
        System.out.println("My Action Button was clicked!");
        // Add any logic you want to perform when the button is clicked
    }

    // Add other methods that your dashboard might need
    public void performSomeAction() {
        System.out.println("Performing some action from DashboardMain controller.");
    }
}