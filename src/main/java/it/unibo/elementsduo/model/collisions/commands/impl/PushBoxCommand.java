package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PushBox;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Command that handles the collision logic and physics when the player pushes a
 * PushBox.
 * This class is final as it is not designed for extension.
 */
public final class PushBoxCommand implements CollisionCommand {

    private static final double FORCE = 15;

    private final PushBox box;
    private final double penetration;
    private final Vector2D normal;
    private final boolean playerFirst;

    /**
     * Constructs a new push command for a box.
     *
     * @param box         the PushBox involved in the collision
     * @param penetration the depth of the collision penetration
     * @param normal      the collision normal vector
     * @param playerFirst true if the player is the first body in the collision,
     *                    false otherwise
     */
    public PushBoxCommand(final PushBox box, final double penetration, final Vector2D normal,
            final boolean playerFirst) {
        this.box = box;
        this.penetration = penetration;
        this.normal = normal;
        this.playerFirst = playerFirst;
    }

    @Override
    public void execute() {
        if (penetration <= 0) {
            return;
        }

        final Vector2D playerNormal = playerFirst ? normal : normal.multiply(-1);

        if (Math.abs(normal.x()) > Math.abs(normal.y())) {
            final double direction = -Math.signum(normal.x());
            final double push = penetration * FORCE * direction;
            box.push(new Vector2D(push, 0));
            return;
        }

        final boolean playerOn = playerNormal.y() < 0;

        if (!playerOn) {
            return;
        }

        final Vector2D normalBox = playerNormal.multiply(-1);
        box.correctPhysicsCollision(0, normalBox);
    }
}
