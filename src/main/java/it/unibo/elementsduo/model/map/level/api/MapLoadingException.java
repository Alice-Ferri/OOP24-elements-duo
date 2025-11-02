package it.unibo.elementsduo.model.map.level.api;

/**
 * An unchecked exception thrown when an error occurs during map loading.
 * Indicates a file-not-found issue.
 */
public final class MapLoadingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new MapLoadingException with the specified detail message.
     *
     * @param message the detail message.
     */
    public MapLoadingException(final String message) {
        super(message);
    }

    /**
     * Constructs a new MapLoadingException with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause   the original cause of the exception (e.g., an IOException).
     */
    public MapLoadingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
