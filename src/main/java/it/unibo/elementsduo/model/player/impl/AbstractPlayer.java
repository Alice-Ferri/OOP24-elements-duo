package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.controller.inputController.impl.InputState;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
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

    private double x;
    private double y;
    private Vector2D velocity = new Vector2D(0, 0);
    private boolean onGround = true;
    private boolean onExit;
    
    private final PlayerCollisionHandler playerCollisionHandler = new PlayerCollisionHandler(this);

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
     * @return the current x-coordinate of the player
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnGround(){
        this.onGround = true;
    }

    /**
     * Corrects the position of the player.
     *
     * @param dx
     *
     * @param dy
     */
    @Override
    public void correctPosition(final double dx, final double dy) {
        this.x += dx;
        this.y += dy;
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
     * @param ceilingY the vertical coordinate of the ceiling
     */
    @Override
    public void stopJump(final double ceilingY) {
        this.y = ceilingY;
        this.velocity = new Vector2D(this.velocity.x(), 0);
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

    /** {@inheritDoc} */
    @Override
    public void setAirborne() {
        this.onGround = false;
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

        final InputState state = controller.getInputState();

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
        playerCollisionHandler.handleCollision(penetration, normal, other);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.PLAYER;
    }
}
