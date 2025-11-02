package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.mission.api.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Mission, that is formed by various objectives.
 * It is complete only if all objectives are complete.
 */
public final class Mission extends AbstractObjective {

    private boolean isComplete;
    private final List<Objective> objectives = new ArrayList<>();

    /**
     * Creates a new Mission with the given description.
     *
     * @param description the mission's description
     */
    public Mission(final String description) {
        super(description);
    }

    /**
     * Adds an objective to this mission.
     *
     * @param objective the objective to add
     */
    public void add(final Objective objective) {
        this.objectives.add(objective);
    }

    /**
     * Gets an unmodifiable list of the objectives that make up this mission.
     *
     * @return the list of objectives
     */
    public List<Objective> getObjectives() {
        return Collections.unmodifiableList(this.objectives);
    }


    /**
     * Checks the completion status of the mission by checking all sub-objectives.
     * The mission is complete if and only if all sub-objectives are complete.
     *
     * @param finalState the final state of the game
     * @param finalTimeInSeconds the final time taken in seconds
     */
    @Override
    public void checkCompletion(final GameState finalState, final double finalTimeInSeconds) {
        if (this.isComplete) {
            return;
        }
        for (final Objective child : this.objectives) {
            child.checkCompletion(finalState, finalTimeInSeconds);
        }
        if (this.objectives.stream().allMatch(Objective::isComplete)) {
            this.isComplete = true;
        }
    }
}
