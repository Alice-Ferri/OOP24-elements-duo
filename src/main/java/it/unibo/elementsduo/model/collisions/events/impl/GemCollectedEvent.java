package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.gem.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when a {@link Player} collects a {@link Gem}.
 * 
 * <p>
 * Used to notify that a player has successfully collected a gem in the game
 * world.
 *
 * @param player the player who collected the gem
 * @param gem    the gem that was collected
 */
public record GemCollectedEvent(Player player, Gem gem) implements Event {
}
