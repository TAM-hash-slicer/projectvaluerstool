package service;

import com.fasterxml.jackson.databind.DeserializationFeature; // Import this
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections; // Import this
import java.util.List;      // Import this
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors; // Import this
import java.util.stream.Stream;     // Import this

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

    private SaveAndLoadService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // This is a good practice to prevent future crashes if you add a field to a save file
        // but try to load it with an older version of the code.
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Files.createDirectories(SAVE_PATH);
            LOGGER.info("Save directory ensured at: " + SAVE_PATH);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create save directory!", e);
        }
    }

    public static synchronized SaveAndLoadService getInstance() {
        if (instance == null) {
            instance = new SaveAndLoadService();
        }
        return instance;
    }

    // ... (saveProgress, loadProgress, deleteProgress methods are unchanged) ...

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
     * Scans the save directory and returns a list of all available save keys (filenames without extension).
     *
     * @return A list of strings, where each string is a save key.
     */
    public List<String> listSaveKeys() {
        if (!Files.isDirectory(SAVE_PATH)) {
            return Collections.emptyList();
        }
        try (Stream<Path> paths = Files.walk(SAVE_PATH, 1)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".json"))
                    .map(name -> name.substring(0, name.length() - 5)) // Remove ".json"
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read save directory to list files.", e);
            return Collections.emptyList();
        }
    }

    private Path getFilePath(String saveKey) {
        String fileName = saveKey.replaceAll("[^a-zA-Z0-9_.-]", "_") + ".json";
        return SAVE_PATH.resolve(fileName);
    }
}