package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.resources.Vector2D;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles player physics corrections after collisions.
 * Extracted from {@link AbstractPlayer} to keep logic modular and reusable.
 */
public class PhysicsCorrectionHandler {

    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private final Player player;

    public PhysicsCorrectionHandler(final Player player) {
        this.player = player;
    }

    /**
     * Corrects the player's position and velocity after a collision.
     *
     * @param penetration the overlap depth of the collision
     * @param normal      the collision normal vector
     * @param other       the other collidable object
     */
    public void correctPhysicsCollision(final double penetration, final Vector2D normal, final Collidable other) {
        if (penetration <= 0) {
            return;
        }

        if (other instanceof Wall && normal.y() < -0.5) {
            if (handleHorizontalOverlap((Wall) other)) {
                return;
            }
        }

        applyCorrection(normal, penetration);
        handleVertical(normal, other);
    }

    private boolean handleHorizontalOverlap(final Wall wall) {
        final HitBox playerHitBox = player.getHitBox();
        final HitBox wallHitBox = wall.getHitBox();

        final double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        final double overlapX = (playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth()) - Math.abs(dx);

        if (overlapX <= 0) {
            return false;
        }

        final Vector2D horizontalNormal = new Vector2D(dx > 0 ? 1 : -1, 0);
        final double depth = Math.max(overlapX - POSITION_SLOP, 0.0);
        final Vector2D correction = horizontalNormal.multiply(CORRECTION_PERCENT * depth);

        player.move(correction.x());

        final double velocityNormal = player.getVelocity().dot(horizontalNormal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(horizontalNormal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
            player.setVelocityY(newVelocity.y());
        }
        return true;
    }

    private void applyCorrection(final Vector2D normal, final double penetration) {
        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);

        player.move(correction.x());

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
            player.landOn(player.getY());
            player.setVelocityY(0);

            if (other instanceof PlatformImpl platform) {
                player.setVelocityY(platform.getVelocity().y());
            }
        } else if (normalY > 0.5) {
            player.stopJump(player.getY());
            player.setVelocityY(0);
        }
    }
}
