package it.unibo.elementsduo.controller.subcontroller.api;

import javax.swing.JPanel;

/**
 * Defines the basic contract for a sub-controller.
 * A controller manages a specific view (like a menu or a game level)
 * and its lifecycle (activation, deactivation).
 */
public interface Controller {

    /**
     * Activates the controller adding listeners to its view.
     */
    void activate();

    /**
     * Deactivates the controller by removing listeners from its view
     * to prevent memory leaks.
     */
    void deactivate();

    /**
     * Gets the view component managed by this controller.
     *
     * @return The {@link JPanel} associated with this controller.
     */
    JPanel getPanel();

}
