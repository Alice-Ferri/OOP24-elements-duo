package it.unibo.elementsduo.model.enemies.impl;

import java.util.Optional;

import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Abstract class that implements the shared logic and state for all enemy
 * entities.
 * It centralizes physics, life state, and movement management, delegating
 * specific behaviors (like attacking) to subclasses.
 * Uses the Template Method Pattern for the update() method.
 */
public abstract class AbstractEnemy implements Enemy {

    private static final double SPEED = 0.8;
    private boolean alive;
    private double x;
    private double y;
    private int direction = 1;
    private Vector2D velocity;
    private EnemiesMoveManager moveManager;

    /**
     * Constructor for an abstract enemy.
     * Initializes position, life state, and initial velocity.
     *
     * @param pos the starting position.
     */
    public AbstractEnemy(final Position pos) {
        this.x = pos.x();
        this.y = pos.y();
        this.alive = true;
        this.velocity = new Vector2D(this.direction * SPEED, 0);
    }

    /**
     * {@inheritDoc}
     * Default implementation: the enemy does not attack.
     * Subclasses (e.g., Shooter) must override this method
     * to implement an attack.
     */
    @Override
    public Optional<Projectiles> attack() {
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
    public int getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     * Reverses the direction and updates the velocity vector.
     * Logic is common due to shared SPEED.
     */
    @Override
    public void setDirection() {
        this.direction *= -1;
        this.velocity = new Vector2D(this.direction * SPEED, this.velocity.y());
    }

    /**
     * {@inheritDoc}
     * This is the Template Method.
     * It defines the base update algorithm (movement) and
     * calls an abstract hook method for specific behaviors.
     *
     * @param deltaTime the time elapsed since the last update.
     */
    @Override
    public final void update(final double deltaTime) {
        if (this.moveManager == null) {
            throw new IllegalStateException("EnemiesMoveManager has not been injected in "
                    + this.getClass().getSimpleName());
        }
        this.moveManager.handleEdgeDetection(this);
        this.velocity = new Vector2D(this.direction * SPEED, 0);
        this.x += this.velocity.x() * deltaTime;

        this.updateSpecificBehavior(deltaTime);
    }

    /**
     * Abstract hook method for the Template Method Pattern.
     * Subclasses must implement this to define their specific
     * update logic (e.g., cooldown management).
     *
     * @param deltaTime the time elapsed since the last update.
     */
    protected abstract void updateSpecificBehavior(double deltaTime);

    /**
     * {@inheritDoc}
     * Common physics collision logic for all enemies.
     */
    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal, Collidable other) {

        if (penetration <= 0) {
            return;
        }

        final double correctionPercent = 0.8;
        final double positionSlop = 0.001;
        final double depth = Math.max(penetration - positionSlop, 0.0);
        final Vector2D correction = normal.multiply(correctionPercent * depth);

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

    /**
     * {@inheritDoc}
     * Common HitBox creation logic.
     */
    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(new Position(this.x, this.y), 1, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMoveManager(final EnemiesMoveManager manager) {
        this.moveManager = manager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void die() {
        this.alive = false;
    }
}
