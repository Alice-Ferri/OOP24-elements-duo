package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.controller.inputController.api.InputController;
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
    
    private final PlayerCollisionHandler collisionHandler = new PlayerCollisionHandler(this);
    private final PlayerInputHandler inputHandler = new PlayerInputHandler();

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
    public Vector2D getVelocity() {
        return this.velocity;
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
     * @return {@code true} if the player is on the exit, {@code false} otherwise
     */
    @Override
    public boolean isOnExit() {
        return this.onExit;
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
     * {@inheritDoc}
     *
     * @return {@code true} if the player is on the ground, {@code false} otherwise
     */
    @Override
    public boolean isOnGround() {
        return this.onGround;
    }

    /** {@inheritDoc} */
    @Override
    public void setOnGround() {
        this.onGround = true;
    }

    /** {@inheritDoc} */
    @Override
    public void setAirborne() {
        this.onGround = false;
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

    /**
     * Updates the player's state based on input and physics.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     * @param input     the current input controller providing player actions
     */
    public void update(final double deltaTime, final InputController input) {

        final Vector2D inputVelocity = inputHandler.handleInput(input, getPlayerType(), this.onGround);

        this.setVelocityX(inputVelocity.x());

        if (inputVelocity.y() != 0) {
            this.jump(JUMP_STRENGTH);
        }

        if (!this.onGround) {
            this.velocity = this.velocity.add(new Vector2D(0, GRAVITY * deltaTime));
        }

        this.x += this.velocity.x() * deltaTime;
        this.y += this.velocity.y() * deltaTime;
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
        collisionHandler.handleCollision(penetration, normal, other);
    }
}
