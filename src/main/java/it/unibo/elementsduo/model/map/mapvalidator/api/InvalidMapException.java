package it.unibo.elementsduo.model.map.mapvalidator.api;

public class InvalidMapException extends Exception{
    private static final long serialVersionUID = 1L;

    public InvalidMapException(final String message) {
        super(message);
    }
}
