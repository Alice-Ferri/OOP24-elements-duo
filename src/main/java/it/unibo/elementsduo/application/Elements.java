package it.unibo.elementsduo.application;

import it.unibo.elementsduo.controller.maincontroller.impl.MainControllerImpl;

/**
 * Main class for the Elements Duo application.
 */
public final class Elements {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private Elements() { }

    /**
     * The main method.
     * It creates the MainController and starts the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(final String[] args) {
        new MainControllerImpl().startApp();
    }
}
