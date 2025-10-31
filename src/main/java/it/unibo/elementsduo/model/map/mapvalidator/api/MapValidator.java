package it.unibo.elementsduo.model.map.mapvalidator.api;

import it.unibo.elementsduo.model.map.level.api.Level;

/**
 * A functional interface responsible for validating a game Level.
 * Implementations should check for structural integrity, reachability,
 * and other game logic rules.
 */
@FunctionalInterface
public interface MapValidator {

    /**
     * Validates the given game level.
     *
     * @param level the Level object to validate.
     * @throws InvalidMapException if the level fails any validation check.
     */
    void validate(Level level) throws InvalidMapException;

}
