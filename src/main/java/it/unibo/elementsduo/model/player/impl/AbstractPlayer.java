package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerPoweredUp;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerCollisionHandler;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerInputHandler;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerPhysicsHandler;
import it.unibo.elementsduo.model.player.impl.handlers.PlayerPowerUpHandler;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Abstract base class implementing common behavior for all {@link Player}
 * types.
 */
public abstract class AbstractPlayer implements Player, PlayerPoweredUp {

    private double x;
    private double y;
    private Vector2D velocity = new Vector2D(0, 0);
    private boolean onGround = true;
    private boolean onExit;

    private final PlayerCollisionHandler collisionHandler = new PlayerCollisionHandler(this);
    private final PlayerPowerUpHandler powerUpHandler = new PlayerPowerUpHandler();
    private final PlayerPhysicsHandler physicsHandler = new PlayerPhysicsHandler();
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(physicsHandler);

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
    public void setOnGround() {
        this.onGround = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAirborne() {
        this.onGround = false;
    }

    /**
     * Corrects the position of the player.
     *
     * @param dx to correct by x position
     *
     * @param dy to correct by y position
     */
    @Override
    public void correctPosition(final double dx, final double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * {@inheritDoc} 
     *
     * @param powerUpType to add to the set
    */
    @Override
    public void addPowerUpEffect(final PowerUpType powerUpType) {
        powerUpHandler.add(powerUpType);
    }

    /**
     * {@inheritDoc} 
     *
     * @param powerUpType to remove to the set
    */
    @Override
    public void removePowerUpEffect(final PowerUpType powerUpType) {
        powerUpHandler.remove(powerUpType);
    }

    /**
     * Updates the player's state based on input and physics.
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     * @param input     the current input controller providing player actions
     */
    @Override
    public void update(final double deltaTime, final InputController input) {
        inputHandler.handleInput(this, input);

        this.onGround = false;
        physicsHandler.updatePosition(this, deltaTime);
    }

    /**
     * {@inheritDoc} 
     *
     * @param powerUpType to ask if is present in the power up set
     *
     * @return true if the power up is present, false otherwise
    */
    @Override
    public boolean hasPowerUpEffect(final PowerUpType powerUpType) {
        return powerUpHandler.has(powerUpType);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.PLAYER;
    }
}
