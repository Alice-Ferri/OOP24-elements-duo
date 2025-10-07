package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.EnemiesType;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.resources.Position;

public class EnemyFactoryImpl implements EnemyFactory{

    @Override
    public Enemy createEnemy(EnemiesType type, Position pos) {
        return switch (type) {
            case CLASSIC -> new ClassicEnemiesImpl(pos);
            case PROJECTILE -> new ProjectilesEnemiesImpl(pos); 
        };
    }
}

    

