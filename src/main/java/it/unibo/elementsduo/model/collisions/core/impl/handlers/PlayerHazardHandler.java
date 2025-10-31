package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

/**
 * Handles collisions between a {@link Player} and a {@link Hazard}.
 * 
 * <p>
 * Determines whether the player should die based on their type and the
 * type of hazard they collide with. For example, Fireboy dies in water pools,
 * Watergirl dies in lava pools, and both die in green pools.
 */
public final class PlayerHazardHandler extends AbstractCollisionHandler<Player, Hazard> {

    private final EventManager eventManager;

    /**
     * Creates a new {@code PlayerHazardHandler} that uses the provided
     * {@link EventManager}
     * to notify player death events.
     *
     * @param eventManager the event manager used to dispatch player death events
     */
    public PlayerHazardHandler(final EventManager eventManager) {
        super(Player.class, Hazard.class);
        this.eventManager = eventManager;
    }

    /**
     * Handles collisions between players and hazards.
     * 
     * <p>
     * Determines if the player should die based on the hazard type and triggers
     * a {@link PlayerDiedEvent} when appropriate.
     *
     * @param player  the player involved in the collision
     * @param hazard  the hazard the player collided with
     * @param c       the collision information
     * @param builder the collision response builder used to queue logic commands
     */
    @Override
    public void handleCollision(final Player player, final Hazard hazard, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        if (player instanceof Fireboy && hazard instanceof waterPool) {
            builder.addLogicCommand(() -> eventManager.notify(new PlayerDiedEvent(player)));
        } else if (player instanceof Watergirl && hazard instanceof lavaPool) {
            builder.addLogicCommand(() -> eventManager.notify(new PlayerDiedEvent(player)));
        } else if (hazard instanceof greenPool) {
            builder.addLogicCommand(() -> eventManager.notify(new PlayerDiedEvent(player)));
        }
    }
}
