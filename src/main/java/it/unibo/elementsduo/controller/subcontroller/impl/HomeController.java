package it.unibo.elementsduo.controller.subcontroller.impl;

import java.awt.event.ActionListener;
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
    private final ActionListener onStartListener;
    private final ActionListener onLoadListener;
    private final ActionListener onGuideListener;

    /**
     * Constructs a new HomeController.
     *
     * @param panel      The {@link MenuPanel} view.
     * @param controller The navigation controller.
     */
    public HomeController(final MenuPanel panel, final HomeNavigation controller) {
        this.view = panel;

        this.onStartListener = e -> controller.startNewGame();
        this.onLoadListener = e -> controller.loadSavedGame();
        this.onGuideListener = e -> controller.gameGuide();
    }

    @Override
    public void activate() {
        this.view.getStartButton().addActionListener(this.onStartListener);
        this.view.getLoadButton().addActionListener(this.onLoadListener);
        this.view.getGuideButton().addActionListener(this.onGuideListener);
    }

    @Override
    public void deactivate() {
        this.view.getStartButton().removeActionListener(this.onStartListener);
        this.view.getLoadButton().removeActionListener(this.onLoadListener);
        this.view.getGuideButton().removeActionListener(this.onGuideListener);
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

}
