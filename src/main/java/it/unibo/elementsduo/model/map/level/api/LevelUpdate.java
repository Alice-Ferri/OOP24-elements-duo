package it.unibo.elementsduo.model.map.level.api;

import it.unibo.elementsduo.model.enemies.api.Projectiles;

/**
 * Interface that define methods that
 * level can use to modify his set.
 */
public interface LevelUpdate {

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

}
