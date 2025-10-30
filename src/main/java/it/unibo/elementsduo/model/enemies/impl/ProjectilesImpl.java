package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Implementation of the Projectiles interface, representing a moving entity
 * fired by an enemy.
 */
public final class ProjectilesImpl implements Projectiles {

    private static final double SPEED = 5.0;
    private static final double PROJECTILE_SIZE = 0.25;
    private double x;
    private double y;
    private final int direction;
    private boolean alive;
    private Vector2D velocity = new Vector2D(0, 0);

    /**
     * Constructs a new projectile with an initial position and direction.
     *
     * @param pos       the starting position of the projectile.
     * @param direction the initial direction.
     */
    public ProjectilesImpl(final Position pos, final int direction) {
        this.x = pos.x();
        this.y = pos.y();
        this.direction = direction;
        this.alive = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
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

    @Override
    public void update(final double deltaTime) {
        this.velocity = new Vector2D(this.direction * SPEED, 0);
        this.x += this.velocity.x() * deltaTime;
    }

    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal) {
        
        if (penetration <= 0) {
            return;
        }
        final double correctionPerc = 0.8;
        final double positionSlop = 0.001;
        final double depth = Math.max(penetration - positionSlop, 0.0);
        final Vector2D correction = normal.multiply(correctionPerc * depth);
        this.x += correction.x();
        this.y += correction.y();

        final double velocityNormal = this.velocity.dot(normal);
        if (velocityNormal < 0) {
            this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
        }
    }

    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(new Position(this.x, this.y), PROJECTILE_SIZE, PROJECTILE_SIZE);
    }

    @Override
    public void deactivate() {
        this.alive = false;
    }
}
