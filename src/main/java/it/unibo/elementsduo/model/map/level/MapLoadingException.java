package it.unibo.elementsduo.model.map.level;

public final class MapLoadingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MapLoadingException(final String message) {
        super(message);
    }

    public MapLoadingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}