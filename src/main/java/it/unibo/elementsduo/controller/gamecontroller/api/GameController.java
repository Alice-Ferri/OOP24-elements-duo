package it.unibo.elementsduo.controller.gamecontroller.api;

import it.unibo.elementsduo.controller.subcontroller.api.Controller;

/**
 * Extends the base {@link Controller} interface with methods
 * specific to a running game loop (update logic and render graphics).
 */
public interface GameController extends Controller {

    /**
     * Updates the game state by one logic tick.
     * This includes moving entities, checking collisions, and handling input.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    void update(double deltaTime);

    /**
     * Renders the current game state to the view.
     * Repaint the game panel.
     */
    void render();

}
