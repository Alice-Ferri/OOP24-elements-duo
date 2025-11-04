package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.powerups.api.PowerUpEffect;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public record PowerUpCollectedEvent(Player player, PowerUpType type, double duration,
                PowerUpEffect effect) implements Event {
}
