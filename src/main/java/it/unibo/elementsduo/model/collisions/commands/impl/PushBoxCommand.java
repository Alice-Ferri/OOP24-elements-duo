package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PushBoxCommand implements CollisionCommand {

    private static final double FORCE = 15;
    private static final double VERTICAL_THRESHOLD = -0.5;

    private final PushBox box;
    private final double penetration;
    private final Vector2D normal;
    private final boolean playerFirst;
    private final Collidable other;

    public PushBoxCommand(final PushBox box, final double penetration, final Vector2D normal,
            final boolean playerFirst, Collidable other) {
        this.box = box;
        this.penetration = penetration;
        this.normal = normal;
        this.playerFirst = playerFirst;
        this.other = other;
    }

    @Override
    public void execute() {
        if (penetration <= 0) {
            return;
        }

        if (!(other instanceof Player)) {
            return;
        }
        final Player player = (Player) other;

        final Vector2D playerNormal = playerFirst ? normal : normal.multiply(-1);
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