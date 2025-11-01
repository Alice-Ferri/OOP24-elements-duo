package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles player collision resolution and response.
 */
public class PlayerCollisionHandler {

    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private final AbstractPlayer player;

    public PlayerCollisionHandler(final AbstractPlayer player) {
        this.player = player;
    }

    public void handleCollision(final double penetration, final Vector2D normal, final Collidable other) {
        if (penetration <= 0) return;

        if (other instanceof Wall && normal.y() < -0.5 && handleHorizontalOverlap((Wall) other)) {
            return;
        }

        applyCorrection(normal, penetration);
        handleVertical(normal, other);
    }

    private boolean handleHorizontalOverlap(final Wall wall) {
        final HitBox playerHitBox = player.getHitBox();
        final HitBox wallHitBox = wall.getHitBox();
        final double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        final double overlapX = (playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth()) - Math.abs(dx);

        if (overlapX <= 0) return false;

        final Vector2D normal = new Vector2D(dx > 0 ? 1 : -1, 0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * Math.max(overlapX - POSITION_SLOP, 0.0));

        player.setVelocityX(player.getX() + correction.x());
        player.setVelocityY(player.getY() + correction.y());

        final double velocityNormal = player.getVelocity().dot(normal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(normal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
            player.setVelocityY(newVelocity.y());
        }

        return true;
    }

    private void applyCorrection(final Vector2D normal, final double penetration) {
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * Math.max(penetration - POSITION_SLOP, 0.0));
        player.setVelocityX(player.getX() + correction.x());
        player.setVelocityY(player.getY() + correction.y());

        final double velocityNormal = player.getVelocity().dot(normal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(normal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
            player.setVelocityY(newVelocity.y());
        }
    }

    private void handleVertical(final Vector2D normal, final Collidable other) {
        final double ny = normal.y();

        if (ny < -0.5) { // Landed
            player.setOnGround();
            player.setVelocityY(0);

            if (other instanceof PlatformImpl platform) {
                player.setVelocityY(platform.getVelocity().y());
            }
        } else if (ny > 0.5) { // Hit ceiling
            player.setVelocityY(0);
        }
    }
}
