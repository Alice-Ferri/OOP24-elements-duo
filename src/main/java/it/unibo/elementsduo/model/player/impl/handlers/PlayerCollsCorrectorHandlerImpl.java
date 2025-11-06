package it.unibo.elementsduo.model.player.impl.handlers;

import java.util.Optional;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.staticObstacles.solid.Wall;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.handlers.PlayerCollsCorrectorHandler;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Responsible for adjusting the player's position and velocity when collisions
 * occur.
 */
public class PlayerCollsCorrectorHandlerImpl implements PlayerCollsCorrectorHandler {
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    /**
     * Corrects the player's position and velocity after a collision.
     *
     * @param penetration the overlap depth of the collision
     *
     * @param normal      the collision normal vector
     *
     * @param other       whoever collides with
     */
    @Override
    public void handleCollision(final Player player, final double penetration, final Vector2D normal,
            final Collidable other) {

        Optional.of(penetration)
                .filter(p -> p > 0)
                .ifPresent(p -> {
                    if (other instanceof Wall wall && normal.y() < -0.5 && handleHorizontalOverlap(player, wall)) {
                        return;
                    }
                    applyCorrection(player, normal, p);
                    handleVertical(player, normal, other);
                });
    }

    private boolean handleHorizontalOverlap(final Player player, final Wall wall) {
        final var playerHitBox = player.getHitBox();
        final var wallHitBox = wall.getHitBox();
        final double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        final double overlapX = playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth() - Math.abs(dx);

        if (overlapX <= 0) {
            return false;
        }

        final Vector2D horizontalNormal = new Vector2D(dx > 0 ? 1 : -1, 0);
        final double depth = Math.max(overlapX - POSITION_SLOP, 0.0);
        final Vector2D correction = horizontalNormal.multiply(CORRECTION_PERCENT * depth);

        player.moveBy(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(horizontalNormal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(horizontalNormal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
        }
        return true;
    }

    private void applyCorrection(final Player player, final Vector2D normal, final double penetration) {
        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);

        player.moveBy(correction.x(), correction.y());

        final double velocityNormal = player.getVelocity().dot(normal);
        if (velocityNormal < 0) {
            final Vector2D newVelocity = player.getVelocity().subtract(normal.multiply(velocityNormal));
            player.setVelocityX(newVelocity.x());
            player.setVelocityY(newVelocity.y());
        }
    }

    private void handleVertical(final Player player, final Vector2D normal, final Collidable other) {
        final double normalY = normal.y();

        if (normalY < -0.5) {
            player.setVelocityY(0);
            player.setOnGround();

            Optional.of(other)
                    .filter(PlatformImpl.class::isInstance)
                    .map(PlatformImpl.class::cast)
                    .map(PlatformImpl::getVelocity)
                    .ifPresent(v -> player.setVelocityY(v.y()));
            return;
        }

        if (normalY > 0.5) {
            player.setVelocityY(0);
        }
    }
}
