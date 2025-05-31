package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Penting untuk memuat FXML baru
import javafx.fxml.Initializable;
import javafx.scene.Parent;   // Parent node dari FXML yang dimuat
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory; // For TableView if used
import javafx.scene.layout.BorderPane; // Jika root layout Anda adalah BorderPane
// import javafx.scene.layout.AnchorPane; // Tidak digunakan secara langsung di sini untuk penggantian konten
import model.ComparableData;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
// import java.util.Arrays; // Tidak digunakan
// import java.util.List; // Tidak digunakan secara langsung
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class dashboardMain implements Initializable {

    // --- Header ---
    @FXML private Label userNameLabel;
    @FXML private TextField searchTextField;
    @FXML private Button notificationsButton;

    // --- Left Sidebar (Navigation - actions are placeholders) ---
    @FXML private Button navDashboardButton;
    @FXML private Button navAllProjectsButton;
    // ... other navigation buttons as defined in FXML

    // --- Center Area ---
    @FXML private BarChart<String, Number> taskHistogramChart;
    @FXML private CategoryAxis histogramCategoryAxis;
    @FXML private NumberAxis histogramNumberAxis;

    @FXML private Label comparableDataCountLabel;
    @FXML private Label completedProjectsLabel;
    @FXML private Label ikuPercentageLabel;

    @FXML private ListView<Project> activeProjectsListView;
    @FXML private ListView<Task> priorityTasksListView;

    // Quick Actions
    @FXML private Button quickActionNewProjectButton;
    @FXML private Button quickActionSearchComparableButton;
    // @FXML private Button quickActionCreateReportButton; // Pastikan ini ada di FXML jika digunakan

    // --- Right Sidebar (Tools - actions are placeholders) ---
    @FXML private Button marketApproachButton;
    @FXML private Button costApproachButton;
    @FXML private Button incomeApproachButton;
    @FXML private Button listComparableDataButton;
    @FXML private Button taskArchiveButton;
    @FXML private Button inputComparableDataButton;

    // --- Data Store (Mock data for now) ---
    private ObservableList<Project> allProjects;
    private ObservableList<Task> allTasks;
    private ObservableList<ComparableData> allComparableData;

    // --- Root Layout for Page Swapping ---
    // Pastikan fx:id="mainLayout" ada di BorderPane root di dashboard.fxml
    @FXML
    private BorderPane mainLayout;
    private Node originalDashboardCenterContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mainLayout != null) {
            this.originalDashboardCenterContent = mainLayout.getCenter();
            // ... (logika error handling jika null) ...
        }
        loadMockData();
        userNameLabel.setText("Budi Penilai");
        notificationsButton.setText("Notifikasi (3)");
        setupTaskHistogram();
        updateKpiBox();
        populateActiveProjectsList();
        populatePriorityTasksList();
        setupActionHandlers();
    }

    private void loadMockData() {
        Random random = new Random();
        allProjects = FXCollections.observableArrayList();
        allProjects.add(new Project("P001", "PT. Sejahtera", "Tanah & Bangunan Kantor", "Analisis Data", LocalDate.now().plusWeeks(2), 0.6));
        allProjects.add(new Project("P002", "Bpk. Hartono", "Rumah Tinggal", "Inspeksi Lapangan", LocalDate.now().plusDays(5), 0.25));
        allProjects.add(new Project("P003", "CV. Maju Jaya", "Gudang", "Penyusunan Draf", LocalDate.now().plusWeeks(1), 0.8));
        allProjects.add(new Project("P004", "Ibu Ratna", "Apartemen", "Review Internal", LocalDate.now().plusDays(3), 0.9));
        allProjects.add(new Project("P005", "Yayasan Bakti", "Sekolah", "Analisis Data", LocalDate.now().plusWeeks(3), 0.5));

        allTasks = FXCollections.observableArrayList();
        allTasks.add(new Task("T001", "Finalisasi Laporan P003", "P003", Task.Priority.TINGGI, LocalDate.now().plusDays(2), Task.Status.ON_PROGRESS));
        allTasks.add(new Task("T002", "Kumpulkan Data Pembanding P001", "P001", Task.Priority.TINGGI, LocalDate.now().plusDays(4), Task.Status.BARU));
        allTasks.add(new Task("T003", "Jadwalkan Inspeksi P002", "P002", Task.Priority.SEDANG, LocalDate.now().plusDays(1), Task.Status.JATUH_TEMPO));
        allTasks.add(new Task("T004", "Review data P004", "P004", Task.Priority.SEDANG, LocalDate.now().plusDays(1), Task.Status.TARGET));
        allTasks.add(new Task("T005", "Input data inspeksi P002", "P002", Task.Priority.RENDAH, LocalDate.now().plusDays(7), Task.Status.BARU));
        allTasks.add(new Task("T006", "Analisis awal P005", "P005", Task.Priority.SEDANG, LocalDate.now().plusWeeks(1), Task.Status.ON_PROGRESS));
        allTasks.add(new Task("T007", "Cek kelengkapan dokumen P001", "P001", Task.Priority.TINGGI, LocalDate.now().plusDays(3), Task.Status.JATUH_TEMPO));
        allTasks.add(new Task("T008", "Buat draf awal laporan P005", "P005", Task.Priority.SEDANG, LocalDate.now().plusWeeks(2), Task.Status.BARU));
        allTasks.add(new Task("T009", "Koordinasi dengan klien P003", "P003", Task.Priority.RENDAH, LocalDate.now().plusDays(5), Task.Status.ON_PROGRESS));
        allTasks.add(new Task("T010", "Verifikasi data pembanding P004", "P004", Task.Priority.TINGGI, LocalDate.now().plusDays(2), Task.Status.TARGET));

        allComparableData = FXCollections.observableArrayList();
        for (int i = 0; i < 150 + random.nextInt(100) ; i++) {
            allComparableData.add(new ComparableData("CD"+i, "Jl. Contoh No. "+i, 500000000 + random.nextInt(1000000000), LocalDate.now().minusMonths(random.nextInt(12)), "Rumah"));
        }
    }

    private void setupTaskHistogram() {
        taskHistogramChart.setAnimated(false);
        Map<Task.Status, Long> taskCountsByStatus = allTasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Jumlah Tugas");

        for (Task.Status status : Task.Status.values()) {
            if (status == Task.Status.SELESAI) continue;
            long count = taskCountsByStatus.getOrDefault(status, 0L);
            series.getData().add(new XYChart.Data<>(status.getDisplayName(), count));
        }

        taskHistogramChart.getData().clear(); // Clear previous data if any
        taskHistogramChart.getData().add(series);
        histogramCategoryAxis.setLabel("Status Tugas");
        histogramNumberAxis.setLabel("Jumlah");
        taskHistogramChart.setLegendVisible(false);
    }

    private void updateKpiBox() {
        comparableDataCountLabel.setText(String.valueOf(allComparableData.size()));
        long completedCount = allProjects.stream().filter(p -> p.getStatus().toLowerCase().contains("final") || p.getStatus().toLowerCase().contains("selesai")).count();
        completedProjectsLabel.setText(String.valueOf(completedCount + 25));
        ikuPercentageLabel.setText("92.5%");
    }

    private void populateActiveProjectsList() {
        ObservableList<Project> active = allProjects.stream()
                .filter(p -> !p.getStatus().toLowerCase().contains("final") && !p.getStatus().toLowerCase().contains("selesai"))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        activeProjectsListView.setItems(active);
        activeProjectsListView.setCellFactory(lv -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(String.format("%s - %s (Due: %s, Status: %s)",
                            item.getId(), item.getClientName(), item.getDueDate().toString(), item.getStatus()));
                }
            }
        });
    }

    private void populatePriorityTasksList() {
        ObservableList<Task> priority = allTasks.stream()
                .filter(t -> t.getPriority() == Task.Priority.TINGGI && t.getStatus() != Task.Status.SELESAI)
                .sorted((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        priorityTasksListView.setItems(priority);
    }

    private void setupActionHandlers() {
        navDashboardButton.setOnAction(event -> {
            System.out.println("Nav: Dashboard clicked - Returning to main dashboard view.");
            if (mainLayout != null && originalDashboardCenterContent != null) {
                mainLayout.setCenter(originalDashboardCenterContent); // Mengembalikan konten asli
                refreshDashboardDisplay(); // Memperbarui data di dashboard
            } else {
                // ... (logika error handling) ...
            }
        });
        navAllProjectsButton.setOnAction(e -> {
            System.out.println("Nav: Semua Proyek clicked");
            // loadPage("/javaFX/AllProjectsView.fxml"); // Example
            showAlert("Info", "Halaman Semua Proyek belum diimplementasikan.");
        });

        quickActionNewProjectButton.setOnAction(e -> System.out.println("Quick Action: Proyek Baru clicked"));
        quickActionSearchComparableButton.setOnAction(e -> System.out.println("Quick Action: Cari Data Pembanding clicked"));

        marketApproachButton.setOnAction(event -> {
            System.out.println("Tool: Pendekatan Pasar clicked - Attempting to load view...");
            loadPage("/javaFX/pendekatanPasar.fxml"); // UPDATED FILENAME
        });
        costApproachButton.setOnAction(e -> {
            System.out.println("Tool: Pendekatan Biaya clicked");
            // loadPage("/javaFX/CostApproachView.fxml");
            showAlert("Info", "Halaman Pendekatan Biaya belum diimplementasikan.");
        });
        incomeApproachButton.setOnAction(e -> {
            System.out.println("Tool: Pendekatan Pendapatan clicked");
            // loadPage("/javaFX/IncomeApproachView.fxml");
            showAlert("Info", "Halaman Pendekatan Pendapatan belum diimplementasikan.");
        });

        searchTextField.setOnAction(e -> System.out.println("Search initiated: " + searchTextField.getText()));
        notificationsButton.setOnAction(e -> System.out.println("Notifications clicked"));

        activeProjectsListView.setOnMouseClicked(event -> {
            Project selectedProject = activeProjectsListView.getSelectionModel().getSelectedItem();
            if (selectedProject != null) {
                System.out.println("Selected Project: " + selectedProject.getId());
            }
        });
        priorityTasksListView.setOnMouseClicked(event -> {
            Task selectedTask = priorityTasksListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Selected Task: " + selectedTask.getDescription());
            }
        });
    }

    /**
     * Metode helper untuk memuat halaman FXML ke bagian tengah BorderPane utama.
     * @param fxmlPath Path ke file FXML (relatif terhadap folder resources, e.g., "/javaFX/MyView.fxml").
     */
    private void loadPage(String fxmlPath) {
        try {
            // Pastikan path diawali dengan '/' jika absolut dari classpath root (resources)
            if (fxmlPath == null || fxmlPath.trim().isEmpty()) {
                System.err.println("Error: FXML path is null or empty.");
                showAlert("Error Kritis", "Path FXML tidak valid.");
                return;
            }
            if (!fxmlPath.startsWith("/")) {
                fxmlPath = "/" + fxmlPath;
            }

            System.out.println("Loading FXML from: " + fxmlPath);
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                System.err.println("Cannot load FXML file: " + fxmlPath + ". URL is null. Check path and if file exists in resources.");
                showAlert("Error", "Tidak dapat memuat halaman: " + fxmlPath + ". File tidak ditemukan di resources.");
                return;
            }

            Parent pageRoot = FXMLLoader.load(fxmlUrl);

            if (mainLayout != null) {
                mainLayout.setCenter(pageRoot);
            } else {
                System.err.println("Error: mainLayout (BorderPane root) is null. Check fx:id=\"mainLayout\" in dashboard.fxml and @FXML injection in controller.");
                showAlert("Error Kritis", "Komponen layout utama (mainLayout) tidak ditemukan di controller.");
            }

        } catch (IOException e) {
            System.err.println("Failed to load page: " + fxmlPath);
            e.printStackTrace();
            showAlert("Error", "Gagal memuat halaman '" + fxmlPath + "': " + e.getMessage());
        }
    }

    /**
     * Helper untuk menampilkan alert sederhana.
     * @param title Judul alert.
     * @param content Isi pesan alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void refreshDashboardDisplay() {
        // Panggil kembali metode yang mengisi data dan tampilan dashboard
        if (allTasks == null || allProjects == null || allComparableData == null) {
            loadMockData(); // Atau logika refresh data yang lebih canggih
        }
        setupTaskHistogram();
        updateKpiBox();
        populateActiveProjectsList();
        populatePriorityTasksList();
    }
}