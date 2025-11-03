package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.mission.api.Objective;

/**
 * Abstract implementation of Objective.
 * Provides the common isComplete flag and description fields.
 */
public abstract class AbstractObjective implements Objective {

    private boolean isComplete;
    private final String description;

    /**
     * Constructs a new AbstractObjective.
     *
     * @param description The text description of this objective.
     */
    public AbstractObjective(final String description) {
        this.description = description;
        this.isComplete = false;
    }

    @Override
    public final boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    /**
     * Protected method for subclasses to securely set the objective as complete.
     */
    protected final void setComplete() {
        this.isComplete = true;
    }

    /**
     * This is the template method
     */
    @Override
    public final void checkCompletion(final GameState finalState, final double finalTimeInSeconds) {
        if (this.isComplete()) {
            return;
        }
        
        if (this.checkObjectiveLogic(finalState, finalTimeInSeconds)) {
            this.setComplete();
        }
    }

    /**
     * Where sub-classes have to put their logic
     *
     * @param finalState the final game state
     * @param finalTimeInSeconds the final time of the game
     * @return true if it is satisfied, otherwise false.
     */
    protected abstract boolean checkObjectiveLogic(GameState finalState, double finalTimeInSeconds);
}
