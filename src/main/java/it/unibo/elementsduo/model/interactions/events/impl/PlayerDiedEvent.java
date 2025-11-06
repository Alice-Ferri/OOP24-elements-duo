package it.unibo.elementsduo.model.interactions.events.impl;

import it.unibo.elementsduo.model.interactions.events.api.Event;

/**
 * Event representing that an enemy has been destroyed or removed from the game.
 * 
 */
public record PlayerDiedEvent() implements Event {
}
