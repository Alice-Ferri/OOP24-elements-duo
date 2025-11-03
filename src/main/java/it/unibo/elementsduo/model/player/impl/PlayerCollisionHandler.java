package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.resources.Vector2D;

public class PlayerCollisionHandler {
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private final AbstractPlayer player;

    public PlayerCollisionHandler(final AbstractPlayer player) {
        this.player = player;
    }

    public void handleCollision(final double penetration, final Vector2D normal, final Collidable other) {
        if (penetration <= 0) {
            return;
        }

        if (other instanceof it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall wall && normal.y() < -0.5) {
            if (handleHorizontalOverlap(wall)) {
                return;
            }
        }

        applyCorrection(normal, penetration);
        handleVertical(normal, other);
    }

    private boolean handleHorizontalOverlap(final it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall wall) {
        final var playerHitBox = this.player.getHitBox();
        final var wallHitBox = wall.getHitBox();
        final double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        final double overlapX = (playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth()) - Math.abs(dx);

        if (overlapX <= 0) {
            return false;
        }

        final Vector2D horizontalNormal = new Vector2D(dx > 0 ? 1 : -1, 0);
        final double depth = Math.max(overlapX - POSITION_SLOP, 0.0);
        final Vector2D correction = horizontalNormal.multiply(CORRECTION_PERCENT * depth);

        player.correctPosition(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(horizontalNormal);
        if (velocityNormal < 0) {
            double newVx = player.getVelocity().x() - horizontalNormal.x() * velocityNormal;
            player.setVelocityX(newVx);
        }
        return true;
    }

    private void applyCorrection(final Vector2D normal, final double penetration) {
        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);

        player.correctPosition(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(normal);
        if (velocityNormal < 0) {
            double newVx = player.getVelocity().x() - normal.x() * velocityNormal;
            double newVy = player.getVelocity().y() - normal.y() * velocityNormal;
            player.setVelocityX(newVx);
            player.setVelocityY(newVy);
        }
    }

    private void handleVertical(final Vector2D normal, final Collidable other) {
        final double normalY = normal.y();

        if (normalY < -0.5) {
            player.setVelocityY(0);
            player.setOnGround();

            if (other instanceof it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl platform) {
                player.setVelocityY(platform.getVelocity().y());
            }
        } else if (normalY > 0.5) {
            player.setVelocityY(0);
        }
    }
}
