package it.unibo.elementsduo.model.gameentity.api;

/**
 * Represents a game object that can be updated over time,
 * such as for movement or animation.
 */
@FunctionalInterface
public interface Updatable {
    /**
     * Updates the state of the object.
     *
     * @param deltatime The time elapsed since the last frame/update, in seconds.
     */
    void update(double deltatime);
}
