package controller.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.PendapatanData.PendekatanPendapatanData;
import service.SaveAndLoadService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class LoadProjectDialogController {

    // A private record to hold all necessary info for display and selection.
    private record SavedProjectInfo(String saveKey, String saveName, String objectName, long timestamp) {}

    private enum SortMode {
        NAME_ASC("Name (A-Z)"),
        NAME_DESC("Name (Z-A)"),
        NEWEST_FIRST("Most Recent"),
        OLDEST_FIRST("Oldest");

        private final String displayName;
        SortMode(String displayName) { this.displayName = displayName; }
        @Override public String toString() { return displayName; }
    }

    // --- FXML Injections ---
    @FXML private TextField searchField;
    @FXML private ComboBox<SortMode> sortComboBox;
    @FXML private TableView<SavedProjectInfo> projectsTableView;
    @FXML private TableColumn<SavedProjectInfo, String> saveNameColumn; // Renamed for clarity
    @FXML private TableColumn<SavedProjectInfo, String> objectNameColumn; // New column
    @FXML private TableColumn<SavedProjectInfo, String> lastSavedColumn;
    @FXML private Button loadButton;
    @FXML private Button cancelButton;
    @FXML private Button deleteButton;

    // --- Class Fields ---
    private final SaveAndLoadService saveAndLoadService = SaveAndLoadService.getInstance();
    private final ObservableList<SavedProjectInfo> masterData = FXCollections.observableArrayList();
    private Optional<String> selectedSaveKey = Optional.empty();

    // The SortedList needs to be a field to be accessible by the sort method.
    private SortedList<SavedProjectInfo> sortedData;

    @FXML
    public void initialize() {
        setupTable();
        setupFilteringAndSorting();
        setupActions();
    }

    public void initData(String requiredProjectType) {
        loadSavedProjects(requiredProjectType);
        // Set the default sort mode after data is loaded.
        sortComboBox.getSelectionModel().select(SortMode.NEWEST_FIRST);
    }

    private void setupTable() {
        saveNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().saveName()));
        objectNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().objectName()));
        lastSavedColumn.setCellValueFactory(cellData -> {
            long timestamp = cellData.getValue().timestamp();
            if (timestamp == 0) {
                return new SimpleStringProperty("N/A");
            }
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new SimpleStringProperty(formattedDate);
        });
    }

    private void loadSavedProjects(String requiredProjectType) {
        masterData.clear();
        saveAndLoadService.listSaveKeys().forEach(key ->
                saveAndLoadService.loadProgress(key, PendekatanPendapatanData.class).ifPresent(data -> {
                    boolean matchesFilter = (requiredProjectType == null || requiredProjectType.isBlank() || requiredProjectType.equals(data.getProjectTypeIdentifier()));
                    if (matchesFilter) {
                        String saveName = (data.getSaveName() != null && !data.getSaveName().isBlank()) ? data.getSaveName() : "Untitled Save";
                        String objectName = (data.getObjectName() != null && !data.getObjectName().isBlank()) ? data.getObjectName() : "Untitled Object";
                        masterData.add(new SavedProjectInfo(key, saveName, objectName, data.getLastSavedTimestamp()));
                    }
                })
        );
    }


    private void setupFilteringAndSorting() {
        FilteredList<SavedProjectInfo> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) ->
                filteredData.setPredicate(project -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String lowerCaseFilter = newVal.toLowerCase();
                    // --- MODIFIED: Search in both names ---
                    return project.saveName().toLowerCase().contains(lowerCaseFilter)
                            || project.objectName().toLowerCase().contains(lowerCaseFilter);
                })
        );

        // 2. Wrap the FilteredList in a SortedList for sorting.
        sortedData = new SortedList<>(filteredData);

        // 3. Set the TableView to display the items from the SortedList.
        projectsTableView.setItems(sortedData);

        // 4. Setup the ComboBox to control the sorting.
        sortComboBox.setItems(FXCollections.observableArrayList(SortMode.values()));
        sortComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applySort(newVal));
    }

    private void applySort(SortMode mode) {
        // This is still good practice to clear any visual indicators from column headers.
        projectsTableView.getSortOrder().clear();

        if (mode == null) {
            sortedData.setComparator(null);
            return;
        }

        Comparator<SavedProjectInfo> comparator = switch (mode) {
            // --- MODIFIED: Sort by saveName ---
            case NAME_ASC -> Comparator.comparing(SavedProjectInfo::saveName, String.CASE_INSENSITIVE_ORDER);
            case NAME_DESC -> Comparator.comparing(SavedProjectInfo::saveName, String.CASE_INSENSITIVE_ORDER).reversed();
            case OLDEST_FIRST -> Comparator.comparingLong(SavedProjectInfo::timestamp);
            case NEWEST_FIRST -> Comparator.comparingLong(SavedProjectInfo::timestamp).reversed();
        };

        // **THE FIX**: Set the comparator on the SortedList, not the TableView.
        sortedData.setComparator(comparator);
    }

    private void setupActions() {
        loadButton.disableProperty().bind(projectsTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(projectsTableView.getSelectionModel().selectedItemProperty().isNull());
        projectsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && projectsTableView.getSelectionModel().getSelectedItem() != null) {
                handleLoad();
            }
        });
    }

    @FXML
    private void handleDelete() {
        SavedProjectInfo selected = projectsTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return; // Should not happen if button is disabled, but good practice
        }

        // Create a confirmation dialog
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Project");
        confirmation.setHeaderText("Are you sure you want to permanently delete this save?");
        confirmation.setContentText(
                "Save Name: " + selected.saveName() + "\n" +
                        "Object: " + selected.objectName() + "\n\n" +
                        "This action cannot be undone."
        );

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, proceed with deletion
            boolean deleted = saveAndLoadService.deleteProgress(selected.saveKey());
            if (deleted) {
                // If deletion from disk is successful, remove it from our list
                masterData.remove(selected); // The TableView will update automatically
            } else {
                // Show an error if deletion fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Deletion Failed");
                errorAlert.setHeaderText("Could not delete the project file from the disk.");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    private void handleLoad() {
        SavedProjectInfo selected = projectsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            this.selectedSaveKey = Optional.of(selected.saveKey());
            closeDialog();
        }
    }

    @FXML
    private void handleCancel() {
        this.selectedSaveKey = Optional.empty();
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public Optional<String> getSelectedSaveKey() {
        return selectedSaveKey;
    }
}