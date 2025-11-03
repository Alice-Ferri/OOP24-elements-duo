package it.unibo.elementsduo.model.map.level.api;

import it.unibo.elementsduo.model.enemies.api.Projectiles;

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
