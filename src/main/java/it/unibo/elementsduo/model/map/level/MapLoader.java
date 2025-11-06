package it.unibo.elementsduo.model.map.level;

import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.impl.EntityAssemblerImpl;
import it.unibo.elementsduo.model.map.level.api.MapLoadingException;
import it.unibo.elementsduo.model.gameentity.api.EntityAssembler;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Button;

import it.unibo.elementsduo.resources.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles loading game levels from text files.
 * This class reads a map file, uses an entityAssembler to create game entities
 * based on symbols, and links interactive objects.
 */
public final class MapLoader {

    private static final String LEVEL_FOLDER = "levels/";
    private static final String LEVEL_FILE = "map%d.txt";
    private final EntityAssembler entityAssembler;

    /**
     * Constructs a MapLoader with the entity factories.
     */
    public MapLoader() {
        this.entityAssembler = new EntityAssemblerImpl();
    }

    /**
     * Loads a specific level by its number.
     * It formats the file path and delegates to loadLevelFromFIle method.
     *
     * @param levelNumber The number of the level to load (e.g., 1 for "map1.txt").
     * @return The fully constructed Level object.
     * @throws MapLoadingException      If an I/O error occurs during file reading.
     * @throws IllegalArgumentException If the map file is not found.
     */
    public Set<GameEntity> loadLevel(final int levelNumber) {
        final String filePath = LEVEL_FOLDER + String.format(LEVEL_FILE, levelNumber);
        return loadLevelFromFile(filePath);
    }

    /**
     * Loads a level from a specific file path (e.g., "levels/map1.txt").
     * Reads the file, creates entities using the entityAssembler,
     * links interactive objects, and returns the final entity set.
     *
     * @param filePath The full path to the map file within the resources folder.
     * @return A {@link Set} of all {@link GameEntity} objects for the level.
     * @throws IllegalArgumentException If the map file is not found.
     * @throws MapLoadingException      If an I/O error occurs during reading.
     */
    public Set<GameEntity> loadLevelFromFile(final String filePath) {
        final Set<GameEntity> gameEntities = new HashSet<>();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

        if (is == null) {
            throw new IllegalArgumentException("Map file not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            int y = 0;
            while (line != null) {
                for (int x = 0; x < line.length(); x++) {
                    final char symbol = line.charAt(x);
                    final Position pos = new Position(x, y);

                    final GameEntity entity = entityAssembler.createEntity(symbol, pos);
                    if (entity != null) {
                        gameEntities.add(entity);
                    }

                }
                y++;
                line = br.readLine();
            }
        } catch (final IOException e) {
            throw new MapLoadingException("Error while reading map file: " + filePath, e);
        }

        linkInteractiveObjects(gameEntities);
        return gameEntities;
    }

    private void linkInteractiveObjects(final Set<GameEntity> gameEntities) {
        final List<Lever> levers = gameEntities.stream()
                .filter(Lever.class::isInstance)
                .map(Lever.class::cast)
                .toList();

        final List<Button> buttons = gameEntities.stream()
                .filter(Button.class::isInstance)
                .map(Button.class::cast)
                .toList();

        final List<PlatformImpl> platforms = gameEntities.stream()
                .filter(PlatformImpl.class::isInstance)
                .map(PlatformImpl.class::cast)
                .toList();

        for (int i = 0; i < Math.min(levers.size(), platforms.size()); i++) {
            levers.get(i).addListener(platforms.get(i));
        }

        final int platformsUsed = levers.size();

        for (int i = 0; i < Math.min(buttons.size(), platforms.size() - platformsUsed); i++) {
            buttons.get(i).addListener(platforms.get(i + platformsUsed));
        }
    }
}
