package it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api;

import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.impl.HazardType;

/**
 * represents a hazard , hazards can kill the player depending on its type.
 */
public interface Hazard extends Obstacle {
    /**
     * @return the specific type of hazard
     */
    HazardType getHazardType();
}
