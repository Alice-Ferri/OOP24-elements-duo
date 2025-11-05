package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pushable;
import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents a dynamic, interactive box that can be pushed and reacts
 * to physics forces such as gravity and friction.
 *
 * <p>
 * The {@code PushBox} moves horizontally when pushed and is affected by
 * ground or air friction. It also responds to collisions and corrects its
 * position to prevent penetration.
 * </p>
 */
public final class PushBox extends AbstractInteractiveObstacle implements Pushable, Movable, Updatable {

    private static final double GROUND_FRICTION = 0.75;

    private static final double AIR_FRICTION = 0.98;

    private static final double GRAVITY = 9.8;

    private static final double MIN_VELOCITY_X = 0.02;

    private static final double MAX_FALL_SPEED = 15;

    private static final double HALF_WIDTH = 0.5;

    private static final double HALF_HEIGHT = 0.5;

    private static final double COLLISION_TOLERANCE = 0.001;

    private static final double COLLISION_CORRECTION_FACTOR = 0.8;

    private static final double VERTICAL_THRESHOLD = 0.5;

    private static final double HORIZONTAL_THRESHOLD = 0.5;

    private Vector2D velocity = Vector2D.ZERO;

    private boolean onGround;

    /**
     * Creates a pushable box centered at the given position.
     *
     * @param center the initial center position of the box
     */
    public PushBox(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /**
     * Applies a horizontal push to this {@code PushBox}.
     *
     * <p>
     * Only the X component of the given vector is used and replaces the current
     * horizontal velocity. Vertical motion is unaffected.
     * </p>
     *
     * @param v the push vector to apply
     */
    @Override
    public void push(final Vector2D v) {
        this.velocity = new Vector2D(v.x(), this.velocity.y());
    }

    /** {@inheritDoc} */
    @Override
    public void move(final Vector2D delta) {
        setCenter(getCenter().add(delta));
    }

    /**
     * Updates the box's physics state by applying gravity, friction,
     * and velocity integration.
     *
     * @param dt the time step in seconds
     */
    @Override
    public void update(final double dt) {
        final boolean wasOnGround = this.onGround;

        if (!wasOnGround) {
            this.velocity = this.velocity.add(new Vector2D(0, GRAVITY * dt));
        }

        move(this.velocity.multiply(dt));

        if (wasOnGround) {
            this.velocity = new Vector2D(this.velocity.x() * GROUND_FRICTION, this.velocity.y());
        } else {
            this.velocity = new Vector2D(this.velocity.x() * AIR_FRICTION, this.velocity.y());
        }

        if (Math.abs(this.velocity.x()) < MIN_VELOCITY_X) {
            this.velocity = new Vector2D(0, this.velocity.y());
        }

        if (this.velocity.y() > MAX_FALL_SPEED) {
            this.velocity = new Vector2D(this.velocity.x(), MAX_FALL_SPEED);
        }

        this.onGround = false;
    }

    /**
     * Sets whether the box is currently on the ground.
     *
     * @param state {@code true} if the box is on the ground, {@code false}
     *              otherwise
     */
    public void setOnGround(final boolean state) {
        this.onGround = state;
    }

    /**
     * Returns whether the box is currently resting on the ground.
     *
     * @return {@code true} if the box is on the ground
     */
    public boolean isOnGround() {
        return this.onGround;
    }

    /**
     * Returns the current velocity vector of the box.
     *
     * @return the current velocity
     */
    public Vector2D getVelocity() {
        return this.velocity;
    }

    /**
     * Corrects the box’s position and velocity in response to a collision.
     *
     * <p>
     * Adjusts the box’s position based on the penetration depth and collision
     * normal, and modifies its velocity accordingly to prevent overlap and
     * simulate realistic collision response.
     * </p>
     *
     * @param penetration the penetration depth between colliding bodies
     * @param normal      the collision normal vector
     * @param other       the other collidable object involved in the collision
     */
    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal, final Collidable other) {
        final double depth = Math.max(penetration - COLLISION_TOLERANCE, 0);
        final Vector2D correction = normal.multiply(depth * COLLISION_CORRECTION_FACTOR);

        move(correction);

        final double vn = this.velocity.dot(normal);
        if (vn < 0) {
            this.velocity = this.velocity.subtract(normal.multiply(vn));
        }

        if (normal.y() < -VERTICAL_THRESHOLD) {
            this.onGround = true;
            this.velocity = new Vector2D(this.velocity.x(), 0);
        } else if (normal.y() > VERTICAL_THRESHOLD) {
            this.velocity = new Vector2D(this.velocity.x(), 0);
        } else if (Math.abs(normal.x()) > HORIZONTAL_THRESHOLD && !(other instanceof Player)) {
            this.velocity = new Vector2D(0, this.velocity.y());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return the collision layer associated with this pushable box
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.PUSHABLE;
    }
}
