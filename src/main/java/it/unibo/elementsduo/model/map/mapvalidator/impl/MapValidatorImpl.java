package it.unibo.elementsduo.model.map.mapvalidator.impl;

import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;

public class MapValidatorImpl implements MapValidator{

    @Override
    public void validate(Level level) throws InvalidMapException {
        if (level.getAllObstacles().isEmpty()) {
            throw new InvalidMapException("La mappa è vuota.");
        }
        
        checkSpawnsAndExits(level);
    
        checkBoundaries(level);

        checkReachability(level);

        checkEnemyFloors(level);
    }

    private void checkSpawnsAndExits(final Level level) throws InvalidMapException{
        if (level.getObstaclesByClass(fireSpawn.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire spawn.");
        }
        if (level.getObstaclesByClass(waterSpawn.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water spawn.");
        }
        if (level.getObstaclesByClass(fireExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 fire exit.");
        }
        if (level.getObstaclesByClass(waterExit.class).size() != 1) {
            throw new InvalidMapException("La mappa deve avere esattamente 1 water exit.");
        }
    }

    private void checkBoundaries(final Level level) throws InvalidMapException{
        
    }

    private void checkReachability(final Level level) throws InvalidMapException{
        
    }

    private void checkEnemyFloors(final Level level) throws InvalidMapException{
        
    }
    
}
