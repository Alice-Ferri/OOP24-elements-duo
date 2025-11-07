package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.events.impl.EventManager;
import it.unibo.elementsduo.model.interactions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles interactions between a {@link Player} and {@link Projectiles}.
 * 
 * <p>
 * When a projectile hits a player, this handler triggers a
 * {@link PlayerDiedEvent}
 * through the {@link EventManager}.
 */
public final class PlayerProjectileHandler extends AbstractInteractionHandler<Player, Projectiles> {

    private final EventManager eventManager;

    /**
     * Creates a new {@code PlayerProjectileHandler} that dispatches death events
     * when a player is hit by a projectile.
     *
     * @param eventManager the event manager used to dispatch player death events
     */
    public PlayerProjectileHandler(final EventManager eventManager) {
        super(Player.class, Projectiles.class);
        this.eventManager = eventManager;
    }

    /**
     * Handles a interaction between a {@link Player} and a {@link Projectiles}.
     * 
     * <p>
     * When a projectile hits the player, a {@link PlayerDiedEvent} is triggered.
     *
     * @param player     the player involved in the collision
     * @param projectile the projectile involved in the collision
     * @param c          the collision information
     * @param builder    the collision response builder used to queue logic commands
     */
    @Override
    public void handleInteraction(final Player player, final Projectiles projectile,
            final CollisionInformations c, final InteractionResponse.Builder builder) {
        builder.addLogicCommand(() -> this.eventManager.dispatch(new PlayerDiedEvent()));
    }
}
