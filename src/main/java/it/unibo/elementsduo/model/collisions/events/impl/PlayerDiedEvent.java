package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when a {@link Player} dies.
 * 
 * <p>
 * Used to notify that a player has perished due to hazards, enemies, or other
 * fatal interactions.
 *
 * @param player the player who died
 */
public record PlayerDiedEvent(Player player) implements Event {
}
