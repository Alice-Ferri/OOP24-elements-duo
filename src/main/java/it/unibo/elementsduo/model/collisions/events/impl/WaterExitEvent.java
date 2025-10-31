package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when the {@link Player} of type Watergirl reaches a water
 * exit.
 * 
 * <p>
 * Used to signal that Watergirl has successfully reached her corresponding goal
 * area.
 *
 * @param player the player who reached the water exit
 */
public record WaterExitEvent(Player player) implements Event {
}
