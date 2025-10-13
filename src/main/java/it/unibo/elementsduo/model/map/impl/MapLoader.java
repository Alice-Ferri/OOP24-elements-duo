package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.obstacleFactory;
import it.unibo.elementsduo.model.obstacles.impl.obstacleType;
import it.unibo.elementsduo.utils.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapLoader {

    private static final String levelFolder = "levels/";
    private static final String levelFile = "map%d.txt";

    private final obstacleFactory obstacleFactory;
    private final EnemyFactory enemiesFactory;

    private static final Map<Character,obstacleType.type> symbolMap = Map.of(
        'P',obstacleType.type.FLOOR,
        '#',obstacleType.type.WALL,
        'B',obstacleType.type.FIRE_SPAWN,
        'W',obstacleType.type.WATER_SPAWN,
        'A',obstacleType.type.WATER_EXIT,
        'F',obstacleType.type.FIRE_EXIT
    );

    private static final Set<Character> symbolEnemies = Set.of('C','S');

    public MapLoader(final obstacleFactory factory, final EnemyFactory enemyFactory) {
        this.obstacleFactory = Objects.requireNonNull(factory);
        this.enemiesFactory = Objects.requireNonNull(enemyFactory);
    }

    public Level loadLevel(final int levelNumber) {
        final String filePath = levelFolder + String.format(levelFile,levelNumber);
        return loadLevelFromFile(filePath);
    }

    private Level loadLevelFromFile(final String filePath) {
        final Set<obstacle> obstacles = new HashSet<>();
        final Set<Enemy> enemies = new HashSet<>();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

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

                    if(symbolEnemies.contains(symbol)){
                        enemies.add(enemiesFactory.createEnemy(symbol, pos));

                    }else if(symbolMap.containsKey(symbol)){
                        final obstacleType.type type = symbolMap.get(symbol);
                        obstacles.add(obstacleFactory.createObstacle(type,pos));
                    }
                    
                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file: " + filePath, e);
        }

        return new LevelImpl(obstacles,enemies);
    }
}

