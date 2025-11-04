package it.unibo.elementsduo.controller.subcontroller.impl;

import java.awt.event.ActionListener;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.MenuPanel;

/**
 * Manages the logic for the menu screen.
 * It handles user input for starting a new game or loading a saved game.
 */
public final class HomeController implements Controller {

    private final MenuPanel view;
    private final ActionListener onStartListener;
    private final ActionListener onLoadListener;
    private final ActionListener onGuideListener;
    private final ActionListener onExitListener;

    /**
     * Constructs a new HomeController.
     *
     * @param controller The navigation controller.
     */
    public HomeController(final HomeNavigation controller) {
        this.view = new MenuPanel();

        this.onStartListener = e -> controller.startNewGame();
        this.onLoadListener = e -> controller.loadSavedGame();
        this.onGuideListener = e -> controller.gameGuide();
        this.onExitListener = e -> controller.quitGame();
    }

    @Override
    public void activate() {
        this.view.addButtonsListeners(onStartListener, onLoadListener, onGuideListener, onExitListener);
    }

    @Override
    public void deactivate() {
        this.view.removeButtonsListeners(onStartListener, onLoadListener, onGuideListener, onExitListener);
    }

    @Override
    @SuppressFBWarnings(value = "EI", 
                        justification = "Required to MainController to add it to the JFrame's card layout")
    public JPanel getPanel() {
        return this.view;
    }

}
