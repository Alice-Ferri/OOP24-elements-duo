package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.WaterPool;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.model.powerups.impl.PowerUpManager;

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
        final boolean immuneByClass = player.isImmuneTo(hazard.getHazardType());

        builder.addLogicCommand(() -> {
            final boolean immuneByPowerUp = player instanceof PlayerPoweredUp aware
                    && aware.hasPowerUpEffect(PowerUpType.HAZARD_IMMUNITY);
            if (immuneByClass || immuneByPowerUp) {
                return;
            }
            hazard.getEffect().apply(player, eventManager);
        });
    }
}
