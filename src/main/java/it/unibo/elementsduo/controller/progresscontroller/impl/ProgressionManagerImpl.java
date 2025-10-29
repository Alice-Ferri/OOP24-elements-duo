package it.unibo.elementsduo.controller.progresscontroller.impl;

import it.unibo.elementsduo.datasave.SaveManager; 
import it.unibo.elementsduo.model.progression.ProgressionState;

public class ProgressionManagerImpl { 

    private ProgressionState currentState;
    private final SaveManager saveLoadManager; 

    public ProgressionManagerImpl(SaveManager manager, ProgressionState initialState) {
        this.saveLoadManager = manager;
        this.currentState = initialState;
    }

    public ProgressionState getCurrentState() {
        return currentState;
    }
    
    public void levelCompleted(int completedLevel, long timeMillis, int gemsCollected) {
        
        currentState.addLevelCompletionTime(completedLevel, timeMillis);
        currentState.setCollectedGems(currentState.getCollectedGems() + gemsCollected);
        
        saveLoadManager.saveGame(currentState);
    }
    
    public void saveGame() {
        this.saveLoadManager.saveGame(currentState);
    }
}