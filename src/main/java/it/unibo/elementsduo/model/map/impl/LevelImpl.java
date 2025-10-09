package it.unibo.elementsduo.model.map.impl;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;

public class LevelImpl implements Level{

    private final Set<obstacle> obstacles;

    public LevelImpl(final Set<obstacle> ob){
        this.obstacles = Set.copyOf(Objects.requireNonNull(ob));
    }

    @Override
    public Set<obstacle> getAllTiles() {
        return this.obstacles;
    }

    @Override
    public <T extends obstacle> Set<T> getObstaclesByClass(Class<T> type) {
        return this.obstacles.stream()
        .filter(type::isInstance)
        .map(type::cast)          
        .collect(Collectors.toSet());
    
    }

    @Override
    public Set<Wall> getWalls() {
        return getObstaclesByClass(Wall.class);
    }

    @Override
    public Set<Floor> getFloors() {
        return getObstaclesByClass(Floor.class);
    }

    @Override
    public Set<fireSpawn> getFireSpawn() {
        return getObstaclesByClass(fireSpawn.class);
    }

    @Override
    public Set<waterSpawn> getWaterSpawn() {
        return getObstaclesByClass(waterSpawn.class);
    }

    @Override
    public Set<fireExit> getFireExit() {
        return getObstaclesByClass(fireExit.class);
    }

    @Override
    public Set<waterExit> getWaterExit() {
        return getObstaclesByClass(waterExit.class);
    }

    
    
    
}
