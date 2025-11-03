package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.powerups.api.PowerUpEffect;

/**
 * Template method skeleton for time-based power-up strategies.
 *
 * <p>
 * The duration countdown and refresh logic is provided here, leaving subclasses
 * to implement only the specific enable/disable behaviour.
 * </p>
 */
public abstract class AbstractTimedPowerUpStrategy implements PowerUpEffect {

    private double remaining;

    @Override
    public final void onActivated(final Player player, final EventManager eventManager, final double duration) {
        this.remaining = duration;
        this.onStart(player, eventManager);
    }

    @Override
    public final void onRefreshed(final Player player, final EventManager eventManager, final double duration) {
        this.remaining = duration;
    }

    @Override
    public final boolean onUpdate(final Player player, final EventManager eventManager, final double deltaTime) {
        this.remaining -= deltaTime;
        return this.remaining > 0;
    }

    @Override
    public final void onExpired(final Player player, final EventManager eventManager) {
        this.onEnd(player, eventManager);
    }

    protected abstract void onStart(Player player, EventManager eventManager);

    protected abstract void onEnd(Player player, EventManager eventManager);

}
