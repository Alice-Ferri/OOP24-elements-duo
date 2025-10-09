package it.unibo.elementsduo.model.enemies.impl;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.utils.Position;

public class EnemyFactoryImpl implements EnemyFactory{

    @Override
    public Enemy createEnemy(char c, Position pos) {
        return switch (c) {
            case 'C' -> new ClassicEnemiesImpl(pos); // Classic enemy

            case 'S' -> new ShooterEnemyImpl(pos); // Shooter enemy

            default -> throw new IllegalArgumentException("Unknown enemy type: " + c);
        };
    }
}

    
