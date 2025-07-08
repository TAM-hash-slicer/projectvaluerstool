package model;

public class ProjectTemplate {
    private final String identifier; // ID unik untuk logika, misal: "SIMPLIFIED_INCOME"
    private final String displayName; // Teks yang akan dilihat pengguna
    private final boolean isUnderDevelopment; // Flag untuk fitur yang belum siap

    public ProjectTemplate(String identifier, String displayName, boolean isUnderDevelopment) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.isUnderDevelopment = isUnderDevelopment;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isUnderDevelopment() {
        return isUnderDevelopment;
    }

    // Ini sangat penting! ComboBox akan menggunakan metode ini untuk menampilkan teks di dalam list.
    @Override
    public String toString() {
        return displayName;
    }
}