package model;

import java.time.LocalDate;

public class Project {
    private String id;
    private String clientName;
    private String assetType;
    private String status; // e.g., "Inspeksi Lapangan", "Analisis Data", "Final"
    private LocalDate dueDate;
    private double progress; // 0.0 to 1.0 for progress bar

    public Project(String id, String clientName, String assetType, String status, LocalDate dueDate, double progress) {
        this.id = id;
        this.clientName = clientName;
        this.assetType = assetType;
        this.status = status;
        this.dueDate = dueDate;
        this.progress = progress;
    }

    // Getters and Setters (or make fields final if immutable after creation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getAssetType() { return assetType; }
    public void setAssetType(String assetType) { this.assetType = assetType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public double getProgress() { return progress; }
    public void setProgress(double progress) { this.progress = progress; }

    @Override
    public String toString() {
        return String.format("ID: %s, Klien: %s, Aset: %s, Status: %s, Due: %s",
                id, clientName, assetType, status, dueDate.toString());
    }
}