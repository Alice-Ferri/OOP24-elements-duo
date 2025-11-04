package it.unibo.elementsduo.model.player.impl.handlers;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.powerups.api.PowerUpType;

/**
 * Manages the active power-ups of a player.
 */
public class PlayerPowerUpHandler {

    private final Set<PowerUpType> activePowerUps = new HashSet<>();

    /**
     * Adds a power-up to the set of active power-ups.
     *
     * @param powerUpType the type of power-up to add
     */
    public void add(final PowerUpType powerUpType) {
        this.activePowerUps.add(powerUpType);
    }

    /**
     * Removes a power-up from the set of active power-ups.
     *
     * @param powerUpType the type of power-up to remove
     */
    public void remove(final PowerUpType powerUpType) {
        this.activePowerUps.remove(powerUpType);
    }

    /**
     * Checks whether a specific power-up is currently active.
     *
     * @param powerUpType the type of power-up to check
     *
     * @return true} if the power-up is active, false otherwise
     */
    public boolean has(final PowerUpType powerUpType) {
        return this.activePowerUps.contains(powerUpType);
    }
}
