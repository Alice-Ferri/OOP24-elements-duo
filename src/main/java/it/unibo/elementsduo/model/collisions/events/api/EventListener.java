package it.unibo.elementsduo.model.collisions.events.api;

/**
 * Defines a listener that reacts to {@link Event} occurrences in the game’s
 * event system.
 * 
 * <p>
 * Classes implementing this interface can subscribe to specific event types and
 * handle them when triggered.
 * 
 * </p>
 */
public interface EventListener {

    /**
     * Called when an {@link Event} is triggered.
     *
     * @param event the event that occurred
     */
    void onEvent(Event event);
}
