package it.unibo.elementsduo.model.map.level.api;

import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.gem.api.Gem;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

/**
 * Represents a game level acting as the main container for all game entities.
 * It provides methods to access, filter, and manage the state of entities
 * within the level.
 */

public interface LevelData {

    /**
     * Gets an unmodifiable view of all game entities currently in the level.
     * This set reflects the current state, but cannot be altered directly.
     *
     * @return An unmodifiable set of all {@link GameEntity} objects.
     */
    Set<GameEntity> getGameEntities();

    /**
     * Gets all static and interactive obstacles in the level.
     *
     * @return A set of all {@link obstacle} objects.
     */
    Set<Obstacle> getAllObstacles();

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
     * Gets all entities that can be part of the collision system.
     *
     * @return A list of all {@link Collidable} objects.
     */
    List<Collidable> getAllCollidables();

    /**
     * Gets all entities that can be part of the update system.
     *
     * @return A set of all {@link Updatable} objects.
     */
    Set<Updatable> getAllUpdatables();

    /**
     * Gets all entities that can be part of the update system.
     *
     * @return A set of all {@link AbstractInteractiveObsactle} objects.
     */
    Set<AbstractInteractiveObstacle> getInteractiveObstacles();

    /**
     * Gets all gems in the level.
     *
     * @return A set of all {@link Gem} objects.
     */
    Set<Gem> getGems();

    /**
     * Gets all walls in the level.
     *
     * @return A set of all {@link Wall} objects.
     */
    Set<Wall> getWalls();

    /**
     * Gets all levers in the level.
     *
     * @return A set of all {@link Lever} objects.
     */
    Set<Lever> getLevers();

    /**
     * Gets all buttons in the level.
     *
     * @return A set of all {@link Buttons} objects.
     */
    Set<Button> getButtons();

    /**
     * Gets a set of WaterGirl, it should be only one instance
     *
     * @return A set of all {@link Watergirl} objects.
     */
    Set<Watergirl> getWatergirl();

    /**
     * Gets a set of Fireboy, it should be only one instance
     *
     * @return A set of all {@link Fireboy} objects.
     */
    Set<Fireboy> getFireboy();

    /**
     * Gets a set of FireExit, it should be only one instance.
     *
     * @return A set of all {@link FireExit} objects.
     */
    Set<FireExit> getFireExit();

    /**
     * Gets Gets a set of WaterExit, it should be only one instance.
     *
     * @return A set of all {@link WaterExit} objects.
     */
    Set<WaterExit> getWaterExit();

}
