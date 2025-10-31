package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;

/**
 * Represents a player entity in the game.
 * A {@code Player} is both {@link Collidable} and {@link Movable}.
 */
public interface Player extends Collidable, Movable, GameEntity {

    /** Default player width. */
    double DEFAULT_WIDTH = 0.8;

    /** Default player height. */
    double DEFAULT_HEIGHT = 1.0;

    /**
     * Returns the current horizontal position of the player.
     *
     * @return the x-coordinate of the player
     */
    double getX();

    /**
     * Returns the current vertical position of the player.
     *
     * @return the y-coordinate of the player
     */
    double getY();

    /**
     * Returns the current vertical velocity of the player.
     *
     * @return the vertical component of the player's velocity
     */
    double getVelocityY();

    /**
     * Returns whether the player is currently on the ground.
     *
     * @return {@code true} if the player is on the ground, {@code false} otherwise
     */
    boolean isOnGround();

    /**
     * Returns whether the player is currently on the exit.
     *
     * @return {@code true} if the player is on the exit, {@code false} otherwise
     */
    boolean isOnExit();

    /**
     * Moves the player horizontally by the given delta value.
     *
     * @param dx the horizontal movement delta
     */
    void move(double dx);

    /**
     * Applies a gravitational force to the player.
     *
     * @param gravity the gravity acceleration to apply
     */
    void applyGravity(double gravity);

    /**
     * Makes the player jump with the given strength.
     *
     * @param strength the upward jump force
     */
    void jump(double strength);

    /**
     * Lands the player on a solid surface.
     *
     * @param groundY the vertical coordinate of the ground surface
     */
    void landOn(double groundY);

    /**
     * Stops the player's upward movement due to a ceiling collision.
     *
     * @param ceilingY the vertical coordinate of the ceiling
     */
    void stopJump(double ceilingY);

    /**
     * Marks the player as airborne (no longer touching the ground).
     */
    void setAirborne();

    /**
     * Sets the horizontal velocity of the player.
     *
     * @param vx the new horizontal velocity
     */
    void setVelocityX(double vx);

    /**
     * Sets the vertical velocity of the player.
     *
     * @param vy the new vertical velocity
     */
    void setVelocityY(double vy);

    /**
     * Sets whether the player is currently on the exit area.
     *
     * @param condition {@code true} if the player is on the exit, otherwise {@code false}
     */
    void setOnExit(boolean condition);

    /**
     * Updates the state of the player.
     *
     * @param deltaTime the time elapsed since the last update
     * @param inputController the input controller managing player input
     */
    void update(double deltaTime, InputController inputController);

    /**
     * Returns the specific player type (Fireboy or Watergirl).
     *
     * @return the type of this player
     */
    PlayerType getPlayerType();

    /**
     * Returns the width of the player's bounding box.
     *
     * @return the player's width
     */
    default double getWidth() {
        return DEFAULT_WIDTH;
    }

    /**
     * Returns the height of the player's bounding box.
     *
     * @return the player's height
     */
    default double getHeight() {
        return DEFAULT_HEIGHT;
    }
}
