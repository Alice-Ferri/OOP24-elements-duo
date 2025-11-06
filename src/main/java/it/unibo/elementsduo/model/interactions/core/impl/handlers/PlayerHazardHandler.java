package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.events.impl.EventManager;
import it.unibo.elementsduo.model.interactions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api.Hazard;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between a {@link Player} and a {@link Hazard}.
 * 
 * <p>
 * Determines whether the player should die based on their type and the
 * type of hazard they collide with. For example, Fireboy dies in water pools,
 * Watergirl dies in lava pools, and both die in green pools.
 */
public final class PlayerHazardHandler extends AbstractInteractionHandler<Player, Hazard> {

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
    public void handleInteraction(final Player player, final Hazard hazard, final CollisionInformations c,
            final InteractionResponse.Builder builder) {
        final boolean immuneByClass = player.isImmuneTo(hazard.getHazardType());

        builder.addLogicCommand(() -> {
            if (immuneByClass) {
                return;
            }
            eventManager.dispatch(new PlayerDiedEvent());
        });
    }
}
