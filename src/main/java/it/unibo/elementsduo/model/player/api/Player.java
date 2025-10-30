package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;

/**
 * Represents a player entity in the game.
 * 
 * A {@code Player} is both {@link Collidable} and {@link Movable}, and defines
 * basic physical behavior such as movement, jumping, and collision handling.
 * 
 */

public interface Player extends Collidable, Movable, GameEntity {

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
     * Marks the player as airborne.
     */
    void setAirborne();

    /*
     *  Set velocity on x.
     */
    void setVelocityX(double vx);

    /*
     * Set the condition on exit.
     */
    void setOnExit(boolean condition);

    /*
     * Updating the state of the player.
     */
    void update(double deltaTime, InputController inputController);

    /**
     * Returns the specific player type.
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
        return 0.8;
    }

    /**
     * Returns the height of the player's bounding box.
     *
     * @return the player's height
     */
    default double getHeight() {
        return 1.0;
    }
}
