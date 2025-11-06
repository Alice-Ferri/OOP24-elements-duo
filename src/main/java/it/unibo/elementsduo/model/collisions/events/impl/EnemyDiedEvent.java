package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;

/**
 * Event representing that an enemy has been destroyed or removed from the game.
 * 
 */
public record EnemyDiedEvent() implements Event {
}
