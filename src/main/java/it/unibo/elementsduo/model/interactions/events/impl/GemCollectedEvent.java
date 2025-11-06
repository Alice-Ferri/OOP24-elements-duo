package it.unibo.elementsduo.model.interactions.events.impl;

import it.unibo.elementsduo.model.interactions.events.api.Event;
import it.unibo.elementsduo.model.obstacles.staticobstacles.gem.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when a {@link Player} collects a {@link Gem}.
 * 
 * <p>
 * Used to notify that a player has successfully collected a gem in the game
 * world.
 */
public record GemCollectedEvent() implements Event {
}
