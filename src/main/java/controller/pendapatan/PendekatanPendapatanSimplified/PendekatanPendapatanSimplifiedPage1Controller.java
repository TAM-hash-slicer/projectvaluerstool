package controller.pendapatan.PendekatanPendapatanSimplified;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.PendapatanData.PendekatanPendapatanData;
import service.SaveAndLoadService;
import service.NavigationService;
import controller.components.SaveProjectDialogController;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller for the Simplified Income Approach form (Page 1).
 * Manages UI state, dynamic expense rows, real-time calculations,
 * and save/load functionality.
 */
public class PendekatanPendapatanSimplifiedPage1Controller {

    //region FXML Injections
    @FXML private TextField objectNameField;
    @FXML private TextField addressField;
    @FXML private TextField objectTypeField;
    @FXML private TextField landAreaField;
    @FXML private TextField buildingAreaField;
    @FXML private TextField builtSinceYearField;
    @FXML private TextField rentalPriceField;
    @FXML private TextField rentalTermField;
    @FXML private TextField costBearerField;
    @FXML private TextArea additionalNotesArea;
    @FXML private VBox monthlyExpensesVBox;
    @FXML private Button addMonthlyExpenseButton;
    @FXML private Label totalMonthlyLabel;
    @FXML private VBox annualExpensesVBox;
    @FXML private Button addAnnualExpenseButton;
    @FXML private Label totalAnnualLabel;
    @FXML private Button saveButton;
    @FXML private Button loadButton;
    //endregion

    private String projectSaveKey; // To store the unique ID for this project
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private final SaveAndLoadService saveAndLoadService = SaveAndLoadService.getInstance();
    private final NavigationService navigationService = NavigationService.getInstance();
    public static final String PROJECT_TYPE_ID = "INCOME_SIMPLIFIED";
    private boolean isLoadedFromSave = false;
    /**
     * Initializes the controller with data. This is called by the HomeController after navigation.
     * It attempts to load existing data for the project or sets up a new, default form.
     * @param projectSaveKey The unique identifier for the project file.
     */
    public void initData(String projectSaveKey) {
        this.projectSaveKey = projectSaveKey;
        System.out.println("Initializing page for project key: " + this.projectSaveKey);

        // Try to load existing progress automatically when the page opens
        Optional<PendekatanPendapatanData> loadedData = saveAndLoadService.loadProgress(this.projectSaveKey, PendekatanPendapatanData.class);

        if (loadedData.isPresent()) {
            // --- MODIFIED ---
            System.out.println("Saved data found. Populating UI.");
            populateUI(loadedData.get());
            isLoadedFromSave = true; // Mark that we've loaded data
        } else {
            // If no data, set up a fresh model and populate with default starter rows
            System.out.println("No saved data found. Setting up new form.");
            addExpenseRow(monthlyExpensesVBox, "Listrik", "500000");
            addExpenseRow(monthlyExpensesVBox, "Air (PDAM)", "200000");
            addExpenseRow(annualExpensesVBox, "Pajak Bumi dan Bangunan (PBB)", "1500000");
            addExpenseRow(annualExpensesVBox, "Iuran Lingkungan", "600000");
            isLoadedFromSave = false; // This is a new project
        }
    }

