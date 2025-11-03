package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles collisions between a {@link Player} and a {@link PlatformImpl}.
 *
 * <p>
 * This handler synchronizes the player's vertical velocity with the platform’s
 * movement when the player is standing on it.
 * </p>
 */
public final class PlayerPlatformHandler extends AbstractCollisionHandler<Player, PlatformImpl> {

    /**
     * Threshold to determine whether the player is standing on top of the platform.
     */
    private static final double VERTICAL_THRESHOLD = -0.5;

    /**
     * Creates a new {@code PlayerPlatformHandler} to manage player–platform
     * interactions.
     */
    public PlayerPlatformHandler() {
        super(Player.class, PlatformImpl.class);
    }

    /**
     * Handles a collision between a {@link Player} and a {@link PlatformImpl}.
     *
     * <p>
     * If the player is standing on the platform (based on collision normal),
     * the player’s vertical velocity is synchronized with the platform’s velocity.
     * </p>
     *
     * @param player   the player involved in the collision
     * @param platform the moving platform involved in the collision
     * @param c        the collision information
     * @param builder  the collision response builder used to enqueue logic commands
     */
    @Override
    public void handleCollision(final Player player, final PlatformImpl platform,
            final CollisionInformations c, final CollisionResponse.Builder builder) {

        final Vector2D playerNormal = getNormalFromPerspective(player, c);

        if (playerNormal.y() < VERTICAL_THRESHOLD) {
            builder.addLogicCommand(() -> player.setVelocityY(platform.getVelocity().y()));
        }
    }
}
