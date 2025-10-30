package it.unibo.elementsduo.model.map.level;

import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.impl.EntityFactoryImpl;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.button;

import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.level.impl.LevelImpl;
import it.unibo.elementsduo.resources.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MapLoader {

    private static final String levelFolder = "levels/";
    private static final String levelFile = "map%d.txt";
    private final EntityFactory entityFactory;

    public MapLoader(final ObstacleFactory obstacleFactory,
                     final EnemyFactory enemyFactory,
                     final InteractiveObstacleFactory interactiveObstacleFactory) { 
        this.entityFactory = new EntityFactoryImpl(
                Objects.requireNonNull(obstacleFactory),
                Objects.requireNonNull(enemyFactory),
                Objects.requireNonNull(interactiveObstacleFactory)
        );
    }

    public Level loadLevel(final int levelNumber) {
        final String filePath = levelFolder + String.format(levelFile, levelNumber);
        return loadLevelFromFile(filePath);
    }

    private Level loadLevelFromFile(final String filePath) {
        final Set<GameEntity> gameEntities = new HashSet<>();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

        if (is == null) {
            throw new IllegalArgumentException("File di mappa non trovato: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    final char symbol = line.charAt(x);
                    final Position pos = new Position(x, y);

                    gameEntities.addAll(entityFactory.createEntities(symbol, pos));

                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la lettura del file di mappa: " + filePath, e);
        }

        linkInteractiveObjects(gameEntities);
        return new LevelImpl(gameEntities);
    }

    private void linkInteractiveObjects(Set<GameEntity> interObjs) {
        List<Lever> levers = interObjs.stream()
                .filter(Lever.class::isInstance)
                .map(Lever.class::cast)
                .toList();

        List<button> buttons = interObjs.stream()
                .filter(button.class::isInstance)
                .map(button.class::cast)
                .toList();

        List<PlatformImpl> platforms = interObjs.stream()
                .filter(PlatformImpl.class::isInstance)
                .map(PlatformImpl.class::cast)
                .toList();

        for (int i = 0; i < Math.min(levers.size(), platforms.size()); i++) {
            levers.get(i).addLinkedObject(platforms.get(i));
        }

        for (int i = 0; i < Math.min(buttons.size(), platforms.size()); i++) {
            buttons.get(i).linkTo(platforms.get(i));
        }
    }
}