    /**
     * Event handler for the "Save Progress" button.
     */
    @FXML
    private void handleSaveProgress() {
        // 1. First, ensure the object has a name.
        if (objectNameField.getText() == null || objectNameField.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Object Name Required", "Please provide an 'Object Name' before saving.");
            return;
        }

        // 2. Gather current data to get existing save name
        PendekatanPendapatanData currentData = gatherDataFromUI();

        try {
            // 3. Launch the new save dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javaFX/components/SaveProjectDialog.fxml"));
            Parent root = loader.load();
            SaveProjectDialogController dialogController = loader.getController();

            // Pass current data to the dialog
            dialogController.initData(currentData.getObjectName(), currentData.getSaveName(), isLoadedFromSave);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Save Project");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(saveButton.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // 4. Process the result from the dialog
            SaveProjectDialogController.SaveResult result = dialogController.getResult();
            if (result.saveName() == null || result.saveName().isBlank()) {
                if (result.action() != SaveProjectDialogController.SaveAction.CANCEL) {
                    showAlert(Alert.AlertType.WARNING, "Save Canceled", "A name for the save file is required.");
                }
                return; // Abort if canceled or name is empty
            }

            currentData.setSaveName(result.saveName());

            switch (result.action()) {
                case OVERWRITE:
                    saveAndLoadService.saveProgress(currentData, this.projectSaveKey);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Project overwritten successfully!");
                    isLoadedFromSave = true; // It's now considered a saved project
                    break;

                case SAVE_AS_NEW:
                    // Generate a new key and update our controller's state
                    this.projectSaveKey = "PROJ-" + System.currentTimeMillis();
                    saveAndLoadService.saveProgress(currentData, this.projectSaveKey);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Project saved as a new file!");
                    isLoadedFromSave = true; // It's now considered a saved project
                    break;

                case CANCEL:
                    // Do nothing
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open the save project dialog.");
        }
    }

    /**
     * Event handler for the "Load Progress" button.
     */
    @FXML
    private void handleLoadProgress() {
        if (projectSaveKey == null || projectSaveKey.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot load: Project key is not set.");
            return;
        }
        // Optional: Add a confirmation dialog here since this will discard current changes.
        saveAndLoadService.loadProgress(projectSaveKey, PendekatanPendapatanData.class)
                .ifPresentOrElse(
                        data -> {
                            populateUI(data);
                            isLoadedFromSave = true; // Mark as loaded
                        },
                        () -> showAlert(Alert.AlertType.INFORMATION, "Not Found", "No saved data found for this project.")
                );
    }

    @FXML
    private void handleGoBack() {
        // In a more advanced implementation, you could check for unsaved changes here
        // and show a confirmation dialog before navigating away.
        navigationService.navigate("/javaFX/Pendekatan_Pendapatan/Pendekatan_Pendapatan_Home.fxml");
    }

    /**
     * Populates the entire UI form from a PendekatanPendapatanData object.
     * @param data The data object to load into the UI.
     */
    private void populateUI(PendekatanPendapatanData data) {
        // Populate static fields
        objectNameField.setText(data.getObjectName());
        addressField.setText(data.getAddress());
        objectTypeField.setText(data.getObjectType());
        landAreaField.setText(data.getLandArea() == 0 ? "" : String.valueOf(data.getLandArea()));
        buildingAreaField.setText(data.getBuildingArea() == 0 ? "" : String.valueOf(data.getBuildingArea()));
        builtSinceYearField.setText(data.getBuiltSinceYear() == 0 ? "" : String.valueOf(data.getBuiltSinceYear()));
        rentalPriceField.setText(data.getRentalPrice() == null ? "" : data.getRentalPrice().toPlainString());
        rentalTermField.setText(data.getRentalTerm());
        costBearerField.setText(data.getCostBearer());
        additionalNotesArea.setText(data.getAdditionalNotes());

        // --- REFINED SECTION ---
        // Populate dynamic monthly expenses
        monthlyExpensesVBox.getChildren().clear();
        List<PendekatanPendapatanData.ExpenseItem> monthlyItems = data.getTenantExpenses().getMonthlyExpenses();
        if (monthlyItems != null) { // The list itself could be null if the JSON is malformed
            for (PendekatanPendapatanData.ExpenseItem item : monthlyItems) {
                String amountStr = (item.getAmount() != null) ? item.getAmount().toPlainString() : "0";
                addExpenseRow(monthlyExpensesVBox, item.getName(), amountStr);
            }
        }

        // Populate dynamic annual expenses
        annualExpensesVBox.getChildren().clear();
        List<PendekatanPendapatanData.ExpenseItem> annualItems = data.getOwnerExpenses().getAnnualExpenses();
        if (annualItems != null) {
            for (PendekatanPendapatanData.ExpenseItem item : annualItems) {
                String amountStr = (item.getAmount() != null) ? item.getAmount().toPlainString() : "0";
                addExpenseRow(annualExpensesVBox, item.getName(), amountStr);
            }
        }
        // --- END REFINED SECTION ---

        // Ensure totals are correct after populating
        updateTotals();
    }

    /**
     * Gathers all data from the UI controls and populates a new data model instance.
     * @return The fully populated PendekatanPendapatanData object from the UI.
     */
    private PendekatanPendapatanData gatherDataFromUI() {
        PendekatanPendapatanData data = new PendekatanPendapatanData();

        // Populate static fields
        data.setObjectName(objectNameField.getText());
        data.setProjectTypeIdentifier(PROJECT_TYPE_ID);
        data.setAddress(addressField.getText());
        data.setLastSavedTimestamp(System.currentTimeMillis());
        data.setObjectType(objectTypeField.getText());
        data.setLandArea(parseDouble(landAreaField.getText()));
        data.setBuildingArea(parseDouble(buildingAreaField.getText()));
        data.setBuiltSinceYear(parseInt(builtSinceYearField.getText()));
        data.setRentalPrice(parseBigDecimal(rentalPriceField.getText()));
        data.setRentalTerm(rentalTermField.getText());
        data.setCostBearer(costBearerField.getText());
        data.setAdditionalNotes(additionalNotesArea.getText());

        // Populate dynamic expenses
        gatherExpensesFrom(monthlyExpensesVBox, data.getTenantExpenses().getMonthlyExpenses());
        gatherExpensesFrom(annualExpensesVBox, data.getOwnerExpenses().getAnnualExpenses());

        return data;
    }

    //region UI Helper Methods (Add/Remove Rows, Calculate Totals)

    @FXML
    private void handleAddMonthlyExpense() {
        addExpenseRow(monthlyExpensesVBox, "", "");
    }

    @FXML
    private void handleAddAnnualExpense() {
        addExpenseRow(annualExpensesVBox, "", "");
    }

    private void addExpenseRow(VBox parentVBox, String name, String amount) {
        HBox expenseRow = new HBox(10);
        expenseRow.setAlignment(Pos.CENTER_LEFT);

        TextField nameField = new TextField(name);
        nameField.setPromptText("Nama Biaya");
        HBox.setHgrow(nameField, javafx.scene.layout.Priority.ALWAYS);

        TextField amountField = new TextField(amount);
        amountField.setPromptText("Jumlah");
        amountField.setPrefWidth(150);

        Button removeButton = new Button("X");
        removeButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");

        removeButton.setOnAction(event -> {
            parentVBox.getChildren().remove(expenseRow);
            updateTotals();
        });

        // Add listener to update totals whenever the amount changes
        amountField.textProperty().addListener((obs, oldVal, newVal) -> updateTotals());

        expenseRow.getChildren().addAll(nameField, amountField, removeButton);
        parentVBox.getChildren().add(expenseRow);
    }

    private void updateTotals() {
        BigDecimal monthlyTotal = calculateTotalFor(monthlyExpensesVBox);
        BigDecimal annualTotal = calculateTotalFor(annualExpensesVBox);

        totalMonthlyLabel.setText(currencyFormatter.format(monthlyTotal));
        totalAnnualLabel.setText(currencyFormatter.format(annualTotal));
    }

    private BigDecimal calculateTotalFor(VBox expenseVBox) {
        return expenseVBox.getChildren().stream()
                .filter(HBox.class::isInstance)
                .map(node -> (HBox) node)
                .map(row -> (TextField) row.getChildren().get(1))
                .map(amountField -> parseBigDecimal(amountField.getText()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void gatherExpensesFrom(VBox expenseVBox, List<PendekatanPendapatanData.ExpenseItem> expenseList) {
        expenseList.clear();
        for (Node node : expenseVBox.getChildren()) {
            if (node instanceof HBox) {
                HBox row = (HBox) node;
                TextField nameField = (TextField) row.getChildren().get(0);
                TextField amountField = (TextField) row.getChildren().get(1);

                String name = nameField.getText();
                if (name != null && !name.isBlank()) {
                    BigDecimal amount = parseBigDecimal(amountField.getText());
                    expenseList.add(new PendekatanPendapatanData.ExpenseItem(name, amount));
                }
            }
        }
    }

    //endregion

    //region Safe Parsing and Alert Helpers

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return BigDecimal.ZERO;
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //endregion
}