package it.unibo.elementsduo.datasave;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.elementsduo.model.progression.ProgressionState;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Manages game persistence by saving and loading the ProgressionState 
 * to and from a JSON file.
 */
public final class SaveManager { 

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
     * overwriting any existing data. Uses UTF-8 encoding.
     *
     * @param state the ProgressionState object to be saved.
     */
    public void saveGame(final ProgressionState state) {

        try {

            this.objectMapper.writeValue(this.savePath.toFile(), state); 

        } catch (final IOException e) { 
            e.printStackTrace();
        }
    }

   /**
     * Loads the progression state from the JSON file, using UTF-8 encoding.
     *
     * @return an {@link Optional} containing the loaded {@link ProgressionState}, or empty otherwise.
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
            
            e.printStackTrace();
            return Optional.empty(); 
        }
    }
}
