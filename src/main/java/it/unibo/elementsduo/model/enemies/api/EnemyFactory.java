package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.resources.Position;


/**
 * Factory for creating different types of enemies.
 * The enemy type is determined by a character code.
 */
public interface EnemyFactory {

    /**
     * @param c the character code representing the type of enemy to create (e.g., 'S' for Shooter).
     * @param pos the starting position of the enemy.
     * @return the created Enemy instance.
     */
    Enemy createEnemy(char c, Position pos);
}

