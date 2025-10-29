package it.unibo.elementsduo.model.enemies.impl;

import java.util.Optional;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Standard enemy that moves laterally in the level and inflicts damage when the player touches it.
 */
public final class ShooterEnemyImpl implements Enemy {

    private static final double SPEED = 2.0;
    private static final double MAX_COOLDOWN = 3.0;


    private boolean alive;
    private double x;
    private double y;
    private int direction = 1;

    private Vector2D velocity = new Vector2D(0, 0);
    private EnemiesMoveManager moveManager;
    private double shootCooldown;

    /**
     * Constructor for the classic enemy.
     * @param pos the starting position.
     */
    public ShooterEnemyImpl(final Position pos) {
        this.x = pos.x();
        this.y = pos.y();
        this.alive = true;
        this.velocity = new Vector2D(this.direction * SPEED, 0); 
        
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Projectiles> attack() {
        if (this.shootCooldown <= 0) {
            this.shootCooldown = MAX_COOLDOWN;
            final double spawnOffset = 0.5;

        final Position pos = new Position(
            this.x + this.direction * spawnOffset, 
            this.y
        ); 
        
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

    /**
     * {@inheritDoc}
     */
   @Override
    public void setDirection() {
        this.direction *= -1;
        this.velocity = new Vector2D(this.direction * SPEED, this.velocity.y());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double deltaTime) { 
        
        this.moveManager.handleEdgeDetection(this);
        
        this.velocity = new Vector2D(this.direction * SPEED, 0);
        this.x += this.velocity.x() * deltaTime;
        if (this.shootCooldown > 0) {
            this.shootCooldown -= deltaTime;
        } else {
            this.attack();
        }
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

        final double normalX = normal.x();
        if (Math.abs(normalX) > 0.5) { 
            this.setDirection(); 
        }

    }
    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(new Position(this.x, this.y), 1, 1);
    }

       @Override
    public void setMoveManager(final EnemiesMoveManager manager) {
        this.moveManager = manager;
    }

       @Override
       public void onEvent(Event event) {
        if (event instanceof EnemyDiedEvent) {
    
        }
       }

       @Override
       public void die() {
        this.alive=false;
       }
    
}



