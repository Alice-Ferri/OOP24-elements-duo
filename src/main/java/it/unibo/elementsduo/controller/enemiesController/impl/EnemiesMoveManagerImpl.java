package it.unibo.elementsduo.controller.enemiesController.impl;

import java.util.Set;

import it.unibo.elementsduo.controller.enemiesController.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the EnemiesMoveManager interface.
 * Handles the logic for detecting edges for enemies 
 * and reversing their direction.
 */
public final class EnemiesMoveManagerImpl implements EnemiesMoveManager { 

    private static final double EDGE_CHECK_VERTICAL_DISTANCE = 0.05; 
    private static final double EDGE_CHECK_HORIZONTAL_OFFSET = 0.51; 
    private static final double EDGE_CHECK_HALF_WIDTH = 0.1; 

    private final Set<obstacle> obstacles;

    /**
     * Constructs a new EnemiesMoveManagerImpl.
     *
     * @param obstacles the set of obstacles in the current level.
     */
    public EnemiesMoveManagerImpl(final Set<obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * {@inheritDoc}
     * Handles edge detection for the given enemy. If the enemy is at the edge 
     * of a platform or near a gap, its direction is reversed.
     *
     * @param enemy the enemy instance to check.
     */
    @Override
    public void handleEdgeDetection(final Enemy enemy) {
        final HitBox edgeCheckHitBox = getNextStepHitBox(enemy.getHitBox(), enemy.getX(), enemy.getY(), enemy.getDirection());

        final boolean isEdge = this.obstacles.stream()
            .noneMatch(obstacle -> obstacle.getHitBox().intersects(edgeCheckHitBox));

        if (isEdge) {
             enemy.setDirection(); 
        }
    }

    /**
     * Calculates the HitBox for checking the edge of a platform.
     * This HitBox extends slightly forward and drops slightly below the enemy's feet.
     *
     * @param hitbox the enemy's current HitBox.
     * @param x the enemy's current X position.
     * @param y the enemy's current Y position.
     * @param direction the enemy's current movement direction (-1 or 1).
     *
     * @return the HitBox used for edge detection.
     */
    private HitBox getNextStepHitBox(final HitBox hitbox, final double x, final double y, final double direction) {

        final double checkVerticalDistance = EDGE_CHECK_VERTICAL_DISTANCE; 
        final double checkHorizontalOffset = EDGE_CHECK_HORIZONTAL_OFFSET; 

        return new HitBoxImpl(
            new Position(
               x + direction * checkHorizontalOffset, 
                y + hitbox.getHalfHeight() * 2 + checkVerticalDistance
            ),
            EDGE_CHECK_HALF_WIDTH, 
            checkVerticalDistance
        );
    }
}

