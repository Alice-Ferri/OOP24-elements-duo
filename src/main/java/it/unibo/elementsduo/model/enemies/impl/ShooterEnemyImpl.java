package it.unibo.elementsduo.model.enemies.impl;

import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;
import it.unibo.elementsduo.utils.Position;
import it.unibo.elementsduo.utils.Vector2D;

/**
* Implementation of a shooter enemy, an enemy that moves laterally and fires projectiles.
*/
public final class ShooterEnemyImpl implements Enemy {

private static final double SPEED = 0.02;
private static final int MAX_COOLDOWN = 90;

private double shootCooldown;
protected Vector2D velocity = new Vector2D(0, 0);
private boolean alive;
private double x;
private final double y;
private int direction = 1;
private final EnemiesMoveManager moveManager;

/**
* @param pos the starting position.
*/
    public ShooterEnemyImpl(final Position pos,final EnemiesMoveManager moveManager) {
        this.x = pos.x();
        this.y = pos.y();
        this.alive = true;
        this.moveManager=moveManager;
}


    /**
    * {@inheritDoc}
    */
    @Override
    public Optional<Projectiles> attack() {
        if (this.shootCooldown <= 0) {
        this.shootCooldown = MAX_COOLDOWN;
        final Position pos = new Position((int) (this.x) + this.direction, (int) (this.y));
        return Optional.of(new ProjectilesImpl(pos, this.direction));
    }
    return Optional.empty();
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isAlive() {
    return this.alive;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void setDirection() {
    this.direction *= -1;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public double getX() {
    return this.x;  
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public double getY() {
    return this.y;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public double getDirection() {
    return this.direction;
    }

    @Override
    public void update(double deltaTime) { 
        
        this.moveManager.handleEdgeDetection(this);
        
        this.velocity = new Vector2D(this.direction * SPEED, 0);
        this.x += this.velocity.x() * deltaTime;

    }

    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal) {

    final double POSITION_SLOP = 0.001;
    final double CORRECTION_PERCENT = 0.8;
    if (penetration <= 0) {
        return;
    }

    final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
    final Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);
    this.x += correction.x();
    this.y += correction.y();

    final double velocityNormal = this.velocity.dot(normal);
    
    if (velocityNormal < 0) {
        this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
    }
    }

@Override
    public HitBox getHitBox() {
        return new HitBoxImpl(new Position(this.x, this.y), 1, 1);
    }

}

