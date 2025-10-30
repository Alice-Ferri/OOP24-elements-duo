package it.unibo.elementsduo.datasave;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unibo.elementsduo.model.progression.ProgressionState;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets; 
import java.nio.file.Path;
import java.util.Optional;

/**
 * Manages game persistence by saving and loading the ProgressionState 
 * to and from a JSON file.
 */
public final class SaveManager { 

    private static final String SAVE_FILE_NAME = "savegame.json";
    private final Gson gson = new Gson();
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
        try (OutputStreamWriter writer = 
                new OutputStreamWriter(new FileOutputStream(this.savePath.toFile()), StandardCharsets.UTF_8)) {

            this.gson.toJson(state, writer);

        } catch (final IOException e) { 
            e.printStackTrace();
        }
    }

    /**
     * Loads the progression state from the JSON file. Uses UTF-8 encoding.
     *
     * @return an Optional containing the loaded ProgressionState if successful, 
     * *or an empty Optional if the file does not exist or an error occurs.
     */
    public Optional<ProgressionState> loadGame() {
        if (!this.savePath.toFile().exists()) {
            return Optional.empty(); 
        }

        try (InputStreamReader reader = 
                new InputStreamReader(new FileInputStream(this.savePath.toFile()), StandardCharsets.UTF_8)) {

            return Optional.of(this.gson.fromJson(reader, ProgressionState.class));

        } catch (final IOException | JsonSyntaxException e) { 
            return Optional.empty(); 
        }
    }
}
