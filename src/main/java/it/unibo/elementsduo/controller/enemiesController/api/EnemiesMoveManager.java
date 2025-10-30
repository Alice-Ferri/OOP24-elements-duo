package it.unibo.elementsduo.controller.enemiesController.api;

import it.unibo.elementsduo.model.enemies.api.Enemy;

/**
 * Defines the contract for classes responsible for managing enemy movement logic,
 * such as detecting edges of platforms or direction reversal due to obstacles.
 */
public interface EnemiesMoveManager {

    /**
     * Checks if the enemy is at the edge of a platform
     * that requires a direction change, and performs the necessary action.
     *
     * @param enemy the enemy instance to check and potentially modify.
     */
    void handleEdgeDetection(Enemy enemy);

}

