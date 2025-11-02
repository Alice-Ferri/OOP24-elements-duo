package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PushBoxCommand implements CollisionCommand {

    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final Vector2D playerNormal;
    private final Player player;

    /**
     *
     * @param box          the PushBox
     * @param penetration  collision penetration
     * @param playerNormal the normal from the player's perspective
     */
    public PushBoxCommand(final PushBox box, final Player player, final Vector2D playerNormal) {
        this.box = box;
        this.playerNormal = playerNormal;
        this.player = player;
    }

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