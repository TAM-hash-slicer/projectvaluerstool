package model;

import java.time.LocalDate;

public class Project {
    private String id;
    private String clientName;
    private String assetType;
    private String status;
    private LocalDate dueDate;
    private double progress;
    private String valuationApproach; // The new field for filtering

    public Project(String id, String clientName, String assetType, String status, LocalDate dueDate, double progress, String valuationApproach) {
        this.id = id;
        this.clientName = clientName;
        this.assetType = assetType;
        this.status = status;
        this.dueDate = dueDate;
        this.progress = progress;
        this.valuationApproach = valuationApproach; // Assign the new value
    }

    // --- Getters and Setters ---

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

    // New getter and setter for the valuation approach
    public String getValuationApproach() {
        return valuationApproach;
    }

    public void setValuationApproach(String valuationApproach) {
        this.valuationApproach = valuationApproach;
    }


    @Override
    public String toString() {
        // You can add the new field here for easier debugging
        return String.format("ID: %s, Klien: %s, Aset: %s, Status: %s, Pendekatan: %s",
                id, clientName, assetType, status, valuationApproach);
    }
}