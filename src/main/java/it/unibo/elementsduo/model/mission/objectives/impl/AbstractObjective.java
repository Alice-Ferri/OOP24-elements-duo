package it.unibo.elementsduo.model.mission.objectives.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.mission.objectives.api.Objective;

/**
 * Abstract implementation of Objective.
 * Provides the common isComplete flag and description fields.
 */
public abstract class AbstractObjective implements Objective {

    protected boolean isComplete = false;
    private final String description;

    public AbstractObjective(final String description) {
        this.description = description;
    }

    @Override
    public final boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public abstract void checkCompletion(GameState finalState, double finalTimeInSeconds);
}