package it.unibo.elementsduo.model.map.mapvalidator.api;

import it.unibo.elementsduo.model.map.level.api.Level;

public interface MapValidator {
    
    void validate(Level level) throws InvalidMapException;
    
}
