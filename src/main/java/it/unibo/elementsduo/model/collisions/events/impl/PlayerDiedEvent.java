package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

public record PlayerDiedEvent(Player player) implements Event {
}
