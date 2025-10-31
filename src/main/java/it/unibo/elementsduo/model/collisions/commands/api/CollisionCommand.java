package it.unibo.elementsduo.model.collisions.commands.api;

/**
 * Represents a command that defines an action to be executed
 * as a result of a collision event.
 * 
 * <p>
 * This interface follows the Command design pattern and allows
 * encapsulating logic that should run after a collision is detected.
 */
@FunctionalInterface
public interface CollisionCommand {

    /**
     * Executes the collision-related action.
     * 
     * <p>
     * Implementations define what happens when the command is run,
     * such as applying physics corrections or triggering events.
     */
    void execute();
}
