package model;

import model.PendapatanData.PendekatanPendapatanData; // 1. Add the import

/**
 * Central data model for a single valuation project.
 * This class acts as a container for all data related to the project,
 * including the data for each valuation approach.
 */
public class ValuationModel {

    private String projectId;
    private String clientName;
    private long lastSavedTimestamp;

    // --- Branches for each valuation approach ---
    private PendekatanPasarData pendekatanPasar;
    // private PendekatanBiayaData pendekatanBiaya;     // Future branch for Cost Approach
    private PendekatanPendapatanData pendekatanPendapatan; // 2. Uncomment the field

    /**
     * Constructor to initialize the model and its branches.
     * When a new valuation is started, a new instance of this model is created.
     */
    public ValuationModel() {
        // Initialize all branches to ensure they are not null
        this.pendekatanPasar = new PendekatanPasarData();
        // this.pendekatanBiaya = new PendekatanBiayaData();
        this.pendekatanPendapatan = new PendekatanPendapatanData(); // 3. Initialize in constructor
    }

    // --- Getters and Setters ---

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getLastSavedTimestamp() {
        return lastSavedTimestamp;
    }

    public void setLastSavedTimestamp(long lastSavedTimestamp) {
        this.lastSavedTimestamp = lastSavedTimestamp;
    }

    public PendekatanPasarData getPendekatanPasar() {
        return pendekatanPasar;
    }

    public void setPendekatanPasar(PendekatanPasarData pendekatanPasar) {
        this.pendekatanPasar = pendekatanPasar;
    }

    // 4. Add the getter and setter for the new branch
    public PendekatanPendapatanData getPendekatanPendapatan() {
        return pendekatanPendapatan;
    }

    public void setPendekatanPendapatan(PendekatanPendapatanData pendekatanPendapatan) {
        this.pendekatanPendapatan = pendekatanPendapatan;
    }
}