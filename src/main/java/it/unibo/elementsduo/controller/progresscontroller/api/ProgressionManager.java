package it.unibo.elementsduo.controller.progresscontroller.api;

import it.unibo.elementsduo.model.progression.ProgressionState;

public interface ProgressionManager {


    ProgressionState getCurrentState();

    void levelCompleted(int completedLevel, long timeMillis, int gemsCollected);
}
