package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;

public final class EnemyKill extends AbstractTimedPowerUpStrategy {

    @Override
    protected void onStart(final Player player, final EventManager eventManager) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.addPowerUpEffect(PowerUpType.ENEMY_KILL);
        }
    }

    @Override
    protected void onEnd(final Player player, final EventManager eventManager) {
        if (player instanceof PlayerPoweredUp aware) {
            aware.removePowerUpEffect(PowerUpType.ENEMY_KILL);
        }
    }
}
