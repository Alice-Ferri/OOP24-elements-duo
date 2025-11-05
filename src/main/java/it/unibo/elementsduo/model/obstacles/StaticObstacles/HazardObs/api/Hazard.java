package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.api;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;

/**
 * represents a hazard in the world
 * 
 * Hazards can kill the player depending on its type
 */
public interface Hazard extends Obstacle {
    HazardType getHazardType();
}
