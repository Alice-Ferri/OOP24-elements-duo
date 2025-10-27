package it.unibo.elementsduo.model.map.mapvalidator.impl;

import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;

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

    }

    private void checkBoundaries(final Level level) throws InvalidMapException{
        
    }

    private void checkReachability(final Level level) throws InvalidMapException{
        
    }

    private void checkEnemyFloors(final Level level) throws InvalidMapException{
        
    }
    
}
