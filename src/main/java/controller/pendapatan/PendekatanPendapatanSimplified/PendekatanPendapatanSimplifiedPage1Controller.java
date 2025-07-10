package controller.pendapatan.PendekatanPendapatanSimplified;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PendapatanData.PendekatanPendapatanData;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the Simplified Income Approach form (Page 1).
 * Manages UI state, dynamic expense rows, real-time calculations,
 * and data binding with the PendekatanPendapatanData model.
 */
public class PendekatanPendapatanSimplifiedPage1Controller implements Initializable {

    //region FXML Injections for Static Fields
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
    //endregion

    //region FXML Injections for Dynamic Sections
    @FXML private VBox monthlyExpensesVBox;
    @FXML private Button addMonthlyExpenseButton;
    @FXML private Label totalMonthlyLabel;

    @FXML private VBox annualExpensesVBox;
    @FXML private Button addAnnualExpenseButton;
    @FXML private Label totalAnnualLabel;
    //endregion

    private PendekatanPendapatanData valuationData;
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.valuationData = new PendekatanPendapatanData();
        // Pre-populate the form with default expenses as a guide for the user
        addExpenseRow(monthlyExpensesVBox, "Listrik", "500000");
        addExpenseRow(monthlyExpensesVBox, "Air (PDAM)", "200000");
        addExpenseRow(annualExpensesVBox, "Pajak Bumi dan Bangunan (PBB)", "1500000");
        addExpenseRow(annualExpensesVBox, "Iuran Lingkungan", "600000");
    }

    /**
     * Event handler for the "+ Tambah Biaya Bulanan" button.
     */
    @FXML
    private void handleAddMonthlyExpense() {
        addExpenseRow(monthlyExpensesVBox, "", "");
    }

    /**
     * Event handler for the "+ Tambah Biaya Tahunan" button.
     */
    @FXML
    private void handleAddAnnualExpense() {
        addExpenseRow(annualExpensesVBox, "", "");
    }

    /**
     * Core logic to programmatically create and add a new expense row to a VBox.
     *
     * @param parentVBox The VBox to add the new row to (either monthly or annual).
     * @param name       The default name for the expense.
     * @param amount     The default amount for the expense.
     */
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

        // Action to remove the row
        removeButton.setOnAction(event -> {
            parentVBox.getChildren().remove(expenseRow);
            updateTotals();
        });

        // Listener for real-time calculation
        amountField.textProperty().addListener((obs, oldVal, newVal) -> updateTotals());

        expenseRow.getChildren().addAll(nameField, amountField, removeButton);
        parentVBox.getChildren().add(expenseRow);
        updateTotals(); // Update totals after adding a new row
    }

    /**
     * A single method to update both monthly and annual totals.
     */
    private void updateTotals() {
        BigDecimal monthlyTotal = calculateTotalFor(monthlyExpensesVBox);
        BigDecimal annualTotal = calculateTotalFor(annualExpensesVBox);

        totalMonthlyLabel.setText(currencyFormatter.format(monthlyTotal));
        totalAnnualLabel.setText(currencyFormatter.format(annualTotal));
    }

    /**
     * Generic calculation logic for a given VBox of expenses.
     *
     * @param expenseVBox The VBox containing expense rows.
     * @return The total sum of amounts as a BigDecimal.
     */
    private BigDecimal calculateTotalFor(VBox expenseVBox) {
        BigDecimal total = BigDecimal.ZERO;
        for (Node node : expenseVBox.getChildren()) {
            if (node instanceof HBox) {
                HBox row = (HBox) node;
                // Find the amount TextField (assuming it's the second-to-last element)
                Node amountNode = row.getChildren().get(1);
                if (amountNode instanceof TextField) {
                    TextField amountField = (TextField) amountNode;
                    try {
                        BigDecimal amount = new BigDecimal(amountField.getText().trim());
                        total = total.add(amount);
                    } catch (NumberFormatException e) {
                        // Ignore invalid numbers, or you could style the field red
                    }
                }
            }
        }
        return total;
    }

    /**
     * Gathers all data from the UI controls and populates the valuationData model instance.
     * This method should be called before saving or processing the valuation.
     *
     * @return The fully populated PendekatanPendapatanData object.
     */
    public PendekatanPendapatanData gatherData() {
        // Populate static fields
        valuationData.setObjectName(objectNameField.getText());
        valuationData.setAddress(addressField.getText());
        valuationData.setObjectType(objectTypeField.getText());
        try {
            valuationData.setLandArea(Double.parseDouble(landAreaField.getText()));
            valuationData.setBuildingArea(Double.parseDouble(buildingAreaField.getText()));
            valuationData.setBuiltSinceYear(Integer.parseInt(builtSinceYearField.getText()));
            valuationData.setRentalPrice(new BigDecimal(rentalPriceField.getText()));
        } catch (NumberFormatException e) {
            // Handle cases where number fields are empty or invalid
            System.err.println("Invalid number format in one of the fields: " + e.getMessage());
        }
        valuationData.setRentalTerm(rentalTermField.getText());
        valuationData.setCostBearer(costBearerField.getText());
        valuationData.setAdditionalNotes(additionalNotesArea.getText());

        // Populate dynamic monthly expenses
        gatherExpensesFrom(monthlyExpensesVBox, valuationData.getTenantExpenses().getMonthlyExpenses());

        // Populate dynamic annual expenses
        gatherExpensesFrom(annualExpensesVBox, valuationData.getOwnerExpenses().getAnnualExpenses());

        return valuationData;
    }

    /**
     * Helper method to iterate through an expense VBox and populate a list of ExpenseItems.
     *
     * @param expenseVBox The UI container for expense rows.
     * @param expenseList The model's list to populate.
     */
    private void gatherExpensesFrom(VBox expenseVBox, List<PendekatanPendapatanData.ExpenseItem> expenseList) {
        expenseList.clear(); // Clear previous data before gathering new data
        for (Node node : expenseVBox.getChildren()) {
            if (node instanceof HBox) {
                HBox row = (HBox) node;
                TextField nameField = (TextField) row.getChildren().get(0);
                TextField amountField = (TextField) row.getChildren().get(1);

                String name = nameField.getText();
                BigDecimal amount = BigDecimal.ZERO;
                try {
                    amount = new BigDecimal(amountField.getText());
                } catch (NumberFormatException e) {
                    // Default to zero if amount is invalid
                }

                if (!name.isBlank()) {
                    expenseList.add(new PendekatanPendapatanData.ExpenseItem(name, amount));
                }
            }
        }
    }
}