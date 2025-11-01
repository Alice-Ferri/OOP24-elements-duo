package it.unibo.elementsduo.controller.subcontroller.impl;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.model.progression.ProgressionState;
import it.unibo.elementsduo.view.LevelSelectionPanel;

/**
 * Manages the logic for the level selection screen.
 * It handles user input (selecting a level, going back) and populates
 * the view with progression data (best times, gems).
 */
public final class LevelSelectionController implements Controller {

    private final LevelSelectionPanel view;
    private final LevelSelectionNavigation controller;
    private final ProgressionManagerImpl progressionManager;
    private final ActionListener onMenuListener;
    private final Map<JButton, ActionListener> levelButtonListeners;

    /**
     * Constructs a new LevelSelectionController.
     *
     * @param panel              The {@link LevelSelectionPanel} view.
     * @param controller         The navigation controller.
     * @param progressionManager The manager for loading player progress.
     */
    public LevelSelectionController(final LevelSelectionPanel panel,
                                    final LevelSelectionNavigation controller,
                                    final ProgressionManagerImpl progressionManager) {
        this.view = panel;
        this.controller = controller;
        this.progressionManager = progressionManager;

        this.onMenuListener = e -> this.controller.goToMenu();
        this.levelButtonListeners = new HashMap<>();
    }

    @Override
    public void activate() {
        this.populateLevelData();

        this.view.getLevelButtons().forEach((button, levelNumber) -> {
            final ActionListener listener = e -> this.controller.startGame(levelNumber);
            this.levelButtonListeners.put(button, listener);
            button.addActionListener(listener);
        });

        this.view.getBackButton().addActionListener(onMenuListener);
    }

    private void populateLevelData() {
        if (this.progressionManager == null) {
            return;
        }

        final ProgressionState state = this.progressionManager.getCurrentState();

        final Map<Integer, Double> bestTimes = state.getLevelCompletionTimes();
        final Map<Integer, String> missionCompleted = state.getLevelMissionCompleted();

        this.view.setBestTimes(bestTimes);
        this.view.setMissionCompleted(missionCompleted);

        this.view.repaint();
    }

    @Override
    public void deactivate() {
        this.levelButtonListeners.forEach((button, listener) -> {
            button.removeActionListener(listener);
        });
        this.levelButtonListeners.clear();

        this.view.getBackButton().removeActionListener(onMenuListener);
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }

}
