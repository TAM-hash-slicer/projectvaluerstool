package controller.pendapatan;

import controller.components.ProjectTableViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Project;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PendekatanPendapatanHomeController implements Initializable {

    // This fx:id MUST match the fx:id in the <fx:include> tag.
    // The name is special: it must be the fx:id plus "Controller".
    @FXML
    private ProjectTableViewController projectTableController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Load your project data (from a database, file, etc.)
        List<Project> allProjects = loadMockProjectData();

        // 2. Define the specific filter for this page
        Predicate<Project> incomeApproachFilter = project ->
                "Pendekatan Pendapatan".equalsIgnoreCase(project.getValuationApproach());

        // 3. Pass the data and the filter to the table component
        projectTableController.setData(allProjects, incomeApproachFilter);
    }

    // Example data loading method. Replace with your actual data source.
    private List<Project> loadMockProjectData() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("P101", "Mall Grand City", "Valuasi Mall", "On Progress", LocalDate.now().plusMonths(3), 0.4, "Pendekatan Pendapatan"));
        projects.add(new Project("P102", "Hotel Bintang Lima", "Analisis Hotel", "Completed", LocalDate.now().minusMonths(1), 1.0, "Pendekatan Pendapatan"));
        projects.add(new Project("P001", "PT. Sejahtera", "Tanah & Bangunan Kantor", "Analisis Data", LocalDate.now().plusWeeks(2), 0.6, "Pendekatan Pasar"));
        projects.add(new Project("P103", "SPBU Pertamina", "Valuasi SPBU", "On Hold", LocalDate.now().plusMonths(2), 0.2, "Pendekatan Pendapatan"));
        projects.add(new Project("P002", "Bpk. Hartono", "Rumah Tinggal", "Inspeksi Lapangan", LocalDate.now().plusDays(5), 0.25, "Pendekatan Pasar"));
        return projects;
    }
}