package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.controller.enemiesController.api.EnemiesMoveManager;

/**
 * Interface for game entities that require the EnemiesMoveManager.
 */
public interface ManagerInjectable {

    /**
     * Sets the move manager.
     *
     * @param manager the EnemiesMoveManager instance.
     */
    void setMoveManager(EnemiesMoveManager manager);
}

