package controller.pendapatan;

import controller.components.ProjectTableViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter; // <-- THE MISSING IMPORT
import model.Project;
import model.ProjectTemplate;
import service.NavigationService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PendekatanPendapatanHomeController implements Initializable {

    // --- FXML Fields ---
    @FXML private ToggleGroup modeToggleGroup;
    @FXML private RadioButton loadProjectRadio;
    @FXML private RadioButton newProjectRadio;
    @FXML private VBox loadProjectBox;
    @FXML private ComboBox<Project> projectSearchComboBox;
    @FXML private VBox newProjectBox;
    @FXML private ComboBox<ProjectTemplate> templateComboBox;
    @FXML private Button actionButton;
    @FXML private ProjectTableViewController projectTableController;

    // --- Data Lists ---
    private ObservableList<Project> incomeApproachProjects;
    private ObservableList<ProjectTemplate> allTemplates;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Project> allProjects = loadMockProjectData();
        Predicate<Project> incomeApproachFilter = project ->
                "Pendekatan Pendapatan".equalsIgnoreCase(project.getValuationApproach());
        projectTableController.setData(allProjects, incomeApproachFilter);
        setupTopControls(allProjects, incomeApproachFilter);
    }

    private void handleActionButtonClick() {
        if (loadProjectRadio.isSelected()) {
            Project selectedProject = projectSearchComboBox.getSelectionModel().getSelectedItem();
            if (selectedProject != null) {
                showAlert("Aksi", "Memuat proyek: " + selectedProject.getClientName());
            } else {
                showAlert("Peringatan", "Silakan pilih proyek yang akan dimuat.");
            }
        } else if (newProjectRadio.isSelected()) {
            ProjectTemplate selectedTemplate = templateComboBox.getValue();

            if (selectedTemplate == null) {
                showAlert("Peringatan", "Silakan pilih template proyek yang valid.");
                return;
            }

            if (selectedTemplate.isUnderDevelopment()) {
                showAlert("Info", "Fitur '" + selectedTemplate.getDisplayName() + "' sedang dalam pengembangan.");
            } else {
                switch (selectedTemplate.getIdentifier()) {
                    case "SIMPLIFIED_INCOME":
                        NavigationService.getInstance().navigate(
                                "/javaFX/Pendekatan_Pendapatan/Pendekatan_Pendapatan_Simplified/Pendekatan_Pendapatan_Simplified_Page1.fxml"
                        );
                        break;
                    case "ANOTHER_TEMPLATE":
                        showAlert("Aksi", "Membuka halaman untuk template contoh lainnya...");
                        break;
                    default:
                        showAlert("Error", "Template tidak dikenali.");
                        break;
                }
            }
        }
    }

    private void setupTopControls(List<Project> allProjects, Predicate<Project> filter) {
        modeToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == loadProjectRadio) {
                showLoadProjectUI();
            } else if (newToggle == newProjectRadio) {
                showNewProjectUI();
            }
        });
        setupSearchableProjectComboBox(allProjects, filter);
        setupTemplateComboBox();
        actionButton.setOnAction(event -> handleActionButtonClick());
        showNewProjectUI();
    }

    private void setupSearchableProjectComboBox(List<Project> allProjects, Predicate<Project> filter) {
        incomeApproachProjects = allProjects.stream()
                .filter(filter)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        FilteredList<Project> filteredItems = new FilteredList<>(incomeApproachProjects, p -> true);
        projectSearchComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            final String newText = newVal == null ? "" : newVal.toLowerCase();
            projectSearchComboBox.show();
            filteredItems.setPredicate(project -> {
                if (newText.isEmpty()) { return true; }
                return project.getClientName().toLowerCase().contains(newText) ||
                        project.getAssetType().toLowerCase().contains(newText);
            });
        });
        projectSearchComboBox.setItems(filteredItems);
        projectSearchComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); }
                else { setText(item.getId() + " - " + item.getClientName() + " (" + item.getAssetType() + ")"); }
            }
        });
        projectSearchComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(Project project) {
                if (project == null) { return null; }
                return project.getId() + " - " + project.getClientName();
            }
            @Override public Project fromString(String string) {
                if (string == null || string.isEmpty()) { return null; }
                return incomeApproachProjects.stream()
                        .filter(p -> (p.getId() + " - " + p.getClientName()).equals(string))
                        .findFirst().orElse(null);
            }
        });
    }

    private void setupTemplateComboBox() {
        allTemplates = FXCollections.observableArrayList(
                new ProjectTemplate("SIMPLIFIED_INCOME", "Pendekatan Pendapatan (Ruko, Hotel, Kos-kosan, ...) - Simplified", false),
                new ProjectTemplate("FULL_REPORT_INCOME", "Pendekatan Pendapatan (Laporan Laba Rugi Lengkap)", true),
                new ProjectTemplate("ANOTHER_TEMPLATE", "Template Contoh Lainnya", false)
        );

        FilteredList<ProjectTemplate> filteredTemplates = new FilteredList<>(allTemplates, p -> true);

        templateComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            final String newText = newVal == null ? "" : newVal.toLowerCase();
            templateComboBox.show();
            filteredTemplates.setPredicate(template -> {
                if (newText.isEmpty()) {
                    return true;
                }
                return template.getDisplayName().toLowerCase().contains(newText);
            });
        });

        templateComboBox.setItems(filteredTemplates);

        templateComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ProjectTemplate template) {
                return (template == null) ? null : template.getDisplayName();
            }

            @Override
            public ProjectTemplate fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                }
                return allTemplates.stream()
                        .filter(template -> template.getDisplayName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void showLoadProjectUI() {
        newProjectBox.setVisible(false);
        newProjectBox.setManaged(false);
        loadProjectBox.setVisible(true);
        loadProjectBox.setManaged(true);
        actionButton.setText("Load Selected Project");
    }

    private void showNewProjectUI() {
        loadProjectBox.setVisible(false);
        loadProjectBox.setManaged(false);
        newProjectBox.setVisible(true);
        newProjectBox.setManaged(true);
        actionButton.setText("Create New Project");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private List<Project> loadMockProjectData() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("P101", "Mall Grand City", "Valuasi Mall", "On Progress", LocalDate.now().plusMonths(3), 0.4, "Pendekatan Pendapatan"));
        projects.add(new Project("P102", "Hotel Bintang Lima", "Analisis Hotel", "Completed", LocalDate.now().minusMonths(1), 1.0, "Pendekatan Pendapatan"));
        projects.add(new Project("P001", "PT. Sejahtera", "Tanah & Bangunan Kantor", "Analisis Data", LocalDate.now().plusWeeks(2), 0.6, "Pendekatan Pasar"));
        projects.add(new Project("P103", "SPBU Pertamina", "Valuasi SPBU", "On Hold", LocalDate.now().plusMonths(2), 0.2, "Pendekatan Pendapatan"));
        projects.add(new Project("P002", "Bpk. Hartono", "Rumah Tinggal", "Inspeksi Lapangan", LocalDate.now().plusDays(5), 0.25, "Pendekatan Pasar"));
        projects.add(new Project("P104", "Ruko Sentra Niaga", "Sewa Ruko", "Review", LocalDate.now().plusDays(10), 0.9, "Pendekatan Pendapatan"));
        return projects;
    }
}