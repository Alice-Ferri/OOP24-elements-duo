package it.unibo.elementsduo.controller.impl;

import java.util.Set;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.Obstacle;
import it.unibo.elementsduo.resources.Position;


public class EnemiesMoveManagerImpl {

    private final Set<Obstacle> obstacles;

    public EnemiesMoveManager(final Set<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    @override
    public void handleEdgeDetection(final Enemy enemy) {
        final HitBox edgeCheckHitBox = getNextStepHitBox(enemy.getHitBox(), enemy.getDirection());
        
        final boolean isEdge = this.obstacles.stream()
            .noneMatch(obstacle -> obstacle.getHitBox().intersects(edgeCheckHitBox));

        if (isEdge) {
             enemy.setDirection(); 
        }
    }

    private HitBox getNextStepHitBox(final HitBox hitbox, final double direction) {
        final double checkVerticalDistance = 0.05; 
        final double checkHorizontalOffset = 0.51; 
        
        return new HitBoxImpl(
            new Position(
                hitbox.getPos().x() + direction * checkHorizontalOffset, 
                hitbox.getPos().y() + hitbox.getHeight() + checkVerticalDistance
            ),
            0.1, 
            checkVerticalDistance
        );
    }
}