// In controller/components/ProjectTableViewController.java

package controller.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Project;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectTableViewController {

    // --- FXML Fields ---
    @FXML private TableView<Project> projectTableView;
    @FXML private TableColumn<Project, String> idColumn;
    @FXML private TableColumn<Project, String> clientNameColumn;
    @FXML private TableColumn<Project, String> propertyDescColumn;
    @FXML private TableColumn<Project, String> approachColumn;
    @FXML private TableColumn<Project, String> statusColumn;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilterComboBox;
    @FXML private ComboBox<Integer> rowsPerPageComboBox;
    @FXML private Pagination pagination;

    // --- Data Handling ---
    private final ObservableList<Project> masterData = FXCollections.observableArrayList();
    private FilteredList<Project> filteredData;
    private Predicate<Project> basePredicate = p -> true; // **NEW**: Stores the initial filter

    // --- Constants ---
    private static final int DEFAULT_ROWS_PER_PAGE = 15;
    private final List<Integer> rowsPerPageOptions = List.of(10, 15, 25, 50);

    @FXML
    public void initialize() {
        setupTableColumns();
        setupRowsPerPageComboBox();
        setupFiltering();
        setupPagination();
    }

    public void setData(List<Project> projects, Predicate<Project> initialPredicate) {
        masterData.setAll(projects);

        // **UPDATED**: Store the initial predicate if it exists
        if (initialPredicate != null) {
            this.basePredicate = initialPredicate;
        }

        // Populate status filter ComboBox
        List<String> uniqueStatuses = masterData.stream()
                .map(Project::getStatus)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        statusFilterComboBox.getItems().add("Semua Status");
        statusFilterComboBox.getItems().addAll(uniqueStatuses);
        statusFilterComboBox.getSelectionModel().selectFirst();

        // Apply the initial filter right away
        filteredData.setPredicate(this.basePredicate);
        updatePagination(); // Refresh view after setting initial data and filter
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        propertyDescColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        approachColumn.setCellValueFactory(new PropertyValueFactory<>("valuationApproach"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setupRowsPerPageComboBox() {
        rowsPerPageComboBox.setItems(FXCollections.observableArrayList(rowsPerPageOptions));
        rowsPerPageComboBox.setValue(DEFAULT_ROWS_PER_PAGE);
        rowsPerPageComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updatePagination());
    }

    private void setupFiltering() {
        filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        statusFilterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        SortedList<Project> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(projectTableView.comparatorProperty());

        // The pagination factory will handle setting items on the table view
    }

    // **FULLY REWRITTEN AND CORRECTED METHOD**
    private void applyFilters() {
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
        String statusFilter = statusFilterComboBox.getValue();

        Predicate<Project> searchPredicate = project -> {
            if (searchText.isEmpty()) {
                return true;
            }
            return project.getClientName().toLowerCase().contains(searchText) ||
                    project.getAssetType().toLowerCase().contains(searchText);
        };

        Predicate<Project> statusPredicate = project -> {
            if (statusFilter == null || statusFilter.equals("Semua Status")) {
                return true;
            }
            return project.getStatus().equalsIgnoreCase(statusFilter);
        };

        // The crucial change: Always combine with the basePredicate
        filteredData.setPredicate(basePredicate.and(searchPredicate).and(statusPredicate));
        updatePagination();
    }

    private void setupPagination() {
        // Initial page count will be calculated in updatePagination()
        pagination.setPageFactory(this::createPage);
    }

    private void updatePagination() {
        int pageCount = calculatePageCount();
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(Math.min(pagination.getCurrentPageIndex(), pageCount - 1));

        // This call is important to refresh the view when filters change
        createPage(pagination.getCurrentPageIndex());
    }

    private int calculatePageCount() {
        if (filteredData.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) filteredData.size() / rowsPerPageComboBox.getValue());
    }

    private Node createPage(int pageIndex) {
        int rowsPerPage = rowsPerPageComboBox.getValue();
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, filteredData.size());

        if (fromIndex >= filteredData.size()) {
            projectTableView.setItems(FXCollections.observableArrayList());
        } else {
            // Use the already sorted and filtered list
            projectTableView.setItems(FXCollections.observableArrayList(filteredData.subList(fromIndex, toIndex)));
        }
        return projectTableView;
    }
}