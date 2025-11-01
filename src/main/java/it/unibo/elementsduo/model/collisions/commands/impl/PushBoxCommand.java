package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PushBoxCommand implements CollisionCommand {

    private static final double FORCE = 15;
    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final double penetration;
    private final Vector2D playerNormal;
    private final Player player;

    /**
     *
     * @param box          la PushBox
     * @param penetration  la penetrazione
     * @param playerNormal la normale calcolata dal punto di vista del player
     * @param player       il Player
     */
    public PushBoxCommand(final PushBox box, final double penetration, final Vector2D playerNormal,
            final Player player) {
        this.box = box;
        this.penetration = penetration;
        this.playerNormal = playerNormal;
        this.player = player;
    }

    @Override
    public void execute() {
        if (penetration <= 0) {
            return;
        }

        final Vector2D boxNormal = playerNormal.multiply(-1);

        if (Math.abs(playerNormal.x()) > Math.abs(playerNormal.y())) {

            final double correction = penetration / 2.0;
            player.correctPhysicsCollision(correction, playerNormal, box);
            box.correctPhysicsCollision(correction, boxNormal, player);

            final double direction = -Math.signum(playerNormal.x());
            final double push = penetration * FORCE * direction;
            box.push(new Vector2D(push, 0));

        } else {
            if (playerNormal.y() < VERTICAL_THRESHOLD) {
                player.correctPhysicsCollision(penetration, playerNormal, box);
            } else {
                final double correction = penetration / 2.0;
                player.correctPhysicsCollision(correction, playerNormal, box);
                box.correctPhysicsCollision(correction, boxNormal, player);
            }
        }
    }
}