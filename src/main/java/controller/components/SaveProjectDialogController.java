package controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class SaveProjectDialogController {

    public enum SaveAction {
        CANCEL,
        OVERWRITE,
        SAVE_AS_NEW
    }

    public record SaveResult(SaveAction action, String saveName) {}

    @FXML private Label objectNameLabel;
    @FXML private TextField saveNameField;
    @FXML private Button overwriteButton;
    @FXML private Button saveAsNewButton;
    @FXML private Button cancelButton;

    private SaveResult result;

    @FXML
    public void initialize() {
        // Default result is CANCEL
        result = new SaveResult(SaveAction.CANCEL, "");
        overwriteButton.setVisible(false); // Hide by default
        overwriteButton.setManaged(false);
    }

    /**
     * Initializes the dialog with data from the calling controller.
     * @param objectName The name of the object being valued.
     * @param existingSaveName The current save name, if it exists.
     * @param isResave True if this project has been saved before.
     */
    public void initData(String objectName, String existingSaveName, boolean isResave) {
        objectNameLabel.setText(objectName);
        saveNameField.setText(existingSaveName);

        if (isResave) {
            overwriteButton.setVisible(true);
            overwriteButton.setManaged(true);
            overwriteButton.setDefaultButton(true); // Make overwrite the default for existing saves
            saveAsNewButton.setDefaultButton(false);
        }
    }

    @FXML
    private void handleOverwrite() {
        result = new SaveResult(SaveAction.OVERWRITE, saveNameField.getText());
        closeDialog();
    }

    @FXML
    private void handleSaveAsNew() {
        result = new SaveResult(SaveAction.SAVE_AS_NEW, saveNameField.getText());
        closeDialog();
    }

    @FXML
    private void handleCancel() {
        // Result is already CANCEL by default
        closeDialog();
    }

    public SaveResult getResult() {
        return result;
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}