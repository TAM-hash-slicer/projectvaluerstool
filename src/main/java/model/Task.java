package model;

import java.time.LocalDate;

public class Task {
    public enum Status {
        BARU("Baru"),
        ON_PROGRESS("On Progress"),
        JATUH_TEMPO("Jatuh Tempo"), // Or "Mendekati Jatuh Tempo"
        TARGET("Target Prioritas"), // Or "Selesai" if target means completion
        SELESAI("Selesai");


        private final String displayName;
        Status(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
        @Override public String toString() { return displayName; }
    }

    public enum Priority {
        TINGGI, SEDANG, RENDAH
    }

    private String id;
    private String description;
    private String relatedProjectId;
    private Priority priority;
    private LocalDate dueDate;
    private Status status;

    public Task(String id, String description, String relatedProjectId, Priority priority, LocalDate dueDate, Status status) {
        this.id = id;
        this.description = description;
        this.relatedProjectId = relatedProjectId;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRelatedProjectId() { return relatedProjectId; }
    public void setRelatedProjectId(String relatedProjectId) { this.relatedProjectId = relatedProjectId; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%s (Proyek: %s, Prioritas: %s, Due: %s, Status: %s)",
                description, relatedProjectId, priority, dueDate.toString(), status.getDisplayName());
    }
}