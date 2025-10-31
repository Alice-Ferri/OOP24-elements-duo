package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.controller.inputController.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.inputController.impl.InputState;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Abstract base class implementing common behavior for all {@link Player}
 * types.
 */
public abstract class AbstractPlayer implements Player {

    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;
    private static final double GRAVITY = 9.8;
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private double x;
    private double y;
    private Vector2D velocity = new Vector2D(0, 0);
    private boolean onGround = true;
    private boolean onExit;

    /**
     * Constructs with the starting position.
     *
     * @param startPos the initial position of the player
     */
    protected AbstractPlayer(final Position startPos) {
        this.x = startPos.x();
        this.y = startPos.y();
    }

    /**
     * {@inheritDoc}
     *
     * @return the current x-coordinate of the player
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} if the player is on the exit, {@code false} otherwise
     */
    @Override
    public boolean isOnExit() {
        return this.onExit;
    }

    /**
     * {@inheritDoc}
     *
     * @return the current y-coordinate of the player
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     *
     * @return the vertical velocity component of the player
     */
    @Override
    public double getVelocityY() {
        return this.velocity.y();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} if the player is on the ground, {@code false} otherwise
     */
    @Override
    public boolean isOnGround() {
        return this.onGround;
    }

    /**
     * {@inheritDoc}
     *
     * @param dx the horizontal movement delta
     */
    @Override
    public void move(final double dx) {
        this.velocity = new Vector2D(dx, this.velocity.y());
        this.x += this.velocity.x();
    }

    /**
     * {@inheritDoc}
     *
     * @param gravity the gravity acceleration to apply
     */
    @Override
    public void applyGravity(final double gravity) {
        if (!this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, gravity));
            this.y += this.velocity.y();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param strength the upward jump force
     */
    @Override
    public void jump(final double strength) {
        if (this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, -strength));
            this.onGround = false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param groundY the vertical coordinate of the ground surface
     */
    @Override
    public void landOn(final double groundY) {
        this.y = groundY;
        this.velocity = new Vector2D(this.velocity.x(), 0);
        this.onGround = true;
    }

    /**
     * {@inheritDoc}
     *
     * @param ceilingY the vertical coordinate of the ceiling
     */
    @Override
    public void stopJump(final double ceilingY) {
        this.y = ceilingY;
        this.velocity = new Vector2D(this.velocity.x(), 0);
    }

    /** {@inheritDoc} */
    @Override
    public void setAirborne() {
        this.onGround = false;
    }

    /**
     * {@inheritDoc}
     *
     * @param vx the horizontal velocity component
     */
    @Override
    public void setVelocityX(final double vx) {
        this.velocity = new Vector2D(vx, this.velocity.y());
    }

    /**
     * {@inheritDoc}
     *
     * @param vy the vertical velocity component
     */
    @Override
    public void setVelocityY(final double vy) {
        this.velocity = new Vector2D(this.velocity.x(), vy);
    }

    /**
     * {@inheritDoc}
     *
     * @param cond {@code true} if the player is on the exit, {@code false}
     *             otherwise
     */
    @Override
    public void setOnExit(final boolean cond) {
        this.onExit = cond;
    }

    /**
     * Updates the player's state based on input and physics.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     * @param input     the current input controller providing player actions
     */
    @Override
    public void update(final double deltaTime, final InputController input) {
        handleInput(input);

        this.onGround = false;

        if (!this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, GRAVITY * deltaTime));
        }

        this.x += this.velocity.x() * deltaTime;
        this.y += this.velocity.y() * deltaTime;
    }

    private void handleInput(final InputController controller) {
        final PlayerType type = this.getPlayerType();

        InputState state = controller.getInputState();

        final boolean left = state.isActionPressed(type, InputState.Action.LEFT);
        final boolean right = state.isActionPressed(type, InputState.Action.RIGHT);

        if (left == right) {
            this.setVelocityX(0);
        } else if (left) {
            this.setVelocityX(-RUN_SPEED);
        } else {
            this.setVelocityX(RUN_SPEED);
        }

        if (state.isActionPressed(type, InputState.Action.JUMP)) {
            this.jump(JUMP_STRENGTH);
            controller.markJumpHandled(type);
        }
    }

    /**
     * Returns the player's current hitbox used for collision detection.
     * The hitbox is centered on the player's position.
     *
     * @return the player's current {@link HitBox} instance
     */
    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(
                new Position(this.x, this.y),
                getHeight(),
                getWidth());
    }

    /**
     * Corrects the player's position and velocity after a collision.
     *
     * @param penetration the overlap depth of the collision
     * @param normal      the collision normal vector
     */
    @Override
    public void correctPhysicsCollision(final double penetration, final Vector2D normal, final Collidable other) {
        if (penetration <= 0)
            return;

        if (other instanceof Wall && normal.y() < -0.5) {
            if (handleHorizontalOverlap((Wall) other)) {
                return;
            }
        }

        applyCorrection(normal, penetration);

        handleVertical(normal, other);
    }

    private boolean handleHorizontalOverlap(Wall wall) {
        HitBox playerHitBox = this.getHitBox();
        HitBox wallHitBox = wall.getHitBox();
        double dx = playerHitBox.getCenter().x() - wallHitBox.getCenter().x();
        double overlapX = (playerHitBox.getHalfWidth() + wallHitBox.getHalfWidth()) - Math.abs(dx);

        if (overlapX <= 0) {
            return false;
        }

        Vector2D horizontalNormal = new Vector2D(dx > 0 ? 1 : -1, 0);
        double depth = Math.max(overlapX - POSITION_SLOP, 0.0);
        Vector2D correction = horizontalNormal.multiply(CORRECTION_PERCENT * depth);

        this.x += correction.x();
        this.y += correction.y();

        double velocityNormal = this.velocity.dot(horizontalNormal);
        if (velocityNormal < 0) {
            this.velocity = this.velocity.subtract(horizontalNormal.multiply(velocityNormal));
        }
        return true;
    }

    private void applyCorrection(Vector2D normal, double penetration) {
        double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        Vector2D correction = normal.multiply(CORRECTION_PERCENT * depth);

        this.x += correction.x();
        this.y += correction.y();

        double velocityNormal = this.velocity.dot(normal);
        if (velocityNormal < 0) {
            this.velocity = this.velocity.subtract(normal.multiply(velocityNormal));
        }
    }

    private void handleVertical(Vector2D normal, Collidable other) {
        double normalY = normal.y();

        if (normalY < -0.5) {
            this.onGround = true;
            this.velocity = new Vector2D(this.velocity.x(), 0);

            if (other instanceof PlatformImpl platform) {
                this.setVelocityY(platform.getVelocity().y());
            }
        } else if (normalY > 0.5) { // hitting ceiling
            this.velocity = new Vector2D(this.velocity.x(), 0);
        }
    }
}
