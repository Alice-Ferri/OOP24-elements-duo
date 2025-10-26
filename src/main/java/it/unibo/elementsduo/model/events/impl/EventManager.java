package it.unibo.elementsduo.model.events.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.api.EventListener;

public class EventManager {
    private List<EventListener> Listeners = new ArrayList<>();

    public void notify(Event event) {
        for (EventListener listener : Listeners) {
            listener.onEvent(event);
        }
    }
}
