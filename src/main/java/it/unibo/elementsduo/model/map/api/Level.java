package it.unibo.elementsduo.model.map.api;

import java.util.Set;

import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;

public interface Level {

    
    Set<obstacle> getAllTiles();

    <T extends obstacle> Set<T> getObstaclesByClass(final Class<T> type);
    Set<Wall> getWalls();
    Set<Floor> getFloors();
    Set<fireSpawn> getFireSpawn();
    Set<waterSpawn> getWaterSpawn();
    Set<fireExit> getFireExit();
    Set<waterExit> getWaterExit();


}
