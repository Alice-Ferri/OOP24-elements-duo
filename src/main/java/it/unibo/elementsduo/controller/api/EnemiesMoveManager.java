package it.unibo.elementsduo.controller.api;

import it.unibo.elementsduo.model.enemies.api.Enemy;

public interface EnemiesMoveManager {

    void handleEdgeDetection(Enemy enemy);
    
}