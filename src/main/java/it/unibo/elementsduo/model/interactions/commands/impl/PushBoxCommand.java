package it.unibo.elementsduo.model.interactions.commands.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.elementsduo.model.interactions.commands.api.InteractionCommand;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * A collision command that applies a pushing effect to a {@link PushBox}
 * when a {@link Player} collides with it from the side.
 */
public final class PushBoxCommand implements InteractionCommand {

    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final Vector2D playerNormal;
    private final Player player;

    /**
     * Creates a new {@code PushBoxCommand}.
     *
     * @param box          the pushable box involved in the collision
     * @param player       the player interacting with the box
     * @param playerNormal the collision normal from the player’s perspective
     */

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The command must keep direct references to the actors for its method execute()")
    public PushBoxCommand(final PushBox box, final Player player, final Vector2D playerNormal) {
        this.box = box;
        this.playerNormal = playerNormal;
        this.player = player;
    }

    /**
     * Executes the push effect if the collision direction and player movement
     * meet the conditions for applying a force to the box.
     */
    @Override
    public void execute() {
        if (this.playerNormal.y() < VERTICAL_THRESHOLD) {
            return;
        }

        final double playerVelX = this.player.getVelocity().x();
        final double direction = -Math.signum(this.playerNormal.x());

        if (direction == 0.0) {
            return;
        }

        if (Math.signum(playerVelX) != direction) {
            return;
        }

        final double pushVelocity = direction * Math.abs(playerVelX);
        this.box.push(new Vector2D(pushVelocity, 0));
    }
}
