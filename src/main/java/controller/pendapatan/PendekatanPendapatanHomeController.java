package controller.pendapatan;

import controller.components.LoadProjectDialogController; // NEW IMPORT
import controller.components.ProjectTableViewController;
import controller.pendapatan.PendekatanPendapatanSimplified.PendekatanPendapatanSimplifiedPage1Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // NEW IMPORT
import javafx.fxml.Initializable;
import javafx.scene.Parent; // NEW IMPORT
import javafx.scene.Scene; // NEW IMPORT
import javafx.scene.control.*;
import javafx.stage.Modality; // NEW IMPORT
import javafx.stage.Stage; // NEW IMPORT
import javafx.util.StringConverter;
import model.Project;
import model.ProjectTemplate;
import service.NavigationService;

import java.io.IOException; // NEW IMPORT
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PendekatanPendapatanHomeController implements Initializable {

    // --- FXML Fields ---
    @FXML private ComboBox<ProjectTemplate> templateComboBox;
    @FXML private Button createProjectButton;
    @FXML private Button loadProjectButton;
    @FXML private ProjectTableViewController projectTableController;

    // --- Data ---
    private ObservableList<ProjectTemplate> allTemplates;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Project> allProjects = loadMockProjectData();
        Predicate<Project> incomeApproachFilter = project ->
                "Pendekatan Pendapatan".equalsIgnoreCase(project.getValuationApproach());
        projectTableController.setData(allProjects, incomeApproachFilter);

        setupTemplateComboBox();
    }

    @FXML
    private void handleCreateProject() {
        ProjectTemplate selectedTemplate = templateComboBox.getValue();
        if (selectedTemplate == null) {
            showAlert("Peringatan", "Silakan pilih template proyek yang valid.");
            return;
        }
        if (selectedTemplate.isUnderDevelopment()) {
            showAlert("Info", "Fitur '" + selectedTemplate.getDisplayName() + "' sedang dalam pengembangan.");
            return;
        }
        // For a new project, generate a new unique ID.
        String projectKey = "PROJ-" + System.currentTimeMillis();
        navigateToSimplifiedPage1(projectKey);
    }

    @FXML
    private void handleLoadProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javaFX/components/LoadProjectDialog.fxml"));
            Parent root = loader.load();
            LoadProjectDialogController dialogController = loader.getController();

            dialogController.initData(PendekatanPendapatanSimplifiedPage1Controller.PROJECT_TYPE_ID);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Load Project");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            // Set the owner window to make the dialog modal to the main app
            dialogStage.initOwner(loadProjectButton.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            // Show the dialog and wait for it to be closed
            dialogStage.showAndWait();

            // After the dialog is closed, get the result and navigate if a project was selected
            dialogController.getSelectedSaveKey().ifPresent(this::navigateToSimplifiedPage1);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open the load project dialog.");
        }
    }

    private void navigateToSimplifiedPage1(String projectKey) {
        PendekatanPendapatanSimplifiedPage1Controller controller = NavigationService.getInstance().navigate(
                "/javaFX/Pendekatan_Pendapatan/Pendekatan_Pendapatan_Simplified/Pendekatan_Pendapatan_Simplified_Page1.fxml"
        );

        if (controller != null) {
            controller.initData(projectKey);
        } else {
            showAlert("Error", "Gagal memuat halaman proyek.");
        }
    }

    // ... (setupTemplateComboBox, showAlert, loadMockProjectData are unchanged) ...
    private void setupTemplateComboBox() {
        allTemplates = FXCollections.observableArrayList(
                new ProjectTemplate("SIMPLIFIED_INCOME", "Pendekatan Pendapatan (Ruko, Hotel, Kos-kosan, ...) - Simplified", false),
                new ProjectTemplate("FULL_REPORT_INCOME", "Pendekatan Pendapatan (Laporan Laba Rugi Lengkap)", true),
                new ProjectTemplate("ANOTHER_TEMPLATE", "Template Contoh Lainnya", false)
        );
        templateComboBox.setItems(allTemplates);
        templateComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ProjectTemplate template) {
                return (template == null) ? null : template.getDisplayName();
            }
            @Override
            public ProjectTemplate fromString(String string) {
                return allTemplates.stream()
                        .filter(t -> t.getDisplayName().equals(string))
                        .findFirst().orElse(null);
            }
        });
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
        return projects;
    }
}