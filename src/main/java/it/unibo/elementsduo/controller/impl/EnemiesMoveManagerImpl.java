package it.unibo.elementsduo.controller.impl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.controller.api.EnemiesMoveManager;

public class EnemiesMoveManagerImpl implements EnemiesMoveManager{

    private Set<Obstacle> obstacles = new HashSet<>();

    public EnemiesMoveManagerImpl(final Set<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    @Override
    public void handleEdgeDetection(final Enemy enemy) {
        final HitBox edgeCheckHitBox = getNextStepHitBox(enemy.getHitBox(), enemy.getX(), enemy.getY(), enemy.getDirection());
        
        final boolean isEdge = this.obstacles.stream()
            .noneMatch(obstacle -> obstacle.getHitBox().intersects(edgeCheckHitBox));

        if (isEdge) {
             enemy.setDirection(); 
        }
    }

    private HitBox getNextStepHitBox(final HitBox hitbox, final double x, final double y, final double direction) {
        final double checkVerticalDistance = 0.05; 
        final double checkHorizontalOffset = 0.51; 
        
        return new HitBoxImpl(
            new Position(
               x + direction * checkHorizontalOffset, 
                y + hitbox.getHalfHeight()*2 + checkVerticalDistance
            ),
            0.1, 
            checkVerticalDistance
        );
    }
}
