package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.model.powerups.api.PowerUpType;

/**
 * Marker interface for players that can expose active power-up effects.
 */
public interface PlayerPoweredUp {

    void addPowerUpEffect(PowerUpType type);

    void removePowerUpEffect(PowerUpType type);

    boolean hasPowerUpEffect(PowerUpType type);
}
