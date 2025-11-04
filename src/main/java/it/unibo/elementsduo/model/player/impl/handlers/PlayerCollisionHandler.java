package it.unibo.elementsduo.model.player.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.AbstractPlayer;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Responsible for adjusting the player's position and velocity when collisions occur.
 */
public class PlayerCollisionHandler {
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private final Player player;

    /**
     * Constructos of collision correction handler.
     *
     * @param player whose collisions will be managed
     */
    public PlayerCollisionHandler(final AbstractPlayer player) {
        this.player = player;
    }

    /**
     * Corrects the player's position and velocity after a collision.
     *
     * @param penetration the overlap depth of the collision
     *
     * @param normal the collision normal vector
     *
     * @param other whoever collides with
     */
    public void handleCollision(final double penetration, final Vector2D normal, final Collidable other) {
        if (penetration <= 0) {
            return;
        }

        if (other instanceof Wall wall && normal.y() < -0.5 && handleHorizontalOverlap(wall)) {
                return;
        }

        applyCorrection(normal, penetration);
        handleVertical(normal, other);
    }

    private boolean handleHorizontalOverlap(final Wall wall) {
        final var playerHitBox = this.player.getHitBox();
        final var wallHitBox = wall.getHitBox();
        final double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        final double overlapX = playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth() - Math.abs(dx);

        if (overlapX <= 0) {
            return false;
        }

        final Vector2D horizontalNormal = new Vector2D(dx > 0 ? 1 : -1, 0);
        final double depth = Math.max(overlapX - POSITION_SLOP, 0.0);
        final Vector2D correction = horizontalNormal.multiply(CORRECTION_PERCENT * depth);

        player.correctPosition(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(horizontalNormal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(horizontalNormal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
        }
        return true;
    }

    private void applyCorrection(final Vector2D normal, final double penetration) {
        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);

        player.correctPosition(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(normal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(normal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
            player.setVelocityY(newVelocity.y());
        }
    }

    private void handleVertical(final Vector2D normal, final Collidable other) {
        final double normalY = normal.y();

        if (normalY < -0.5) {
            player.setVelocityY(0);
            player.setOnGround();

            if (other instanceof PlatformImpl platform) {
                player.setVelocityY(platform.getVelocity().y());
            }
        } else if (normalY > 0.5) {
            player.setVelocityY(0);
        }
    }
}
