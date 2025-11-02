package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PushBoxCommand implements CollisionCommand {

    private static final double FORCE = 15;
    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final Vector2D playerNormal;

    /**
     *
     * @param box          the PushBox
     * @param penetration  collision penetration
     * @param playerNormal the normal from the player's perspective
     */
    public PushBoxCommand(final PushBox box, final Vector2D playerNormal) {
        this.box = box;
        this.playerNormal = playerNormal;
    }

    @Override
    public void execute() {
        if (playerNormal.y() < VERTICAL_THRESHOLD) {
            return;
        } else {
            if (Math.abs(playerNormal.x()) > Math.abs(playerNormal.y())) {

                final double direction = -Math.signum(playerNormal.x());
                final double push = FORCE * direction;
                box.push(new Vector2D(push, 0));
            }
        }
    }
}