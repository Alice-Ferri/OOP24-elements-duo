package it.unibo.elementsduo.datasave;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.elementsduo.model.progression.ProgressionState;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages game persistence by saving and loading the ProgressionState 
 * to and from a JSON file.
 */
public final class SaveManager { 

    private static final Logger LOGGER = Logger.getLogger(SaveManager.class.getName());
    private static final String SAVE_FILE_NAME = "savegame.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path savePath;

    /**
     * Initializes the SaveManager, setting the path to the save file.
     *
     * @param baseDir the base directory where the save file should be stored.
     */
    public SaveManager(final Path baseDir) { 
        this.savePath = baseDir.resolve(SAVE_FILE_NAME);
    }

    /**
     * Saves the current progression state to the designated JSON file, 
     * overwriting any existing data.
     *
     * @param state the ProgressionState object to be saved.
     */
    public void saveGame(final ProgressionState state) {

        try {

            this.objectMapper.writeValue(this.savePath.toFile(), state); 

        } catch (final IOException e) { 
            LOGGER.log(Level.SEVERE, "Failed to save game state to " + this.savePath, e);
        }
    }

    /**
     * Loads the progression state from the JSON file.
     *
     * @return an {@link Optional} containing the loaded {@link ProgressionState}, 
     */
    public Optional<ProgressionState> loadGame() {
        final File saveFile = this.savePath.toFile();
        if (!saveFile.exists()) {
            return Optional.empty();
        }

        try {

            final ProgressionState loadedState = 
                this.objectMapper.readValue(saveFile, ProgressionState.class);

            return Optional.of(loadedState);

        } catch (final IOException e) { 

           LOGGER.log(Level.SEVERE, "Failed to load game state from " + this.savePath, e);
            return Optional.empty();
        }
    }
}
