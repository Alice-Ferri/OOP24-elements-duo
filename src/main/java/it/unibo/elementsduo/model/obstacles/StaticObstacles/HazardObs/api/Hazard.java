package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.api;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;

/**
 * Represents a hazardous static obstacle in the game world.
 * 
 * <p>
 * Hazards are obstacles that can cause negative effects to players,
 * such as dealing damage or causing death upon contact.
 */
public interface Hazard extends Obstacle {
    HazardType getHazardType();
}
