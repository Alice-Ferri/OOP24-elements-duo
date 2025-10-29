
package it.unibo.elementsduo.model.progression;

import java.util.HashMap;
import java.util.Map;

public class ProgressionState {

    private int currentLevel; 
    private int collectedGems; 
    private final Map<Integer, Long> levelCompletionTimes = new HashMap<>(); 

    public ProgressionState() { }

    public ProgressionState(int currentLevel, int collectedGems) {
        this.currentLevel = currentLevel;
        this.collectedGems = collectedGems;
    }

    public void addLevelCompletionTime(int levelNumber, long timeMillis) {
        
        if (this.levelCompletionTimes.containsKey(levelNumber)) {
            
            final long oldTime = this.levelCompletionTimes.get(levelNumber);
            if (timeMillis < oldTime) {
                this.levelCompletionTimes.put(levelNumber, timeMillis);
            }     
        } else {
            this.levelCompletionTimes.put(levelNumber, timeMillis);
            System.out.println("Primo completamento del Livello " + levelNumber + " registrato.");
        }
    }
       
    public int getCurrentLevel() { 
        return currentLevel; 
    }
    public void setCurrentLevel (int currentLevel) { 
        this.currentLevel = currentLevel; 
    }
    public int getCollectedGems() { 
        return collectedGems; 
    }
    public void setCollectedGems(int collectedGems) { 
        this.collectedGems = collectedGems; 
    }
    public Map<Integer, Long> getLevelCompletionTimes() { 
        return levelCompletionTimes; 
    }
}