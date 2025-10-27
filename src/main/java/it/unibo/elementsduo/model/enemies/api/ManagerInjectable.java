package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;

/**
 * Interface for game entities that require the EnemiesMoveManager to be injected.
 */
public interface ManagerInjectable {
    
    /**
     * Sets the move manager dependency.
     * @param manager the EnemiesMoveManager instance.
     */
    void setMoveManager(EnemiesMoveManager manager);
}
