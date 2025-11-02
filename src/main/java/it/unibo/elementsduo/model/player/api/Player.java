package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.ExitType;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents a player entity in the game.
 * A {@code Player} is both {@link Collidable} and {@link Movable}.
 */

public interface Player extends Movable, GameEntity {

    /** Default player dimensions. */
    final static double DEFAULT_WIDTH = 0.8;
    final static double DEFAULT_HEIGHT = 1.0;

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
    Vector2D getVelocity();

    /**
     *  Set velocity on x.
     * 
     *  @param vx velocity to set.
     */
    void setVelocityX(double vx);

    /**
     *  Set velocity on y.
     * 
     *  @param vy velocity to set.
     */
    void setVelocityY(double vy);

    /**
     * Returns whether the player is currently on the ground.
     *
     * @return {@code true} if the player is on the ground, {@code false} otherwise
     */
    boolean isOnGround();

    /**
     * Marks the player as airborne.
     */
    void setAirborne();

    /**
     * Returns whether the player is currently on the exit.
     *
     * @return {@code true} if the player is on the exit, {@code false} otherwise
     */
    boolean isOnExit();

    /**
     * Set the condition on exit.
     * 
     * @param condition to set on exit.
     */
    void setOnExit(boolean condition);

    /**
     * Moves the player horizontally by the delta value.
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
     * Updating the state of the player.
     * 
     * @param deltaTime the time elapsed since the last update.
     * 
     * @param inputController the controller that provides the player's input.
     */
    void update(double deltaTime, InputController inputController);

    /**
     * Returns the specific player type.
     *
     * @return the type of this player
     */
    PlayerType getPlayerType();

    /**
     * Returns the specific player's exit.
     *
     * @return the exit of this player
     */
    ExitType getRequiredExitType();

    /**
     * Returns the specific player's exit
     * 
     * @param hazardType obstacle to check
     *
     * @return the exit of this player
     */
    boolean isImmuneTo(HazardType hazardType);

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
