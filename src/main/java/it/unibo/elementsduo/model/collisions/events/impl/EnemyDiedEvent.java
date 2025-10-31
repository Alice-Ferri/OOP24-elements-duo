package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Enemy;

/**
 * Event triggered when an {@link Enemy} is defeated or destroyed.
 * <p>
 * This event is used to notify listeners that an enemy has been eliminated
 * from the game world, allowing related logic (e.g., score updates or
 * animation triggers) to execute.
 *
 * @param enemy the {@link Enemy} that has died
 */
public record EnemyDiedEvent(Enemy enemy) implements Event {
}
