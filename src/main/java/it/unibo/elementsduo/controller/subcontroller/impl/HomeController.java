package it.unibo.elementsduo.controller.subcontroller.impl;

import javax.swing.JPanel;
import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.MenuPanel;

/**
 * Manages the logic for the menu screen.
 * It handles user input for starting a new game or loading a saved game.
 */
public final class HomeController implements Controller {

    private final MenuPanel view;
    private final HomeNavigation controller;

    /**
     * Constructs a new HomeController.
     *
     * @param panel      The {@link MenuPanel} view.
     * @param controller The navigation controller.
     */
    public HomeController(final MenuPanel panel, final HomeNavigation controller) {
        this.view = panel;
        this.controller = controller;
    }

    @Override
    public void activate() {
        this.view.getStartButton().addActionListener(e -> controller.startNewGame());
        this.view.getLoadButton().addActionListener(e -> controller.loadSavedGame());
        this.view.getGuideButton().addActionListener(e -> controller.gameGuide());
    }

    @Override
    public void deactivate() {
        this.view.getStartButton().removeActionListener(null);
        this.view.getLoadButton().removeActionListener(null);
        this.view.getGuideButton().removeActionListener(null);
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

}
