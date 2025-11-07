package it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl;

import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.TriggerListener;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents a moving platform that can be activated or deactivated
 * by triggers such as levers or buttons.
 *
 * <p>
 * The platform moves back and forth between two points {@code a} and {@code b}
 * at a fixed speed while active. It also implements {@link TriggerListener},
 * allowing it to react to external trigger events.
 * </p>
 *
 */
public final class PlatformImpl extends AbstractInteractiveObstacle implements TriggerListener, Updatable {

    private static final double HALF_WIDTH = 0.5;

    private static final double HALF_HEIGHT = 0.5;

    private static final double SPEED = 1.0;

    private final Position a;

    private final Position b;

    private Position pos;

    private Vector2D velocity = Vector2D.ZERO;

    private boolean forward = true;

    private boolean active;

    /**
     * Creates a new moving platform between the given start and end positions.
     *
     * @param pos the initial position of the platform
     * @param a   the first target position
     * @param b   the second target position
     */
    public PlatformImpl(final Position pos, final Position a, final Position b) {
        super(pos, HALF_WIDTH, HALF_HEIGHT);
        this.a = a;
        this.b = b;
        this.pos = pos;
    }

    /**
     * Updates the platform's position
     *
     * <p>
     * The platform oscillates between {@code a} and {@code b} while active.
     * </p>
     *
     * @param delta the time step for the update, in seconds
     */
    @Override
    public void update(final double delta) {
        if (!this.active) {
            return;
        }

        final Position target = forward ? b : a;
        final Vector2D dir = pos.vectorTo(target).normalize();

        velocity = dir.multiply(SPEED);
        pos = pos.add(velocity.multiply(delta));
        setCenter(pos);

        if (pos.distanceBetween(target) < SPEED * delta) {
            forward = !forward;
            this.velocity = Vector2D.ZERO;
        }
    }

    /**
     * @return is active or not.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * set activate true.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Sets the 'active' flag to false and immediately resets its velocity to zero.
     */
    public void deactivate() {
        this.active = false;
        this.velocity = Vector2D.ZERO;
    }

    /**
     * Returns the current velocity vector of the platform.
     *
     * @return the velocity vector
     */
    public Vector2D getVelocity() {
        return this.velocity;
    }

    /**
     * Called when a linked trigger changes state.
     *
     * <p>
     * Activates or deactivates the platform depending on the given state.
     * </p>
     *
     * @param state {@code true} to activate the platform, {@code false} to
     *              deactivate it
     */
    @Override
    public void onTriggered(final boolean state) {
        if (state) {
            this.activate();
        } else {
            this.deactivate();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return the collision layer associated with this platform
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.PLATFORM;
    }
}
