package it.unibo.elementsduo.model.map.mapvalidator.api;

/**
 * A checked exception thrown when a game level fails validation.
 * This indicates that a map file is malformed, unwinnable, or violates
 * game rules.
 */
public class InvalidMapException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InvalidMapException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidMapException(final String message) {
        super(message);
    }
}
