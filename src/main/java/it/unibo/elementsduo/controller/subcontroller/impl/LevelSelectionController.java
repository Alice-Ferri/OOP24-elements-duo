package it.unibo.elementsduo.controller.subcontroller.impl;

import java.util.Map;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.progresscontroller.impl.ProgressionManagerImpl;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.model.progression.ProgressionState;
import it.unibo.elementsduo.view.LevelSelectionPanel;

public class LevelSelectionController implements Controller{

    private final LevelSelectionPanel view;
    private final LevelSelectionNavigation controller;
    private final ProgressionManagerImpl progressionManager; 

    public LevelSelectionController (final LevelSelectionPanel panel, final LevelSelectionNavigation controller,final ProgressionManagerImpl progressionManager){
        this.view = panel;
        this.controller = controller;
        this.progressionManager = progressionManager;
    }

    @Override
    public void activate() {
        this.populateLevelData(); 
        
        this.view.getLevelButtons().forEach((button, levelNumber) -> {
            button.addActionListener(e -> {
                this.controller.startGame(levelNumber);
            });
        });

        this.view.getBackButton().addActionListener(e -> {
            this.controller.goToMenu();
        });
    }
    
    private void populateLevelData() {
        if (this.progressionManager == null) {
            return; 
        }
        
        final ProgressionState state = this.progressionManager.getCurrentState();
        
        final Map<Integer, Long> bestTimes = state.getLevelCompletionTimes();
        final Map<Integer, Integer> levelGems = state.getLevelGemsCollected();

        this.view.setBestTimes(bestTimes);
        this.view.setLevelGems(levelGems);
        
        this.view.repaint();
    }

    @Override
    public void deactivate() {
         this.view.getLevelButtons().keySet().forEach(button -> button.removeActionListener(null));

        this.view.getBackButton().removeActionListener(null); 
    }
    

    @Override
    public JPanel getPanel() {
        return this.view;
    }
    
}