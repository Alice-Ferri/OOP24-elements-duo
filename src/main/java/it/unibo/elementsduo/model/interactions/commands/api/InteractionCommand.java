package it.unibo.elementsduo.model.interactions.commands.api;

/**
 * Represents a command that defines an action to be executed.
 * as a result of an interaction event.
 * This interface allows encapsulating logic that should run
 * after an interaction is detected.
 */
@FunctionalInterface
public interface InteractionCommand {

    /**
     * Executes the resolution.
     *
     */
    void execute();
}
