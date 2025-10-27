package it.unibo.elementsduo.model.map.impl;

import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleType;
import it.unibo.elementsduo.resources.Position;

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

    private final ObstacleFactory obstacleFactory;
    private final EnemyFactory enemiesFactory;
    private final InteractiveObstacleFactory interactiveObsFactory;

    private static final Map<Character, obstacleType.type> symbolMap = Map.of(
            'P', obstacleType.type.FLOOR,
            '#', obstacleType.type.WALL,
            'B', obstacleType.type.FIRE_SPAWN,
            'W', obstacleType.type.WATER_SPAWN,
            'A', obstacleType.type.WATER_EXIT,
            'F', obstacleType.type.FIRE_EXIT);

    private static final Set<Character> symbolEnemies = Set.of('C', 'S');
    private static final Set<Character> symbolPlayer = Set.of('W', 'B');
    private static final Set<Character> symbolInteractiveObstacle = Set.of('L', 'P', 'M');

    public MapLoader(final ObstacleFactory factory, final EnemyFactory enemyFactory,
            final InteractiveObstacleFactory interactiveObstacleFactory) {
        this.obstacleFactory = Objects.requireNonNull(factory);
        this.enemiesFactory = Objects.requireNonNull(enemyFactory);
        this.interactiveObsFactory = Objects.requireNonNull(interactiveObstacleFactory);
    }

    public Level loadLevel(final int levelNumber) {
        final String filePath = levelFolder + String.format(levelFile, levelNumber);
        return loadLevelFromFile(filePath);
    }

    private Level loadLevelFromFile(final String filePath) {
        final Set<Obstacle> obstacles = new HashSet<>();
        final Set<Enemy> enemies = new HashSet<>();
        final Set<Player> players = new HashSet<>();
        final Set<InteractiveObstacle> interactiveObstacles = new HashSet<>();
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

                    if (symbolEnemies.contains(symbol)) {
                        enemies.add(enemiesFactory.createEnemy(symbol, pos));

                    } else if (symbolMap.containsKey(symbol)) {
                        final obstacleType.type type = symbolMap.get(symbol);
                        obstacles.add(obstacleFactory.createObstacle(type, new HitBoxImpl(pos, 1, 1)));
                    } else if (symbolPlayer.contains(symbol)) {
                        if (symbol == 'W') {
                            players.add(new Watergirl(pos));
                        } else {
                            players.add(new Fireboy(pos));
                        }
                    } else if (symbolInteractiveObstacle.contains(symbol)) {
                        switch (symbol) {
                            case 'L':
                                interactiveObstacles.add(
                                        interactiveObsFactory.createLever(pos));
                                break;
                            case 'P':
                                interactiveObstacles.add(
                                        interactiveObsFactory.createPushBox(pos));
                                break;
                            case 'M':
                                interactiveObstacles.add(
                                        interactiveObsFactory.createMovingPlatform(
                                                pos, pos, new Position(pos.x() - 3, pos.y() - 3)));
                                break;
                            default:
                                break;
                        }

                    }

                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file: " + filePath, e);
        }

        return new LevelImpl(obstacles, enemies, players, interactiveObstacles);
    }
}
