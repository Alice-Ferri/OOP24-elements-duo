package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.model.powerups.api.PowerUpType;

/**
 * Marker interface for players that can expose active power-up effects.
 */
public interface PlayerPoweredUp {

    /**
     * Adds a power-up effect to the player.
     *
     * @param powerUpType the type of the power-up to add
     */
    void addPowerUpEffect(PowerUpType powerUpType);

    /**
     * Removes a power-up effect from the player.
     *
     * @param powerUpType the type of the power-up to remove
     */
    void removePowerUpEffect(PowerUpType powerUpType);

    /**
     * Checks whether a specific power-up effect is currently active on the player.
     *
     * @param powerUpType the type of the power-up to check
     *
     * @return true if the power-up is active, false otherwise
     */
    boolean hasPowerUpEffect(PowerUpType powerUpType);
}
