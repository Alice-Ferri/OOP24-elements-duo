package it.unibo.elementsduo.model.events.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.api.EventListener;

public class EventManager {
    private Map<Class<? extends Event>, List<EventListener>> Listeners = new HashMap<>();

    public void subscribe(Class<? extends Event> eventType, EventListener listener) {
        this.Listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void notify(Event event) {
        final List<EventListener> evListeners = this.Listeners.get(event.getClass());
        if (evListeners != null) {
            for (EventListener listener : evListeners) {
                listener.onEvent(event);
            }
        }
    }
}
