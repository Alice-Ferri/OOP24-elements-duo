package it.unibo.elementsduo.datasave;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unibo.elementsduo.model.progression.ProgressionState;
import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

public class SaveManager {

    private static final String SAVE_FILE_NAME = "savegame.json";
    private final Gson gson = new Gson();
    private final Path savePath;

    public SaveManager(Path baseDir) {
        this.savePath = baseDir.resolve(SAVE_FILE_NAME);
    }

    public void saveGame(final ProgressionState state) {
        try (FileWriter writer = new FileWriter(savePath.toFile())) {
            gson.toJson(state, writer);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del gioco.");
        }
    }

    public Optional<ProgressionState> loadGame() {
        if (!savePath.toFile().exists()) {
            return Optional.empty(); 
        }

        try (FileReader reader = new FileReader(savePath.toFile())) {
            return Optional.of(gson.fromJson(reader, ProgressionState.class));
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Errore durante il caricamento o la lettura del file.");
            return Optional.empty(); 
        }
    }
}