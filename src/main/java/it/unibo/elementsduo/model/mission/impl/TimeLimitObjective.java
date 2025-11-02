package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;

/**
 * Checks if the level was completed
 * within a specific time limit.
 */
public final class TimeLimitObjective extends AbstractObjective {

    private boolean isComplete;
    private final double timeLimitInSeconds;

    /**
     * @param timeLimitInSeconds The time limit in seconds to beat.
     */
    public TimeLimitObjective(final double timeLimitInSeconds) {
        super("Finish the level within " + timeLimitInSeconds + " seconds");
        this.timeLimitInSeconds = timeLimitInSeconds;
    }

    @Override
    public void checkCompletion(final GameState finalState, final double finalTimeInSeconds) {
        if (this.isComplete) {
            return;
        }

        if (finalState.didWin() && finalTimeInSeconds <= this.timeLimitInSeconds) {
            this.isComplete = true;
        }
    }
}
