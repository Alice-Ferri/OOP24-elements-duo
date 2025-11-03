package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * A {@link CollisionCommand} that applies a horizontal push to a
 * {@link PushBox}
 * when a {@link Player} collides with it.
 * <p>
 * The command checks the collision direction and player's velocity to determine
 * whether the box should be pushed.
 * </p>
 * <p>
 * This class is final and not designed for extension.
 * </p>
 */
public final class PushBoxCommand implements CollisionCommand {

    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final Vector2D playerNormal;
    private final Player player;

    /**
     * Creates a new {@code PushBoxCommand}.
     *
     * @param box          the {@link PushBox} affected by the collision
     * @param player       the {@link Player} colliding with the box
     * @param playerNormal the collision normal vector from the player's perspective
     */
    public PushBoxCommand(final PushBox box, final Player player, final Vector2D playerNormal) {
        this.box = box;
        this.playerNormal = playerNormal;
        this.player = player;
    }

    /**
     * Executes the push command.
     * <p>
     * Applies a horizontal force to the box if the player collides from the side
     * and moves in the same direction as the collision normal.
     * </p>
     */
    @Override
    public void execute() {
        if (playerNormal.y() < VERTICAL_THRESHOLD) {
            return;
        }

        final double playerVelX = player.getVelocity().x();
        final double direction = -Math.signum(playerNormal.x());

        if (direction == 0.0) {
            return;
        }

        if (Math.signum(playerVelX) != direction) {
            return;
        }

        final double pushVelocity = direction * Math.abs(playerVelX);
        box.push(new Vector2D(pushVelocity, 0));
    }
}
