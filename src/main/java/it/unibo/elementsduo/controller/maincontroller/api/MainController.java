package it.unibo.elementsduo.controller.maincontroller.api;

/**
 * Defines the main controller for starting the application.
 */
public interface MainController {

    /**
     * Starts the application, initializing and displaying the first view
     * (e.g., the main menu).
     */
    void startApp();

    /**
     * Shows a joption panel with the error.
     *
     * @param error A String with the error occoured
     */
    void handleException(String error);
}
