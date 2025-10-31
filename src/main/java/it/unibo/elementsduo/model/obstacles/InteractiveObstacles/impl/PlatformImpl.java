package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
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
 */
public class PlatformImpl extends AbstractInteractiveObstacle implements Triggerable, TriggerListener {

    /** The platform's half width. */
    private static final double HALF_WIDTH = 0.5;

    /** The platform's half height. */
    private static final double HALF_HEIGHT = 0.5;

    /** The first target position. */
    private final Position a;

    /** The second target position. */
    private final Position b;

    /** The platform's movement speed. */
    private double speed = 1.0;

    /** The platform's current position. */
    private Position pos;

    /** The platform's velocity vector. */
    private Vector2D velocity = Vector2D.ZERO;

    /** Whether the platform is currently moving forward. */
    private boolean forward = true;

    /** Whether the platform is currently active. */
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
     * Updates the platform's position based on its current direction,
     * speed, and activation state.
     *
     * <p>
     * The platform oscillates between {@code a} and {@code b} while active.
     * </p>
     *
     * @param delta the time step for the update, in seconds
     */
    public void update(final double delta) {
        if (!this.active) {
            return;
        }

        final Position target = forward ? b : a;
        final Vector2D dir = pos.vectorTo(target).normalize();

        velocity = dir.multiply(speed);
        pos = pos.add(velocity.multiply(delta));
        setCenter(pos);

        if (pos.distanceBetween(target) < speed * delta) {
            forward = !forward;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /** {@inheritDoc} */
    @Override
    public void activate() {
        this.active = true;
    }

    /** {@inheritDoc} */
    @Override
    public void deactivate() {
        this.active = false;
    }

    /** {@inheritDoc} */
    @Override
    public void toggle() {
        this.active = !this.active;
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
}
