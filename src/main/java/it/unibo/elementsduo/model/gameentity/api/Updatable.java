package it.unibo.elementsduo.model.gameentity.api;

/**
 * Represents a game object that can be updated over time.
 * This is used for entities that need to handle movement,
 * physics, or animation logic each frame.
 */
@FunctionalInterface
public interface Updatable {

    /**
     * Updates the state of the object.
     *
     * @param deltaTime The time elapsed (in seconds) since the last update.
     */
    void update(double deltaTime);

}
