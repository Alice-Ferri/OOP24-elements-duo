package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.obstacles.staticObstacles.gem.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between {@link Player} objects and {@link Gem} instances.
 * 
 * <p>
 * When a player collides with an active gem, the gem is collected and a
 * {@link GemCollectedEvent} is triggered through the {@link EventManager}.
 */
public final class GemCollisionsHandler extends AbstractCollisionHandler<Player, Gem> {

    private final EventManager eventManager;

    /**
     * Creates a new {@code GemCollisionsHandler} that notifies the given event
     * manager
     * when gems are collected.
     *
     * @param em the event manager used to dispatch {@link GemCollectedEvent}s
     */
    public GemCollisionsHandler(final EventManager em) {
        super(Player.class, Gem.class);
        this.eventManager = em;
    }

    /**
     * Handles the collision between a player and a gem.
     * 
     * <p>
     * If the gem is active, it is collected and an event is sent to notify
     * listeners.
     *
     * @param player  the player involved in the collision
     * @param gem     the gem involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue logic commands
     */
    @Override
    public void handleCollision(final Player player, final Gem gem, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        builder.addLogicCommand(() -> {
            if (gem.isActive()) {
                gem.collect();
                this.eventManager.notify(new GemCollectedEvent());
            }
        });
    }
}
