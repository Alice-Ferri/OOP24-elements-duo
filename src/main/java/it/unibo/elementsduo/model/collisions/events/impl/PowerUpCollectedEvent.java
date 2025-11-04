package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.powerups.api.PowerUpEffect;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public record PowerUpCollectedEvent(PlayerType playerType, PowerUpType type, double duration,
        PowerUpEffect effect) implements Event {
}
