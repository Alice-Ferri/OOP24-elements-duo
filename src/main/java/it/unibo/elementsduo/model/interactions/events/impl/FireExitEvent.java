package it.unibo.elementsduo.model.interactions.events.impl;

import it.unibo.elementsduo.model.interactions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when the {@link Player} of type Fireboy reaches a fire exit.
 */
public record FireExitEvent() implements Event {
}
