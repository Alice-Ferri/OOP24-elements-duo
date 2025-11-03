package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

/**
 * Power-up strategy providing temporary immunity from any hazard.
 */
public final class HazardImmunity extends AbstractTimedPowerUpStrategy {

    @Override
    protected void onStart(final Player player, final EventManager eventManager) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.addPowerUpEffect(PowerUpType.HAZARD_IMMUNITY);
        }
    }

    @Override
    protected void onEnd(final Player player, final EventManager eventManager) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.removePowerUpEffect(PowerUpType.HAZARD_IMMUNITY);
        }
    }
}
