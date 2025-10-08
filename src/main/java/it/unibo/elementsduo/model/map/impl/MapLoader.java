package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.map.api.Tile;
import it.unibo.elementsduo.model.map.api.TileFactory;
import it.unibo.elementsduo.resources.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {

    private static final String LEVELS_FOLDER = "levels/";
    private static final String LEVEL_FILE_PREFIX = "map";
    private static final String LEVEL_FILE_SUFFIX = ".txt";

    private final TileFactory tileFactory;

    public MapLoader(final TileFactory factory) {
        this.tileFactory = factory;
    }

    public Level loadLevel(final int levelNumber) {
        final String filePath = LEVELS_FOLDER + LEVEL_FILE_PREFIX + levelNumber + LEVEL_FILE_SUFFIX;
        return loadLevelFromFile(filePath);
    }

    private Level loadLevelFromFile(final String filePath) {
        final Map<Position, Tile> tiles = new HashMap<>();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        System.out.println(getClass().getClassLoader().getResource("levels/map1.txt"));

        if (is == null) {
            throw new IllegalArgumentException("File non trovato: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            int y = 0;

            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    final char symbol = line.charAt(x);
                    final Position pos = new Position(x, y);
                    final Tile tile = tileFactory.createTile(symbol);
                    if (tile != null) {
                        tiles.put(pos, tile);
                    }
                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file: " + filePath, e);
        }

        return new LevelImpl(tiles);
    }
}

