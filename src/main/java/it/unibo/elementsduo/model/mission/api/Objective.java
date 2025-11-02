package it.unibo.elementsduo.model.mission.api;

import it.unibo.elementsduo.model.gamestate.api.GameState;

/**
 * Represents a single game objective.
 * The check is performed only at the end of the game.
 */
public interface Objective {
    /**
     * Checks if this objective has been completed,
     * based on the final game state and the time taken.
     *
     * @param finalState         The GameState at the end of the match.
     * @param finalTimeInSeconds The time (in seconds) taken to finish the level.
     */
    void checkCompletion(GameState finalState, double finalTimeInSeconds);

    /**
     * @return true if the objective is complete, false otherwise.
     */
    boolean isComplete();

    /**
     * @return A textual description of the objective for the UI.
     */
    String getDescription();
}