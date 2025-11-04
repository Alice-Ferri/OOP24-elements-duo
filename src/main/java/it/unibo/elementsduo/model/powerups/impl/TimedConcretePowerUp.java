package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public final class TimedConcretePowerUp extends AbstractTimedPowerUp {

    private final PowerUpType type;

    public TimedConcretePowerUp(final PowerUpType type) {
        this.type = type;
    }

    @Override
    protected void onStart(final Player player) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.addPowerUpEffect(this.type);
        }
    }

    @Override
    protected void onEnd(final Player player) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.removePowerUpEffect(this.type);
        }
    }
}
