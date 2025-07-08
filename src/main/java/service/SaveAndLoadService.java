package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A singleton service for saving and loading application progress as JSON files.
 * This service is generic and can handle any serializable data object.
 */
public class SaveAndLoadService {

    private static final Logger LOGGER = Logger.getLogger(SaveAndLoadService.class.getName());
    private static final String SAVE_DIRECTORY_NAME = ".projectvaluertools/saves";
    private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), SAVE_DIRECTORY_NAME);

    private static SaveAndLoadService instance;
    private final ObjectMapper objectMapper;

    /**
     * Private constructor to ensure a single instance (Singleton pattern).
     */
    private SaveAndLoadService() {
        // Configure the JSON mapper
        this.objectMapper = new ObjectMapper();
        // Indent output for human-readable JSON files
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Ensure the save directory exists
        try {
            Files.createDirectories(SAVE_PATH);
            LOGGER.info("Save directory ensured at: " + SAVE_PATH);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create save directory!", e);
        }
    }

    /**
     * Gets the single instance of the SaveAndLoadService.
     *
     * @return The singleton instance.
     */
    public static synchronized SaveAndLoadService getInstance() {
        if (instance == null) {
            instance = new SaveAndLoadService();
        }
        return instance;
    }

    /**
     * Saves a data object to a JSON file. The file will be named based on the saveKey.
     *
     * @param dataObject The object containing the progress to save.
     * @param saveKey    A unique identifier for the save file (e.g., "pendekatanPasar_P001").
     * @param <T>        The type of the data object.
     */
    public <T> void saveProgress(T dataObject, String saveKey) {
        if (dataObject == null || saveKey == null || saveKey.trim().isEmpty()) {
            LOGGER.warning("Save aborted: data object or save key is null/empty.");
            return;
        }

        Path filePath = getFilePath(saveKey);
        try {
            String jsonString = objectMapper.writeValueAsString(dataObject);
            Files.writeString(filePath, jsonString);
            LOGGER.info("Successfully saved progress for key: " + saveKey);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save progress for key: " + saveKey, e);
        }
    }

    /**
     * Loads a data object from a JSON file.
     *
     * @param saveKey  The unique identifier for the save file to load.
     * @param dataType The class of the object to be loaded (e.g., PendekatanPasarData.class).
     * @param <T>      The type of the data object.
     * @return An Optional containing the loaded object, or an empty Optional if loading fails or the file doesn't exist.
     */
    public <T> Optional<T> loadProgress(String saveKey, Class<T> dataType) {
        if (saveKey == null || saveKey.trim().isEmpty()) {
            LOGGER.warning("Load aborted: save key is null or empty.");
            return Optional.empty();
        }

        Path filePath = getFilePath(saveKey);
        if (!Files.exists(filePath)) {
            LOGGER.info("No save file found for key: " + saveKey);
            return Optional.empty();
        }

        try {
            String jsonString = Files.readString(filePath);
            T loadedObject = objectMapper.readValue(jsonString, dataType);
            LOGGER.info("Successfully loaded progress for key: " + saveKey);
            return Optional.of(loadedObject);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load or parse progress for key: " + saveKey, e);
            return Optional.empty();
        }
    }

    /**
     * Deletes a specific save file.
     *
     * @param saveKey The unique identifier for the save file to delete.
     * @return true if the file was successfully deleted, false otherwise.
     */
    public boolean deleteProgress(String saveKey) {
        Path filePath = getFilePath(saveKey);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error deleting save file for key: " + saveKey, e);
            return false;
        }
    }

    /**
     * Constructs the full file path for a given save key.
     *
     * @param saveKey The unique key.
     * @return The full Path to the save file.
     */
    private Path getFilePath(String saveKey) {
        // Sanitize the key to make it a valid filename
        String fileName = saveKey.replaceAll("[^a-zA-Z0-9_.-]", "_") + ".json";
        return SAVE_PATH.resolve(fileName);
    }
}