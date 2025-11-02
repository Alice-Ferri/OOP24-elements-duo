package it.unibo.elementsduo.model.mission.objectives.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.mission.objectives.api.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Mission, that is formed by various objectives
 * It is complete only if all objectives are complete.
 */
public class Mission extends AbstractObjective {

    private final List<Objective> objectives = new ArrayList<>();

    public Mission(final String description) {
        super(description);
    }

    public void add(final Objective objective) {
        this.objectives.add(objective);
    }

    public List<Objective> getobjectives() {
        return Collections.unmodifiableList(this.objectives);
    }

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