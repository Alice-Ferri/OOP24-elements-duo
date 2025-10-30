package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;

/**
 * Interface for game entities that require the EnemiesMoveManager.
 */
@FunctionalInterface
public interface ManagerInjectable {

    /**
     * Sets the move manager.
     *
     * @param manager the EnemiesMoveManager instance.
     */
    void setMoveManager(EnemiesMoveManager manager);
}

