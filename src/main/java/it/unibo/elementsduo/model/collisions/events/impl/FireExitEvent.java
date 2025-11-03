package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when the {@link Player} of type Fireboy reaches a fire exit.
 * 
 * <p>
 * Used to signal that Fireboy has successfully reached his corresponding goal
 * area.
 */
public record FireExitEvent() implements Event {
}
