package it.unibo.elementsduo.model.map.level.api;

import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Represents a game level acting as the main container for all game entities.
 * It provides methods to access, filter, and manage the state of entities
 * within the level.
 */

public interface Level {

    /**
     * Gets an unmodifiable view of all game entities currently in the level.
     * This set reflects the current state, but cannot be altered directly.
     *
     * @return An unmodifiable set of all {@link GameEntity} objects.
     */
    Set<GameEntity> getGameEntities();

    /**
     * Filters and returns a set of entities that match the specified class type.
     *
     * @param <T>  The type of the entity.
     * @param type The Class object of the type to filter by.
     * @return An unmodifiable set of entities of the specified type.
     */
    <T extends GameEntity> Set<T> getEntitiesByClass(Class<T> type);

    /**
     * Gets all static and interactive obstacles in the level.
     *
     * @return A set of all {@link obstacle} objects.
     */
    Set<obstacle> getAllObstacles();

    /**
     * Gets all enemies in the level, regardless of their state (alive or dead).
     *
     * @return A set of all {@link Enemy} objects.
     */
    Set<Enemy> getAllEnemies();

    /**
     * Gets only the enemies that are currently alive.
     *
     * @return A set of living {@link Enemy} objects.
     */
    Set<Enemy> getLivingEnemies();

    /**
     * Gets all players in the level.
     *
     * @return A set of all {@link Player} objects.
     */
    Set<Player> getAllPlayers();

    /**
     * Gets all interactive obstacles (e.g, levers, buttons, platforms) in the
     * level.
     *
     * @return A set of all {@link InteractiveObstacle} objects.
     */
    Set<AbstractInteractiveObstacle> getAllInteractiveObstacles();

    /**
     * Gets all active projectiles in the level.
     *
     * @return A set of all {@link Projectiles} objects.
     */
    Set<Projectiles> getAllProjectiles();

    /**
     * Removes inactive entities (like dead enemies or expired projectiles)
     * from the main game entity set.
     */
    void cleanInactiveEntities();

    /**
     * Adds a new projectile to the level.
     *
     * @param p The projectile to add.
     */
    void addProjectile(Projectiles p);

    /**
     * Removes all inactive projectiles from the level.
     */

    void cleanProjectiles();

    /**
     * Gets all entities that can be part of the collision system.
     *
     * @return A list of all {@link Collidable} objects.
     */

    List<Collidable> getAllCollidables();
}
