package it.unibo.elementsduo.model.interactions.events.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.elementsduo.model.interactions.events.api.Event;
import it.unibo.elementsduo.model.interactions.events.api.EventListener;

/**
 * Manages subscriptions and notifications of collision-related {@link Event}.
 * 
 * <p>
 * The {@code EventManager} allows listeners to subscribe to specific event
 * types
 * and ensures that all relevant listeners are notified when such events occur.
 */
public final class EventManager {

    private final Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

    /**
     * Subscribes an {@link EventListener} to a specific {@link Event} type.
     * 
     * <p>
     * When an event of the given type is triggered, the listener’s
     * {@link EventListener#onEvent(Event)} method will be called.
     *
     * @param eventType the class of the event to subscribe to
     * @param listener  the listener that will handle the event
     * 
     */
    public void subscribe(final Class<? extends Event> eventType, final EventListener listener) {
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(listener);
        this.listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(final Class<? extends Event> eventType, final EventListener listener) {
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(listener);

        this.listeners.computeIfPresent(eventType, (key, list) -> {
            list.remove(listener);
            return list.isEmpty() ? null : list;
        });
    }

    /**
     * Notifies all listeners subscribed to the given event type.
     * 
     * <p>
     * Invokes the {@link EventListener#onEvent(Event)} method for each listener
     * registered for the event’s class.
     *
     * @param event the event to dispatch to all relevant listeners
     * 
     */
    public void dispatch(final Event event) {
        final List<EventListener> evListeners = this.listeners.get(event.getClass());
        if (evListeners != null) {
            evListeners.forEach(listener -> listener.onEvent(event));
        }
    }
}
