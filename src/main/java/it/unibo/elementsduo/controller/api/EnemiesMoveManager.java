package it.unibo.elementsduo.controller.api;

import it.unibo.elementsduo.model.enemies.api.Enemy;

public interface EnemiesMoveManager {

    public void handleEdgeDetection(final Enemy enemy);
    
}
