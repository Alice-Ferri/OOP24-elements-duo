package it.unibo.elementsduo.model.powerups.api;

import it.unibo.elementsduo.resources.Position;

public interface PowerUpFactory {
    PowerUp createPowerUp(PowerUpType type, Position pos);
}