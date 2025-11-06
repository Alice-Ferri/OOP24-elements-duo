package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import it.unibo.elementsduo.model.interactions.commands.impl.PushBoxCommand;
import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles collisions between a {@link Player} and a {@link PushBox}.
 * 
 * <p>
 * This handler generates a {@link PushBoxCommand} that applies a force
 * to the box when the player pushes it during a collision.
 */
public final class PlayerPushBoxHandler extends AbstractInteractionHandler<Player, PushBox> {

    /**
     * Creates a new {@code PushBoxHandler} to manage player–push box interactions.
     */
    public PlayerPushBoxHandler() {
        super(Player.class, PushBox.class);
    }

    /**
     * Handles the collision between a {@link Player} and a {@link PushBox}.
     * 
     * <p>
     * When a player collides with a pushable box, a {@link PushBoxCommand} is
     * added
     * to the physics response builder to simulate the push effect.
     *
     * @param player  the player involved in the collision
     * @param box     the pushable box involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue physics commands
     */
    @Override
    public void handleInteraction(final Player player, final PushBox box, final CollisionInformations c,
            final InteractionResponse.Builder builder) {

        final Vector2D playerNormal = getNormalFromPerspective(player, c);
        builder.addPhysicsCommand(
                new PushBoxCommand(box, player, playerNormal));
    }
}
