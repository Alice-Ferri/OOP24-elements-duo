package it.unibo.elementsduo.controller.enemiescontroller.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.interactions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of the EnemiesMoveManager interface.
 * Handles the logic for detecting edges for enemies
 * and reversing their direction.
 */
public final class EnemiesMoveManagerImpl implements EnemiesMoveManager {

    private static final double CHECK_DROP_DISTANCE = 0.05;
    private static final double CHECK_FORWARD_OFFSET = 0.51;
    private static final double CHECK_HITBOX_WIDTH = 0.2;

    private final Set<Obstacle> obstacles;

    /**
     * Constructs a new EnemiesMoveManagerImpl.
     *
     * @param obstacles the set of obstacles in the current level.
     */
    public EnemiesMoveManagerImpl(final Set<Obstacle> obstacles) {
        this.obstacles = new HashSet<>(obstacles);
    }

    /**
     * {@inheritDoc}
     * If the enemy is at the edge of a platform or near a gap, its direction is
     * reversed.
     */
    @Override
    public void handleEdgeDetection(final Enemy enemy) {
        final HitBox edgeCheckHitBox = createEdgeCheckHitBox(enemy);

        final boolean isEdge = this.obstacles.stream()
                .noneMatch(obstacle -> obstacle.getHitBox().intersects(edgeCheckHitBox));

        if (isEdge) {
            enemy.setDirection();
        }
    }

    /**
     * Calculates the HitBox for checking the edge of a platform.
     * The box is created slightly forward (in the direction of travel) and
     * slightly below the enemy's expected foot position.
     *
     * @param enemy the enemy instance.
     * @return the HitBox used for edge detection.
     */
    private HitBox createEdgeCheckHitBox(final Enemy enemy) {
        final double x = enemy.getX();
        final double y = enemy.getY();
        final double direction = enemy.getDirection();
        final double enemyHeight = enemy.getHitBox().getHalfHeight() * 2;

        final double checkBoxCenterX = x + direction * CHECK_FORWARD_OFFSET;

        final double yCenterPosition = y + enemyHeight + CHECK_DROP_DISTANCE;

        return new HitBoxImpl(
                new Position(checkBoxCenterX, yCenterPosition), CHECK_HITBOX_WIDTH, CHECK_DROP_DISTANCE);
    }
}
