package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PushBoxCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles collisions between a {@link Player} and a {@link PushBox}.
 * 
 * <p>
 * This handler generates a {@link PushBoxCommand} that applies a force
 * to the box when the player pushes it during a collision.
 */
public final class PushBoxHandler extends AbstractCollisionHandler<Player, PushBox> {

    /**
     * Creates a new {@code PushBoxHandler} to manage player–push box interactions.
     */
    public PushBoxHandler() {
        super(Player.class, PushBox.class);
    }

    /**
     * Handles the collision between a {@link Player} and a {@link PushBox}.
     * 
     * <p>
     * When a player collides with a pushable box, a {@link PushBoxCommand} is added
     * to the physics response builder to simulate the push effect.
     *
     * @param player  the player involved in the collision
     * @param box     the pushable box involved in the collision
     * @param c       the collision information
     * @param builder the collision response builder used to queue physics commands
     */
    @Override
    public void handleCollision(final Player player, final PushBox box, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        builder.addPhysicsCommand(
                new PushBoxCommand(box, c.getPenetration(), c.getNormal(), c.getObjectA() instanceof Player));
    }
}
