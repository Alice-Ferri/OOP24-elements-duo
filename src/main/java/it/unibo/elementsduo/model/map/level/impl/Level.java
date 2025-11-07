package it.unibo.elementsduo.model.map.level.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.map.level.api.LevelData;
import it.unibo.elementsduo.model.map.level.api.LevelUpdate;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.staticobstacles.gem.api.Gem;
import it.unibo.elementsduo.model.obstacles.staticobstacles.solid.Wall;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

/**
 * Implementation of the {@link Level} interface.
 * It holds the complete set of game entities for a level and provides
 * methods to access and manage them.
 */
public final class Level implements LevelData, LevelUpdate {

    private final Set<GameEntity> gameEntities;

    /**
     * Constructs a new Level instance.
     *
     * @param gameEntities A non-null set of all game entities that compose this
     *                     level.
     */
    public Level(final Set<GameEntity> gameEntities) {
        this.gameEntities = new HashSet<>(Objects.requireNonNull(gameEntities));
    }

    @Override
    public Set<GameEntity> getGameEntities() {
        return Collections.unmodifiableSet(this.gameEntities);
    }

    /**
     * Filters and returns a set of entities that match the specified class type.
     *
     * @param <T>  The type of the entity.
     * @param type The Class object of the type to filter by.
     * @return An unmodifiable set of entities of the specified type.
     */
    private <T extends GameEntity> Set<T> getEntitiesByClass(final Class<T> type) {
        return this.gameEntities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Obstacle> getAllObstacles() {
        return getEntitiesByClass(Obstacle.class);
    }

    @Override
    public Set<Enemy> getAllEnemies() {
        return getEntitiesByClass(Enemy.class);
    }

    @Override
    public Set<Enemy> getLivingEnemies() {
        return getAllEnemies().stream()
                .filter(Enemy::isAlive)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Player> getAllPlayers() {
        return getEntitiesByClass(Player.class);
    }

    @Override
    public Set<AbstractInteractiveObstacle> getAllInteractiveObstacles() {
        return getEntitiesByClass(AbstractInteractiveObstacle.class);
    }

    @Override
    public Set<Projectiles> getAllProjectiles() {
        return getEntitiesByClass(Projectiles.class);
    }

    @Override
    public void addProjectile(final Projectiles p) {
        if (p != null) {
            this.gameEntities.add(p);
        }
    }

    @Override
    public void cleanProjectiles() {
        this.gameEntities.removeIf(e -> e instanceof Projectiles p && !p.isActive());
    }

    @Override
    public void cleanInactiveEntities() {
        this.gameEntities.removeIf(entity -> entity instanceof Projectiles p && !p.isActive()
                || entity instanceof Enemy e && !e.isAlive()
                || entity instanceof Gem gem && !gem.isCollectable());
    }

    @Override
    public List<Collidable> getAllCollidables() {
        return this.gameEntities.stream()
                .filter(Collidable.class::isInstance)
                .map(Collidable.class::cast)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Set<Updatable> getAllUpdatables() {
        return this.gameEntities.stream()
                .filter(Updatable.class::isInstance)
                .map(Updatable.class::cast)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Fireboy> getFireboy() {
        return this.getEntitiesByClass(Fireboy.class);
    }

    @Override
    public Set<Watergirl> getWatergirl() {
        return this.getEntitiesByClass(Watergirl.class);
    }

    @Override
    public Set<Button> getButtons() {
        return this.getEntitiesByClass(Button.class);
    }

    @Override
    public Set<Lever> getLevers() {
        return this.getEntitiesByClass(Lever.class);
    }

    @Override
    public Set<Wall> getWalls() {
        return this.getEntitiesByClass(Wall.class);
    }

    @Override
    public Set<Gem> getGems() {
        return this.getEntitiesByClass(Gem.class);
    }

    @Override
    public void cleanGems() {
        this.gameEntities.removeIf(e -> e instanceof Gem p && !p.isCollectable());
    }

    @Override
    public Set<AbstractInteractiveObstacle> getInteractiveObstacles() {
        return this.getEntitiesByClass(AbstractInteractiveObstacle.class);
    }

    @Override
    public Set<WaterExit> getWaterExit() {
        return this.getEntitiesByClass(WaterExit.class);
    }

    @Override
    public Set<FireExit> getFireExit() {
        return this.getEntitiesByClass(FireExit.class);
    }

}
