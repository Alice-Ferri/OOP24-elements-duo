package it.unibo.elementsduo.model.obstacles.staticObstacles.HazardObs.api;

import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.staticObstacles.HazardObs.impl.HazardType;

/**
 * represents a hazard in the world, Hazards can kill the player depending on
 * its type
 *
 */
public interface Hazard extends Obstacle {
    /**
     * @return the specific type of hazard
     */
    HazardType getHazardType();
}
