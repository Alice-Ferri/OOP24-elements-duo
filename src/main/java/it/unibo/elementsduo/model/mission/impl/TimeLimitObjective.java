package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;

/**
 * Checks if the level was completed
 * within a specific time limit.
 */
public final class TimeLimitObjective extends AbstractObjective {

    private final double timeLimitInSeconds;

    /**
     * @param timeLimitInSeconds The time limit in seconds to beat.
     */
    public TimeLimitObjective(final double timeLimitInSeconds) {
        super("Finish the level within " + timeLimitInSeconds + " seconds");
        this.timeLimitInSeconds = timeLimitInSeconds;
    }

    @Override
    protected boolean checkObjectiveLogic(GameState finalState, double finalTimeInSeconds) {
       return this.timeLimitInSeconds >= finalTimeInSeconds;
    }

    
}
