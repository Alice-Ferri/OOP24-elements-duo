package it.unibo.elementsduo.controller.subcontroller.impl;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    private final List<ActionListener> list = new LinkedList<>();
    final Function<Integer, ActionListener> listenerProvider = (levelNumber) -> {
            return list.get(levelNumber);
        };

    /**
     * Constructs a new LevelSelectionController.
     *
     * @param panel              The {@link LevelSelectionPanel} view.
     * @param controller         The navigation controller.
     * @param progressionManager The manager for loading player progress.
     */
    public LevelSelectionController(final LevelSelectionNavigation controller,
                                    final ProgressionManagerImpl progressionManager) {
        this.view = new LevelSelectionPanel();
        this.controller = controller;
        this.progressionManager = progressionManager;

        this.onMenuListener = e -> this.controller.goToMenu();
        list.add(e -> this.controller.startGame(0));
        list.add(e -> this.controller.startGame(1));
        list.add(e -> this.controller.startGame(2));
    }

    @Override
    public void activate() {
        this.populateLevelData();

        this.view.addButtonListeners(listenerProvider,onMenuListener);

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
        
        this.view.removeButtonListeners(listenerProvider,onMenuListener);
    }

    @Override
    @SuppressFBWarnings(value = "EI", justification = "i need panel for card layout")
    public JPanel getPanel() {
        return this.view;
    }

}
