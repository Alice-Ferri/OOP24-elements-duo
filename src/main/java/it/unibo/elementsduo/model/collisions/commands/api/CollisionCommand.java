package it.unibo.elementsduo.model.collisions.commands.api;

/**
 * Represents a command that defines an action to be executed
 * as a result of a collision event.
 * 
 * <p>
 * This interface allows encapsulating logic that should run
 * after a collision is detected.
 */
@FunctionalInterface
public interface CollisionCommand {

    /**
     * Executes the resolution
     *
     */
    void execute();
}
