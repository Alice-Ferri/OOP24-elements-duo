package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.events.impl.EventManager;
import it.unibo.elementsduo.model.interactions.events.impl.FireExitEvent;
import it.unibo.elementsduo.model.interactions.events.impl.WaterExitEvent;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.impl.ExitType;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Handles collisions between a {@link Player} and an {@link ExitZone}.
 * 
 * <p>
 * This handler determines whether the player has reached the correct exit
 * based on their {@link PlayerType}, and triggers the appropriate exit event.
 */
public final class PlayerExitHandler extends AbstractInteractionHandler<Player, ExitZone> {

    private final EventManager eventManager;

    /**
     * Creates a new {@code PlayerExitHandler}.
     *
     * @param eventManager the event manager used to dispatch exit events
     */
    public PlayerExitHandler(final EventManager eventManager) {
        super(Player.class, ExitZone.class);
        this.eventManager = eventManager;
    }

    /**
     * Handles a collision between a {@link Player} and an {@link ExitZone}.
     * 
     * <p>
     * If the player reaches the correct exit (Fireboy → FireExit, Watergirl →
     * WaterExit),
     * the corresponding exit event is triggered. The event is only fired once per
     * entry.
     *
     * @param player        the player involved in the collision
     * @param exitZone      the exit zone involved in the collision
     * @param collisionInfo the collision information
     * @param builder       the collision response builder used to queue logic
     *                      commands
     */
    @Override
    public void handleInteraction(final Player player, final ExitZone exitZone,
            final CollisionInformations collisionInfo,
            final InteractionResponse.Builder builder) {

        final boolean correctExit = player.getRequiredExitType() == exitZone.getExitType();

        if (correctExit && !player.isOnExit()) {
            builder.addLogicCommand(() -> {
                player.setOnExit(true);
                exitZone.activate();
                if (exitZone.getExitType() == ExitType.FIRE_EXIT) {
                    this.eventManager.dispatch(new FireExitEvent());
                } else if (exitZone.getExitType() == ExitType.WATER_EXIT) {
                    this.eventManager.dispatch(new WaterExitEvent());
                }
            });
        }
    }
}
