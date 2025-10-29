package it.unibo.elementsduo.controller.progresscontroller.impl;

private ProgressionState currentState;
    private final SaveLoadManager saveLoadManager;
    
    public ProgressionManagerImpl(SaveLoadManager SaveLoadManagermanager, ProgressionState initialState) {
        this.saveLoadManager = manager;
        this.currentState = initialState;
    }

    public ProgressionState getCurrentState() {
        return currentState;
    }
    
    public void levelCompleted(int completedLevel, long timeMillis, int gemsCollected) {
        
        currentState.addLevelCompletionTime(completedLevel, timeMillis);
        currentState.setCollectedGems(currentState.getCollectedGems() + gemsCollected);
        currentState.setCurrentLevel(completedLevel + 1);
        
        saveLoadManager.saveGame(currentState);
    }
}
